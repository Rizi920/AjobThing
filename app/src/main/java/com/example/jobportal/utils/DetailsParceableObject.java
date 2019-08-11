package com.example.jobportal.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsParceableObject implements Parcelable {


    String job_title;
    String company_name;
    String location;
    String min_salary;
    String max_salary;
    String tv_about_company;
    String about_company_value;
    String role;
    String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getLocation() {
        return location;
    }

    public String getMin_salary() {
        return min_salary;
    }

    public String getMax_salary() {
        return max_salary;
    }

    public String getTv_about_company() {
        return tv_about_company;
    }

    public String getAbout_company_value() {
        return about_company_value;
    }

    public String getRole() {
        return role;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getLogo() {
        return logo;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMin_salary(String min_salary) {
        this.min_salary = min_salary;
    }

    public void setMax_salary(String max_salary) {
        this.max_salary = max_salary;
    }

    public void setTv_about_company(String tv_about_company) {
        this.tv_about_company = tv_about_company;
    }

    public void setAbout_company_value(String about_company_value) {
        this.about_company_value = about_company_value;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    String requirements;
    String logo;


    public DetailsParceableObject(String job_title, String company_name, String location, String min_salary, String max_salary, String tv_about_company, String about_company_value, String role, String requirements, String logo, String id) {
        this.job_title = job_title;
        this.company_name = company_name;
        this.location = location;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
        this.tv_about_company = tv_about_company;
        this.about_company_value = about_company_value;
        this.role = role;
        this.requirements = requirements;
        this.logo = logo;
        this.id = id;
    }


    //parcel part
    public DetailsParceableObject(Parcel in) {
        String[] data = new String[11];

        in.readStringArray(data);
        job_title = data[0];
        company_name = data[1];
        location = data[2];
        min_salary = data[3];
        max_salary = data[4];
        tv_about_company = data[5];
        about_company_value = data[6];
        role = data[7];
        requirements = data[8];
        logo = data[9];
        id = data[10];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{this.job_title, this.company_name, this.location, this.min_salary, this.max_salary, this.tv_about_company, this.about_company_value, this.role, this.requirements, this.logo, this.id});
    }

    public static final Parcelable.Creator<DetailsParceableObject> CREATOR = new Parcelable.Creator<DetailsParceableObject>() {

        @Override
        public DetailsParceableObject createFromParcel(Parcel source) {
            return new DetailsParceableObject(source);  //using parcelable constructor
        }

        @Override
        public DetailsParceableObject[] newArray(int size) {
            return new DetailsParceableObject[size];
        }
    };
}
