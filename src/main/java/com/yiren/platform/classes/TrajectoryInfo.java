package com.yiren.platform.classes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Iterator;
import java.util.Map;



//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.yiren.util.Download;
import com.yiren.util.HttpRequest;

public class TrajectoryInfo {
	
    //private String ="";
    //private String requestURL="";
    
	public TrajectoryInfo(String ak,int serid,String facid,String fileurl,long st,long et){
		this.AccessKey=ak;
		this.ServiceID=serid;
		this.FacilityID=facid;
		this.FileUrl=fileurl;
		this.StartTime=st;
		this.EndTime=et;
	}
	private String AccessKey = "";
	private int ServiceID = -1;
	private String FacilityID = "";
	private String FileUrl = "";
	private long StartTime = -1;
	private long EndTime = -1;
	
	public void run(){
		//保存数据
		//TODO 组织保存路径
		String fileName = StartTime+"-"+EndTime+"_"+FileUrl.substring(FileUrl.lastIndexOf("/")+1);
		Date date = new Date(StartTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String saveFilePath=GlobalVariables.TrajectoryPath+File.separator
				+sdf.format(date)+File.separator
				+AccessKey+File.separator
				+ServiceID;
				
		if(Download.ERROR_SUCCESS!=Download.saveUrlAs(FileUrl, 
				saveFilePath,fileName, "GET")){
			return;
		}
		//通知第三方
		// TODO 生成请求URL,向所有客户发送
		String localURL=GlobalVariables.TrajectoryURL+"/"+fileName;
		String sendParam="ak="+AccessKey
				+"&"+"service_id="+ServiceID
				+"&"+"facility_id="+FacilityID
				+"&"+"file_url="+localURL
				+"&"+"start_time="+StartTime
				+"&"+"end_time="+EndTime;
		
		Iterator<Map.Entry<String,Client>> iter = GlobalVariables.mapClients.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,Client> entry = (Map.Entry<String,Client>) iter.next();
			//String key = entry.getKey();
			Client val = entry.getValue();
			HttpRequest.sendGet(val.getTrajectoryUrl(), sendParam);
			}
		
		
//		int errno=-1;
//		int trytimes=0;
		//String requestRet=
		
		/*while(errno!=0 && trytimes<5){
			 requestRet = 
			 //解析结果
			 JsonParser parser=new JsonParser();  //创建JSON解析器
			 JsonObject object=(JsonObject) parser.parse(requestRet);  //创建JsonObject对象
			 errno = object.get("errno").getAsInt();
			 if(errno!=0){
				 try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}*/
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
