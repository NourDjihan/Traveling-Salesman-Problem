package algorithme_genetique;

import java.util.Random;
import java.util.Vector;
import commun.Ville;

public class Population {
	Vector<Individu> population = new Vector<Individu>();
	Individu BestI = new Individu();
	double P;

	Population(Population P) {
		Population p = new Population(P.population, P.P);
		this.population = p.population;
	}

	Population(Vector<Individu> V, double P) {
		Vector<Individu> VV = new Vector<Individu>();
		for (int i = 0; i < VV.size(); i++) {
			this.population.addElement(VV.elementAt(i));
		}
		this.P = P;
	}

	Population(Individu I, int NbrIndividu, double P) {
		this.population.addElement(I);
		for (int i = 1; i < NbrIndividu; i++) {
			this.population.addElement(I.aleatoire());
		}

		this.P = P;
		Population p = new Population();
		p = this.tri();
		this.BestI.Chemin.removeAllElements();
		Individu I1 = new Individu(p.population.elementAt(0).Chemin);
		for (int i = 0; i < I1.Chemin.size(); i++) {
			Ville V = new Ville(I1.Chemin.elementAt(i).nom, I1.Chemin.elementAt(i).x, I1.Chemin.elementAt(i).y);
			this.BestI.Chemin.addElement(V);
		}
		this.BestI.CalculerCouts(this.BestI.GetChoiceValue());
		this.BestI.CalculFitness();
	}

	Population() {
	}

	Population tri() {
		int i = 0;
		int j = 0;
		Individu I = new Individu();
		while (i < this.population.size()) {
			j = i + 1;
			while (j < this.population.size()) {
				if ((this.population.elementAt(i).Fitness) > (this.population.elementAt(j).Fitness)) {
					I = this.population.elementAt(i);
					this.population.set(i, this.population.elementAt(j));
					this.population.set(j, I);
					j++;
				} else {
					j++;
				}
			}
			i++;
		}
		Population P1 = new Population();
		int k = 0;
		if (this.population.size() % 2 == 0) {
			while (k < this.population.size() / 2) {
				P1.population.addElement(this.population.elementAt(k));
				k++;
			}
		} else if (this.population.size() % 2 != 0) {
			while (k <= (this.population.size() - 1) / 2) {
				P1.population.addElement(this.population.elementAt(k));
				k++;
			}
		}

		return P1;
	}

	Population Trier() {
		Population P = new Population();
		Population Copy = new Population();
		Copy = this.Copier();

		while (Copy.population.size() != 0) {
			double fitness = Copy.population.elementAt(0).Fitness;
			for (int i = 1; i < Copy.population.size(); i++) {
				if (fitness > Copy.population.elementAt(i).Fitness) {
					fitness = Copy.population.elementAt(i).Fitness;
				}
			}
			for (int j = 0; j < Copy.population.size(); j++) {
				if (fitness == Copy.population.elementAt(j).Fitness) {
					Individu I = new Individu(this.population.elementAt(j).Chemin);
					P.population.addElement(I);
					Copy.population.removeElementAt(j);
				}
			}
		}

		return P;

	}

	Population Copier() {

		Population P = new Population();
		int i = 0;
		while (i < this.population.size()) {
			Individu I = new Individu(this.population.elementAt(i));
			P.population.addElement(I);
			i++;
		}

		return P;
	}

	Population Croisement_Selection(int Position, Individu I) {
		int size = this.population.size();
		Population P = new Population();
		P = this.tri();
		Population Parent = P.Copier();
		Population P1 = new Population();
		int i = 0;

		int j = P.population.size() - 1;

		while ((i < P.population.size() / 2) && (j >= P.population.size() / 2)) {
			P.population.elementAt(i).Croisement(P.population.elementAt(j), Position, I);
			Individu I1 = new Individu(P.population.elementAt(i));
			P1.population.addElement(I1);
			Individu I2 = new Individu(P.population.elementAt(j));
			P1.population.addElement(I2);

			i++;
			j--;
		}
		i = 0;
		j = 0;
		while (i <= size - P1.population.size()) {
			while (j < Parent.population.size()) {
				Individu II = new Individu(Parent.population.elementAt(j));
				P1.population.addElement(II);
				j++;
			}
			i++;

		}

		this.population = P1.population;
		return this;
	}

	Population Mutation() {
		int size = this.population.elementAt(0).Chemin.size() - 1;
		Random Dice = new Random();
		int Position1 = Dice.nextInt(size - 1 + 1) + 1;
		int Position2 = Dice.nextInt(size - 1 + 1) + 1;

		double nbr = Math.random() * (1 - 0);
		if (nbr <= P) {
			int i = 0;
			while (i < this.population.size()) {
				this.population.elementAt(i).Permutation(Position1, Position2);

				i++;
			}
		}

		Population P = new Population();
		P = this.Trier();
		this.BestI.Chemin.removeAllElements();
		Individu I = new Individu(P.population.elementAt(0).Chemin);
		for (int i = 0; i < I.Chemin.size(); i++) {
			Ville V = new Ville(I.Chemin.elementAt(i).nom, I.Chemin.elementAt(i).x, I.Chemin.elementAt(i).y);
			this.BestI.Chemin.addElement(V);
		}
		this.BestI.CalculerCouts(this.BestI.GetChoiceValue());
		this.BestI.CalculFitness();

		return this;
	}

	void AfficherChromosome() {
		int j;
		for (int i = 0; i < this.population.size(); i++) {
			j = 0;
			while (j < this.population.elementAt(i).Chemin.size()) {
				System.out.print(" " + this.population.elementAt(i).Chemin.elementAt(j).nom);
				j++;
			}
			System.out.println(this.population.elementAt(i).Fitness);
			System.out.println("\n");
		}
		System.out.print(this.BestI.Fitness);

		System.out.println("FIN");
	}

	void AfficherBestIndividual() {
		System.out.println(this.BestI.Fitness);
		for (int i = 0; i < this.BestI.Chemin.size(); i++) {
			System.out.print("  " + this.BestI.Chemin.elementAt(i).nom);
		}
	}

	public static void main(String[] args) {

	}

}
