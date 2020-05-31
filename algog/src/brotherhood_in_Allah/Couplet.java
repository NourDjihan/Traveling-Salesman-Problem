package brotherhood_in_Allah;

import java.util.Vector;

import commun.Ville;

public class Couplet {
	MuslimIndividual[] CoupletArray = new MuslimIndividual[2];
	String CoupletType;

	Couplet() {
	}

	Couplet(MuslimIndividual I1, MuslimIndividual I2, String Couplet) {
		this.CoupletArray[0] = I1;
		this.CoupletArray[1] = I2;
		this.CoupletType = Couplet;
	}

	Couplet(Vector<Ville> I1, Vector<Ville> I2, String Couplet) {
		this.CoupletType = Couplet;
		MuslimIndividual M1 = new MuslimIndividual(I1);
		MuslimIndividual M2 = new MuslimIndividual(I2);
		this.CoupletArray[0] = M1;
		this.CoupletArray[1] = M2;
	}

	Couplet(Vector<Ville> I, String NeutralMuslim) {
		MuslimIndividual M = new MuslimIndividual(I);
		MuslimIndividual MM = new MuslimIndividual(I);
		this.CoupletArray[0] = M;
		this.CoupletArray[1] = MM;
		this.CoupletType = NeutralMuslim;
	}

}
