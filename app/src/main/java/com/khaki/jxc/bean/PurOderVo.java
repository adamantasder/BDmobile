package com.khaki.jxc.bean;


public class PurOderVo {
    private int POID;      //采购订单主表标识

    private String cappcode;	//请购单号

    private String cPOID;		//采购单号

    private int inverifystate;  //审核状态 （-1，终审不同意，0，未提交，1已提交，2，终审同意）

    private  String dep;		//申请部门号

    private String cDepName; //部门名称

    private Integer iflowid;		//流程id

    private String cPersonCode;	//业务员编码

    private String cMaker; 		//制单人

    private String cVerifier ;  //审核人

    private String dPODate; 		//订单日期

    private String cBusType;	//采购类型

    private String cLcocker; 	//锁定人

    private String csysbarcode ;//单据条码

    private int iPrintCount; //打印次数

    private String warehouse; //入库厂库

    private String cWhName;//库房名称

    private String username;//申请人名称

    public String getcDepName() {
        return cDepName;
    }

    public void setcDepName(String cDepName) {
        this.cDepName = cDepName;
    }

    public String getcWhName() {
        return cWhName;
    }

    public void setcWhName(String cWhName) {
        this.cWhName = cWhName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPOID() {
        return POID;
    }

    public void setPOID(int POID) {
        this.POID = POID;
    }

    public String getCappcode() {
        return cappcode;
    }

    public void setCappcode(String cappcode) {
        this.cappcode = cappcode;
    }

    public String getcPOID() {
        return cPOID;
    }

    public void setcPOID(String cPOID) {
        this.cPOID = cPOID;
    }

    public int getInverifystate() {
        return inverifystate;
    }

    public void setInverifystate(int inverifystate) {
        this.inverifystate = inverifystate;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public Integer getIflowid() {
        return iflowid;
    }

    public void setIflowid(Integer iflowid) {
        this.iflowid = iflowid;
    }

    public String getcPersonCode() {
        return cPersonCode;
    }

    public void setcPersonCode(String cPersonCode) {
        this.cPersonCode = cPersonCode;
    }

    public String getcMaker() {
        return cMaker;
    }

    public void setcMaker(String cMaker) {
        this.cMaker = cMaker;
    }

    public String getcVerifier() {
        return cVerifier;
    }

    public void setcVerifier(String cVerifier) {
        this.cVerifier = cVerifier;
    }

    public String getdPODate() {
        return dPODate;
    }

    public void setdPODate(String dPODate) {
        this.dPODate = dPODate;
    }

    public String getcBusType() {
        return cBusType;
    }

    public void setcBusType(String cBusType) {
        this.cBusType = cBusType;
    }

    public String getcLcocker() {
        return cLcocker;
    }

    public void setcLcocker(String cLcocker) {
        this.cLcocker = cLcocker;
    }

    public String getCsysbarcode() {
        return csysbarcode;
    }

    public void setCsysbarcode(String csysbarcode) {
        this.csysbarcode = csysbarcode;
    }

    public int getiPrintCount() {
        return iPrintCount;
    }

    public void setiPrintCount(int iPrintCount) {
        this.iPrintCount = iPrintCount;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

}
