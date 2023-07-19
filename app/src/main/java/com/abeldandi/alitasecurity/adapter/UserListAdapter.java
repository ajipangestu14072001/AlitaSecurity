package com.abeldandi.alitasecurity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abeldandi.alitasecurity.ListElement;
import com.abeldandi.alitasecurity.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<ListElement> userList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserListAdapter(List<ListElement> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView securityname, idnumber, secureOff, lastMessageTextView, lastMessageTimeTextView;

        LinearLayout linearLayoutCV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            securityname = itemView.findViewById(R.id.securityname);
            secureOff = itemView.findViewById(R.id.secureOff);
            idnumber = itemView.findViewById(R.id.idnumber);
            linearLayoutCV = itemView.findViewById(R.id.linearLayoutCV);
            lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView);
            lastMessageTimeTextView = itemView.findViewById(R.id.lastMessageTimeTextView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ListElement user = userList.get(position);

        holder.secureOff.setVisibility(View.GONE);
        Glide.with(context)
                .load("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                .into(holder.iconImage);
        holder.securityname.setText(user.getSecurityname());
        holder.idnumber.setText(user.getIdnumber());
        holder.lastMessageTextView.setText(user.getLastMessage());
        holder.lastMessageTimeTextView.setText(user.getLastMessageTime());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

