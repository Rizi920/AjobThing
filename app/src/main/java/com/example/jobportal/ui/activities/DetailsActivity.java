package com.example.jobportal.ui.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobportal.R;
import com.example.jobportal.utils.BaseUtlis;
import com.example.jobportal.utils.DetailsParceableObject;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPrefs;
    private static final String MY_PREFS_NAME = "Shared_pref";
    private static final String DETAILS_PARCEABLE_OBJECT = "jobRecommendationsObject";
    TextView job_title;
    TextView comapny_name;
    TextView location;
    TextView min_salary;
    TextView max_salary;
    TextView tv_about_company;
    TextView about_company_value;
    TextView role;
    TextView requirements;
    ImageView love;
    DetailsParceableObject detailsParceableObject;
    ImageView job_banner;
    BaseUtlis baseUtlis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        findViewById(R.id.iv_close).setOnClickListener(this);


    }

    public void init() {
        sharedPrefs = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        job_title = findViewById(R.id.tv_job_title);
        comapny_name = findViewById(R.id.tv_company_name);
        location = findViewById(R.id.tv_location);
        min_salary = findViewById(R.id.tv_min_salalry);
        max_salary = findViewById(R.id.tv_max_salary);
        tv_about_company = findViewById(R.id.tv_about_company);
        about_company_value = findViewById(R.id.tv_about_company_value);
        role = findViewById(R.id.tv_job_role_value);
        requirements = findViewById(R.id.tv_requirements_value);
        love = findViewById(R.id.iv_love_details);
        detailsParceableObject = getIntent().getParcelableExtra(DETAILS_PARCEABLE_OBJECT);
        job_banner = findViewById(R.id.iv_job_banner);
        baseUtlis=new BaseUtlis();


        populateUi();


    }

    public void populateUi() {
        job_title.setText(detailsParceableObject.getJob_title());
        comapny_name.setText(detailsParceableObject.getCompany_name());
        location.setText(detailsParceableObject.getLocation());
        min_salary.setText(detailsParceableObject.getMin_salary());
        max_salary.setText(detailsParceableObject.getMax_salary());
        tv_about_company.setText("About " + detailsParceableObject.getTv_about_company());
        about_company_value.setText(detailsParceableObject.getAbout_company_value());
        role.setText(Html.fromHtml(Html.fromHtml(detailsParceableObject.getRole()).toString()));
        requirements.setText(Html.fromHtml(Html.fromHtml(detailsParceableObject.getRequirements()).toString()));
        baseUtlis.loadImageGlide(this,detailsParceableObject.getLogo(),job_banner);
        if (sharedPrefs.contains("id_" + detailsParceableObject.getId())) {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_filled));
        } else {
            love.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
        }
    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.iv_close)) {
            onBackPressed();
        }

    }
}
