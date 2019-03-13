package com.app.tobdon.ui.binders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.app.tobdon.R;
import com.app.tobdon.activities.DockActivity;
import com.app.tobdon.helpers.BasePreferenceHelper;
import com.app.tobdon.interfaces.FeedInterface;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedBinder extends RecyclerViewBinder<String> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private FeedInterface clickListner;
    private RecyclerClickListner mainClickListner;

    public FeedBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, FeedInterface clickListner, RecyclerClickListner mainClickListner) {
        super(R.layout.row_item_feed);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
        this.mainClickListner=mainClickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        if (entity != null) {

        }

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onLikeClick(entity,position);
            }
        });

        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onCommentClick(entity,position);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onSharetClick(entity,position);
            }
        });

        holder.btnLPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onPopuptClick(entity,position,view);
            }
        });
        holder.cvMainFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainClickListner.onClick(entity,position);
            }
        });

    }


    static class ViewHolder extends BaseViewHolder {
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
        @BindView(R.id.cvMainFrame)
        CardView cvMainFrame;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
