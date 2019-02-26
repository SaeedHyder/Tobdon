package com.app.tobdon.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.btnResetNow)
    AnyTextView btnResetNow;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.btnSignup)
    AnyTextView btnSignup;
    @BindView(R.id.fbButton)
    Button fbButton;
    @BindView(R.id.instaButton)
    Button instaButton;

    Unbinder unbinder;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);
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
        titleBar.hideTitleBar();
    }


    @OnClick({R.id.btnResetNow, R.id.loginButton, R.id.fbButton, R.id.instaButton,R.id.btnSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnResetNow:
                getDockActivity().replaceDockableFragment(ForgotPasswordFragment.newInstance(),"ForgotPasswordFragment");
                break;
            case R.id.loginButton:
                if(isvalidated()) {
                    prefHelper.setLoginStatus(true);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(),"HomeFragment");
                }
                break;

            case R.id.btnSignup:
                getDockActivity().replaceDockableFragment(SignUpFragment.newInstance(),"SignUpFragment");
                break;
            case R.id.fbButton:
                UIHelper.showShortToastInDialoge(getDockActivity(),getResString(R.string.will_be_implemented));
                break;
            case R.id.instaButton:
                UIHelper.showShortToastInDialoge(getDockActivity(),getResString(R.string.will_be_implemented));
                break;
        }
    }

    private boolean isvalidated() {
        if (edtEmail.getText() == null || edtEmail.getText().toString().trim().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getResString(R.string.enter_valid_email));
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail);
            }
            return false;
        } else if (edtPassword.getText().toString().trim().isEmpty()) {
            edtPassword.setError(getResString(R.string.enter_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else
            return true;

    }
}
