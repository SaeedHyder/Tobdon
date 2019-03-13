package com.app.tobdon.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.tobdon.R;
import com.app.tobdon.activities.DockActivity;
import com.app.tobdon.interfaces.RecyclerClickListner;
import com.app.tobdon.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> collection;
    private DockActivity dockActivity;
    private RecyclerClickListner listner;
    private String previous;
    private String newCharacter;
    private int previousPos = 0;

    public UserAdapter(ArrayList<String> dataset, DockActivity dockActivity, RecyclerClickListner recyclerViewOnClick) {
        collection = dataset;
        this.dockActivity = dockActivity;
        this.listner = recyclerViewOnClick;

        if (collection != null && collection.size() > 0) {
            previous = String.valueOf(collection.get(0).charAt(0));
            previousPos = 0;
        }
    }


    @Override
    public int getItemCount() {
        if (collection == null)
            return 0;
        return collection.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public int checkViewType(int position) {
        if (collection.get(position) != null  && !collection.get(position).isEmpty() && !collection.get(position).equals("")) {
            newCharacter = String.valueOf(collection.get(position).charAt(0));

            if (previousPos != position && !newCharacter.toLowerCase().trim().equals(previous.toLowerCase().trim())) {
                previous = newCharacter;
                previousPos = position;
                return 0;
            } else if (previousPos == position && newCharacter.toLowerCase().trim().equals(previous.toLowerCase().trim())) {
                //   previous = newCharacter;
                return 0;
            } else {
                return 2;
            }

        } else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if (checkViewType(viewType) == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_user_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_user, parent, false);
            return new ChildViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (checkViewType(position) == 0) {
            try {
                HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
                viewHolder.txtHeader.setText(String.valueOf(collection.get(position).charAt(0)).toUpperCase());
                viewHolder.txtName.setText(String.valueOf(collection.get(position)));

                viewHolder.mainFrameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listner.onClick(new Object(), position);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {

                ChildViewHolder viewHolder = (ChildViewHolder) holder;
                viewHolder.txtName.setText(String.valueOf(collection.get(position)));

                viewHolder.mainFrameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listner.onClick(new Object(), position);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        CircleImageView ivImage;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.txtHeader)
        AnyTextView txtHeader;
        @BindView(R.id.mainFrameLayout)
        LinearLayout mainFrameLayout;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        CircleImageView ivImage;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.mainFrameLayout)
        LinearLayout mainFrameLayout;

        ChildViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
