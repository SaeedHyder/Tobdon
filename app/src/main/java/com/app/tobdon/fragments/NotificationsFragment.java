package com.app.tobdon.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.app.tobdon.R;
import com.app.tobdon.entities.NotificationEnt;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.interfaces.PopupClickListner;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.NotificationBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 9/15/2017.
 */

public class NotificationsFragment extends BaseFragment implements RecyclerClickListner,PopupClickListner {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.txtJustNow)
    AnyTextView txtJustNow;
    @BindView(R.id.rv_notification_now)
    CustomRecyclerView rvNotificationNow;
    @BindView(R.id.txtEarlier)
    AnyTextView txtEarlier;
    @BindView(R.id.rv_notification_earlier)
    CustomRecyclerView rvNotificationEarlier;
    Unbinder unbinder;

    private ArrayList<NotificationEnt> collection;


    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setNotificationNowData();
        setNotificationEarlierData();
    }

    private void setNotificationEarlierData() {
        collection = new ArrayList<>();
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());


        rvNotificationEarlier.BindRecyclerView(new NotificationBinder(getDockActivity(), prefHelper, this,this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());

    }

    private void setNotificationNowData() {
        collection = new ArrayList<>();
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());
        collection.add(new NotificationEnt());


        rvNotificationNow.BindRecyclerView(new NotificationBinder(getDockActivity(), prefHelper, this,this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.notification));
    }




    @Override
    public void onClick(Object entity, int position) {

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
}
