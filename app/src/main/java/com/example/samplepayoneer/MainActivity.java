package com.example.samplepayoneer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplepayoneer.Adaptors.ItemTypeAdapterFactory;
import com.example.samplepayoneer.Adaptors.RecyclerAdaptor;
import com.example.samplepayoneer.Models.ModelListOfPaymentOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private final ArrayList<ModelListOfPaymentOptions> paymentsOptionsLists = new ArrayList<>();
    private RecyclerView rv;
    private RecyclerAdaptor adapter;
    private ShimmerFrameLayout mFrameLayout;
    private static final String baseURL = "https://raw.githubusercontent.com/optile/checkout-android/develop/shared-test/lists/";
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mFrameLayout = findViewById(R.id.shimmerLayout);
        rv = findViewById(R.id.recyclerview);
        constraintLayout = findViewById(R.id.mainLayout);

        adapter = new RecyclerAdaptor(paymentsOptionsLists,MainActivity.this);
        rv.setAdapter(adapter);

        mFrameLayout.startShimmer();
        rv.setVisibility(View.GONE);
        rv.setLayoutManager(new LinearLayoutManager(this));


        //To simulate a delayed response
        rv.postDelayed(this::getData, 2000);


    }

    private void getData() {


        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ItemTypeAdapterFactory()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<ModelListOfPaymentOptions>> call = retrofitAPI.getPaymentOptions();
        call.enqueue(new Callback<List<ModelListOfPaymentOptions>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModelListOfPaymentOptions>> call, @NonNull Response<List<ModelListOfPaymentOptions>> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        paymentsOptionsLists.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        mFrameLayout.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            showError("There was an error while loading your request. Please wait while we fix it");
                            Log.d(TAG, "onResponse: Error Code 404");
                            break;
                        case 500:
                            showError("Internal Server Error. Please wait while we fix it");
                            Log.d(TAG, "onResponse: Error Code 500");
                            break;
                        default:
                            showError("Fail to get the data. This could be due to a network issue or an internal error. Please try again after sometime.");
                            Log.d(TAG, "onResponse: Error " + response.code());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelListOfPaymentOptions>> call, @NonNull Throwable t) {
                Log.d(TAG, "onResponse: Network issue");
                showError("Fail to get the data. This could be due to a network issue or an internal error. Please try again after sometime.");

            }
        });
    }

    private void showError(String message){
        Snackbar snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_LONG).setAction("Refresh",
                view -> Toast.makeText(MainActivity.this,"Refresh Clicked",Toast.LENGTH_SHORT).show());

        snackbar.show();
    }
}