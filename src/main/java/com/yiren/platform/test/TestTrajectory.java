package com.yiren.platform.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class TestTrajectory
 */
public class TestTrajectory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestTrajectory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解析参数
		@SuppressWarnings("rawtypes")
		Map prams = request.getParameterMap();
		
		@SuppressWarnings("rawtypes")
		Iterator iter = prams.entrySet().iterator();
		System.out.println("********"+this.toString()+"********");
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			String[] val = (String[])prams.get(key);
			String strv="[";
			for(int i=0;i<val.length;i++){
				strv+=val[i];
				if(i!=val.length-1){
					strv+=",";
				}
			}
			strv+="]";
			System.out.println(key+"="+strv);
			}
		System.out.println("########"+this.toString()+"########");	
//		String ak = request.getParameter("ak");
//		int service_id = Integer.parseInt(request.getParameter("service_id"));
//		String facility_id = request.getParameter("facility_id");
//		String file_url = request.getParameter("file_url");
//		long start_time = Long.parseLong(request.getParameter("start_time"));
//		long end_time = Long.parseLong(request.getParameter("end_time"));
//		
//		
//		System.out.println("service_id="+service_id);
//		System.out.println("facility_id="+facility_id);
//		System.out.println("file_url="+file_url);
//		System.out.println("start_time"+start_time);
//		System.out.println("end_time="+end_time);
		
		
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
		// TODO Auto-generated method stub
	}

}
