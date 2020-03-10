package com.khaki.jxc.event;

import com.khaki.jxc.bean.ChatRecord;

/**
 * Created by dell88 on 2017/7/27 0027.
 */

public class ChatRecordEvent {
    private ChatRecord chatRecord;

    public ChatRecordEvent(ChatRecord chatRecord) {
        this.chatRecord = chatRecord;
    }

    public ChatRecord getChatRecord() {
        return chatRecord;
    }

    public void setChatRecord(ChatRecord chatRecord) {
        this.chatRecord = chatRecord;
    }
}
