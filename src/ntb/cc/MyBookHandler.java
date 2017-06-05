package ntb.cc;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import com.ibm.hamlet.ExHamletHandler;
import com.ibm.hamlet.Hamlet;
import com.ibm.hamlet.helpers.Helpers;

public class MyBookHandler extends ExHamletHandler {
	private int i = 0 - 1;
	private ArrayList<Adresse> adressen;

	public MyBookHandler(Hamlet hamlet, ArrayList<Adresse> adressen) {
		super(hamlet);
		this.adressen = adressen;
	} // GuestBookHandler

	public int getAdressenRepeatCount(String id, String name, Attributes atts) {
		i++;
		return adressen.size();
	} // getCommentsRepeatCount

	public String getVornameReplacement(String id, String name, Attributes atts) {
		return adressen.get(i).vorname.toString();
	} // getDateReplacement

	public String getNachnameReplacement(String id, String name, Attributes atts) {
		return adressen.get(i).nachname.toString();
	}

	public String getStrasseReplacement(String id, String name, Attributes atts) {
		return adressen.get(i).strasse.toString();
	}

	public String getStadtReplacement(String id, String name, Attributes atts) {
		return adressen.get(i).stadt.toString();
	}

	public String getPLZReplacement(String id, String name, Attributes atts) {
		return adressen.get(i).plz.toString();
	}

	public String getLandReplacement(String id, String name, Attributes atts) {
		return adressen.get(i).land.toString();
	}

	public String getTelefonnummerReplacement(String id, String name, Attributes atts) {
		// return addresses.get(i - 1).entity.getKey().toString();
		return adressen.get(i).telefonnummer.toString();
	}

	public Attributes getCheckboxAttributes(String id, String name, Attributes atts) throws Exception {
		atts = Helpers.getAttributes(atts, "value", "" + i);
		atts = Helpers.getAttributes(atts, "ID", "checkbox" + i);
		return atts;
	}

}