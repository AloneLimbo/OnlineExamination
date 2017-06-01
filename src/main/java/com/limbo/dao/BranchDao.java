package com.limbo.dao;

import com.limbo.entity.Branch;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门表数据操作
 * Created by limbo on 17-4-5.
 */
@Mapper
public interface BranchDao {


    @Select("select branch_id,branch_name from branch where branch_name like #{searchWord} AND founder_id=#{founderId} AND branch_state=1")
    List<Branch> searchBranch(@Param("searchWord") String searchWord, @Param("founderId") String founderId);

    @Insert("INSERT INTO branch(branch_id, branch_name,founder_id, branch_state)\n" +
            "  VALUE (#{branch_id},#{branch_name},#{founder_id},TRUE)")
    int insertBranch(@Param("founder_id") String founder_id, @Param("branch_id") String branch_id, @Param("branch_name") String branch_name);

    @Select("SELECT b.branch_id,branch_name,branch_create_date,branch_state,u.real_name\n" +
            "FROM branch b JOIN user u ON b.founder_id = u.user_id AND b.founder_id=#{founderID} ORDER BY b.branch_create_date DESC LIMIT #{offset},8")
    List<Branch> getBranches(@Param("founderID") String founderID, @Param("offset") int offset);

    @Select("SELECT 1 FROM branch WHERE branch_name = #{branch_name} and founder_id=#{founderId} LIMIT 1")
    String branchNameExists(@Param("branch_name") String branch_name, @Param("founderId") String founderId);

    @Delete("delete from branch where branch_id=#{branchId}")
    int deleteBranch(@Param("branchId") String branchId);

    @Select("SELECT count(branch_id) FROM branch WHERE founder_id=#{founderId}")
    int queryCountByFounderId(@Param("founderId") String founderId);

    @Update("UPDATE branch SET branch_name=#{branchName} WHERE branch_id=#{branchId} AND founder_id=#{founderId}")
    int updateBranch(@Param("founderId") String founderId, @Param("branchId") String branchId, @Param("branchName") String branchName);

    @Select("SELECT x.branch_id,x.branch_name,ifnull(y.notCorrectedNum,0)as notCorrectedNum FROM (SELECT branch.branch_id,branch_name FROM exam_branch,branch WHERE exam_branch.exam_id=#{examId} AND exam_branch.branch_id=branch.branch_id) x\n" +
            " LEFT JOIN (SELECT exam_branch.branch_id ,count(report_card.report_card_id) AS notCorrectedNum FROM exam_branch,user,report_card WHERE exam_branch.exam_id=#{examId} AND user.branch_id=exam_branch.branch_id AND report_card.founder_id=user.user_id AND report_card.state=2 GROUP BY exam_branch.branch_id) y\n" +
            "  ON x.branch_id = y.branch_id;")
    List<Branch> queryBranchExamCorrect(@Param("examId") String examId);
}
