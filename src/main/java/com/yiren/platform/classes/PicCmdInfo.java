package com.yiren.platform.classes;

import com.yiren.util.HttpRequest;

public class PicCmdInfo {

	//解析指令
	private String seqid = "";
	private String facility_id = "";
	private long start_time = -1;
	private long end_time = -1;
	private String client_id="";
	
	public PicCmdInfo(String s,String f,long st,long et,String cid){
		this.seqid=s;
		this.facility_id=f;
		this.start_time=st;
		this.end_time=et;
		this.client_id=cid;
	}
	public void run() throws InterruptedException{
		//发送给前端或第三方平台图片命令
		//通知第三方
		//String localURL="";
		String sendParam="seqid="+seqid
				+"&"+"facility_id="+facility_id
				+"&"+"start_time="+start_time
				+"&"+"end_time="+end_time
				+"&"+"client_id="+client_id;
//		int errno=-1;
//		int trytimes=0;
		//该条命令应该转给谁,向谁要图片
		Client c = GlobalVariables.getClientByFaciltyID(facility_id);
		if(c!=null){
			HttpRequest.sendGet(c.getPicCmdUrl(), sendParam);
		}
		//String requestRet="";
//		//TODO 
//		while(errno!=0 && trytimes<5){
//			 requestRet = 
//			 //解析结果
//			 JsonParser parser=new JsonParser();  //创建JSON解析器
//			 JsonObject object=(JsonObject) parser.parse(requestRet);  //创建JsonObject对象
//			 errno = object.get("errno").getAsInt();
//		}
//				
		//检查图片是否生成完成，完成测通知请求平台
		PicInfo pi = PicInfo.getPicInfo(seqid);
		int trytimes=0;//60*10;//等待10分钟
		while(pi==null && trytimes++<60*10){
			Thread.sleep(1000);
			pi=PicInfo.getPicInfo(seqid);			
		}
		//图片生成完成
		if(pi!=null){
			Client clientPicCmp = GlobalVariables.getClientByAK(pi.getAcessKey());
			if(clientPicCmp!=null){
				HttpRequest.sendGet(clientPicCmp.getPicCmdUrl(), pi.getPicCompletedCmd());
			}
		}
		else{//图片生成失败
			
		}
//		for(int i=0;i<60;i++){
//			System.out.println(PicCmdInfo.class+":"+i);
//			Thread.sleep(1000);
//		}
		
	}

}
