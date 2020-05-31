package main_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Comparaison extends JFrame implements ActionListener {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	static Vector<Integer> agi = new Vector<Integer>();
	static Vector<Double> agf = new Vector<Double>();
	static Vector<Integer> psoi = new Vector<Integer>();
	static Vector<Double> psof = new Vector<Double>();
	static Vector<Integer> musi = new Vector<Integer>();
	static Vector<Double> musf = new Vector<Double>();
	static Vector<Integer> aci = new Vector<Integer>();
	static Vector<Double> acf = new Vector<Double>();
	// ************ChartPanels***************************************************
	ChartPanel AGchart;
	ChartPanel PSOchart;
	ChartPanel MUSchart;
	ChartPanel ACchart;
	JPanel all = new JPanel();
	// **************************************************************************

	// ************Buttons*******************************************************
	JButton OK = new JButton("     OK     ");

	JPanel panel_ok = new JPanel();

	// **************************************************************************

	public Comparaison() {
		// *******************AG************************************
		JFreeChart xylineChartAG = ChartFactory.createXYLineChart("Genetic Algorithm", "Iteration", "Best Fitness",
				null, PlotOrientation.VERTICAL, true, true, false);
		AGchart = new ChartPanel(xylineChartAG);
		// *******************PSO**********************************
		JFreeChart xylineChartPSO = ChartFactory.createXYLineChart("Particle Swarm Optimization", "Iteration",
				"Best Fitness", null, PlotOrientation.VERTICAL, true, true, false);
		PSOchart = new ChartPanel(xylineChartPSO);
		// *******************MUS**********************************
		JFreeChart xylineChartMUS = ChartFactory.createXYLineChart("Optimization Muslims Heuristic", "Iteration",
				"Best Fitness", null, PlotOrientation.VERTICAL, true, true, false);
		MUSchart = new ChartPanel(xylineChartMUS);
		// *******************AC************************************
		JFreeChart xylineChartAC = ChartFactory.createXYLineChart("Ant Colony Optimization", "Iteration",
				"Best Fitness", null, PlotOrientation.VERTICAL, true, true, false);
		ACchart = new ChartPanel(xylineChartAC);
		// *********************************************************
		AGchart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		PSOchart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MUSchart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ACchart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		all.setLayout(new GridLayout(0, 2));
		all.add(AGchart);
		all.add(PSOchart);
		all.add(MUSchart);
		all.add(ACchart);
		panel_ok.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		panel_ok.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_ok.add(OK);

		this.add(all, BorderLayout.CENTER);

		this.add(panel_ok, BorderLayout.SOUTH);
		this.setSize(1500, 800);
		OK.addActionListener(this);

	}

	public void Update() {

		agi.removeAllElements();
		agf.removeAllElements();
		psoi.removeAllElements();
		psof.removeAllElements();
		musi.removeAllElements();
		musf.removeAllElements();
		aci.removeAllElements();
		acf.removeAllElements();
		try {
			String i1, i2, i3, i4;
			String f1, f2, f3, f4;
			BufferedReader agit = new BufferedReader(new FileReader("C:/project_data/ag/iitiration.txt"));
			BufferedReader agfi = new BufferedReader(new FileReader("C:/project_data/ag/fitt.txt"));
			BufferedReader psoit = new BufferedReader(new FileReader("C:/project_data/pso/iitirationpso.txt"));
			BufferedReader psofi = new BufferedReader(new FileReader("C:/project_data/pso/fittpso.txt"));
			BufferedReader musit = new BufferedReader(new FileReader("C:/project_data/omh/iitirationmus.txt"));
			BufferedReader musfi = new BufferedReader(new FileReader("C:/project_data/omh/fittmus.txt"));
			BufferedReader acit = new BufferedReader(new FileReader("C:/project_data/ac/iitirationac.txt"));
			BufferedReader acfi = new BufferedReader(new FileReader("C:/project_data/ac/fittac.txt"));

			while (((i1 = agit.readLine()) != null) && (f1 = agfi.readLine()) != null) {
				agi.addElement(Integer.parseInt(i1));
				agf.addElement(Double.parseDouble(f1));

			}
			while (((i2 = psoit.readLine()) != null) && (f2 = psofi.readLine()) != null) {
				psoi.addElement(Integer.parseInt(i2));
				psof.addElement(Double.parseDouble(f2));
			}
			while (((i3 = musit.readLine()) != null) && (f3 = musfi.readLine()) != null) {
				musi.addElement(Integer.parseInt(i3));
				musf.addElement(Double.parseDouble(f3));
			}
			while (((i4 = acit.readLine()) != null) && (f4 = acfi.readLine()) != null) {
				aci.addElement(Integer.parseInt(i4));
				acf.addElement(Double.parseDouble(f4));
			}
			agit.close();
			agfi.close();
			psoit.close();
			psofi.close();
			musit.close();
			musfi.close();
			acit.close();
			acfi.close();
		} catch (Exception n) {
			n.printStackTrace();

		}

		// *******************AG************************************
		JFreeChart xylineChartAG = ChartFactory.createXYLineChart("Genetic Algorithm", "Iteration", "Best Fitness",
				createDatasetAg(), PlotOrientation.VERTICAL, true, true, false);
		AGchart.setChart(xylineChartAG);
		// *******************PSO**********************************
		JFreeChart xylineChartPSO = ChartFactory.createXYLineChart("Particle Swarm Optimization", "Iteration",
				"Best Fitness", createDatasetPso(), PlotOrientation.VERTICAL, true, true, false);
		PSOchart.setChart(xylineChartPSO);
		// *******************MUS**********************************
		JFreeChart xylineChartMUS = ChartFactory.createXYLineChart("Optimization Muslims Heuristic", "Iteration",
				"Best Fitness", createDatasetMus(), PlotOrientation.VERTICAL, true, true, false);
		MUSchart.setChart(xylineChartMUS);
		// *******************AC************************************
		JFreeChart xylineChartAC = ChartFactory.createXYLineChart("Ant Colony Optimization", "Iteration",
				"Best Fitness", createDatasetAc(), PlotOrientation.VERTICAL, true, true, false);
		ACchart.setChart(xylineChartAC);
		// *********************************************************

	}

	private XYDataset createDatasetAg() {
		XYSeries curve = new XYSeries("Curve");
		XYSeriesCollection dataset = new XYSeriesCollection();
		if (!agi.isEmpty()) {
			for (int i = 0; i < agi.size(); i++) {
				curve.add(agi.elementAt(i), agf.elementAt(i));

			}
			dataset.addSeries(curve);
		} else {

			dataset.addSeries(curve);

		}
		return dataset;
	}

	private XYDataset createDatasetPso() {
		XYSeries curve = new XYSeries("Curve");
		XYSeriesCollection dataset = new XYSeriesCollection();
		if (!psoi.isEmpty()) {
			for (int i = 0; i < psoi.size(); i++) {
				curve.add(psoi.elementAt(i), psof.elementAt(i));

			}
			dataset.addSeries(curve);
		} else {

			dataset.addSeries(curve);

		}
		return dataset;
	}

	private XYDataset createDatasetMus() {
		XYSeries curve = new XYSeries("Curve");
		XYSeriesCollection dataset = new XYSeriesCollection();
		if (!musi.isEmpty()) {
			for (int i = 0; i < musi.size(); i++) {
				curve.add(musi.elementAt(i), musf.elementAt(i));

			}
			dataset.addSeries(curve);
		} else {

			dataset.addSeries(curve);

		}
		return dataset;
	}

	private XYDataset createDatasetAc() {
		XYSeries curve = new XYSeries("Curve");
		XYSeriesCollection dataset = new XYSeriesCollection();
		if (!aci.isEmpty()) {
			for (int i = 0; i < aci.size(); i++) {
				curve.add(aci.elementAt(i), acf.elementAt(i));

			}
			dataset.addSeries(curve);
		} else {

			dataset.addSeries(curve);

		}
		return dataset;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == OK) {
			this.dispose();
		}

	}

	public static void main(String[] args) {
		Comparaison c = new Comparaison();
		c.setVisible(true);
		c.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
