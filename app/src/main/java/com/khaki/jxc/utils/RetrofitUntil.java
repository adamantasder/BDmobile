package com.khaki.jxc.utils;

import android.util.Log;


import com.khaki.jxc.Contants;
import com.khaki.jxc.bean.Contact;
import com.khaki.jxc.event.LoginFinishEvent;
import com.khaki.jxc.event.SignOfficeEvent;
import com.khaki.jxc.event.SignUpFinishEvent;
import com.khaki.jxc.gson.GsonBase;
import com.khaki.jxc.gson.GsonFriend;
import com.khaki.jxc.gson.GsonFriends;
import com.khaki.jxc.gson.GsonGroup;
import com.khaki.jxc.gson.GsonGroups;
import com.khaki.jxc.gson.GsonUsers;
import com.khaki.jxc.retrofit.IFriendBiz;
import com.khaki.jxc.retrofit.IGroupBiz;
import com.khaki.jxc.retrofit.ILoginBiz;
import com.khaki.jxc.retrofit.ISignOffice;
import com.khaki.jxc.smack.SmackMultiChatManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 嘉进 on 15:01.
 */

public class RetrofitUntil {

    public static int type = -1;

    public static void getUserInfo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.SERVER_IP + "/group/")
                .callFactory(OkHttpUtil.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ILoginBiz iLoginBiz = retrofit.create(ILoginBiz.class);
        Call<GsonUsers> call = iLoginBiz.getUserInfo(UserUntil.phone);
        call.enqueue(new Callback<GsonUsers>() {
            @Override
            public void onResponse(Call<GsonUsers> call, Response<GsonUsers> response) {
                GsonUsers gsonUsers = response.body();
                if(gsonUsers.getCode() == 200){
                    Log.e("getUser", "success");
                    UserUntil.gsonUser = gsonUsers.getUserInfo();
                    if (type == Contants.SIGN_UP_GET_DATA){
                        EventBus.getDefault().post(new SignUpFinishEvent());
                    }

                }else {
                    Log.e("getUser", "fail");
                }
            }

            @Override
            public void onFailure(Call<GsonUsers> call, Throwable t) {
                Log.e("getUser", "fail");
            }
        });
    }

    public static void getFriend() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.SERVER_IP + "/user/")
                .callFactory(OkHttpUtil.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFriendBiz iFriendBiz = retrofit.create(IFriendBiz.class);
        Call<GsonFriends> call = iFriendBiz.getFriend(UserUntil.phone);
        call.enqueue(new Callback<GsonFriends>() {
            @Override
            public void onResponse(Call<GsonFriends> call, Response<GsonFriends> response) {
                GsonFriends gsonFriends = response.body();
                if (gsonFriends.getCode() == 200) {
                    List<GsonFriend> friendList = gsonFriends.getListFriends();
                    ArrayList<Contact> contacts = new ArrayList<Contact>();
                    for (GsonFriend myFriend : friendList) {
                        Contact contact = new Contact();
                        String name = myFriend.getNickname();
                        contact.setPhone(myFriend.getUserPhone());
                        contact.setName(name);
                        contact.setFirstLetter(FistLetterUntil.getSortKey(name));
                        Object object = myFriend.getUserHeadPortrait();
                        String path = null;
                        if (object != null){
                            path = object.toString();
                            contact.setPhotoURL(path);
                        }
                        contacts.add(contact);
                    }
                    UserUntil.friendList = contacts;
                } else {
                    Log.e("getFriend", gsonFriends.getMsg());
                }

            }

            @Override
            public void onFailure(Call<GsonFriends> call, Throwable t) {
                Log.e("getFriend", "fail");
            }
        });
    }

    public static void getGroupInfo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.SERVER_IP + "/group/")
                .callFactory(OkHttpUtil.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGroupBiz iGroupBiz = retrofit.create(IGroupBiz.class);
        Call<GsonGroups> call = iGroupBiz.getGroup(UserUntil.phone);
        call.enqueue(new Callback<GsonGroups>() {
            @Override
            public void onResponse(Call<GsonGroups> call, Response<GsonGroups> response) {
                if(response.body().getCode() == 200){
                    Log.e("getGroup", "success");
                    List<GsonGroup> createGroupList = new ArrayList<GsonGroup>();
                    List<GsonGroup> joinGroupList = new ArrayList<GsonGroup>();
                    List<GsonGroup> groupList = response.body().getListGroupId();
                    for(GsonGroup gsonGroup : groupList){
                        Log.e(gsonGroup.getGcreateduserid() + "", UserUntil.gsonUser.getId()+ "");
                        if(gsonGroup.getGcreateduserid() == UserUntil.gsonUser.getId()){
                            createGroupList.add(gsonGroup);
                        }else {
                            joinGroupList.add(gsonGroup);
                        }
                    }
                    UserUntil.createGroupList = createGroupList;
                    UserUntil.joinGroupList = joinGroupList;
                    UserUntil.groupList = groupList;
                    if (type == Contants.LOGIN_IN_GET_DATA){
                        EventBus.getDefault().post(new LoginFinishEvent());
                    }
                    try {
                        SmackMultiChatManager.bindJoinMultiChat();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e("getGroup", "fail");
                }
            }

            @Override
            public void onFailure(Call<GsonGroups> call, Throwable t) {
                Log.e("getGroup", "fail");
            }
        });
    }


    /**
     * 签到
     * @param time
     * @param address
     */
    public static void signOffice(String time, String address){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.SERVER_IP + "/user/")
                .callFactory(OkHttpUtil.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ISignOffice iSignOffice = retrofit.create(ISignOffice.class);
        Call<GsonBase> call = iSignOffice.signOffice(UserUntil.phone, time, address);
        call.enqueue(new Callback<GsonBase>() {
            @Override
            public void onResponse(Call<GsonBase> call, Response<GsonBase> response) {
                if(response.body().getCode() == 200){
                    Log.e("signOffice", "success");
                    if (type == Contants.LOGIN_IN_GET_DATA){
                        EventBus.getDefault().post(new SignOfficeEvent());
                    }

                }else {
                    Log.e("signOffice", "fail");
                }
            }

            @Override
            public void onFailure(Call<GsonBase> call, Throwable t) {
                Log.e("signOffice", "fail");
            }
        });

    }
}
