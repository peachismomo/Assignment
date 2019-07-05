package sg.edu.np.s10179055.says;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class member extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout mTabLayout;
    TabItem profileTab;
    TabItem foodPlacesTab;
    TabItem reportingTab;
    ViewPager mPager;
    PagerController mPagerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("No Problem");

        mTabLayout = findViewById(R.id.tabLayout);
        profileTab = findViewById(R.id.profileTab);
        foodPlacesTab = findViewById(R.id.foodPlacesTab);
        reportingTab = findViewById(R.id.reportTab);
        mPager = findViewById(R.id.viewPager);

        mPagerController = new PagerController(getSupportFragmentManager(), mTabLayout.getTabCount());
        mPager.setAdapter(mPagerController);

        //Open tab via click.
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }
}
