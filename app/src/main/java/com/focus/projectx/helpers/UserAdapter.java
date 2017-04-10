package com.focus.projectx.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.focus.projectx.R;
import com.focus.projectx.model.AllUserModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.focus.projectx.helpers.Urls.USER_AVATAR;

/**
 * Created by Focus on 08.04.2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private AllUserModel allUserModel;
    private Context context;

    public UserAdapter(Context context, AllUserModel allUserModel){
        this.context = context;
        this.allUserModel = allUserModel;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(allUserModel.getUser().get(position).getPersonFirstName() + " " + allUserModel.getUser().get(position).getPersonLastName());
        holder.genre.setText(allUserModel.getUser().get(position).getPersonDescription());
        Picasso.with(context)
                .load(USER_AVATAR + allUserModel.getUser().get(position).getPersonAvatar())
                .into(holder.imageView);
        Log.d("adapter",USER_AVATAR + allUserModel.getUser().get(position).getPersonAvatar());
    }

    @Override
    public int getItemCount() {
        return allUserModel.getUser().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,  genre;
        public CircleImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.person_name);
            genre = (TextView) view.findViewById(R.id.person_age);
            imageView = (CircleImageView) view.findViewById(R.id.person_photo);
        }
    }
}
