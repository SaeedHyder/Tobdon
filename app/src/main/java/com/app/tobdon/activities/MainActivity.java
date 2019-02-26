package com.app.tobdon.activities;

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

import com.app.tobdon.R;
import com.app.tobdon.fragments.HomeFragment;
import com.app.tobdon.fragments.NotificationsFragment;
import com.app.tobdon.fragments.SideMenuFragment;
import com.app.tobdon.fragments.TutorialFragment;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.global.SideMenuChooser;
import com.app.tobdon.global.SideMenuDirection;
import com.app.tobdon.helpers.ScreenHelper;
import com.app.tobdon.helpers.UIHelper;
import com.app.tobdon.residemenu.ResideMenu;
import com.app.tobdon.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends DockActivity implements OnClickListener {
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

    private ResideMenu resideMenu;

    private float lastTranslate = 0.0f;

    private String sideMenuType;
    private String sideMenuDirection;

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
            tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like4, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabOne));

            TextView tabTwo = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabTwo.setText(getResString(R.string.activity));
            tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabTwo));

            TextView tabThree = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabThree.setText(getResString(R.string.trending));
            tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabThree));

            TextView tabFour = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabFour.setText(getResString(R.string.profile));
            tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabFour));

            TextView tabFive = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.custom_tab, null).findViewById(R.id.tabText);
            tabFive.setText(getResString(R.string.settings));
            tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabFive));


           /* tabLayout.addTab(tabLayout.newTab().setText(getResString(R.string.home)).setIcon(R.drawable.like4));
            tabLayout.addTab(tabLayout.newTab().setText(getResString(R.string.activity)).setIcon(R.drawable.hotelico));
            tabLayout.addTab(tabLayout.newTab().setText(getResString(R.string.trending)).setIcon(R.drawable.like3));
            tabLayout.addTab(tabLayout.newTab().setText(getResString(R.string.profile)).setIcon(R.drawable.hotelico));
            tabLayout.addTab(tabLayout.newTab().setText(getResString(R.string.settings)).setIcon(R.drawable.like3));*/


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
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like4, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 1) {
                        txtTab.setText(getResString(R.string.activity));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like4, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 2) {
                        txtTab.setText(getResString(R.string.trending));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like4, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 3) {
                        txtTab.setText(getResString(R.string.profile));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like4, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 4) {
                        txtTab.setText(getResString(R.string.settings));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like4, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    }

                   /* if (tab.getPosition() == 0) {
                        tab.setIcon(R.drawable.like4);
                    } else if (tab.getPosition() == 1) {
                        tab.setIcon(R.drawable.like4);
                    } else if (tab.getPosition() == 2) {
                        tab.setIcon(R.drawable.like4);
                        tab.setIcon(R.drawable.like4);
                    } else if (tab.getPosition() == 3) {
                        tab.setIcon(R.drawable.like4);
                    } else if (tab.getPosition() == 4) {
                        tab.setIcon(R.drawable.like4);
                           tabLayout.setTabTextColors(getDockActivity().getResources().getColor(R.color.tab_text_color), getDockActivity().getResources().getColor(R.color.app_brown));
                    }*/

                    setData(tab);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                    TextView txtTab = (TextView) tabLayout.getTabAt(tab.getPosition()).getCustomView();
                    txtTab.setTextColor(getDockActivity().getResources().getColor(R.color.tab_text_color));
                    if (tab.getPosition() == 0) {
                        txtTab.setText(getResString(R.string.home));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 1) {
                        txtTab.setText(getResString(R.string.activity));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 2) {
                        txtTab.setText(getResString(R.string.trending));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 3) {
                        txtTab.setText(getResString(R.string.profile));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    } else if (tab.getPosition() == 4) {
                        txtTab.setText(getResString(R.string.settings));
                        txtTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like3, 0, 0);
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(txtTab);
                    }
                   /* if (tab.getPosition() == 0) {
                        tab.setIcon(R.drawable.like3);
                    } else if (tab.getPosition() == 1) {
                        tab.setIcon(R.drawable.like3);
                    } else if (tab.getPosition() == 2) {
                        tab.setIcon(R.drawable.like3);
                    } else if (tab.getPosition() == 3) {
                        tab.setIcon(R.drawable.like3);
                    } else if (tab.getPosition() == 4) {
                        tab.setIcon(R.drawable.like3);
                    }
                      tabLayout.setTabTextColors(getDockActivity().getResources().getColor(R.color.tab_text_color), getDockActivity().getResources().getColor(R.color.app_brown));*/
                     }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    private void setData(TabLayout.Tab tab) {

        if (tab.getPosition() == 0) {

        } else if (tab.getPosition() == 1) {

        } else if (tab.getPosition() == 2) {

        } else if (tab.getPosition() == 3) {

        } else if (tab.getPosition() == 4) {

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


}
