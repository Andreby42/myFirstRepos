package com.gps.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

@WebServlet
public class Log4JInitServlet extends HttpServlet {
	public Log4JInitServlet() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet ���ڳ�ʼ�� log4j��־������Ϣ");
		String log4jLocation = config
				.getInitParameter("log4j-properties-location");

		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.err
					.println("*** û�� log4j-properties-location ��ʼ�����ļ�, ����ʹ�� BasicConfigurator��ʼ��");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			System.setProperty("WORKDIR",webAppPath);
			String log4jProp = webAppPath + log4jLocation;
			File ff = new File(log4jProp);
			if (ff.exists()) {
				System.out.println("ʹ��: " + log4jProp + "��ʼ����־������Ϣ");
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("*** " + log4jProp
						+ " �ļ�û���ҵ��� ����ʹ�� BasicConfigurator��ʼ��");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
