package com.limbo.util;

import com.limbo.dto.Result;
import com.limbo.entity.User;

import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * Created by limbo on 17-4-7.
 */
public class StrCheck {


    public static Result userSignUpInfoCheck(User user){

        if(user.getReal_name().length()>15||user.getReal_name().length()<2){
            return new Result(1,"姓名错误！",new Date().getTime(),null);
        }
        if(user.getUser_name().length()>30||user.getUser_name().length()<6){
            return new Result(2,"用户名错误！",new Date().getTime(),null);
        }
        String userNameRegEx = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？]";
        Pattern userNamePatten = Pattern.compile(userNameRegEx);
        if(userNamePatten.matcher(user.getUser_name()).find()){
            return new Result(4,"用户名必须为6-30个字母、数字或下划线",new Date().getTime(),null);
        }

        String fullRegEx = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？_]";

        Pattern fullPatten = Pattern.compile(fullRegEx);
        if(fullPatten.matcher(user.getReal_name()).find()){
            return new Result(5,"姓名错误！",new Date().getTime(),null);
        }
        String emailRegEx = "[`~!#$%^&*()+=|{}':;,\\[\\]<>/?！￥…（）—【】‘；：”“’。，、？_]";
        Pattern emailPatten  = Pattern.compile(emailRegEx);
        if(emailPatten.matcher(user.getEmail()).find()){
            return new Result(7,"邮箱错误！",new Date().getTime(),null);
        }

        if(user.getPassword().length()!=40){
            return new Result(8,"密码错误！",new Date().getTime(),null);
        }

        return null;
    }

}
