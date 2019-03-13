package com.app.tobdon.ui.binders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.tobdon.R;
import com.app.tobdon.helpers.BasePreferenceHelper;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.tobdon.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatBinder extends RecyclerViewBinder<String> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private RecyclerClickListner listner;

    public ChatBinder(Context context, BasePreferenceHelper prefHelper, RecyclerClickListner listner) {
        super(R.layout.row_item_chat);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.listner = listner;
        imageLoader = ImageLoader.getInstance();
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
        @BindView(R.id.txt_left)
        AnyTextView txtLeft;
        @BindView(R.id.txt_left_date)
        AnyTextView txtLeftDate;
        @BindView(R.id.ll_left_text)
        LinearLayout llLeftText;
        @BindView(R.id.iv_image_left)
        ImageView ivImageLeft;
        @BindView(R.id.txt_left_count)
        AnyTextView txtLeftCount;
        @BindView(R.id.txt_image_message_left)
        AnyTextView txtImageMessageLeft;
        @BindView(R.id.txt_image_date_left)
        AnyTextView txtImageDateLeft;
        @BindView(R.id.cv_image_left)
        CardView cvImageLeft;
        @BindView(R.id.iv_pdf_left)
        ImageView ivPdfLeft;
        @BindView(R.id.txt_pdf_count_left)
        AnyTextView txtPdfCountLeft;
        @BindView(R.id.txt_pdf_message_left)
        AnyTextView txtPdfMessageLeft;
        @BindView(R.id.txt_pdf_date_left)
        AnyTextView txtPdfDateLeft;
        @BindView(R.id.cv_pdf_left)
        CardView cvPdfLeft;
        @BindView(R.id.iv_map_left)
        ImageView ivMapLeft;
        @BindView(R.id.txt_map_address_left)
        AnyTextView txtMapAddressLeft;
        @BindView(R.id.txt_map_date_left)
        AnyTextView txtMapDateLeft;
        @BindView(R.id.cv_map_left)
        CardView cvMapLeft;
        @BindView(R.id.rl_left)
        RelativeLayout rlLeft;
        @BindView(R.id.txt_right)
        AnyTextView txtRight;
        @BindView(R.id.txt_right_date)
        AnyTextView txtRightDate;
        @BindView(R.id.ll_right_text)
        LinearLayout llRightText;
        @BindView(R.id.iv_image_right)
        ImageView ivImageRight;
        @BindView(R.id.txt_right_count)
        AnyTextView txtRightCount;
        @BindView(R.id.txt_image_message_right)
        AnyTextView txtImageMessageRight;
        @BindView(R.id.txt_image_date_right)
        AnyTextView txtImageDateRight;
        @BindView(R.id.cv_image_right)
        CardView cvImageRight;
        @BindView(R.id.iv_pdf_right)
        ImageView ivPdfRight;
        @BindView(R.id.txt_pdf_count_right)
        AnyTextView txtPdfCountRight;
        @BindView(R.id.txt_pdf_message_right)
        AnyTextView txtPdfMessageRight;
        @BindView(R.id.txt_pdf_date_right)
        AnyTextView txtPdfDateRight;
        @BindView(R.id.cv_pdf_right)
        CardView cvPdfRight;
        @BindView(R.id.iv_map_right)
        ImageView ivMapRight;
        @BindView(R.id.txt_map_address_right)
        AnyTextView txtMapAddressRight;
        @BindView(R.id.txt_map_date_right)
        AnyTextView txtMapDateRight;
        @BindView(R.id.cv_map_right)
        CardView cvMapRight;
        @BindView(R.id.rl_right)
        RelativeLayout rlRight;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
