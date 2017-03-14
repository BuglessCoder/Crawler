package crawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class Analysis {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Analysis analysis = new Analysis();
		analysis.analysis("/Users/lidawei/Desktop/Sp/bistu.html");

	}
	
	public ArrayList<String> analysis(String filePath){
		ArrayList<String> urls = new ArrayList<String>();
		File input = new File(filePath);		//为指定路径下的文件创建一个File对象
		Document document;
		try {
			document = Jsoup.parse(input,"UTF-8","");		//Jsoup的一个静态解析方法，生成一个Document对象
			Elements contents = document.getElementsByTag("body");		//用Tag(标签)获得元素，返回一个Elements实例（类似JS）
			for(Element cont:contents){						//for-each循环的写法，即cont是contents中的每个元素
				Elements links = cont.getElementsByTag("a");
				for(Element link:links){
					String linkHref = link.attr("href");	//Elements的attr方法获取"href"元素的数据
					urls.add(linkHref);						//讲得到的数据放入已定义的ArrayList中
					System.out.println(linkHref);			//打印数据	
				}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return urls;
		
		
	}

}
