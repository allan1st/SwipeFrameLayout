package allan1st.github.io.swipeframelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yilun
 * on 22/09/15.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TextFragment textFragment = new TextFragment();
        Bundle data = new Bundle();
        data.putString("TEXT", "Page: " + position);
        textFragment.setArguments(data);
        return textFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
