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
import com.app.tobdon.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class FollowersBinder extends RecyclerViewBinder<String> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private RecyclerClickListner listner;
    private PopupClickListner popupClickListner;

    public FollowersBinder(Context context, BasePreferenceHelper prefHelper, RecyclerClickListner listner, PopupClickListner popupClickListner) {
        super(R.layout.row_item_notification);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.listner = listner;
        this.popupClickListner = popupClickListner;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public void bindView(String entity, int position, Object holder, Context context) {

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupClickListner.onPopuptClick(entity, position, view);
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.ivNotification)
        CircleImageView ivNotification;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.txtNotificaition)
        AnyTextView txtNotificaition;
        @BindView(R.id.txtTime)
        AnyTextView txtTime;
        @BindView(R.id.btnPopup)
        ImageView btnPopup;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
