package com.example.a18.path.invest;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a18.path.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.completable.CompletableLift;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class InvestActivity extends AppCompatActivity {
    @BindView(R.id.本金)
    EditText 本金;

    @BindView(R.id.投资天数)
    EditText 投资天数;

    @BindView(R.id.年化收益率)
    EditText 年化收益率;
    @BindView(R.id.n个月)
    EditText n个月;


    @BindView(R.id.tv)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);
        ButterKnife.bind(this);

        Observable
                .create(e -> e.onNext("aaa" + Thread.currentThread().getName()))
                .doOnSubscribe(disposable -> Timber.d(Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .subscribe(o -> Timber.d(o.getClass().getSimpleName() + Thread.currentThread().getName()));

        CompletableLift.complete();

    }

    @OnClick(R.id.计算收益)
    void 计算收益() {

        float money = Float.valueOf(本金.getText().toString());
        float day = Float.valueOf(投资天数.getText().toString());
        float rate = Float.valueOf(年化收益率.getText().toString());

        setTextView(money * rate * day / 365.0f / 100);
    }

    @OnClick(R.id.计算一年收益)
    void 计算一年收益() {

        float money = Float.valueOf(本金.getText().toString());
        float rate = Float.valueOf(年化收益率.getText().toString());

        setTextView(money * rate / 100);
    }

    @OnClick(R.id.计算n月收益)
    void 计算n月收益() {

        float money = Float.valueOf(本金.getText().toString());
        float month = Float.valueOf(n个月.getText().toString());
        float rate = Float.valueOf(年化收益率.getText().toString());

        float gain = money * rate * month * 30 / 365.0f / 100;
        setTextView(gain);
    }

    @SuppressLint("DefaultLocale")
    void setTextView(float gain) {
        textView.setText(String.format("预计收益 : %f元", gain));
    }
}
