package com.yiren.platform.http;

//import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yiren.platform.classes.TrajectoryInfo;


/**
 * Servlet implementation class Trajectory
 */
public class Trajectory extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trajectory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//解析参数
		String ak = request.getParameter("ak");
		int service_id = Integer.parseInt(request.getParameter("service_id"));
		String facility_id = request.getParameter("facility_id");
		String file_url = request.getParameter("file_url");
		long start_time = Long.parseLong(request.getParameter("start_time"));
		long end_time = Long.parseLong(request.getParameter("end_time"));
		
		//检查参数解析是否成功
		if(ak==null || "".equals(ak)){
			return;
		}
		if(facility_id==null || "".equals(facility_id)){
			return;
		}
		if(file_url==null || "".equals(file_url)){
			return;
		}
		
		TrajectoryInfo trainfo = new TrajectoryInfo(ak,service_id,facility_id,file_url,start_time,end_time);
		CmdCallBackThread ccbt = new CmdCallBackThread(trainfo);
		Thread thread1 = new Thread(ccbt);
        thread1.start();

          
		//返回结果
		response.setContentType("application/javascript;charset=UTF-8");		
		PrintWriter out = response.getWriter();
		//StringBuffer result = new StringBuffer();
		//result.append();
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
        private TrajectoryInfo trainfo;
        public CmdCallBackThread(TrajectoryInfo ti) {
            this.trainfo = ti;
        }

        @Override
        public void run() {
        	trainfo.run();
        }

    }
}
