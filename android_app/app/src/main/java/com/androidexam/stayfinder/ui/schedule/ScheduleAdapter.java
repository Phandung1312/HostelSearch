package com.androidexam.stayfinder.ui.schedule;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.Hostel;
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

    // Creating date format
    private final DateFormat dateFormat = new SimpleDateFormat(
            "'Ngày' dd 'Tháng' MM 'Năm' yyyy", Locale.getDefault());
    private final DateFormat timeFormat = new SimpleDateFormat(
            "'Lúc' HH:mm Z", Locale.getDefault());

    public ScheduleAdapter(Context context, ArrayList<ScheduleRequest> schedules, String accountName){
        this.context = context;
        this.schedules = schedules;
        this.accountName = accountName;
    }

    @Override
    public int getItemViewType(int position) {
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
            ScheduleRequest schedule = schedules.get(position);

            viewHolder.binding.tvTitle.setText((position+1)+". Hẹn với chủ trọ");
            viewHolder.binding.setSchedule(schedule);
            viewHolder.binding.setHostel(schedule.getPost().getHostel());

            long milliSec = schedule.getMeetingTime().getTime();
            Date dateEvent = new Date(milliSec);
            viewHolder.binding.tvScheduleDate.setText(dateFormat.format(dateEvent));
            viewHolder.binding.tvScheduleTime.setText(timeFormat.format(dateEvent));
            if(schedule.getColor() == ""){
                String randomColor = getRandomColor();
                viewHolder.binding.cvSchedule.getBackground().setTint(Color.parseColor(randomColor));
                schedule.setColor(randomColor);
            }
            else{
                viewHolder.binding.cvSchedule.getBackground().setTint(Color.parseColor(schedule.getColor()));
            }
        }

        else{
            ViewHolder viewHolder = (ViewHolder) holder;
            ScheduleRequest schedule = schedules.get(position);

            viewHolder.binding.tvTitle.setText((position+1)+". Hẹn với người thuê trọ");
            viewHolder.binding.setSchedule(schedule);
            viewHolder.binding.setHostel(schedule.getPost().getHostel());

            long milliSec = schedule.getMeetingTime().getTime();
            Date dateEvent = new Date(milliSec);
            viewHolder.binding.tvScheduleDate.setText(dateFormat.format(dateEvent));
            viewHolder.binding.tvScheduleTime.setText(timeFormat.format(dateEvent));
            if(schedule.getColor() == ""){
                String randomColor = getRandomColor();
                viewHolder.binding.cvSchedule.getBackground().setTint(Color.parseColor(randomColor));
                schedule.setColor(randomColor);
            }
            else{
                viewHolder.binding.cvSchedule.getBackground().setTint(Color.parseColor(schedule.getColor()));
            }
        }

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

            binding.cvSchedule.setOnClickListener(view ->{
                Hostel hostel = schedules.get(getBindingAdapterPosition()).getPost().getHostel();
                Bundle bundle = new Bundle();
                bundle.putSerializable("hostel", hostel);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.postDetailAdminFragment,bundle);
            });
            binding.executePendingBindings();

        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemDetailScheduleBinding binding;

        public ViewHolder(@NonNull ItemDetailScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.cvSchedule.setOnClickListener(view ->{
                Hostel hostel = schedules.get(getBindingAdapterPosition()).getPost().getHostel();
                Bundle bundle = new Bundle();
                bundle.putSerializable("hostel", hostel);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.postDetailAdminFragment,bundle);
            });
            binding.executePendingBindings();
        }
    }

    private String getRandomColor(){
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#d7e7d8");
        colors.add("#3fb59e");
        colors.add("#facade");
        colors.add("#bcc499");
        colors.add("#c39797");
        colors.add("#eea990");
        colors.add("#f7cac9");
        colors.add("#ffc3a0");
        colors.add("#61aef4");
        colors.add("#e1d09c");
        colors.add("#f9b5b5");
        colors.add("#d8cff4");
        colors.add("#e9a9cf");
        colors.add("#ffcccc");
        colors.add("#ffc299");
        colors.add("#ffffe6");
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
        colors.add("#e6e6fa");
        colors.add("#ffb2d6");
        colors.add("#faebd7");

        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }

}
