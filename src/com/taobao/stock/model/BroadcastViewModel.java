package com.taobao.stock.model;

import com.taobao.stock.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyubin on 2018/1/16.
 */
public class BroadcastViewModel {

    public String unicode;

    public String name;

    public String color;

    //吊牌价
    public int orgPrice;

    //采购价
    public int purPrice;

    //售卖价
    public int sellPrice;

    public List<BroadcastViewModel.FootageSku> footageSkus = new ArrayList<>();

    public static class FootageSku{

        public String footage;
        public int  quantity;
    }


    /**
     * 拉平实体
     * @return
     */
    public List<BroadcastDetailModel> applanatio(){

        List<BroadcastDetailModel> models = new ArrayList<>();

        BroadcastDetailModel model;
        for(BroadcastViewModel.FootageSku footageSku: footageSkus){
            model = new BroadcastDetailModel();
            model.unicode = this.unicode;
            model.name = this.name;
            model.color = this.color;
            model.orgPrice = this.orgPrice;
            model.purPrice = this.purPrice;
            model.sellPrice = this.sellPrice;

            model.footage = footageSku.footage;
            model.quantity = footageSku.quantity;

            models.add(model);
        }

        return models;
    }


    /**
     * 检测该实体是否有效
     * @return
     */
    public boolean isValid(){
        return  !(unicode == null || unicode.length() == 0);
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getOrgPrice() {
        return orgPrice;
    }

    /**
     * 设置价格
     * @param orgPrice   吊牌价
     */
    public void setOrgPrice(int orgPrice) {
        this.orgPrice = orgPrice;
        this.purPrice = (int) Math.ceil(orgPrice* Constants.PURPRICE_RATE);
        this.sellPrice = (int) Math.ceil(orgPrice*Constants.SELLPRICE_RATE);
    }

    public List<BroadcastViewModel.FootageSku> getFootageSkus() {
        return footageSkus;
    }

    /**
     * 设置尺码sku值
     */
    public void addFootageSku(String footage, int quantity) {
        BroadcastViewModel.FootageSku skuItem = new BroadcastViewModel.FootageSku();
        skuItem.footage = footage;
        skuItem.quantity = quantity;
        this.footageSkus.add(skuItem);
    }
}
