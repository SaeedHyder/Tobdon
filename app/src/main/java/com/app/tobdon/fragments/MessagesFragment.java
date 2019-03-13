package com.app.tobdon.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.MessageBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MessagesFragment extends BaseFragment implements RecyclerClickListner {
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rvMessage)
    CustomRecyclerView rvMessage;
    Unbinder unbinder;

    public static MessagesFragment newInstance() {
        Bundle args = new Bundle();

        MessagesFragment fragment = new MessagesFragment();
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
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMessageData();
    }

    private void setMessageData() {
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


        rvMessage.BindRecyclerView(new MessageBinder(getDockActivity(), prefHelper, this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
    //    titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.messages));
    }

    @Override
    public void onClick(Object entity, int position) {

        getDockActivity().replaceDockableFragment(ChatFragment.newInstance(),"ChatFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().showTabLayout();
    }
}
