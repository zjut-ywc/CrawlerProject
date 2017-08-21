
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
 * httpclient����
 * ��ȡ��aa�����aa���������ֱ����
 * htmlʹ��jsoup��������jsonͨ��json������xmlͨ��jdom����dom4j����
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
	 * HttpGet��ʽץȡ������Ϣ
	 */
	public static void httpGetMethod(String url){
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			logger.info("ִ��get����:...." + httpGet.getURI());
			CloseableHttpResponse httpResponse = null;
			
			try {
				httpResponse = httpClient.execute(httpGet);
			} catch (Exception e1) {
				logger.info("----------------------------����ʧ��");
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
	 * HttpPost��ʽץȡ������Ϣ��filed1Ϊ�ϴ��������������
	 */
	public void httpPostMethod(String url,String filed1){
		CloseableHttpClient httpClient = getHttpClient();
		
		try {
			HttpPost httpPost = new HttpPost(url);
			// ���������б�
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("filed1���ֶ�����", filed1));
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");
			httpPost.setEntity(uefEntity);
			
			CloseableHttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (Exception e1) {
				logger.info("----------------------------����ʧ��");
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
		// ����һ��Buffer�ַ���
		byte[] buffer = new byte[1024];
		// ÿ�ζ�ȡ���ַ������ȣ����Ϊ-1������ȫ����ȡ���
		int len = 0;
		// ʹ��һ����������buffer������ݶ�ȡ����
		while ((len = inStream.read(buffer)) != -1) {
			// ���������buffer��д�����ݣ��м����������ĸ�λ�ÿ�ʼ����len�����ȡ�ĳ���
			outStream.write(buffer, 0, len);
		}
		// �ر�������
		inStream.close();
		// ��outStream�������д���ڴ�
		return outStream.toByteArray();
	}
	/*
	 * ͼƬ����
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
