package com.app.tobdon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.ui.views.AnyEditTextView;
import com.app.tobdon.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContactUsFragment extends BaseFragment {
    @BindView(R.id.edtQuery)
    AnyEditTextView edtQuery;
    @BindView(R.id.SubmitButton)
    Button SubmitButton;
    Unbinder unbinder;

    public static ContactUsFragment newInstance() {
        Bundle args = new Bundle();

        ContactUsFragment fragment = new ContactUsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contactus, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listner();
    }

    private void listner() {

        edtQuery.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }


    @OnClick(R.id.SubmitButton)
    public void onViewClicked() {
        if (isvalidated()) {
            getDockActivity().popFragment();
        }

    }

    private boolean isvalidated() {
        if (edtQuery.getText().toString().trim().isEmpty() || edtQuery.getText().toString().trim().length() < 3) {
            edtQuery.setError(getString(R.string.enter_query));
            if (edtQuery.requestFocus()) {
                setEditTextFocus(edtQuery);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
            titleBar.setSubHeading(getResString(R.string.contact_us));
        }
}
