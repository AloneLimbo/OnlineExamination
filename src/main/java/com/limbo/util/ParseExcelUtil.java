package com.limbo.util;

import com.limbo.dto.Result;
import com.limbo.entity.User;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

/**
 * 解析excel文件
 * Created by limbo on 17-5-3.
 */
public class ParseExcelUtil {

    public static Object importExcel(FileInputStream excelInputStream){

        try {
            Workbook workbook= Workbook.getWorkbook(excelInputStream);

            //获取第一张Sheet表

            Sheet readsheet = workbook.getSheet(0);

            //获取Sheet表中所包含的总列数

            int rsColumns = readsheet.getColumns();


            //获取Sheet表中所包含的总行数

            int rsRows = readsheet.getRows();

            if(rsColumns!=5){
                return new Result(1,"Excel 格式错误！",new Date().getTime(),null);
            }
            LinkedList<User> users = new LinkedList<>();
            Cell[] cell;
            for(int i=1;i<rsRows;i++){
                cell=readsheet.getRow(i);
                users.add(new User(cell));
            }
            return users;
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return null;
    }

}
