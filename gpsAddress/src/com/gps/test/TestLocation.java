package com.gps.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gps.entity.Home;
import com.gps.entity.LonLat;
import com.gps.entity.Unit;

public class TestLocation {
	public static String url = "http://127.0.0.1:8080/gpsAddress/index";
	private static LonLat lonLat = null;
	private static List<LonLat> lonlats = null;
	private static String home_address = null; // �ҷõ�ַ
	private static String unit_address = null; // ��λ�õ�ַ

	public static void main(String[] args) {

		// ������������
		List<LonLat> list = new ArrayList<LonLat>();

		 Home h1 = new Home("121.42048519","31.21062474");
		 Unit u1 = new Unit("121.431100","31.216131");
		 Home h2 = new Home("121.431100","31.216131");
		 Unit u2 = new Unit("121.42048519","31.21062474");

//		Home h1 = new Home("460", "1", "53511", "101168382", "0");
//		Unit u1 = new Unit("460", "1", "41028", "5527245", "0");
//		Home h2 = new Home("460", "1", "55045", "151232234", "0");
//		Unit u2 = new Unit("121.42048519","31.21062474");

		LonLat lat1 = new LonLat("1", h1, u1);
		LonLat lat2 = new LonLat("2", h2, null);

		list.add(lat1);
		list.add(lat2);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lonlats", list);

		JSONObject latObject = JSONObject.fromObject(map); // ��װJSON����
		String lalon = latObject.toString();
		System.out.println(lalon);

		String sendPost = sendPost(url, lalon);// ��������
		System.out.println(sendPost);

		// ����JSON����
		/*
		 * JSONObject ss = JSONObject.fromObject(sendPost); JSONArray jsonArray
		 * = ss.getJSONArray("lonlats"); for (int i = 0; i < jsonArray.size();
		 * i++) { JSONObject opt = (JSONObject) jsonArray.opt(i); String c =
		 * opt.getString("c"); String h = opt.getString("h"); String id =
		 * opt.getString("id"); System.out.println("id:" + id + ",c:" + c +
		 * ",h:" + h);
		 * 
		 * String lon = null; String lat = null; Unit unit = null; Home home =
		 * null; if (c != "null" && !"".equals(c)) { JSONObject jc =
		 * JSONObject.fromObject(c); lon = jc.optString("lon"); // ���� lat =
		 * jc.optString("lat"); // γ�� unit_address = jc.optString("address");
		 * 
		 * unit = new Unit(lon, lat, unit_address); }
		 * 
		 * if (h != "null" && !"".equals(h)) { JSONObject jh =
		 * JSONObject.fromObject(h); lon = jh.optString("lon"); // ���� lat =
		 * jh.optString("lat"); // γ�� home_address = jh.optString("address");
		 * 
		 * home = new Home(lon, lat, home_address);
		 * 
		 * } lonLat = new LonLat(id,home,unit); lonlats.add(lonLat); }
		 * 
		 * for (int i = 0; i < lonlats.size(); i++) { LonLat ll =
		 * lonlats.get(i); System.out.println(ll.getC().getAddress()); }
		 */
	}

	static HttpURLConnection conn;

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer sb = null;
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			conn = (HttpURLConnection) realUrl.openConnection();
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestMethod("POST");
			// ����ͨ�õ���������
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.connect();

			if (param != null && param.length() > 0) {
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());

				dos.writeBytes(param);
				dos.flush();
				dos.close();
			}

			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
			// �Ͽ�����
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
}
