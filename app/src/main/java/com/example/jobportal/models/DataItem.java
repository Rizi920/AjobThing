package com.example.jobportal.models;

import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("job_type")
	private String jobType;

	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("description")
	private String description;

	@SerializedName("requirement")
	private String requirement;

	@SerializedName("salary")
	private Object salary;

	@SerializedName("responsibility")
	private String responsibility;

	@SerializedName("share_url")
	private String shareUrl;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("logo")
	private String logo;

	@SerializedName("id")
	private int id;

	@SerializedName("job_title")
	private String jobTitle;

	public void setJobType(String jobType){
		this.jobType = jobType;
	}

	public String getJobType(){
		return jobType;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setRequirement(String requirement){
		this.requirement = requirement;
	}

	public String getRequirement(){
		return requirement;
	}

	public void setSalary(Salary salary){
		this.salary = salary;
	}

	public Object getSalary(){
		return salary;
	}

	public void setResponsibility(String responsibility){
		this.responsibility = responsibility;
	}

	public String getResponsibility(){
		return responsibility;
	}

	public void setShareUrl(String shareUrl){
		this.shareUrl = shareUrl;
	}

	public String getShareUrl(){
		return shareUrl;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setJobTitle(String jobTitle){
		this.jobTitle = jobTitle;
	}

	public String getJobTitle(){
		return jobTitle;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"job_type = '" + jobType + '\'' + 
			",country = '" + country + '\'' + 
			",city = '" + city + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",description = '" + description + '\'' + 
			",requirement = '" + requirement + '\'' + 
			",salary = '" + salary + '\'' + 
			",responsibility = '" + responsibility + '\'' + 
			",share_url = '" + shareUrl + '\'' + 
			",company_name = '" + companyName + '\'' + 
			",logo = '" + logo + '\'' + 
			",id = '" + id + '\'' + 
			",job_title = '" + jobTitle + '\'' + 
			"}";
		}
}