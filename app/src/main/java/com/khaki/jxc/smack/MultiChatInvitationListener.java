package com.khaki.jxc.smack;


import android.util.Log;


import com.khaki.jxc.Contants;
import com.khaki.jxc.bean.ChatRecord;
import com.khaki.jxc.utils.DateUtil;
import com.khaki.jxc.utils.RetrofitUntil;
import com.khaki.jxc.utils.UserUntil;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.packet.MUCUser;
import org.jxmpp.jid.EntityJid;
import org.jxmpp.jid.parts.Resourcepart;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.UUID;


/**
 * 多人聊天邀请监听
 *
 * @author: laohu on 2017/1/24
 * @site: http://ittiger.cn
 */
public class MultiChatInvitationListener implements InvitationListener {

    public void invitationReceived(XMPPConnection conn, MultiUserChat room, String inviter,
                                   String reason, String password, Message message) {
        Log.e("invitationReceived", "invitationReceived");
        try {
            room.join(Resourcepart.fromOrThrowUnchecked(UserUntil.gsonUser.getNickname()));
            SmackMultiChatManager.saveMultiChat(room);
            SmackListenerManager.addMultiChatMessageListener(room);
            Log.e("加入群里", "加入群里");
            ChatRecord record;
            List<ChatRecord> chatRecords = DataSupport.where("mfriendusername=?", String.valueOf(room.getRoom())).find(ChatRecord.class);
            if (chatRecords.size() == 0) {
                record = new ChatRecord();
                String friendUserName = String.valueOf(room.getRoom());
                int idx = friendUserName.indexOf("@conference.");
                String friendNickName = friendUserName.substring(0, idx);
                record.setUuid(UUID.randomUUID().toString());
                record.setmFriendUsername(friendUserName);
                record.setmFriendNickname(friendNickName);
                record.setmMeUsername(UserUntil.gsonUser.getUserPhone());
                record.setmMeNickname(String.valueOf(UserUntil.gsonUser.getNickname()));
                record.setmChatTime(DateUtil.currentDatetime());
                record.setmChatJid(friendUserName);
                record.setmIsMulti(true);
                record.save();
                try {
                    RetrofitUntil.type = Contants.LOGIN_IN_GET_DATA;
                    RetrofitUntil.getUserInfo();
                    RetrofitUntil.getFriend();
                    RetrofitUntil.getGroupInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                record = chatRecords.get(0);
            }
            EventBus.getDefault().post(record);
//将群聊保存在通讯录
//            String friendUserName = room.getRoom();
//            int idx = friendUserName.indexOf(Constant.MULTI_CHAT_ADDRESS_SPLIT);
//            String friendNickName = friendUserName.substring(0, idx);
//            ChatUser chatUser = new ChatUser(friendUserName, friendNickName, true);
//            DBHelper.getInstance().getSQLiteDB().save(chatUser);
        } catch (Exception e) {
            Log.e(e.toString(), "join multiChat failure on invitationReceived");
        }
    }

    @Override
    public void invitationReceived(XMPPConnection conn, MultiUserChat room, EntityJid inviter, String reason, String password, Message message, MUCUser.Invite invitation) {

    }
}
