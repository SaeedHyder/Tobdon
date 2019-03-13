package com.app.tobdon.fragments;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.ui.views.TitleBar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PrivacySettingsFragment extends BaseFragment {
    public static PrivacySettingsFragment newInstance() {
        Bundle args = new Bundle();

        PrivacySettingsFragment fragment = new PrivacySettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_privacy_settings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.privacy_settings));
    }
}
