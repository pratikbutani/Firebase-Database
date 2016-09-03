package com.androidbuts.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class QuotesHolder extends RecyclerView.ViewHolder {

    public TextView titleView;

    public QuotesHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.row_text_view);
    }

    public void bindTo(String quotes) {
        titleView.setText(quotes);
    }
}