package com.shamanland.test.nestedfragmentsissue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewPagerFragment extends Fragment {
    private TextView mTextView;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.f_pager, container, false);

        mTextView = (TextView) result.findViewById(R.id.text);
        mTextView.setText("ViewPagerFragment");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("ViewPagerFragment");
            }
        });

        mViewPager = (ViewPager) result.findViewById(R.id.pager);
        mViewPager.setAdapter(new NestedAdapter(getChildFragmentManager(), inflater.getContext()));

        return result;
    }

    public static class NestedAdapter extends FragmentPagerAdapter {
        public static final int[] COLORS = {0xffcccc00, 0xff009966, 0xffffcc33};
        private final Context mContext;

        public NestedAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int i) {
            Bundle args = new Bundle();
            args.putInt("background.color", COLORS[i % COLORS.length]);
            args.putInt("request.code", i + 101);
            return Fragment.instantiate(mContext, TextViewFragment.class.getName(), args);
        }

        @Override
        public int getCount() {
            return 10;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mTextView.setText("ViewPagerFragment: unexpected onActivityResult: " + requestCode);
    }
}
