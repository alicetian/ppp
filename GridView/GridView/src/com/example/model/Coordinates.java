package com.example.model;

public class Coordinates {

	private int id;
	private String className;
	private double longitude;
	private double latitude;
	
	public Coordinates(){
		
	}
	
	public Coordinates(int newId, String className, double longitude, double latitude){
		this.id = newId;
		this.className = className;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
