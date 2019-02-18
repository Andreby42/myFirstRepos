package com.pushwin.batchwork.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;


/**
 * �ļ�������
 * 
 * @author jiqinlin
 *
 */
public class FileTool {
    private static final Logger log = Logger.getLogger(FileTool.class);
    
    /**
     * ����Ŀ¼
     * 
     * @param dir Ŀ¼
     */
    public static void mkdir(String dir) {
        try {
            String dirTemp = dir;
            File dirPath = new File(dirTemp);
            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        } catch (Exception e) {
            log.error("����Ŀ¼��������: "+e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * �½��ļ�
     * 
     * @param fileName
     *            String ����·�����ļ��� ��:E:\phsftp\src\123.txt
     * @param content
     *            String �ļ�����
     *            
     */
    public static void createNewFile(String fileName, String content) {
        try {
            String fileNameTemp = fileName;
            File filePath = new File(fileNameTemp);
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            String strContent = content;
            pw.println(strContent);
            pw.flush();
            pw.close();
            fw.close();
        } catch (Exception e) {
            log.error("�½��ļ���������: "+e.getMessage());
            e.printStackTrace();
        }

    }
    
    /**
     * ɾ���ļ�
     * 
     * @param fileName ����·�����ļ���
     */
    public static void delFile(String fileName) {
        try {
            String filePath = fileName;
            java.io.File delFile = new java.io.File(filePath);
            FileUtils.deleteQuietly(delFile);
        } catch (Exception e) {
            log.error("ɾ���ļ���������: "+e.getMessage());
            e.printStackTrace();
        }
    }
    

    /**
     * ɾ���ļ���
     * 
     * @param folderPath  �ļ���·��
     */
    public static void delFolder(String folderPath) {
        try {
            // ɾ���ļ���������������
            delAllFile(folderPath); 
            String filePath = folderPath;
            java.io.File myFilePath = new java.io.File(filePath);
            // ɾ�����ļ���
            myFilePath.delete(); 
        } catch (Exception e) {
            log.error("ɾ���ļ��в�������"+e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * ɾ���ļ�������������ļ�
     * 
     * @param path �ļ���·��
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] childFiles = file.list();
        File temp = null;
        for (int i = 0; i < childFiles.length; i++) {
            //File.separator��ϵͳ�йص�Ĭ�����Ʒָ���
            //��UNIXϵͳ�ϣ����ֶε�ֵΪ'/'����Microsoft Windowsϵͳ�ϣ���Ϊ '\'��
            if (path.endsWith(File.separator)) {
                temp = new File(path + childFiles[i]);
            } else {
                temp = new File(path + File.separator + childFiles[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + childFiles[i]);// ��ɾ���ļ���������ļ�
                delFolder(path + "/" + childFiles[i]);// ��ɾ�����ļ���
            }
        }
    }

    
    /**
     * ���Ƶ����ļ�
     * 
     * @param srcFile
     *            ����·����Դ�ļ� �磺E:/phsftp/src/abc.txt
     * @param dirDest
     *            Ŀ���ļ�Ŀ¼�����ļ�Ŀ¼���������Զ�����  �磺E:/phsftp/dest
     * @throws IOException
     */
    public static void copyFile(String srcFile, String dirDest) {
        try {
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(dirDest+"/"+new File(srcFile).getName());
            byte buffer[] = StreamUtils.getBytes(in);
            out.write(buffer);
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            log.error("�����ļ���������:"+e.getMessage());
            e.printStackTrace();
        } finally {
        	
        }
    }

    /**
     * �����ļ���
     * 
     * @param oldPath
     *            String Դ�ļ���·�� �磺E:/phsftp/src
     * @param newPath
     *            String Ŀ���ļ���·�� �磺E:/phsftp/dest
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            // ����ļ��в����� ���½��ļ���
            mkdir(newPath);
            File file = new File(oldPath);
            String[] files = file.list();
            File temp = null;
            for (int i = 0; i < files.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + files[i]);
                } else {
                    temp = new File(oldPath + File.separator + files[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath
                            + "/" + (temp.getName()).toString());
                    byte[] buffer = new byte[1024 * 2];
                    int len;
                    while ((len = input.read(buffer)) != -1) {
                        output.write(buffer, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// ��������ļ���
                    copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
                }
            }
        } catch (Exception e) {
            log.error("�����ļ��в�������:"+e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * �ƶ��ļ���ָ��Ŀ¼
     * 
     * @param oldPath ����·�����ļ��� �磺E:/phsftp/src/ljq.txt
     * @param newPath Ŀ���ļ�Ŀ¼ �磺E:/phsftp/dest
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }
    
    /**
     * �ƶ��ļ���ָ��Ŀ¼������ɾ���ļ���
     * 
     * @param oldPath Դ�ļ�Ŀ¼  �磺E:/phsftp/src
     * @param newPath Ŀ���ļ�Ŀ¼ �磺E:/phsftp/dest
     */
    public static void moveFiles(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delAllFile(oldPath);
    }
    
    /**
     * �ƶ��ļ���ָ��Ŀ¼����ɾ���ļ���
     * 
     * @param oldPath Դ�ļ�Ŀ¼  �磺E:/phsftp/src
     * @param newPath Ŀ���ļ�Ŀ¼ �磺E:/phsftp/dest
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }
    
    /**
     * ��ȡ����
     * 
     * @param inSream
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static String readData(InputStream inSream, String charsetName) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inSream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return new String(data, charsetName);
    }
    
    /**
     * һ��һ�ж�ȡ�ļ����ʺ��ַ���ȡ������ȡ�����ַ�ʱ���������
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static Set<String> readFile(String path) throws Exception{
        Set<String> datas=new HashSet<String>();
        FileReader fr=new FileReader(path);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        while ((line=br.readLine())!=null) {
            datas.add(line);
        }
        br.close();
        fr.close();
        return datas;
    }
}
