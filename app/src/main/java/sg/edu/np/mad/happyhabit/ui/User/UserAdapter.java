package sg.edu.np.mad.happyhabit.ui.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;

    private FragmentManager fragmentManager;

    //contructor.

    public UserAdapter(FragmentManager fragmentManager) {

        this.fragmentManager=fragmentManager;
    }

    public void setRoutines(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
        //call the function notfydarsetchanged so that the changes in data would be reflected
    }

    public  List<User> getRoutines()
    {
        return users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_user_details, parent, false);
        return new UserViewHolder(view);//creating a viewholder
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
        private TextView textViewName;
        private TextView textViewEmail;
        private TextView textViewDescription;

        ImageView userImage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
            textViewDescription = itemView.findViewById(R.id.text_view_user_description);
        }

        public void bind(User user) {
            //userImage.setImageIcon(//add in user image list);
            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            textViewDescription.setText(user.getDescription());
        }
    }
}
