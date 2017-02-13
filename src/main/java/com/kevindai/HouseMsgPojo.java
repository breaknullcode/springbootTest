package com.kevindai;

/**
 * 爬虫爬出的房屋信息对应的实体类
 * Created by kevindai on 2017/2/13.
 */
public class HouseMsgPojo {
    private String location;//位置
    private String totalMoney;//总价
    private String unitPrice;//单价
    private String layout;//布局
    private String size;//大小

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
