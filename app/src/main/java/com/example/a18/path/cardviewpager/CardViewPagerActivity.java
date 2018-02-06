package com.example.a18.path.cardviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.a18.path.R;

public class CardViewPagerActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_pager);
        mViewPager = findViewById(R.id.viewPager);

        mCardAdapter = new CardPagerAdapter();


        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(3);

    }



    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    @Override
    public void onClick(View view) {
    }

}
