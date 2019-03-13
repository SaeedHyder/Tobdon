package com.app.tobdon.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.tobdon.R;
import com.app.tobdon.activities.DockActivity;
import com.app.tobdon.entities.AttachmentEnt;
import com.app.tobdon.global.AppConstants;
import com.app.tobdon.helpers.BasePreferenceHelper;
import com.app.tobdon.interfaces.AttachmentInterface;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentBinder extends RecyclerViewBinder<AttachmentEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerClickListner clickListner;
    private AttachmentInterface crossClick;

    public AttachmentBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, AttachmentInterface crossClick) {
        super(R.layout.row_item_attachment);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();

        this.crossClick = crossClick;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(AttachmentEnt entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;
        if (entity != null) {
            if (entity.getType().equals(AppConstants.VIDEO)) {
                //     Picasso.with(dockActivity).load(new File(entity.getAttahcment())).placeholder(R.drawable.pdf_blue).into(holder.ivAttachment);
                holder.ivAttachment.setImageResource(R.drawable.placeholder_thumb);
            } else {
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                options.placeholder(R.drawable.placeholder_thumb);
                Glide.with(dockActivity).load(entity.getBitmapImage()).apply(options).into(holder.ivAttachment);
            }
        }
        holder.ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crossClick.onCrossClick(entity, position);
            }
        });


    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_attachment)
        ImageView ivAttachment;
        @BindView(R.id.iv_cross)
        ImageView ivCross;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
