package com.khaki.jxc.bean;

import org.litepal.annotation.Column;
/**
 * 请购单bean
 * */
public class AppVouch {
       //主键
    private int appvouchid;
     //审核人
    private String cVerifier;
  	//制单人
    private String cMaker;
   //请购单号
    private String cCode;
  //审核状态
    private int iverifystateex;
  //审核日期
    private String cAuditDate ;
  //申请人
    private String cPersonCode ;
    //订单日期
    private String dDate;
    //请购单类别
    private String cBusType;
    private Integer IsWfControlled ;//是否启用工作流  1启用

    private Integer iflowid ;//流程ID

    private String cMakeTime;//制单时间

    private String cAuditTime;//审批时间

    private int iPrintCount;//打印次数

    private String csysbarcode;//请购单条码

    private String cDepCode;//部门号

    public Integer getIsWfControlled() {
        return IsWfControlled;
    }

    public void setIsWfControlled(Integer isWfControlled) {
        IsWfControlled = isWfControlled;
    }

    public Integer getIflowid() {
        return iflowid;
    }

    public void setIflowid(Integer iflowid) {
        this.iflowid = iflowid;
    }

    public String getcMakeTime() {
        return cMakeTime;
    }

    public void setcMakeTime(String cMakeTime) {
        this.cMakeTime = cMakeTime;
    }

    public String getcAuditTime() {
        return cAuditTime;
    }

    public void setcAuditTime(String cAuditTime) {
        this.cAuditTime = cAuditTime;
    }

    public int getiPrintCount() {
        return iPrintCount;
    }

    public void setiPrintCount(int iPrintCount) {
        this.iPrintCount = iPrintCount;
    }

    public String getCsysbarcode() {
        return csysbarcode;
    }

    public void setCsysbarcode(String csysbarcode) {
        this.csysbarcode = csysbarcode;
    }

    public String getcDepCode() {
        return cDepCode;
    }

    public void setcDepCode(String cDepCode) {
        this.cDepCode = cDepCode;
    }

    public int getAppvouchid() {
        return appvouchid;
    }

    public void setAppvouchid(int appvouchid) {
        this.appvouchid = appvouchid;
    }

    public String getcVerifier() {
        return cVerifier;
    }

    public void setcVerifier(String cVerifier) {
        this.cVerifier = cVerifier;
    }

    public String getcMaker() {
        return cMaker;
    }

    public void setcMaker(String cMaker) {
        this.cMaker = cMaker;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public int getIverifystateex() {
        return iverifystateex;
    }

    public void setIverifystateex(int iverifystateex) {
        this.iverifystateex = iverifystateex;
    }

    public String getcAuditDate() {
        return cAuditDate;
    }

    public void setcAuditDate(String cAuditDate) {
        this.cAuditDate = cAuditDate;
    }

    public String getcPersonCode() {
        return cPersonCode;
    }

    public void setcPersonCode(String cPersonCode) {
        this.cPersonCode = cPersonCode;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getcBusType() {
        return cBusType;
    }

    public void setcBusType(String cBusType) {
        this.cBusType = cBusType;
    }
}
