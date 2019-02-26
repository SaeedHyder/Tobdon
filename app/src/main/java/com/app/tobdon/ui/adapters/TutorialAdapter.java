package com.app.tobdon.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.app.tobdon.R;
import com.app.tobdon.activities.MainActivity;
import com.app.tobdon.interfaces.TutorialInterface;
import com.app.tobdon.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class TutorialAdapter extends PagerAdapter {
    MainActivity context;
    ArrayList<String> images;
    LayoutInflater layoutInflater;
    ImageLoader imageLoader;
    private TutorialInterface tutorialInterface;


    public TutorialAdapter(MainActivity context, ArrayList<String> images, TutorialInterface tutorialInterface) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ImageLoader.getInstance();
        this.tutorialInterface=tutorialInterface;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.tutorail_row_item, container, false);

        ImageView background = (ImageView) itemView.findViewById(R.id.background);
        ImageView logo = (ImageView) itemView.findViewById(R.id.logo);
        AnyTextView txtTitle = (AnyTextView) itemView.findViewById(R.id.txt_title);
        AnyTextView txt_text = (AnyTextView) itemView.findViewById(R.id.txt_text);
        imageLoader.displayImage(images.get(position), background);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
