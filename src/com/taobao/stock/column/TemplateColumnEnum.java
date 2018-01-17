package com.taobao.stock.column;

/**
 * 模板列描述enum
 * Created by geyubin on 2018/1/17.
 */
public enum TemplateColumnEnum {

    UNICODE("货号"),
    NAME("名称"),
    PUR_PRICE("采购价"),
    SELL_PRICE("批发"),
    ORG_PRICE("零售价"),
    FAC_PRICE("厂家建议零售价"),
    COLOR("颜色"),
    FOOTAGE("尺码"),
    QUANTITY("盘点库存数量");

    private String name;

    TemplateColumnEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static TemplateColumnEnum getColumnHead(String name){

        TemplateColumnEnum head = null;


        if(name == null
                || name.length() == 0){
            return null;
        }

        TemplateColumnEnum[] heads = TemplateColumnEnum.values();
        for (int i = 0; i < heads.length; i++) {
            if(name.startsWith(heads[i].getName())){
                head = heads[i];
                break;
            }
        }
        return head;
    }
}
