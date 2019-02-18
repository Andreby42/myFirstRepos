package com.pushwin.batchwork.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipFile;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pushwin.batchwork.common.SpringProperty;
import com.pushwin.batchwork.dao.IImportDebitDao;
import com.pushwin.batchwork.utils.CompressUtil;
import com.pushwin.batchwork.utils.DateUtil;

@Service("importDebitService")
public class ImportDebitServiceImpl {
	
	@Autowired
	private IImportDebitDao importDebitDao;
	
	protected Logger logger = LoggerFactory.getLogger(ImportDebitServiceImpl.class);
	private String debitModelFile = (String) SpringProperty.props.get("debitModelFile");
	private String debitAppFolder = (String) SpringProperty.props.get("debitAppFolder");
	
	@Transactional
	public void importDebit() throws IOException{
		//��ȡ���ÿ������ļ�������zip���ļ��б�
		File appFolder = new File(debitAppFolder);
		FilenameFilter zipFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String fileName) {
				if (fileName.toLowerCase().endsWith(".zip")) {
					return true;
				} else {
					return false;
				}
			}
		};
		String[] zipFileNames = appFolder.list(zipFilter);
		if(ArrayUtils.isEmpty(zipFileNames)){
			logger.info("û��Ҫ����Ľ��������˳���ǿ���������");
			return;
		}
		//ȡ��һ��zip�ļ����д���
		String zipFilePath = "";
		ZipFile zipFile = null;
		for (String zipFileName : zipFileNames) {
			try {
				zipFilePath = debitAppFolder + zipFileName;
				zipFile = new ZipFile(zipFilePath);
			} catch (IOException e) {
				logger.info("zip�����𻵣�������ȡ��һ��zip����");
				e.printStackTrace();
				continue;
			} finally {
				try {
					if(null != zipFile)
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}
		if(null == zipFile)
			return;
		//��zip���ƶ���ָ���ļ���
		File dealPath = new File(appFolder.getParent() + "/deal");
		try {
			FileUtils.moveFileToDirectory(new File(zipFilePath), dealPath, true);
		} catch (IOException e) {
			logger.info("�ļ��ƶ�����" + zipFilePath);
			e.printStackTrace();
			return;
		}
		
		//��ѹzip����terminal�ļ���
		String unZipFolderPath = appFolder.getParent() + "/terminal";
		File dealZipFile = new File(dealPath.getAbsoluteFile() + "/" + FilenameUtils.getName(zipFilePath));
		try {
			CompressUtil.unzip(dealZipFile, unZipFolderPath, null);
		} catch (Exception e) {
			logger.info("�ļ���ѹ����" + zipFilePath);
			e.printStackTrace();
			FileUtils.moveFileToDirectory(dealZipFile, new File(appFolder.getParent() + "/error"), true);
		}
		
		//��ȡtxt�ļ�json��ת��Ϊmap
		Map<String, String> appInfoMap = resolveInfoTxt(unZipFolderPath + "/" + FilenameUtils.getBaseName(zipFilePath) + "/txt/info.txt");
		JSONArray appInfo = readExcelModel(appInfoMap);
		
		//����������
		String sql = "";
		JSONArray columnArray = new JSONArray();
		JSONArray valueArray = new JSONArray();
		for (int i = 0; i < appInfo.size(); i++) {
			JSONObject columnJson = appInfo.getJSONObject(i);
			if(StringUtils.isNotBlank(columnJson.optString("DB_COLUMN"))){
				columnArray.add(columnJson.optString("DB_COLUMN"));
				valueArray.add(" '" + columnJson.optString("VALUE") + "' ");
			}
		}
		String[] bindColumn = {"ID", "UPDATE_TIME", "STATUS"};
		String[] bindValues = {" '" + FilenameUtils.getBaseName(zipFilePath) + "' ", " '" + DateUtil.format(new Date()) + "' ", " '1' "};
		columnArray.addAll(Arrays.asList(bindColumn));
		valueArray.addAll(Arrays.asList(bindValues));
		if(!columnArray.isEmpty()){
			sql += "INSERT INTO T_BASIS_DEBIT (" + columnArray.join(",") + ") VALUES (" + valueArray.join(",", true) + ")";
		}
		importDebitDao.executeSql(sql);
	}
	
	
	public JSONArray readExcelModel(Map<String, String> zipMap) throws IOException{
		Workbook readwb = null;
		InputStream instream = null;
		try
		{
			// ����Workbook����, ֻ��Workbook����
			// ֱ�Ӵӱ����ļ�����Workbook
			instream = new FileInputStream(debitModelFile);

			readwb = Workbook.getWorkbook(instream);

			// Sheet���±��Ǵ�0��ʼ
			// ��ȡ��һ��Sheet��
			Sheet readsheet = readwb.getSheet(0);

//			// ��ȡSheet������������������
//			int rsColumns = readsheet.getColumns();

			// ��ȡSheet������������������
			int rsRows = readsheet.getRows();

			// ��ȡָ����Ԫ��Ķ�������
			JSONArray dataArray = new JSONArray();
			for (int j = 1; j < rsRows; j ++){
				JSONObject dataJson = new JSONObject();
				Cell cell_0 = readsheet.getCell(0, j);
				dataJson.put("ROW_NUM", cell_0.getContents());
				
				Cell cell_1 = readsheet.getCell(1, j);
				dataJson.put("DATA_SOURCE", cell_1.getContents());
				
				Cell cell_2 = readsheet.getCell(2, j);
				dataJson.put("EN_NAME", cell_2.getContents());
				
				Cell cell_3 = readsheet.getCell(3, j);
				dataJson.put("CN_NAME", cell_3.getContents());
				
				Cell cell_4 = readsheet.getCell(4, j);
				dataJson.put("REMARK", cell_4.getContents());
				
				Cell cell_6 = readsheet.getCell(6, j);
				dataJson.put("DB_COLUMN", cell_6.getContents());
				
				dataJson.put("VALUE", zipMap.get(cell_2.getContents()));
				dataArray.add(dataJson);
			}
			return dataArray;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != readwb)
				readwb.close();
			if(null != instream)
				instream.close();
		}
		return null;
	}
	
	public Map<String, String> resolveInfoTxt(String txtPath)
			throws IOException {

		byte[] txtByte = FileUtils.readFileToByteArray(new File(txtPath));
		String jsonStr = new String(txtByte, "UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Map<String, String> dataMap = new HashMap<String, String>();

//		if (jsonObject.keys().hasNext()) {
//			JSONObject obj = jsonObject.optJSONObject(String.valueOf(jsonObject.keys().next()));
			Iterator<?> it = jsonObject.keys();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = jsonObject.optString(key);
				dataMap.put(key, value);
			}
//		}
		return dataMap;
	}
	public static void main(String[] args) throws IOException {
//		ImportCreditServiceImpl ic = new ImportCreditServiceImpl();
//		ic.creditAppFolder = "D:/pushwin/Credit/source/";
//		ic.creditModelFile = "D:/pushwin/Credit/model/CreditModel.xls";
//		ic.importCredit();
//		String[] bindColumn = {"\"ID\"", " 'UPDATE_TIME' ", " 'STATUS' "};
//		JSONArray ja = new JSONArray();
////		ja.add(" '1' ");
//		ja.addAll(Arrays.asList(bindColumn));
//		String a = ja.join(",", true);
//		System.out.println(a);
		
		System.out.println(FilenameUtils.getBaseName("D:/pushwin/Credit/model/CreditModel.xls"));
	}

}
