package com.app.tobdon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CmsFragment extends BaseFragment {
    @BindView(R.id.txt_text)
    AnyTextView txtText;
    Unbinder unbinder;

    private static String title = "";

    public static CmsFragment newInstance(String text) {
        Bundle args = new Bundle();
        title=text;
        CmsFragment fragment = new CmsFragment();
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
        View view = inflater.inflate(R.layout.fragment_cms, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtText.setText(getResString(R.string.lorem_ipsum));

        if(title.equals(getResString(R.string.TermsCondition))){

        //    serviceHelper.enqueueCall(headerWebService.Cms("3"), CmsService);

        }else if(title.equals(getResString(R.string.PrivacyPolicy))){
         //   serviceHelper.enqueueCall(headerWebService.Cms("2"), CmsService);

        }else if(title.equals(getResString(R.string.AboutUs))){
        //    serviceHelper.enqueueCall(headerWebService.Cms("1"), CmsService);
        }

    }


}
