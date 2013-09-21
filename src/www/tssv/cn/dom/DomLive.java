package www.tssv.cn.dom;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;

import www.tssv.cn.AppLog;
import www.tssv.cn.TSetting;
import www.tssv.cn.type.TypeLive;
import www.tssv.cn.utils.HttpUtil;

public class DomLive {

	public List<TypeLive> parseXml(Context context) {
		List<TypeLive> typeLives = new ArrayList<TypeLive>();
		InputStream stream = HttpUtil.getInputStreamFromLocal(context,
				TSetting.live_file);
		TypeLive live = null;
		try {
			// 得到Dom解析对象工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// 通过工厂创建Dom解析对象实例
			DocumentBuilder db = factory.newDocumentBuilder();
			// 将xml文件的输入流交给Dom解析对象进行解析，并将Dom树返回
			Document document = db.parse(stream);
			// 通过Dom树接收到根元素
			Element rootElement = document.getDocumentElement();
			// 通过根元素获得下属的所有名字为live节点
			NodeList nodeList = rootElement.getElementsByTagName("live");
			int nodeListcount = nodeList.getLength();
			AppLog.e("nodeListcount: " + nodeListcount);
			// 遍历取出来的live节点集合
			for (int i = 0; i < nodeListcount; i++) {
				// 得到一个live节点
				Element videoElement = (Element) nodeList.item(i);
				// 新建一个live对象
				live = new TypeLive();
				// 取得live标签的下属所有节点
				NodeList liveChildList = videoElement.getChildNodes();
				int countChild = liveChildList.getLength();
				AppLog.e("countChild: " + countChild);
				for (int j = 0; j < countChild; j++) {
					// 创建一个引用，指向循环的标签
					Node node = liveChildList.item(j);
					// 如果此循环出来的元素是Element对象，即标签元素，那么执行以下代码
					if (Node.ELEMENT_NODE == node.getNodeType()) {
						if ("live_img".equals(node.getNodeName())) {
							String live_img = node.getFirstChild()
									.getNodeValue().trim();
							live.setLive_img(live_img);
							AppLog.e(live_img);
						} else if ("live_title".equals(node.getNodeName())) {
							String live_title = node.getFirstChild()
									.getNodeValue().trim();
							live.setLive_title(live_title);
						} else if ("live_content".equals(node.getNodeName())) {
							String live_content = node.getFirstChild()
									.getNodeValue().trim();
							live.setLive_content(live_content);
						} else if ("live_url".equals(node.getNodeName())) {
							String live_url = node.getFirstChild()
									.getNodeValue().trim();
							live.setLive_url(live_url);
						} else if ("live_pragram_url"
								.equals(node.getNodeName())) {
							String live_pragram_url = node.getFirstChild()
									.getNodeValue().trim();
							live.setLive_pragram_url(live_pragram_url);
						}
					}
				}
				typeLives.add(live);
				live = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return typeLives;
	}
}
