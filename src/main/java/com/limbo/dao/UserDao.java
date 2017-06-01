package com.limbo.dao;

import com.limbo.dao.provider.UserDynaSqlProvider;
import com.limbo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 用户表数据操作
 * Created by limbo on 17-4-5.
 */
@Mapper
public interface UserDao {

    @Insert("INSERT INTO user(user_id,user_name,real_name,password, user_status, branch_id, email) " +
            "VALUE (#{user_id},#{user_name},#{real_name},#{password},#{user_status},#{branch_id},#{email})")
    int insertUser(User user);

    @Select("SELECT count(user_id) FROM user u Join branch b ON u.branch_id=b.branch_id and b.founder_id=#{founderId};")
    int queryCountByFounderId(@Param("founderId") String founderId);

    @Select("SELECT 1 FROM user WHERE user_name = #{user_name} LIMIT 1")
    String userNameExists(@Param("user_name") String user_name);

    @Select("SELECT password,user_id,user_status,branch_id,user_name FROM user WHERE user_name=#{user_name} AND user_state=1")
    User queryByUserName(@Param("user_name") String user_name);

    @InsertProvider(type = UserDynaSqlProvider.class,
    method = "batchInsertUser")
    int batchInsertUser(LinkedList<User> users, String founderId);

    @Select("SELECT  u.branch_id,user_id,user_name,real_name,branch_name,user_create_date,user_status,user_state FROM user u JOIN branch b\n" +
            "WHERE u.branch_id=b.branch_id AND b.founder_id=#{founderId} and u.user_status!=2 ORDER BY u.user_create_date LIMIT #{offset},8\n")
    List<User> queryUsersByFounderId(@Param("founderId") String founderId, @Param("offset") int offset);

    @Insert("INSERT INTO user(user_id, user_name, real_name, password, user_status, branch_id, email) \n" +
            "  VALUE(uuid(),#{user_name},#{real_name},#{password},3,#{branch_id},#{email})")
    int singleInsertUser(User user);

    @Delete("delete from user where user_id=#{userId} and #{founderId}=(select founder_id from branch where branch_id=#{branchId})")
    int deleteUserById(@Param("userId") String userId, @Param("branchId") String branchId, @Param("founderId") String founderId);

    @Select("SELECT u.user_id,u.real_name,report_card.state,report_card.score FROM\n" +
            "  (SELECT * FROM user WHERE user.branch_id=#{branchId}) u LEFT JOIN report_card ON u.user_id=report_card.founder_id")
    List<User> queryUserExamCorrect(@Param("branchId") String branchId);
}
