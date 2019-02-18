package ticket.xml.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.yinlian.Extended.LogType;
import com.yinlian.wssc.web.util.MD5Util;
import com.yinlian.wssc.web.util.PropertiesUtil;
import com.yinlian.wssc.web.util.StringUtils;
import com.yinlian.wssc.web.util.StringUtilsEX;
import com.yl.soft.log.LogHandle;

public class XmlUint {

	public static void main(String[] args)throws Exception {
		
		String pp="<order><certificateNo>371402199301042328</certificateNo><linkName>周琳</linkName><linkMobile>18515283454</linkMobile><orderCode>182085049193715</orderCode><orderPrice>0</orderPrice><payMethod>third_vm</payMethod><ticketOrders><ticketOrder><orderCode>182086114205893</orderCode><price>90.00</price><quantity>1</quantity><totalPrice>90.00</totalPrice><occDate>2018-08-03</occDate><goodsCode>PST20180111016903</goodsCode><goodsName>成人票</goodsName><remark>测试环境用上述参数</remark></ticketOrder></ticketOrders></order>";
		String[] rsl=sendPost("SEND_CODE_REQ", pp);
	
		 System.out.println(rsl[0]);
		// System.out.println(map.get("description"));
		// System.out.println(MD5Util.encodeByMD5("12aA张").toLowerCase());

	}
	// 请求服务器
	// public static String
	// serviceRequest="http://ds-zff.sendinfo.com.cn/boss/service/code.htm";
	// public static String serviceCode="TESTFX";
	// public static String serviceUser="admin";

	public static String getHeadXml(String REQ) throws Exception {
		Properties properties = PropertiesUtil.getProperties("cfg.properties");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		stringBuffer.append("<PWBRequest>");
		stringBuffer.append("<transactionName>" + REQ + "</transactionName>");
		stringBuffer.append("<header>");
		stringBuffer.append("<application>SendCode</application>");
		stringBuffer.append("<requestTime>" + StringUtilsEX.toShortStringDateTime(new Date()) + "</requestTime>");
		stringBuffer.append("</header>");
		stringBuffer.append("<identityInfo>");
		stringBuffer.append("<corpCode>" + properties.getProperty("serviceCode") + "</corpCode>");
		stringBuffer.append("<userName>" + properties.getProperty("serviceUser") + "</userName>");
		stringBuffer.append("</identityInfo>");
		return stringBuffer.toString();
	}

	public static String getXml(String requestName, String param) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getHeadXml(requestName));
		stringBuffer.append("<orderRequest>");
		stringBuffer.append(param);
		stringBuffer.append("</orderRequest>");
		stringBuffer.append("</PWBRequest>");
		return stringBuffer.toString();
	}

	/*
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL
	 * 
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String[] sendPost(String requestName, String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String temp = "0";
		String result = "";
		String[] teml = new String[2];
		try {
			Properties properties1 = PropertiesUtil.getProperties("cfg.properties");
			URL realUrl = new URL(properties1.getProperty("serviceRequest"));
			// 打开和URL之间的连接
			URLConnection conn1 = realUrl.openConnection();
			HttpURLConnection conn = (HttpURLConnection) conn1;
			conn.setConnectTimeout(10*1000);
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			String requestStr = getXml(requestName, param);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());

			System.out.println("xmlMsg=" + requestStr + ",sign="
					+ MD5Util.encodeByMD5("xmlMsg=" + requestStr + properties1.getProperty("serviceKey")).toLowerCase());
			// 发送请求参数
			//out.print("xmlMsg=" + requestStr);
			String requestStr1= new String(("xmlMsg="+requestStr).getBytes("UTF-8"),"UTF-8");
			// 发送请求参数			
			out.print("xmlMsg="+URLEncoder.encode(requestStr, "UTF-8"));
			out.print("&sign=" + MD5Util.encodeByMD5(requestStr1 + properties1.getProperty("serviceKey")).toLowerCase());
			// out.print();
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			InputStreamReader fReader = new InputStreamReader(conn.getInputStream(),"UTF-8");
			in = new BufferedReader(fReader);
			//in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			// conn.getRequestProperties()
			if (conn.getResponseCode() != 200) {
				temp = "1";
			} else {
				while ((line = in.readLine()) != null) {
					result += line;
				}
				System.out.println(result);
			}

		} catch (Exception e) {
			temp = "1";
			LogHandle.error(LogType.tick, "票务对接请求异常：" + e);
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
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
		teml[0] = result;
		teml[1] = temp;
		return teml;
	}

	/**
	 * 解析xml的value值
	 * 
	 * @param xml
	 * @return
	 */
	public static Map<String, Object> parserValue(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotNull(xml)) {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder saxbBuilder = new SAXBuilder();
			try {
				// 通过输入源构造一个Document
				Document doc = saxbBuilder.build(source);
				// 取的根元素
				Element root = doc.getRootElement();
				System.out.println(root.getName());
				List<?> node = root.getChildren(); // 一级
				for (int i = 0; i < node.size(); i++) {
					Element element = (Element) node.get(i);
					if (element != null && !element.getName().equals("Header")) {
						String key1 = element.getName();
						String value1 = element.getValue();
						System.out.println(key1 + "-->一级标签下的:" + value1);
						map.put(key1, value1);
					}
					List<?> subNode = element.getChildren(); // 二级
					String key2 = "";
					if (subNode != null && subNode.size() > 0) {
						for (int j = 0; j < subNode.size(); j++) {
							Element subElement = (Element) subNode.get(j);
							key2 = subElement.getName();
							String value2 = subElement.getValue();
							System.out.println(key2 + "-->二级标签下的:" + value2);
							if (key2 != null) {
								if (key2.equals("ResultCode") || key2.equals("ResultMsg")) {
									map.put(key2, value2);
								}
							}
							map.put(key2, value2);

							List<?> tNode = subElement.getChildren(); // 三级
							if (tNode != null && tNode.size() > 0) {
								for (int k = 0; k < tNode.size(); k++) {
									Element tElement = (Element) tNode.get(k);
									String key3 = tElement.getName();
									String value3 = tElement.getValue();
									System.out.println(tElement.getName() + "-->三级标签下的:" + value3);
									if (key3 != null) {
										map.put(key3, value3);
									}
									List<?> fNode = tElement.getChildren();// 四级
									if (fNode != null && fNode.size() > 0) {

										for (int h = 0; h < fNode.size(); h++) {
											Element fElement = (Element) fNode.get(h);
											String key4 = fElement.getName();
											String value4 = fElement.getValue();
											System.out.println(fElement.getName() + "-->四级标签下的:" + value4);
											map.put(key4, value4);
										}
									}

								}
							}

						}
					}
				}
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!map.containsKey("ResultMsg")) {
			if (map.containsKey("ResultCode")) {
				if (!map.get("ResultCode").equals("1"))
					map.put("ResultMsg", "服务系统错误！");
				else {
					map.put("ResultMsg", "");
				}
			}
		}
		return map;
	}

	/**
	 * 解析xml的value值
	 * 
	 * @param xml
	 * @return
	 */
	public static Map<String, Object> parserValueForVoucher(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotNull(xml)) {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder saxbBuilder = new SAXBuilder();
			try {
				// 通过输入源构造一个Document
				Document doc = saxbBuilder.build(source);
				// 取的根元素
				Element root = doc.getRootElement();
				System.out.println(root.getName());
				List<?> node = root.getChildren(); // 一级
				for (int i = 0; i < node.size(); i++) {
					Element element = (Element) node.get(i);
					if (element != null && !element.getName().equals("Header")) {
						String key1 = element.getName();
						String value1 = element.getValue();
						System.out.println(key1 + "-->一级标签下的:" + value1);
					}
					List<?> subNode = element.getChildren(); // 二级
					String key2 = "";
					if (subNode != null && subNode.size() > 0) {
						for (int j = 0; j < subNode.size(); j++) {
							Element subElement = (Element) subNode.get(j);
							key2 = subElement.getName();
							String value2 = subElement.getValue();
							System.out.println(key2 + "-->二级标签下的:" + value2);
							if (key2 != null) {
								if (key2.equals("ResultCode") || key2.equals("ResultMsg")) {
									map.put(key2, value2);
								}
							}
							map.put(key2, value2);
							List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
							List<?> tNode = subElement.getChildren(); // 三级
							if (tNode != null && tNode.size() > 0) {
								for (int k = 0; k < tNode.size(); k++) {
									Element tElement = (Element) tNode.get(k);
									String key3 = tElement.getName();
									String value3 = tElement.getValue();
									System.out.println(tElement.getName() + "-->三级标签下的:" + value3);
									if (key3 != null) {
										map.put(key3, value3);
									}
									Map<String, Object> map2 = new HashMap<String, Object>();
									List<?> fNode = tElement.getChildren();// 四级
									if (fNode != null && fNode.size() > 0) {

										for (int h = 0; h < fNode.size(); h++) {
											Element fElement = (Element) fNode.get(h);
											String key4 = fElement.getName();
											String value4 = fElement.getValue();
											System.out.println(fElement.getName() + "-->四级标签下的:" + value4);
											map2.put(key4, value4);
										}
									}
									list.add(map2);
								}
							}
							map.put(key2, list);
						}
					}
				}
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 解析 xml 的属性值
	 * 
	 * @param xml
	 *            目标 xml
	 */
	public static Map<String, Object> parserAttributes(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotNull(xml)) {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder saxbBuilder = new SAXBuilder();
			try {
				// 通过输入源构造一个Document
				Document doc = saxbBuilder.build(source);
				// 取的根元素
				Element root = doc.getRootElement();
				System.out.println(root.getName());
				List<?> node = root.getChildren(); // 一级
				for (int i = 0; i < node.size(); i++) {
					Element element = (Element) node.get(i);
					System.out.println(element.getName());
					@SuppressWarnings("unchecked")
					List<Attribute> list = element.getAttributes();
					if (list != null && list.size() > 0) {
						for (Attribute attribute : list) {
							System.out.println(attribute.getName() + ":" + attribute.getValue());
							map.put(attribute.getName(), attribute.getValue());
						}
					}

					List<?> subNode = element.getChildren(); // 二级
					if (subNode != null && subNode.size() > 0) {
						for (int j = 0; j < subNode.size(); j++) {
							Element subElement = (Element) subNode.get(j);
							@SuppressWarnings("unchecked")
							List<Attribute> list2 = subElement.getAttributes();
							if (list2 != null && list2.size() > 0) {
								for (Attribute attribute : list2) {
									System.out.println(attribute.getName() + "-->二级标签下的:" + attribute.getValue());
									map.put(attribute.getName(), attribute.getValue());
								}
							}

							List<?> tNode = subElement.getChildren(); // 三级
							if (tNode != null && tNode.size() > 0) {
								for (int k = 0; k < tNode.size(); k++) {
									Element tElement = (Element) tNode.get(j);
									@SuppressWarnings("unchecked")
									List<Attribute> list3 = tElement.getAttributes();
									for (Attribute attribute : list3) {
										System.out.println(attribute.getName() + "-->三级级标签下的:" + attribute.getValue());
										map.put(attribute.getName(), attribute.getValue());
									}
								}
							}
						}
					}
				}
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
