package com.limbo.dao.provider;

import com.limbo.entity.Branch;
import org.apache.ibatis.jdbc.SQL;

/**
 * BranchDao动态Sql
 * Created by limbo on 17-4-5.
 */
public class BranchSqlProvider {

    public String insertBranch(final Branch branch){
        return new SQL(){{
            INSERT_INTO("branch");
            VALUES("branch_id","#{branch_id}");
            VALUES("branch_name","#{branch_name}");
            VALUES("founder_id","#{founder_id}");
            VALUES("","");
        }}.toString();
    }

}
