package com.taobao.stock.column;

/**
 * Created by geyubin on 2018/1/15.
 */
public enum BroadcastColumnEnum{

    UNICODE("款号"),
    NAME("款名"),
    COLOR("颜色"),
    ORG_PRICE("吊牌价"),

    SELL_PRICE("销售价"),
    PUR_PRICE("采购价"),

    //尺码类别
    SIZE_XS("XS"),
    SIZE_S("S"),
    SIZE_M("M"),
    SIZE_L("L"),
    SIZE_XL("XL"),
    SIZE_XXL("XXL"),
    SIZE_XXXL("XXXL");

    private String name;

    BroadcastColumnEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static BroadcastColumnEnum getColumnHead(String name){

        BroadcastColumnEnum head = null;

        BroadcastColumnEnum[] heads = BroadcastColumnEnum.values();
        for (int i = 0; i < heads.length; i++) {
            if(heads[i].getName().equalsIgnoreCase(name)){
                head = heads[i];
                break;
            }
        }
        return head;
    }
}
