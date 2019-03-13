package com.app.tobdon.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.ui.views.TitleBar;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AccountSettingFragment extends BaseFragment {
    @BindView(R.id.edt_username)
    TextInputEditText edtUsername;
    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.txt_language)
    TextView txtLanguage;
    @BindView(R.id.spLanguage)
    Spinner spLanguage;
    @BindView(R.id.btnYesLoginVer)
    RadioButton btnYesLoginVer;
    @BindView(R.id.btnNoLoginVer)
    RadioButton btnNoLoginVer;
    @BindView(R.id.btnYesPasswordVer)
    RadioButton btnYesPasswordVer;
    @BindView(R.id.btnNoPasswordVer)
    RadioButton btnNoPasswordVer;
    @BindView(R.id.txt_country)
    TextView txtCountry;
    @BindView(R.id.Countrypicker)
    CountryCodePicker Countrypicker;
    @BindView(R.id.btnYesVideos)
    RadioButton btnYesVideos;
    @BindView(R.id.btnNoVideos)
    RadioButton btnNoVideos;
    Unbinder unbinder;

    public static AccountSettingFragment newInstance() {
        Bundle args = new Bundle();

        AccountSettingFragment fragment = new AccountSettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLanguageSpinner();
    }

    private void setLanguageSpinner() {


        ArrayList<String> collection = new ArrayList<>();

        collection.add("Select Language");
        collection.add("English");
        collection.add("Arabic");

        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_1, collection) {
         //    ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_dropdown_item, collection) {
            @Override
            public boolean isEnabled(int position) {
                return !(position == 0);
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLanguage.setAdapter(interestAdapter);

        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.account_settings));
    }



    @OnClick({R.id.btnYesLoginVer, R.id.btnNoLoginVer, R.id.btnYesPasswordVer, R.id.btnNoPasswordVer, R.id.btnYesVideos, R.id.btnNoVideos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnYesLoginVer:
                break;
            case R.id.btnNoLoginVer:
                break;
            case R.id.btnYesPasswordVer:
                break;
            case R.id.btnNoPasswordVer:
                break;
            case R.id.btnYesVideos:
                break;
            case R.id.btnNoVideos:
                break;
        }
    }
}
