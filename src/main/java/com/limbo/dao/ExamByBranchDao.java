package com.limbo.dao;

import com.limbo.dao.provider.ExamByBranchSqlProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * Created by limbo on 17-4-18.
 */
@Mapper
public interface ExamByBranchDao {

    @InsertProvider(type = ExamByBranchSqlProvider.class,
    method = "insertExamByBranch")
    int insert(String[] branchIds, String examId);

}
