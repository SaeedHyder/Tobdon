package com.app.tobdon.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.tobdon.activities.DockActivity.KEY_FRAG_FIRST;

public class ProfileFragment extends BaseFragment {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.iv_profilePic)
    CircleImageView ivProfilePic;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScroll;
    @BindView(R.id.mainFrameLayout)
    CoordinatorLayout mainFrameLayout;
    Unbinder unbinder;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
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
    public void onResume() {
        super.onResume();
        getMainActivity().showTabLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTabLayout();
        tabLayoutListner();
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResString(R.string.profile));
    }

    private void setTabLayout() {

        if (tabLayout != null) {
            tabLayout.removeAllTabs();

            LinearLayout llTabPost = (LinearLayout) LayoutInflater.from(getDockActivity()).inflate(R.layout.profile_custom_tab, null).findViewById(R.id.llTab);
            TextView txtTitlePost = (TextView) llTabPost.getChildAt(1);
            TextView txtCountPost = (TextView) llTabPost.getChildAt(0);
            txtTitlePost.setText(getResString(R.string.posts));
            txtCountPost.setText("10");
            txtCountPost.setTextColor(getDockActivity().getResources().getColor(R.color.app_brown));
          //  txtTitlePost.setTypeface(txtTitlePost.getTypeface(), Typeface.BOLD);
            txtCountPost.setTypeface(txtCountPost.getTypeface(), Typeface.BOLD);
            tabLayout.addTab(tabLayout.newTab().setCustomView(llTabPost));

            LinearLayout llTabMedia = (LinearLayout) LayoutInflater.from(getDockActivity()).inflate(R.layout.profile_custom_tab, null).findViewById(R.id.llTab);
            TextView txtTitleMedia = (TextView) llTabMedia.getChildAt(1);
            TextView txtCountMedia = (TextView) llTabMedia.getChildAt(0);
            txtTitleMedia.setText(getResString(R.string.media));
            txtCountMedia.setText("36");
            tabLayout.addTab(tabLayout.newTab().setCustomView(llTabMedia));

            LinearLayout llTabFollowing = (LinearLayout) LayoutInflater.from(getDockActivity()).inflate(R.layout.profile_custom_tab, null).findViewById(R.id.llTab);
            TextView txtTitleFollowing = (TextView) llTabFollowing.getChildAt(1);
            TextView txtCountFollowing = (TextView) llTabFollowing.getChildAt(0);
            txtTitleFollowing.setText(getResString(R.string.following));
            txtCountFollowing.setText("13");
            tabLayout.addTab(tabLayout.newTab().setCustomView(llTabFollowing));

            LinearLayout llTabFollowers = (LinearLayout) LayoutInflater.from(getDockActivity()).inflate(R.layout.profile_custom_tab, null).findViewById(R.id.llTab);
            TextView txtTitleFollowers = (TextView) llTabFollowers.getChildAt(1);
            TextView txtCountFollowers = (TextView) llTabFollowers.getChildAt(0);
            txtTitleFollowers.setText(getResString(R.string.followers));
            txtCountFollowers.setText("17");
            tabLayout.addTab(tabLayout.newTab().setCustomView(llTabFollowers));


            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            setData(tab);


        }
    }

    private void tabLayoutListner() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                LinearLayout llTab = (LinearLayout) tabLayout.getTabAt(tab.getPosition()).getCustomView();
                TextView txtTitle = (TextView) llTab.getChildAt(1);
                TextView txtCount = (TextView) llTab.getChildAt(0);
                txtCount.setTextColor(getDockActivity().getResources().getColor(R.color.app_brown));
             //   txtTitle.setTypeface(txtTitle.getTypeface(), Typeface.BOLD);
                txtCount.setTypeface(txtCount.getTypeface(), Typeface.BOLD);
                tabLayout.getTabAt(tab.getPosition()).setCustomView(llTab);

                if (tab.getPosition() == 0) {
                } else if (tab.getPosition() == 1) {
                } else if (tab.getPosition() == 2) {
                } else if (tab.getPosition() == 3) {
                }
                setData(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                LinearLayout llTab = (LinearLayout) tabLayout.getTabAt(tab.getPosition()).getCustomView();
                TextView txtTitle = (TextView) llTab.getChildAt(1);
                TextView txtCount = (TextView) llTab.getChildAt(0);
                txtCount.setTextColor(getDockActivity().getResources().getColor(R.color.tab_text_color));
                //  txtTitle.setTypeface(txtTitle.getTypeface(), Typeface.NORMAL);
                txtCount.setTypeface(txtCount.getTypeface(), Typeface.NORMAL);
                tabLayout.getTabAt(tab.getPosition()).setCustomView(llTab);

                if (tab.getPosition() == 0) {
                } else if (tab.getPosition() == 1) {
                } else if (tab.getPosition() == 2) {
                } else if (tab.getPosition() == 3) {
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setData(TabLayout.Tab tab) {

        if (tab.getPosition() == 0) {
            replaceFragment(ProfilePostsFragment.newInstance());
        } else if (tab.getPosition() == 1) {
            replaceFragment(ProfileMediaFragment.newInstance());
        } else if (tab.getPosition() == 2) {
            replaceFragment(ProfileFollowersFragment.newInstance());
        } else if (tab.getPosition() == 3) {
            replaceFragment(ProfileFollowersFragment.newInstance());
        }
    }

    public void replaceFragment(Fragment frag) {

        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, frag);
        transaction.addToBackStack(manager.getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();


    }

}
