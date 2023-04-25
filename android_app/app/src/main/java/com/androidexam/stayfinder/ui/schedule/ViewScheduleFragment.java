package com.androidexam.stayfinder.ui.schedule;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.request.ScheduleRequest;
import com.androidexam.stayfinder.databinding.FragmentDetailScheduleBinding;
import com.androidexam.stayfinder.databinding.ScheduleClass;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewScheduleFragment extends BaseFragment<ScheduleClass> {
    ViewScheduleViewModel viewScheduleViewModel;

    private static final int FILL_LARGE_INDICATOR = 1;            //fill circle background for event
    private static final int NO_FILL_LARGE_INDICATOR = 2;         //fill circle around for event
    private static final int SMALL_INDICATOR = 3;                 //fill small circle event (default)

    // Creating date format
    private final DateFormat format = new SimpleDateFormat(
            "'Tháng' MM 'Năm' yyyy", Locale.getDefault());

    private CustomDialogFragment customDialogFragment;
    private ArrayList<ScheduleRequest> ownerSchedule = new ArrayList<>();
    private ArrayList<ScheduleRequest> renterSchedule = new ArrayList<>();

    public ViewScheduleFragment() {
        super(ScheduleClass::inflate);
    }
    @Override
    public void initView() {
        viewScheduleViewModel = new ViewModelProvider(this).get(ViewScheduleViewModel.class);
        dataBinding.calendarView.setEventIndicatorStyle(FILL_LARGE_INDICATOR);

        customDialogFragment = new CustomDialogFragment(mainActivity.account.getAccountName());
    }

    @Override
    public void initListeners() {
        //        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
        //check is new login???
        viewScheduleViewModel.GetAllScheduleByAccountName(mainActivity.account.getAccountName());
        viewScheduleViewModel.loadListOwnerSchedule().observe(getViewLifecycleOwner(),ownerList ->{
            viewScheduleViewModel.loadListRenterSchedule().observe(getViewLifecycleOwner(), renterList -> {
                dataBinding.calendarView.setLocale(TimeZone.getDefault(),Locale.getDefault());
                dataBinding.calendarView.setUseThreeLetterAbbreviation(true);
                dataBinding.calendarView.setFirstDayOfWeek(Calendar.MONDAY);
                ownerSchedule.clear();
                renterSchedule.clear();
                ownerSchedule.addAll(ownerList);
                renterSchedule.addAll(renterList);

                dataBinding.calendarView.removeAllEvents();
                for(ScheduleRequest schedule : ownerSchedule){
                    Event event = new Event(Color.RED, schedule.getMeetingTime().getTime(), schedule.getContent());
                    dataBinding.calendarView.addEvent(event);
                }
                for(ScheduleRequest schedule : renterSchedule){
                    Event event = new Event(Color.RED, schedule.getMeetingTime().getTime(), schedule.getContent());
                    dataBinding.calendarView.addEvent(event);
                }
            });
        });
//        }
        dataBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot()).popBackStack();
            }
        });

        dataBinding.imgLeft.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    dataBinding.imgLeft.setImageResource(R.drawable.solid_left);
                    dataBinding.calendarView.scrollLeft();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    dataBinding.imgLeft.setImageResource(R.drawable.left);
                }
                return true;
            }

        });
        dataBinding.imgRight.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if((event.getAction()&event.getActionMasked()) == MotionEvent.ACTION_DOWN){
                    dataBinding.imgRight.setImageResource(R.drawable.solid_right);
                    dataBinding.calendarView.scrollRight();
                }
                if((event.getAction()&event.getActionMasked()) == MotionEvent.ACTION_UP){
                    dataBinding.imgRight.setImageResource(R.drawable.right);
                }
                return true;
            }

        });
        dataBinding.calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //get all events of selected date
                ArrayList<ScheduleRequest> ownerLst = new ArrayList<>();
                ArrayList<ScheduleRequest> renterLst = new ArrayList<>();

                Calendar c = Calendar.getInstance();
                c.setTime(dateClicked);
                c.add(Calendar.DATE, 1);        //increment 1 day

                for(ScheduleRequest schedule : ownerSchedule){
                    Date event = new Date(schedule.getMeetingTime().getTime());
                    if(event.after(dateClicked) && event.before(c.getTime())){
                        ownerLst.add(schedule);
                    }
                }
                for(ScheduleRequest schedule : renterSchedule){
                    Date event = new Date(schedule.getMeetingTime().getTime());
                    if(event.after(dateClicked) && event.before(c.getTime())){
                        renterLst.add(schedule);
                    }
                }

                customDialogFragment.updateList(ownerLst, renterLst);
                customDialogFragment.show(getParentFragmentManager(), "Schedule list");

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                dataBinding.tvMonthYear.setText(format.format(firstDayOfNewMonth));
            }
        });
    }

    @Override
    public void initData() {
        dataBinding.tvMonthYear.setText(format.format(System.currentTimeMillis()));
    }
}
