package com.valeriia.beta_ver_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;

import com.valeriia.beta_ver_1.adapter.CalendarAdapter;
import com.valeriia.beta_ver_1.model.CalendarUtils;

import static com.valeriia.beta_ver_1.model.CalendarUtils.daysInMonthArray;
import static com.valeriia.beta_ver_1.model.CalendarUtils.monthYearFromDate;

public class DiaryFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    private Button previousButton, nextButton;  // Add Button references

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the widgets and set up the calendar view
        initWidgets(view);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction();
            }
        });

        monthYearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyAction();
            }
        });

    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        previousButton = view.findViewById(R.id.previousButton);  // Initialize the button
        nextButton = view.findViewById(R.id.nextButton);  // Initialize the button
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    public void weeklyAction() {
        // Replace the current fragment with CalendarWeekFragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new CalendarWeekFragment())
                .addToBackStack(null) // Allows going back to the DiaryFragment
                .commit();

        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

}
