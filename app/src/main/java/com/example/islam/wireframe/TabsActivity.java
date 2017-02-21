package com.example.islam.wireframe;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islam.wireframe.Tabs.SlidingTabLayout;

public class TabsActivity extends AppCompatActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTaps = (SlidingTabLayout) findViewById(R.id.tab);
        mTaps.setDistributeEvenly(true);
      //  mTaps.setCustomTabView(R.layout.costume_tab_view, R.id.tabtext);
        mTaps.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTaps.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTaps.setViewPager(mPager);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;


            MyFragment myFragment = MyFragment.getInstance(position);


            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class MyFragment extends Fragment {

        public View layout = null;
        public TextView costumerName;
        TextView costumerId;
        TextView shoppingId;
        TextView costumerAddress;
        TextView userId;
        TextView phone;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            Bundle bundle = getArguments();
            int i = bundle.getInt("position");


            switch (i) {
                case 0:
                    layout = inflater.inflate(R.layout.first_tab, container, false);
                    costumerName = (TextView) layout.findViewById(R.id.detailsName);
                    costumerId = (TextView) layout.findViewById(R.id.detailsCustomerCode);
                    shoppingId = (TextView) layout.findViewById(R.id.detailsChappingCoder);
                    costumerAddress = (TextView) layout.findViewById(R.id.detailsAddress);
                    userId = (TextView) layout.findViewById(R.id.detailsDelivaryCode);
                    phone = (TextView) layout.findViewById(R.id.detailsPhone);

                    break;
                case 1:
                    break;
            }
            return layout;
        }
    }
}
