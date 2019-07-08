package com.example.stackoverflowapi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowapi.R;
import com.example.stackoverflowapi.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private List<User> users;
    private Context context;
    private int rowLayout;

    Bitmap myImage;

    public UsersAdapter(List<User> users, Context context, int rowLayout) {
        this.setUsers(users);
        this.setContext(context);
        this.setRowLayout(rowLayout);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);

        return new UsersViewHolder(view);
    }

    public class ImageDownload extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        holder.userName.setText("Name: " + users.get(position).getUserName());
        holder.userReputation.setText("Reputation: " + users.get(position).getReputation());
        holder.userLocation.setText("Location: " + users.get(position).getLocation());

        //map
        Iterator<Map.Entry<String, Integer>> iterator =
                users.get(position).getBadge().entrySet().iterator();

        Map.Entry<String, Integer> pair = iterator.next();
        holder.goldenBadge.setText(pair.getKey() + " : ");
        holder.goldenValue.setText(pair.getValue().toString());

        pair = iterator.next();
        holder.silverBadge.setText(pair.getKey() + " : ");
        holder.silverValue.setText(pair.getValue().toString());

        pair = iterator.next();
        holder.bronzeBadge.setText(pair.getKey() + " : ");
        holder.bronzeValue.setText(pair.getValue().toString());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        LinearLayout usersLayout;
        ImageView avatar;
        TextView userName, userReputation, userLocation;

        TextView goldenBadge, goldenValue, silverBadge, silverValue, bronzeBadge, bronzeValue;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);


            usersLayout = (LinearLayout)itemView.findViewById(R.id.usersLayout);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userReputation = (TextView) itemView.findViewById(R.id.userReputation);
            userLocation = (TextView) itemView.findViewById(R.id.userLocation);
            goldenBadge = (TextView) itemView.findViewById(R.id.goldenBadge);
            goldenValue = (TextView) itemView.findViewById(R.id.goldenValue);
            silverBadge = (TextView) itemView.findViewById(R.id.silverBadge);
            silverValue = (TextView) itemView.findViewById(R.id.silverValue);
            bronzeBadge = (TextView) itemView.findViewById(R.id.bronzeBadge);
            bronzeValue = (TextView) itemView.findViewById(R.id.bronzeValue);
        }
    }


}
