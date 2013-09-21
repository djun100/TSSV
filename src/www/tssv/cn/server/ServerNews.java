package www.tssv.cn.server;

import www.tssv.cn.type.TypeNews;
import www.tssv.cn.utils.DBUtils;

import android.app.Activity;

public class ServerNews {

	public ServerNews(Activity instance) {
		DBUtils dbUtils = new DBUtils(instance);
		TypeNews news = new TypeNews();
		news.setNews_img("http://v.tssv.cn/UploadFiles_1779/201309/2013092019581013.jpg");
		news.setNews_title("文章标题：第十六届陶博会硕果累累");
		news.setNews_content("2013-9-20 23:57:10");
		news.setNews_url("mms://live.tsr.he.cn/tv/2013/shizheng/09/20-1.wmv");
		dbUtils.insertNews(news);
	}
}
