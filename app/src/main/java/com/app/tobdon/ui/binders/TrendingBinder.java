package com.app.tobdon.ui.binders;

import android.content.Context;
import android.view.View;

import com.app.tobdon.R;
import com.app.tobdon.activities.DockActivity;
import com.app.tobdon.helpers.BasePreferenceHelper;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrendingBinder extends RecyclerViewBinder<String> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerClickListner clickListner;

    public TrendingBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner) {
        super(R.layout.row_item_trending);
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


    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtTrending)
        AnyTextView txtTrending;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
