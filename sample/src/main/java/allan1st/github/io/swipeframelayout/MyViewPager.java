package allan1st.github.io.swipeframelayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yilun
 * on 22/09/15.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
        initPageTransformer();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPageTransformer();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    private void initPageTransformer() {
        setPageTransformer(false, new PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setTranslationX(getWidth() * -position);
                page.setTranslationY(getHeight() * position);
                page.setAlpha(1 - Math.abs(position));
            }
        });
    }
}
