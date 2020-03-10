package com.khaki.jxc.bean;

public class PurOrdersVo {
    private int id;      //采购订单子表标识

    private String cPOID;	//请购单号

    private String cInvCode; //存货编码

    private float  iQuantity;  //数量

    private int POID;	//采购订单主表标识

    private String iAppIds; //采购请购单子表标识

    private String    cSource;   //单据来源

    private String cupsocode ;//来源单据号

    private int ivouchrowno; //行号

    private String cbsysbarcode; //行条码

    private String cInvName;//货物名称

    private String cInvStd;// 规格型号

    private String cInvCCode;// 存货类别

    public String getcInvCCode() {
        return cInvCCode;
    }

    public void setcInvCCode(String cInvCCode) {
        this.cInvCCode = cInvCCode;
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

    public PurOrdersVo(String s, String s1) {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcPOID() {
        return cPOID;
    }

    public void setcPOID(String cPOID) {
        this.cPOID = cPOID;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public float getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(float iQuantity) {
        this.iQuantity = iQuantity;
    }

    public int getPOID() {
        return POID;
    }

    public void setPOID(int POID) {
        this.POID = POID;
    }

    public String getiAppIds() {
        return iAppIds;
    }

    public void setiAppIds(String iAppIds) {
        this.iAppIds = iAppIds;
    }

    public String getcSource() {
        return cSource;
    }

    public void setcSource(String cSource) {
        this.cSource = cSource;
    }

    public String getCupsocode() {
        return cupsocode;
    }

    public void setCupsocode(String cupsocode) {
        this.cupsocode = cupsocode;
    }

    public int getIvouchrowno() {
        return ivouchrowno;
    }

    public void setIvouchrowno(int ivouchrowno) {
        this.ivouchrowno = ivouchrowno;
    }

    public String getCbsysbarcode() {
        return cbsysbarcode;
    }

    public void setCbsysbarcode(String cbsysbarcode) { this.cbsysbarcode = cbsysbarcode; }


/*    public PurOrdersVo(int id, String cPOID, CharSequence iQuantity) {
        this.id = id;
        this.cPOID = cPOID;
        this.iQuantity = iQuantity;

    }*/
}
