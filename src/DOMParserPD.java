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

import ApiPackage.PerformanceData;

public class DOMParserPD {

	public ArrayList<String> domParserPD(String si, String gngu)
			throws ParserConfigurationException, SAXException, IOException {
		// XML 문서 파싱
		ArrayList<String> arrlistPD = new ArrayList<String>();
		ArrayList<Perform> performGroup = new ArrayList<Perform>();

		PerformanceData pd = new PerformanceData();
		String xml = pd.performanceData(si, gngu);
		// System.out.println(xml);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document d = builder.parse(is);
		d.getDocumentElement().normalize();
		// System.out.println("Root Element : " + d.getDocumentElement().getNodeName());

		// root의 속성
		NodeList childeren = d.getElementsByTagName("db");
		// System.out.println(childeren.getLength());
		for (int i = 0; i < childeren.getLength(); i++) {
			Node node = childeren.item(i);
			Element ele = (Element) node;
			// String nodeName = ele.getNodeName();
			// System.out.println("node name: " + nodeName);
			String a = ele.getElementsByTagName("prfnm").item(0).getTextContent();
			String b = ele.getElementsByTagName("fcltynm").item(0).getTextContent();
			// arrlistPD.add("시설명: " +
			// ele.getElementsByTagName("prfnm").item(0).getTextContent());
			// arrlistPD.add("주소: " +
			// ele.getElementsByTagName("fcltynm").item(0).getTextContent());
			Perform newPerform = new Perform(ele.getElementsByTagName("prfnm").item(0).getTextContent(),
					ele.getElementsByTagName("fcltynm").item(0).getTextContent());
			performGroup.add(newPerform);
			arrlistPD.add("공연명: " + a + "\n시설명: " + b + "\n");
		}
		// System.out.println(performGroup);
		return (arrlistPD);
	}
}

class Perform {
	String prfnm;
	String fcltynm;

	Perform(String a, String b) {
		prfnm = a;
		fcltynm = b;
	}
}