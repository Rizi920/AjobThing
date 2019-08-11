package com.example.jobportal.models;

import com.google.gson.annotations.SerializedName;

public class Salary extends Object {

	@SerializedName("maximum")
	private String maximum;

	@SerializedName("currency")
	private String currency;

	@SerializedName("minimum")
	private String minimum;

	public void setMaximum(String maximum){
		this.maximum = maximum;
	}

	public String getMaximum(){
		return maximum;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setMinimum(String minimum){
		this.minimum = minimum;
	}

	public String getMinimum(){
		return minimum;
	}

	@Override
 	public String toString(){
		return 
			"Salary{" + 
			"maximum = '" + maximum + '\'' + 
			",currency = '" + currency + '\'' + 
			",minimum = '" + minimum + '\'' + 
			"}";
		}
}