package edu.calstatela.cs594.model;

public class Classes {

	private int id;
	private String className;
	private String description;
	private String days;
	private String time;
	private String roomNumber;
	
	public Classes(){
		
	}
	public Classes(int newId, String newCN, String desc, String newDays, String newTime, String newRoomNumber){
		
		this.id = newId;
		this.className = newCN;
		this.description = desc;
		this.days = newDays;
		this.time = newTime;
		this.roomNumber = newRoomNumber;
		
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	@Override
    public String toString() {
      return "Class: " + className + " " + description + " Days: " + days + " Class Time: " + time;
    }
	
}
