package sg.edu.np.s10179055.says;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class student extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout mTabLayout;
    TabItem profileTab, foodPlacesTab, reportingTab, mapsTab;
    ViewPager mPager;
    PagerController mPagerController;
    GoogleLocation currentLocation = new GoogleLocation();
    /*Account currentAccount = new Account();
    private Handler mHandler = new Handler();
    private Runnable updateLocation = new Runnable() {
        @Override
        public void run() {
            currentLocation.getLoca2(student.this, getBaseContext(), currentAccount);
            mHandler.postDelayed(this, 10000);
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        mToolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.tabLayout);
        profileTab = findViewById(R.id.profileTab);
        foodPlacesTab = findViewById(R.id.foodPlacesTab);
        reportingTab = findViewById(R.id.reportTab);
        mapsTab = findViewById(R.id.userMap);
        mPager = findViewById(R.id.viewPager);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("No Problemo");

        mPagerController = new PagerController(getSupportFragmentManager(), mTabLayout.getTabCount());
        mPager.setAdapter(mPagerController);

        /*updateLocation.run();*/

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
