package com.example.myappdemo.ViewOverride;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class PagerEnabledSlidingPaneLayout extends SlidingPaneLayout {

	private float mInitialMotionX;
	private final float mEdgeSlop;

	public PagerEnabledSlidingPaneLayout(final Context context) {
		this(context, null);
	}

	public PagerEnabledSlidingPaneLayout(final Context context, final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PagerEnabledSlidingPaneLayout(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);

		final ViewConfiguration config = ViewConfiguration.get(context);
		mEdgeSlop = config.getScaledEdgeSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(final MotionEvent ev) {

		switch (MotionEventCompat.getActionMasked(ev)) {
		case MotionEvent.ACTION_DOWN: {
			mInitialMotionX = ev.getX();
			ev.getY();
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			final float x = ev.getX();
			final float y = ev.getY();
			// The user should always be able to "close" the pane, so we only
			// check
			// for child scrollability if the pane is currently closed.
			if (mInitialMotionX > mEdgeSlop
					&& !isOpen()
					&& canScroll(this, false, Math.round(x - mInitialMotionX),
							Math.round(x), Math.round(y))) {

				// How do we set super.mIsUnableToDrag = true?

				// send the parent a cancel event
				final MotionEvent cancelEvent = MotionEvent.obtain(ev);
				cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
				return super.onInterceptTouchEvent(cancelEvent);
			}
		}
		}

		return super.onInterceptTouchEvent(ev);
	}
}