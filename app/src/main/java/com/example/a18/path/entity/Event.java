package com.example.a18.path.entity;

import org.litepal.crud.DataSupport;

import java.util.List;


public class Event extends DataSupport {
    public String name;
    public List<Message> messages;
}
