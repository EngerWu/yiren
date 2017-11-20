package com.yiren.platform.classes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.yiren.util.Download;
import com.yiren.util.StringUtil;

public class PicInfo {
	public static final int RESULT_SUCCESS=0;
	public static final int RESULT_DOWNLOAD__FAIL=1;
	public static final int ERROR_SAVE_FAIL=2;
	
	private  static HashMap<String,PicInfo>  pictureMap = new HashMap<String,PicInfo> ();
	
	public PicInfo(String accesskey,int serviceid,String seq,int cmdtype,String msg,String[] d ){
		ak=accesskey;
		service_id=serviceid;
		seqid=seq;
		cmd_type=cmdtype;
		messge=msg;
		data=d;
		
		for(int i=0;i<data.length;i++){
			String[] items = data[i].split(",");
			String strTime = StringUtil.trim(items[0], "\"");
			Long time=Long.parseLong(strTime);
			String picUrl=StringUtil.trim(items[1], "\'");
			
			String picName=time+"_"+picUrl.substring(picUrl.lastIndexOf('/')+1);
			
			String localPicUrl=GlobalVariables.PictureURL+"/"+picName;
			
			//strRet+="&"+"data="+"\""+time+"\":"+"\'"+localPicUrl+"\'";
		}
	}
	//Accesskey
	private String ak="";
	private int service_id=-1;
	private String seqid="";
	private int cmd_type=-1;
	private String messge="";
	private String[] data;
	
	public void setAcessKey(String accesskey){
		ak=accesskey;
	}
	public String getAcessKey(){
		return ak;
	}
	
	public void setServiceID(int serviceid){
		service_id=serviceid;
	}
	public int getServiceID(){
		return service_id;
	}
	
	public void setSeqID(String seq){
		seqid=seq;
	}
	public String getSeqID(){
		return seqid;
	}
	
	public void setCmdType(int cmdtype){
		cmd_type=cmdtype;
	}
	public int getCmdType(){
		return cmd_type;
	}
	
	public void setMessage(String msg){
		messge=msg;
	}
	public String getMessage(){
		return messge;
	}
	
	public void setData(String[] d){
		data=d;
	}
	public String[] getData(){
		return data;
	}
	
	public String getPicCompletedCmd(){
		String strRet="ak="+ak
				+"&"+"service_id="+service_id
				+"&"+"seqid="+seqid
				+"&"+"cmd_type="+cmd_type
				+"&"+"messge="+messge;
		for(int i=0;i<data.length;i++){
			String[] items = data[i].split(",");
			String strTime = StringUtil.trim(items[0], "\"");
			Long time=Long.parseLong(strTime);
			String picUrl=StringUtil.trim(items[1], "\'");
			
			String picName=time+"_"+picUrl.substring(picUrl.lastIndexOf('/')+1);
			
			String localPicUrl=GlobalVariables.PictureURL+"/"+picName;
			
			strRet+="&"+"data="+"\""+time+"\":"+"\'"+localPicUrl+"\'";
		}

		return strRet;
	}
	public int SavePic(){
		int ret=RESULT_SUCCESS;
		for(int i=0;i<data.length;i++){
			String[] items = data[i].split(",");
			String strTime = StringUtil.trim(items[0], "\"");
			Long time=Long.parseLong(strTime);
			String picUrl=StringUtil.trim(items[1], "\'");
			
			String picName=time+"_"+picUrl.substring(picUrl.lastIndexOf('/')+1);
			
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//图片
			String savePath=GlobalVariables.PicturnPath+File.separator
					+sdf.format(date)+File.separator
					+ak+File.separator
					+service_id;
			int downloadret = Download.saveUrlAs(picUrl, savePath,picName, "GET");
			if(Download.ERROR_SUCCESS!=downloadret){
				//TODO 下载失败，写日志
				System.out.println(Download.getErrorInfo(downloadret));
				ret=downloadret;
				break;
			}
		}
		return ret;
	}
	private  void addPicInfo(PicInfo pi){
		synchronized(pictureMap){
		pictureMap.put(pi.getSeqID(), pi);
		}
	}
	public static PicInfo getPicInfo(String seqid){
		PicInfo ret=null;
		synchronized(pictureMap){
			if(pictureMap.containsKey(seqid)){
			//ret = (PicInfo) pictureMap.get(seqid);
				// TODO 查检结果
			ret = pictureMap.remove(seqid);
			}
		}
		return ret;
	}
	
	public void run(){
		//保存成功 TODO 返回错误结果
		if(PicInfo.RESULT_SUCCESS!=SavePic()){
			cmd_type=2;
			messge="抽取失败！";
		}else{
			cmd_type=1;
			messge="抽取成功！";
		}
		addPicInfo(this);
	}
}
