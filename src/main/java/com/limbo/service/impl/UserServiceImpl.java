package com.limbo.service.impl;

import com.limbo.dao.BranchDao;
import com.limbo.dao.UserDao;
import com.limbo.dto.PageData;
import com.limbo.dto.Result;
import com.limbo.entity.User;
import com.limbo.service.UserService;
import com.limbo.util.ParseExcelUtil;
import com.limbo.util.StrCheck;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;


/**
 * 用户操作业务接口实现类
 * Created by limbo on 17-4-5.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final BranchDao branchDao;
    @Autowired
    public UserServiceImpl(UserDao userDao, BranchDao branchDao) {
        this.userDao = userDao;
        this.branchDao = branchDao;
    }

    @Override
    public Result login(final User loginData) {

        User user;

        user=userDao.queryByUserName(loginData.getUser_name()); //通过用户名查询用户
        //判断该用户是否存在
        if(user==null){
            //用户不存在则返回用户无效的错误信息
            return new Result(1,"Invalid accounts",new Date().getTime(),null);
        }
        //如果用户存在,则进行密码验证
        if(user.getPassword().equals(loginData.getPassword())){
            //密码验证成功
            String token;
            //创建JWT 格式token
            token = Jwts.builder()
                    //Jwt中放入用户id,角色权限,部门id,用户名等信息
                    .setId(user.getUser_id())
                    .setIssuedAt(new Date())
                    .claim("status",user.getUser_status())
                    .claim("branch_id",user.getBranch_id())
                    .claim("user_name",user.getUser_name())
                    .signWith(SignatureAlgorithm.HS256, "d76ebe22e95fa9da30fb401f3074cd8dcad4efc3").compact();
            return new Result(0,"OK",new Date().getTime(),token);
        }else {
            //密码验证失败,返回密码无效错误信息
            return new Result(2,"Invalid password",new Date().getTime(),null);
        }

    }

    @Override
    @Transactional
    public Result signUp(User user) {

        Result result = StrCheck.userSignUpInfoCheck(user);
        if(result==null){
                String user_id = UUID.randomUUID().toString();
                String branch_id = UUID.randomUUID().toString();
                user.setUser_id(user_id);
                user.setBranch_id(branch_id);
                user.setUser_status(2);
                if ( userDao.insertUser(user) == 1) {
                    return new Result(0, "signUP OK", new Date().getTime(), null);
                } else {
                    return new Result(8, "Invalid signUp", new Date().getTime(), null);
                }
        }else {
            return result;
        }
    }

    @Override
    public Result userNameExists(String username) {
        String check;
        check=userDao.userNameExists(username);
        if("1".equals(check)){
            return new Result(1,"error",new Date().getTime(),null);
        }else {
            return new Result(0,"OK",new Date().getTime(),null);
        }
    }

    @Override
    public Result batchInsertUser(FileInputStream excelFileInputStream, String founderId) {
        Object users = ParseExcelUtil.importExcel(excelFileInputStream);
        if(userDao.batchInsertUser((LinkedList<User>) users,founderId)==1)
        return new Result(0,"上传成功",new Date().getTime(),null);
        else
            return new Result(1,"上传失败",new Date().getTime(),null);
    }

    @Override
    public Result queryUsersByFounderId(String founderId, int offset) {

        if(offset==1){
            userDao.queryUsersByFounderId(founderId,offset);
            return new Result(0,"OK",new Date().getTime(),new PageData(userDao.queryCountByFounderId(founderId),userDao.queryUsersByFounderId(founderId,0)));
        }else {
            return new Result(0,"OK",new Date().getTime(),userDao.queryUsersByFounderId(founderId,(offset-1)*8));
        }
    }

    @Override
    public Result addUser(User user) {
        if(userDao.singleInsertUser(user)==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error",new Date().getTime(),null);
        }
    }

    @Override
    public Result deleteUserById(String userId, String branchId, String founderId) {
        if(userDao.deleteUserById(userId,branchId,founderId)==1){
            return new Result(0,"OK",new Date().getTime(),null);
        }else {
            return new Result(1,"error",new Date().getTime(),null);
        }
    }


    public static void main(String[] args) throws SQLException {
        // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";

        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://localhost:3306/onlineExamDB?useSSL=false&characterEncoding=utf8&allowMultiQueries=true";

        // MySQL配置时的用户名
        String user = "root";

        // MySQL配置时的密码
        String password = "27849";

        String[] firstName= {"赵","钱","孙","李","周","武","郑","王"};
        String[] lastName={"一","二","三","四","五","六","七","八","九","十"};
        Connection conn = null;
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 开时时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "INSERT INTO test (id,name) VALUES ";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            assert conn != null;
            conn.setAutoCommit(false);
            // Statement st = conn.createStatement();
            // 比起st，pst会更好些
            PreparedStatement pst = conn.prepareStatement("");
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 1000; i++) {
                // 第次提交步长
                for (int j = 1; j <= 10000; j++) {
                    // 构建sql后缀
                    suffix.append("(").append("uuid()").append(",'").append(firstName[(int) (Math.random() * 8)]).append(lastName[(int) (Math.random() * 10)]).append("'").append("),");
                }
                // 构建完整sql
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行sql
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("cast : " + (end - begin) / 1000 + " ms");
    }


    @Override
    public Result queryUserExamCorrect(String branchId) {
       return new Result(0,"OK",new Date().getTime(),userDao.queryUserExamCorrect(branchId));
    }
}
