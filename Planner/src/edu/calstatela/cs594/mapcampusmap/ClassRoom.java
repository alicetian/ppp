package edu.calstatela.cs594.mapcampusmap;

public class ClassRoom {
	private Integer id;
	private String name;
	private Double lat, lon;
	
	public ClassRoom() {
		
	}
	
	public ClassRoom(String name, double lat, double lon) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	@Override 
	public String toString() {
		return id.toString() + " " + name + "(" + lat + ", " + lon + ")";
	}

}
