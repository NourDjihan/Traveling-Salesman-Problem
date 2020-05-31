package algorithme_genetique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;

import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.plot.XYPlot;

import org.jfree.chart.title.TextTitle;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.jfree.ui.RectangleEdge;
import commun.Ville;

public class AG extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JTabbedPane work = new JTabbedPane();
	static DefaultComboBoxModel<String> m = new DefaultComboBoxModel<String>();
	static Vector<Integer> iter = new Vector<Integer>();
	static Vector<Double> fitt = new Vector<Double>();
	static Vector<Ville> v = new Vector<Ville>();
	static int choi;
	static Vector<String> end = new Vector<String>();
	static double max = 0;
	// Labele principal AlgoGen***********
	ImageIcon i = new ImageIcon("C:/project_data/images/image.jpg");
	JLabel main_l = new JLabel();
	// ***************************

	// Precedent_annuler**********

	JButton annu = new JButton("Cancel");
	JPanel prec_annu = new JPanel();// FlowLayout
	// ***************************

	// les parametres*************
	String[] nbite;
	String[] nbind;
	static String[] nbcroi;
	String[] nbmut = { "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9" };
	static int nbcro = 7;

	JLabel nb_itiration = new JLabel("Number of generations :");
	JComboBox<String> t_iteration;
	JLabel nb_individu = new JLabel("Population size :");
	JComboBox<String> t_indiv;
	JLabel nb_croisement = new JLabel("Crossover point :");
	static JComboBox<String> t_croi;
	JLabel nb_mutation = new JLabel("Mutation chance :");
	JComboBox<String> t_mut;
	JPanel parametre = new JPanel();// GridLayout
	// ****************************

	// run_stop********************

	JButton run = new JButton("                                     Run                                     ");

	JPanel run_stop = new JPanel();// FlowLayout
	// ****************************

	// Resultat_Graph**************
	JTextArea res = new JTextArea();
	JScrollPane resultat = new JScrollPane(res);
	ChartPanel chartPanel;
	JPanel result_graph = new JPanel();// GridLayout
	// ****************************

	// parametre_runstop***********
	JPanel par_run_stop = new JPanel();// GridLayout
	// ****************************

	//////////////////////////////
	JPanel all = new JPanel();// par_run_stop(west) result_graph(center)
	/////////////////////////////
	// *****************************
	JPanel tab_work = new JPanel();

	public AG() {

		main_l.setBorder(BorderFactory.createEtchedBorder());
		main_l.setIcon(i);
		// ******************************************************************************************************************************************************
		// ******************************************************************************************************************************************************
		// les param
		int c;
		nbite = new String[1000];
		for (int k = 0; k < 1000; k++) {
			c = k + 1;
			nbite[k] = c + "";
		}
		nbind = new String[2000];
		for (int k = 0; k < 2000; k++) {
			c = k + 1;
			nbind[k] = c + "";
		}

		nbcroi = new String[nbcro];
		for (int k = 0; k < nbcro; k++) {
			c = k + 1;
			nbcroi[k] = c + "";
			m.addElement(nbcroi[k]);
		}

		t_iteration = new JComboBox<String>(nbite);
		t_indiv = new JComboBox<String>(nbind);
		t_croi = new JComboBox<String>(m);
		nb_itiration.setBorder(BorderFactory.createEtchedBorder());
		nb_itiration.setBorder(BorderFactory.createLineBorder(Color.black));
		nb_individu.setBorder(BorderFactory.createLineBorder(Color.black));
		nb_croisement.setBorder(BorderFactory.createLineBorder(Color.black));
		nb_mutation.setBorder(BorderFactory.createLineBorder(Color.black));
		t_mut = new JComboBox<String>(nbmut);
		parametre.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Settings"));
		parametre.setLayout(new GridLayout(0, 2));
		run.setEnabled(false);
		t_iteration.setEnabled(false);
		t_indiv.setEnabled(false);
		t_croi.setEnabled(false);
		t_mut.setEnabled(false);
		parametre.add(nb_itiration);
		parametre.add(t_iteration);
		parametre.add(nb_individu);
		parametre.add(t_indiv);
		parametre.add(nb_croisement);
		parametre.add(t_croi);
		parametre.add(nb_mutation);
		parametre.add(t_mut);
		parametre.setPreferredSize(new Dimension(50, 150));

		//

		// run_stop
		run_stop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Operations")));
		run_stop.setLayout(new FlowLayout());
		run_stop.add(run);

		//

		// par_runstop

		par_run_stop.setLayout(new BorderLayout());
		par_run_stop.add(parametre, BorderLayout.NORTH);
		par_run_stop.add(run_stop, BorderLayout.CENTER);

		//

		// result_graph

		result_graph.setLayout(new GridLayout(0, 1));
		result_graph.add(resultat);
		JFreeChart xylineChart = ChartFactory.createXYLineChart("Genetic Algorithm", "Iteration", "Best Fitness", null,
				PlotOrientation.VERTICAL, true, true, false);
		chartPanel = new ChartPanel(xylineChart);
		result_graph.add(chartPanel);
		work.addTab("        Work        ", result_graph);
		//
		// all

		all.setLayout(new BorderLayout());
		all.add(par_run_stop, BorderLayout.WEST);
		all.add(work, BorderLayout.CENTER);
		//

		// prec_annu
		prec_annu.setBorder(BorderFactory.createEtchedBorder());
		prec_annu.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		prec_annu.add(annu);
		//
		this.add(main_l, BorderLayout.NORTH);
		this.add(all, BorderLayout.CENTER);
		this.add(prec_annu, BorderLayout.SOUTH);
		this.setBounds(500, 500, 1100, 800);
		this.setLocation(50, 50);
		run.addActionListener(this);
		annu.addActionListener(this);

	}

	public void Update(int choice) {

		v.removeAllElements();
		if (choice == 1) {
			choi = choice;
			try {
				String n, x1, y1;
				BufferedReader names = new BufferedReader(new FileReader("C:/project_data/cities/citiesC.txt"));
				BufferedReader absi = new BufferedReader(new FileReader("C:/project_data/cities/x.txt"));
				BufferedReader ordo = new BufferedReader(new FileReader("C:/project_data/cities/y.txt"));

				while (((n = names.readLine()) != null) && (((x1 = absi.readLine()) != null))
						&& ((y1 = ordo.readLine()) != null)) {
					Ville v1 = new Ville(n, Double.parseDouble(x1), Double.parseDouble(y1));
					v.addElement(v1);
				}
				names.close();
				absi.close();
				ordo.close();
			} catch (Exception n) {
				n.printStackTrace();

			}
		} else if (choice == 0) {
			choi = choice;
			try {
				String n, x1, y1;
				BufferedReader names = new BufferedReader(new FileReader("C:/project_data/cities/cities.txt"));
				BufferedReader lat = new BufferedReader(new FileReader("C:/project_data/cities/latitude.txt"));
				BufferedReader lon = new BufferedReader(new FileReader("C:/project_data/cities/longitude.txt"));

				while (((n = names.readLine()) != null) && (((x1 = lat.readLine()) != null))
						&& ((y1 = lon.readLine()) != null)) {
					Ville v1 = new Ville(n, Double.parseDouble(x1), Double.parseDouble(y1));
					v.addElement(v1);
				}
				names.close();
				lat.close();
				lon.close();
			} catch (Exception n) {
				n.printStackTrace();

			}
		}
		if (!v.isEmpty()) {
			run.setEnabled(true);
			t_iteration.setEnabled(true);
			t_indiv.setEnabled(true);
			t_croi.setEnabled(true);
			t_mut.setEnabled(true);
			DefaultComboBoxModel<String> b = new DefaultComboBoxModel<String>();
			nbcroi = new String[v.size()];
			int c;
			for (int k = 0; k < v.size(); k++) {
				c = k + 1;
				nbcroi[k] = c + "";
				b.addElement(nbcroi[k]);
			}
			t_croi.setModel(b);
		} else {
			run.setEnabled(false);
			t_iteration.setEnabled(false);
			t_indiv.setEnabled(false);
			t_croi.setEnabled(false);
			t_mut.setEnabled(false);
		}

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

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == run) {
			res.setText("");
			int iteration = Integer.parseInt((String) t_iteration.getSelectedItem());
			int nbr_individus = Integer.parseInt((String) t_indiv.getSelectedItem());
			int croisement = Integer.parseInt((String) t_croi.getSelectedItem());
			double mutation = Double.parseDouble((String) (t_mut.getSelectedItem()));
			toString();
			// **************************************************************************
			Individu I = new Individu(v);
			Population P1 = new Population(I, nbr_individus, mutation);
			Vector<Ville> VV = new Vector<Ville>();
			CopyVectorVille(VV, v);
			Individu II = new Individu(VV);
			int i = 0;
			Individu best;
			int j;
			while (i < iteration) {

				P1.Croisement_Selection(croisement, II);

				P1.Mutation();
				best = P1.BestI;
				j = 0;
				String no = "";
				res.append("  Iteration " + i + " :     ");
				while (j < best.Chemin.size()) {

					res.append(best.Chemin.elementAt(j).nom + " | ");
					no = no + (best.Chemin.elementAt(j).nom + " __ ");
					j++;
				}
				end.addElement(no);

				res.append("       " + Double.toString(best.Fitness));
				res.append("\n");
				fitt.addElement(best.Fitness);
				iter.addElement(i);

				i++;
			}
			// ***********************************************************
			double bestf = fitt.elementAt(0);
			int it = 0;
			for (int g = 1; g < fitt.size(); g++) {
				if (bestf > fitt.elementAt(g)) {
					bestf = fitt.elementAt(g);
				}
			}
			for (int g = 0; g < iter.size(); g++) {
				if (bestf == fitt.elementAt(g)) {
					it = g;
					break;
				}
			}

			try {
				BufferedWriter trajet = new BufferedWriter(new FileWriter("C:/project_data/ag/path.txt"));
				BufferedWriter Fitt = new BufferedWriter(new FileWriter("C:/project_data/ag/fitt.txt"));
				BufferedWriter Iti = new BufferedWriter(new FileWriter("C:/project_data/ag/iitiration.txt"));
				if (!end.isEmpty()) {
					for (int i2 = 0; i2 < end.size(); i2++) {
						trajet.write(end.elementAt(i2));
						Fitt.write(Double.toString(fitt.elementAt(i2)));
						Iti.write(Integer.toString(iter.elementAt(i2)));
						trajet.newLine();
						Fitt.newLine();
						Iti.newLine();
					}
				}
				trajet.close();
				Fitt.close();
				Iti.close();
			} catch (Exception n) {
				n.printStackTrace();

			}

			XYSeries curve = new XYSeries("Curve");
			XYSeriesCollection dataset = new XYSeriesCollection();
			if (!iter.isEmpty()) {
				for (int x = 0; x < iter.size(); x++) {
					curve.add(iter.elementAt(x), fitt.elementAt(x));

				}
				dataset.addSeries(curve);
			} else {

				dataset.addSeries(curve);

			}
			JFreeChart xylineCharte = ChartFactory.createXYLineChart("Genetic Algorithm", "Iteration", "Best Fitness",
					dataset, PlotOrientation.VERTICAL, true, true, false);
			XYPlot xAxis = (XYPlot) xylineCharte.getPlot();

			TextTitle legendText = new TextTitle(
					"Best pathe is: ***" + end.elementAt(it) + "***   itiration: " + it + "     " + bestf);
			legendText.setPosition(RectangleEdge.BOTTOM);
			xylineCharte.addSubtitle(legendText);

			NumberAxis range = (NumberAxis) xAxis.getRangeAxis();
			range.setRange(0.0, curve.getMaxY() + 100);
			range.setTickUnit(new NumberTickUnit(100));

			chartPanel.setChart(xylineCharte);
			// ***********************************************************
			end.removeAllElements();
			fitt.removeAllElements();
			iter.removeAllElements();

		}
		if (e.getSource() == annu) {
			this.dispose();
		}

	}
	

	public static void main(String[] args) {
		Ville A = new Ville("A", 5, 6);
		Ville B = new Ville("B", 8, 4);
		Ville C = new Ville("C", 8, 4);
		Ville D = new Ville("D", 5, 6);
		Ville E = new Ville("E", 9, 2);
		Ville F = new Ville("F", 7, 4);
		Ville G = new Ville("G", 5, 1);
		Ville K = new Ville("K", 5, 7);
		Vector<Ville> v1 = new Vector<Ville>();
		v1.add(A);
		v1.add(B);
		v1.add(C);
		v1.add(D);
		v1.add(E);
		v1.add(F);
		v1.add(G);
		v1.add(K);
		AG g = new AG();
		g.setVisible(true);
		g.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
