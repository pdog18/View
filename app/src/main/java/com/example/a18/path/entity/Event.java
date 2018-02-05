package com.example.a18.path.entity;

import org.litepal.crud.DataSupport;

import java.util.List;


public class Event extends DataSupport {
    private long id;

    private String name;
    private List<Message> messages ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
