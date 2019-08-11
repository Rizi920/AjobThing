package com.example.jobportal.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.jobportal.R;
import com.example.jobportal.adapters.JobsAdapter;
import com.example.jobportal.adapters.MyPagerAdapter;
import com.example.jobportal.listeners.JobsAdapterCallBack;
import com.example.jobportal.models.JobsRecommendationsResponse;
import com.example.jobportal.models.Salary;
import com.example.jobportal.ui.fragments.ForYouTabFragment;
import com.example.jobportal.ui.fragments.SavedTabFragment;
import com.example.jobportal.utils.DetailsParceableObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SavedTabFragment.OnFragmentInteractionListener, ForYouTabFragment.OnFragmentInteractionListener {

    TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;
    SearchView searchView;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    TextView no_search_results;
    TextView no_search_results_explain;
    ImageView no_search_result;
    ConstraintLayout hidden_layout;
    RecyclerView hiddenRecyclerview;
    JobsRecommendationsResponse jobsRecommendationsResponse;
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "jobRecommendationsObject";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        no_search_result = findViewById(R.id.iv_no_search_results);
        no_search_results = findViewById(R.id.tv_no_search_results);
        no_search_results_explain = findViewById(R.id.tv_no_search_results_explain);
        hiddenRecyclerview = findViewById(R.id.rv_hidden_search);
        init();
        setupTabLayout();
        searchView = findViewById(R.id.sv_search_jobs);
        searchView.setOnCloseListener(() -> {
            ConstraintLayout hidden_layout = findViewById(R.id.hidden_constraint_layout);
            hidden_layout.setVisibility(View.INVISIBLE);
            LinearLayout linearLayout = findViewById(R.id.linera_layout_pager);
            linearLayout.setVisibility(View.VISIBLE);
            init();
            setupTabLayout();
            return true;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hidden_layout = findViewById(R.id.hidden_constraint_layout);
                hidden_layout.setVisibility(View.VISIBLE);
                LinearLayout linearLayout = findViewById(R.id.linera_layout_pager);
                linearLayout.setVisibility(View.INVISIBLE);
                sharedPrefs = MainActivity.this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                editor = sharedPrefs.edit();
                Gson gson = new Gson();
                String json = sharedPrefs.getString(DETAILS_PARCEABLE_OBJECT, "");
                jobsRecommendationsResponse = gson.fromJson(json, JobsRecommendationsResponse.class);
                int numberOfColumns = 2;
                ArrayList<String> job_title = new ArrayList<>();
                ArrayList<String> posting_date = new ArrayList<>();
                ArrayList<String> company_name = new ArrayList<>();
                ArrayList<String> job_location = new ArrayList<>();
                ArrayList<String> job_type = new ArrayList<>();
                ArrayList<String> image = new ArrayList<>();
                ArrayList<Integer> id = new ArrayList<>();


                for (int i = 0; i < jobsRecommendationsResponse.getData().size(); i++) {
                    if (jobsRecommendationsResponse.getData().get(i).getCompanyName().toLowerCase().contains(query.toLowerCase())) {
                        job_title.add(jobsRecommendationsResponse.getData().get(i).getJobTitle());
                        posting_date.add(jobsRecommendationsResponse.getData().get(i).getCreatedAt());
                        company_name.add(jobsRecommendationsResponse.getData().get(i).getCompanyName());
                        job_location.add(jobsRecommendationsResponse.getData().get(i).getCountry());
                        job_type.add(jobsRecommendationsResponse.getData().get(i).getJobType());
                        image.add(jobsRecommendationsResponse.getData().get(i).getLogo());
                        id.add(jobsRecommendationsResponse.getData().get(i).getId());
                    }
                }

                if (id.size() == 0) {
                    hiddenRecyclerview.setVisibility(View.INVISIBLE);
                    no_search_result.setVisibility(View.VISIBLE);
                    no_search_results.setVisibility(View.VISIBLE);
                    no_search_results_explain.setVisibility(View.VISIBLE);
                } else {
                    init();
                    RecyclerView recyclerView = findViewById(R.id.rv_hidden_search);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));
                    JobsAdapter ratesAdapter = new JobsAdapter(company_name, posting_date, job_title, job_type, job_location, id, image, MainActivity.this, new JobsAdapterCallBack() {

                        @Override
                        public void onLoveClickCallback(int position, ImageView love) {

                        }

                        @Override
                        public void onJobClickCallback(int position) {
                            String min_salary;
                            String max_salary;
                            String job_title = jobsRecommendationsResponse.getData().get(position).getJobTitle();
                            String company_name = jobsRecommendationsResponse.getData().get(position).getCompanyName();
                            String location = jobsRecommendationsResponse.getData().get(position).getCountry();
                            if (jobsRecommendationsResponse.getData().get(position).getSalary() instanceof ArrayList<?>) {
                                min_salary = "";
                                max_salary = "";
                            } else {
                                Object x = jobsRecommendationsResponse.getData().get(position).getSalary();
                                String mJsonString = x.toString();
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(mJsonString);
                                Gson gson = new Gson();
                                Salary object = gson.fromJson(mJson, Salary.class);
                                min_salary = object.getMinimum();
                                max_salary = "-" + object.getMaximum();
                            }
                            String tv_about_company = jobsRecommendationsResponse.getData().get(position).getCompanyName();
                            String about_company_value = jobsRecommendationsResponse.getData().get(position).getDescription();
                            String role = jobsRecommendationsResponse.getData().get(position).getResponsibility();
                            String requirements = jobsRecommendationsResponse.getData().get(position).getRequirement();
                            String logo = jobsRecommendationsResponse.getData().get(position).getLogo();
                            String id = String.valueOf(jobsRecommendationsResponse.getData().get(position).getId());
                            DetailsParceableObject detailsParceableObject = new DetailsParceableObject(job_title, company_name, location, min_salary, max_salary, tv_about_company, about_company_value, role, requirements, logo, id);
                            Intent myIntent = new Intent(MainActivity.this, DetailsActivity.class);
                            myIntent.putExtra(DETAILS_PARCEABLE_OBJECT, detailsParceableObject);
                            MainActivity.this.startActivity(myIntent);

                        }
                    });
                    recyclerView.setAdapter(ratesAdapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void init() {

        tabLayout = findViewById(R.id.tab_layout);
        hiddenRecyclerview.setVisibility(View.VISIBLE);
        no_search_result.setVisibility(View.INVISIBLE);
        no_search_results.setVisibility(View.INVISIBLE);
        no_search_results_explain.setVisibility(View.INVISIBLE);
    }

    private void setupTabLayout() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        ViewPager vpPager = findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(vpPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
