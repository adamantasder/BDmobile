package com.khaki.jxc.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaki on 2019-07-29.
 */

public class GsonUser {
    @SerializedName("userHeadPortrait")
    @Expose
    private Object userHeadPortrait;

    public Object getUserHeadPortrait() {
        return userHeadPortrait;
    }

    public void setUserHeadPortrait(Object userHeadPortrait) {
        this.userHeadPortrait = userHeadPortrait;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("pswd")
    @Expose
    private String pswd;

    //状态
    @SerializedName("status")
    @Expose
    private Integer status;
    //部门

    @SerializedName("department")
    @Expose
    private Integer department;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
