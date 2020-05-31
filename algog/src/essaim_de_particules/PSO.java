package essaim_de_particules;

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

public class PSO extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static DefaultComboBoxModel<String> m = new DefaultComboBoxModel<String>();
	JTabbedPane work = new JTabbedPane();
	static Vector<Integer> iter = new Vector<Integer>();
	static Vector<Double> fitt = new Vector<Double>();
	static Vector<Ville> v = new Vector<Ville>();
	static int choi;
	static Vector<String> fin = new Vector<String>();
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Labele principal ArticleSwarm***********
	ImageIcon i = new ImageIcon("C:/project_data/images/image1.jpg");
	JLabel main_l = new JLabel();
	// ***************************

	// Precedent_annuler**********

	JButton annu = new JButton("Cancel");
	JPanel prec_annu = new JPanel();// FlowLayout
	// ***************************

	// les parametres*************
	String[] nbite;
	String[] nbind;
	static String[] nbvitt;
	String[] nbinert = { "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9" };
	static int nbvi = 7;

	JLabel nb_itiration = new JLabel("Number of iterations :");
	JComboBox<String> t_iteration;
	JLabel nb_individu = new JLabel("Swarm size :");
	JComboBox<String> t_indiv;
	JLabel nb_vitesse = new JLabel("Initial speed :");
	static JComboBox<String> t_vitesse;
	JLabel nb_inertie = new JLabel("Inertia coefficient:");
	JComboBox<String> t_inertie;
	static JPanel parametre = new JPanel();// GridLayout
	// ****************************

	// run_stop********************

	JButton run = new JButton("                                    Run                                    ");
	JLabel bes = new JLabel("");
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

	public PSO() {

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

		nbvitt = new String[100];
		for (int k = 0; k < 100; k++) {
			c = k + 1;
			nbvitt[k] = c + "";
			m.addElement(nbvitt[k]);
		}

		t_iteration = new JComboBox<String>(nbite);
		t_indiv = new JComboBox<String>(nbind);
		t_vitesse = new JComboBox<String>(m);
		nb_itiration.setBorder(BorderFactory.createEtchedBorder());
		nb_itiration.setBorder(BorderFactory.createLineBorder(Color.black));
		nb_individu.setBorder(BorderFactory.createLineBorder(Color.black));
		nb_vitesse.setBorder(BorderFactory.createLineBorder(Color.black));
		nb_inertie.setBorder(BorderFactory.createLineBorder(Color.black));
		t_inertie = new JComboBox<String>(nbinert);
		t_iteration.setEnabled(false);
		t_indiv.setEnabled(false);
		t_vitesse.setEnabled(false);
		t_inertie.setEnabled(false);
		run.setEnabled(false);
		parametre.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Settings"));
		parametre.setLayout(new GridLayout(0, 2));
		parametre.add(nb_itiration);
		parametre.add(t_iteration);
		parametre.add(nb_individu);
		parametre.add(t_indiv);
		parametre.add(nb_vitesse);
		parametre.add(t_vitesse);
		parametre.add(nb_inertie);
		parametre.add(t_inertie);
		parametre.setPreferredSize(new Dimension(50, 150));

		//

		// run_stop
		run_stop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Operations")));
		run_stop.setLayout(new BorderLayout());
		run_stop.add(run, BorderLayout.NORTH);
		run_stop.add(bes, BorderLayout.CENTER);

		//

		// par_runstop

		par_run_stop.setLayout(new BorderLayout());
		par_run_stop.add(parametre, BorderLayout.NORTH);
		par_run_stop.add(run_stop, BorderLayout.CENTER);

		//

		// result_graph

		result_graph.setLayout(new GridLayout(0, 1));
		result_graph.add(resultat);
		JFreeChart xylineChart = ChartFactory.createXYLineChart("Particle Swarm Optimization", "Iteration",
				"Best Fitness", null, PlotOrientation.VERTICAL, true, true, false);
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

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == run) {
			res.setText("");
			int iteration = Integer.parseInt((String) t_iteration.getSelectedItem());
			int swarm_size = Integer.parseInt((String) t_indiv.getSelectedItem());
			int vitesse = Integer.parseInt((String) t_vitesse.getSelectedItem());
			double inertie = Double.parseDouble((String) (t_inertie.getSelectedItem()));
			toString();
			Particule P = new Particule(v, inertie, vitesse);
			Essaim Es = new Essaim(P, swarm_size, vitesse);

			for (int i1 = 0; i1 < iteration; i1++) {
				int j = 0;

				Es.MAJ_Positions();
				String no = "";
				res.append("  Iteration " + i1 + " :     ");
				while (j < Es.Particules.elementAt(Es.Best()).Chemin.size()) {
					res.append(Es.Particules.elementAt(Es.Best()).Chemin.elementAt(j).nom + " |");
					no = no + (Es.Particules.elementAt(Es.Best()).Chemin.elementAt(j).nom + " __ ");
					j++;
				}
				fin.addElement(no);
				res.append("        " + Es.Particules.elementAt(Es.Best()).Fitness);
				res.append("\n");
				fitt.addElement(Es.Particules.elementAt(Es.Best()).Fitness);
				iter.addElement(i1);

			}
			Double bestf = null;
			int it = 0;
			String no = "";

			Es.PgdBest = Es.WhoIsTheBest();
			bestf = Es.Particules.elementAt(Es.IndexMeilleurSolution).Fitness;
			it = fitt.indexOf(bestf);
			for (int l = 0; l < Es.Particules.elementAt(Es.IndexMeilleurSolution).Chemin.size(); l++) {
				no = no + (Es.Particules.elementAt(Es.IndexMeilleurSolution).Chemin.elementAt(l).nom + "__");
			}
			try {
				BufferedWriter trajet = new BufferedWriter(new FileWriter("C:/project_data/pso/pathpso.txt"));
				BufferedWriter Fitt = new BufferedWriter(new FileWriter("C:/project_data/pso/fittpso.txt"));
				BufferedWriter Iti = new BufferedWriter(new FileWriter("C:/project_data/pso/iitirationpso.txt"));
				if (!fin.isEmpty()) {
					for (int i2 = 0; i2 < fin.size(); i2++) {
						trajet.write(fin.elementAt(i2));
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
			// ***********************************************************
			XYSeries curve = new XYSeries("Curve");
			XYSeriesCollection dataset = new XYSeriesCollection();
			if (!iter.isEmpty()) {
				for (int i = 0; i < iter.size(); i++) {
					curve.add(iter.elementAt(i), fitt.elementAt(i));

				}
				dataset.addSeries(curve);
			} else {

				dataset.addSeries(curve);

			}
			JFreeChart xylineCharte = ChartFactory.createXYLineChart("Particle Swarm Optimization", "Iteration",
					"Best Fitness", dataset, PlotOrientation.VERTICAL, true, true, false);
			XYPlot xAxis = (XYPlot) xylineCharte.getPlot();

			TextTitle legendText = new TextTitle(
					"Best pathe is: ***" + no + "***   itiration: " + it + "     " + bestf);
			legendText.setPosition(RectangleEdge.BOTTOM);
			NumberAxis range = (NumberAxis) xAxis.getRangeAxis();
			range.setRange(curve.getMinY() - 50, curve.getMaxY() + 20);
			range.setTickUnit(new NumberTickUnit(50));
			xylineCharte.addSubtitle(legendText);
			chartPanel.setChart(xylineCharte);
			// ***********************************************************

			fitt.removeAllElements();
			iter.removeAllElements();
			fin.removeAllElements();
		}
		if (e.getSource() == annu) {
			this.dispose();
		}

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
			t_vitesse.setEnabled(true);
			t_inertie.setEnabled(true);

		} else {
			run.setEnabled(false);
			t_iteration.setEnabled(false);
			t_indiv.setEnabled(false);
			t_vitesse.setEnabled(false);
			t_inertie.setEnabled(false);
		}

	}

}
