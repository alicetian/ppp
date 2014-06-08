package edu.calstatela.cs594.model;

public class Homework {
	
	private int id;
	private String className;
	private String description;
	private String time;
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Homework(){
		
	}
	public Homework(int newid, String cN, String desc, String nTime, String nDate){
		
		this.id = newid;
		this.className = cN;
		this.description = desc;
		this.time = nTime;
		this.date = nDate;
		
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
    public String toString() {
      return "Class: " + className + " " + description + " Date: " + date + " Class Time: " + time;
    }

}
