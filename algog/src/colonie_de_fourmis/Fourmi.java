package colonie_de_fourmis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Vector;

import commun.Ville;

public class Fourmi {
	////////////////////////////////////////////////
	Vector<Ville> chemin = new Vector<Ville>(); //
	Vector<Double> Distance = new Vector<Double>();//
	double Fitness; //

	////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Fourmi() {
	}

	public Fourmi(Fourmi F) {
		CopyVectorVille(this.chemin, F.chemin);
		CopyVectorDouble(this.Distance, F.Distance);
		this.Fitness = F.Fitness;
	}

	public Fourmi(Vector<Ville> v) {

		CopyVectorVille(this.chemin, v);
		this.CalculerCouts(GetChoiceValue());
		this.Fitness = GetFitnesse(this.Distance);
	}

	double convertRad(double input) {
		return (Math.PI * input) / 180;
	}

	double DistanceRe(double lat_a_degre, double lon_a_degre, double lat_b_degre, double lon_b_degre) {
		double d = 0;
		double R = 6378000; // Rayon de la terre en mètre

		double lat_a = convertRad(lat_a_degre);
		double lon_a = convertRad(lon_a_degre);
		double lat_b = convertRad(lat_b_degre);
		double lon_b = convertRad(lon_b_degre);

		d = R * (Math.PI / 2 - Math
				.asin(Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(lon_b - lon_a) * Math.cos(lat_b) * Math.cos(lat_a)));
		return d;
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

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	Vector<Double> CalculerCouts(int choi) {
		this.Distance.removeAllElements();

		if (choi == 0) {
			int i = 0;
			int j = 1;
			while ((j < this.chemin.size()) && (i < this.chemin.size() - 1)) {
				this.Distance.addElement(DistanceRe(this.chemin.elementAt(i).x, this.chemin.elementAt(i).y,
						this.chemin.elementAt(j).x, this.chemin.elementAt(j).y) / 1000);
				j++;
				i++;
			}
			this.Distance.addElement(DistanceRe(this.chemin.lastElement().x, this.chemin.lastElement().y,
					this.chemin.firstElement().x, this.chemin.firstElement().y) / 1000);
		} else if (choi == 1) {
			int i = 0;
			int j = 1;
			while ((j < this.chemin.size()) && (i < this.chemin.size() - 1)) {
				this.Distance.addElement(Math.sqrt(Math.pow(this.chemin.elementAt(j).x - this.chemin.elementAt(i).x, 2)
						+ Math.pow(this.chemin.elementAt(j).y - this.chemin.elementAt(i).y, 2)));
				j++;
				i++;
			}
			this.Distance.addElement(Math.sqrt(
					Math.pow(this.chemin.elementAt(0).x - this.chemin.elementAt(this.chemin.size() - 1).x, 2) + Math
							.pow(this.chemin.elementAt(0).y - this.chemin.elementAt(this.chemin.size() - 1).y, 2)));
		}

		return this.Distance;
	}

	/////////////////////////////////////////// La ville
	/////////////////////////////////////////// suivante////////////////////////////////////////////////////////////////////////////////////
	Ville NextVille(Ville depart, Vector<Ville> v, Vector<Pelement> P, double a, double b) {
		int i = 0;
		int j = 0;
		int k = 0;
		double sum = 0;
		for (i = 0; i < v.size(); i++) {
			sum = sum + ProbaOf(depart, v.elementAt(i), P, a, b);
		}
		Vector<Double> interval = new Vector<Double>();
		interval.addElement(ProbaOf(depart, v.firstElement(), P, a, b) / sum);

		for (j = 1; j < v.size(); j++) {
			interval.addElement(interval.lastElement() + (ProbaOf(depart, v.elementAt(j), P, a, b) / sum));

		}
		Random r = new Random();
		double random = r.nextInt(10);

		double ran = random / 10;

		int IndexNext = 0;
		k = 0;
		for (k = 0; k < interval.size(); k++) {
			if (ran < interval.elementAt(k)) {
				IndexNext = k;
				break;
			}
		}
		Ville next = new Ville(v.elementAt(IndexNext));
		return next;
	}

	// ************************************************************************************************

	////////////////////////////////// Calculer [T(ij)^a * N(ij)^b]
	////////////////////////////////// ///////////////////////////////////////
	double ProbaOf(Ville depart, Ville element, Vector<Pelement> P, double a, double b) {

		return Math.pow(P.elementAt(getIndexOf(depart.nom, element.nom, P)).ph, a)
				* Math.pow(P.elementAt(getIndexOf(depart.nom, element.nom, P)).dist, b);
	}

	public int getIndexOf(String f, String s, Vector<Pelement> P) {
		int r = 0;
		for (int i = 0; i < P.size(); i++) {
			if ((f == P.elementAt(i).first && (s == P.elementAt(i).second))
					|| (s == P.elementAt(i).first && (f == P.elementAt(i).second))) {
				r = i;
				break;
			}
		}

		return r;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	int IndexOf(String s, Vector<Ville> v) {
		int in = 0;
		for (int i = 0; i < v.size(); i++) {
			if (s == v.elementAt(i).nom) {
				in = i;
				break;
			}
		}
		return in;
	}

	double GetFitnesse(Vector<Double> v) {
		double fitt = 0;
		for (int i = 0; i < v.size(); i++) {
			fitt = fitt + v.elementAt(i);
		}
		return fitt;
	}

	double DistanceBetween(Ville depart, Ville element, int choice) {
		double di = 0;
		if (choice == 0) {
			di = DistanceRe(depart.x, depart.y, element.x, element.y) / 1000;
		} else if (choice == 1) {
			di = Math.sqrt(Math.pow(element.x - depart.x, 2) + Math.pow(element.y - depart.y, 2));
		}
		return di;
	}

	void CopyVectorVille(Vector<Ville> copy, Vector<Ville> original) {

		for (int i = 0; i < original.size(); i++) {
			Ville v1 = new Ville(original.elementAt(i).nom, original.elementAt(i).x, original.elementAt(i).y);
			copy.addElement(v1);
		}
	}

	void CopyVectorDouble(Vector<Double> copy, Vector<Double> original) {

		for (int i = 0; i < original.size(); i++) {
			double k = original.elementAt(i);
			copy.add(k);
		}
	}

}
