package com.taobao.stock;

import com.taobao.stock.column.TemplateColumnEnum;
import com.taobao.stock.model.BroadcastDetailModel;
import com.taobao.stock.utils.Constants;
import com.taobao.stock.utils.SLog;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.taobao.stock.column.TemplateColumnEnum.*;

/**
 * Created by geyubin on 2018/1/16.
 */
public class StockWriter {


    public Map<TemplateColumnEnum, Integer> tempCategory = new HashMap<>();   //  <column名,列序列>


    public void  write(List<BroadcastDetailModel> detailModelList, String fileName) throws Exception {

        String tempPath = FileProvider.getInstance().getOutFilePath(Constants.PRE_TEMP + fileName);

        File finalXlsxFile = new File(tempPath);
        FileProvider.getInstance().copyTemplateFolder(tempPath);

        FileInputStream in = new FileInputStream(finalXlsxFile); // 文件流
        Workbook workBook =   FileProvider.getInstance().getWorkbook(finalXlsxFile, in);
        Sheet sheet = workBook.getSheet(Constants.TEMPLATE_SHEET_NAME);

        if(sheet == null){
            SLog.w("has no template sheet ");
            return;
        }


        if(detailModelList.size() > sheet.getLastRowNum()){
            SLog.w("============detailModelList   size error ");
            return;
        }else{
            initColumnTitle(sheet.getRow(0));
        }

        Row row;
        BroadcastDetailModel detailModel;
        for (int i = 0; i <  detailModelList.size(); i++) {

            detailModel = detailModelList.get(i);
            row = sheet.getRow(i + 1); //去除顶部列名行


            Set<TemplateColumnEnum> keySet = tempCategory.keySet();
            for (TemplateColumnEnum tpColumn: keySet){

                if(tpColumn == UNICODE){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getUnicode());
                }else if (tpColumn == NAME){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getName());
                }else if(tpColumn == PUR_PRICE){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getPurPrice());
                }else if(tpColumn == SELL_PRICE){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getSellPrice());
                }else if(tpColumn == ORG_PRICE || tpColumn == FAC_PRICE){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getOrgPrice());
                }else if(tpColumn == COLOR){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getColor());
                }else if(tpColumn == FOOTAGE){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getFootage());
                }else if(tpColumn == QUANTITY){
                    row.createCell(tempCategory.get(tpColumn)).setCellValue(detailModel.getQuantity());
                }
            }
        }

        OutputStream outputStream = new FileOutputStream( FileProvider.getInstance().getOutFilePath(fileName));
        workBook.write(outputStream);


        outputStream.flush();
        outputStream.close();

        in.close();
        finalXlsxFile.delete();
    }


    private void  initColumnTitle(Row row){

        Cell cell;
        TemplateColumnEnum head;
        for (int j = 0; j < row.getLastCellNum() + 1; j++) {
            cell = row.getCell(j);

            if(cell == null){
                continue;
            }

            if(cell.getCellTypeEnum() == CellType.STRING){
                head = getColumnHead(cell.getStringCellValue());
                if(head != null){
                    tempCategory.put(head, j);
                }
            }
        }
    }


    public static void main(String[] args) {
    }

        /**
         * 原始文件输出
         * @param detailModelList
         * @param finalXlsxPath
         * @throws Exception
         */
    private void writeOrgDetailModels(List<BroadcastDetailModel> detailModelList, String finalXlsxPath) throws Exception{
        File finalXlsxFile = new File(finalXlsxPath);

        Workbook workBook = FileProvider.getInstance().getWorkbook(finalXlsxFile);
        OutputStream outputStream = new FileOutputStream(finalXlsxPath);
        workBook.createSheet("all_sheet1");


        try {
            Sheet sheet = workBook.getSheetAt(0);
            Row row;
            for (int j = 0; j < detailModelList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                row = sheet.createRow(j + 1);
                row.createCell(0).setCellValue(detailModelList.get(j).getUnicode());  //货品码
                row.createCell(1).setCellValue(detailModelList.get(j).getName());  //商品名称
                row.createCell(2).setCellValue(detailModelList.get(j).getPurPrice());  //采购价
                row.createCell(3).setCellValue(detailModelList.get(j).getSellPrice());  //销售价
                row.createCell(4).setCellValue(detailModelList.get(j).getOrgPrice());  //吊牌价
                row.createCell(5).setCellValue(detailModelList.get(j).getColor());  //颜色
                row.createCell(6).setCellValue(detailModelList.get(j).getQuantity());  //库存数
                row.createCell(7).setCellValue(detailModelList.get(j).getFootage());   // 尺码
            }

            workBook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }

    }

}
