package com.app.tobdon.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.tobdon.R;
import com.app.tobdon.fragments.abstracts.BaseFragment;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.binders.CommentBinder;
import com.app.tobdon.ui.views.AnyEditTextView;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.app.tobdon.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailFragment extends BaseFragment implements RecyclerClickListner {
    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtAddress)
    AnyTextView txtAddress;
    @BindView(R.id.iv_feedImage)
    ImageView ivFeedImage;
    @BindView(R.id.txtDescription)
    AnyTextView txtDescription;
    @BindView(R.id.txtDateTime)
    AnyTextView txtDateTime;
    @BindView(R.id.txtLikes)
    AnyTextView txtLikes;
    @BindView(R.id.txtShares)
    AnyTextView txtShares;
    @BindView(R.id.btnLike)
    ImageView btnLike;
    @BindView(R.id.btnComment)
    ImageView btnComment;
    @BindView(R.id.btnShare)
    ImageView btnShare;
    @BindView(R.id.btnLPopup)
    ImageView btnLPopup;
    @BindView(R.id.rvComments)
    CustomRecyclerView rvComments;
    Unbinder unbinder;
    @BindView(R.id.edtMessage)
    AnyEditTextView edtMessage;
    @BindView(R.id.btnSend)
    ImageView btnSend;

    public static PostDetailFragment newInstance() {
        Bundle args = new Bundle();

        PostDetailFragment fragment = new PostDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCommentsData();
    }

    private void setCommentsData() {

        ArrayList<String> collection = new ArrayList<>();
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");
        collection.add("");

        rvComments.BindRecyclerView(new CommentBinder(getDockActivity(), prefHelper, this, false), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());
        setTouchListner(collection.size() - 1);
    }

    private void setTouchListner(final int position) {
        edtMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvComments.scrollToPosition(position);
                        }
                    }, 1000);
                }
                return false;
            }
        });

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.post_detail));
    }


    @OnClick({R.id.btnLike, R.id.btnComment, R.id.btnShare, R.id.btnLPopup, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLike:
                break;
            case R.id.btnComment:
                break;
            case R.id.btnShare:
                break;
            case R.id.btnLPopup:
                break;
            case R.id.btnSend:
                break;
        }
    }

    @Override
    public void onClick(Object entity, int position) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getDockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


}
