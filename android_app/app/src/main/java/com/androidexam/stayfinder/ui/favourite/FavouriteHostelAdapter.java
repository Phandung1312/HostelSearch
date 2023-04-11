package com.androidexam.stayfinder.ui.client.listpost;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.databinding.ItemsHomesBinding;

import java.util.List;

public class ClientHostelAdapter extends RecyclerView.Adapter<ClientHostelAdapter.ViewHolder> {
    private Context context;
    private List<Hostel> hostels;
    private ImageFavouriteClickListener imgClick;


    public ClientHostelAdapter(Context context, List<Hostel> hostels, ImageFavouriteClickListener imgClick) {
        this.context = context;
        this.hostels = hostels;
        this.imgClick = imgClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsHomesBinding binding = ItemsHomesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setHostel(hostels.get(holder.getLayoutPosition()));
        holder.binding.imgFavourite.setOnClickListener(view ->{
            imgClick.onClick(hostels.get(holder.getLayoutPosition()));
            ImageView img = (ImageView) view;
            Drawable.ConstantState drawable = img.getDrawable().getConstantState();
            if (drawable == ContextCompat.getDrawable(context, R.drawable.ic_favourite_border_white).getConstantState()){
                img.setImageResource(R.drawable.ic_favourite_red);
                img.setTag(R.drawable.ic_favourite_red);
            }
            if (drawable == ContextCompat.getDrawable(context,R.drawable.ic_favourite_red).getConstantState()){
                img.setImageResource(R.drawable.ic_favourite_border_white);
                img.setTag(R.drawable.ic_favourite_border_white);
            }
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return this.hostels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemsHomesBinding binding;
        public ViewHolder(ItemsHomesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface ImageFavouriteClickListener{
        void onClick(Hostel hostel);
    }
}
