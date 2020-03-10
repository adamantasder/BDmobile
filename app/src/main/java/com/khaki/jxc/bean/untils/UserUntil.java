package com.khaki.jxc.bean.untils;



import com.khaki.jxc.bean.Contact;
import com.khaki.jxc.gson.GsonGroup;
import com.khaki.jxc.gson.GsonUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 嘉进 on 11:48.
 */

public class UserUntil {
    public static String name;
    public static String phone;
    public static List<Contact> friendList = new ArrayList<>();
    public static GsonUser gsonUser;
    public static List<GsonGroup> createGroupList = new ArrayList<>();
    public static List<GsonGroup> joinGroupList = new ArrayList<>();
    public static List<GsonGroup> groupList = new ArrayList<>();
}
