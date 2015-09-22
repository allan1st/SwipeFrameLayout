package allan1st.github.io.swipeframelayout;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import allan1st.github.io.swipelayout.VerticalSwipeLayout;

/**
 * Created by yilun
 * on 22/09/15.
 */
public class TextFragment extends Fragment implements VerticalSwipeLayout.SwipeListener {

    VerticalSwipeLayout mSwipeLayout;

    ScrollView mScrollView;

    private TextView mTextHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeLayout = (VerticalSwipeLayout) view.findViewById(R.id.layout_swipe);
        mSwipeLayout.requestDisallowInterceptTouchEvent(true);
        mSwipeLayout.setSwipeListener(this);
        mSwipeLayout.setTranslationY(0);
        mTextHeader = (TextView) view.findViewById(R.id.text_header);
        mTextHeader.setText(getArguments().getString("TEXT"));
        mScrollView = (ScrollView) view.findViewById(R.id.view_scroll_text);
    }

    @Override
    public void onSwipeDown(float offsetFraction) {

    }

    @Override
    public void onSwipeUp(float offsetFraction) {

    }

    @Override
    public void onTriggerDown() {
        if (getActivity() instanceof MainActivity) {
            final ViewPager mPager = ((MainActivity) getActivity()).mPager;
            final int i = mPager.getCurrentItem();
            if (i < ((MainActivity) getActivity()).mAdapter.getCount() - 1) {
                mSwipeLayout.animate().translationY(-mSwipeLayout.getHeight())
                        .setDuration(350)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mSwipeLayout.setTranslationY(0);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
                mSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPager.setCurrentItem(i + 1, true);
                        mScrollView.scrollTo(0, 0);
                    }
                }, 300);
            }
        }

    }

    @Override
    public void onTriggerUp() {
        if (getActivity() instanceof MainActivity) {
            final ViewPager mPager = ((MainActivity) getActivity()).mPager;
            final int i = mPager.getCurrentItem();
            if (i > 0) {
                mSwipeLayout.animate().translationY(mSwipeLayout.getHeight())
                        .setDuration(350)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mSwipeLayout.setTranslationY(0);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .start();
                mSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPager.setCurrentItem(i - 1, true);
                        mScrollView.scrollTo(0, 0);
                    }
                }, 300);
            }
        }
    }
}
