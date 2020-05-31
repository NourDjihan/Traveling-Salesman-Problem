package algorithme_genetique;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Vector;
import commun.Ville;

public class Individu {
	Vector<Ville> Chemin = new Vector<Ville>();
	Vector<Double> Couts = new Vector<Double>();
	Double Fitness = 0.0;

	Individu() {
	}

	Individu(Individu I) {
		this.Chemin = CopyChemin(I.Chemin);
		this.Couts = CopyCouts(I.Couts);
		this.Fitness = I.Fitness;
	}

	Individu(Vector<Ville> V) {
		this.Chemin = CopyChemin(V);
		// x=latitude y=longuetude
		this.CalculerCouts(GetChoiceValue());
		this.CalculFitness();
	}

	////////////////////////////////////////////////////////////////////////////////////
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

	/////////////////////////////////////////////////////////////////////////////////////
	Vector<Ville> CopyChemin(Vector<Ville> V) {
		Vector<Ville> I = new Vector<Ville>();
		for (int i = 0; i < V.size(); i++) {
			Ville ville = new Ville(V.elementAt(i).nom, V.elementAt(i).x, V.elementAt(i).y);
			I.addElement(ville);
		}
		return I;
	}

	Vector<Double> CopyCouts(Vector<Double> V) {
		Vector<Double> I = new Vector<Double>();
		for (int i = 0; i < V.size(); i++) {
			double D = V.elementAt(i);
			I.addElement(D);
		}
		return I;
	}

	// LES OPERATIONS****************
	// Generer_un_chemin_al�atoire
	Individu aleatoire() {
		Random Dice = new Random();
		Individu I = new Individu();
		I.Chemin.addElement(this.Chemin.elementAt(0));
		int i = 1;
		while (i < this.Chemin.size()) {
			int j = Dice.nextInt(this.Chemin.size());
			if (I.Chemin.contains(this.Chemin.elementAt(j)) == false) {
				I.Chemin.addElement(this.Chemin.elementAt(j));
				i++;
			}
		}
		Individu I1 = new Individu(I.Chemin);
		return I1;
	}

	// Calculer_Les_Couts
	Vector<Double> CalculerCouts(int choi) {
		this.Couts.removeAllElements();

		if (choi == 0) {
			int i = 0;
			int j = 1;
			while ((j < this.Chemin.size()) && (i < this.Chemin.size() - 1)) {
				this.Couts.addElement(DistanceRe(this.Chemin.elementAt(i).x, this.Chemin.elementAt(i).y,
						this.Chemin.elementAt(j).x, this.Chemin.elementAt(j).y) / 1000);
				j++;
				i++;
			}
			this.Couts.addElement(DistanceRe(this.Chemin.lastElement().x, this.Chemin.lastElement().y,
					this.Chemin.firstElement().x, this.Chemin.firstElement().y) / 1000);
		} else if (choi == 1) {
			int i = 0;
			int j = 1;
			while ((j < this.Chemin.size()) && (i < this.Chemin.size() - 1)) {
				this.Couts.addElement(Math.sqrt(Math.pow(this.Chemin.elementAt(j).x - this.Chemin.elementAt(i).x, 2)
						+ Math.pow(this.Chemin.elementAt(j).y - this.Chemin.elementAt(i).y, 2)));
				j++;
				i++;
			}
			this.Couts.addElement(Math.sqrt(
					Math.pow(this.Chemin.elementAt(0).x - this.Chemin.elementAt(this.Chemin.size() - 1).x, 2) + Math
							.pow(this.Chemin.elementAt(0).y - this.Chemin.elementAt(this.Chemin.size() - 1).y, 2)));
		}

		return this.Couts;
	}

	// Trouver_les_elements_r�p�t�s
	Vector<Ville> FindElements(Individu I) {
		Individu II = new Individu(I.Chemin);
		Vector<Ville> V = new Vector<Ville>();
		boolean exists = false;
		String nom;
		String nom2;
		int i = 1;
		int j = 1;
		while (i < II.Chemin.size()) {
			nom = II.Chemin.elementAt(i).nom;
			j = 1;
			exists = false;
			while (j < this.Chemin.size()) {
				nom2 = this.Chemin.elementAt(j).nom;
				if (nom == nom2) {
					exists = true;
					break;
				}

				j++;
			}
			if (exists == false) {
				Ville v = new Ville(II.Chemin.elementAt(i).nom, II.Chemin.elementAt(i).x, II.Chemin.elementAt(i).y);
				V.addElement(v);
			}
			i++;
		}

		return V;
	}

	// Remplacer_les_elements_r�p�t�s

	Individu ReplaceElements(Individu I) {
		Vector<Ville> V = this.FindElements(I);
		if (V.size() != 0) {
			Ville VilleDepart = new Ville(this.Chemin.elementAt(0).nom, this.Chemin.elementAt(0).x,
					this.Chemin.elementAt(0).y);
			this.Chemin.removeElementAt(0);
			int i = 0;
			int j = 1;
			int k = 0;

			while (i < this.Chemin.size() - 1) {
				while (j < this.Chemin.size()) {
					if (this.Chemin.elementAt(i).nom == (this.Chemin.elementAt(j).nom)) {
						this.Chemin.set(i, V.elementAt(k));
						k++;
						j++;
					} else {
						j++;
					}
				}
				i++;
				j = i + 1;
			}
			this.Chemin.add(0, VilleDepart);
			this.CalculerCouts(GetChoiceValue());
		}

		return this;
	}

	// Reparer_
	Individu Reparer(Individu I) {
		this.ReplaceElements(I);
		this.CalculerCouts(GetChoiceValue());
		this.Fitness = this.CalculFitness();
		return this;
	}

	// Croisement_
	void Croisement(Individu I, int position, Individu Norm) {
		Individu Normale = new Individu(Norm);
		position = position - 1;
		if (position > 0) {
			for (int i = position; i < this.Chemin.size(); i++) {
				Ville V = new Ville();
				V = this.Chemin.elementAt(i);
				this.Chemin.setElementAt(I.Chemin.elementAt(i), i);
				I.Chemin.setElementAt(V, i);
			}
		}
		I.Reparer(Normale);
		this.Reparer(Normale);
	}

	Individu Permutation(int Position1, int Position2) {
		Ville V = new Ville(this.Chemin.elementAt(Position1).nom, this.Chemin.elementAt(Position1).x,
				this.Chemin.elementAt(Position1).y);
		Ville VV = new Ville(this.Chemin.elementAt(Position2).nom, this.Chemin.elementAt(Position2).x,
				this.Chemin.elementAt(Position2).y);

		this.Chemin.setElementAt(VV, Position1);
		this.Chemin.setElementAt(V, Position2);

		this.CalculerCouts(GetChoiceValue());
		this.CalculFitness();
		return this;
	}
	// Afficher_Les_Noms_Des_Villes

	void ToString() {
		for (int i = 0; i < this.Chemin.size(); i++) {
			System.out.print(" " + this.Chemin.elementAt(i).nom);
		}
	}

	void AfficherCouts() {
		int i = 0;
		while (i < this.Couts.size()) {
			System.out.println("  " + this.Couts.elementAt(i));
			i++;
		}
	}

	// Fonction_Fitness
	double CalculFitness() {
		double Somme = 0;
		for (int i = 0; i < this.Chemin.size(); i++) {
			Somme = Somme + this.Couts.elementAt(i);
		}
		this.Fitness = Somme;
		return this.Fitness;
	}

	void AfficherFitness() {
		System.out.println("Fitness est:" + this.Fitness);
	}

	int getCheminNbr() {
		return this.Chemin.size();
	}

	public static void main(String[] args) {
		Ville A = new Ville("A", 50, 60);
		Ville B = new Ville("B", 80, 40);
		Ville C = new Ville("C", 80, 64);
		Ville D = new Ville("D", 5, 60);
		Ville E = new Ville("E", 98, 21);
		Ville F = new Ville("F", 87, 42);
		Ville G = new Ville("G", 55, 12);
		Ville K = new Ville("K", 58, 77);
		Vector<Ville> V = new Vector<Ville>();
		V.addElement(A);
		V.addElement(B);
		V.addElement(C);
		V.addElement(D);
		V.addElement(E);
		V.addElement(F);
		V.addElement(G);
		V.addElement(K);
		Individu I = new Individu(V);
		Vector<Ville> T = new Vector<Ville>();
		T.addElement(A);
		T.addElement(E);
		T.addElement(F);
		T.addElement(G);
		T.addElement(B);
		T.addElement(K);
		T.addElement(C);
		T.addElement(D);
		Individu O = new Individu(T);
		Vector<Ville> VV = new Vector<Ville>();
		VV.addElement(A);
		VV.addElement(C);
		VV.addElement(B);
		VV.addElement(G);
		VV.addElement(E);
		VV.addElement(K);
		VV.addElement(F);
		VV.addElement(D);
		Individu II = new Individu(VV);

		I.ToString();
		System.out.println("\n");
		O.ToString();
		System.out.println("\n");
		O.Croisement(I, 5, II);
		O.ToString();
		System.out.println("\n");
		I.ToString();
		for (int i = 0; i < I.FindElements(II).size(); i++) {
			System.out.println("elem" + I.FindElements(II).elementAt(i).nom);
		}
	}
}
