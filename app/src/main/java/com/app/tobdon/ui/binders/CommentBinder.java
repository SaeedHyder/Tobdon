package com.app.tobdon.ui.binders;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.app.tobdon.R;
import com.app.tobdon.activities.DockActivity;
import com.app.tobdon.helpers.BasePreferenceHelper;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.app.tobdon.ui.views.CustomRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class CommentBinder extends RecyclerViewBinder<String> implements RecyclerClickListner {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerClickListner clickListner;
    private boolean neestedComment;

    public CommentBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner, boolean neestedComment) {
        super(R.layout.row_item_comments);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
        this.neestedComment = neestedComment;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        if (neestedComment) {
            holder.viewLine.setVisibility(View.GONE);
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
        }

        if (position == 2) {
            holder.rvComments.setVisibility(View.VISIBLE);
            setCommentsData(holder.rvComments, dockActivity);
        }else{
            holder.rvComments.setVisibility(View.GONE);
        }


    }

    private void setCommentsData(CustomRecyclerView rvComments, DockActivity dockActivity) {

        ArrayList<String> collection = new ArrayList<>();
        collection.add("");
        collection.add("");

        rvComments.BindRecyclerView(new CommentBinder(dockActivity, prefHelper, this, true), collection,
                new LinearLayoutManager(dockActivity, LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());
    }

    @Override
    public void onClick(Object entity, int position) {

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.ivImage)
        CircleImageView ivImage;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.txtTime)
        AnyTextView txtTime;
        @BindView(R.id.btnLike)
        AnyTextView btnLike;
        @BindView(R.id.btnComment)
        AnyTextView btnComment;
        @BindView(R.id.ll_text)
        LinearLayout llText;
        @BindView(R.id.rvComments)
        CustomRecyclerView rvComments;
        @BindView(R.id.viewLine)
        View viewLine;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}