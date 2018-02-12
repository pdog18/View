package com.example.a18.path.retrofitrequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a18.path.R;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitActivity extends AppCompatActivity implements Callback<String> {

    private Retrofit retrofit;
    private API api;

    {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .followRedirects(false)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
//                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    /**
     * 向一个请求体中添加内容的方法
     * @param chain
     * @return
     * @throws IOException
     */
    private okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();
        Buffer buffer = new Buffer();

        body.writeTo(buffer);
        String source = buffer.readString(Charset.defaultCharset());
        String result = String.format("<string xxxx>%s</string>", source);
        MediaType your_mediatype = MediaType.parse("your_mediatype");
        return chain.proceed(request.newBuilder().post(RequestBody.create(your_mediatype, result)).build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        api = retrofit.create(API.class);
    }

    void run(Call<String> call) {
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }


    public void get(View view) {
        run(api.get());
    }

    public void query(View view) {
        run(api.query("query_value"));
    }

    public void post(View view) {
        run(api.post());
    }

    public void queryMap(View view) {
        HashMap<String, String> map = new HashMap<>();
        map.put("query_key", "query_value");

        run(api.queryMap(map));
    }


    public void formUrlEncoded(View view) {
        run(api.formUrlEncoded_filed("filed_value"));
    }

    public void part(View view) {
        run(api.part("part_value"));
    }

    public void path(View view) {
        run(api.path("path_value"));
    }

    public void header(View view){
        run(api.head("header_value"));
    }

    public void body(View view){
        run(api.body(new User("dog", 25)));
    }

    public void postWithQuery(View view) {
        run(api.postWithQuery("value"));
    }
}
