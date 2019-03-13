package com.app.tobdon.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.interfaces.FeedInterface;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.FeedBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfilePostsFragment extends BaseFragment implements FeedInterface,RecyclerClickListner {
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rv_Posts)
    CustomRecyclerView rvPosts;
    Unbinder unbinder;

    public static ProfilePostsFragment newInstance() {
        Bundle args = new Bundle();

        ProfilePostsFragment fragment = new ProfilePostsFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_posts, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFeedData();
    }

    private void setFeedData() {

        ArrayList<String> collection = new ArrayList<>();
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");

        rvPosts.BindRecyclerView(new FeedBinder(getDockActivity(), prefHelper, this,this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onLikeClick(Object entity, int position) {

    }

    @Override
    public void onCommentClick(Object entity, int position) {
        getDockActivity().replaceDockableFragment(PostDetailFragment.newInstance(),"PostDetailFragment");
    }

    @Override
    public void onSharetClick(Object entity, int position) {

    }

    @Override
    public void onPopuptClick(Object entity, int position, View view) {

        LayoutInflater layoutInflater = (LayoutInflater) getDockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup_window_dialoge, null);


        AnyTextView hidePost = (AnyTextView) customView.findViewById(R.id.txt_HidePost);
        AnyTextView hideAllPost = (AnyTextView) customView.findViewById(R.id.txt_HideAllPost);
        AnyTextView turnOffNotificaitons = (AnyTextView) customView.findViewById(R.id.txt_turnOffNotificaitons);

        final PopupWindow popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(view);


    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().showTabLayout();
    }


    @Override
    public void onClick(Object entity, int position) {

    }
}
