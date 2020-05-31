package brotherhood_in_Allah;

import java.util.Random;
import java.util.Vector;

public class MuslimsCommunity {
	Vector<MuslimIndividual> MC = new Vector<MuslimIndividual>();
	Vector<MuslimIndividual> BestSolutions = new Vector<MuslimIndividual>();
	MuslimIndividual BestMI = new MuslimIndividual();
	Vector<Vector<MuslimIndividual>> Clusters = new Vector<Vector<MuslimIndividual>>();
	Vector<Vector<Couplet>> Couplets = new Vector<Vector<Couplet>>();

	MuslimsCommunity() {
	}

	MuslimsCommunity(MuslimIndividual I, int nbrMuslims, int nbrClusters) {
		this.MC.addElement(I);
		for (int i = 1; i < nbrMuslims; i++) {
			this.MC.addElement(I.Aleatoire());
		}
		this.TrierMuslims();
		this.BestSolutions(nbrClusters);
		this.DevideMuslimsCommunity(nbrClusters);
		for (int i = 0; i < this.Clusters.size(); i++) {
			this.TrierClusters(i);
		}
		for (int i = 0; i < this.Clusters.size(); i++) {
			this.CreateCouplet(this.Clusters.elementAt(i), i);
		}
	}

	MuslimsCommunity TrierMuslims() {
		MuslimsCommunity mc = new MuslimsCommunity();
		Double MinFitness;
		while (this.MC.size() != 0) {
			MinFitness = this.MC.elementAt(0).Fitness;

			for (int i = 1; i < this.MC.size(); i++) {
				if (this.MC.elementAt(i).Fitness < MinFitness) {
					MinFitness = this.MC.elementAt(i).Fitness;
				}
			}
			for (int k = 0; k < this.MC.size(); k++) {
				if (this.MC.elementAt(k).Fitness == MinFitness) {
					MuslimIndividual I = new MuslimIndividual(this.MC.elementAt(k).Solution);
					mc.MC.addElement(I);
					this.MC.removeElementAt(k);
				}

			}
		}

		this.CopyCommunity(mc);
		return this;
	}

	Vector<MuslimIndividual> TrierClusters(int indexCluster) {

		Vector<MuslimIndividual> Copy = new Vector<MuslimIndividual>();
		Copy = this.CopyCluster(this.Clusters.elementAt(indexCluster));
		this.Clusters.elementAt(indexCluster).removeAllElements();
		MuslimIndividual MII = new MuslimIndividual(Copy.elementAt(0).Solution);
		this.Clusters.elementAt(indexCluster).addElement(MII);
		Copy.removeElementAt(0);

		Double MinFitness;
		while (Copy.size() != 0) {
			MinFitness = Copy.elementAt(0).Fitness;
			for (int j = 1; j < Copy.size(); j++) {
				if (Copy.elementAt(j).Fitness < MinFitness) {
					MinFitness = Copy.elementAt(j).Fitness;
				}
			}
			for (int k = 0; k < Copy.size(); k++) {
				if (Copy.elementAt(k).Fitness == MinFitness) {
					MuslimIndividual I = new MuslimIndividual(Copy.elementAt(k).Solution);
					this.Clusters.elementAt(indexCluster).addElement(I);
					Copy.removeElementAt(k);
				}

			}

		}
		return this.Clusters.elementAt(indexCluster);
	}

	Vector<MuslimIndividual> TrierCluster(int indexCluster) {

		Vector<MuslimIndividual> Copy = new Vector<MuslimIndividual>();
		Copy = this.CopyCluster(this.Clusters.elementAt(indexCluster));
		this.Clusters.elementAt(indexCluster).removeAllElements();

		Double MinFitness;
		while (Copy.size() != 0) {
			MinFitness = Copy.elementAt(0).Fitness;
			for (int j = 1; j < Copy.size(); j++) {
				if (Copy.elementAt(j).Fitness < MinFitness) {
					MinFitness = Copy.elementAt(j).Fitness;
				}
			}
			for (int k = 0; k < Copy.size(); k++) {
				if (Copy.elementAt(k).Fitness == MinFitness) {
					MuslimIndividual I = new MuslimIndividual(Copy.elementAt(k).Solution);
					this.Clusters.elementAt(indexCluster).addElement(I);
					Copy.removeElementAt(k);
				}

			}

		}
		return this.Clusters.elementAt(indexCluster);
	}

	MuslimsCommunity CopyCommunity(MuslimsCommunity mc) {
		for (int i = 0; i < mc.MC.size(); i++) {
			MuslimIndividual I = new MuslimIndividual(mc.MC.elementAt(i).Solution);
			this.MC.addElement(I);
		}
		return this;
	}

	void AfficherMuslimsCommunity() {
		System.out.println("BestMi");
		this.BestMI.ToString();
		System.out.println("Best Solutions:\n");
		for (int i = 0; i < this.BestSolutions.size(); i++) {
			this.BestSolutions.elementAt(i).ToString();
		}

		System.out.println("LES CLUSTERS:");
		for (int i = 0; i < this.Clusters.size(); i++) {
			System.out.println("\n\nCLUSTERNumber*******************" + i);
			for (int j = 0; j < this.Clusters.elementAt(i).size(); j++) {
				System.out.println("\n");
				System.out.println(this.Clusters.elementAt(i).elementAt(j).Fitness);
				for (int k = 0; k < this.Clusters.elementAt(i).elementAt(j).Solution.size(); k++) {
					System.out.println(this.Clusters.elementAt(i).elementAt(j).Solution.elementAt(k).nom
							+ this.Clusters.elementAt(i).elementAt(j).Solution.elementAt(k).x
							+ this.Clusters.elementAt(i).elementAt(j).Solution.elementAt(k).y);
				}
			}
		}
		System.out.println("LES COUPLETS:");
		for (int i = 0; i < this.Couplets.size(); i++) {
			System.out.println("\n\n LES COUPLET IN " + i);
			for (int j = 0; j < this.Couplets.elementAt(i).size(); j++) {
				System.out.println("\nCouplet" + j);
				this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].ToString();
				this.Couplets.elementAt(i).elementAt(j).CoupletArray[1].ToString();
			}
		}

	}

	Vector<MuslimIndividual> BestSolutions(int nbrClusters) {
		MuslimIndividual M = new MuslimIndividual(this.MC.elementAt(0).Solution);
		this.BestMI = M;
		for (int i = 0; i < nbrClusters; i++) {
			MuslimIndividual MI = new MuslimIndividual(this.MC.elementAt(i).Solution);
			this.BestSolutions.addElement(MI);
		}

		for (int j = nbrClusters - 1; j >= 0; j--) {
			this.MC.removeElementAt(j);
		}

		return this.BestSolutions;
	}

	void DevideMuslimsCommunity(int nbrClusters) {
		MuslimsCommunity CMC = new MuslimsCommunity();
		CMC.CopyCommunity(this);
		CMC.BestSolutions = CopyCluster(this.BestSolutions);
		int size = CMC.MC.size() / nbrClusters;
		int finish = 0;
		int BestSolutionIndex = 0;
		boolean F = true;
		int end = 0;
		Vector<MuslimIndividual> Cluster = new Vector<MuslimIndividual>();
		while (F) {
			finish = 0;
			while (finish < size) {

				if (Cluster.size() == 0) {
					MuslimIndividual MI = new MuslimIndividual(CMC.BestSolutions.elementAt(BestSolutionIndex).Solution);
					Cluster.addElement(MI);
					BestSolutionIndex++;
				} else {
					Random rand = new Random();
					int randomNum = rand.nextInt(((CMC.MC.size() - 1) - 0) + 1) + 0;

					MuslimIndividual MI = new MuslimIndividual(CMC.MC.elementAt(randomNum).Solution);
					CMC.MC.removeElementAt(randomNum);
					Cluster.addElement(MI);
					finish++;
					end++;
					if (end == nbrClusters * size) {
						F = false;
					}
				}
			}

			Vector<MuslimIndividual> CC = new Vector<MuslimIndividual>();
			CC = CopyCluster(Cluster);
			Clusters.addElement(CC);
			Cluster.removeAllElements();
		}

		int LastClusterIndex = this.Clusters.size();
		if (CMC.MC.size() != 0) {
			for (int i = 0; i < CMC.MC.size(); i++) {
				Random rand = new Random();
				int randomNum = rand.nextInt(((CMC.MC.size() - 1) - 0) + 1) + 0;
				MuslimIndividual MI = new MuslimIndividual(CMC.MC.elementAt(randomNum).Solution);
				this.Clusters.elementAt(LastClusterIndex - 1).addElement(MI);
				CMC.MC.removeElementAt(randomNum);

			}
		}

	}

	Vector<Couplet> CreateCouplet(Vector<MuslimIndividual> V, int i) {
		Vector<Couplet> VecteurCouplet = new Vector<Couplet>();
		if (V.size() % 2 == 0) {
			int i1 = 0;
			int j = V.size() - 1;
			while ((i1 < V.size() / 2) && (j >= (V.size() / 2))) {
				Couplet couplet = new Couplet(V.elementAt(i1).Solution, V.elementAt(j).Solution, "Couplet");
				VecteurCouplet.addElement(couplet);
				i1++;
				j--;

			}
		} else if (V.size() % 2 != 0) {
			int i1 = 0;
			int j = V.size() - 1;
			while ((i1 < V.size() / 2) && (j > (V.size() / 2))) {
				Couplet couplet = new Couplet(V.elementAt(i1).Solution, V.elementAt(j).Solution, "Couplet");
				VecteurCouplet.addElement(couplet);
				i1++;
				j--;
			}

			Couplet couplet = new Couplet(V.elementAt(i1).Solution, "NMI");
			VecteurCouplet.addElement(couplet);

		}
		this.Couplets.add(i, VecteurCouplet);
		return this.Couplets.elementAt(i);
	}

	Vector<MuslimIndividual> CopyCluster(Vector<MuslimIndividual> Cluster) {
		Vector<MuslimIndividual> CC = new Vector<MuslimIndividual>();
		for (int i = 0; i < Cluster.size(); i++) {
			MuslimIndividual MI = new MuslimIndividual(Cluster.elementAt(i).Solution);
			CC.addElement(MI);
		}
		return CC;
	}

	Vector<Vector<Couplet>> MAJCouplets(int nbrClusters, int nbr_w) {
		int k = 0;
		// NMI
		for (int i = 0; i < this.Couplets.size(); i++) {
			for (int j = 0; j < this.Couplets.elementAt(i).size(); j++) {
				if (this.Couplets.elementAt(i).elementAt(j).CoupletType == "NMI") {
					MuslimIndividual M = new MuslimIndividual();
					M.CopyMI(this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Solution);
					M = M.Aleatoire();
					if (M.Fitness < this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Fitness) {
						Couplet C = new Couplet(M.Solution, "NMI");
						this.Couplets.elementAt(i).removeElementAt(j);
						this.Couplets.elementAt(i).add(j, C);
					}
				}
			}
		}
		// WMI
		for (int i = 0; i < this.Couplets.size(); i++) {
			for (int j = 0; j < this.Couplets.elementAt(i).size(); j++) {
				if (this.Couplets.elementAt(i).elementAt(j).CoupletType == "Couplet") {

					if (verifycopy(this.Couplets.elementAt(i).elementAt(j).CoupletArray[0],
							this.Couplets.elementAt(i).elementAt(j).CoupletArray[1])) {

						this.Couplets.elementAt(i)
								.elementAt(j).CoupletArray[1] = this.Couplets.elementAt(i).elementAt(j).CoupletArray[1]
										.Aleatoire();

						if (this.Couplets.elementAt(i).elementAt(j).CoupletArray[1].Fitness > this.Couplets.elementAt(i)
								.elementAt(j).CoupletArray[0].Fitness) {
							this.Couplets.elementAt(i).elementAt(j).CoupletArray[1]
									.CopyMI(this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Solution);
						}
					}

					else {
						while ((k < nbr_w)
								&& (this.Couplets.elementAt(i).elementAt(j).CoupletArray[1].Fitness > this.Couplets
										.elementAt(i).elementAt(j).CoupletArray[0].Fitness)) {
							MuslimIndividual I = new MuslimIndividual(
									this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Solution);
							I.Mutation();
							if (I.Fitness < this.Couplets.elementAt(i).elementAt(j).CoupletArray[1].Fitness) {
								this.Couplets.elementAt(i).elementAt(j).CoupletArray[1] = I;

							}
							k++;
						}
					}
				}
			}
		}
		// SMI
		for (int i = 0; i < this.Couplets.size(); i++) {
			for (int j = 0; j < this.Couplets.elementAt(i).size(); j++) {
				if (this.Couplets.elementAt(i).elementAt(j).CoupletType == "Couplet") {
					if (verifycopy(this.BestMI, this.Couplets.elementAt(i).elementAt(j).CoupletArray[0])) {
						this.Couplets.elementAt(i)
								.elementAt(j).CoupletArray[0] = this.Couplets.elementAt(i).elementAt(j).CoupletArray[0]
										.Aleatoire();
						if (this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Fitness > this.BestMI.Fitness) {
							this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].CopyMI(this.BestMI.Solution);
						}
					} else {
						MuslimIndividual I = new MuslimIndividual(this.BestMI.Solution);
						I.Mutation();
						if (I.Fitness < this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Fitness) {
							this.Couplets.elementAt(i).elementAt(j).CoupletArray[0] = I;
						}
					}
				}
			}
		}
		this.MAJClusters();
		return this.Couplets;
	}

	boolean verifycopy(MuslimIndividual I1, MuslimIndividual I2) {
		int size = I1.Solution.size();
		int count = 0;
		for (int i = 0; i < I1.Solution.size(); i++) {
			if (I1.Solution.elementAt(i).nom == I2.Solution.elementAt(i).nom) {
				count++;
			} else {
				count--;
			}
		}
		if (count == size) {
			return true;
		} else {
			return false;
		}

	}

	Vector<Vector<MuslimIndividual>> MAJClusters() {
		this.Clusters.removeAllElements();
		for (int i = 0; i < this.Couplets.size(); i++) {
			Vector<MuslimIndividual> V = new Vector<MuslimIndividual>();
			for (int j = 0; j < this.Couplets.elementAt(i).size(); j++) {
				if (this.Couplets.elementAt(i).elementAt(j).CoupletType == "Couplet") {
					MuslimIndividual I = new MuslimIndividual(
							this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Solution);
					MuslimIndividual II = new MuslimIndividual(
							this.Couplets.elementAt(i).elementAt(j).CoupletArray[1].Solution);
					V.addElement(I);
					V.addElement(II);

				} else if (this.Couplets.elementAt(i).elementAt(j).CoupletType == "NMI") {
					MuslimIndividual I = new MuslimIndividual(
							this.Couplets.elementAt(i).elementAt(j).CoupletArray[0].Solution);
					V.addElement(I);
				}
			}
			this.Clusters.addElement(V);
			this.TrierCluster(i);
		}
		for (int i = 0; i < this.Clusters.size(); i++) {
			this.BestSolutions.elementAt(i).CopyMI(this.Clusters.elementAt(i).elementAt(0).Solution);
		}
		double fitness = this.BestSolutions.elementAt(0).Fitness;
		for (int i = 1; i < this.BestSolutions.size(); i++) {
			if (fitness > this.BestSolutions.elementAt(i).Fitness) {
				fitness = this.BestSolutions.elementAt(i).Fitness;
			}
		}
		for (int i = 0; i < this.BestSolutions.size(); i++) {
			if (fitness == this.BestSolutions.elementAt(i).Fitness) {
				this.BestMI.CopyMI(this.Clusters.elementAt(i).elementAt(0).Solution);

			}
		}
		this.Couplets.removeAllElements();
		for (int i = 0; i < this.Clusters.size(); i++) {
			this.CreateCouplet(this.Clusters.elementAt(i), i);
		}
		return this.Clusters;
	}

}
