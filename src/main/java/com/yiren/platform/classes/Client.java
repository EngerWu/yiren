package com.yiren.platform.classes;

public class Client {
	private String AccessKey="";
	private String Name="";
	private String PicCmdUrl="";
	private String TrajectoryUrl="";
	
	public Client(String ak,String name,String picurl,String traurl){
		this.AccessKey=ak;
		this.Name=name;
		this.PicCmdUrl=picurl;
		this.TrajectoryUrl=traurl;
	}
	
	public void setAccessKey(String ak){
		this.AccessKey=ak;
	}
	public String getAccessKey(){
		return this.AccessKey;
	}

	public void setName(String name){
		this.Name=name;
	}
	public String getName(){
		return this.Name;
	}
	
	public void setPicCmdUrl(String pcu){
		this.PicCmdUrl=pcu;
	}
	public String getPicCmdUrl(){
		return this.PicCmdUrl;
	}
	
	public void setTrajectoryUrl(String traurl){
		this.TrajectoryUrl=traurl;
	}
	public String getTrajectoryUrl(){
		return this.TrajectoryUrl;
	}
}
