package com.taobao.stock;

import com.taobao.stock.column.BroadcastColumnEnum;
import com.taobao.stock.model.BroadcastViewModel;

import static com.taobao.stock.column.BroadcastColumnEnum.*;

/**
 * Created by geyubin on 2018/1/16.
 */
public class Injector {

    //播的实体注册
    public static void inject(BroadcastColumnEnum columnEnum, BroadcastViewModel model, Object value){

//        if(model == null
//                || columnEnum == null){
//            return;
//        }

        if(columnEnum == UNICODE){
            model.setUnicode((String) value);
        }else if(columnEnum == NAME){
            model.setName((String)value);
        }else if(columnEnum == COLOR){
            model.setColor((String)value);
        }else if(columnEnum == ORG_PRICE){
            model.setOrgPrice((Integer)value);
        }else if(columnEnum == SIZE_XS
                || columnEnum == SIZE_S
                || columnEnum == SIZE_M
                || columnEnum == SIZE_L
                || columnEnum == SIZE_XL
                || columnEnum == SIZE_XXL
                || columnEnum == SIZE_XXXL){

            try {
                if(value instanceof Integer){
                    model.addFootageSku(columnEnum.getName(), (Integer) value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
