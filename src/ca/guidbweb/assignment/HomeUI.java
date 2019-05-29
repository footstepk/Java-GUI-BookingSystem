package ca.guidbweb.assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomeUI {
	/* Declare the GUI instances. */
	private JFrame frame;
	private JPanel panel;
	/** Declare the GUI component instances. */
	private JButton barberLoginBtn, customerLoginBtn, barberNewBtn, customerNewBtn; // A login and create a new account buttons.
	private JButton adminLoginBtn;
	private JLabel homeLabel; // A text label describing the home page.

	/** Constructor to setup the GUI and event handlers. */
	public HomeUI() {
		createHomeUI();
	}

	public JLabel setHomeLabel() {
		final String message = "Welcome to Automated Booking System\n\nPlease login below or create a new account";
		homeLabel = new JLabel("---------", JLabel.CENTER);
		homeLabel.setOpaque(true);
		homeLabel.setText(message);
		return homeLabel;
	}

	public JComponent setContentButton() {
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setOpaque(true);

		// Setup the buttons.
		adminLoginBtn = new JButton("Administrator Login");
		adminLoginBtn.setOpaque(true);
		
		barberLoginBtn = new JButton("Barber Log In");
		barberLoginBtn.setOpaque(true);
		
		customerLoginBtn = new JButton("Customer Log In");
		customerLoginBtn.setOpaque(true);
		
		barberNewBtn = new JButton("Create New Barber Account");
		barberNewBtn.setOpaque(true);
		
		customerNewBtn = new JButton("Create New Customer Account");
		customerNewBtn.setOpaque(true);
		
		// Allocate of instance of the inner class when fire.
		HomeButtonListener listener = new HomeButtonListener();

		adminLoginBtn.addActionListener(listener);
		barberLoginBtn.addActionListener(listener);
		customerLoginBtn.addActionListener(listener);
		barberNewBtn.addActionListener(listener);
		customerNewBtn.addActionListener(listener);

		panel.add(adminLoginBtn);
		panel.add(barberLoginBtn);
		panel.add(customerLoginBtn);
		panel.add(barberNewBtn);
		panel.add(customerNewBtn);

		return panel;
	}

	/** This method should invoked in the safety thread. */
	public void createHomeUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(setHomeLabel(), BorderLayout.PAGE_START);
		frame.add(setContentButton(), BorderLayout.PAGE_END);

		frame.setTitle("Automated Booking System");
		frame.setSize(350000, 350000);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String [] args) {
		HomeUI homeUI = new HomeUI();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				homeUI.createHomeUI();
			}
		});
	}

	/*
	 * HomeButtonListener is a inner class for use of ActionListener
	 */

	private class HomeButtonListener implements ActionListener {

		/** Declare an instance of new registration option. */
		private AdministratorLogin adminLogin = new AdministratorLogin();
		private BarberLogin barberLogin = new BarberLogin();
		private CustomerLogin customerLogin = new CustomerLogin();
		private BarberRegistration barberRegistration = new BarberRegistration();
		private CustomerRegistration customerRegistration = new CustomerRegistration();

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
if(source == adminLoginBtn) {
	adminLogin.createAdminLoginHomeUI();
}
else if(source == barberLoginBtn) {
				barberLogin.createBarberLoginHomeUI();
			}
			else if(source == customerLoginBtn) {
				customerLogin.createCustomerLoginHomeUI();
			}
			else if(source == barberNewBtn) {
				barberRegistration.createBarberRegistrationUI();
			}
			else if(source == customerNewBtn) {
				customerRegistration.createCustomerRegistrationUI();
			}
			else {
				JOptionPane.showMessageDialog(frame, "You didn't select an option to login or create new account", "Aleart: Incorrect Input", JOptionPane.showConfirmDialog(frame, "Return To Select Again?"));
			}
		}
	}
}
