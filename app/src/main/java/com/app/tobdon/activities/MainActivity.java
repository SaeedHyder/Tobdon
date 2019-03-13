package com.app.tobdon.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tobdon.R;
import com.app.tobdon.fragments.ActivityLogFragment;
import com.app.tobdon.fragments.HomeFragment;
import com.app.tobdon.fragments.MessagesFragment;
import com.app.tobdon.fragments.NotificationsFragment;
import com.app.tobdon.fragments.ProfileFragment;
import com.app.tobdon.fragments.SettingFragment;
import com.app.tobdon.fragments.SideMenuFragment;
import com.app.tobdon.fragments.TutorialFragment;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.global.SideMenuChooser;
import com.app.tobdon.global.SideMenuDirection;
import com.app.tobdon.helpers.ScreenHelper;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.interfaces.ImageSetter;
import com.app.tobdon.residemenu.ResideMenu;
import com.app.tobdon.ui.views.TitleBar;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ChosenVideo;
import com.kbeanie.imagechooser.api.ChosenVideos;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.api.VideoChooserListener;
import com.kbeanie.imagechooser.api.VideoChooserManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends DockActivity implements OnClickListener, ImageChooserListener, VideoChooserListener {
    public TitleBar titleBar;
    @BindView(R.id.sideMneuFragmentContainer)
    public FrameLayout sideMneuFragmentContainer;
    @BindView(R.id.header_main)
    TitleBar header_main;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private MainActivity mContext;
    private boolean loading;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private String address = "";
    private String country = "";

    private ResideMenu resideMenu;

    private float lastTranslate = 0.0f;

    private String sideMenuType;
    private String sideMenuDirection;

    private ImageChooserManager imageChooserManager;
    private VideoChooserManager videoChooserManager;
    private int chooserType;
    private String filePath;
    private String originalFilePath;
    private String thumbnailFilePath;
    private String thumbnailSmallFilePath;
    private boolean isActivityResultOver = false;
    private ImageSetter imageSetter;
    private String TAG;

    public void setImageSetter(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);
        ButterKnife.bind(this);
        titleBar = header_main;
        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        sideMenuType = SideMenuChooser.DRAWER.getValue();
        sideMenuDirection = SideMenuDirection.LEFT.getValue();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        settingSideMenu(sideMenuType, sideMenuDirection);
        lockDrawer();
        setTabLayout();

        titleBar.setMenuButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sideMenuType.equals(SideMenuChooser.DRAWER.getValue()) && getDrawerLayout() != null) {
                    if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    resideMenu.openMenu(sideMenuDirection);
                }

            }
        });

        titleBar.setBackButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {

                    popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });

        titleBar.setNotificationButtonListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        });

        // if (savedInstanceState == null)
        initFragment();

    }


    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }

    private void settingSideMenu(String type, String direction) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {


            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams((int) getResources().getDimension(R.dimen.x300), (int) DrawerLayout.LayoutParams.MATCH_PARENT);


            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
                params.gravity = Gravity.LEFT;
                sideMneuFragmentContainer.setLayoutParams(params);
            } else {
                params.gravity = Gravity.RIGHT;
                sideMneuFragmentContainer.setLayoutParams(params);
            }
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            sideMenuFragment = SideMenuFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(getSideMenuFrameLayoutId(), sideMenuFragment).commit();

            drawerLayout.closeDrawers();
        } else {
            resideMenu = new ResideMenu(this);
            resideMenu.attachToActivity(this);
            resideMenu.setMenuListener(getMenuListener());
            resideMenu.setScaleValue(0.52f);

            setMenuItemDirection(direction);
        }
    }

    private void setMenuItemDirection(String direction) {

        if (direction.equals(SideMenuDirection.LEFT.getValue())) {

            SideMenuFragment leftSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(leftSideMenuFragment, "LeftSideMenuFragment", direction);

        } else if (direction.equals(SideMenuDirection.RIGHT.getValue())) {

            SideMenuFragment rightSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);

        }

    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }


    public void initFragment() {
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        if (prefHelper.isLogin()) {
            replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
        } else {
            replaceDockableFragment(TutorialFragment.newInstance(), "TutorialFragment");
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(getDockFrameLayoutId());
                    if (currFrag != null) {
                        currFrag.fragmentResume();
                    }
                }
            }
        };

        return result;
    }

    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBar != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    @Override
    public void onBackPressed() {
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }

    @Override
    public void onClick(View view) {

    }

    private void notImplemented() {
        UIHelper.showLongToastInCenter(this, "Coming Soon");
    }

    public void lockDrawer() {
        try {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void closeDrawer() {
        drawerLayout.closeDrawers();

    }

    public void releaseDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public String getResString(int id) {
        return getDockActivity().getResources().getString(id);
    }


    private void setTabLayout() {

        if (tabLayout != null) {
            tabLayout.removeAllTabs();


            TextView tabOne = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabOne.setText(getResString(R.string.home));
            tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home, 0, 0);
            tabOne.setTextColor(getDockActivity().getResources().getColor(R.color.app_brown));
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabOne));

            TextView tabTwo = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabTwo.setText(getResString(R.string.activity));
            tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.activity1, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabTwo));

            TextView tabThree = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabThree.setText(getResString(R.string.trending));
            tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.trending1, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabThree));

            TextView tabFour = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabFour.setText(getResString(R.string.profile));
            tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile1, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabFour));

            TextView tabFive = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabFive.setText(getResString(R.string.settings));
            tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.settings1, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabFive));


            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            setData(tab);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    TextView txtTab = (TextView) tabLayout.getTabAt(tab.getPosition()).getCustomView();
                    txtTab.setTextColor(getDockActivity().getResources().getColor(R.color.app_brown));

                    if (tab.getPosition() == 0) {
                        txtTab.setText(getResString(R.string.home));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 1) {
                        txtTab.setText(getResString(R.string.activity));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.activity, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 2) {
                        txtTab.setText(getResString(R.string.trending));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.trending, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 3) {
                        txtTab.setText(getResString(R.string.profile));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 4) {
                        txtTab.setText(getResString(R.string.settings));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.settings, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    }

                    setData(tab);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                    TextView txtTab = (TextView) tabLayout.getTabAt(tab.getPosition()).getCustomView();
                    txtTab.setTextColor(getDockActivity().getResources().getColor(R.color.tab_text_color));
                    if (tab.getPosition() == 0) {
                        txtTab.setText(getResString(R.string.home));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home1, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 1) {
                        txtTab.setText(getResString(R.string.activity));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.activity1, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 2) {
                        txtTab.setText(getResString(R.string.trending));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.trending1, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 3) {
                        txtTab.setText(getResString(R.string.profile));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile1, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 4) {
                        txtTab.setText(getResString(R.string.settings));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.settings1, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    private void setData(TabLayout.Tab tab) {

        if (tab.getPosition() == 0) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
        } else if (tab.getPosition() == 1) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(ActivityLogFragment.newInstance(), "ActivityLogFragment");
        } else if (tab.getPosition() == 2) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(MessagesFragment.newInstance(), "MessagesFragment");

        } else if (tab.getPosition() == 3) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(), "ProfileFragment");

        } else if (tab.getPosition() == 4) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), "SettingFragment");

        }
    }

    public void showTabLayout() {
        tabLayout.setVisibility(View.VISIBLE);

    }

    public void hideTabLayout() {
        tabLayout.setVisibility(View.GONE);
    }

    public void replaceFragment(Fragment frag) {
        FragmentManager manager;
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //  transaction.add(R.id.fragmentContainer, frag);
        transaction.addToBackStack(manager.getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();


    }

    public String getCurrentAddress(double lat, double lng) {
        try {


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            }

            if (addresses.size() > 0) {
                country = addresses.get(0).getCountryName();
            }

            return address + ", " + country;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        }
        if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_CAPTURE_VIDEO || requestCode == ChooserType.REQUEST_PICK_VIDEO)) {
            if (videoChooserManager == null) {
                reinitializeVideoChooser();
            }
            videoChooserManager.submit(requestCode, data);
        }

    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, false);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    private void reinitializeVideoChooser() {
        videoChooserManager = new VideoChooserManager(this, chooserType, true);
        videoChooserManager.setVideoChooserListener(this);
        videoChooserManager.reinitialize(filePath);
    }

    public void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                isActivityResultOver = true;
                originalFilePath = image.getFilePathOriginal();
                thumbnailFilePath = image.getFileThumbnail();
                thumbnailSmallFilePath = image.getFileThumbnailSmall();

                if (image != null) {
                    imageSetter.setImage(originalFilePath);
                }
            }
        });

    }

    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "OnError: " + reason);
                Toast.makeText(MainActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onImagesChosen(final ChosenImages images) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "On Images Chosen: " + images.size());
                onImageChosen(images.getImage(0));
            }
        });

    }

    public void captureVideo() {
        chooserType = ChooserType.REQUEST_CAPTURE_VIDEO;
        videoChooserManager = new VideoChooserManager(this,
                ChooserType.REQUEST_CAPTURE_VIDEO);
        videoChooserManager.setVideoChooserListener(this);
        try {
            filePath = videoChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pickVideo(View view) {
        chooserType = ChooserType.REQUEST_PICK_VIDEO;
        videoChooserManager = new VideoChooserManager(this,
                ChooserType.REQUEST_PICK_VIDEO);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        videoChooserManager.setExtras(bundle);
        videoChooserManager.setVideoChooserListener(this);
        try {
            videoChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVideoChosen(final ChosenVideo video) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (video != null) {
                    imageSetter.setVideo(video.getVideoFilePath(), video.getThumbnailPath());
                }
            }
        });
    }


    @Override
    public void onVideosChosen(final ChosenVideos videos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(getClass().getName(), "run: Videos Chosen: " + videos.size());
                onVideoChosen(videos.getImage(0));
            }
        });
    }

}
