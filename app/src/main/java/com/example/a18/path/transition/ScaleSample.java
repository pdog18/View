/*
 * Copyright (C) 2016 Andrey Kulikov (andkulikov@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.a18.path.transition;

import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ScaleSample extends BaseFragment {


    @BindView(R.id.transitions_container)
    ViewGroup transitionsContainer;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    private boolean visible;


    @OnClick(R.id.button1)
    void button1() {
        TransitionManager.beginDelayedTransition(transitionsContainer, new Scale());
        text1.setVisibility((visible = !visible) ? View.VISIBLE : View.INVISIBLE);
    }
    @OnClick(R.id.button2)
    void button2() {
        visible = !visible;
        TransitionSet set = new TransitionSet()
            .addTransition(new Scale())
            .addTransition(new Fade())
            .setInterpolator((visible) ? new LinearOutSlowInInterpolator() : new FastOutLinearInInterpolator());
        TransitionManager.beginDelayedTransition(transitionsContainer, set);
        text2.setVisibility((visible) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scale;
    }

}
