package com.androidexam.stayfinder.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
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

        binding.tvTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX() <= (binding.tvTitle.getPaddingStart() + binding.tvTitle.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        CustomDialogFragment.this.dismiss();
                        return true;
                    }
                }
                return true;
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public void updateList(ArrayList<ScheduleRequest> schedules){
        listSchedules = new ArrayList<>(schedules);
    }
}
