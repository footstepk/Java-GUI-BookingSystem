package ca.guidbweb.assignment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * NewRegistrationOption is a class that represents a new registration frame
 * for user to create a new account.
 * User(s) able to choose the user(s) types.
 * 
 */

public class NewRegistrationOption extends JFrame {
	
	/** Declare the GUI containers and its components instances. */
	private JPanel panel;
	private JButton newCustomerBtn, newBarberBtn;
	private JLabel pageLabel; // label text for window frame.
	
	/** Constructors to setup the GUI component and its event handlers. */
	public NewRegistrationOption() {
	}
	
	/*
	 * Create a text label to represent the page.
	 * @return label the label to represent the page text.
	 */
	public JLabel setPageLabel() {
		String message = "Welcome to Automated Booking System\n\nPlease choose user type to register";
		pageLabel = new JLabel("-----");
		pageLabel.setOpaque(true);
		pageLabel.setText(message);
		return pageLabel;
	}
	
	/*
	 * Create the GUI container to hold two
	 * new customer and new barber buttons.
	 * @return container the GUI container's component.
	 */
	public JComponent setPanelContentPane() {
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panel.setOpaque(true);
		
		// Setup the button into the panel.
		newCustomerBtn = new JButton("Create New Customer");
		newCustomerBtn.setOpaque(true);
		
		newBarberBtn = new JButton("Create New Barber");
		newBarberBtn.setOpaque(true);
		
		panel.add(newCustomerBtn);
		panel.add(newBarberBtn);
		
		// Allocate an instance of the inner class of NewRegistrationButtonListener.
		NewRegistrationButtonListener listener = new NewRegistrationButtonListener();
		newCustomerBtn.addActionListener(listener);
		newBarberBtn.addActionListener(listener);

		return panel;
	}
	
	/*
	 * Create the GUI and show it. For thread safety,
	 * this method should be invoke in the event-dispatching thread.
	 */
	public void createNewRegistrationOption() {
		// Setup the frame.
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(setPageLabel(), BorderLayout.PAGE_START);
		add(setPanelContentPane(), BorderLayout.PAGE_END);
		
		setTitle("Automated Booking System -> New Registration Option");
		pack();
		setVisible(true);
	}
	
	/**
	 * NewRegistrationButtonListener is an instance class of the inner class of NewRegistrationOption.
	 */
	private class NewRegistrationButtonListener implements ActionListener {
		private NewRegistrationOption newRegistrationOption = new NewRegistrationOption();
		private BarberRegistration barberRegistration = new BarberRegistration();
		private CustomerRegistration customerRegistration = new CustomerRegistration();
		
		@Override
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
			
			// Check which button caused on fire the event listener.
			if(source == newCustomerBtn) {
				customerRegistration.createCustomerRegistrationUI();
			} else if(source == newBarberBtn) {
				barberRegistration.createBarberRegistrationUI();
			} else {
				newRegistrationOption.dispatchEvent(event);
			}
		}
		
	}
}
