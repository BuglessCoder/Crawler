package crawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DownLoad {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		DownLoad dl = new DownLoad();								//实例化DownLoad
		String url = "http://www.bistu.edu.cn";						//给定一个url
		String filePath = "/Users/lidawei/Desktop/Sp/bistu.html";	//选定将该url存储到本地的路径
		dl.DownLoadPage(url, filePath);								//调用DownLoadPage方法将url爬到本地

	}
	
	//下载页面的方法
	public void DownLoadPage(String url,String filePath){
		
		//Http请求
		CloseableHttpClient httpClient = HttpClients.createDefault();		//创建默认的HttpClient实例
		HttpGet httpGet = new HttpGet(url);									//对于给定的url创建一个请求对象（HttpGet）
		
		/** Http响应
		 *  服务器收到客户端的http请求后，就会对其进行解析，然后把响应发给客户端，这个响应就是HTTP response
		 */
		CloseableHttpResponse response;										
		try {
			response = httpClient.execute(httpGet);							//调用HttpClient对象的execute方法发送请求，返回一个HttpResponse
			InputStream is = null;											//创建一个输入流对象
			
			/** 判断：如果连接成功 
			 *  调用response.getStatusLine().getStatusCode()方法可以得到http响应结果的状态代码
			 *  HttpStatus.SC_OK被定义成一个常量并被赋值为200（此处可以直接写200）
			 */
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);{
				
				//调用HttpResponse的getEntity()方法获取响应实体（HttpEntity对象），该对象包装了服务器的响应内容
				HttpEntity entity = response.getEntity();
				
				//将获取到的服务器响应内容放入输入流is中
				is = entity.getContent();	
				
				//调用saveFile，将is作为参数传入
				saveFile(filePath, is);
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}		
	
			
	}

	
	//将下载内容存储到本地的方法
	private void saveFile(String filePath, InputStream is) {
		Scanner sc = new Scanner(is);				//创建一个Scanner，扫描输入流is
		Writer os = null;							
		try {
			os = new PrintWriter(filePath);			//为指定文件创建一个PrintWriter
			while(sc.hasNext()){
				try {
					os.write(sc.nextLine());		//将Scanner扫描的内容写入到该文件中
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if(sc!=null){
				sc.close();							//关闭Scanner
			}
			if(os!=null){
				try {
					os.flush();						//刷新该流的缓冲
					os.close();						//关闭该流并释放与其相关的系统资源
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
		
			}
		}
	}

}
