package com.example.supermarket_plan;

import static com.example.supermarket_plan.Constants.PLAN_PATH;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ZoomButtonsController;
import android.widget.ZoomButtonsController.OnZoomListener;

public class PlanActivity extends Activity implements OnTouchListener,
		OnZoomListener {
	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	private ZoomButtonsController zoomButtons;

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	int height = 0, width = 0;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	float currentScale = 1.0f;
	final float maxScale = 3f;

	ImageView imageView;
	boolean move = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);

		FrameLayout fl = (FrameLayout) findViewById(R.id.parent);
		imageView = (ImageView) findViewById(R.id.imageView);

		Intent intent = getIntent();
		String planPath = intent.getStringExtra(PLAN_PATH);
		int id = getResources().getIdentifier(planPath, "drawable",
				getPackageName());

		if (id != 0) {
			imageView.setAdjustViewBounds(false);
			imageView.setImageResource(id);
			imageView.setOnTouchListener(this);
		} else {
			int notFoundId = getResources().getIdentifier("not_found", "drawable",
					getPackageName());
			imageView.setAdjustViewBounds(true);
			imageView.setImageResource(notFoundId);
		}

		View view = new View(this);
		zoomButtons = new ZoomButtonsController(view);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		view = zoomButtons.getContainer();
		view.setLayoutParams(params);
		fl.addView(view);
		zoomButtons.setOnZoomListener(this);
		view.setVisibility(View.INVISIBLE);

		// Work around a Cupcake bug
		matrix.setTranslate(1f, 1f);
		imageView.setImageMatrix(matrix);
	}

	public void onVisibilityChanged(boolean visible) {
		// TODO Auto-generated method stub

	}

	public void onZoom(boolean zoomIn) {
		matrix.set(imageView.getImageMatrix());
		if (zoomIn) {
			if (currentScale < maxScale) {
				currentScale *= 1.25f;
				matrix.postScale(1.25f, 1.25f);
			}
		} else {
			if (currentScale > 1.0f) {
				currentScale *= 0.8f;
				matrix.postScale(0.8f, 0.8f);
			}
		}
		limitDrag(matrix);
		limitZoom(matrix);
		imageView.setImageMatrix(matrix);
	}

	@TargetApi(4)
	public boolean onTouch(View v, MotionEvent rawEvent) {
		WrapMotionEvent event = WrapMotionEvent.wrap(rawEvent);

		ImageView view = (ImageView) v;
		View zbView = (View) zoomButtons.getContainer();

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			mode = DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if (!move) {
				int vis = zbView.getVisibility();
				if (vis == View.INVISIBLE) {
					vis = View.VISIBLE;
				} else {
					vis = View.INVISIBLE;
				}
				zbView.setVisibility(vis);
			}
			move = false;
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			move = true;
			zbView.setVisibility(View.INVISIBLE);
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);

			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);
					limitZoom(matrix);
				}
			}
			break;
		}
		limitDrag(matrix);
		view.setImageMatrix(matrix);
		return true; // indicate event was handled
	}

	/** Determine the space between the first two fingers */
	private float spacing(WrapMotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, WrapMotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private void limitDrag(Matrix m) {
		float[] values = new float[9];
		m.getValues(values);
		float transX = values[Matrix.MTRANS_X];
		float transY = values[Matrix.MTRANS_Y];
		float scaleX = values[Matrix.MSCALE_X];
		float scaleY = values[Matrix.MSCALE_Y];

		Rect bounds = imageView.getDrawable().getBounds();
		int viewWidth = getResources().getDisplayMetrics().widthPixels;
		int viewHeight = getResources().getDisplayMetrics().heightPixels;

		int width = bounds.right - bounds.left;
		int height = bounds.bottom - bounds.top;

		float minX = viewWidth - width * scaleX;
		float minY = viewHeight - height * scaleY;

		if (viewWidth <= (width * scaleX)) {
			if (transX > 0) {
				transX = 0;
			} else if (transX < minX) { // обмеження по правому борту
				transX = minX;
			}
		} else {
			transX = 0;
		}

		if (viewHeight <= (height * scaleY)) {
			if (transY > 0) {
				transY = 0;
			} else if (transY < minY) { // обмеження по низу
				transY = minY;
			}
		} else {
			transY = 0;
		}

		values[Matrix.MTRANS_X] = transX;
		values[Matrix.MTRANS_Y] = transY;
		m.setValues(values);
	}

	private void limitZoom(Matrix m) {
		float[] values = new float[9];
		m.getValues(values);
		float scaleX = values[Matrix.MSCALE_X];
		float scaleY = values[Matrix.MSCALE_Y];

		float MAX_ZOOM = (float) maxScale;
		float MIN_ZOOM = 1.0f;

		if (scaleX > MAX_ZOOM) {
			scaleX = MAX_ZOOM;
		} else if (scaleX < MIN_ZOOM) {
			scaleX = MIN_ZOOM;
		}

		if (scaleY > MAX_ZOOM) {
			scaleY = MAX_ZOOM;
		} else if (scaleY < MIN_ZOOM) {
			scaleY = MIN_ZOOM;
		}

		currentScale = scaleX;
		values[Matrix.MSCALE_X] = scaleX;
		values[Matrix.MSCALE_Y] = scaleY;
		m.setValues(values);
	}
}
