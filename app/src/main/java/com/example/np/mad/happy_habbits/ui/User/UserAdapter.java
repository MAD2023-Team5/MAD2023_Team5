package com.example.np.mad.happy_habbits.ui.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.User;
import com.example.np.mad.happy_habbits.ui.User.UserAdapter;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;

    public UserAdapter(FragmentManager fragmentManager) {
    }

    public void setRoutines(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.selected_routine, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewEmail;
        private TextView textViewDescription;
        private TextView textViewUserNo;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);;
            textViewEmail = itemView.findViewById(R.id.text_view_email);
            textViewDescription = itemView.findViewById(R.id.text_view_user_description);
            textViewUserNo = itemView.findViewById(R.id.text_view_user_no);
        }

        public void bind(User user) {
            textViewEmail.setText(user.getEmail());
            textViewDescription.setText(user.getDescription());
            textViewUserNo.setText(String.valueOf(user.getUserNo()));
        }
    }
}
