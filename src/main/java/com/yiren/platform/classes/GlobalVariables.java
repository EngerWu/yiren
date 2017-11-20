package com.yiren.platform.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;


public class GlobalVariables {
	
	public static Logger logger = Logger.getLogger(GlobalVariables.class); // *保存日志
	
	public static String TrajectoryPath="";
	public static String PicturnPath="";
	public static String TrajectoryURL="";
	public static String PictureURL="";
	public static String ClientAccesskey="";
	public static String FacilityToAccesskey="";
	
	public static HashMap<String,Client> mapClients = new HashMap<String,Client>();
	public static HashMap<String,String> mapFacilityToAccesskey = new HashMap<String,String>();
	
	
	static{
		logger.debug("*****************开始加载配置文件***********************");
		try {
			InputStreamReader is = new InputStreamReader(GlobalVariables.class.getClassLoader().getResourceAsStream("config.properties"),"UTF-8");
			Properties p = new Properties();
			p.load(is);
			is.close();
			is = null;
			TrajectoryPath = p.getProperty("TrajectoryPath");
			PicturnPath = p.getProperty("PicturnPath");
			TrajectoryURL = p.getProperty("TrajectoryURL");
			PictureURL = p.getProperty("PictureURL");
			ClientAccesskey = p.getProperty("ClientAccesskey");
			FacilityToAccesskey = p.getProperty("FacilityToAccesskey");
			p.clear();
			p = null;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("配置文件加载错误，请查看!");
		}
		logger.debug("#############配置文件加载结束#############");
		
		logger.debug("*************开始加载客户列表*************");
		LoadClientAccesskey();
		logger.debug("#############加载客户列表结束#############");
		
		logger.debug("*************开始加载设备到客户对应表*************");
		LoadFacilityToAccesskey();
		logger.debug("#############加载设备到客户对应表结束#############");
	}
	//TODO 初始化
	public static void Initiate(){
		
	}
	private static void LoadClientAccesskey(){
		synchronized(mapClients){
			try {
				String encoding="UTF-8";
	            File file=new File(ClientAccesskey);
	            if(file.isFile() && file.exists()){ //判断文件是否存在
	                InputStreamReader read = new InputStreamReader(
	                new FileInputStream(file),encoding);//考虑到编码格式
	                BufferedReader bufferedReader = new BufferedReader(read);
	                String lineTxt = null;
	                while((lineTxt = bufferedReader.readLine()) != null){
	                	if(lineTxt.length()<10){
	                    	continue;
	                    }
	                    //System.out.println(lineTxt);
	                	lineTxt=lineTxt.trim();
	                	char t0 = lineTxt.charAt(0);
	                	char t1 = lineTxt.charAt(1);
	                    if(t0=='#' || t1=='#'){
	                    	continue;
	                    }
	                    String[] items = lineTxt.split(",");
	                    Client c = new Client(items[0],items[1],items[2],items[3]);
	                    mapClients.put(c.getAccessKey(), c);
	                }
	                read.close();
	                }else{
	               	 logger.error("ClientAccesskey File Not Exist"+ClientAccesskey);
	               	 }
	            } catch (Exception e) {
	           	 logger.error("Exception:", e);
	           	 e.printStackTrace();
	           	 }
		}
	}
	private static void LoadFacilityToAccesskey(){
		
		synchronized(mapFacilityToAccesskey){
			try {
				String encoding="UTF-8";
	            File file=new File(FacilityToAccesskey);
	            if(file.isFile() && file.exists()){ //判断文件是否存在
	                InputStreamReader read = new InputStreamReader(
	                new FileInputStream(file),encoding);//考虑到编码格式
	                BufferedReader bufferedReader = new BufferedReader(read);
	                String lineTxt = null;
	                while((lineTxt = bufferedReader.readLine()) != null){
	                    if(lineTxt.startsWith("﻿#")){
	                    	continue;
	                    }
	                    String[] items = lineTxt.split(",");
	                    mapFacilityToAccesskey.put(items[0],items[1]);
	                }
	                read.close();
	                }else{
	               	 logger.error("FacilityToAccesskey File Not Exist"+FacilityToAccesskey);
	               	 }
	            } catch (Exception e) {
	           	 logger.error("Exception:", e);
	           	 e.printStackTrace();
	           	 }
		}
		
	}
	//添加设备号
	public static void AddFacilityID(String facility_id,String ak){
		synchronized(mapFacilityToAccesskey){
			if(mapFacilityToAccesskey.containsKey(facility_id)){
				return;
				}
			mapFacilityToAccesskey.put(facility_id, ak);
			// 更新映射表 FacilityToAccesskey
			BufferedWriter fw = null;
			try {
				File file = new File(FacilityToAccesskey);
				if(!file.exists()){
					if(!file.createNewFile()){
						logger.error("createNewFile faild:"+FacilityToAccesskey);
						return;
					}
				}
				fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
				fw.append(facility_id+","+ak);
				fw.flush(); // 全部写入缓存中的内容
			} catch (Exception e) {
				logger.error("Exception:", e);
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						logger.error("IOException:", e);
						e.printStackTrace();
					}
				}
			}
			}
	}
	
	//根据设备号取所属客户
	public static Client getClientByFaciltyID(String facility_id){
		Client c=null;
		String ak="";
		synchronized(mapFacilityToAccesskey){
			if(mapFacilityToAccesskey.containsKey(facility_id)){
				ak = mapFacilityToAccesskey.get(facility_id);
				}
			}
		
		synchronized(mapClients){
			if(mapClients.containsKey(ak)){
				c = mapClients.get(ak);
				}
			}
		return c;
	}
	//根据设备号取所属客户
	public static Client getClientByAK(String ak){
		Client c=null;		
		synchronized(mapClients){
			if(mapClients.containsKey(ak)){
				c = mapClients.get(ak);
				}
			}
		return c;
	}
}
