package com.app.tobdon.interfaces;

import android.view.View;

public interface FeedInterface {

    void onLikeClick(Object entity, int position);
    void onCommentClick(Object entity, int position);
    void onSharetClick(Object entity, int position);
    void onPopuptClick(Object entity, int position, View view);
}
