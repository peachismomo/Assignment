package sg.edu.np.s10179055.says;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout mTabLayout;
    TabItem profileTab, foodPlacesTab, reportingTab, mapsTab;
    ViewPager mPager;
    PagerController mPagerController;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Member");

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

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String allUsersKey = "";
                int numberOfUsers = 0;
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    allUsersKey = data.getKey()+",";
                    numberOfUsers++;
                }
                SharedPreferences.Editor editor = getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
                editor.putString("AllUsers", allUsersKey);

                editor.putInt("NumberOfUsers",numberOfUsers);
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
