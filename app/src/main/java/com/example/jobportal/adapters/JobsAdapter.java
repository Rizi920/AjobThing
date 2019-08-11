package com.example.jobportal.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jobportal.R;
import com.example.jobportal.listeners.JobsAdapterCallBack;
import com.example.jobportal.utils.BaseUtlis;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private ArrayList<String> company_name = new ArrayList<>();
    private ArrayList<String> posting_date = new ArrayList<>();
    private ArrayList<String> job_title = new ArrayList<>();
    private ArrayList<String> job_type = new ArrayList<>();
    private ArrayList<String> job_location = new ArrayList<>();
    private ArrayList<String> image = new ArrayList<>();
    private JobsAdapterCallBack jobsAdapterCallBack;
    private ArrayList<Integer> id = new ArrayList<>();
    SharedPreferences sharedPrefs;
    private static final String MY_PREFS_NAME = "Shared_pref";
    private BaseUtlis baseUtlis;

    private Context mContext;

    public JobsAdapter(ArrayList<String> company_name, ArrayList<String> posting_date, ArrayList<String> job_title, ArrayList<String> job_type, ArrayList<String> job_location, ArrayList<Integer> id, ArrayList<String> image, Context mContext, JobsAdapterCallBack jobsAdapterCallBack) {
        this.company_name = company_name;
        this.posting_date = posting_date;
        this.job_title = job_title;
        this.job_type = job_type;
        this.job_location = job_location;
        this.image = image;
        this.mContext = mContext;
        this.jobsAdapterCallBack = jobsAdapterCallBack;
        this.id = id;
        sharedPrefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_job, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        baseUtlis = new BaseUtlis();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.job_title.setText(job_title.get(i));
        viewHolder.posting_date.setText(posting_date.get(i));
        viewHolder.job_type.setText(job_type.get(i));
        viewHolder.job_location.setText(job_location.get(i));
        viewHolder.company_name.setText(company_name.get(i));
        baseUtlis.loadImageGlide(mContext, image.get(i), viewHolder.image);
        if (sharedPrefs.contains("id_" + id.get(i))) {
            viewHolder.love.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_filled));
        }
        viewHolder.love.setOnClickListener(v -> jobsAdapterCallBack.onLoveClickCallback(i, viewHolder.love));
        viewHolder.jobClick.setOnClickListener(v -> jobsAdapterCallBack.onJobClickCallback(i));

    }

    @Override
    public int getItemCount() {
        return job_title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView job_title;
        TextView posting_date;
        TextView job_type;
        TextView job_location;
        TextView company_name;
        ImageView image;
        ImageView love;
        ConstraintLayout jobClick;
        ConstraintLayout childLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            job_title = itemView.findViewById(R.id.tv_post);
            posting_date = itemView.findViewById(R.id.tv_posting_time);
            job_type = itemView.findViewById(R.id.tv_job_category);
            job_location = itemView.findViewById(R.id.tv_location);
            company_name = itemView.findViewById(R.id.tv_company_name);
            image = itemView.findViewById(R.id.iv_main_banner);
            love = itemView.findViewById(R.id.iv_love);
            jobClick = itemView.findViewById(R.id.onClick_layout);


        }
    }
}
