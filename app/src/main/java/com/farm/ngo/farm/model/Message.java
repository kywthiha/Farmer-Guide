package com.farm.ngo.farm.model;

import java.io.Serializable;

public class Message implements Serializable {
    private String id;
    private String user_id;
    private Type type;
    private String text;
    private String image_url;
    private Object time;
    private MessageType messageType;
    private Boolean seen;
    private String nodeKey;

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Message() {

    }



    public Message(String user_id, Type type, String text, Object time, String image_url, MessageType messageType, Boolean seen) {
        this.image_url=image_url;
        this.messageType=messageType;
        this.user_id = user_id;
        this.type = type;
        this.text = text;
        this.time = time;
        this.seen = seen;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getTime() {
        return time;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return " messages -> "+this.getText()+"  :-> imageurl "+this.getImage_url()+"\n";
    }
}
