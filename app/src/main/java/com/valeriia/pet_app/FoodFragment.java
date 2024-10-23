package com.valeriia.pet_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.valeriia.pet_app.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FoodFragment extends Fragment {

    private ArrayList<Item> listItems = new ArrayList<>();
    private Calendar selectedDateTime = Calendar.getInstance();
    private int userId; // Change userId to int
    private FirebaseFirestore firestore; // Firestore instance

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        userId = getUserIdFromPreferences(); // Retrieve userId as an integer

        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(requireContext(), R.layout.item_food, listItems) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_food, parent, false);
                }

                Item item = getItem(position);
                TextView textView = convertView.findViewById(R.id.itemFoodTitle);
                if (item != null) {
                    textView.setText(item.getText() + " at " + item.getFormattedDate());
                }

                Button deleteButton = convertView.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(v -> {
                    listItems.remove(position);
                    notifyDataSetChanged();
                });
                return convertView;
            }
        };

        ListView listView = view.findViewById(R.id.listViewFood);
        listView.setAdapter(adapter);

        EditText editTextFood = view.findViewById(R.id.editTextFood);
        Button datePickerButton = view.findViewById(R.id.datePickerButton);
        Button timePickerButton = view.findViewById(R.id.timePickerButton);
        Button buttonAdd = view.findViewById(R.id.addFoodToListButton);

        datePickerButton.setOnClickListener(v -> {
            int year = selectedDateTime.get(Calendar.YEAR);
            int month = selectedDateTime.get(Calendar.MONTH);
            int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        selectedDateTime.set(Calendar.YEAR, year1);
                        selectedDateTime.set(Calendar.MONTH, monthOfYear);
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }, year, month, day);
            datePickerDialog.show();
        });

        timePickerButton.setOnClickListener(v -> {
            int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
            int minute = selectedDateTime.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (view12, hourOfDay, minute1) -> {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute1);
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        buttonAdd.setOnClickListener(v -> {
            String text = editTextFood.getText().toString();

            if (!text.isEmpty()) {
                Date selectedDate = selectedDateTime.getTime();
                Item newItem = new Item(text, selectedDate, userId); // Create Item object
                listItems.add(0, newItem); // Add to list
                adapter.notifyDataSetChanged();
                editTextFood.getText().clear();
                listView.setSelection(0);

                // Save the new item to Firestore
                saveItemToFirestore(newItem);
            } else {
                Toast.makeText(getContext(), "Please enter food", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void saveItemToFirestore(Item item) {
        DocumentReference newItemRef = firestore.collection("items").document(); // Create a new document reference
        newItemRef.set(item)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Item added successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to add item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private int getUserIdFromPreferences() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", getActivity().MODE_PRIVATE);
        return prefs.getInt("userId", -1); // Default to -1 if no userId found
    }
}
