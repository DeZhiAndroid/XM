package com.example.xm.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xm.R;
import com.example.xm.databinding.PhotoItemBinding;

import java.util.List;

/**
 * @ClassName PhotoAdapter
 * @Author 史正龙
 * @date 2022.02.28 07:43
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private Context context;
    private List<Bitmap> list;

    public OnItemClickListener mListener;

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public PhotoAdapter(Context context, List<Bitmap> list) {
        this.context = context;
        this.list = list;
    }

    public List<Bitmap> getList() {
        return list;

    }

    public void setList(List<Bitmap> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoHolder(PhotoItemBinding.inflate(LayoutInflater.from(context)), mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        if (list == null || position >= list.size()) {
            Glide.with(context).load(R.mipmap.icon_plus).into(holder.binding.imageView);
        } else {
            Glide.with(context).load(list.get(position)).into(holder.binding.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size() + 1;
    }

    static class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final PhotoItemBinding binding;
        private final OnItemClickListener mListener;

        public PhotoHolder(@NonNull PhotoItemBinding itemView, OnItemClickListener listener) {
            super(itemView.getRoot());
            binding = itemView;
            mListener = listener;
            itemView.imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, getPosition());
        }
    }
}
