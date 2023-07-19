package com.abeldandi.alitasecurity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.abeldandi.alitasecurity.R;
import com.abeldandi.alitasecurity.model.Message;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> messages) {
        super(context, resource, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }

        Message message = getItem(position);

        TextView senderTextView = convertView.findViewById(R.id.userNameTextView);
        TextView messageTextView = convertView.findViewById(R.id.lastMessageTextView);

        messageTextView.setText(message.getMessage());

        if (message.isSender()) {
            senderTextView.setText(message.getTime());
            senderTextView.setVisibility(View.VISIBLE);
            messageTextView.setBackgroundResource(R.drawable.outgoing_message_bg);

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) senderTextView.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_END, R.id.messageCardView);
            senderTextView.setLayoutParams(layoutParams);
        } else {
            senderTextView.setText(message.getTime());
            senderTextView.setVisibility(View.VISIBLE);
            messageTextView.setBackgroundResource(R.drawable.incoming_message_bg);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
            convertView.findViewById(R.id.messageCardView).setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) senderTextView.getLayoutParams();
            layoutParams2.addRule(RelativeLayout.ALIGN_START, R.id.messageCardView);
            senderTextView.setLayoutParams(layoutParams2);
        }

        return convertView;
    }

}
