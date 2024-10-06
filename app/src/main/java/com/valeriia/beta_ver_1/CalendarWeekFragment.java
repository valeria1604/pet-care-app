package com.valeriia.beta_ver_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import com.valeriia.beta_ver_1.adapter.EventAdapter;
import com.valeriia.beta_ver_1.model.Event;
import com.valeriia.beta_ver_1.model.CalendarUtils;
import com.valeriia.beta_ver_1.adapter.CalendarAdapter;

import static com.valeriia.beta_ver_1.model.CalendarUtils.daysInWeekArray;
import static com.valeriia.beta_ver_1.model.CalendarUtils.monthYearFromDate;

public class CalendarWeekFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    private Button previousButton2, nextButton2, newEventButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_week, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize widgets
        initWidgets(view);
        setWeekView();

        previousButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousWeekAction();
            }
        });

        nextButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWeekAction();
            }
        });

        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEventAction();
            }
        });
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);
        previousButton2 = view.findViewById(R.id.previousButton2);  // Initialize the button
        nextButton2 = view.findViewById(R.id.nextButton2);  // Initialize the button
        newEventButton = view.findViewById(R.id.newEventButton);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    public void previousWeekAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }



    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    public void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EventEditFragment())  // Замените R.id.fragment_container на ваш контейнер фрагментов
                .addToBackStack(null)  // Добавляет текущий фрагмент в стек возврата, чтобы можно было вернуться
                .commit();
    }
}
