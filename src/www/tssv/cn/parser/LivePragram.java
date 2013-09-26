package www.tssv.cn.parser;

import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import www.tssv.cn.TSetting;
import www.tssv.cn.type.TypePragrams;
import www.tssv.cn.utils.DateUtils;

public class LivePragram {
	
	public ArrayList<TypePragrams> ParsePragram(String url) {
		ArrayList<TypePragrams> listPage = new ArrayList<TypePragrams>();
		TypePragrams typePragrams = null;
		boolean isFirst = true;
		boolean flag = false;
		int cur_time = TSetting.getWeekDay = DateUtils.getDateToInt();
		try {
			Document document = Jsoup.connect(url).get();
			Element ul = document.getElementById("pgrow");
			Elements lis = ul.select("li:has(span)");
			for (Element li : lis) {
				String[] text = li.text().split(" ");
				if (text.length == 2) {
					typePragrams = new TypePragrams();
					typePragrams.setTime(text[0]);
					typePragrams.setTitle(text[1]);
					listPage.add(typePragrams);
					if (isFirst) {
						isFirst = false;
						listPage.add(typePragrams);
					}else {
						if (!flag) {
							Date curr = DateUtils.getStringToDate(text[0].trim());
							int cur = DateUtils.getDateToInt(curr);
							if (cur_time > cur) {
								listPage.remove(0);
								listPage.add(0, typePragrams);
							}else {
								flag = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listPage;
	}
}
