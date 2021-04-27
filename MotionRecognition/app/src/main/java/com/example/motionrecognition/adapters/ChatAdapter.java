package adroidtown.org.graduateproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import adroidtown.org.graduateproject.R;
import adroidtown.org.graduateproject.models.Message;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private ArrayList<Message> userData = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_one, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(userData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public void addItems(ArrayList<Message> users) {
        userData = users;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView messageSend;
        TextView messageReceive;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageSend = itemView.findViewById(R.id.message_send);
            messageReceive = itemView.findViewById(R.id.message_receive);
        }

        public void onBind(Message user, int position) {
            if (position % 2 == 1) {
                messageReceive.setText(user.getTitle());
                messageReceive.setVisibility(View.VISIBLE);
                messageSend.setVisibility(View.GONE);
            } else {
                messageSend.setText(user.getTitle());
                messageSend.setVisibility(View.VISIBLE);
                messageReceive.setVisibility(View.GONE);
            }
        }
    }
}
