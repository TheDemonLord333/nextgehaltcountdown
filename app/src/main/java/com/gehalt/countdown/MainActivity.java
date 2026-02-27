package com.gehalt.countdown;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gehalt.countdown.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd. MMMM yyyy", Locale.GERMAN);
    private static final int UPCOMING_COUNT = 6;

    private ActivityMainBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable tickRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNextSalaryDate();
        setupUpcomingList();
        startCountdown();
    }

    private void setupNextSalaryDate() {
        LocalDate nextSalary = SalaryCalculator.getNextSalaryDate();
        binding.tvNextSalaryDate.setText(nextSalary.format(DATE_FORMAT));
    }

    private void setupUpcomingList() {
        List<LocalDate> dates = SalaryCalculator.getUpcomingSalaryDates(UPCOMING_COUNT);
        List<SalaryDateItem> items = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            items.add(new SalaryDateItem(dates.get(i), i + 1));
        }
        SalaryAdapter adapter = new SalaryAdapter(items);
        binding.rvUpcoming.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUpcoming.setAdapter(adapter);
        binding.rvUpcoming.setNestedScrollingEnabled(false);
    }

    private void startCountdown() {
        tickRunnable = new Runnable() {
            @Override
            public void run() {
                updateCountdown();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(tickRunnable);
    }

    private void updateCountdown() {
        long millis = SalaryCalculator.getMillisUntilNextSalary();
        long[] parts = SalaryCalculator.breakDownMillis(millis);

        long days    = parts[0];
        long hours   = parts[1];
        long minutes = parts[2];
        long seconds = parts[3];

        binding.tvDays.setText(String.format(Locale.GERMAN, "%02d", days));
        binding.tvHours.setText(String.format(Locale.GERMAN, "%02d", hours));
        binding.tvMinutes.setText(String.format(Locale.GERMAN, "%02d", minutes));
        binding.tvSeconds.setText(String.format(Locale.GERMAN, "%02d", seconds));

        if (millis <= 0) {
            binding.tvCountdownLabel.setText(R.string.label_payday_today);
            // Refresh next date after payday
            setupNextSalaryDate();
            setupUpcomingList();
        } else {
            binding.tvCountdownLabel.setText(R.string.label_next_salary);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tickRunnable != null) {
            handler.removeCallbacks(tickRunnable);
        }
    }
}
