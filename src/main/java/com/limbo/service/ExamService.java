package com.limbo.service;

import com.limbo.dto.Result;
import com.limbo.entity.*;

/**
 *
 * Created by limbo on 17-4-8.
 */
public interface ExamService {

    Result createQuestionBank(QuestionBank questionBank);

    Result questionBankNameExists(String questionBankName, String founder_id);

    Result getQuestionBanks(String founderId, int offset);

    Result createQuestion(Question question);

    Result getQuestions(String questionBankId, int offset);

    Result searchQuestionBank(String search, String founderId, int isFirst);

    Result getQuestionByIsWhat(String questionBankId, int isFirst, int isWhat);

    Result createPaper(Paper paper);

    Result searchPaper(String paperName, String founderId);

    Result searchBranch(String searchWord, String founderId);

    Result getBranches(String founderId, int offset);

    Result createExam(Exam exam);

    Result getExamsByBranch(String userId);

    Result getPaperById(String paperId);

    Result createBranch(String userId, String branchName);

    Result branchNameExists(String branchName, String founderId);

    Result deleteBranch(String branchId);

    Result updateBranch(Branch branch);

    Result deleteQuestionBank(String questionBankId, String founderId);

    Result submitPaper(HistoryExam historyExam, String founderId);

    Result getFlawSweeper(String founderId, int offset);

    Result getWrongQuestions(String examId, String founderId);

    Result getReportCard(String founderId);

    Result getPapers(String fonderId, int offset);

    Result getExamInfoByFounderId(String founderId);

    Result deleteQuestion(String questionId, String founderId);

    Result queryBranchExamCorrect(String examId);

    Result querySubjectiveQuestions(String examId, String founderId);

    Result submitCorrect(HistoryExam historyExam);
}
