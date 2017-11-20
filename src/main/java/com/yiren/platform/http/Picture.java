package com.yiren.platform.http;

//import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;



import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.yiren.platform.classes.PicInfo;
//import com.yiren.util.Download;
//import com.yiren.util.HttpRequest;
//import com.yiren.platform.classes.TrajectoryInfo;
//import com.yiren.platform.http.Trajectory.CmdCallBackThread;

/**
 * Servlet implementation class Picture
 */
public class Picture extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Picture() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解析参数
		String ak = request.getParameter("ak");
		int service_id = Integer.parseInt(request.getParameter("service_id"));
		String seqid = request.getParameter("seqid");
		int cmd_type = Integer.parseInt(request.getParameter("cmd_type"));
		String messge = request.getParameter("messge");
		String[] data = request.getParameterValues("data");
		
		//检查参数解析是否成功
 		if(ak==null || "".equals(ak)){
			return;
		}
		if(seqid==null || "".equals(seqid)){
			return;
		}
		if(messge==null || "".equals(messge)){
			return;
		}
		if(data==null || "".equals(data)){ 
			return;
		}
		/*保存上传的图片Hash表
		 * 
		 * 若下载并保存成功，添加到哈希表
		 * 
		 * TODO 对该哈希表做线程同步，保证线程安全
		 */
		PicInfo pi = new PicInfo(ak,service_id,seqid,cmd_type,messge,data);
		CmdCallBackThread ccbt = new CmdCallBackThread(pi);
		Thread thread1 = new Thread(ccbt);
        thread1.start();


//		//通知第三方，此处不做。
          
		//返回结果
		response.setContentType("application/javascript;charset=UTF-8");		
		PrintWriter out = response.getWriter();
		//StringBuffer result = new StringBuffer();
		//result.append("{\"errno\":0,\"errmsg\":\"success\",\"data\":[]}");
		out.print("{\"errno\":0,\"errmsg\":\"success\",\"data\":[]}");
		out.close();
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	class CmdCallBackThread implements Runnable {
        private PicInfo picinfo;
        public CmdCallBackThread(PicInfo pi) {
            this.picinfo = pi;
        }

        @Override
        public void run() {
        	picinfo.run();
        }

    }
}
