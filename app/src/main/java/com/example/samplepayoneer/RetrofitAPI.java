package com.example.samplepayoneer;

import com.example.samplepayoneer.Models.ModelListOfPaymentOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

    @GET("listresult.json")
    Call<List<ModelListOfPaymentOptions>> getPaymentOptions();
}
