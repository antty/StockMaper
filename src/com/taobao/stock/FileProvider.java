package com.taobao.stock;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyubin on 2018/1/15.
 */
public class FileProvider {


    private static final String EXCEL_XLSX = "xlsx";
    private static final String EXCEL_XLS = "xls";


    public static final String PATH_RES = "res/";
    public static final String PATH_OUT_RES = "res_out/";


    public static final String PATH_TEMPLATE_RES = "res_template/";
    public static final String PATH_USELESS_RES = "res_temp/";


    public static final String TEMPLATE_FILE= "skusImportTemplate.xlsx";


    public static FileProvider provider = new FileProvider();

    public static FileProvider getInstance(){
        return provider;
    }


    public  List<File> getParseFiles(){

        File file = new File(PATH_RES);
        // get the folder list
        File[] array = file.listFiles();

        List<File> xlsxFiles = new ArrayList<>();
        for (int i = 0; i < array.length ; i++) {

            if(isXLSXFile(array[i])){
                xlsxFiles.add(array[i]);
            }
        }

        return xlsxFiles;
    }

    /**
     * 判断文件是否是excel
     * @throws Exception
     */
    public  boolean isXLSXFile(File file){
        if(!file.exists()){
            return false;
        }
        if(!(file.isFile() && isParseFileName(file.getName()))
                || file.getName().startsWith("~")){
            return false;
        }

        return true;
    }

    public String getOutFilePath(String fileName){
        return PATH_OUT_RES + "out_" + fileName;
    }

    private boolean isParseFileName(String fileName){

        if(fileName == null){
            return false;
        }

        if(fileName.endsWith(EXCEL_XLS)
                || fileName.endsWith(EXCEL_XLSX)){
            return true;
        }

        return false;
    }




    /**
     * 复制整个文件夹内容
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public void copyTemplateFolder(String newPath) {

        String  oldPath = PATH_TEMPLATE_RES + TEMPLATE_FILE;

        try {
            File a=new File(oldPath);

            FileInputStream input = new FileInputStream(a);
            FileOutputStream output = new FileOutputStream(newPath);
            byte[] b = new byte[1024 * 5];
            int len;
            while ( (len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }


    public void clearOutRes(){
        deleteDir(new File(PATH_OUT_RES));
    }

    private static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }else{
             dir.delete();
        }
    }



    public Workbook  getWorkbook(File file, FileInputStream in) throws Exception {
        try {
            if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003
                return new HSSFWorkbook(in);
            }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
                return new XSSFWorkbook(in);
            }
        } catch (Exception e) {
            return WorkbookFactory.create(file);
        }

        return null;
    }

    public Workbook  getWorkbook(File file) throws IOException {
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003
            return new HSSFWorkbook();
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            return new XSSFWorkbook();
        }

        return null;
    }

}
