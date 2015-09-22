package allan1st.github.io.swipeframelayout;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yilun
 * on 22/09/15.
 */
public class MainActivity extends AppCompatActivity {

    public ViewPager mPager;

    public PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
    }
}
