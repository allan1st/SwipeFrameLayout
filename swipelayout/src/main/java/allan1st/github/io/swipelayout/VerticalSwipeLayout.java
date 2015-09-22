package allan1st.github.io.swipelayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by yilun
 * on 22/09/15.
 */
@SuppressWarnings("unused")
public class VerticalSwipeLayout extends FrameLayout {

    private static final String LOG_TAG = VerticalSwipeLayout.class.getSimpleName();

    private static final int ANIMATION_DURATION_SHORT = 300;

    private static final int DIMEN_PULL_TRIGGER_DISTANCE_DP = 50;

    private static final int DIMEN_PULL_MAX_DISTANCE_DP = 80;

    private float mDownY;

    private View mTarget;

    private Interpolator mInterpolator;

    private SwipeListener mSwipeListener;

    public VerticalSwipeLayout(Context context) {
        super(context);
        init();
    }

    public VerticalSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalSwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
        mInterpolator = new DecelerateInterpolator();
    }

    public void setSwipeListener(SwipeListener swipeListener) {
        this.mSwipeListener = swipeListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new RuntimeException("You can only have one child");
        } else if (getChildCount() == 1) {
            mTarget = getChildAt(0);
        }
    }

    @Override
    public void addView(@NonNull View child) {
        if (mTarget == null) {
            super.addView(child);
        }
        throw new RuntimeException("You can only have one child");
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerticalSwipeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canChildScrollUp() && canChildScrollDown()) {
            return false;
        }
        switch (ev.getAction()) {
            case ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case ACTION_MOVE:
                float y = ev.getY();
                float dy = y - mDownY;
                if (dy > 0 && !canChildScrollUp()) {
                    return true;
                }
                if (dy < 0 && !canChildScrollDown()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case ACTION_DOWN:
                mDownY = event.getY();
                return true;
            case ACTION_MOVE:
                float y = event.getY();
                float dy = y - mDownY;
                float translationY = (getTranslationY() + dy) / 3;
                float fraction = Math.abs(translationY / getMaxDistancePixels());
                if (mSwipeListener != null) {
                    if (translationY > 0) {
                        mSwipeListener.onSwipeDown(fraction);
                    } else if (translationY < 0) {
                        mSwipeListener.onSwipeUp(fraction);
                    }
                }
                if (!canChildScrollUp() && canChildScrollDown()) {
                    setTranslationY(MathUtils.constrains(translationY, 0, getMaxDistancePixels()));
                }
                if (!canChildScrollDown() && canChildScrollUp()) {
                    setTranslationY(MathUtils.constrains(translationY, 0, -getMaxDistancePixels()));
                }
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
                this.animate().translationY(0)
                        .setInterpolator(mInterpolator)
                        .setDuration(ANIMATION_DURATION_SHORT)
                        .start();
                if (getTranslationY() > getTriggerDistancePixels()) {
                    if (mSwipeListener != null) {
                        mSwipeListener.onTriggerUp();
                    }
                } else if (getTranslationY() < -getTriggerDistancePixels()) {
                    if (mSwipeListener != null) {
                        mSwipeListener.onTriggerDown();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean canChildScrollUp() {
        return mTarget != null && ViewCompat.canScrollVertically(mTarget, -1);
    }


    public boolean canChildScrollDown() {
        return mTarget != null && ViewCompat.canScrollVertically(mTarget, 1);
    }

    private float getMaxDistancePixels() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DIMEN_PULL_MAX_DISTANCE_DP,
                getDisplayMatrics());
    }

    private DisplayMetrics getDisplayMatrics() {
        return getContext().getResources().getDisplayMetrics();
    }

    private float getTriggerDistancePixels() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DIMEN_PULL_TRIGGER_DISTANCE_DP,
                getDisplayMatrics());
    }

    public interface SwipeListener {

        void onSwipeDown(float offsetFraction);

        void onSwipeUp(float offsetFraction);

        void onTriggerDown();

        void onTriggerUp();
    }
}
