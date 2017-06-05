package ntb.cc;

import java.io.Serializable;

// Klasse Adresse
import com.google.appengine.api.datastore.Entity;

public class Adresse implements Serializable {
	private static final long serialVersionUID = -1199467372679306131L;
	public String vorname;
	public String nachname;
	public String strasse;
	public String stadt;
	public String plz;
	public String land;
	public String telefonnummer;
	public Entity entity;

	public Adresse(String vorname, String nachname, String strasse, String stadt, String plz,
			String land, String telefonnummer, Entity entity) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.stadt = stadt;
		this.plz = plz;
		this.land = land;
		this.telefonnummer = telefonnummer;
		this.entity = entity;
	}
}