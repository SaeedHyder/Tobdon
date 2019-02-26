package com.app.tobdon.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.PinEntryEditText;
import com.app.tobdon.ui.views.TitleBar;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VerificationFragment extends BaseFragment {
    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txtPinEntry;
    @BindView(R.id.btnResendCode)
    AnyTextView btnResendCode;
    @BindView(R.id.tv_counter)
    AnyTextView tvCounter;
    @BindView(R.id.countDown)
    LinearLayout countDown;
    Unbinder unbinder;

    private static boolean isSignup = false;
    private CountDownTimer timer;

    public static VerificationFragment newInstance(boolean isSignupKey) {
        Bundle args = new Bundle();
        isSignup = isSignupKey;
        VerificationFragment fragment = new VerificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_verification, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        counter();
        textWatcher();
    }

    private void textWatcher() {
        txtPinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() == 4) {
                  /*  if (timer != null) {
                        timer.cancel();

                    if (isSignup) {

                    } else {

                    }
                    }*/
                }
            }
        });
    }

    public void counter() {
        timer = new CountDownTimer(225000, 1000) {

            public void onTick(long millisUntilFinished) {

                String text = String.format(Locale.getDefault(), "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                if (tvCounter != null) {
                    tvCounter.setText(text + "");
                    tvCounter.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            public void onFinish() {
                if (tvCounter != null) {
                    countDown.setVisibility(View.GONE);
                    btnResendCode.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.verification));
        titleBar.showVerificationTick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /*  if (timer != null) {
                        timer.cancel();
                    }*/
                if (isvalidated()) {
                    if (isSignup) {
                        getDockActivity().popBackStackTillEntry(0);
                        prefHelper.setLoginStatus(true);
                        getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                    } else {
                        getDockActivity().popFragment();
                        getDockActivity().replaceDockableFragment(ResetPasswordFragment.newInstance(), "ResetPasswordFragment");
                    }
                }
            }
        });
    }


    @OnClick(R.id.btnResendCode)
    public void onViewClicked() {
        UIHelper.showShortToastInDialoge(getDockActivity(), "");
        counter();
        btnResendCode.setVisibility(View.GONE);
        countDown.setVisibility(View.VISIBLE);
    }

    private boolean isvalidated() {
        if (txtPinEntry.getText().toString().trim().isEmpty() || txtPinEntry.getText().toString().equals("")) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.enter_valid_pincode));
            return false;
        } else if (txtPinEntry.getText().toString().trim().length() < 4) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.enter_valid_pincode));
            return false;
        } else
            return true;

    }
}
