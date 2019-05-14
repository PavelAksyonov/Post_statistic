package com.aksyonov.post_statistic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aksyonov.post_statistic.Likers.Liker;
import com.aksyonov.post_statistic.Likers.Likers;
import com.squareup.picasso.Picasso;

public class Adapter_Liker extends RecyclerView.Adapter<Adapter_Liker.LikerViewHolder> {

    private final Likers likers;

    public Adapter_Liker(Likers likers) {
        this.likers = likers;
    }


    //@NonNull
    @Override
    public LikerViewHolder onCreateViewHolder(/*@NonNull*/ ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int LayoutIdForListItems = R.layout.liker_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(LayoutIdForListItems, parent, false);

        LikerViewHolder viewHolder = new LikerViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull LikerViewHolder likerViewHolder, int position) {
        Liker liker = likers.data.get(position);
        likerViewHolder.user_name.setText(liker.nickname);
//        likerViewHolder.user_name.setText(liker.avatarImage.urlSmall);
        Picasso.get().load(liker.avatarImage.urlSmall).into(likerViewHolder.user_foto);


    }

    @Override
    public int getItemCount() {
        return likers.meta.total;
    }

    class LikerViewHolder extends RecyclerView.ViewHolder {
        TextView user_name;
       ImageView user_foto;

        public LikerViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.tv_user_name);
            user_foto = itemView.findViewById(R.id.iv_user_foto);

        }
    }
}
