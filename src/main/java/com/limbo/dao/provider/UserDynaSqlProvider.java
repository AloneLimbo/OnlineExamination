package com.limbo.dao.provider;

import com.limbo.entity.User;

import java.util.LinkedList;

/**
 * UserDao 动态sql
 * Created by limbo on 17-4-5.
 */
public class UserDynaSqlProvider {

    public String batchInsertUser(final LinkedList<User> users,final String founderid){
        StringBuilder sql=new StringBuilder();
        for(User user:users){
            sql.append("INSERT INTO user(user_id, user_name, real_name, password, user_status, branch_id, email)\n" + "  SELECT uuid(),'").append(user.getUser_name()).append("','").append(user.getReal_name()).append("',").append("SHA1('").append(user.getPassword()).append("')").append(",3,branch_id,'").append(user.getEmail()).append("' FROM branch\n").append("WHERE branch_name='").append(user.getBranch_name()).append("' AND founder_id='").append(founderid).append("';");
        }
        return String.valueOf(sql);
    }

}
