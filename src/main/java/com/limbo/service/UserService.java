package com.limbo.service;

import com.limbo.dto.Result;
import com.limbo.entity.User;

import java.io.FileInputStream;

/**
 * 用户操作业务接口
 * Created by limbo on 17-4-5.
 */
public interface UserService {

    Result login(User loginData);

    Result signUp(User user);

    Result userNameExists(String username);

    Result batchInsertUser(FileInputStream excelFileInputStream, String founderId);

    Result queryUsersByFounderId(String founderId, int offset);

    Result addUser(User user);

    Result deleteUserById(String userId, String branchId, String founderId);

    Result queryUserExamCorrect(String branchId);

}
