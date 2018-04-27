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
package kt.pdog18.com.module_transition;


import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import kt.pdog18.com.base.BaseFragment;


public class SlideSample extends BaseFragment {


    @BindView(R.id.transitions_container)
    ViewGroup transitions_container;
    @BindView(R.id.text)
    TextView text;
    private boolean visible;

    @OnClick(R.id.button)
    void button() {
        TransitionManager.beginDelayedTransition(transitions_container, new Slide(Gravity.RIGHT));
        text.setVisibility((visible =!visible) ? View.VISIBLE : View.GONE);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_slide;
    }

}
