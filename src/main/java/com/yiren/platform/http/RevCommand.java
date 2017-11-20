package com.yiren.platform.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.yiren.platform.classes.PicCmdInfo;
//import com.yiren.util.HttpRequest;

/**
 * Servlet implementation class RevCommand
 */
public class RevCommand extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private String requestURL="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevCommand() {
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
		//解析指令
		String seqid = request.getParameter("seqid");
		String facility_id = request.getParameter("facility_id");
		long start_time = Long.parseLong(request.getParameter("start_time"));
		long end_time = Long.parseLong(request.getParameter("end_time"));
		String client_id=request.getParameter("seqid");

		//检查参数解析是否成功
		if(seqid==null || "".equals(seqid)){
			return;
		}
		if(facility_id==null || "".equals(facility_id)){
			return;
		}
		if(client_id==null || "".equals(client_id)){
			return;
		}
		
		//返回结果
		PicCmdInfo pci = new PicCmdInfo(seqid,facility_id,start_time,end_time,client_id);
		CmdCallBackThread ccbt = new CmdCallBackThread(pci);
		Thread thread1 = new Thread(ccbt);
        thread1.start();

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
        private PicCmdInfo cmdinfo;
        public CmdCallBackThread(PicCmdInfo pci) {
            this.cmdinfo = pci;
        }

        @Override
        public void run() {
        	try {
				cmdinfo.run();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }
}
