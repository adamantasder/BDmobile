package com.khaki.jxc.bean;



public class PurchaseTypeVo {

    private int cPTCode ;      //主键 编码


    private String cPTName ; //名称


    private String cRdCode  ; //入库类别编码


    private Integer bDefault   ; //是否默认

    public int getcPTCode() {
        return cPTCode;
    }

    public void setcPTCode(int cPTCode) {
        this.cPTCode = cPTCode;
    }

    public String getcPTName() {
        return cPTName;
    }

    public void setcPTName(String cPTName) {
        this.cPTName = cPTName;
    }

    public String getcRdCode() {
        return cRdCode;
    }

    public void setcRdCode(String cRdCode) {
        this.cRdCode = cRdCode;
    }

    public Integer getbDefault() {
        return bDefault;
    }

    public void setbDefault(Integer bDefault) {
        this.bDefault = bDefault;
    }
}
