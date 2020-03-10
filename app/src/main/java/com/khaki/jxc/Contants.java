package com.khaki.jxc;

import android.os.Environment;

import com.khaki.jxc.client.R;

/**
 * 服务器地址、各种请求的 URL
 * layout使用的字段就封装在string中
 */

public class Contants {
    public static final String SERVER_IP = "http://223.100.49.159:333/BDSOFT/api/";
    public static final String PHOTO_SERVER_IP = "";
    public static final String APP_IP = "http://223.100.49.159:9010/tender/BD.apk";
//    public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + MyApplication.getInstance().getPackageName();
    public final static String DOWNLOAD_DIR = "BDDownlaod";
    public final static String AppName = "宝得客户端";
    public static final String getInfo="/group";
    public static final String file_Server = "/fileServer";
    public static final String OfficeManage = "/OfficeManage";
    public static final String getDir = "/getDir.shtml";
    public static final String getGroupDir = "/getGroupDir.shtml";
    public static final String fileUpload = "/fileUpload.shtml";
    public static final String uploadGroupFile = "/uploadGroupFile.shtml";
    public static final String filedownload = "/download.shtml";
    public static final String downLoadGroupFile = "/downLoadGroupFile.shtml";
    public static final String createFile = "/CreateFile.shtml";
    public static final String createDir = "/CreateDir.shtml";
    public static final String moveFile = "/MoveFile.shtml";
    public static final String fileRename = "/fileRename.shtml";
    public static final String DeleteFile = "/DeleteFile.shtml";
    public static final String deleteGroupFile = "/deleteGroupFile.shtml";
    public static final String DeleteDir = "/DeleteDir.shtml";
    public static final String createGroup="/createGroup.shtml";
    public static final String joinGroup="/joinGroup.shtml";

    public static final String getGroupInfo="/getGroupInfo.shtml";
    public static final String room_id="@conference.192.168.13.48";


    public static final String createOfficeThing = "/createOfficeThing.shtml";
    public static final String OMUploadImage = "/uploadOfficeManageImage.shtml";
    public static final String OMaddOmBoss = "/addOmBoss.shtml";
    public static final String queryOfficeThing = "/queryOfficeThing.shtml";
    public static final String queryOmBoss = "/queryOmBoss.shtml";
    public static final String queryOmImages = "/queryOmImages.shtml";
    public static final String checkApproval = "/checkApproval.shtml";



    // 登录保存字段
    public static final String SP_LOGIN_USER_ID_KEY = "sp_login_user_phone_key";
    public static final String SP_LOGIN_PASSWORD_KEY = "sp_login_password_key";
    public static final String REMEMBER_PWD_PREF = "rememberPwd";
    public static final String ACCOUNT_PREF = "account";
    public static final String PASSWORD_PREF = "password";
    public static final String USERNAME = "USERNAME";
    public static final String DEPARTMENT = "dep";
    public static final String multi_invite="invite";
    public static final String creat_multi_chat="creat";
    public static final String multi_invite_room_name="room";

    public static final int REFLASH_NOTICE_MORE = 11;
    public static final int REFLASH_NOTICE_FIRST = 12;
    public static final int LOGIN_IN_GET_DATA = 13;
    public static final int SIGN_UP_GET_DATA = 14;
    public static final int OPEN_DAY_REPORTY = 15;
    public static final int OPEN_WEEK_REPORT = 16;
    public static final int OPEN_MONTH_REPORT = 17;
    public static final int SELECT_MEMBER_REPORT = 18;

    public static final int START_ACTIVIRY_SELECT_MEMBER_FOR_RESULT = 19;
    public static final int ACTIVIRY_SELECT_MEMBER_RETURN_RESULT = 20;
    public static final String APK_PATH_ABSOULT = "";
    public static final String APK_PATH = "";

    /**
     * Create by edwincheng in 2017/7/28.
     * <p>
     * 用于"文件管理"部分的字段
     */
    public static class FILEMANAGER {
        public static final int EMPTYVIEW = -1;
        public static final int ITEM_VIEW = 1;
        public static final int TAIL_VIEW = 2;

        public static final int FILE_TYPE = 10000;
        public static final int FOLDER_TYPE = 10001;

        //14个接口 28个handler 状态的字段
        public static final int GETDIR_SUCCESS = 101;
        public static final int GETDIR_FAILURE = 102;
        public static final int FILEUPLOAD_SUCCESS = 103;
        public static final int FILEUPLOAD_FAILURE = 104;
        public static final int FILEDOWNLOAD_ING = 105;
        public static final int FILEDOWNLOAD_SUCCESS = 106;
        public static final int FILEDOWNLOAD_FAILURE = 107;
        public static final int CREATE_SUCCESS = 108;
        public static final int CREATE_FAILURE = 109;
        public static final int MOVEFILE_SUCCESS = 110;
        public static final int MOVEFILE_FAILURE = 111;
        public static final int FILERENAME_SUCCESS = 112;
        public static final int FILERENAME_FAILURE = 113;
        public static final int DELETE_SUCCESS = 114;
        public static final int DELETE_FAILURE = 115;
    }

    /**
     * requestCode
     */
    public static class RequestCode {

        public static final int RENAME = 801;

        public static final int CREATE = 802;

        public static final int UPLOAD = 803;

        public static final int QRSCAN = 804;

        public static final int OPEN_SYSTEM_ALBUM = 805;

        public static final int MOVE = 806;
        public static final int QRSCANO = 807;
    }

    public static class FIELDWORK{
        public static final int OPEN_LEAVE = 121;
        public static final int OPEN_GO_OUT = 122;
        public static final int OPEN_TRAVEL = 123;
        public static final int OPEN_OVERTIME = 124;
        public static final int OPEN_VOUCHSCANO = 124;
        public static final int CREATEOM_SUCCESS = 131;
        public static final int CREATEOM_FAILURE = 132;
        public static final int OMUPLOADIMAGE_SUCCESS = 133;
        public static final int OMUPLOADIMAGE_FAILURE = 134;
        public static final int OMADDBOSS_SUCCESS = 135;
        public static final int OMADDBOSS_FAILURE = 136;
        public static final int QUERYOM_SUCCESS = 137;
        public static final int QUERYOM_FAILURE = 138;
        public static final int QUEBOSS_SUCCESS = 139;
        public static final int QUERYBOSS_FAILURE = 140;
        public static final int QUEIMAGE_SUCCESS = 141;
        public static final int QUERYIMAGE_FAILURE = 142;
        public static final int CHECKAPPROVAL_SUCCESS = 143;
        public static final int CHECKAPPROVAL_FAILURE = 144;

        public static final int  OPEN_SCANIN=1145;
        public static final int  OPEN_SCANOUT=146;
    }



    public static final String IMAGE_ICON_URL = "IMAGE_ICON_URL";
    public static final String FILEPATH = Environment.getExternalStorageDirectory().getPath()+"/BDSOFT";
}
