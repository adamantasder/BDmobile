package com.khaki.jxc.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 存货档案
 * <p>
 */

public class Inventory {
    private String cInvCode; // 存货编码 key

    private String cInvName;// 存货名称

    private String cInvStd;// 规格型号

    private String cInvCCode;// 存货类别

    private int cGroupCode; // 单位组

    private int cComUnitCode;// 单位代码

    private Float  iQuantity;  //现存数量

    private Float currentIQ; //库存数量

    public Float getCurrentIQ() {
        return currentIQ;
    }

    public void setCurrentIQ(Float currentIQ) {
        this.currentIQ = currentIQ;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getcInvName() {
        return cInvName;
    }

    public void setcInvName(String cInvName) {
        this.cInvName = cInvName;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public void setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
    }

    public String getcInvCCode() {
        return cInvCCode;
    }

    public void setcInvCCode(String cInvCCode) {
        this.cInvCCode = cInvCCode;
    }

    public int getcGroupCode() {
        return cGroupCode;
    }

    public void setcGroupCode(int cGroupCode) {
        this.cGroupCode = cGroupCode;
    }

    public int getcComUnitCode() {
        return cComUnitCode;
    }

    public void setcComUnitCode(int cComUnitCode) {
        this.cComUnitCode = cComUnitCode;
    }

    public Float getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(Float iQuantity) {
        this.iQuantity = iQuantity;
    }
}
