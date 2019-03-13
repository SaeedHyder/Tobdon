package com.app.tobdon.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.MediaBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileMediaFragment extends BaseFragment implements RecyclerClickListner {
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rv_Media)
    CustomRecyclerView rvMedia;
    Unbinder unbinder;

    public static ProfileMediaFragment newInstance() {
        Bundle args = new Bundle();

        ProfileMediaFragment fragment = new ProfileMediaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_media, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMediaData();
    }


    private void setMediaData() {

        ArrayList<String> collection = new ArrayList<>();
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");

        rvMedia.BindRecyclerView(new MediaBinder(getDockActivity(), prefHelper, this), collection,
                new GridLayoutManager(getDockActivity(), 3)
                , new DefaultItemAnimator());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onClick(Object entity, int position) {

    }
    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().showTabLayout();
    }

}
