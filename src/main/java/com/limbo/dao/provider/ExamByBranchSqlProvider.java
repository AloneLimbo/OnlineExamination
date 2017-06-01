package com.limbo.dao.provider;


import org.apache.ibatis.jdbc.SQL;

/**
 *
 * Created by limbo on 17-4-18.
 */
public class ExamByBranchSqlProvider {

    public String insertExamByBranch(final String[] branchIds,final String examId){

//        StringBuilder sql=new StringBuilder("");
//
//        for(String s : branchIds){
//            sql.append("insert into exam_branch(id,branch_id,exam_id) values(uuid(),").append(s).append(",").append(examId).append(");");
//        }
//        return sql.toString();

        String sql ="";

        for(String s:branchIds){
            sql+=new SQL(){{
                    INSERT_INTO("exam_branch");
                    VALUES("id","uuid()");
                    VALUES("branch_id","'"+s+"'");
                    VALUES("exam_id","'"+examId+"'");
            }}.toString()+";";
        }


        return sql;
    }

}
