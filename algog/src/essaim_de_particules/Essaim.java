package essaim_de_particules;

import java.util.Vector;

public class Essaim {

	int IndexMeilleurSolution;
	Vector<Particule> Particules = new Vector<Particule>();
	Vector<Position> PgdBest = new Vector<Position>();

	Essaim(Particule P, int nbrParticules, double vitesse) {
		this.Particules.addElement(P);
		for (int i = 1; i < nbrParticules; i++) {
			this.Particules.addElement(P.Aleatoire(vitesse));
		}

	}

	Essaim MAJ_Positions() {
		this.PgdBest = WhoIsTheBest();
		System.out.println("\n" + this.Particules.elementAt(IndexMeilleurSolution).Fitness);
		for (int i1 = 0; i1 < this.PgdBest.size(); i1++) {

			System.out
					.println("            (" + this.PgdBest.elementAt(i1).x + ";" + this.PgdBest.elementAt(i1).y + ")");

		}
		int i = 0;
		while (i < this.Particules.size()) {
			this.Particules.elementAt(i).MAJP(PgdBest);
			i++;
		}
		return this;
	}

	Vector<Position> PositionDonnePosition(Vector<Position> p) {
		Vector<Position> P = new Vector<Position>();
		for (int i = 0; i < p.size(); i++) {
			Position XY = new Position(p.elementAt(i).x, p.elementAt(i).y);
			P.addElement(XY);
		}
		return P;
	}

	Vector<Position> WhoIsTheBest() {
		Vector<Position> BestP = new Vector<Position>();
		double BestFitness = this.Particules.elementAt(0).Fitness;
		int i = 1;
		while (i < this.Particules.size()) {
			if (BestFitness > this.Particules.elementAt(i).Fitness) {
				BestFitness = this.Particules.elementAt(i).Fitness;
				IndexMeilleurSolution = i;
			}
			i++;
		}
		BestP = PositionDonnePosition(this.Particules.elementAt(IndexMeilleurSolution).P);
		return BestP;

	}

	int Best() {
		int d = 1;
		double bes = this.Particules.elementAt(0).Fitness;
		int best = 0;
		while (d < this.Particules.size()) {
			if (this.Particules.elementAt(d).Fitness < bes) {
				bes = this.Particules.elementAt(d).Fitness;
				best = d;
			}
			d++;
		}

		return best;
	}

	void ToStringE() {
		for (int j = 0; j < this.Particules.size(); j++) {
			System.out.println("PARTICULE NUMBER ***************************" + j);
			System.out.println("BestPosition:");
			for (int i = 0; i < this.PgdBest.size(); i++) {
				System.out.println("(" + this.PgdBest.elementAt(i).x + ";" + this.PgdBest.elementAt(i).y + ")");
			}
			this.Particules.elementAt(j).ToStringP();
		}

	}

}
