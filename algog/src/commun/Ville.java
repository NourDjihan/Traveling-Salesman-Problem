package commun;

public class Ville {

	public String nom;
	public double x;
	public double y;

	public Ville(Ville V) {
		nom = V.nom;
		x = V.x;
		y = V.y;
	}

	public Ville(String nom, double x, double y) {
		this.nom = nom;
		this.x = x;
		this.y = y;
	}

	public Ville() {
	}

}
