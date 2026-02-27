package com.gehalt.countdown;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.ViewHolder> {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd. MMMM yyyy", Locale.GERMAN);

    private final List<SalaryDateItem> items;

    public SalaryAdapter(List<SalaryDateItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_salary_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SalaryDateItem item = items.get(position);
        String dayName = item.date.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.GERMAN);
        holder.tvIndex.setText(String.valueOf(item.index));
        holder.tvDate.setText(item.date.format(DATE_FORMAT));
        holder.tvDayName.setText(dayName);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;
        TextView tvDate;
        TextView tvDayName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex   = itemView.findViewById(R.id.tv_index);
            tvDate    = itemView.findViewById(R.id.tv_date);
            tvDayName = itemView.findViewById(R.id.tv_day_name);
        }
    }
}
