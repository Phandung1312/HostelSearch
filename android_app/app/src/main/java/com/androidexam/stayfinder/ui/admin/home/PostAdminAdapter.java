package com.androidexam.stayfinder.ui.admin.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.common.BindingAdapters;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.databinding.ItemsAdminPostBinding;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostAdminAdapter extends RecyclerView.Adapter<PostAdminAdapter.ViewHolder> implements Filterable {
    private  ArrayList<Hostel> hostelList;

    public PostAdminAdapter( ArrayList<Hostel> hostelList){
        this.hostelList = hostelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsAdminPostBinding binding =
                ItemsAdminPostBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdminAdapter.ViewHolder holder, int position) {
        holder.binding.setHostel(hostelList.get(position));
        holder.binding.cardViewPostAdmin.setOnClickListener(view ->{
            Hostel hostel =hostelList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("hostel", (Serializable) hostel);
            Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.postDetailAdminFragment,bundle);
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase();
                List<Hostel> filteredHostel = new ArrayList<>();
                if(input.isEmpty()){
                    filteredHostel.addAll(hostelList);
                }else {
                    for(Hostel hostel: hostelList){
                        if(hostel.getName().toLowerCase().contains(input))
                        {
                            filteredHostel.add(hostel);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = hostelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                hostelList = new ArrayList<>();
                hostelList.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private  ItemsAdminPostBinding binding;

        public ViewHolder( ItemsAdminPostBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
