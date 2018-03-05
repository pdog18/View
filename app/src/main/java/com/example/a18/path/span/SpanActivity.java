package com.example.a18.path.span;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a18.path.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class SpanActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView tv;
    private MediaRecorder mediaRecorder;
    private boolean isRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);
        ButterKnife.bind(this);

        SpannableStringBuilder spannableStringBuilder = new SpanUtils().append("国").setFontSize(128, true)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_TOP)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_BASELINE)
            .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_BOTTOM)
            .append("小小小").setFontSize(12, true)
            .create();

        tv.setText(spannableStringBuilder);

//        RxView.clicks(img_2)
//            .compose(RxPermissions.)
    }

    @BindView(R.id.img_2)
    ImageView img_2;

    @OnClick(R.id.btn1)
    void start() {
        try {

            File cacheDir = getCacheDir();
            Timber.d("dataDir.getAbsolutePath() = %s", cacheDir.getAbsolutePath());

            File dataDirectory = Environment.getDataDirectory();
            Timber.d("dataDirectory = %s", dataDirectory);

            mediaRecorder = new MediaRecorder();
            // 设置音频录入源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置录制音频的输出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // 设置音频的编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置录制音频文件输出文件路径
            mediaRecorder.setOutputFile(cacheDir.getAbsolutePath());

            mediaRecorder.setOnErrorListener((mr, what, extra) -> {
                // 发生错误，停止录制
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                isRecording = false;
                Toast.makeText(SpanActivity.this, "录音发生错误", Toast.LENGTH_SHORT).show();
            });

            // 准备、开始
            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;
            Toast.makeText(SpanActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btn2)
    void stop() {

        if (isRecording) {
            // 如果正在录音，停止并释放资源
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            Toast.makeText(this, "录音结束", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onDestroy() {
        if (isRecording) {
            // 如果正在录音，停止并释放资源
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        super.onDestroy();
    }

    @BindView(R.id.img_4)
    ImageView img_4;
    @BindView(R.id.img_3)
    ImageView img_3;


    @OnClick(R.id.img_4)
    void imgZ() {
        img_4.animate()
            .translationZ(10)
            .setDuration(3000)
            .start();
    }

    @OnClick(R.id.img_3)
    void img3Z() {
        img_3.animate()
            .translationZ(0)
            .setDuration(3000)
            .start();
    }

    @OnClick(R.id.btn3)
    void premissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
            .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    Toast.makeText(SpanActivity.this, String.format("aBoolean = %s", aBoolean), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
