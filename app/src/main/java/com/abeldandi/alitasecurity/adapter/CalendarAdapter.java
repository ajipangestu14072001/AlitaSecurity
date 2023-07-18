package com.abeldandi.alitasecurity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abeldandi.alitasecurity.R;
import com.abeldandi.alitasecurity.model.CalendarDateModel;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {
    private ArrayList<CalendarDateModel> list = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CalendarDateModel calendarDateModel, int position);
    }

    public CalendarAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView calendarDay;
        TextView calendarDate;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            calendarDay = itemView.findViewById(R.id.tv_calendar_day);
            calendarDate = itemView.findViewById(R.id.tv_calendar_date);
            cardView = itemView.findViewById(R.id.card_calendar);
        }

        public void bind(final CalendarDateModel calendarDateModel) {
            if (calendarDateModel.isSelected()) {
                calendarDay.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                calendarDate.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
            } else {
                calendarDay.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                calendarDate.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            }

            calendarDay.setText(calendarDateModel.getCalendarDay());
            calendarDate.setText(calendarDateModel.getCalendarDate());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(calendarDateModel, position);
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_calendar_date, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(ArrayList<CalendarDateModel> calendarList) {
        list.clear();
        list.addAll(calendarList);
        notifyDataSetChanged();
    }
}
