package com.valeriia.beta_ver_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.valeriia.beta_ver_1.R;
import com.valeriia.beta_ver_1.model.CalendarUtils;
import com.valeriia.beta_ver_1.model.Event;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        Button deleteEventButton = convertView.findViewById(R.id.deleteEventButton);

        String eventTitle = event.getName() + " " + CalendarUtils.formattedTime(event.getTime());
        eventCellTV.setText(eventTitle);

        // Установка обработчика нажатия на кнопку "Удалить"
        deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Удалить событие из списка
                remove(event);
                Event.eventsForDate(CalendarUtils.selectedDate).remove(event);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
