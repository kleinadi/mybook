package ntb.cc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.ibm.hamlet.ExHamletHandler;
import com.ibm.hamlet.Hamlet;

public class MybookServlet extends Hamlet {

	private static final long serialVersionUID = 5264892082158101750L;
	private static Logger logger = Logger.getLogger(MybookServlet.class.getName());
	public static ArrayList<Adresse> adressen = new ArrayList<Adresse>();

	private void storeAdresse(String vorname, String nachname, String strasse, String stadt, String plz, String land, String telefonnummer) {
		Key adresseDataKey = KeyFactory.createKey("Adressen", "MyBook");
		Entity sensorData = new Entity("Adresse", adresseDataKey);
		sensorData.setProperty("vorname", vorname);
		sensorData.setProperty("nachname", nachname);
		sensorData.setProperty("strasse", strasse);
		sensorData.setProperty("stadt", stadt);
		sensorData.setProperty("plz", plz);
		sensorData.setProperty("land", land);
		sensorData.setProperty("telefonnummer", telefonnummer);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(sensorData);

		logger.debug("Adresse wurde in die Google Datenbank geladen");
	} // storeAddress

	private void readAdresse() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key adresseDataKey = KeyFactory.createKey("Adressen", "MyBook");
		Query query = new Query("Adresse", adresseDataKey).addSort("vorname", Query.SortDirection.ASCENDING);
		

		List<Entity> aktuelleAdressen = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10));

		adressen.clear();

		for (Entity aktuelleAdresse : aktuelleAdressen) {
			String vorname = "" + aktuelleAdresse.getProperty("vorname");
			String nachname = "" + aktuelleAdresse.getProperty("nachname");
			String strasse = "" + aktuelleAdresse.getProperty("strasse");
			String stadt = "" + aktuelleAdresse.getProperty("stadt");
			String plz = "" + aktuelleAdresse.getProperty("plz");
			String land = "" + aktuelleAdresse.getProperty("land");
			String telefonnummer = "" + aktuelleAdresse.getProperty("telefonnummer");

			Adresse adresse = new Adresse(vorname, nachname, strasse, stadt, plz, land, telefonnummer,
					aktuelleAdresse);
			adressen.add(adresse);
		} // for
	} // readComments

	private void deleteAdresse(int arrayPosition) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.delete(adressen.get(arrayPosition).entity.getKey());
	}

	public void init() {
		BasicConfigurator.configure();
		logger.debug("init");
	} // init

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		try {
			logger.debug("doPost wurde ausgeführt");

			String vorname = req.getParameter("inputVorname");
			String nachname = req.getParameter("inputNachname");
			String strasse = req.getParameter("inputStrasse");
			String stadt = req.getParameter("inputStadt");
			String plz = req.getParameter("inputPLZ");
			String land = req.getParameter("inputLand");
			String telefonnummer = req.getParameter("inputTelefonnummer");

			String[] person = req.getParameterValues("adressen");
			

			if (vorname != null && nachname != null && strasse != null && stadt != null && plz != null && land != null && telefonnummer != null) {
				storeAdresse(vorname, nachname, strasse, stadt, plz, land, telefonnummer);
				logger.debug("Neue Adresse");
			} // if
			else if (person[0] != "") {
				logger.debug("in els if schleife");
				for (String element : person) {
					logger.debug("do Post delete" + element);
					deleteAdresse(Integer.parseInt(element));
				}

			} else {
				logger.debug("do Post ohne Parameter");
			}
			doGet(req, res);
		} catch (Exception e) {
			logger.error("catch", e);
		} // try

	} // doPost

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		try {
			readAdresse();
			ExHamletHandler handler = new MyBookHandler(this, adressen);
			serveDoc(req, res, "index.html", handler);
		} catch (Exception e) {
			logger.error("", e);
		} // try
	} // doGet
}