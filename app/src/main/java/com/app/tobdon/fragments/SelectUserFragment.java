package com.app.tobdon.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.adapters.UserAdapter;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SelectUserFragment extends BaseFragment implements RecyclerClickListner {
    @BindView(R.id.cvSearch)
    CardView cvSearch;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rvUsers)
    CustomRecyclerView rvUsers;
    Unbinder unbinder;

    public static SelectUserFragment newInstance() {
        Bundle args = new Bundle();

        SelectUserFragment fragment = new SelectUserFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserData();
    }

    private void setUserData() {
        ArrayList<String> collection = new ArrayList<>();
        collection.add("Abc");
        collection.add("ghj");
        collection.add("bnv");
        collection.add("cdhj");
        collection.add("uhbg");
        collection.add("ljjf");
        collection.add("cfgh");
        collection.add("khu");
        collection.add("jbgg");

        Collections.sort(collection, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.toLowerCase().trim().compareTo(t1.toLowerCase().trim());
            }
        });

        UserAdapter userAdapter=new UserAdapter(collection, getDockActivity(),this);
        rvUsers.setLayoutManager(new LinearLayoutManager(getDockActivity()));
        rvUsers.setAdapter(userAdapter);


    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.select_user));
    }


    @Override
    public void onClick(Object entity, int position) {

    }
}
