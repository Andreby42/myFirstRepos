package com.pushwin.batchwork.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


/**
 *  �ļ��ƶ�����
 * @author admin
 *
 */

public class FileMv {
	
	private static Logger logger4j = Logger.getLogger(FileMv.class);
	/**
	 * �ƶ��ļ���ָ��Ŀ¼
	 * @param filemv �ƶ���ָ��ľľ
	 * @param file  �ƶ����ļ�
	 */
	public static void mvFileToDir(String filemv,File file){
		String time =  DateUtil.batchTime() ;
		File filemvdir = new File(filemv+"/" + time);
		if(!filemvdir.exists()){
			filemvdir.mkdir();
		}
		try {
			FileUtils.moveToDirectory(file, filemvdir, true);
		} catch (IOException e) {
			logger4j.error("�ļ��ƶ�ʧ��" + file.getName());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File file = new File("D:\\mywork\\NJBANK\\njcb\\20160301\\PlanType20160408.txt");
		String filemv = "D:\\mywork\\NJBANK\\njcb\\20160301\\";
		mvFileToDir(filemv,file);
	}
}
