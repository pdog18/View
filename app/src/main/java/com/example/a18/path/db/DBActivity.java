package com.example.a18.path.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.a18.path.R;
import com.example.a18.path.entity.Event;
import com.example.a18.path.entity.Message;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class DBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.save)
    void save() {
        Event event = new Event();
        event.name = "event name";
        Message[] messages = new Message[]{new Message("message1"), new Message("message2")};

        List<Message> messageList = Arrays.asList(messages);

        event.messages = messageList;
        boolean save = event.save();
        Timber.d("save = %s", save);

        Toast.makeText(this, String.format("save = %s",save), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.query)
    void query() {
        Event event = DataSupport.find(Event.class, 1);
        // FIXME: 04/02/2018  messages is null
//        if (event != null) {
//            Timber.d("event.name = %s", event.name);
//            for (Message message : event.messages) {
//                Timber.d("message.name = %s", message.name);
//            }
//        }
    }
}
