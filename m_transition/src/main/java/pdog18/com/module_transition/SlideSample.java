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
package pdog18.com.module_transition;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pdog18.com.base.BaseFragment;


public class SlideSample extends BaseFragment {

    ViewGroup transitions_container;
    TextView text;
    private boolean visible;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transitions_container = view.findViewById(R.id.transitions_container);
        text = view.findViewById(R.id.text);
        view.findViewById(R.id.button).setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(transitions_container, new Slide(Gravity.RIGHT));
            text.setVisibility((visible =!visible) ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_slide;
    }

}
