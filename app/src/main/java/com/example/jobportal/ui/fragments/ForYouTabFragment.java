package com.example.jobportal.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobportal.R;
import com.example.jobportal.adapters.JobsAdapter;
import com.example.jobportal.listeners.JobsAdapterCallBack;
import com.example.jobportal.listeners.JobsDataSuccess;
import com.example.jobportal.models.JobsRecommendationsResponse;
import com.example.jobportal.models.Salary;
import com.example.jobportal.network.ApiClient;
import com.example.jobportal.network.ApiServices;
import com.example.jobportal.ui.activities.DetailsActivity;
import com.example.jobportal.utils.BaseUtlis;
import com.example.jobportal.utils.DetailsParceableObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForYouTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForYouTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForYouTabFragment extends Fragment implements JobsDataSuccess, JobsAdapterCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "jobRecommendationsObject";

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    JobsAdapter ratesAdapter;
    ApiServices apiInterface;
    ProgressDialog mProgressDialog;
    JobsRecommendationsResponse jobsRecommendationsResponse;

    private String mParam1;
    private String mParam2;
    View mView;

    private OnFragmentInteractionListener mListener;

    public ForYouTabFragment() {
        // Required empty public constructor
    }


    public static ForYouTabFragment newInstance(String param1, String param2) {
        ForYouTabFragment fragment = new ForYouTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_for_you_tab, container, false);
        apiInterface = ApiClient.jobsInfo().create(ApiServices.class);
        TextView no_internet = mView.findViewById(R.id.tv_no_conection);
        TextView no_internet_explain = mView.findViewById(R.id.tv_no_connection_explain);
        ImageView no_interneted = mView.findViewById(R.id.iv_no_connection);

        sharedPrefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = sharedPrefs.edit();
        BaseUtlis obj = new BaseUtlis();
        if (obj.checkWifiConnection(getContext())) {
            no_internet.setVisibility(View.INVISIBLE);
            no_internet_explain.setVisibility(View.INVISIBLE);
            no_interneted.setVisibility(View.INVISIBLE);
            getJobsData(this);
        } else {
            no_internet.setVisibility(View.VISIBLE);
            no_internet_explain.setVisibility(View.VISIBLE);
            no_interneted.setVisibility(View.VISIBLE);
        }
        return mView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onJobDataSuccess(JobsRecommendationsResponse jobsRecommendationsResponse) {
        Gson gson = new Gson();
        String json = gson.toJson(jobsRecommendationsResponse);
        editor.putString(DETAILS_PARCEABLE_OBJECT, json);
        editor.commit();
        int numberOfColumns = 2;

        ArrayList<String> job_title = new ArrayList<>();
        ArrayList<String> posting_date = new ArrayList<>();
        ArrayList<String> company_name = new ArrayList<>();
        ArrayList<String> job_location = new ArrayList<>();
        ArrayList<String> job_type = new ArrayList<>();
        ArrayList<String> image = new ArrayList<>();
        ArrayList<Integer> id = new ArrayList<>();


        for (int i = 0; i < jobsRecommendationsResponse.getData().size(); i++) {
            job_title.add(jobsRecommendationsResponse.getData().get(i).getJobTitle());
            posting_date.add(jobsRecommendationsResponse.getData().get(i).getCreatedAt());
            company_name.add(jobsRecommendationsResponse.getData().get(i).getCompanyName());
            job_location.add(jobsRecommendationsResponse.getData().get(i).getCountry());
            job_type.add(jobsRecommendationsResponse.getData().get(i).getJobType());
            image.add(jobsRecommendationsResponse.getData().get(i).getLogo());
            id.add(jobsRecommendationsResponse.getData().get(i).getId());

        }

        RecyclerView recyclerView = mView.findViewById(R.id.rv_for_you);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        ratesAdapter = new JobsAdapter(company_name, posting_date, job_title, job_type, job_location, id, image, getContext(), this);
        recyclerView.setAdapter(ratesAdapter);
    }


    @Override
    public void onLoveClickCallback(int position, ImageView love) {
        int id = jobsRecommendationsResponse.getData().get(position).getId();
        if (sharedPrefs.contains("id_" + id)) {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
            editor.remove("id_" + id);
            editor.apply();
        } else {
            editor.putInt("id_" + id, id);
            editor.apply();
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));
        }
    }

    @Override
    public void onJobClickCallback(int position) {
        String min_salary;
        String max_salary;
        String job_title = jobsRecommendationsResponse.getData().get(position).getJobTitle();
        String company_name = jobsRecommendationsResponse.getData().get(position).getCompanyName();
        String location = jobsRecommendationsResponse.getData().get(position).getCountry();
        if(jobsRecommendationsResponse.getData().get(position).getSalary() instanceof ArrayList<?>){
            min_salary = "";
            max_salary = "";
        }else{
            Object x=jobsRecommendationsResponse.getData().get(position).getSalary();
            String mJsonString = x.toString();
            JsonParser parser = new JsonParser();
            JsonElement mJson =  parser.parse(mJsonString);
            Gson gson = new Gson();
            Salary object = gson.fromJson(mJson, Salary.class);
            min_salary=object.getMinimum();
            max_salary="-"+object.getMaximum();
        }
        String tv_about_company = jobsRecommendationsResponse.getData().get(position).getCompanyName();
        String about_company_value = jobsRecommendationsResponse.getData().get(position).getDescription();
        String role = jobsRecommendationsResponse.getData().get(position).getResponsibility();
        String requirements = jobsRecommendationsResponse.getData().get(position).getRequirement();
        String logo = jobsRecommendationsResponse.getData().get(position).getLogo();
        String id = String.valueOf(jobsRecommendationsResponse.getData().get(position).getId());
        DetailsParceableObject detailsParceableObject = new DetailsParceableObject(job_title, company_name, location, min_salary, max_salary, tv_about_company, about_company_value, role, requirements, logo, id);
        Intent myIntent = new Intent(getContext(), DetailsActivity.class);
        myIntent.putExtra(DETAILS_PARCEABLE_OBJECT, detailsParceableObject);
        getActivity().startActivity(myIntent);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void getJobsData(JobsDataSuccess callBack) {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        apiInterface.getJobs().enqueue(new Callback<JobsRecommendationsResponse>() {
            @Override
            public void onResponse(Call<JobsRecommendationsResponse> call, Response<JobsRecommendationsResponse> response) {
                if (response.code() == 200) {
                    jobsRecommendationsResponse = response.body();
                    mProgressDialog.dismiss();
                    callBack.onJobDataSuccess(jobsRecommendationsResponse);
                }

            }

            @Override
            public void onFailure(Call<JobsRecommendationsResponse> call, Throwable t) {
                Log.d("Error305", t.getMessage());
                mProgressDialog.dismiss();

            }
        });

    }

}
