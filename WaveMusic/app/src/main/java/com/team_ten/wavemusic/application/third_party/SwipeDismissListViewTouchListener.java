package com.team_ten.wavemusic.application.third_party;

/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * File modified from the original source code to adhere to SOLID principles.
 * Modifications Copyright 2019 - Tyler Loewen, Lukas Timmerman, Jiehao Luo, and Diljot Garcha
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link View.OnTouchListener} that makes the list items in a {@link ListView}
 * dismissible. {@link ListView} is given special treatment because by default it handles touches
 * for its list items... i.e. it's in charge of drawing the pressed state (the list selector),
 * handling list item clicks, etc.
 *
 * <p>After creating the listener, the caller should also call
 * {@link ListView#setOnScrollListener(AbsListView.OnScrollListener)}, passing
 * in the scroll listener returned by {}. If a scroll listener is
 * already assigned, the caller should still pass scroll changes through to this listener. This will
 * ensure that this {@link SwipeDismissListViewTouchListener} is paused during list view
 * scrolling.</p>
 *
 * <p>Example usage:</p>
 *
 * <pre>
 * SwipeDismissListViewTouchListener touchListener =
 *         new SwipeDismissListViewTouchListener(
 *                 listView,
 *                 new SwipeDismissListViewTouchListener.OnDismissCallback() {
 *                     public void onDismiss(ListView listView, int[] reverseSortedPositions) {
 *                         for (int position : reverseSortedPositions) {
 *                             adapter.remove(adapter.getItem(position));
 *                         }
 *                         adapter.notifyDataSetChanged();
 *                     }
 *                 });
 * listView.setOnTouchListener(touchListener);
 * listView.setOnScrollListener(touchListener.makeScrollListener());
 * </pre>
 *
 * <p>This class Requires API level 12 or later due to use of {@link
 * ViewPropertyAnimator}.</p>
 *
 * <p>For a generalized {@link View.OnTouchListener} that makes any view dismissible,
 * see {@link SwipeDismissListViewTouchListener}.</p>
 *
 * @see SwipeDismissListViewTouchListener
 */
public class SwipeDismissListViewTouchListener implements View.OnTouchListener
{
	// Cached ViewConfiguration and system-wide constant values
	private final int mSlop;
	private final int mMinFlingVelocity;
	private final int mMaxFlingVelocity;
	private final long mAnimationTime;

	// Fixed properties
	private final ListView mListView;
	private final IDismissCallbacks mCallbacks;
	private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

	// Transient properties
	private final List<PendingDismissData> mPendingDismisses = new ArrayList<>();
	private int mDismissAnimationRefCount = 0;
	private float mDownX;
	private float mDownY;
	private boolean mSwiping;
	private int mSwipingSlop;
	private VelocityTracker mVelocityTracker;
	private int mDownPosition;
	private View mDownView;
	@SuppressWarnings("unused")
	private boolean mPaused;

	/**
	 * Constructs a new swipe-to-dismiss touch listener for the given list view.
	 *
	 * @param listView  The list view whose items should be dismissible.
	 * @param callbacks The callback to trigger when the user has indicated that she would like to
	 *                  dismiss one or more list items.
	 */
	public SwipeDismissListViewTouchListener(ListView listView, IDismissCallbacks callbacks)
	{
		ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
		mSlop = vc.getScaledTouchSlop();
		mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
		mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
		mAnimationTime = listView.getContext()
								 .getResources()
								 .getInteger(android.R.integer.config_shortAnimTime);
		mListView = listView;
		mCallbacks = callbacks;
	}

	/**
	 * Detect motions and enable event handling on fragment touch.
	 *
	 * @return whether or not there was an event handled.
	 */
	@SuppressLint("ClickableViewAccessibility") @Override public boolean onTouch(View view, MotionEvent motionEvent)
	{
		if (mViewWidth < 2)
		{
			mViewWidth = mListView.getWidth();
		}

		switch (motionEvent.getActionMasked())
		{
			case MotionEvent.ACTION_DOWN:
			{
				return actionDown(motionEvent);
			}

			case MotionEvent.ACTION_CANCEL:
			{
				if (mVelocityTracker == null)
				{
					break;
				}

				actionCancel();
				break;
			}

			case MotionEvent.ACTION_UP:
			{
				if (mVelocityTracker == null)
				{
					break;
				}
				actionUp(motionEvent);
				break;
			}

			case MotionEvent.ACTION_MOVE:
			{
				if (mVelocityTracker == null || mPaused)
				{
					break;
				}
				return actionMove(motionEvent);
			}
		}
		return false;
	}

	@SuppressWarnings("SameReturnValue") private boolean actionDown(MotionEvent motionEvent)
	{
		if (mPaused)
		{
			return false;
		}

		// Find the child view that was touched (perform a hit test)
		Rect rect = new Rect();
		int childCount = mListView.getChildCount();
		int[] coordinates = calculateCoordinates(motionEvent, mListView);
		View child;
		for (int i = 0; i < childCount; i++)
		{
			child = mListView.getChildAt(i);
			child.getHitRect(rect);
			if (rect.contains(coordinates[0], coordinates[1]))
			{
				mDownView = child;
				break;
			}
		}

		if (mDownView != null)
		{
			mDownX = motionEvent.getRawX();
			mDownY = motionEvent.getRawY();
			mDownPosition = mListView.getPositionForView(mDownView);
			if (mCallbacks.canDismiss(mDownPosition))
			{
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(motionEvent);
			}
			else
			{
				mDownView = null;
			}
		}
		return false;
	}

	private void actionCancel()
	{
		if (mDownView != null && mSwiping)
		{
			// cancel
			mDownView.animate()
					 .translationX(0)
					 .alpha(1)
					 .setDuration(mAnimationTime)
					 .setListener(null);
		}
		mVelocityTracker.recycle();
		mVelocityTracker = null;
		mDownX = 0;
		mDownY = 0;
		mDownView = null;
		mDownPosition = ListView.INVALID_POSITION;
		mSwiping = false;
	}

	private void actionUp(MotionEvent motionEvent)
	{

		float deltaX = motionEvent.getRawX() - mDownX;
		mVelocityTracker.addMovement(motionEvent);
		mVelocityTracker.computeCurrentVelocity(1000);
		float velocityX = mVelocityTracker.getXVelocity();
		float absVelocityX = Math.abs(velocityX);
		float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
		boolean dismiss = false;
		boolean dismissRight = false;
		if (Math.abs(deltaX) > mViewWidth / 2 && mSwiping)
		{
			dismiss = true;
			dismissRight = deltaX > 0;
		}
		else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity &&
				 absVelocityY < absVelocityX && mSwiping)
		{
			// dismiss only if flinging in the same direction as dragging
			dismiss = (velocityX < 0) == (deltaX < 0);
			dismissRight = mVelocityTracker.getXVelocity() > 0;
		}
		if (dismiss && mDownPosition != ListView.INVALID_POSITION)
		{
			// dismiss
			final View downView = mDownView; // mDownView gets null'd before animation ends
			final int downPosition = mDownPosition;
			++mDismissAnimationRefCount;
			mDownView.animate()
					 .translationX(dismissRight ? mViewWidth : -mViewWidth)
					 .alpha(0)
					 .setDuration(mAnimationTime)
					 .setListener(new AnimatorListenerAdapter()
					 {
						 @Override public void onAnimationEnd(Animator animation)
						 {
							 performDismiss(downView, downPosition);
						 }
					 });
		}
		else
		{
			// cancel
			mDownView.animate()
					 .translationX(0)
					 .alpha(1)
					 .setDuration(mAnimationTime)
					 .setListener(null);
		}
		mVelocityTracker.recycle();
		mVelocityTracker = null;
		mDownX = 0;
		mDownY = 0;
		mDownView = null;
		mDownPosition = ListView.INVALID_POSITION;
		mSwiping = false;
	}

	private boolean actionMove(MotionEvent motionEvent)
	{
		mVelocityTracker.addMovement(motionEvent);
		float deltaX = motionEvent.getRawX() - mDownX;
		float deltaY = motionEvent.getRawY() - mDownY;
		if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2)
		{
			mSwiping = true;
			mSwipingSlop = (deltaX > 0 ? mSlop : -mSlop);
			mListView.requestDisallowInterceptTouchEvent(true);

			// Cancel ListView's touch (un-highlighting the item)
			MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
			cancelEvent.setAction(MotionEvent.ACTION_CANCEL | (motionEvent.getActionIndex() <<
															   MotionEvent.ACTION_POINTER_INDEX_SHIFT));
			mListView.onTouchEvent(cancelEvent);
			cancelEvent.recycle();
		}

		if (mSwiping)
		{
			mDownView.setTranslationX(deltaX - mSwipingSlop);
			mDownView.setAlpha(Math.max(0f, Math.min(1f,
													 1f - 2f * Math.abs(deltaX) / mViewWidth)));
			return true;
		}
		return false;
	}

	private int[] calculateCoordinates(MotionEvent motionEvent, ListView mListView)
	{
		int[] listViewCoordinates = new int[2];
		mListView.getLocationOnScreen(listViewCoordinates);
		int x = (int) motionEvent.getRawX() - listViewCoordinates[0];
		int y = (int) motionEvent.getRawY() - listViewCoordinates[1];

		return new int[] {x, y};
	}

	/**
	 * Add list data to the queue of data pending dismissal.
	 */
	private void performDismiss(final View dismissView, final int dismissPosition)
	{
		// Animate the dismissed list item to zero-height and fire the dismiss callback when
		// all dismissed list item animations have completed. This triggers layout on each
		// animation frame; in the future we may want to do something smarter with better
		// performance.

		final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
		final int originalHeight = dismissView.getHeight();

		ValueAnimator animator =
				ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

		animator.addListener(new AnimatorListenerAdapter()
		{
			@Override public void onAnimationEnd(Animator animation)
			{
				--mDismissAnimationRefCount;
				if (mDismissAnimationRefCount == 0)
				{
					// No active animations, process all pending dismisses.
					// Sort by descending position
					Collections.sort(mPendingDismisses);

					int[] dismissPositions = new int[mPendingDismisses.size()];
					for (int i = mPendingDismisses.size() - 1; i >= 0; i--)
					{
						dismissPositions[i] = mPendingDismisses.get(i).position;
					}
					mCallbacks.onDismiss(mListView, dismissPositions);

					// Reset mDownPosition to avoid MotionEvent.ACTION_UP trying to start a dismiss
					// animation with a stale position
					mDownPosition = ListView.INVALID_POSITION;

					ViewGroup.LayoutParams lp;
					for (PendingDismissData pendingDismiss : mPendingDismisses)
					{
						// Reset view presentation
						pendingDismiss.view.setAlpha(1f);
						pendingDismiss.view.setTranslationX(0);
						lp = pendingDismiss.view.getLayoutParams();
						lp.height = originalHeight;
						pendingDismiss.view.setLayoutParams(lp);
					}

					// Send a cancel event
					long time = SystemClock.uptimeMillis();
					MotionEvent cancelEvent = MotionEvent.obtain(time,
																 time,
																 MotionEvent.ACTION_CANCEL,
																 0,
																 0,
																 0);
					mListView.dispatchTouchEvent(cancelEvent);

					mPendingDismisses.clear();
				}
			}
		});

		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			@Override public void onAnimationUpdate(ValueAnimator valueAnimator)
			{
				lp.height = (Integer) valueAnimator.getAnimatedValue();
				dismissView.setLayoutParams(lp);
			}
		});

		mPendingDismisses.add(new PendingDismissData(dismissPosition, dismissView));
		animator.start();
	}

	class PendingDismissData implements Comparable<PendingDismissData>
	{
		final int position;
		final View view;

		PendingDismissData(int position, View view)
		{
			this.position = position;
			this.view = view;
		}

		/**
		 * Return a comparison between this data and other data being dismissed.
		 *
		 * @return the difference in positions; cardinality of returned value indicates the
		 * "comparison" (equal, less or greater)
		 */
		@Override public int compareTo(PendingDismissData other)
		{
			// Sort by descending position
			return other.position - position;
		}
	}
}