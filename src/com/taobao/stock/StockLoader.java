package com.taobao.stock;

import com.taobao.stock.column.BroadcastColumnEnum;
import com.taobao.stock.model.BroadcastDetailModel;
import com.taobao.stock.model.BroadcastViewModel;
import com.taobao.stock.utils.CellUtils;
import com.taobao.stock.utils.Constants;
import com.taobao.stock.utils.SLog;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by geyubin on 2018/1/15.
 */
public class StockLoader {

    public static final int HEAD_ROW_INDEX = 0;

    public FileProvider provider = FileProvider.getInstance();

    public StockWriter writer = new StockWriter();


    public Map<BroadcastColumnEnum, Integer> headCategory = new HashMap<>();   //  <column名,列序列>

    private static StockLoader loader = new StockLoader();

    {
        initEnv();
    }

    public static void main(String[] args) {

        List<File> orgFiles = loader.provider.getParseFiles();

        try {
            SLog.i("===================start==========================" );

            for (int i = 0; i < orgFiles.size(); i++) {
                loader.parseFile(orgFiles.get(i));
            }

            SLog.i("===================end==========================" );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initEnv(){
        provider.clearOutRes();
    }

    private void parseFile(File file) throws  Exception{
        FileInputStream in = new FileInputStream(file); // 文件流
        Workbook workbook = provider.getWorkbook(file, in);
        int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量

        Sheet sheet;

        for (int i = 0; i < sheetCount; i++) {
            sheet = workbook.getSheetAt(i);
            List<BroadcastViewModel> viewlModelList = parseSheet(sheet);


            if(viewlModelList.isEmpty()){
                return;
            }


            List<BroadcastDetailModel> detailModelList = new ArrayList<>();
            for (BroadcastViewModel viewModel: viewlModelList){
                detailModelList.addAll(viewModel.applanatio());
            }


            int  quantity = 0;
            for(BroadcastDetailModel vm: detailModelList){
                quantity += vm.quantity;
            }



            SLog.i("===" + file.getName() + "====商品总数: " + detailModelList.size());
            SLog.i("===" + file.getName() + "====库存总数: " + quantity);


            int size = (int) Math.ceil((double)detailModelList.size()/Constants.MAX_SHEET_DETAIL_SIZE);

            int startIndex = 0;
            int endIndex = 0;
            String spFileName = "";
            List<BroadcastDetailModel> subList;
            for (int j = 0; j < size ; j++) {   //分表拆分写入,按照最大的长度限制

                startIndex = Constants.MAX_SHEET_DETAIL_SIZE*j;
                endIndex = (j + 1)*Constants.MAX_SHEET_DETAIL_SIZE;

                if(endIndex > detailModelList.size()){
                    endIndex = detailModelList.size();
                }

                spFileName = sheet.getSheetName()+ "_" + j + "_" + file.getName();

                subList = detailModelList.subList(startIndex, endIndex);
                writer.write(subList, spFileName);
                SLog.i("===" + spFileName + "====size: " + subList.size());
            }

        }

        SLog.i("--------------------------------------------------------" );


        in.close();
    }

    private List<BroadcastViewModel> parseSheet(Sheet sheet){

        List<BroadcastViewModel>  viewlModelList = new ArrayList<>();

        BroadcastViewModel deailModel;
        for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
            deailModel = parseRow(sheet.getRow(j), j == HEAD_ROW_INDEX ? true: false);

            if(deailModel.isValid()){
                viewlModelList.add(deailModel);
            }
        }

        return viewlModelList;

    }


    private BroadcastViewModel parseRow(Row row, boolean isHead){

        Cell cell;
        BroadcastViewModel detailModel = new BroadcastViewModel();

        for (int j = 0; j < row.getLastCellNum() + 1; j++) {
            cell = row.getCell(j);

            if(cell == null){
                continue;
            }

            if(isHead){  //初始化头部行
               initColumnHead(cell, j);
               continue;
            }

            if(j == headCategory.get(BroadcastColumnEnum.UNICODE) && !isValidUnicodeCell(cell)){
                //非商品内容行
                SLog.w("=======has illegal row  ===> unicode is  " + cell);
                break;
            }


            Injector.inject(getColumnEnum(j), detailModel,  CellUtils.getValue(cell));

        }

        return detailModel;

    }

    private BroadcastColumnEnum getColumnEnum(int index){

        Set<BroadcastColumnEnum> keys = headCategory.keySet();

        BroadcastColumnEnum  columnEnum = null;
        for (BroadcastColumnEnum key: keys){
            if(headCategory.get(key) == index){
                columnEnum = key;
                break;
            }
        }

        return columnEnum;
    }

    private boolean isValidUnicodeCell(Cell cell){

        String value = CellUtils.getStringValue(cell);

        if(value == null || value.trim().length() == 0){
            return false;
        }

        String reg = "(?i)^(?!([a-z]*|\\d*)$)[a-z\\d]+$";
        return value.matches(reg);
    }

    private void initColumnHead(Cell cell, int index){
        BroadcastColumnEnum head;
        if(cell.getCellTypeEnum() == CellType.STRING){
            head = BroadcastColumnEnum.getColumnHead(cell.getStringCellValue());
            if(head != null){
                headCategory.put(head, index);
            }
        }
    }


}
