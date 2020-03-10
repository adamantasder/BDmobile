package com.khaki.jxc.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 嘉进 on 20:15.
 */

public class GsonNotices {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("announcementList")
    @Expose
    private List<GsonNotice> announcementList = null;
    @SerializedName("isNext")
    @Expose
    private Boolean isNext;
    @SerializedName("startPos")
    @Expose
    private Integer startPos;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GsonNotice> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<GsonNotice> announcementList) {
        this.announcementList = announcementList;
    }

    public Boolean getIsNext() {
        return isNext;
    }

    public void setIsNext(Boolean isNext) {
        this.isNext = isNext;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }
}
