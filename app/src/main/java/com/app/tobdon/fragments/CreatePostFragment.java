package com.app.tobdon.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tobdon.R;
import com.app.tobdon.entities.AttachmentEnt;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.interfaces.AttachmentInterface;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.AttachmentBinder;
import com.app.tobdon.ui.binders.TrendingBinder;
import com.app.tobdon.ui.views.AnyEditTextView;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.app.tobdon.global.AppConstants.PHOTO;
import static com.app.tobdon.global.AppConstants.VIDEO;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO;

public class CreatePostFragment extends BaseFragment implements RecyclerClickListner, AttachmentInterface {
    @BindView(R.id.ivImage)
    CircleImageView ivImage;
    @BindView(R.id.btnAddPhoto)
    LinearLayout btnAddPhoto;
    @BindView(R.id.btnAddVideo)
    LinearLayout btnAddVideo;
    @BindView(R.id.txtTobeDon)
    AnyTextView txtTobeDon;
    @BindView(R.id.btnTobdon)
    LinearLayout btnTobdon;
    Unbinder unbinder;
    @BindView(R.id.edtWhatsNew)
    AnyEditTextView edtWhatsNew;
    @BindView(R.id.btnFollowing)
    Button btnFollowing;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.spInterest)
    Spinner spInterest;
    @BindView(R.id.btnMedium)
    Button btnMedium;
    @BindView(R.id.btnLow)
    Button btnLow;
    @BindView(R.id.btnEveryone)
    Button btnEveryone;
    @BindView(R.id.btnOnlyMe)
    Button btnOnlyMe;
    @BindView(R.id.btnHigh)
    Button btnHigh;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private final int REQUEST_CODE_VIDEO = 2154;
    @BindView(R.id.rv_attachments)
    CustomRecyclerView rvAttachments;
    @BindView(R.id.rvTrending)
    CustomRecyclerView rvTrending;
    @BindView(R.id.cvTrending)
    CardView cvTrending;
    private int MAX_ATTACHMENT_COUNT = 5;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<String> videoPaths = new ArrayList<>();
    private ArrayList<AttachmentEnt> allAttachments = new ArrayList<>();

    public static CreatePostFragment newInstance() {
        Bundle args = new Bundle();

        CreatePostFragment fragment = new CreatePostFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInterestSpinner();
        txtLocation.setSelected(true);
        setAttachmentData();
        setTrendingData();

    }

    private void setTrendingData() {
        ArrayList<String> trendingCollection = new ArrayList<>();
        trendingCollection.add("");
        trendingCollection.add("");
        trendingCollection.add("");

        rvTrending.BindRecyclerView(new TrendingBinder(getDockActivity(), prefHelper, this), trendingCollection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());
    }


    private void setAttachmentData() {

        allAttachments = new ArrayList<>();

        rvAttachments.BindRecyclerView(new AttachmentBinder(getDockActivity(), prefHelper, this), new ArrayList(),
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.create_post));
    }


    @OnClick({R.id.btnAddPhoto, R.id.btnAddVideo, R.id.btnTobdon, R.id.txtLocation, R.id.btnMedium, R.id.btnLow, R.id.btnEveryone, R.id.btnOnlyMe, R.id.btnHigh, R.id.btnFollowing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddPhoto:
                requestCameraPermission();
                break;
            case R.id.btnAddVideo:
                requestVideoPermission();
                break;
            case R.id.btnTobdon:
                cvTrending.setVisibility(View.VISIBLE);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                break;

            case R.id.txtLocation:
                requestLocationPermission();
                break;
            case R.id.btnHigh:
                selectButton(btnHigh, btnMedium, btnLow);
                break;
            case R.id.btnMedium:
                selectButton(btnMedium, btnHigh, btnLow);
                break;
            case R.id.btnLow:
                selectButton(btnLow, btnHigh, btnMedium);
                break;
            case R.id.btnEveryone:
                selectButton(btnEveryone, btnFollowing, btnOnlyMe);
                break;
            case R.id.btnFollowing:
                selectButton(btnFollowing, btnEveryone, btnOnlyMe);
                break;
            case R.id.btnOnlyMe:
                selectButton(btnOnlyMe, btnEveryone, btnFollowing);
                break;
        }
    }


    private void requestLocationPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {

                            openLocationSelector();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestLocationPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestLocationPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant LocationEnt Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void openLocationSelector() {

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(getDockActivity()), PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    private void setInterestSpinner() {

        ArrayList<String> collection = new ArrayList<>();

        collection.add("Select Interest");
        collection.add("Sports");
        collection.add("Gaming");
        collection.add("Movies");

        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, collection) {
            // ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_dropdown_item, collection) {
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
        spInterest.setAdapter(interestAdapter);

        spInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void selectButton(Button selectedBtn, Button unSelectedBtn1, Button unSelectedBtn2) {

        selectedBtn.setTextColor(getDockActivity().getResources().getColor(R.color.white));
        selectedBtn.setBackground(getDockActivity().getResources().getDrawable(R.drawable.rounded_brown_button));

        unSelectedBtn1.setTextColor(getDockActivity().getResources().getColor(R.color.app_brown));
        unSelectedBtn1.setBackground(getDockActivity().getResources().getDrawable(R.drawable.rounded_white_button));

        unSelectedBtn2.setTextColor(getDockActivity().getResources().getColor(R.color.app_brown));
        unSelectedBtn2.setBackground(getDockActivity().getResources().getDrawable(R.drawable.rounded_white_button));

    }

    private void requestVideoPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            onPickVideo();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestCameraPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestCameraPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Camera And Storage Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    private void requestCameraPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            onPickPhoto();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestCameraPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestCameraPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Camera And Storage Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    public void onPickPhoto() {

        String[] images = {".jpg,.png,.jpeg"};
        int maxCount = MAX_ATTACHMENT_COUNT - videoPaths.size();
        if ((photoPaths.size() + videoPaths.size()) == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(getDockActivity(), "Cannot select more than " + MAX_ATTACHMENT_COUNT + " items", Toast.LENGTH_SHORT).show();
        } else {
            FilePickerBuilder.getInstance()
                    .setMaxCount(maxCount)
                    .setSelectedFiles(photoPaths)
                    .setActivityTheme(R.style.LibAppTheme)
                    .setActivityTitle("Please select media")
                    .enableVideoPicker(false)
                    .addFileSupport("Images", images)
                    .enableCameraSupport(true)
                    .showGifs(false)
                    .showFolderView(false)
                    .enableSelectAll(false)
                    .enableImagePicker(true)
                    .setCameraPlaceholder(R.drawable.custom_camera)
                    .withOrientation(Orientation.UNSPECIFIED)
                    .pickPhoto(this, REQUEST_CODE_PHOTO);
        }

    }

    public void onPickVideo() {

        String[] images = {".mp4"};
        int maxCount = MAX_ATTACHMENT_COUNT - photoPaths.size();
        if ((photoPaths.size() + videoPaths.size()) == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(getDockActivity(), "Cannot select more than " + MAX_ATTACHMENT_COUNT + " items", Toast.LENGTH_SHORT).show();
        } else {
            FilePickerBuilder.getInstance()
                    .setMaxCount(maxCount)
                    .setSelectedFiles(videoPaths)
                    .setActivityTheme(R.style.LibAppTheme)
                    .setActivityTitle("Please select media")
                    .enableVideoPicker(true)
                    .addFileSupport("Images", images)
                    .enableCameraSupport(true)
                    .showGifs(false)
                    .showFolderView(false)
                    .enableSelectAll(false)
                    .enableImagePicker(false)
                    .setCameraPlaceholder(R.drawable.custom_camera)
                    .withOrientation(Orientation.UNSPECIFIED)
                    .pickPhoto(this, REQUEST_CODE_VIDEO);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    Place place = PlaceAutocomplete.getPlace(getDockActivity(), data);
                    if (place != null && getMainActivity() != null) {
                        txtLocation.setText(getMainActivity().getCurrentAddress(place.getLatLng().latitude, place.getLatLng().longitude));
                    }

                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(getDockActivity(), data);

                } else if (resultCode == RESULT_CANCELED) {

                }

                break;
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    rvAttachments.setVisibility(View.VISIBLE);
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                    updateAttachmentData();


                }
                break;

            case REQUEST_CODE_VIDEO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    rvAttachments.setVisibility(View.VISIBLE);
                    videoPaths = new ArrayList<>();
                    videoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                    updateAttachmentData();

                }

                break;

        }

    }

    private void updateAttachmentData() {


        if (photoPaths != null && videoPaths != null && (photoPaths.size() > 0 || videoPaths.size() > 0)) {
            allAttachments = new ArrayList<>();

            for (String item : photoPaths) {
                try {
                    allAttachments.add(new AttachmentEnt(PHOTO, new Compressor(getDockActivity()).compressToBitmap(new File(item)), item));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (String item : videoPaths) {
                allAttachments.add(new AttachmentEnt(VIDEO, item));
            }

            rvAttachments.clearList();
            rvAttachments.addAll(allAttachments);

        } else {
            rvAttachments.setVisibility(View.GONE);
        }

    }


    @Override
    public void onCrossClick(Object entity, int position) {

        AttachmentEnt attachmentEnt = (AttachmentEnt) entity;

        if (attachmentEnt.getType().equals(VIDEO)) {
            videoPaths.remove(attachmentEnt.getAttahcment());
        } else {
            photoPaths.remove(position);
        }
        updateAttachmentData();
    }

    @Override
    public void onClick(Object entity, int position) {

    }


}
