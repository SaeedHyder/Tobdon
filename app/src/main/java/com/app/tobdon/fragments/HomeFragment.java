package com.app.tobdon.fragments;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.helpers.DialogHelper;
import com.app.tobdon.interfaces.FeedInterface;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.FeedBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment implements FeedInterface,RecyclerClickListner {


    @BindView(R.id.rv_homeFeeds)
    CustomRecyclerView rvHomeFeeds;
    Unbinder unbinder;
    @BindView(R.id.addPost)
    FloatingActionButton addPost;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().showTabLayout();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFeedData();
        btnListner();

    }

    private void btnListner() {

        rvHomeFeeds.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0 && addPost.isShown())
                {
                    addPost.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    addPost.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().replaceDockableFragment(CreatePostFragment.newInstance(),"CreatePostFragment");
            }
        });
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

        rvHomeFeeds.BindRecyclerView(new FeedBinder(getDockActivity(), prefHelper, this,this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showNotificationButton();
        titleBar.setSubHeading("Home");
    }



    @OnClick(R.id.addPost)
    public void onViewClicked() {
    }


    @Override
    public void onLikeClick(Object entity, int position) {
        getDockActivity().replaceDockableFragment(SelectUserFragment.newInstance(),"SelectUserFragment");
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
    public void onClick(Object entity, int position) {

    }
}

