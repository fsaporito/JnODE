package Gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextPane;

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

import odeSolver.DifferentialEquation;
import odeSolver.OdeSolver;


public class JnODE implements ActionListener {

	private JFrame frame;
	private JPanel panelFrame;
	private JPanel panelConf;
	private JPanel panelInputOde;
	private JPanel panelParameter;
	private JPanel panelMethodSelection;
	private JPanel panelExact;
	private JPanel panelPlot;
	private JPanel panelErrors;
	private JTextPane textPaneYk;
	private JLabel labelInput;
	private JLabel labelStep;
	private JLabel labely0;
	private JLabel labelT0;
	private JLabel labelTmax;
	private JLabel labelExact;
	private JLabel labelError;
	private JTextField textFieldInput;
	private JTextField textFieldStep;
	private JTextField textFieldy0;
	private JTextField textFieldT0;
	private JTextField textFieldTmax;
	private JTextField textFieldExact;
	private JButton compButton;
	private JButton clearButton;
	private JCheckBox hasExactButton;
	private JComboBox<String> methodBox;
	
	private int sizeNoExact = 160;
	private int sizeExact = 190;
	private int sizePerLine = 17;
	
	private String title = "JnODE";
	
	private double[] yk;
	
	private boolean isSolved = false;
	
	private String[] methodList = {"EulerExplicit", 
								   "EulerImplicit",
								   "MidPointExplicit",
								   "Ralston",
								   "Heun",
								   "Kutta3",
								   "RK4"
									};

	

	/**
	 * Create the application.
	 */
	public JnODE() {
		
		this.initialize();
		
		this.buildGui();
	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Frame Initialization
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, sizeNoExact);
		this.frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle(this.title);
		
		// Panels Initialization
		this.panelFrame = new JPanel();
		this.panelConf = new JPanel();
		this.panelInputOde = new JPanel();
		this.panelParameter = new JPanel();
		this.panelMethodSelection = new JPanel();
		this.panelExact = new JPanel();
		this.panelPlot = new JPanel();
		this.panelErrors = new JPanel();
		
		// Labels Initialization
		this.labelInput = new JLabel ("y' = ");
		this.labelStep = new JLabel ("step");
		this.labely0 = new JLabel ("y0");
		this.labelT0 = new JLabel ("t0");
		this.labelTmax = new JLabel ("tmax");
		this.labelExact = new JLabel ("exact");
		this.labelError = new JLabel ("Press Compute To Calculate Numerically The Solution");
		
		// TextFields Initialization
		this.textFieldInput = new JTextField (30);
		this.textFieldStep = new JTextField (5);
		this.textFieldy0 = new JTextField (5);
		this.textFieldT0 = new JTextField (5);
		this.textFieldTmax = new JTextField (5);
		this.textFieldExact = new JTextField (30);
		
		// Text And Scroll Pane Initialization
		this.textPaneYk = new JTextPane();
		this.textPaneYk.setText("");
		this.textPaneYk.setEditable(false);		
		
		// Buttons Initialization
		this.compButton = new JButton ("Compute");
		this.clearButton = new JButton ("Clear");
		
		// CheckBox Initialization
		this.hasExactButton = new JCheckBox ("Exact");
		
		// ComboBox Initialization
		this.methodBox = new JComboBox<String>(this.methodList);
		
		
	}
	
	
	/**
	 * Build The Gui
	 */
	private void buildGui() {
		
		// Main Panel
		this.frame.add (this.panelFrame);		
		this.panelFrame.setLayout(new BorderLayout()); 
		this.panelFrame.add(this.panelConf, BorderLayout.NORTH);		
		this.panelFrame.add(this.panelPlot, BorderLayout.CENTER);
		this.panelFrame.add(this.panelErrors, BorderLayout.SOUTH);
		
		// Configuration Panel
		this.panelConf.setLayout(new GridLayout(3, 1, 1, 1));
		this.panelConf.add(this.panelInputOde);
		this.panelConf.add(this.panelParameter);
		this.panelConf.add(this.panelMethodSelection);
				
		// InputOde Panel
		this.panelInputOde.add(this.labelInput);
		this.panelInputOde.add(this.textFieldInput);
		
		// Parameter Panel
		this.panelParameter.add(this.labelT0);
		this.panelParameter.add(this.textFieldT0);
		this.panelParameter.add(this.labely0);
		this.panelParameter.add(this.textFieldy0);
		this.panelParameter.add(this.labelTmax);
		this.panelParameter.add(this.textFieldTmax);
		this.panelParameter.add(this.labelStep);
		this.panelParameter.add(this.textFieldStep);
		
		// Method Selection Panel
		this.panelMethodSelection.add(this.hasExactButton);
		this.panelMethodSelection.add(this.methodBox);
		this.panelMethodSelection.add(this.compButton);
		this.panelMethodSelection.add(this.clearButton);
		
		// Exact Panel
		this.panelExact.add(this.labelExact);
		this.panelExact.add(this.textFieldExact);
		
		// Plot Panel
		this.panelPlot.add(this.textPaneYk);
		this.panelPlot.setVisible(false);
		
		// Errors Panel
		this.panelErrors.add(this.labelError);
		
		
		// Action Listeners
		this.compButton.addActionListener(this);
		this.clearButton.addActionListener(this);
		this.hasExactButton.addActionListener(this);
		
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getActionCommand().equals(this.hasExactButton.getText())) {
			
			if (this.hasExactButton.isSelected()) { // Exact Solution Present
				
				if (this.isSolved) { // Adjust The Size With The Plot
					
					this.frame.setBounds(100, 100, 450, sizeExact+sizePerLine*this.yk.length);
					
				} else { // Adjust The Size Without The Plot
				
					this.frame.setBounds(100, 100, 450, sizeExact);
					
				}
				
				this.panelConf.setLayout(new GridLayout(4, 1, 1, 1));
								
				this.panelConf.add(this.panelExact);
					
			} else { // No Exact Solution
				
				this.panelConf.remove(this.panelExact);
				
				this.panelConf.setLayout(new GridLayout(3, 1, 1, 1));
				
				if (this.isSolved) { // Adjust The Size With The Plot
					
					this.frame.setBounds(100, 100, 450, sizeNoExact+sizePerLine*this.yk.length);
					
				} else { // Adjust The Size Without The Plot
				
					this.frame.setBounds(100, 100, 450, sizeNoExact);
					
				}
				
			}
			
		}
		
		if (arg0.getActionCommand().equals(this.clearButton.getText())) {
			
			this.textFieldInput.setText("");
			this.textFieldStep.setText("");
			this.textFieldy0.setText("");
			this.textFieldT0.setText("");
			this.textFieldTmax.setText("");
			this.textFieldExact.setText("");
						
			this.labelError.setText("Press Compute To Calculate Numerically The Solution");
			this.labelError.setForeground(Color.black);
			
			this.textPaneYk.setText("");
			this.panelPlot.setVisible(false);
			
			this.methodBox.setSelectedIndex(0);
			
			if (this.hasExactButton.isSelected()) { // Exact Solution Present
				
				this.hasExactButton.setSelected(false);
				
				this.panelConf.remove(this.panelExact);
				
				this.panelConf.setLayout(new GridLayout(3, 1, 1, 1));
				
			}			
		
			this.frame.setBounds(100, 100, 450, sizeNoExact);
			
			this.isSolved = false;
			
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
					
					Class<?> c = Class.forName("odeSolver." + methodName);
					Constructor<?> cons = c.getConstructor(odeSolver.DifferentialEquation.class);
					OdeSolver solver = (OdeSolver) cons.newInstance(diff);
					
					this.yk = solver.solve();
					
					this.labelError.setText("Plotting ...");
					
					this.isSolved = true;
					
					String ykOut = "";
					
					for (int i = 0; i < yk.length; i++) {
						
						ykOut += "yk[" + i + "] = " + this.yk[i] + "\n";
						
					}
					
					this.textPaneYk.setText(ykOut);
					
					if (this.hasExactButton.isSelected()) { // Exact Solution
						
						this.frame.setBounds(100, 100, 450, sizeExact+sizePerLine*this.yk.length);
						
					} else {
						
						this.frame.setBounds(100, 100, 450, sizeNoExact+sizePerLine*this.yk.length);
						
					}

					this.panelPlot.setVisible(true);
					
					if (this.hasExactButton.isSelected()) { // Compute Errors
					
						this.labelError.setText("Errors Computation ...");
						
						solver.errors();
						
						this.labelError.setText("Average Error: " + solver.getDiff().getErrorsPercAvg() + " %");
						
					} else {
						
						this.labelError.setText(solver.getMethodName());
		
					}
					
					this.frame.repaint();
					
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
					
				} catch (NoSuchMethodException e) {
					
					e.printStackTrace();
					
				} catch (SecurityException e) {
					
					e.printStackTrace();
					
				} catch (InstantiationException e) {

					e.printStackTrace();
					
				} catch (IllegalAccessException e) {

					e.printStackTrace();
					
				} catch (IllegalArgumentException e) {

					e.printStackTrace();
					
				} catch (InvocationTargetException e) {

					e.printStackTrace();
					
				} catch (WrongInputException e) {
					
					e.printStackTrace();
					
				} catch (WrongCalculationException e) {
					
					e.printStackTrace();
					
				}			
				
			}			
			
		}
		
	}
	

	public static void main(String[] args) {
		
		JnODE window = new JnODE();
		
		window.frame.setVisible(true);
		
	}


	
}
