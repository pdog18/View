package com.example.a18.path.entity;

import org.litepal.crud.DataSupport;


public class Message extends DataSupport {
    public String name;

    public Event event;

    public Message(String name) {
        this.name = name;
    }
}
