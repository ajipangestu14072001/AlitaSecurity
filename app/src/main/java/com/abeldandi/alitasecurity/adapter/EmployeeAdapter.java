package com.abeldandi.alitasecurity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.abeldandi.alitasecurity.ListElement;
import com.abeldandi.alitasecurity.R;
import com.abeldandi.alitasecurity.callback.FetchRecyclerViewItems;
import com.bumptech.glide.Glide;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    private final FetchRecyclerViewItems listener;


    public EmployeeAdapter(List<ListElement> itemList, Context context, FetchRecyclerViewItems listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.employee_list, null);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EmployeeAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElement> items) { mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView securityname, idnumber;

        LinearLayout linearLayoutCV;


        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            securityname = itemView.findViewById(R.id.securityname);
            idnumber = itemView.findViewById(R.id.idnumber);
            linearLayoutCV = itemView.findViewById(R.id.linearLayoutCV);
        }

        void bindData(final ListElement item) {
            Glide.with(context)
                    .load("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                    .into(iconImage);
            securityname.setText(item.getSecurityname());
            idnumber.setText(item.getIdnumber());
            linearLayoutCV.setOnClickListener(view -> listener.onIntentEmployee(item));

        }
    }
}
