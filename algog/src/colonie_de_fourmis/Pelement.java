package colonie_de_fourmis;

public class Pelement {
	public String first;
	public String second;
	double ph;
	double dist;

	public Pelement(String first, String second, double ph, double dist) {
		this.first = first;
		this.second = second;
		this.ph = ph;
		this.dist = dist;

	}

	public Pelement(Pelement p) {
		this.first = p.first;
		this.second = p.second;
		this.ph = p.ph;
		this.dist = p.dist;
	}

}
