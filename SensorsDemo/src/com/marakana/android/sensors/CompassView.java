package com.marakana.android.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * The compass rose view. You can set its direction in terms of degrees and it
 * points there.
 */
public class CompassView extends ImageView {
	private static final int DEFAULT = 200;
	private static final int PADDING = 10;

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setImageResource(R.drawable.rose);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.setMeasuredDimension(measure(widthMeasureSpec),
				measure(heightMeasureSpec));
	}

	/**
	 * Given measurement specification, returns a measured size.
	 * 
	 * @param spec
	 *            Measurement Spec
	 * @return size
	 */
	private int measure(int spec) {
		int size = DEFAULT;
		switch (View.MeasureSpec.getMode(spec)) {
		case View.MeasureSpec.EXACTLY:
			size = View.MeasureSpec.getSize(spec);
			break;
		case View.MeasureSpec.AT_MOST:
			size = View.MeasureSpec.getSize(spec) - PADDING;
			break;
		case View.MeasureSpec.UNSPECIFIED:
			size = View.MeasureSpec.getSize(spec) / 2;
			break;
		}
		Log.d("CompassView", String.format("mode: %d, size: %d, resized: %d ",
				View.MeasureSpec.getMode(spec), View.MeasureSpec.getSize(spec),
				size));
		return size;
	}
}
