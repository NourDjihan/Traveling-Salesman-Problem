package essaim_de_particules;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Vector;

import commun.Ville;

public class Particule {
	Vector<Ville> Chemin = new Vector<Ville>();
	Vector<Double> Couts = new Vector<Double>();
	Vector<Position> P = new Vector<Position>();
	Vector<Position> PidBest = new Vector<Position>();
	Vector<Vitesse> Vid = new Vector<Vitesse>();
	final int n1 = 2;
	final int n2 = 2;
	double w = 0;
	double Fitness = 0.0;

	

	
	// CONSTRUCTEURconstructeurCONSTRUCTEUR****************************
	Particule() {
	}
	Particule(Vector<Ville> V, double w, double v) {
		// Remplir_LE_Vecteur_DES_CHEMIN_ET_POSITION
		this.Chemin = CheminDonneChemin(V);
		this.P = CheminDonnePosition();
		this.PidBest = PositionDonnePosition(this.P);
		int i = 0;
		int j = 1;
		// FIN_CHEMIN_POSITION

		// CALCULER_LES_COUTS
		while ((j < this.P.size()) && (i < this.P.size() - 1)) {
			this.Couts.addElement(Math.sqrt(Math.pow(this.P.elementAt(j).x - this.P.elementAt(i).x, 2)
					+ Math.pow(this.P.elementAt(j).y - this.P.elementAt(i).y, 2)));
			j++;
			i++;
		}
		this.Couts.addElement(Math.sqrt(Math.pow(this.P.elementAt(0).x - this.P.elementAt(this.P.size() - 1).x, 2)
				+ Math.pow(this.P.elementAt(0).y - this.P.elementAt(this.P.size() - 1).y, 2)));
                         
		// FIN_Calcule_DES_COUTS
		// Calcule_DU_FITNESS
		this.Fitness = this.Calculer_Fitness();
		// FIN_FITNESS
		// VITESSE
		Vitesse vitesse = new Vitesse(v);
		for (int i1 = 0; i1 < this.Chemin.size() - 1; i1++) {
			this.Vid.addElement(vitesse);
		}
		this.w = w;

	}

	double Calculer_Fitness() {
		double somme = 0;
		for (int i = 0; i < this.Couts.size(); i++) {
			somme = this.Couts.elementAt(i) + somme;
		}
		return somme;
	}

	Vector<Double> Mise_A_Jour_De_VX(Vector<Position> PgdBest) {
		Vector<Double> valeurs = new Vector<Double>();
		int i = 0;
		int j = 0;
		double valeur = 0;
		while (i < this.Vid.size()) {
			j = i + 1;

			valeur = (this.w * this.Vid.elementAt(i).Vx)
					+ ((n1 * (Math.random() * (1 - 0))) * (this.PidBest.elementAt(j).x - this.P.elementAt(j).x))
					+ ((n2 * (Math.random() * (1 - 0))) * (PgdBest.elementAt(j).x - this.P.elementAt(j).x));
			if (valeur < 0) {
				valeur = -valeur;
			}
			valeurs.addElement(valeur);
			i++;
		}
		return valeurs;

	}

	Vector<Double> Mise_A_Jour_De_VY(Vector<Position> PgdBest) {
		Vector<Double> valeurs = new Vector<Double>();
		int i = 0;
		double valeur = 0;
		int j = 0;
		while (i < this.Vid.size()) {
			j = i + 1;
			valeur = ((this.w * this.Vid.elementAt(i).Vy)
					+ ((n1 * (Math.random() * (1 - 0))) * (this.PidBest.elementAt(j).y - this.P.elementAt(j).y)))
					+ (((n2 * (Math.random() * (1 - 0))) * (PgdBest.elementAt(j).y - this.P.elementAt(j).y)));
			if (valeur < 0) {
				valeur = -valeur;
			}
			valeurs.addElement(valeur);
			i++;
		}
		return valeurs;
	}

	Vector<Vitesse> MAJVitesse(Vector<Position> PgdBest) {
		Vector<Double> valeursX = new Vector<Double>();
		valeursX = this.Mise_A_Jour_De_VX(PgdBest);
		Vector<Double> valeursY = new Vector<Double>();
		valeursY = this.Mise_A_Jour_De_VY(PgdBest);
		int i = 0;
		int size = this.Vid.size();
		this.Vid.removeAllElements();
		while (i < size) {
			Vitesse vitesse = new Vitesse(valeursX.elementAt(i), valeursY.elementAt(i));
			this.Vid.addElement(vitesse);
			i++;
		}
		return this.Vid;
	}

	Particule MAJP(Vector<Position> PgdBest) {
		Vector<Vitesse> V = this.MAJVitesse(PgdBest);
		Vector<Position> CP = new Vector<Position>();
		CP = PositionDonnePosition(this.P);

		double fitness = this.Fitness;
		this.P.removeAllElements();
		Position p = new Position(CP.elementAt(0).x, CP.elementAt(0).y);

		this.P.addElement(p);
		int i = 0;
		int j = 0;
		while (i < V.size()) {
			j = i + 1;
			Position p1 = new Position(CP.elementAt(j).x + V.elementAt(i).Vx, CP.elementAt(j).y + V.elementAt(i).Vy);
			this.P.addElement(p1);
			i++;
		}

		this.PositionDonneChemin();

		this.P = this.CheminDonnePosition();
		this.Couts.removeAllElements();
		this.Couts = this.Calculer_Couts(GetChoiceValue());
		this.Fitness = 0;
		this.Fitness = this.Calculer_Fitness();
		if (fitness >= this.Fitness) {
			this.PidBest = PositionDonnePosition(this.P);
		} else if (fitness <= this.Fitness) {
			this.PidBest = PositionDonnePosition(CP);
		}

		return this;
	}

	Vector<Double> Calculer_Couts(int choix) {
		this.Couts.removeAllElements();

		if (choix == 0) {
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
		} else if (choix == 1) {
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

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	double convertRad(double input) {
		return (Math.PI * input) / 180;
	}

	/////////////////////////////////////////
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

	////////////////////////////////
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

	Particule Aleatoire(double v) {
		Random Dice = new Random();
		Particule P = new Particule();
		P.w = this.w;
		P.Chemin.addElement(this.Chemin.elementAt(0));
		int i = 1;
		while (i < this.Chemin.size()) {
			int j = Dice.nextInt(this.Chemin.size());
			if (P.Chemin.contains(this.Chemin.elementAt(j)) == false) {
				P.Chemin.addElement(this.Chemin.elementAt(j));
				i++;
			}
		}

		Particule P1 = new Particule(P.Chemin, P.w, v);
		return P1;
	}

	Vector<Ville> PositionDonneChemin() {
		Vector<Position> CP = PositionDonnePosition(this.P);
		Vector<Ville> CC = CopieDuChemin();
		Vector<Ville> CI = new Vector<Ville>();
		Vector<Ville> chemindice = new Vector<Ville>();
		int i = 1;
		Ville ville = new Ville(CC.elementAt(0).nom, CC.elementAt(0).x, CC.elementAt(0).y);
		chemindice.addElement(ville);

		while (i < CP.size()) {
			int XP = (int) CP.elementAt(i).x;
			int YP = (int) CP.elementAt(i).y;

			for (int j = 1; j < CC.size(); j++) {
				int XC = (int) CC.elementAt(j).x;
				if (XP == XC) {
					Ville I = new Ville(CC.elementAt(j).nom, CC.elementAt(j).x, CC.elementAt(j).y);
					CI.addElement(I);
				}
			}

			if (CI.size() == 0) {
				for (int j = 1; j < CC.size(); j++) {
					int YC = (int) CC.elementAt(j).y;
					if (YP == YC) {

						Ville I = new Ville(CC.elementAt(j).nom, CC.elementAt(j).x, CC.elementAt(j).y);
						CI.addElement(I);
					}
				}

				if (CI.size() == 0) {

					Ville I = new Ville("null", CP.elementAt(i).x, CP.elementAt(i).y);
					chemindice.addElement(I);
					CP.removeElementAt(i);
					i--;
				}

				else if (CI.size() != 0) {

					Ville I = new Ville();
					I = Approximation_X(CI, CP.elementAt(i));
					CP.removeElementAt(i);
					i--;
					chemindice.addElement(I);

					int ii = 1;
					while (ii < CC.size()) {
						if (I.nom == CC.elementAt(ii).nom) {
							CC.removeElementAt(ii);
						}
						ii++;
					}
				}
			} // END YYYYY

			else if (CI.size() != 0) {
				Ville I = new Ville();
				I = Approximation_Y(CI, CP.elementAt(i));
				chemindice.addElement(I);
				CP.removeElementAt(i);
				i--;
				int ii = 1;
				while (ii < CC.size()) {
					if (I.nom == CC.elementAt(ii).nom) {
						CC.removeElementAt(ii);
					}
					ii++;
				}
			}

			i++;
		}

		Vector<Ville> NewV = new Vector<Ville>();
		CC.removeElementAt(0);
		NewV = TriChemin(CC);
		this.Chemin.removeAllElements();

		int i1 = 0;
		while (i1 < chemindice.size()) {
			Ville V = new Ville(chemindice.elementAt(i1).nom, chemindice.elementAt(i1).x, chemindice.elementAt(i1).y);
			this.Chemin.addElement(V);
			i1++;
		}
		int index = 0;
		for (int l = 0; l < this.Chemin.size(); l++) {
			if (this.Chemin.elementAt(l).nom == "null") {
				this.Chemin.elementAt(l).nom = NewV.elementAt(index).nom;
				this.Chemin.elementAt(l).x = NewV.elementAt(index).x;
				this.Chemin.elementAt(l).y = NewV.elementAt(index).y;
				index++;
			}
		}

		return this.Chemin;
	}

	Ville Approximation_X(Vector<Ville> CI, Position p) {
		Ville VI = new Ville();
		if (CI.size() == 1) {
			VI.nom = CI.elementAt(0).nom;
			VI.x = CI.elementAt(0).x;
			VI.y = CI.elementAt(0).y;
		}

		else if (CI.size() != 1) {
			Vector<VecteurMin> VM = new Vector<VecteurMin>();
			for (int i = 0; i < CI.size(); i++) {

				VecteurMin valeurs = new VecteurMin(CI.elementAt(i).nom, CI.elementAt(i).x, CI.elementAt(i).y,
						Math.abs((p.x - CI.elementAt(i).x)));
				VM.addElement(valeurs);
			}
			double mintrouve = TrouverLeMin(VM);
			for (int j = 0; j < VM.size(); j++) {
				if (mintrouve == VM.elementAt(j).Sous) {
					VI.nom = VM.elementAt(j).nom;
					VI.x = VM.elementAt(j).x;
					VI.y = VM.elementAt(j).y;
				}
			}
		}
		CI.removeAllElements();
		return VI;
	}

	Ville Approximation_Y(Vector<Ville> CI, Position p) {
		Ville VI = new Ville();
		Vector<VecteurMin> VM = new Vector<VecteurMin>();
		if (CI.size() == 1) {

			VI.nom = CI.elementAt(0).nom;
			VI.x = CI.elementAt(0).x;
			VI.y = CI.elementAt(0).y;
		}

		else if (CI.size() != 1) {
			for (int i = 0; i < CI.size(); i++) {
				VecteurMin valeurs = new VecteurMin(CI.elementAt(i).nom, CI.elementAt(i).x, CI.elementAt(i).y,
						Math.abs((p.y - CI.elementAt(i).y)));
				VM.addElement(valeurs);
			}
			double mintrouve = TrouverLeMin(VM);
			for (int j = 0; j < VM.size(); j++) {
				if (mintrouve == VM.elementAt(j).Sous) {
					VI.nom = VM.elementAt(j).nom;
					VI.x = VM.elementAt(j).x;
					VI.y = VM.elementAt(j).y;
				}
			}
		}
		CI.removeAllElements();
		return VI;
	}

	Vector<Ville> TriChemin(Vector<Ville> V) {
		Vector<Ville> NewV = new Vector<Ville>();
		double max = 0;
		int i;
		int index;
		while (V.size() != 0) {

			if (V.size() == 1) {
				index = 0;
				i = 1;
				max = V.elementAt(index).x;
				Ville I = new Ville(V.elementAt(index).nom, V.elementAt(index).x, V.elementAt(index).y);
				NewV.addElement(I);
				V.removeElementAt(index);
			} else {
				index = 0;
				i = 1;
				max = V.elementAt(index).x;
				while (i < V.size()) {
					if (max < V.elementAt(i).x) {
						max = V.elementAt(i).x;
						index = i;
					}

					i++;
				}
				Ville I = new Ville(V.elementAt(index).nom, V.elementAt(index).x, V.elementAt(index).y);
				NewV.addElement(I);
				V.removeElementAt(index);

			}

		}

		return NewV;
	}

	double TrouverLeMin(Vector<VecteurMin> VM) {
		double min = 0;
		int i = 1;
		min = VM.elementAt(0).Sous;
		while (i < VM.size()) {
			if (min > VM.elementAt(i).Sous) {
				min = VM.elementAt(i).Sous;
			}
			i++;
		}
		return min;
	}

	Vector<Ville> CheminDonneChemin(Vector<Ville> villes) {
		Vector<Ville> V = new Vector<Ville>();
		for (int i = 0; i < villes.size(); i++) {
			Ville I = new Ville(villes.elementAt(i).nom, villes.elementAt(i).x, villes.elementAt(i).y);
			V.addElement(I);
		}
		return V;
	}

	Vector<Ville> CopieDuChemin() {
		Vector<Ville> V = new Vector<Ville>();
		for (int i = 0; i < this.Chemin.size(); i++) {
			Ville I = new Ville(this.Chemin.elementAt(i).nom, this.Chemin.elementAt(i).x, this.Chemin.elementAt(i).y);
			V.addElement(I);
		}
		return V;
	}

	Vector<Position> CheminDonnePosition() {
		Vector<Position> P = new Vector<Position>();
		for (int i = 0; i < this.Chemin.size(); i++) {
			Position XY = new Position(this.Chemin.elementAt(i).x, this.Chemin.elementAt(i).y);
			P.addElement(XY);
		}
		return P;
	}

	Vector<Position> PositionDonnePosition(Vector<Position> p) {
		Vector<Position> P = new Vector<Position>();
		for (int i = 0; i < p.size(); i++) {
			Position XY = new Position(p.elementAt(i).x, p.elementAt(i).y);
			P.addElement(XY);
		}
		return P;
	}

	void ToStringP() {
		System.out.println("LE CHEMIN:");
		for (int i = 0; i < this.Chemin.size(); i++) {
			System.out.println("   " + this.Chemin.elementAt(i).nom + " " + "(" + this.Chemin.elementAt(i).x + ";"
					+ this.Chemin.elementAt(i).y + ")");
		}
		System.out.println("La POSITION:");
		for (int i = 0; i < this.P.size(); i++) {
			System.out.println("  (" + this.P.elementAt(i).x + ";" + this.P.elementAt(i).y + ")");
		}
		System.out.println("LA MEILLEUR POSITION DE LA PARTICULE:");
		for (int i = 0; i < this.PidBest.size(); i++) {
			System.out.println("  (" + this.PidBest.elementAt(i).x + ";" + this.PidBest.elementAt(i).y + ")");
		}
		System.out.println("LES COUTS:");
		for (int i = 0; i < this.Couts.size(); i++) {
			System.out.println("  " + this.Couts.elementAt(i));
		}
		System.out.println("LES VITESSES:");
		for (int i = 0; i < this.Vid.size(); i++) {
			System.out.println("  (" + this.Vid.elementAt(i).Vx + ";" + this.Vid.elementAt(i).Vy + ")");
		}
		System.out.println("FITNESS:" + this.Fitness);
	}
}
