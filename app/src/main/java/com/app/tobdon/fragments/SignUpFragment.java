package com.app.tobdon.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.TitleBar;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SignUpFragment extends BaseFragment {
    @BindView(R.id.edt_fullname)
    TextInputEditText edtFullname;
    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_mobileNumber)
    TextInputEditText edtMobileNumber;
    @BindView(R.id.txt_country)
    TextView txtCountry;
    @BindView(R.id.Countrypicker)
    CountryCodePicker Countrypicker;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    TextInputEditText edtConfirmPassword;
    @BindView(R.id.VerifyButton)
    Button VerifyButton;
    @BindView(R.id.btnSignIn)
    AnyTextView btnSignIn;
    Unbinder unbinder;

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.sign_up));
    }

    private boolean isvalidated() {
        if (edtFullname.getText().toString().trim().isEmpty() || edtFullname.getText().toString().trim().length() < 3) {
            edtFullname.setError(getString(R.string.enter_fullname));
            if (edtFullname.requestFocus()) {
                setEditTextFocus(edtFullname);
            }
            return false;
        } else if (edtEmail.getText() == null || edtEmail.getText().toString().trim().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail);
            }
            return false;
        } else  if (edtMobileNumber.getText().toString().trim().isEmpty() || edtMobileNumber.getText().toString().trim().length() < 3) {
            edtMobileNumber.setError(getString(R.string.enter_phonenumber));
            if (edtMobileNumber.requestFocus()) {
                setEditTextFocus(edtMobileNumber);
            }
            return false;
        }  else if (edtPassword.getText().toString().trim().isEmpty()) {
            edtPassword.setError(getString(R.string.enter_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtPassword.getText().toString().trim().length() < 6) {
            edtPassword.setError(getString(R.string.passwordLength));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtConfirmPassword.getText().toString().trim().isEmpty()) {
            edtConfirmPassword.setError(getString(R.string.enter_password));
            if (edtConfirmPassword.requestFocus()) {
                setEditTextFocus(edtConfirmPassword);
            }
            return false;
        } else if (!edtConfirmPassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            edtConfirmPassword.setError(getString(R.string.confirm_password_error));
            if (edtConfirmPassword.requestFocus()) {
                setEditTextFocus(edtConfirmPassword);
            }
            return false;
        }else {
            return true;
        }

    }



    @OnClick({R.id.VerifyButton, R.id.btnSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.VerifyButton:
                if(isvalidated()){
                    getDockActivity().popFragment();
                   getDockActivity().replaceDockableFragment(VerificationFragment.newInstance(true),"VerificationFragment");
                }
                break;
            case R.id.btnSignIn:
                getDockActivity().popFragment();
                break;
        }
    }
}
