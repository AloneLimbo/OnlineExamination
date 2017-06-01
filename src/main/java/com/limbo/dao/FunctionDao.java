package com.limbo.dao;

import com.limbo.entity.Function;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 功能表数据操作
 * Created by limbo on 17-4-7.
 */
@Mapper
public interface FunctionDao {


    @Select("SELECT function_id,function_name,function_icon,function_url," +
            "is_parent,pid FROM function where status=#{status} ORDER BY function_name DESC")
    List<Function> queryFunctionByStatus(@Param("status") int status);



}
