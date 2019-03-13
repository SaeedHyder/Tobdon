package com.app.tobdon.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.tobdon.R;
import com.app.tobdon.entities.AttachmentEnt;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.helpers.CameraHelper;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.interfaces.AttachmentInterface;
import com.app.tobdon.interfaces.ImageSetter;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.AttachmentBinder;
import com.app.tobdon.ui.binders.ChatBinder;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;
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
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static com.app.tobdon.global.AppConstants.PHOTO;
import static com.app.tobdon.global.AppConstants.VIDEO;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO;

public class ChatFragment extends BaseFragment implements RecyclerClickListner, AttachmentInterface, ImageSetter {
    @BindView(R.id.rv_chat)
    CustomRecyclerView rvChat;
    Unbinder unbinder;
    @BindView(R.id.edtMessage)
    EmojiconEditText edtMessage;
    @BindView(R.id.btnSend)
    ImageView btnSend;
    @BindView(R.id.btnEmoji)
    LinearLayout btnEmoji;
    @BindView(R.id.btnCamera)
    LinearLayout btnCamera;
    @BindView(R.id.btnPhoto)
    LinearLayout btnPhoto;
    @BindView(R.id.btnVideo)
    LinearLayout btnVideo;
    @BindView(R.id.mainFrameLayout)
    LinearLayout mainFrameLayout;
    @BindView(R.id.ivEmojiBtn)
    ImageView ivEmojiBtn;
    @BindView(R.id.rv_attachments)
    CustomRecyclerView rvAttachments;

    private final int REQUEST_CODE_VIDEO = 2154;
    private int MAX_ATTACHMENT_COUNT = 5;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<String> videoPaths = new ArrayList<>();
    private ArrayList<AttachmentEnt> allAttachments = new ArrayList<>();


    public static ChatFragment newInstance() {
        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setChatData();
        setAttachmentData();
        setKeyboardListner();
        getMainActivity().setImageSetter(this);

    }

    private void setKeyboardListner() {
        EmojIconActions emojIcon;
        emojIcon = new EmojIconActions(getDockActivity(), mainFrameLayout, edtMessage, ivEmojiBtn);
        emojIcon.ShowEmojIcon();
        emojIcon.addEmojiconEditTextList(edtMessage);
    }

    private void setTouchListner(final int position) {
        edtMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvChat.scrollToPosition(position);
                        }
                    }, 1000);
                }
                return false;
            }
        });

    }

    private void setChatData() {
        ArrayList<String> collection = new ArrayList<>();
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");


        rvChat.BindRecyclerView(new ChatBinder(getDockActivity(), prefHelper, this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());

        rvChat.scrollToPosition(collection.size() - 1);
        setTouchListner(collection.size() - 1);

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
        titleBar.setSubHeading(getResString(R.string.chat));
    }


    @Override
    public void onClick(Object entity, int position) {

    }

    //, R.id.btnEmoji
    @OnClick({R.id.btnSend, R.id.btnCamera, R.id.btnPhoto, R.id.btnVideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                edtMessage.getText().clear();
                UIHelper.showShortToastInCenter(getDockActivity(), "Message send successfully");
                break;
            case R.id.btnCamera:
             requestCameraPermission();
                break;
            case R.id.btnPhoto:
                requestPhotoPermission();
                break;
            case R.id.btnVideo:
                requestVideoPermission();
                break;
        }
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
                            requestPhotoPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestPhotoPermission();
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
                            CameraHelper.uploadPhotoDialog(getMainActivity());
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

    private void requestPhotoPermission() {
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
                            requestPhotoPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestPhotoPermission();
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
                    .setActivityTitle("Please select photos")
                    .enableVideoPicker(false)
                    .addFileSupport("Images", images)
                    .enableCameraSupport(false)
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
                    .setActivityTitle("Please select videos")
                    .enableVideoPicker(true)
                    .addFileSupport("Images", images)
                    .enableCameraSupport(false)
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

            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    rvAttachments.setVisibility(View.VISIBLE);
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                    updateAttachmentData();


                }
                break;

            case REQUEST_CODE_VIDEO:
                if (resultCode == RESULT_OK && data != null) {
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
    public void onDestroy() {
        super.onDestroy();
        getDockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public void setImage(String imagePath) {

        if (photoPaths.size() <= 0)
            photoPaths = new ArrayList<>();
        photoPaths.add(imagePath);

        updateAttachmentData();

    }

    @Override
    public void setVideo(String videoPath, String VideoThumbail) {

        if (videoPaths.size() <= 0)
            videoPaths = new ArrayList<>();
        videoPaths.add(videoPath);

        updateAttachmentData();

    }
}
