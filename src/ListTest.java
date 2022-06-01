import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class ListTest extends JFrame {

	Display display;

	ArrayList<String> cd_item = new ArrayList<String>();
	ArrayList<String> pd_item = new ArrayList<String>();

	String si;
	String gngu = "";

	JList<Camp> listCD;
	JList<Perform> listPD;

	Vector<String> v = new Vector<>(10);
	Vector<String> v2 = new Vector<>(10);
	List campList = new List();
	List performList = new List();
	protected String[] xy;

	public ListTest(Display display) {
		this.display = display;
	}

	public void dateLoad() {
		DOMParser dp = new DOMParser();
		DOMParserPD dpPD = new DOMParserPD();

		si = display.selectedDoCode;

		try {
			cd_item = dp.domParser(xy[0], xy[1],display.searchWord, display.campType);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pd_item = dpPD.domParserPD(si, gngu);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("------");
		for (String str : cd_item) {
			campList.add(str);
			System.out.println(str);
		}

		System.out.println("------");
		for (String str : pd_item) {
			performList.add(str);
			System.out.println(str);
		}
		System.out.println("------");

	}
}
