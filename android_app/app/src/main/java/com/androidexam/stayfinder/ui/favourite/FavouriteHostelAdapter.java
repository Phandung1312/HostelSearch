package com.androidexam.stayfinder.ui.favourite;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.databinding.ItemsFavouriteHomeBinding;
import com.androidexam.stayfinder.databinding.ItemsHomesBinding;

import java.util.List;

import dagger.hilt.android.components.FragmentComponent;

public class FavouriteHostelAdapter extends RecyclerView.Adapter<FavouriteHostelAdapter.ViewHolder> {
    private Context context;
    private List<Hostel> hostels;
    private FavouriteViewModel favouriteViewModel;
    private LifecycleOwner lifecycleOwner;
    private String username;

    public FavouriteHostelAdapter(Context context, List<Hostel> hostels, FavouriteViewModel favouriteViewModel, LifecycleOwner lifecycleOwner, String username) {
        this.context = context;
        this.hostels = hostels;
        this.favouriteViewModel = favouriteViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.username = username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsFavouriteHomeBinding binding = ItemsFavouriteHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hostel hostel = hostels.get(holder.getLayoutPosition());
        holder.binding.setHostel(hostels.get(holder.getLayoutPosition()));
        setFavouriteImage(holder.binding.imgFavourite, true);
        holder.binding.imgFavourite.setOnClickListener(view ->{
            ImageView img = (ImageView) view;
            favouriteViewModel.checkFavouriteHostel(username, hostel.getPost().getId())
                    .observe(lifecycleOwner, response ->{
                        if(response){
                            favouriteViewModel.unFavouriteHostel(username, hostel.getPost().getId());
                        }
                        else{
                            favouriteViewModel.addFavouriteHostel(username, hostel.getPost().getId());
                        }
                        setFavouriteImage(img, !response);
                    });
        });
        holder.binding.getRoot().setOnClickListener(view ->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("hostelId", hostel.getId());
            bundle.putSerializable("postId",hostel.getPost().getId());
            Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.postDetailAdminFragment, bundle);
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return this.hostels.size();
    }
    public void setFavouriteImage(ImageView img,Boolean isFavourite){
        Drawable.ConstantState drawable = img.getDrawable().getConstantState();
        if (isFavourite){
            img.setImageResource(R.drawable.ic_favourite_red);
            img.setTag(R.drawable.ic_favourite_red);
        }
        else{
            img.setImageResource(R.drawable.ic_favourite_border_white);
            img.setTag(R.drawable.ic_favourite_border_white);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemsFavouriteHomeBinding binding;
        public ViewHolder(ItemsFavouriteHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
