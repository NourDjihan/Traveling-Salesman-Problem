package essaim_de_particules;

public class Vitesse {
	public double Vx;
	public double Vy;

	Vitesse() {
	};

	Vitesse(double V) {
		this.Vx = V;
		this.Vy = V;

	}

	Vitesse(double vx, double vy) {
		this.Vx = vx;
		this.Vy = vy;
	}

	Vitesse(Vitesse V) {
		this.Vx = V.Vx;
		this.Vy = V.Vy;
	}
}
