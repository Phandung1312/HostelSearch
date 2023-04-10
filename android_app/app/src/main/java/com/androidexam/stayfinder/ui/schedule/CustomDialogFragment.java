package com.androidexam.stayfinder.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.request.ScheduleRequest;
import com.androidexam.stayfinder.databinding.FragmentDetailScheduleBinding;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class CustomDialogFragment extends DialogFragment {
    private FragmentDetailScheduleBinding binding;
    private ScheduleAdapter scheduleAdapter;
    private String accountName;
    private ArrayList<ScheduleRequest> listSchedules;

    public CustomDialogFragment(String accountName){
        this.accountName = accountName;
    }

    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentDetailScheduleBinding.inflate(getLayoutInflater());

        binding.tvTitle.setText(String.format("Lịch hẹn (%d)", listSchedules.size()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL,
                false);
        binding.rvSchedule.setLayoutManager(linearLayoutManager);

        scheduleAdapter = new ScheduleAdapter(this.getActivity(), listSchedules, accountName);
        binding.rvSchedule.setAdapter(scheduleAdapter);

        return binding.getRoot();
    }

    public void updateList(ArrayList<ScheduleRequest> schedules){
        listSchedules = new ArrayList<>(schedules);
    }
}
