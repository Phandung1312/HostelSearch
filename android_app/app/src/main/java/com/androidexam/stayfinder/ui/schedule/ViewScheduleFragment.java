package com.androidexam.stayfinder.ui.schedule;

import android.graphics.Color;

import androidx.lifecycle.ViewModelProvider;

import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.base.fragment.Inflate;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.databinding.ScheduleClass;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ViewScheduleFragment extends BaseFragment<ScheduleClass> {
    private ViewScheduleViewModel viewScheduleViewModel;

    private CompactCalendarView compactCalendarView;
    public ViewScheduleFragment(Inflate<ScheduleClass> inflate) {
        super(inflate);
    }
    @Override
    public void onStart() {
        super.onStart();
//        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
        //check is new login???
        viewScheduleViewModel.GetAllScheduleByAccountName(mainActivity.account.getAccountName());
        viewScheduleViewModel.loadListSchedule().observe(getViewLifecycleOwner(),lst ->{
                // compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

                for(Schedule schedule : lst){
                }
                // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
                Event ev1 = new Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.");
                compactCalendarView.addEvent(ev1);

                // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
                Event ev2 = new Event(Color.GREEN, 1433704251000L);
                compactCalendarView.addEvent(ev2);

                // Query for events on Sun, 07 Jun 2015 GMT.
                // Time is not relevant when querying for events, since events are returned by day.
                // So you can pass in any arbitary DateTime and you will receive all events for that day.
                List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object
            });
//        }
    }
    @Override
    public void initView() {
        compactCalendarView = dataBinding.calendarView;
        viewScheduleViewModel = new ViewModelProvider(this).get(ViewScheduleViewModel.class);
    }

    @Override
    public void initListeners() {
        dataBinding.calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //get all events of selected date
                List<Event> events = dataBinding.calendarView.getEvents(dateClicked);
//                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
//                for()
                long milliSec = 3010;

                // Creating date format
                DateFormat simple = new SimpleDateFormat(
                        "dd MMM yyyy HH:mm:ss:SSS Z");

                // Creating date from milliseconds
                // using Date() constructor
                Date result = new Date(milliSec);

                // Formatting Date according to the
                // given format
                System.out.println(simple.format(result));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
    }

    @Override
    public void initData() {

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
