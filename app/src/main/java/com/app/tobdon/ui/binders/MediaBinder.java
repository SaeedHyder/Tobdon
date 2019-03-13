package com.app.tobdon.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.tobdon.R;
import com.app.tobdon.entities.NotificationEnt;
import com.app.tobdon.helpers.BasePreferenceHelper;
import com.app.tobdon.interfaces.PopupClickListner;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaBinder extends RecyclerViewBinder<String> {

    private Context context;
    private BasePreferenceHelper preferenceHelper;
    private RecyclerClickListner listner;

    public MediaBinder(Context context, BasePreferenceHelper prefHelper, RecyclerClickListner listner) {
        super(R.layout.row_item_media);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.listner = listner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public void bindView(String entity, int position, Object holder, Context context) {

        ViewHolder viewHolder = (ViewHolder) holder;


    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
