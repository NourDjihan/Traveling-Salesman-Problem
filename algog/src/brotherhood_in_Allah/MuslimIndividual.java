package brotherhood_in_Allah;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Vector;

import commun.Ville;

public class MuslimIndividual {
	Vector<Ville> Solution = new Vector<Ville>();
	Vector<Double> Couts = new Vector<Double>();
	Double Fitness;

	MuslimIndividual() {
	}

	MuslimIndividual(Vector<Ville> V) {
		for (int i = 0; i < V.size(); i++) {
			Ville I = new Ville(V.elementAt(i).nom, V.elementAt(i).x, V.elementAt(i).y);
			this.Solution.addElement(I);
		}

		this.Calculer_Couts(GetChoiceValue());
		this.Calculer_Fitness();
	}

	MuslimIndividual Mutation() {
		int first = 0;
		int second = 0;
		int size = this.Solution.size() - 1;
		Random Dice = new Random();
		first = Dice.nextInt(size - 2 + 1) + 2;
		second = Dice.nextInt(size - 2 + 1) + 2;

		Ville V = new Ville(this.Solution.elementAt(first));
		Ville VV = new Ville(this.Solution.elementAt(second));
		this.Solution.setElementAt(VV, first);
		this.Solution.setElementAt(V, second);
		this.Calculer_Couts(GetChoiceValue());
		this.Calculer_Fitness();
		return this;
	}

	MuslimIndividual Aleatoire() {
		Random Dice = new Random();
		MuslimIndividual P = new MuslimIndividual();
		P.Solution.addElement(this.Solution.elementAt(0));
		int i = 1;
		while (i < this.Solution.size()) {
			int j = Dice.nextInt(this.Solution.size());
			if (P.Solution.contains(this.Solution.elementAt(j)) == false) {
				P.Solution.addElement(this.Solution.elementAt(j));
				i++;
			}
		}
		MuslimIndividual P1 = new MuslimIndividual(P.Solution);
		return P1;
	}

	Double Calculer_Fitness() {
		this.Fitness = 0.0;
		double somme = 0;
		for (int i = 0; i < this.Couts.size(); i++) {
			somme = this.Couts.elementAt(i) + somme;
		}
		this.Fitness = somme;
		return this.Fitness;
	}

	int GetChoiceValue() {
		int c = 0;
		try {
			String n;
			BufferedReader choice = new BufferedReader(new FileReader("C:/project_data/choi.txt"));
			if ((n = choice.readLine()) != null) {
				c = Integer.parseInt(n);
			}
			choice.close();

		} catch (Exception n) {
			n.printStackTrace();

		}
		return c;
	}

	Vector<Double> Calculer_Couts(int choi) {
		this.Couts.removeAllElements();

		if (choi == 0) {
			int i = 0;
			int j = 1;
			while ((j < this.Solution.size()) && (i < this.Solution.size() - 1)) {
				this.Couts.addElement(DistanceRe(this.Solution.elementAt(i).x, this.Solution.elementAt(i).y,
						this.Solution.elementAt(j).x, this.Solution.elementAt(j).y) / 1000);
				j++;
				i++;
			}
			this.Couts.addElement(DistanceRe(this.Solution.lastElement().x, this.Solution.lastElement().y,
					this.Solution.firstElement().x, this.Solution.firstElement().y) / 1000);
		} else if (choi == 1) {
			int i = 0;
			int j = 1;
			while ((j < this.Solution.size()) && (i < this.Solution.size() - 1)) {
				this.Couts.addElement(Math.sqrt(Math.pow(this.Solution.elementAt(j).x - this.Solution.elementAt(i).x, 2)
						+ Math.pow(this.Solution.elementAt(j).y - this.Solution.elementAt(i).y, 2)));
				j++;
				i++;
			}
			this.Couts.addElement(Math.sqrt(Math
					.pow(this.Solution.elementAt(0).x - this.Solution.elementAt(this.Solution.size() - 1).x, 2)
					+ Math.pow(this.Solution.elementAt(0).y - this.Solution.elementAt(this.Solution.size() - 1).y, 2)));
		}

		return this.Couts;
	}

	double convertRad(double input) {
		return (Math.PI * input) / 180;
	}

	double DistanceRe(double lat_a_degre, double lon_a_degre, double lat_b_degre, double lon_b_degre) {
		double d = 0;
		double R = 6378000; // Rayon de la terre en m�tre

		double lat_a = convertRad(lat_a_degre);
		double lon_a = convertRad(lon_a_degre);
		double lat_b = convertRad(lat_b_degre);
		double lon_b = convertRad(lon_b_degre);

		d = R * (Math.PI / 2 - Math
				.asin(Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(lon_b - lon_a) * Math.cos(lat_b) * Math.cos(lat_a)));
		return d;
	}

	void ToString() {

		System.out.println("LE CHEMIN:**************************\n");
		for (int i = 0; i < this.Solution.size(); i++) {
			System.out.println(this.Solution.elementAt(i).nom + "  (" + this.Solution.elementAt(i).x + ";"
					+ this.Solution.elementAt(i).y + ")");
		}
		/*
		 * System.out.println("Les Couts:\n"); for(int
		 * i=0;i<this.Couts.size();i++){
		 * System.out.println(this.Couts.elementAt(i)); }
		 */
		System.out.println("FITNESS:\n" + this.Fitness);
	}

	MuslimIndividual CopyMI(Vector<Ville> V) {
		this.Solution.removeAllElements();
		this.Couts.removeAllElements();
		this.Fitness = 0.0;
		for (int i = 0; i < V.size(); i++) {
			Ville I = new Ville(V.elementAt(i).nom, V.elementAt(i).x, V.elementAt(i).y);
			this.Solution.addElement(I);
		}
		this.Calculer_Couts(GetChoiceValue());
		this.Calculer_Fitness();
		return this;
	}
}
