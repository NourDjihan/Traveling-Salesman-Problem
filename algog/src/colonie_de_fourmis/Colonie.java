package colonie_de_fourmis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import commun.Ville;

public class Colonie {
	////////////////////////// les attributs/////////////////////////
	Vector<Fourmi> Ants = new Vector<Fourmi>(); //
	Vector<Pelement> P = new Vector<Pelement>(); //
	Vector<Pelement> evap = new Vector<Pelement>(); //
	////////////////////////////////////////////////////////////////

	////////////////////////// les
	////////////////////////// constructeurs///////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Colonie() {
	}

	public Colonie(int nbr_ants, Vector<Ville> v) {
		for (int i = 0; i < nbr_ants; i++) {
			Fourmi a = new Fourmi();
			Ants.addElement(a);
		}
		int i = 0;
		int j = 0;
		for (i = 0; i < v.size() - 1; i++) {
			for (j = i + 1; j < v.size(); j++) {
				this.P.addElement(new Pelement(v.elementAt(i).nom, v.elementAt(j).nom, 0.1,
						DistanceBetween(v.elementAt(i), v.elementAt(j), GetChoiceValue())));
				this.evap.addElement(new Pelement(v.elementAt(i).nom, v.elementAt(j).nom, 0.0, 0));
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////// best Ant dans la
	////////////////////////////////////// colonie////////////////////////////////////////////////////////////
	Fourmi Best() {
		int index = 0;
		double bestf = this.Ants.firstElement().Fitness;
		for (int i = 1; i < this.Ants.size(); i++) {
			if (bestf > this.Ants.elementAt(i).Fitness) {
				bestf = this.Ants.elementAt(i).Fitness;
				index = i;
			}
		}

		return new Fourmi(this.Ants.elementAt(index));
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////// Fonction
	//////////////////////////////////////////// d'evaporation////////////////////////////////////////
	void Evapuration(double k) {

		for (int j = 0; j < this.P.size(); j++) {

			this.P.elementAt(j).ph = (1 - k) * this.P.elementAt(j).ph + this.evap.elementAt(j).ph;

		}

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	boolean contain(Vector<Pelement> p, Pelement element) {
		boolean ok = false;
		for (int i = 0; i < p.size(); i++) {
			if ((((element.first == p.elementAt(i).first) && (element.second == p.elementAt(i).second))
					|| ((element.first == p.elementAt(i).second) && (element.second == p.elementAt(i).first)))
					&& (element.ph == p.elementAt(i).ph) && (element.dist == p.elementAt(i).dist)) {
				ok = true;
				break;
			}
		}
		return ok;
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

	double DistanceBetween(Ville depart, Ville element, int choice) {
		double di = 0;
		if (choice == 0) {
			di = DistanceRe(depart.x, depart.y, element.x, element.y) / 1000;
		} else if (choice == 1) {
			di = Math.sqrt(Math.pow(element.x - depart.x, 2) + Math.pow(element.y - depart.y, 2));
		}
		return di;
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

	void VectorTrie(Vector<Pelement> v) {

		for (int i = 0; i < v.size() - 1; i++) {
			for (int j = i + 1; j < v.size(); j++) {
				if (v.elementAt(i).ph < v.elementAt(j).ph) {
					Pelement p1 = new Pelement(v.elementAt(j).first, v.elementAt(j).second, v.elementAt(j).ph,
							v.elementAt(j).dist);
					v.elementAt(j).first = v.elementAt(i).first;
					v.elementAt(j).second = v.elementAt(i).second;
					v.elementAt(j).ph = v.elementAt(i).ph;
					v.elementAt(j).dist = v.elementAt(i).dist;
					v.elementAt(i).first = p1.first;
					v.elementAt(i).second = p1.second;
					v.elementAt(i).ph = p1.ph;
					v.elementAt(i).dist = p1.dist;
				}
			}
		}
	}

	void CopyVectorPelement(Vector<Pelement> copy, Vector<Pelement> original) {

		for (int i = 0; i < original.size(); i++) {
			Pelement v1 = new Pelement(original.elementAt(i).first, original.elementAt(i).second,
					original.elementAt(i).ph, original.elementAt(i).dist);
			copy.addElement(v1);
		}
	}

	void DeletOcurenceOf(String d, Vector<Pelement> p) {
		int i = 0;
		while (!p.isEmpty() && (i < p.size())) {
			if (p.elementAt(i).first == d || (p.elementAt(i).second == d)) {
				p.remove(i);
			} else {
				i++;
			}

		}
	}

	int FindOut(String d, Vector<Pelement> p) {
		int index = 0;
		for (int i = 0; i < p.size(); i++) {
			if (p.elementAt(i).first == d) {
				index = i;
				break;
			}
		}
		return index;
	}

	void AddReverse(Vector<Pelement> p) {
		Vector<Pelement> copy = new Vector<Pelement>();
		CopyVectorPelement(copy, p);
		for (int i = 0; i < copy.size(); i++) {
			String in = copy.elementAt(i).first;
			copy.elementAt(i).first = copy.elementAt(i).second;
			copy.elementAt(i).second = in;
			p.addElement(copy.elementAt(i));
		}

	}
	//////////////////////////////////////// Charger les Fourmies(Affectation
	//////////////////////////////////////// les trajets
	//////////////////////////////////////// possibles)//////////////////////////////////////////////////

	void LoadingAll(Vector<Ville> v, double a, double b, double q) {
		for (int i = 0; i < this.Ants.size(); i++) {
			this.Ants.elementAt(i).chemin.removeAllElements();
			this.Ants.elementAt(i).Distance.removeAllElements();

			Vector<Ville> fourm = new Vector<Ville>();
			Ville depart = new Ville(v.firstElement());
			fourm.add(depart);
			Vector<Ville> list = new Vector<Ville>();
			CopyVectorVille(list, v);
			list.remove(list.firstElement());
			while (!list.isEmpty()) {
				Ville next = new Ville(this.Ants.elementAt(i).NextVille(depart, list, this.P, a, b));

				fourm.add(next);
				depart = new Ville(next);
				list.removeElementAt(IndexOf(next.nom, list));
			}

			Fourmi F = new Fourmi(fourm);
			int j = 0;
			int h = 0;
			int k = 0;
			double d = 0;
			for (int i1 = 0; i1 < F.chemin.size() - 1; i1++) {
				k = i1;
				d = 0;
				for (h = 0; h <= k; h++) {
					d = d + F.Distance.elementAt(h);
				}
				j = i1 + 1;
				this.P.elementAt(getIndexOf(F.chemin.elementAt(i1).nom, F.chemin.elementAt(j).nom, this.P)).ph = this.P
						.elementAt(getIndexOf(F.chemin.elementAt(i1).nom, F.chemin.elementAt(j).nom, this.P)).ph
						+ q / d;
				this.evap.elementAt(getIndexOf(F.chemin.elementAt(i1).nom, F.chemin.elementAt(j).nom,
						this.evap)).ph = this.evap.elementAt(
								getIndexOf(F.chemin.elementAt(i1).nom, F.chemin.elementAt(j).nom, this.evap)).ph
								+ q / d;
			}
			d = d + F.Distance.lastElement();
			this.P.elementAt(getIndexOf(F.chemin.lastElement().nom, v.firstElement().nom, this.P)).ph = this.P
					.elementAt(getIndexOf(F.chemin.lastElement().nom, v.firstElement().nom, this.P)).ph + q / d;
			this.evap.elementAt(getIndexOf(F.chemin.lastElement().nom, v.firstElement().nom, this.evap)).ph = this.evap
					.elementAt(getIndexOf(F.chemin.lastElement().nom, v.firstElement().nom, this.evap)).ph + q / d;
			this.Ants.elementAt(i).chemin = new Vector<Ville>();
			CopyVectorVille(this.Ants.elementAt(i).chemin, F.chemin);
			this.Ants.elementAt(i).Distance = new Vector<Double>();
			CopyVectorDouble(this.Ants.elementAt(i).Distance, F.Distance);
			this.Ants.elementAt(i).Fitness = F.Fitness;
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////// La derniére
	//////////////////////////////////////////// fourmie////////////////////

	Fourmi Last(Vector<Ville> v) {
		Vector<Pelement> last = new Vector<Pelement>();
		Vector<Pelement> Pc = new Vector<Pelement>();
		CopyVectorPelement(Pc, this.P);
		AddReverse(Pc);
		VectorTrie(Pc);
		String sec = v.firstElement().nom;
		while (!Pc.isEmpty()) {
			Pelement find = new Pelement(Pc.elementAt(FindOut(sec, Pc)));
			last.add(find);
			DeletOcurenceOf(sec, Pc);

			sec = find.second;

		}
		Vector<Ville> l = new Vector<Ville>();
		Ville v1 = new Ville(v.elementAt(IndexOf(last.firstElement().first, v)));
		l.add(v1);
		Ville v2 = new Ville(v.elementAt(IndexOf(last.firstElement().second, v)));
		l.add(v2);
		for (int i = 1; i < last.size(); i++) {
			Ville v3 = new Ville(v.elementAt(IndexOf(last.elementAt(i).second, v)));
			l.add(v3);
		}

		return new Fourmi(l);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	Vector<Pelement> GetP() {
		return P;
	}

	
	
}
