package com.aksyonov.post_statistic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends RecyclerView.Adapter<Adapter.NumberViewHolder> {

    private int number_items;

    public Adapter (int count){
        number_items=count;
    }


    //@NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(/*@NonNull*/ ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int LayoutIdForListItems = R.layout.number_image_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(LayoutIdForListItems, parent, false);

        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolder.user_name.setText("test");
        number_items ++;
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
        numberViewHolder.bind(position);

    }

    @Override
    public int getItemCount() {
        return number_items;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {

        TextView user_name;
       // ImageView user_foto;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.tv_user_name);
           // user_foto = itemView.findViewById(R.id.iv_user_foto);

        }

        void bind (int name){
          //  user_name.setText(String.valueOf(name));
            user_name.setText("test yy " + String.valueOf(number_items));
        }
    }
}
