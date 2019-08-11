package com.example.jobportal.network;


import com.example.jobportal.models.JobsRecommendationsResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiServices {

    @GET("/api/recommendation")
    Call<JobsRecommendationsResponse> getJobs();
}

