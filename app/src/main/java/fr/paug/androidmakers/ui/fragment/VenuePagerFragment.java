package fr.paug.androidmakers.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.paug.androidmakers.R;
import fr.paug.androidmakers.ui.adapter.VenuePagerAdapter;

/**
 * Created by benju on 30/03/2017.
 */

public class VenuePagerFragment extends Fragment {

    @BindView(R.id.venue_viewpager) ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venue_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new VenuePagerAdapter(getContext(), getChildFragmentManager()));
    }
}