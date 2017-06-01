package com.limbo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limbo.dao.*;
import com.limbo.dto.PageData;
import com.limbo.dto.QuestionTemp;
import com.limbo.dto.Result;
import com.limbo.entity.*;
import com.limbo.service.ExamService;
import com.limbo.util.JacksonUtil;
import org.apache.ibatis.binding.BindingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by limbo on 17-4-8.
 */
@Service
public class ExamServiceImpl implements ExamService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final QuestionBankDao questionBankDao;

    private final QuestionDao questionDao;

    private final PaperDao paperDao;

    private final BranchDao branchDao;

    private final ExamDao examDao;

    private final ExamByBranchDao examByBranchDao;

    private final FlawSweeperDao flawSweeperDao;

    private final ReportCardDao reportCardDao;

    private final StringRedisTemplate stringRedisTemplate;

    private final HistoryExamDao historyExamDao;

    @Autowired
    public ExamServiceImpl(QuestionBankDao questionBankDao, QuestionDao questionDao, PaperDao paperDao, BranchDao branchDao, ExamDao examDao, ExamByBranchDao examByBranchDao, FlawSweeperDao flawSweeperDao, ReportCardDao reportCardDao, StringRedisTemplate stringRedisTemplate, HistoryExamDao historyExamDao) {
        this.questionBankDao = questionBankDao;
        this.questionDao = questionDao;
        this.paperDao = paperDao;
        this.branchDao = branchDao;
        this.examDao = examDao;
        this.examByBranchDao = examByBranchDao;
        this.flawSweeperDao = flawSweeperDao;
        this.reportCardDao = reportCardDao;
        this.stringRedisTemplate = stringRedisTemplate;
        this.historyExamDao = historyExamDao;
    }

    @Override
    public Result createQuestionBank(QuestionBank questionBank) {
        try {
            if(questionBankDao.insertQuestionBank(questionBank)==1){
                return new Result(0,"OK",new Date().getTime(),null);
            }else {
                return new Result(1,"error",new Date().getTime(),null);
            }
        }catch (DataIntegrityViolationException e){
           logger.error("questionBankName Error:for createQuestionBank founderId:{}",questionBank.getFounder_id());
        }
        return new Result(2,"题库名长度应在36字符之内。",new Date().getTime(),null);
    }

    @Override
    public Result questionBankNameExists(String questionBankName, String founder_id) {
        String check;
        check=questionBankDao.questionBankNameExists(questionBankName,founder_id);
        if("1".equals(check)){
            return new Result(1,"error",new Date().getTime(),null);
        }else {
            return new Result(0,"OK",new Date().getTime(),null);
        }
    }

    @Override
    public Result getQuestionBanks(String founderId, int offset) {

        if(offset==1){
            return new Result(0,"OK",new Date().getTime(),new PageData(questionBankDao.queryCountByFounderId(founderId),questionBankDao.queryQuestionBanksByFounderId(founderId,0)));
        }else {
            return new Result(0,"OK",new Date().getTime(),questionBankDao.queryQuestionBanksByFounderId(founderId,(offset-1)*8));

        }
    }

    @Override
    @Transactional
    public Result createQuestion(Question question) {
        question.setFromDataBase(false);
        if(questionDao.insertQuestion(question)==1&&questionBankDao.updateQuestionBankQuestionNum(question.getQuestion_bank_id(),question.getIs_what())==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error",new Date().getTime(),null);
        }
    }

    @Override
    public Result getQuestions(String questionBankId, int offset) {

        if(offset==1) {
            List<Question> questions = questionDao.queryQuestionByQBId(questionBankId, 0);
            if (questions != null) {
                try {
                    return new Result(0, "OK", new Date().getTime(),new PageData(questionBankDao.queryQuestionNumByQuestionBankId(questionBankId),questions));
                }catch (BindingException e){
                    logger.error("Unknown questionBankId {}",questionBankId);
                    return new Result(1, "Unknown questionBankId", new Date().getTime(), null);
                }
            } else {
                return new Result(2, "Unknown questionBankId", new Date().getTime(), null);
            }
        }else {
            List<Question> questions = questionDao.queryQuestionByQBId(questionBankId, (offset - 1) * 5);
            if (questions != null) {
                return new Result(0, "OK", new Date().getTime(), questions);
            } else {
                return new Result(1, "error", new Date().getTime(), null);
            }
        }
    }

    @Override
    public Result searchQuestionBank(String search, String founderId, int isFirst) {
        if(isFirst==1){
            return new Result(0,"OK",new Date().getTime(),questionBankDao.searchQuestionBank("%%",founderId));
        }else {
            return new Result(0,"OK",new Date().getTime(),questionBankDao.searchQuestionBank("%"+search+"%",founderId));
        }
    }

    @Override
    public Result getQuestionByIsWhat(String questionBankId, int isFirst, int isWhat) {
        if(isFirst==1) {
            List<Question> questions = questionDao.queryQuestionByIsWhat(questionBankId, 0,isWhat);
            if (questions != null) {
                switch (isWhat){
                    case 1:return new Result(0, "OK", new Date().getTime(),new PageData(questionBankDao.querySingleQuestionNumByQuestionBankId(questionBankId),questions));
                    case 2:return new Result(0, "OK", new Date().getTime(),new PageData(questionBankDao.queryTFNumByQuestionBankId(questionBankId),questions));
                    case 3:return new Result(0, "OK", new Date().getTime(),new PageData(questionBankDao.querySubjectiveQuestionNumByQuestionBankId(questionBankId),questions));
                    default:return new Result(1, "error", new Date().getTime(), null);
                }
            } else {
                return new Result(1, "error", new Date().getTime(), null);
            }
        }else {
            List<Question> questions = questionDao.queryQuestionByIsWhat(questionBankId, (isFirst - 1) * 5,isWhat);
            if (questions != null) {
                return new Result(0, "OK", new Date().getTime(), questions);
            } else {
                return new Result(2, "error", new Date().getTime(), null);
            }
        }
    }

    @Override
    public Result createPaper(Paper paper) {
        paper.setFromDataBase(false);
        if(paperDao.insertPaper(paper)==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error",new Date().getTime(),null);
        }
    }

    @Override
    public Result searchPaper(String paperName, String founderId) {

        return new Result(0,"OK",new Date().getTime(),paperDao.searchPaper("%"+paperName+"%",founderId));
    }

    @Override
    public Result searchBranch(String searchWord, String founderId) {
        return new Result(0,"Ok",new Date().getTime(),branchDao.searchBranch("%"+searchWord+"%",founderId));
    }

    @Override
    @Transactional
    public Result createExam(Exam exam) {

        String exam_id = UUID.randomUUID().toString();
        exam.setExam_id(exam_id);
        if(examDao.insertExam(exam)==1){
            if(examByBranchDao.insert(exam.getBranchId(),exam.getExam_id())==1){
                return new Result(0,"OK",new Date().getTime(),null);
            }
        }
        return new Result(1,"error",new Date().getTime(),null);
    }

    @Override
    public Result getExamsByBranch(String userId) {
        return new Result(0,"Ok",new Date().getTime(),examDao.queryExamsByBranch(userId));
    }

    @Override
    public Result getPaperById(String paperId) {
        Paper paper = null;
        String paperJson = stringRedisTemplate.opsForValue().get(paperId);
        if(paperJson==null){

            paper = paperDao.queryPaperById(paperId);
            try {
                stringRedisTemplate.opsForValue().set(paperId,
                        JacksonUtil.fullDataConversion(paper),
                        10, TimeUnit.SECONDS);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return new Result(0,"OK",new Date().getTime(),paper);
        }else {
            try {
                paper = JacksonUtil.fullDataBinding(paperJson,Paper.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Result(0,"OK",new Date().getTime(),paper);
        }
    }

    @Override
    public Result getBranches(String founderId, int offset) {
        if(offset==1){
            return new Result(0,"OK",new Date().getTime(),new PageData(branchDao.queryCountByFounderId(founderId),branchDao.getBranches(founderId,(offset-1)*8)));
        }else {
            return new Result(0,"OK",new Date().getTime(),branchDao.getBranches(founderId,(offset-1)*8));
        }
    }

    @Override
    public Result createBranch(String userId, String branchName) {
        if(branchDao.insertBranch(userId,UUID.randomUUID().toString(),branchName)==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error",new Date().getTime(),null);
        }
    }

    @Override
    public Result branchNameExists(String branchName, String founderId) {
        String check;
        check=branchDao.branchNameExists(branchName,founderId);
        if("1".equals(check)){
            return new Result(1,"error",new Date().getTime(),null);
        }else {
            return new Result(0,"OK",new Date().getTime(),null);
        }
    }

    @Override
    public Result deleteBranch(String branchId) {
        if(branchDao.deleteBranch(branchId)==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error!",new Date().getTime(),null);
        }
    }

    @Override
    public Result updateBranch(Branch branch) {
        if(branchDao.updateBranch(branch.getFounder_id(),branch.getBranch_id(),branch.getBranch_name())==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }
        return new Result(1,"error",new Date().getTime(),null);
    }

    @Override
    public Result deleteQuestionBank(String questionBankId, String founderId) {
        if(questionBankDao.deleteQuestionBankById(questionBankId,founderId)==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error",new Date().getTime(),null);
        }
    }

    @Override
    public Result submitPaper(final HistoryExam historyExam, final String founderId) {
        int score=0;
        String check;
        List<Question> questions = questionDao.queryQuestionByQuestionId(historyExam.getAutoScoringQuestions());

        for(QuestionTemp q : historyExam.getAutoScoringQuestions()){
            for(Question question : questions){
                if(q.getQuestion_id().equals(question.getQuestion_id())){
                    if(q.getExaminee_answer().equals(question.getAnswer())){
                        score+=q.getScore();
                    }else {
                        q.setFounder_id(founderId);
                        check=flawSweeperDao.questionExists(q.getQuestion_id(),historyExam.getExam_id());
                        if("1".equals(check)){
                            q.setExam_id(historyExam.getExam_id());
                            flawSweeperDao.update(q);
                        }else {
                            q.setExam_id(historyExam.getExam_id());
                            flawSweeperDao.insert(q);
                        }
                    }
                }
            }
        }
        //1:已批改;2:未批改
        if(historyExam.getSubjectiveQuestions().size()==0){
            reportCardDao.insert(new ReportCard(score,founderId,historyExam.getExam_id(),1));
        }else {
            reportCardDao.insert(new ReportCard(0,founderId,historyExam.getExam_id(),2));
        }
        historyExam.setExam_score(score);
        historyExam.setFounder_id(founderId);
        System.out.println(historyExamDao.insert(historyExam));
        return null;
    }

    @Override
    public Result getFlawSweeper(String founderId, int offset) {

        if(offset==1){
            return  new Result(0,"OK",new Date().getTime(),new PageData(flawSweeperDao.queryFlawSweeperNumByFounderId(founderId),flawSweeperDao.queryFlawSweeperByFounderId(founderId,0)));
        }else {
            return new Result(0,"OK",new Date().getTime(),flawSweeperDao.queryFlawSweeperByFounderId(founderId,(offset-1)*8));
        }
    }

    @Override
    public Result getWrongQuestions(String examId, String founderId) {
        return new Result(0,"OK",new Date().getTime(),flawSweeperDao.getWrongQuestions(examId,founderId));
    }

    @Override
    public Result getReportCard(String founderId) {
        return new Result(0,"OK",new Date().getTime(),reportCardDao.queryReportCard(founderId));
    }

    @Override
    public Result getPapers(String founderId, int offset) {
        if(offset==1){
            return new Result(0,"OK",new Date().getTime(),new PageData(paperDao.queryCountByFounderId(founderId),paperDao.queryAllByFounderId(founderId,0)));
        }else {
            return new Result(0,"OK",new Date().getTime(),paperDao.queryAllByFounderId(founderId,(offset-1)*8));

        }
    }

    @Override
    public Result getExamInfoByFounderId(String founderId) {
        return new Result(0,"OK",new Date().getTime(),examDao.queryExamInfoByFounderId(founderId));
    }

    @Override
    public Result deleteQuestion(String questionId, String founderId) {
        if(questionDao.deleteQuestion(questionId,founderId)==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error",new Date().getTime(),null);
        }
    }

    @Override
    public Result queryBranchExamCorrect(String examId) {
        return new Result(0,"OK",new Date().getTime(),branchDao.queryBranchExamCorrect(examId));
    }


    @Override
    public Result querySubjectiveQuestions(String examId, String founderId) {
        HistoryExam historyExam = historyExamDao.querySubjectiveQuestions(examId,founderId);
        historyExam.setSubjective_questions(null);
        return new Result(0,"OK",new Date().getTime(),historyExam);
    }

    @Override
    @Transactional
    public Result submitCorrect(HistoryExam historyExam) {
        if(historyExam!=null){
            int score=0;
            for(QuestionTemp q : historyExam.getSubjectiveQuestions()){
                score+=q.getCorrectScore();
            }
            if(reportCardDao.updateScore(score,historyExam.getExam_id(),historyExam.getFounder_id())==1){
                if(historyExamDao.updateSubjectiveQuestions(historyExam)==1){
                    return new Result(0,"OK",new Date().getTime(),null);
                }else {
                    return new Result(2,"error",new Date().getTime(),null);
                }
            }else {
                return new Result(3,"error",new Date().getTime(),null);
            }
        }else {
            return new Result(1,"HistoryExam is Null!",new Date().getTime(),null);

        }
    }
}
