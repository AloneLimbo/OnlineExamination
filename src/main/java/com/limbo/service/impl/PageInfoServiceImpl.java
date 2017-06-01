package com.limbo.service.impl;

import com.limbo.dao.FunctionDao;
import com.limbo.dto.Result;
import com.limbo.entity.Function;
import com.limbo.service.PageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by limbo on 17-4-7.
 */
@Service
public class PageInfoServiceImpl implements PageInfoService {

    private final FunctionDao functionDao;

    @Autowired
    public PageInfoServiceImpl(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }

    @Override
    public Result getFunctions(int status) {

        List<Function> functions = functionDao.queryFunctionByStatus(status);
        if(functions.size()==0){
            return new Result(1,"Invalid status",new Date().getTime(),null);
        }
        return new Result(0,"Ok",new Date().getTime(),functions);
    }
}
