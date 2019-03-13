package com.app.tobdon.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.app.tobdon.R;
import com.app.tobdon.activities.DockActivity;
import com.app.tobdon.helpers.BasePreferenceHelper;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class FollowerBinder extends RecyclerViewBinder<String> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerClickListner clickListner;

    public FollowerBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner) {
        super(R.layout.row_item_followers);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        if (position == 2 || position == 4 || position == 6) {
            holder.btnFollow.setTextColor(context.getResources().getColor(R.color.white));
            holder.btnFollow.setBackground(context.getResources().getDrawable(R.drawable.rounded_brown_button));
            holder.btnFollow.setText(context.getResources().getString(R.string.unfollow));
        } else {
            holder.btnFollow.setTextColor(context.getResources().getColor(R.color.app_brown));
            holder.btnFollow.setBackground(context.getResources().getDrawable(R.drawable.rounded_white_button));
        }

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.ivImage)
        CircleImageView ivImage;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.btnFollow)
        Button btnFollow;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
