package com.example.musixexo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musixexo.models.ModelClass;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter {
    public OnItemLongClickListener mItemLongClickListener;
    public OnItemClickListener mItemClickListener;
    Context context;
    ArrayList<ModelClass> menuList;


    public CustomAdapter(Context context, ArrayList<ModelClass> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcv_list_item, viewGroup, false);
        return new ViewHolder(v);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final ModelClass name = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            // genericViewHolder.tvName.setText((name.getActivityName()));
            genericViewHolder.tvName.setText(name.getActivityName());
            Glide.with(context).load(name.getImgUri()).placeholder(R.drawable.ic_background).into(genericViewHolder.imgView);
        }
    }


    //    need to override this method
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public ModelClass getItem(int position) {
        return menuList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ArrayList<ModelClass> menulist);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position, ArrayList<ModelClass> names);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView imgView;
        View rcvLinearLayout;

        ViewHolder(final View itemView) {
            super(itemView);

            this.tvName = itemView.findViewById(R.id.tvList);
            // this.rcvLinearLayout=itemView.findViewById(R.id.rcvLinLayout);

            this.imgView = itemView.findViewById(R.id.imgvList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), menuList);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mItemLongClickListener.onItemLongClick(itemView, getAdapterPosition(), menuList);

                    return true;
                }
            });

        }
    }
}
