package com.example.stackoverflowapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.stackoverflowapi.R;
import com.example.stackoverflowapi.adapter.UsersAdapter;
import com.example.stackoverflowapi.model.User;
import com.example.stackoverflowapi.model.UsersRecived;
import com.example.stackoverflowapi.rest.UserEndPoints;
import com.example.stackoverflowapi.rest.client.APIClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<User> myDataSource = new ArrayList<>();
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniLiaz();

        loadUsers();

    }

    public void iniLiaz() {
        mRecyclerView = (RecyclerView) findViewById(R.id.usersRecylerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new UsersAdapter(myDataSource, getApplicationContext(), R.layout.list_item_user);

        mRecyclerView.setAdapter(myAdapter);
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


    private void loadUsers() {
        UserEndPoints apiServices =
                APIClient.getClient().create(UserEndPoints.class);

        Call<UsersRecived> call = apiServices.getUsers("reputation");
        call.enqueue(new Callback<UsersRecived>() {
            @Override
            public void onResponse(Call<UsersRecived> call, Response<UsersRecived> response) {

                myDataSource.clear();
                myDataSource.addAll(response.body().getUsers());
                myAdapter.notifyDataSetChanged();

                List<User> users = response.body().getUsers();
                for (User user : users) {
                    System.out.println(
                            "Name: " + user.getUserName() +
                                    "; Location: " + user.getLocation() +
                                    "; Reputation: " + user.getReputation()
                );

                    System.out.println("Badges: ");

                    for (Map.Entry<String, Integer> entry : user.getBadge().entrySet()) {
                        String key = entry.getKey().toString();
                        Integer value = entry.getValue();
                        System.out.println(key + ":" + value);
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersRecived> call, Throwable t) {

                System.out.println("FAILURE");
            }
        });

    }
}