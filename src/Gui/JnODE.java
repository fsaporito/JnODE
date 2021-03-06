package Gui;


import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;

import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import Parser.MathParser;

import odeSolver.AdamsBashforth;
import odeSolver.AdamsMoulton;
import odeSolver.BDF;
import odeSolver.DifferentialEquation;
import odeSolver.OdeSolver;


public class JnODE implements ActionListener {

	private JFrame frame; // General Frame

	private JPanel panelFrame; // General Panel Frame
	private JPanel panelConf;
	private JPanel panelInput;
	private JPanel panelMethodSelection;
	private JPanel panelOutput; // Panel Thath Contains The Output Panels, Either Values Or Plot
	private JPanel panelPlot; // Panel That Contains The Plot
	private JPanel panelValues; // Panel That Contains The Computed Values
	private JPanel panelErrors;

	private JLabel labelInput;
	private JLabel labelStep;
	private JLabel labely0;
	private JLabel labelT0;
	private JLabel labelTmax;
	private JLabel labelS;
	private JLabel labelError;

	private JTextField textFieldInput;
	private JTextField textFieldStep;
	private JTextField textFieldy0;
	private JTextField textFieldT0;
	private JTextField textFieldTmax;
	private JTextField textFieldS;
	private JTextField textFieldExact;

	private JButton compButton;
	private JButton clearButton;
	private JRadioButton plotButton;
	private JRadioButton valuesButton;
	private ButtonGroup outputGroup;
	private JCheckBox hasExactButton;

	private JSpinner Spinner;
	private SpinnerModel Smodel;

	private JComboBox<String> methodBox;

	private JFreeChart chartPlot;
	private ChartPanel panelChart;
	private XYSeries approximatedXY;
	private XYSeries exactXY;
	private JTextPane textPaneYk;
	private JTextPane textPaneExact;

	private int heightExact = 130;
	private int widthExact = 700; // 430
	private int heightPlot = 450;
	private int widthPlot = 700;
	private int heightPerLine = 17;

	private String title = "JnODE";

	private double[] yk;

	private boolean isSolved = false;

	private String[] methodList = {"EulerExplicit",
								   "EulerImplicit",
								   "MidPointExplicit",
								   "MidPointImplicit",
								   "Ralston",
								   "Heun",
								   "Kutta3",
								   "RK4",
								   "AdamsBashforth",
								   "AdamsMoulton",
								   "BFD"
									};


	private boolean DEFAULT_VALUES = true;
	private String formatDouble = "%1$,.10f"; // Used To Limit The Output Precision


	/**
	 * Create the application.
	 */
	public JnODE() {

		this.initialize();

		this.buildGui();

		this.defaultValues();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Frame Initialization
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, widthExact, heightExact);
		this.frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle(this.title);

		// Panels Initialization
		this.panelFrame = new JPanel();
		this.panelConf = new JPanel();
		this.panelInput = new JPanel();
		this.panelMethodSelection = new JPanel();
		this.panelOutput = new JPanel();
		this.panelPlot = new JPanel();
		this.panelValues = new JPanel();
		this.panelErrors = new JPanel();

		// Labels Initialization
		this.labelInput = new JLabel ("y' = ");
		this.labelStep = new JLabel ("step");
		this.labely0 = new JLabel ("y0");
		this.labelT0 = new JLabel ("t0");
		this.labelTmax = new JLabel ("tmax");
		this.labelS = new JLabel ("s");
		this.labelError = new JLabel ("Press Compute To Calculate Numerically The Solution");

		// TextFields Initialization
		this.textFieldInput = new JTextField (20);
		this.textFieldStep = new JTextField (4);
		this.textFieldy0 = new JTextField (4);
		this.textFieldT0 = new JTextField (4);
		this.textFieldTmax = new JTextField (4);
		this.textFieldS = new JTextField (4);
		this.textFieldExact = new JTextField (10);

		// Text And Scroll Pane Initialization
		this.textPaneYk = new JTextPane();
		this.textPaneYk.setText("");
		this.textPaneYk.setEditable(false);
		this.textPaneExact = new JTextPane();
		this.textPaneExact.setText("");
		this.textPaneExact.setEditable(false);

		// Buttons Initialization
		this.compButton = new JButton ("Compute");
		this.clearButton = new JButton ("Clear");

		// RadioButtons Initialization
		this.plotButton = new JRadioButton ("Plot");
		this.plotButton.setSelected(true);
		this.valuesButton = new JRadioButton ("Values");
		this.valuesButton.setSelected(false);
		this.outputGroup = new ButtonGroup ();
		this.outputGroup.add(this.plotButton);
		this.outputGroup.add(this.valuesButton);

		// Spinner
		this.Spinner = new JSpinner();

		// CheckBox Initialization
		this.hasExactButton = new JCheckBox ("Exact");

		// ComboBox Initialization
		this.methodBox = new JComboBox<String>();
		for (int i = 0; i < this.methodList.length; i++) {

			this.methodBox.addItem(this.methodList[i]);

		}

	}


	/**
	 * Build The Gui
	 */
	private void buildGui() {

		// Main Panel
		this.frame.add (this.panelFrame);
		this.panelFrame.setLayout(new BorderLayout());
		this.panelFrame.add(this.panelConf, BorderLayout.NORTH);
		this.panelFrame.add(this.panelOutput, BorderLayout.CENTER);
		this.panelFrame.add(this.panelErrors, BorderLayout.SOUTH);

		// Configuration Panel
		this.panelConf.setLayout(new GridLayout(2, 1, 1, 1));
		this.panelConf.add(this.panelInput);
		this.panelConf.add(this.panelMethodSelection);

		// Input Panel
		this.panelInput.add(this.labelInput);
		this.panelInput.add(this.textFieldInput);

		// Parameter Panel
		this.panelInput.add(this.labelT0);
		this.panelInput.add(this.textFieldT0);
		this.panelInput.add(this.labely0);
		this.panelInput.add(this.textFieldy0);
		this.panelInput.add(this.labelTmax);
		this.panelInput.add(this.textFieldTmax);
		this.panelInput.add(this.labelStep);
		this.panelInput.add(this.textFieldStep);

		// Method Selection Panel
		this.panelMethodSelection.add(this.methodBox);
		this.panelMethodSelection.add(this.plotButton);
		this.panelMethodSelection.add(this.valuesButton);
		this.panelMethodSelection.add(this.hasExactButton);
		this.panelMethodSelection.add(this.textFieldExact);
		this.textFieldExact.setEnabled(false);
		this.panelMethodSelection.add(this.clearButton);
		this.panelMethodSelection.add(this.compButton);


		// Plot Panel
		this.panelOutput.setLayout(new BorderLayout ());
		this.panelValues.setLayout (new GridLayout (1, 1, 1, 1));
		this.panelOutput.add(this.panelValues, BorderLayout.CENTER);
		this.panelOutput.setVisible(false);

		// Errors Panel
		this.panelErrors.add(this.labelError);


		// Action Listeners
		this.methodBox.addActionListener(this);
		this.compButton.addActionListener(this);
		this.clearButton.addActionListener(this);
		this.hasExactButton.addActionListener(this);
		this.plotButton.addActionListener(this);
		this.valuesButton.addActionListener(this);

	}


	/**
	 *
	 */
	public void defaultValues () {

		if (DEFAULT_VALUES) {

			//this.textFieldInput.setText("y");
			this.textFieldInput.setText("-15*y");
			this.textFieldStep.setText("0.1");
			this.textFieldy0.setText("1");
			this.textFieldT0.setText("0");
			this.textFieldTmax.setText("1");
			this.textFieldS.setText("2");

			// Exact
			this.hasExactButton.doClick();
			this.textFieldExact.setText("e^(-15*t)");

			// Method Choice
			this.methodBox.setSelectedItem("RK4");
			
			// Compute
			this.compButton.doClick();

		}


	}


	/**
	 * Action Performed Method
	 * Implement the button's listeners
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getActionCommand().equals(this.hasExactButton.getText())) {

			if (this.hasExactButton.isSelected()) { // Exact Solution Present

				this.textFieldExact.setEnabled(true);


			} else { // No Exact Solution

				this.textFieldExact.setEnabled(false);

			}

			this.panelMethodSelection.revalidate();

			this.frame.repaint();

		}

		if (arg0.getActionCommand().equals("comboBoxChanged")) {

			if (this.methodBox.getSelectedItem().equals ("AdamsBashforth")
				| this.methodBox.getSelectedItem().equals ("AdamsMoulton")
				| this.methodBox.getSelectedItem().equals ("BFD")) {


				if (this.methodBox.getSelectedItem().equals ("AdamsBashforth")) {

					// new SpinnerNumberModel (default, min, max, step);
					this.Smodel = new SpinnerNumberModel (1, 1, 5, 1);

				}

				if (this.methodBox.getSelectedItem().equals ("AdamsMoulton")) {

					// new SpinnerNumberModel (default, min, max, step);
					this.Smodel = new SpinnerNumberModel (0, 0, 4, 1);

				}

				if (this.methodBox.getSelectedItem().equals ("BFD")) {

					// new SpinnerNumberModel (default, min, max, step);
					this.Smodel = new SpinnerNumberModel (1, 1, 6, 1);

				}

				this.panelInput.add(this.labelS);
				this.Spinner.setModel(this.Smodel);
				this.panelInput.add(this.Spinner);

				this.panelInput.revalidate();

				this.frame.repaint();

			} else {

				this.panelInput.remove(this.labelS);
				this.panelInput.remove(this.Spinner);

				this.panelInput.revalidate();
				this.frame.repaint();

			}

		}

		if (arg0.getActionCommand().equals(this.clearButton.getText())) {

			this.textFieldInput.setText("");
			this.textFieldStep.setText("");
			this.textFieldy0.setText("");
			this.textFieldT0.setText("");
			this.textFieldTmax.setText("");
			this.textFieldExact.setText("");
			this.textFieldS.setText("");

			this.labelError.setText("Press Compute To Calculate Numerically The Solution");
			this.labelError.setForeground(Color.black);

			this.textPaneYk.setText("");
			this.textPaneExact.setText("");
			this.panelValues.setLayout(new GridLayout (1, 1, 1, 1));
			this.panelOutput.removeAll();
			this.panelOutput.add(this.panelValues, BorderLayout.CENTER);
			this.panelOutput.setVisible(false);

			this.methodBox.setSelectedIndex(0);

			if (this.hasExactButton.isSelected()) { // Exact Solution Present

				this.hasExactButton.setSelected(false);

				this.textFieldExact.setEnabled(false);

			}

			if (this.plotButton.isSelected()) {

				this.plotButton.setSelected(false);
				this.valuesButton.setSelected(true);

			}

			this.frame.setBounds(100, 100, widthExact, heightExact);

			this.isSolved = false;


			this.panelInput.revalidate();
			this.panelValues.revalidate();
			this.panelPlot.revalidate();
			this.panelOutput.revalidate();
			this.panelErrors.revalidate();
			this.frame.repaint();

		}

		if (arg0.getActionCommand().equals(this.compButton.getText())) {

			String errorString = "";
			String function = "";
			MathExpr exprFun = null;
			String exact = "";
			MathExpr exprExact = null;
			double step = 0.0;
			double t0 = 0.0;
			double y0 = 0.0;
			double tmax = 0.0;
			int s = 0;
			MathTokenSymbol t = new MathTokenSymbol ("t");
			MathTokenSymbol y = new MathTokenSymbol ("y");
			DifferentialEquation diff = null;

			this.labelError.setForeground (Color.black);

			try {

				this.labelError.setText("Checking input ...");

				function = this.textFieldInput.getText();
				exprFun = (new MathParser (function, "infix")).getMathExpr();

				step = (new MathParser (this.textFieldStep.getText(), "infix")).getMathExpr().getOperandDouble();
				t0 = (new MathParser (this.textFieldT0.getText(), "infix")).getMathExpr().getOperandDouble();
				y0 = (new MathParser (this.textFieldy0.getText(), "infix")).getMathExpr().getOperandDouble();
				tmax = (new MathParser (this.textFieldTmax.getText(), "infix")).getMathExpr().getOperandDouble();

				if (this.methodBox.getSelectedItem().equals("AdamsBashforth")) {

					s = ((SpinnerNumberModel) this.Spinner.getModel()).getNumber().intValue();

					if (s < 1 || s > 5) {

						throw new WrongInputException ("s Must Be From 1 To 5 With AdamsBashforth!!!");

					}

				}

				if (this.methodBox.getSelectedItem().equals("AdamsMoulton")) {

					s = ((SpinnerNumberModel) this.Spinner.getModel()).getNumber().intValue();

					if (s < 0 || s > 4) {

						throw new WrongInputException ("s Must Be From 0 To 4 With AdamsMoulton!!!");

					}

				}

				if (this.methodBox.getSelectedItem().equals("BFD")) {

					s = ((SpinnerNumberModel) this.Spinner.getModel()).getNumber().intValue();

					if (s < 1 || s > 7) {

						throw new WrongInputException ("s Must Be From 1 To 6 With BFD!!!");

					}

				}

				if (this.hasExactButton.isSelected()) { // Exact Solution

					exact = this.textFieldExact.getText();
					exprExact = (new MathParser (exact, "infix")).getMathExpr();

					diff = new DifferentialEquation (exprExact, exprFun, t0, y0, step, tmax, t, y);

				} else {

					diff = new DifferentialEquation (exprFun, t0, y0, step, tmax, t, y);

				}

			} catch (NullPointerException | NumberFormatException | WrongInputException | WrongExpressionException | WrongCalculationException e) {

				errorString = "Error: " + e.getMessage();

				this.labelError.setText (errorString);

				this.labelError.setForeground (Color.red);

			}

			if (errorString == "") {

				this.labelError.setText("Computing ...");

				String methodName = (String) this.methodBox.getSelectedItem();

				try {

					OdeSolver solver = null;

					if (this.methodBox.getSelectedItem().equals("AdamsBashforth")) {

						solver = new AdamsBashforth (diff, s);

					} else if (this.methodBox.getSelectedItem().equals("AdamsMoulton")) {

						solver = new AdamsMoulton (diff, s);

					} else if (this.methodBox.getSelectedItem().equals("BFD")) {

						solver = new BDF (diff, s);

					} else {

						Class<?> c = Class.forName("odeSolver." + methodName);
						Constructor<?> cons = c.getConstructor(odeSolver.DifferentialEquation.class);
						solver = (OdeSolver) cons.newInstance(diff);

					}

					this.yk = solver.solve();

					this.isSolved = true;

					this.plot(diff);

					if (this.hasExactButton.isSelected()) { // Compute Errors

						this.labelError.setText("Errors Computation ...");

						solver.errors();

						this.labelError.setText("Average Error: " + solver.getDiff().getErrorsPercAvg() + " %");

					} else {

						this.labelError.setText(solver.getMethodName());

					}

					System.out.println ();

					for (int i = 0; i < diff.getYk().length; i++) {

						System.out.println (diff.getYk()[i]);

					}

					this.panelOutput.revalidate();
					this.frame.repaint();

				} catch (ClassNotFoundException
						| NoSuchMethodException
						| SecurityException
						| InstantiationException
						| IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException
						| WrongInputException
						| WrongExpressionException
						| WrongCalculationException e) {

					this.labelError.setText (e.getClass().getName() + ": " + e.getMessage());

					this.labelError.setForeground (Color.red);

				}

			}

		}

		if (arg0.getActionCommand().equals(this.plotButton.getText())) {

			this.panelOutput.remove(this.panelValues);

			this.panelOutput.add(this.panelPlot);

			if (this.isSolved) {

				this.frame.setBounds(100, 100, this.widthPlot, this.heightPlot+this.heightExact);

			}

			this.panelOutput.revalidate();

			this.frame.repaint();

		}

		if (arg0.getActionCommand().equals(this.valuesButton.getText())) {

			this.panelOutput.remove(this.panelPlot);

			this.panelOutput.add(this.panelValues);

			if (this.isSolved) {

				this.frame.setBounds(100, 100, widthExact, heightExact+heightPerLine*this.yk.length);

			}

			this.panelOutput.revalidate();

			this.frame.repaint();

		}

	}


	/**
	 * Plot the approximated solution of the differential equation
	 * (and, if possible, the exact solution)
	 *
	 * @param diff Differential Equation Object Containing The Solution
	 */
	private void plot (DifferentialEquation diff) {

		this.labelError.setText("Plotting ...");

		// Initialization
		double[] timeInterval = diff.getTimeInterval();
		double[] yk = diff.getYk();
		double[] exact = new double[yk.length];
		XYSeriesCollection dataset = new XYSeriesCollection ();

		// Exact Data
		if (diff.isHasExact()) {

			MathExpr exactExpr = diff.getExprExact();

			for (int i = 0; i < yk.length; i++) {

				try {

					exact[i] = exactExpr.evalSymbolic(timeInterval[i]).getOperandDouble();

				} catch (WrongCalculationException | WrongExpressionException | WrongInputException e) {

					e.printStackTrace();

				}

			}

		}



		// Printing Values To YkTextPanel
		String ykOut = "";

		if (diff.isHasExact()) {

			ykOut += "Approximated Solution: \n";

		}

		for (int i = 0; i < yk.length; i++) {

			ykOut += "yk[" + i + "] = " + String.format(formatDouble, yk[i]) + "\n";

		}

		this.textPaneYk.setText(ykOut);
		this.panelValues.removeAll();
		this.panelValues.setLayout( new GridLayout (1, 1, 1, 1));
		this.panelValues.add (this.textPaneYk);

		// Printing Values To ExactTextPanel
		if (diff.isHasExact()) {

			String exactOut = "Exact Solution: \n";

			for (int i = 0; i < yk.length; i++) {

				exactOut += "exact[" + i + "] = " + String.format(formatDouble, exact[i]) + "\n";

			}

			this.textPaneExact.setText(exactOut);
			this.panelValues.removeAll();
			this.panelValues.setLayout( new GridLayout (1, 2, 1, 1));
			this.panelValues.add (this.textPaneYk);
			this.panelValues.add (this.textPaneExact);

		}


		// Approximated Serie Generation
		this.approximatedXY = new XYSeries ("Approximated - " + this.methodBox.getSelectedItem());
		for (int i = 0; i < yk.length; i++) { // Approximated Solution

			this.approximatedXY.add(timeInterval[i], yk[i]);

		}
		dataset.addSeries (this.approximatedXY);


		// Exact Serie Generation
		if (diff.isHasExact()) { // Exact Solution

			this.exactXY = new XYSeries ("Exact");

			for (int i = 0; i < exact.length; i++) { // Exact Solution

				this.exactXY.add(timeInterval[i], exact[i]);

			}

			dataset.addSeries (this.exactXY);

		}


		// Plotting
		this.chartPlot = ChartFactory.createXYLineChart("y'(t) = "+this.textFieldInput.getText(), // Title
						 "t", // X Label
						 "y(t)", // Y Label
						 dataset);

		this.panelChart = new ChartPanel (this.chartPlot);

		this.panelPlot.removeAll();

		this.panelPlot.add(this.panelChart);


		// Visualization (ValuesView Or PlotView)
		if (this.valuesButton.isSelected()) { // Visualize Values List

			this.panelOutput.removeAll();

			this.panelOutput.add (this.panelValues, BorderLayout.CENTER);

			this.frame.setBounds(100, 100, widthExact, heightExact+heightPerLine*this.yk.length);

			this.panelOutput.revalidate();

		}

		if (this.plotButton.isSelected()) { // Visualize Plot

			this.panelOutput.removeAll();
			this.panelOutput.add (this.panelPlot, BorderLayout.CENTER);

			this.frame.setBounds(100, 100, this.widthPlot, this.heightPlot+this.heightExact);

			this.panelOutput.revalidate();

		}

		this.panelPlot.revalidate();
		this.panelValues.revalidate();
		this.panelOutput.setVisible(true);
		this.panelOutput.revalidate();
		this.frame.repaint();

	}


	public static void main(String[] args) {

		JnODE window = new JnODE();

		window.frame.setVisible(true);

	}



}
