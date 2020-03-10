package com.khaki.jxc.bean;




public class WarehouseOut {


    private int ID;	//key主表标识bigint

    private int bRdFlag;	//收发标识tinyint

    private String cVouchType;	//编码规则nvarchar

    private String cSource	;//单据来源nvarchar

    private String cBusType;	//出库类别

    private String cWhCode	;//库房编码nvarchar

    private String dDate;	//单据时间datetime

    private String cCode;	//单据号码nvarchar

    private String cHandler;	//审核人nvarchar

    private int bTransFlag;	//是否传递bit

    private String cMaker;	//制单人nvarchar

    private int bIsSTQc;	//库存期初标志bit

    private String dnmaketime	;//制单时间datetime

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getbRdFlag() {
        return bRdFlag;
    }

    public void setbRdFlag(int bRdFlag) {
        this.bRdFlag = bRdFlag;
    }

    public String getcVouchType() {
        return cVouchType;
    }

    public void setcVouchType(String cVouchType) {
        this.cVouchType = cVouchType;
    }

    public String getcSource() {
        return cSource;
    }

    public void setcSource(String cSource) {
        this.cSource = cSource;
    }

    public String getcBusType() {
        return cBusType;
    }

    public void setcBusType(String cBusType) {
        this.cBusType = cBusType;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public void setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcHandler() {
        return cHandler;
    }

    public void setcHandler(String cHandler) {
        this.cHandler = cHandler;
    }

    public int getbTransFlag() {
        return bTransFlag;
    }

    public void setbTransFlag(int bTransFlag) {
        this.bTransFlag = bTransFlag;
    }

    public String getcMaker() {
        return cMaker;
    }

    public void setcMaker(String cMaker) {
        this.cMaker = cMaker;
    }

    public int getbIsSTQc() {
        return bIsSTQc;
    }

    public void setbIsSTQc(int bIsSTQc) {
        this.bIsSTQc = bIsSTQc;
    }

    public String getDnmaketime() {
        return dnmaketime;
    }

    public void setDnmaketime(String dnmaketime) {
        this.dnmaketime = dnmaketime;
    }
}
