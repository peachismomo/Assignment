package sg.edu.np.s10179055.says;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    int tabCounts;

    public PagerController(FragmentManager fm, int tabCounts) {
        super(fm);
        this.tabCounts = tabCounts;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new profileFragment();
            case 1:
                return new foodPlacesFragment();
            case 2:
                return new mapsFragment();
            case 3:
                return new reportingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCounts;
    }
}
