package com.laiweifeng.launcher.utlis;


import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.view.View;
public class AnimUtils {

	/**
	 * 右进入动画
	 */
	public static void rightIn(View view,final OnAnimListener listener){

		ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", -200,0);
		animatorX.setDuration(500);
		animatorX.start();
		animatorX.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {}

			@Override
			public void onAnimationRepeat(Animator animation) {}

			@Override
			public void onAnimationEnd(Animator animation) {
				listener.onAnimEnd();
			}

			@Override
			public void onAnimationCancel(Animator animation) {}
		});
	}
	/**
	 * 左进入动画
	 */
	public  static void leftIn(View view,final OnAnimListener listener){
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", 200,0);
		animatorX.setDuration(500);
		animatorX.start();
		animatorX.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {}

			@Override
			public void onAnimationRepeat(Animator animation) {}

			@Override
			public void onAnimationEnd(Animator animation) {
				listener.onAnimEnd();
			}

			@Override
			public void onAnimationCancel(Animator animation) {}
		});
	}
	
	/**
	 * 左离开动画
	 */
	public static  void leftOut(View view,final OnAnimListener listener){
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", 0,200);
		animatorX.setDuration(500);
		animatorX.start();
		animatorX.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {}

			@Override
			public void onAnimationRepeat(Animator animation) {}

			@Override
			public void onAnimationEnd(Animator animation) {
				listener.onAnimEnd();
			}

			@Override
			public void onAnimationCancel(Animator animation) {}
		});
	}

	public static void rightOut(View view, final OnAnimListener listener ) {
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", 0,-200);
		animatorX.setDuration(500);
		animatorX.start();
		animatorX.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {}

			@Override
			public void onAnimationRepeat(Animator animation) {}

			@Override
			public void onAnimationEnd(Animator animation) {
				listener.onAnimEnd();
			}

			@Override
			public void onAnimationCancel(Animator animation) {}
		});
	}

	public interface OnAnimListener{
		void onAnimEnd();
	}
}
