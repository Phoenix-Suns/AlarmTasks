package com.windyroad.nghia.common;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.transition.AutoTransition;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

public class AnimationUtil {

    public static final int DURATION = 500;

    /**
     * Auto Show/Hide Animation
     * @param viewGroup
     * @param listener
     */
    public static void beginAuto(ViewGroup viewGroup, Transition.TransitionListener listener) {
        AutoTransition transition = new AutoTransition();
        transition.setDuration(DURATION);
        transition.setOrdering(TransitionSet.ORDERING_TOGETHER);
        if (listener != null) transition.addListener(listener);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void beginFade(ViewGroup viewGroup) {
        Fade transition = new Fade();
        transition.setDuration(DURATION);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void beginChangeBounds(ViewGroup viewGroup) {
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(DURATION);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void beginSlide(ViewGroup viewGroup, boolean slideUp) {
        Slide transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(DURATION);
        TransitionManager.beginDelayedTransition(viewGroup, transition);
    }

    public static void changeColor(View view) {
        int colorFrom = Color.RED;
        int colorTo = Color.GREEN;
        ObjectAnimator.ofObject(view, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo)
                .setDuration(1000)
                .start();
    }
}
