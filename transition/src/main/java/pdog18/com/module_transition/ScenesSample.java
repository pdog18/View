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
import androidx.annotation.Nullable;
import androidx.transition.ChangeBounds;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import pdog18.com.base.BaseFragment;


/**
 * Created by Andrey Kulikov on 20/03/16.
 */
public class ScenesSample extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    // We transition between these Scenes
    private Scene mScene1;
    private Scene mScene2;
    private Scene mScene3;


    ViewGroup scene_root;
    RadioGroup select_scene;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scene_root = view.findViewById(R.id.scene_root);
        select_scene = view.findViewById(R.id.select_scene);
        select_scene.setOnCheckedChangeListener(this);

        // A Scene can be instantiated from a live view hierarchy.
        mScene1 = new Scene(scene_root, scene_root.findViewById(R.id.container));

        // You can also inflate a generate a Scene from a layout resource file.
        mScene2 = Scene.getSceneForLayout(scene_root, R.layout.scene2, getContext());

        // Another scene from a layout resource file.
        mScene3 = Scene.getSceneForLayout(scene_root, R.layout.scene3, getContext());

        // We create a custom TransitionManager for Scene 3, in which ChangeBounds, Fade and
        // ChangeImageTransform take place at the same time.
//        mTransitionManagerForScene3 = TransitionInflater.from(getContext())
//            .inflateTransitionManager(R.anim.scene3_transition_manager, mSceneRoot);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scenes;
    }

    @Override
    public void onCheckedChanged(final RadioGroup group, int checkedId) {
        if (checkedId == R.id.select_scene_1) {
            TransitionManager.go(mScene1);
        } else if (checkedId == R.id.select_scene_2) {
            TransitionSet set = new TransitionSet();
            Slide slide = new Slide(Gravity.LEFT);
            slide.addTarget(R.id.transition_title);
            set.addTransition(slide);
            set.addTransition(new ChangeBounds());
            set.setOrdering(TransitionSet.ORDERING_TOGETHER);
            set.setDuration(350);
            TransitionManager.go(mScene2, set);
        } else if (checkedId == R.id.select_scene_3) {
            TransitionManager.go(mScene2);
        }
    }
}
