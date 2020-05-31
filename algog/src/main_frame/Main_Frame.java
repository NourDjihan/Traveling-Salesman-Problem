package main_frame;

import algorithme_genetique.AG;
import brotherhood_in_Allah.OMH;
import essaim_de_particules.PSO;
import commun.Ville;
import commun.DoubleJTextField;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.GridLayout;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.table.DefaultTableModel;

import colonie_de_fourmis.AC;

public class Main_Frame {

	private JFrame frame;
	Font f = new Font("SansSerif", Font.BOLD, 20);
	Font f1 = new Font("Arial", Font.BOLD, 15);
	Font f2 = new Font("Calibri", Font.ROMAN_BASELINE, 15);
	static Vector<String> writer = new Vector<String>();
	static Vector<String> nom = new Vector<String>();
	static Vector<String> coordo = new Vector<String>();
	static Vector<String> absice = new Vector<String>();
	static Vector<String> ordon = new Vector<String>();
	static Vector<Ville> v = new Vector<Ville>();
	static int choi = 0;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private JFrame Cities;
	// Labele principal*************************************
	ImageIcon is = new ImageIcon("images/cities.jpg");
	JLabel main_ls = new JLabel();// tab_cities.NORTH
	// *****************************************************

	// Button ok********************************************
	JPanel pok = new JPanel();
	static JButton ok = new JButton("      Apply      ");// tab_cities.SOUTH
	// *****************************************************

	// la table*********************************************
	String[] colums = { "Name", "Abscissa/Latitude : � ", "Ordinate/Longitude : � " };
	Object[][] data = new Object[][] {};
	JScrollPane stab = new JScrollPane();
	// this.CENTER
	JTable table = new JTable();
	// *****************************************************

	// les coordon�s****************************************
	JPanel coord = new JPanel();
	DoubleJTextField x = new DoubleJTextField();
	DoubleJTextField y = new DoubleJTextField();
	// *****************************************************

	// parametre********************************************
	JPanel param = new JPanel();
	JTextField name = new JTextField();// +++les coordon�s
	// *****************************************************

	// add remove*******************************************
	JPanel add_remove = new JPanel();
	JPanel Choice = new JPanel();
	JPanel Rbuttons = new JPanel();
	ButtonGroup allb = new ButtonGroup();
	JButton Apply = new JButton("         Apply         ");
	JRadioButton geo = new JRadioButton("Geographical coordinates");
	JRadioButton Carti = new JRadioButton("Cartesian coordinates");
	JPanel add_remove_choice = new JPanel();
	ImageIcon plus = new ImageIcon("Images/plus.png");
	ImageIcon moin = new ImageIcon("Images/moin.png");
	JButton add = new JButton();
	JButton remove = new JButton();
	// *****************************************************

	// city***********
	JPanel city = new JPanel();// tab_cities.WEST
	// param+add_remove
	// ****************

	// *****************
	JPanel tab_cities = new JPanel();////////

	// *****************

	AG g = new AG();
	PSO p = new PSO();
	OMH muslim = new OMH();
	AC Antc = new AC();
	Comparaison c = new Comparaison();

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Frame window = new Main_Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_Frame() {
		initialize();
		UpdateCities();
	}

	void UpdateCities() {
		// ****
		writer.removeAllElements();
		absice.removeAllElements();
		ordon.removeAllElements();
		nom.removeAllElements();
		coordo.removeAllElements();
		v.removeAllElements();
		name.setText("");
		x.setText("");
		y.setText("");
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (choi == 0) {
			x.setBorder(BorderFactory.createTitledBorder("Latitude "));
			y.setBorder(BorderFactory.createTitledBorder("Longitude "));

			try {
				String n, x1, y1;
				BufferedReader names = new BufferedReader(new FileReader("cities/cities.txt"));
				BufferedReader lat = new BufferedReader(new FileReader("cities/latitude.txt"));
				BufferedReader lon = new BufferedReader(new FileReader("cities/longitude.txt"));

				while (((n = names.readLine()) != null) && (((x1 = lat.readLine()) != null))
						&& ((y1 = lon.readLine()) != null)) {
					Ville v1 = new Ville(n, Double.parseDouble(x1), Double.parseDouble(y1));
					v.addElement(v1);
					writer.addElement(n);
					absice.addElement(x1);
					ordon.addElement(y1);
				}
				names.close();
				lat.close();
				lon.close();
			} catch (Exception n) {
				n.printStackTrace();

			}
		} else if (choi == 1) {
			x.setBorder(BorderFactory.createTitledBorder("Abscissa"));
			y.setBorder(BorderFactory.createTitledBorder("Ordinate"));

			try {
				String n, x1, y1;
				BufferedReader names = new BufferedReader(new FileReader("cities/citiesC.txt"));
				BufferedReader absi = new BufferedReader(new FileReader("cities/x.txt"));
				BufferedReader ordo = new BufferedReader(new FileReader("cities/y.txt"));

				while (((n = names.readLine()) != null) && (((x1 = absi.readLine()) != null))
						&& ((y1 = ordo.readLine()) != null)) {
					Ville v1 = new Ville(n, Double.parseDouble(x1), Double.parseDouble(y1));
					v.addElement(v1);
					writer.addElement(n);
					absice.addElement(x1);
					ordon.addElement(y1);
				}
				names.close();
				absi.close();
				ordo.close();
			} catch (Exception n) {
				n.printStackTrace();

			}
		}
		// ****

		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
			}
		}
		if (!v.isEmpty()) {

			for (int i = 0; i < v.size(); i++) {
				nom.addElement(v.elementAt(i).nom.toLowerCase());
				coordo.addElement(v.elementAt(i).x + " " + v.elementAt(i).y);
				Object[] o = { v.elementAt(i).nom.toLowerCase(), v.elementAt(i).x, v.elementAt(i).y };
				model.addRow(o);
			}
		} else {
			String msgm = "OPS !!There is no cities !! You have to enter the list of cities ";
			JOptionPane.showMessageDialog(null, msgm);

		}

	}

	boolean testentrer(String nam, String x, String y) {

		boolean b = true;

		if (!nom.isEmpty()) {

			if (nom.contains(nam.toLowerCase())) {
				b = false;
			} else {
				if (coordo.contains(x + ".0" + " " + y + ".0") || (coordo.contains(x + " " + y))) {
					b = false;
				} else {
					b = true;
				}
			}

		} else {
			b = true;
		}
		return b;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Cities = new JFrame();

		// ********************************************************************************************************************************************************

		// Main_l+button ok**********************************
		main_ls.setIcon(is);
		pok.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 10));
		pok.add(ok);

		x.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {

			}

			public void focusLost(FocusEvent arg0) {
				if (!x.getText().isEmpty()) {
					if (choi == 0) {

						if (Double.parseDouble(x.getText()) < -90 || (Double.parseDouble(x.getText()) > 90)) {
							String g1 = "you must enter a valid Latitude [-90<=Latitude<=90]";
							JOptionPane.showMessageDialog(null, g1);
							x.grabFocus();
							x.selectAll();
						}
					}
				}

			}

		});
		y.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {

			}

			public void focusLost(FocusEvent arg0) {
				if (!y.getText().isEmpty()) {
					if (choi == 0) {
						if (Double.parseDouble(y.getText()) < -180 || (Double.parseDouble(y.getText()) > 180)) {

							String g = "you must enter a valid Longitude [-180<=Longitude<=180]";
							JOptionPane.showMessageDialog(null, g);
							y.grabFocus();
							y.selectAll();
						}
					}
				}
			}

		});
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// ***
				try {
					BufferedWriter choice = new BufferedWriter(new FileWriter("choi.txt"));

					choice.write(Integer.toString(choi));
					choice.close();

				} catch (Exception n) {
					n.printStackTrace();

				}
				if (choi == 0) {
					try {
						BufferedWriter fichier = new BufferedWriter(
								new FileWriter("cities/cities.txt"));
						BufferedWriter lat = new BufferedWriter(new FileWriter("cities/latitude.txt"));
						BufferedWriter lon = new BufferedWriter(new FileWriter("cities/longitude.txt"));
						if (!writer.isEmpty()) {
							for (int j = 0; j < writer.size(); j++) {
								fichier.write(writer.elementAt(j));
								fichier.newLine();
								lat.write(absice.elementAt(j));
								lat.newLine();
								lon.write(ordon.elementAt(j));
								lon.newLine();
							}
						}
						fichier.close();
						lat.close();
						lon.close();
					} catch (Exception n) {
						n.printStackTrace();

					}
				} else if (choi == 1) {
					try {
						BufferedWriter fichier = new BufferedWriter(
								new FileWriter("cities/citiesC.txt"));
						BufferedWriter absi = new BufferedWriter(new FileWriter("cities/x.txt"));
						BufferedWriter ordo = new BufferedWriter(new FileWriter("a/cities/y.txt"));
						if (!writer.isEmpty()) {
							for (int j = 0; j < writer.size(); j++) {
								fichier.write(writer.elementAt(j));
								fichier.newLine();
								absi.write(absice.elementAt(j));
								absi.newLine();
								ordo.write(ordon.elementAt(j));
								ordo.newLine();
							}
						}
						fichier.close();
						absi.close();
						ordo.close();
					} catch (Exception n) {
						n.printStackTrace();

					}
				}
				// ****

				Cities.dispose();

			}

		});
		// ****************************************

		// add remove*********************************
		add_remove.setLayout(new GridLayout(0, 1));
		add_remove.add(add);
		add_remove.add(remove);
		add_remove.setBorder(BorderFactory.createEtchedBorder());
		add_remove_choice.setLayout(new GridLayout(0, 1));
		add_remove_choice.add(add_remove);
		Rbuttons.setLayout(new GridLayout(0, 1));
		Rbuttons.setBorder(BorderFactory.createEtchedBorder());
		geo.setSelected(true);

		allb.add(geo);
		allb.add(Carti);

		Rbuttons.add(geo);
		Rbuttons.add(Carti);
		Choice.setLayout(new BorderLayout());
		Choice.add(Rbuttons, BorderLayout.CENTER);
		Choice.add(Apply, BorderLayout.SOUTH);
		add_remove_choice.add(Choice);
		add.setBorder(BorderFactory.createEmptyBorder());
		remove.setBorder(BorderFactory.createEmptyBorder());
		add.setIcon(plus);
		Apply.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (geo.isSelected()) {
					choi = 0;

				} else if (Carti.isSelected()) {
					choi = 1;
				}

				UpdateCities();

			}

		});
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (!v.isEmpty()) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					v.removeElementAt(table.getSelectedRow());
					writer.removeElementAt(table.getSelectedRow());
					absice.removeElementAt(table.getSelectedRow());
					ordon.removeElementAt(table.getSelectedRow());
					model.removeRow(table.getSelectedRow());
				}

			}

		});
		// *********************************************************************************************************
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(name.getText().isEmpty()) && !(x.getText().isEmpty()) && !(y.getText().isEmpty())) {

					if (testentrer(name.getText(), x.getText(), y.getText())) {

						writer.addElement(name.getText());
						absice.addElement(x.getText());
						ordon.addElement(y.getText());
						Ville w = new Ville(name.getText(), Double.parseDouble(x.getText()),
								Double.parseDouble(y.getText()));
						v.addElement(w);
						nom.addElement(w.nom.toLowerCase());

						coordo.addElement(x.getText() + " " + y.getText());
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						Object[] o = { name.getText().toLowerCase(), x.getText(), y.getText() };
						model.addRow(o);

						name.setText("");
						x.setText("");
						y.setText("");
					} else {
						String ms = "There is another city that has the same name or coordinates !!!";

						JOptionPane.showMessageDialog(null, ms);

					}
				} else {
					System.out.println("");
				}
			}
		});
		;
		// *******************************************************************************************************************
		remove.setIcon(moin);

		// ****************************************

		// les coordon�s**************************
		coord.setLayout(new GridLayout(0, 2));
		x.setBorder(BorderFactory.createTitledBorder("Latitude "));
		y.setBorder(BorderFactory.createTitledBorder("Longitude "));
		x.setFont(f);
		y.setFont(f);
		coord.add(x);
		coord.add(y);
		// ***************************************

		// parametre*****************************
		param.setLayout(new GridLayout(0, 1));
		name.setBorder(BorderFactory.createTitledBorder("Name"));
		param.setBorder((BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "City"))));
		name.setFont(f);
		param.add(name);
		param.add(coord);
		// **************************************

		// city**********************************
		city.setLayout(new BorderLayout());
		city.add(param, BorderLayout.NORTH);
		city.add(add_remove_choice, BorderLayout.CENTER);
		// ***************************************

		// la table*******************************
		DefaultTableModel model = new DefaultTableModel() {

			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

		};

		model.addColumn("Name");
		model.addColumn("Abscissa/Latitude : � ");
		model.addColumn("Ordinate/Longitude : � ");

		table.setModel(model);
		table.setFont(f1);
		table.setRowHeight(24);
		table.setBackground(Color.orange);
		stab = new JScrollPane(table);
		stab.getViewport().setBackground(Color.WHITE);
		// ***************************************
		tab_cities.setLayout(new BorderLayout());
		Cities.add(main_ls, BorderLayout.NORTH);
		Cities.add(city, BorderLayout.WEST);
		Cities.add(pok, BorderLayout.SOUTH);
		Cities.add(stab, BorderLayout.CENTER);

		Cities.setResizable(false);

		Cities.setLocation(500, 300);

		// ********************************************************************************************************************************************************

		frame.setBounds(100, 100, 850, 780);
		frame.setResizable(false);
		frame.setLocation(400, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("images/123.jpg"));
		frame.getContentPane().add(label, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		panel_1.setBorder(BorderFactory.createLineBorder(Color.black));
		JButton Exit = new JButton("       Exit       ");
		Exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();

			}

		});
		panel_1.add(Exit);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_4.setBackground(new Color(255, 255, 255));
		panel_4.setBorder(BorderFactory.createLineBorder(Color.black));
;

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setHgap(10);

		panel_2.setLayout(new GridLayout(0, 1));
		panel_2.add(panel_3);
		panel_2.add(panel_4);
		panel.add(panel_2, BorderLayout.NORTH);

		JButton b1 = new JButton("");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				g.Update(choi);
				g.setVisible(true);
			}
		});

		b1.setIcon(new ImageIcon("images/b1.jpg"));
		panel_3.add(b1);

		JButton b2 = new JButton("");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				p.Update(choi);
				p.setVisible(true);
			}
		});

		b2.setIcon(new ImageIcon("images/b2.jpg"));
		panel_3.add(b2);

		JButton b3 = new JButton("");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				muslim.Update(choi);
				muslim.setVisible(true);
			}
		});
		b3.setIcon(new ImageIcon("images/b3.jpg"));
		panel_3.add(b3);

		JButton b4 = new JButton("");
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Antc.Update(choi);
				Antc.setVisible(true);
			}
		});
		b4.setIcon(new ImageIcon("images/b4.jpg"));
		panel_3.add(b4);
		JButton Compare = new JButton();
		Compare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.Update();
				c.setVisible(true);
			}
		});
		Compare.setIcon(new ImageIcon("images/compare.jpg"));
		panel_4.add(Compare);

		JButton btnSaisi = new JButton();
		btnSaisi.setIcon(new ImageIcon("images/city1.jpg"));
		btnSaisi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Cities.setVisible(true);
				Cities.setSize(1000, 500);
			}
		});
		panel_4.add(btnSaisi);
	}

}
