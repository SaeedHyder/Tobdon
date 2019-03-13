package com.app.tobdon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.helpers.DialogHelper;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends BaseFragment {
    @BindView(R.id.btnNotifications)
    Switch btnNotifications;
    @BindView(R.id.btnAccountSetting)
    AnyTextView btnAccountSetting;
    @BindView(R.id.btnPrivacySetting)
    AnyTextView btnPrivacySetting;
    @BindView(R.id.btnPrivacyPolicy)
    AnyTextView btnPrivacyPolicy;
    @BindView(R.id.btnTermsCondition)
    AnyTextView btnTermsCondition;
    @BindView(R.id.btnContactUs)
    AnyTextView btnContactUs;
    @BindView(R.id.btnAboutUs)
    AnyTextView btnAboutUs;
    @BindView(R.id.btnLogout)
    AnyTextView btnLogout;
    Unbinder unbinder;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
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
    public void onResume() {
        super.onResume();
        getMainActivity().showTabLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
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
        titleBar.setSubHeading(getResString(R.string.settings));
    }

    @OnClick({R.id.btnAccountSetting, R.id.btnPrivacySetting, R.id.btnPrivacyPolicy, R.id.btnTermsCondition, R.id.btnContactUs, R.id.btnAboutUs, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAccountSetting:
                getDockActivity().replaceDockableFragment(AccountSettingFragment.newInstance(), "AccountSettingFragment");
                break;
            case R.id.btnPrivacySetting:
                getDockActivity().replaceDockableFragment(PrivacySettingsFragment.newInstance(), "PrivacySettingsFragment");
                break;
            case R.id.btnPrivacyPolicy:
                getDockActivity().replaceDockableFragment(CmsFragment.newInstance(getResString(R.string.PrivacyPolicy)), "CmsFragment");
                break;
            case R.id.btnTermsCondition:
                getDockActivity().replaceDockableFragment(CmsFragment.newInstance(getResString(R.string.TermsCondition)), "CmsFragment");
                break;
            case R.id.btnContactUs:
                getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(), "ContactUsFragment");
                break;
            case R.id.btnAboutUs:
                getDockActivity().replaceDockableFragment(CmsFragment.newInstance(getResString(R.string.AboutUs)), "CmsFragment");
                break;
            case R.id.btnLogout:
                DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                dialogHelper.initlogout(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prefHelper.setLoginStatus(false);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        dialogHelper.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogHelper.hideDialog();
                    }
                });
                dialogHelper.showDialog();
                break;
        }
    }
}
