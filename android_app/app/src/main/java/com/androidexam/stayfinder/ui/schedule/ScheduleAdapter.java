package com.androidexam.stayfinder.ui.schedule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.request.ScheduleRequest;
import com.androidexam.stayfinder.databinding.ItemDetailScheduleBinding;
import com.androidexam.stayfinder.databinding.ItemDetailScheduleRenterBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ScheduleRequest> schedules;
    private String accountName;
//    private ArrayList<DogBreed> dogBreedsCopy; //C1


    // Creating date format
    private final DateFormat dateFormat = new SimpleDateFormat(
            "'Ngày' dd 'Tháng' MM 'Năm' yyyy", Locale.getDefault());
    private final DateFormat timeFormat = new SimpleDateFormat(
            "'Lúc' HH:mm:ss:SSS Z", Locale.getDefault());

    public ScheduleAdapter(Context context, ArrayList<ScheduleRequest> schedules, String accountName){
        this.context = context;
        this.schedules = schedules;
        this.accountName = accountName;
//        this.dogBreedsCopy = dogBreeds; //C1
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println(schedules.get(position).getUsername() + ", " + accountName + ", " + (schedules.get(position).getUsername().equals(accountName)));
        return schedules.get(position).getUsername().equals(accountName)? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_detail_schedule_renter, parent, false);
            ItemDetailScheduleRenterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_detail_schedule_renter,
                    parent,
                    false);
            return new ViewHolderRenter(binding);
        }
        else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_detail_schedule, parent, false);
            ItemDetailScheduleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_detail_schedule,
                    parent,
                    false);
            return new ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == 0){
            ViewHolderRenter viewHolder = (ViewHolderRenter) holder;
            viewHolder.binding.setSchedule(schedules.get(position));
            viewHolder.binding.setHostel(schedules.get(position).getPost().getHostel());

            long milliSec = schedules.get(position).getMeetingTime().getTime();
            Date dateEvent = new Date(milliSec);
            viewHolder.binding.tvScheduleDate.setText(dateFormat.format(dateEvent));
            viewHolder.binding.tvScheduleTime.setText(timeFormat.format(dateEvent));
            viewHolder.binding.cvSchedule.getBackground().setTint(Color.parseColor(getRandomColor()));
        }

        else{
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.binding.setSchedule(schedules.get(position));
            viewHolder.binding.setHostel(schedules.get(position).getPost().getHostel());

            long milliSec = schedules.get(position).getMeetingTime().getTime();
            Date dateEvent = new Date(milliSec);
            viewHolder.binding.tvScheduleDate.setText(dateFormat.format(dateEvent));
            viewHolder.binding.tvScheduleTime.setText(timeFormat.format(dateEvent));
            viewHolder.binding.cvSchedule.getBackground().setTint(Color.parseColor(getRandomColor()));
        }

//        Picasso.get().load(dogBreeds.get(position).getUrl()).into(holder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        if(schedules != null) return schedules.size();
        else return 0;
    }

    //C1
//    public Filter getFilter(){
//        return new Filter(){
//
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String input = charSequence.toString().toLowerCase();
//                List<DogBreed> filteredDog = new ArrayList<DogBreed>();
//                if(input.isEmpty()){
//                    filteredDog.addAll(dogBreedsCopy);
//                }
//                else{
//                    for(DogBreed dog: dogBreedsCopy){
//                        if(dog.getName().toLowerCase().contains(input)){
//                            filteredDog.add(dog);
//                        }
//                    }
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredDog;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                dogBreeds = new ArrayList<>();
//                dogBreeds.addAll((List)filterResults.values);
//                notifyDataSetChanged();
//            }
//        };
//    }

    //C2
    public void updateList(ArrayList<ScheduleRequest> newList) {
        schedules = newList;
        notifyDataSetChanged();
    }

    public class ViewHolderRenter extends RecyclerView.ViewHolder {
        public ItemDetailScheduleRenterBinding binding;

        public ViewHolderRenter(@NonNull ItemDetailScheduleRenterBinding renterBinding) {
            super(renterBinding.getRoot());
            this.binding = renterBinding;

        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemDetailScheduleBinding binding;

        public ViewHolder(@NonNull ItemDetailScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    private String getRandomColor(){
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#35ad68");
        colors.add("#c27ba8");
        colors.add("#baa9aa");
        colors.add("#bfbd97");
        colors.add("#746cc0");
        colors.add("#666666");
        colors.add("#ff6019");
        colors.add("#29cdff");
        colors.add("#929a70");
        colors.add("#3ded97");
        colors.add("#d21500");
        colors.add("#e6e6fa");
        colors.add("#ffb2d6");
        colors.add("#faebd7");

        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }

}
