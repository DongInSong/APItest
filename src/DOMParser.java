import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ApiPackage.CampingData;

public class DOMParser {

	public ArrayList<String> domParser(String x, String y, String word, String induty)
			throws ParserConfigurationException, SAXException, IOException {
		// XML 문서 파싱
		ArrayList<String> arrlist = new ArrayList<String>();
		ArrayList<Camp> group = new ArrayList<Camp>();

		CampingData cd = new CampingData();
		String xml = cd.campingData(x, y);
		// System.out.println(xml);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document d = builder.parse(is);
		d.getDocumentElement().normalize();
		// System.out.println("Root Element : " + d.getDocumentElement().getNodeName());

		// root의 속성
		NodeList childeren = d.getDocumentElement().getChildNodes();
		// System.out.println(childeren.getLength());
		for (int i = 0; i < childeren.getLength(); i++) {
			Node node = childeren.item(i);
			Element ele = (Element) node;
			String nodeName = ele.getNodeName();
			// System.out.println("node name: " + nodeName);
			if (nodeName == "body") {
				NodeList bodyChilderen = ele.getChildNodes();
				// System.out.println(bodyChilderen.getLength());
				for (int j = 0; j < bodyChilderen.getLength(); j++) {
					Node bodyNode = bodyChilderen.item(j);
					Element bodyEle = (Element) bodyNode;
					String bodyNodeName = bodyEle.getNodeName();
					// System.out.println("node name: " + bodyNodeName);
					if (bodyNodeName == "items") {
						NodeList itemChilderen = bodyEle.getChildNodes();
						for (int temp = 0; temp < itemChilderen.getLength(); temp++) {
							String a = bodyEle.getElementsByTagName("facltNm").item(temp).getTextContent();
							String b = bodyEle.getElementsByTagName("addr1").item(temp).getTextContent();
							String c = bodyEle.getElementsByTagName("induty").item(temp).getTextContent();

							if (a.contains(word)) {
								if (c.contains(induty)) {
									Camp newCamp = new Camp(
											bodyEle.getElementsByTagName("facltNm").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("addr1").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("induty").item(temp).getTextContent());
									group.add(newCamp);
									arrlist.add("시설명: " + a + "\n주소: " + b + "\n업종: " + c + "\n");
								} else if (induty.equals("전체")) {
									Camp newCamp = new Camp(
											bodyEle.getElementsByTagName("facltNm").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("addr1").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("induty").item(temp).getTextContent());
									group.add(newCamp);
									arrlist.add("시설명: " + a + "\n주소: " + b + "\n업종: " + c + "\n");
								}
							} else if(word.equals("없음")) {
								if (c.contains(induty)) {
									Camp newCamp = new Camp(
											bodyEle.getElementsByTagName("facltNm").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("addr1").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("induty").item(temp).getTextContent());
									group.add(newCamp);
									arrlist.add("시설명: " + a + "\n주소: " + b + "\n업종: " + c + "\n");
								} else if (induty.equals("전체")) {
									Camp newCamp = new Camp(
											bodyEle.getElementsByTagName("facltNm").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("addr1").item(temp).getTextContent(),
											bodyEle.getElementsByTagName("induty").item(temp).getTextContent());
									group.add(newCamp);
									arrlist.add("시설명: " + a + "\n주소: " + b + "\n업종: " + c + "\n");
								}
							}
						}

					}
				}
			}
		}
		// System.out.println(group);
		return (arrlist);
	}
}

class Camp {
	String facltNm;
	String addr1;
	String type;

	Camp(String a, String b, String c) {
		facltNm = a;
		addr1 = b;
		type = c;
	}

	public String getFacltNm() {
		return facltNm;
	}

	public String getAddr1() {
		return addr1;
	}

	public String getType() {
		return type;
	}

}