package com.example.a18.path.entity;

import org.litepal.crud.DataSupport;


public class Message extends DataSupport {
    private String name;

    private Event event;

    public Message(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Event getEvent() {
        return event;
    }
}
