package com.taobao.stock.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Created by geyubin on 2018/1/16.
 */
public class CellUtils {

    public static  String getStringValue(Cell cell){

        if(cell.getCellTypeEnum() == CellType.STRING){
             return cell.getStringCellValue();
        }

        return "";
    }

    public static Object getValue(Cell cell){

        if(cell.getCellTypeEnum() == CellType.STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellTypeEnum() == CellType.NUMERIC){
            return (int)cell.getNumericCellValue();
        }

        return new Object();

    }

}
