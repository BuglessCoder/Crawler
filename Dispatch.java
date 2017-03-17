package crawler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Dispatch {
	
	private ArrayList<String> seedUrls = new ArrayList<String>();		//创建一个ArrayList用来存储种子Url (种子库)
	private LinkedList<String> unVisitedUrls = new LinkedList<String>();	//创建一个链表用来存储待下载的Url (待下载队列)
	private Hashtable visitedUrls = new Hashtable();					//创建一个哈希表来存储已下载的Url (已下载队列)
	private DownLoad downLoad = new DownLoad();							//实例化之前写好的DownLoad类，用来下载
	private Analysis analysis = new Analysis();							//实例化之前写好的Analysis类，用来做页面分析
	
	//初始化种子Url
	public void initSeedUrls(){	
		
		//利用ArrayList的add方法将指定的开始Url加入种子库中
		seedUrls.add("http://www.bistu.edu.cn");
		seedUrls.add("http://www.sohu.com");
		
	}
	
	//将种子库中的Url放入待下载队列中
	public void seedUnvisitedUrls(){
		for(int i=0;i<seedUrls.size();i++){
			unVisitedUrls.add(seedUrls.get(i));
		}
	}
	
	//爬虫调度的主方法
	public void startCrawl(){
		while(!unVisitedUrls.isEmpty()){	//当待下载队列不空的时候
			
			//1.从待下载队列取出一个Url
			String newUrl = unVisitedUrls.getFirst();
			
			//2.下载该Url到指定位置并且根据url生成保存的文件名(调用getFileName方法)
			String filePath = "/Users/lidawei/Desktop/Sp/" + getFileName(newUrl); 
			downLoad.DownLoadPage(newUrl, filePath);
			
			//3.更新已下载队列
			visitedUrls.put(newUrl, 1);
			
			//4.分析下载页面
			ArrayList<String> tmpUrls = analysis.analysis(filePath);
			
			//5.更新待下载队列
			for(int i=0;i<tmpUrls.size();i++){
				if(!visitedUrls.containsKey(tmpUrls.get(i))){
					unVisitedUrls.add(tmpUrls.get(i));
				}
			}
			
			//6.把待下载队列中第一个Url(已经下载过的)删掉
			unVisitedUrls.removeFirst();
		}
	}
	
	public String getFileName(String url){
		url = url.substring(7);									// 在url中去掉http://			
		url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";	//正则表达式
		return url;
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Dispatch dispatch = new Dispatch();
		dispatch.initSeedUrls();
		dispatch.seedUnvisitedUrls();
		dispatch.startCrawl();

	}
	
	

}
