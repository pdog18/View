package com.example.a18.path.cardrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a18.path.R;

public class CardRecyclerviewActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_recyclerview);
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view;
                if (viewType == 0){
                    view = inflater.inflate(R.layout.adapter_assets_card_home, parent, false);
                }else {
                    view = inflater.inflate(R.layout.adapter_credit_card, parent, false);
                }
                return new RecyclerView.ViewHolder(view){};
            }

            @Override
            public int getItemViewType(int position) {
                if (position <10 ){
                    return 0;
                }else {
                    return 1;
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });


        editText = findViewById(R.id.et);

    }

    public void btn(View v){
        String s = editText.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return;
        }

        String[] split = s.split(" ");

        if (split.length !=3) {
            Toast.makeText(this, "length !=3", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < 3; i++) {
            int temp = Integer.valueOf(split[i]);
            split[i] = Integer.toHexString(temp);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(split[0])
                .append(split[1])
                .append(split[2]);

        editText.setText(builder.toString());

        editText.setSelection(builder.toString().length());

    }
}
