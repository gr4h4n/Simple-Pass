/**
 * (C) Copyright 2015, Alexander Wei�
 *	The use of this software is free. 
 *	I do not assume that the passwords generated by this program
 *	are unbreakable. This program should only give a fast
 *	possibility to generate a random password. 
 *	Use at your own risk.
 */


package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import application.PasswordGenerator;

/**
 * Class for the user interface
 */
public class UserInterface extends JFrame {


	private static final long serialVersionUID = -6184788803882753965L;
	private JComboBox<?> lengthField;
	private JTextField outField;
	private String[] lengthsOfPw = {"8","12","20","25"};
	public JRadioButton lowerCase;
	public  JRadioButton upperCase;
	public  JRadioButton numbers;
	public JRadioButton specials;
	public boolean copied = false;
	
	private JButton generate;
	private JButton copyToClipboard;
	
	/**
	 * Creates a new visible graphical user interface
	 */
	public UserInterface(){
		
		createUi();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setTitle("Simple Password Generator");
	
	}

	/**
	 * Creates the main panel for the graphical user interface-
	 */
	private void createUi() {
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		
		upperPanel.add(createInOutPanel());
		lowerPanel.add(createSelectionPanel());
		lowerPanel.add(createButtonPanel());
		
		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		setContentPane(mainPanel);
	}
	
	/**
	 * Create the panel where the input text-field and output text-field is placed.
	 * @return a JPanel
	 */
	private JPanel createInOutPanel(){
		
		JPanel inOutPanel = new JPanel();
		inOutPanel.setLayout(new GridLayout(2,2));
		lengthField = new JComboBox<>(lengthsOfPw);
		lengthField.setEditable(true);
		lengthField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				lengthField.setForeground(Color.black);;
				lengthField.setToolTipText("");
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
			}
			
		});
		outField = new JTextField("");
		outField.setEditable(false);
		inOutPanel.add(new JLabel("Length of password"));
		inOutPanel.add(new JLabel("Output"));
		inOutPanel.add(lengthField);
		inOutPanel.add(outField);
		
		return inOutPanel;
	}
	

	/**
	 * Creates the options panel for the user to choose which characters should be included in the password.
	 * @return a JPanel
	 */
	private JPanel createSelectionPanel() {
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new GridLayout(2,2));
		lowerCase = new JRadioButton("lowercase characters");
		upperCase = new JRadioButton("uppercase  characters");
		numbers = new JRadioButton("numbers");
		specials = new JRadioButton("special characters like {\u00C4, \u00F6, #, /, !}");
		
		selectionPanel.add(lowerCase);
		selectionPanel.add(upperCase);
		selectionPanel.add(numbers);
		selectionPanel.add(specials);
		selectionPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		
		return selectionPanel;
	}
	
	/**
	 * Creates the button panel.
	 * @return a JPanel
	 * 
	 */
	private JPanel createButtonPanel(){
		
		JPanel buttonPanel  = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		generate = new JButton("Generate password");
		generate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			try{
				PasswordGenerator pwGenerator = new PasswordGenerator(Integer.parseInt((String)lengthField.getSelectedItem()));
				outField.setForeground(Color.BLACK);
				outField.setText(pwGenerator.generatePassword(lowerCase.isSelected(), upperCase.isSelected(), numbers.isSelected(), specials.isSelected()));
				copied=false;
				}catch(NumberFormatException nfe){
					lengthField.setForeground(Color.red);
					lengthField.setToolTipText("!You have to type a number here!");
				}catch(IllegalArgumentException iae){
					lengthField.setForeground(Color.red);
					lengthField.setToolTipText("!Set at least one option!");
				}
			}
			
		});
		copyToClipboard = new JButton("Copy to clipboard");
		copyToClipboard.setSize(generate.getWidth(), generate.getHeight());
		copyToClipboard.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!copied){
					StringSelection stringSelection = new StringSelection (outField.getText());
					Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
					clpbrd.setContents (stringSelection, null);	
					outField.setForeground(new Color(20,190,0));
					outField.setText(outField.getText() + "           (!Copied to Clipboard!)");
					copied = true;
				}
			}
			
		});
		
		GridBagConstraints  c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy =0;
		
		buttonPanel.add(generate,c);
		c.gridy=1;
		buttonPanel.add(copyToClipboard,c);
		return buttonPanel;
	}
	
	@SuppressWarnings("unused")
	public static void main (String [] args){
		UserInterface ui = new UserInterface();
	}
}
