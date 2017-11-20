package com.yiren.platform.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.yiren.platform.classes.GlobalVariables;

/**
 * Servlet implementation class LoadFilePro
 */
public class LoadFilePro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void destroy() {
		super.destroy();
	}
	
	public void init() throws ServletException {
		try {
			GlobalVariables.class.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
