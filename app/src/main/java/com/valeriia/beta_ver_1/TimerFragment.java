package com.valeriia.beta_ver_1;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerFragment extends Fragment {

    private TextView timerText;
    private Button stopStartButton;

    private Timer timer;
    private TimerTask timerTask;
    private Double time = 0.0;

    private boolean timerStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        timerText = view.findViewById(R.id.timerText);
        stopStartButton = view.findViewById(R.id.startStopButton);

        timer = new Timer();

        // Set listeners for buttons (if necessary)
        stopStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopTapped(v);
            }
        });

        view.findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTapped(v);
            }
        });

        return view;
    }

    public void resetTapped(View view) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(requireContext());
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (timerTask != null) {
                    timerTask.cancel();
                    setButtonUI("START", R.color.black);
                    time = 0.0;
                    timerStarted = false;
                    timerText.setText(formatTime(0, 0, 0));
                }
            }
        });

        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        resetAlert.show();
    }

    public void startStopTapped(View view) {
        if (!timerStarted) {
            timerStarted = true;
            setButtonUI("STOP", R.color.black);
            startTimer();
        } else {
            timerStarted = false;
            setButtonUI("START", R.color.white);

            if (timerTask != null) {
                timerTask.cancel();
            }
        }
    }

    private void setButtonUI(String text, int colorResId) {
        stopStartButton.setText(text);
        stopStartButton.setTextColor(ContextCompat.getColor(requireContext(), colorResId));
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
}
