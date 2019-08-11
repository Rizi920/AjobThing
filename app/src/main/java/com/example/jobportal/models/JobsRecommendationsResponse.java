package com.example.jobportal.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JobsRecommendationsResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("header")
	private String header;

	@SerializedName("status")
	private String status;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setHeader(String header){
		this.header = header;
	}

	public String getHeader(){
		return header;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"JobsRecommendationsResponse{" + 
			"data = '" + data + '\'' + 
			",header = '" + header + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}