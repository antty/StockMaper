package com.taobao.stock.model;

/**
 * 播的商品映射model
 *
 * Created by geyubin on 2018/1/16.
 */
public class BroadcastDetailModel {

   public String unicode;

    public String name;

    public String color;

    //吊牌价
    public int orgPrice;

    //采购价
    public int purPrice;

    //售卖价
    public int sellPrice;

    //尺码
    public String footage;

    //库存
    public int quantity;


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


    public void setOrgPrice(int orgPrice) {
        this.orgPrice = orgPrice;
    }

    public int getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(int purPrice) {
        this.purPrice = purPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getFootage() {
        return footage;
    }

    public void setFootage(String footage) {
        this.footage = footage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
