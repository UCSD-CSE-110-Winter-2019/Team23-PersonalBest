package com.cse110.personalbest;

public class ChatMessage {
    public enum MSG_TYPE {
        FROM_FRIEND, TO_FRIEND
    }

    private String email;
    private String chatText;
    private String time;
    private MSG_TYPE msgType;

    public ChatMessage(String email, String chatText, String time, MSG_TYPE msgType) {
        this.email = email;
        this.chatText = chatText;
        this.time = time;
        this.msgType = msgType;
    }

    public String getEmail() {
        return email;
    }

    public String getChatText() {
        return this.chatText;
    }

    public String getTime() {
        return this.time;
    }

    public MSG_TYPE getMsgType() {
        return this.msgType;
    }
}
