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
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

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

    public static void scaleAnimateView(View view) {
        ScaleAnimation animation = new ScaleAnimation(
                1.15f, 1, 1.15f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        view.setAnimation(animation);
        animation.setDuration(100);
        animation.start();
    }

    /**
     * Expand View Height
     */
    public final void expand(@NotNull final View v, @Nullable Long duration) {
        final int initHeight = v.getHeight();

        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int targetHeight = v.getMeasuredHeight();

        final int distanceHeight = targetHeight - initHeight;

        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, @NotNull Transformation t) {

                v.getLayoutParams().height =
                        interpolatedTime == 1.0F
                                ? ViewGroup.LayoutParams.WRAP_CONTENT
                                : initHeight + (int)((float)distanceHeight * interpolatedTime);
                v.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration != null ? duration : 300L);
        v.startAnimation(animation);
    }

    /**
     * Collapse View Height
     * How to Use: AnimationUtil.collapse(txtLyric, 0, 300f), true, null)
     */
    public final void collapse(@NotNull final View v, int targetHeight, final boolean goneAfterDone, @Nullable Long duration) {

        final int initialHeight = v.getMeasuredHeight();
        final int distanceHeight = targetHeight - v.getMeasuredHeight();

        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, @NotNull Transformation t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                if (interpolatedTime == 1.0F && goneAfterDone) {
                    v.setVisibility(View.GONE);
                }

                v.getLayoutParams().height = initialHeight + (int)((float)distanceHeight * interpolatedTime);
                v.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration != null ? duration : 300L);
        v.startAnimation(animation);
    }
}
