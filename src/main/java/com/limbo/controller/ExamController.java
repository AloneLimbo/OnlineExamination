package com.limbo.controller;

import com.limbo.dto.Result;
import com.limbo.entity.*;
import com.limbo.service.ExamService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by limbo on 17-4-8.
 */
@RestController
public class ExamController {


    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }
    @PostMapping(value = "/api/questionBank")
    public Result createQuestionBank(@RequestBody QuestionBank questionBank, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        questionBank.setFounder_id(claims.getId());
        return examService.createQuestionBank(questionBank);
    }

    @GetMapping(value = "/api/questionBank/{offset}")
    public Result getQuestionBank(HttpServletRequest request, @PathVariable("offset") int offset){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getQuestionBanks(claims.getId(),offset);
    }

    @GetMapping(value = "/api/questionBankName/exists/{questionBankName}")
    public Result questionBankNameExists(@PathVariable("questionBankName") String questionBankName, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.questionBankNameExists(questionBankName,claims.getId());
    }

    @PostMapping(value = "/api/question")
    public Result createQuestion(@RequestBody Question question, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        question.setFounder_id(claims.getId());
        return examService.createQuestion(question);
    }

    @GetMapping(value = "/api/questions/{questionBankId}")
    public Result getQuestions(@PathVariable("questionBankId") String questionBankId, HttpServletRequest request){
        return examService.getQuestions(questionBankId,Integer.parseInt(request.getHeader("isFirst")));
    }

    @GetMapping(value = "/api/questionBank/search/{word}")
    public Result searchQuestionBank(@PathVariable("word") String search, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.searchQuestionBank(search,claims.getId(),Integer.parseInt(request.getHeader("isFirst")));
    }
    @GetMapping(value = "/api/questions/{questionBankId}/{isWhat}")
    public Result getQuestionByIsWhat(@PathVariable("questionBankId") String questionBankId, HttpServletRequest request, @PathVariable("isWhat") int isWhat){
        return examService.getQuestionByIsWhat(questionBankId,Integer.parseInt(request.getHeader("isFirst")),isWhat);
    }

    @PostMapping(value = "/api/paper")
    public Result createPaper(@RequestBody Paper paper, HttpServletRequest request){

        Claims claims = (Claims) request.getAttribute("claims");
        paper.setFounder_id(claims.getId());
        return examService.createPaper(paper);
    }

    @GetMapping(value = "/api/paper/search/{word}")
    public Result searchPaper(@PathVariable("word") String paperName, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.searchPaper(paperName,claims.getId());
    }

    @GetMapping(value = "/api/branch/search/{word}")
    public Result searchBranch(@PathVariable("word") String searchWord, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.searchBranch(searchWord, claims.getId());
    }

    @PostMapping(value = "/api/exam")
    public Result createExam(@RequestBody Exam exam, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        exam.setFounder_id(claims.getId());
        return examService.createExam(exam);
    }


    @GetMapping(value = "/api/exam")
    public Result getExamByBranch(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getExamsByBranch(claims.getId());
    }

    @GetMapping(value = "/api/paper/{examId}")
    public Result getPaperByExamId(@PathVariable("examId") String examId){
        return examService.getPaperById(examId);
    }

    @GetMapping(value = "/api/branches/{offset}")
    public Result getBranches(HttpServletRequest request, @PathVariable("offset") int offset){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getBranches(claims.getId(),offset);
    }

    @PostMapping(value = "/api/branch")
    public Result createBranch(@RequestBody()Branch branch, HttpServletRequest request){

        Claims claims = (Claims) request.getAttribute("claims");
        return examService.createBranch(claims.getId(),branch.getBranch_name());
    }

    @GetMapping(value = "/api/branch/exists/{branch_name}")
    public Result branchNameExists(@PathVariable("branch_name") String branch_name, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.branchNameExists(branch_name,claims.getId());
    }

    @DeleteMapping(value = "/api/branch/{branchId}")
    public Result deleteBranch(@PathVariable("branchId") String branchId){
        return examService.deleteBranch(branchId);
    }

    @PutMapping(value = "/api/branch")
    public Result updateBranch(@RequestBody() Branch branch, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        branch.setFounder_id(claims.getId());
        return examService.updateBranch(branch);
    }

    @DeleteMapping(value = "/api/questionBank/{questionBankId}")
    public Result deleteQuestionBank(@PathVariable("questionBankId") String questionBankId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.deleteQuestionBank(questionBankId,claims.getId());
    }

    @PostMapping(value = "/api/user/paper")
    public Result submitPaper(@RequestBody HistoryExam historyExam, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.submitPaper(historyExam,claims.getId());
    }

    @GetMapping(value = "/api/flawSweeper/{offset}")
    public Result getFlawSweeper(@PathVariable("offset") int offset, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getFlawSweeper(claims.getId(),offset);
    }

    @GetMapping(value = "/api/flawSweeper/wrongQuestions/{examId}")
    public Result getWrongQuestions(@PathVariable("examId") String examId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getWrongQuestions(examId,claims.getId());
    }

    @GetMapping(value = "/api/reportCard")
    public Result getReportCard(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getReportCard(claims.getId());
    }

    @GetMapping(value = "/api/papers/{offset}")
    public Result getPapers(HttpServletRequest request, @PathVariable("offset") int offset){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getPapers(claims.getId(),offset);
    }

    @GetMapping(value = "/api/examInfo")
    public Result getExamInfoByFounderId(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.getExamInfoByFounderId(claims.getId());
    }

    @DeleteMapping(value = "/api/question/{questionId}")
    public Result deleteQuestion(@PathVariable("questionId") String questionId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return examService.deleteQuestion(questionId,claims.getId());
    }

    @GetMapping(value = "/api/branchCorrect/{examId}")
    public Result getBranchExamCorrect(@PathVariable("examId") String examId){
        return examService.queryBranchExamCorrect(examId);
    }

    @GetMapping(value = "/api/subjectiveQuestions/{examId}/{founderId}")
    public Result getSubjectiveQuestions(@PathVariable("examId") String examId, @PathVariable("founderId")String founderId){
        return examService.querySubjectiveQuestions(examId,founderId);
    }

    @PostMapping(value = "/api/correct")
    public Result submitCorrect(@RequestBody HistoryExam historyExam){
        return examService.submitCorrect(historyExam);
    }
}
