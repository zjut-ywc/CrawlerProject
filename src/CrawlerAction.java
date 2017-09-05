
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
/**
 * httpclient爬虫
 * 获取到aa后根据aa的类型来分别解析
 * html使用jsoup来解析，json通过json解析，xml通过jdom或者dom4j解析
 * @author yaowc
 *
 */
public class CrawlerAction{
	static Logger logger = Logger.getLogger(CrawlerAction.class);
			
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private static void closeHttpClient(CloseableHttpClient client) throws IOException {
		if (client != null) {
			client.close();
		}
	} 
	/*
	 * HttpGet方式抓取返回信息
	 */
	public static void httpGetMethod(String url){
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			logger.info("ִ执行get请求:...." + httpGet.getURI());
			CloseableHttpResponse httpResponse = null;
			
			try {
				httpResponse = httpClient.execute(httpGet);
			} catch (Exception e1) {
				logger.info("----------------------------连接失败");
			}
			HttpEntity entity = httpResponse.getEntity();
			if(null != entity){
				String aa = EntityUtils.toString(entity);
				System.out.println("aa=="+aa);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/*
	 * HttpPost方式抓取返回信息，filed1为上传参数可自行添加
	 */
	public void httpPostMethod(String url,String filed1){
		CloseableHttpClient httpClient = getHttpClient();
		
		try {
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("filed1的字段名称", filed1));
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");
			httpPost.setEntity(uefEntity);
			
			CloseableHttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (Exception e1) {
				logger.info("----------------------------连接失败");
			}
			
			HttpEntity entity = httpResponse.getEntity();
			if(null != entity){
				String aa = EntityUtils.toString(entity);
				System.out.println("aa=="+aa);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
	/*
	 * 图片下载
	 */
	public void getImg(String url){
		HttpURLConnection conn = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URL imgurl = new URL(url);
			conn = (HttpURLConnection) imgurl.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5*1000);
			is = conn.getInputStream();
			byte[] data = readInputStream(is);
			File imgFile = new File("D:/a.jpg");
			fos = new FileOutputStream(imgFile);
			fos.write(data);
			is.close();
			fos.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		//httpGetMethod("http://www.acgsou.com/sort-4-1.html");
		
	}
}
