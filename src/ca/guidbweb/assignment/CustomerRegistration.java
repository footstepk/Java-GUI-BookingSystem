package ca.guidbweb.assignment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.*;
import java.sql.*;

/*
 * CustomerRegistration class is a class that represents the customer registration forms.
 */

public class CustomerRegistration {

	private JFrame frame;
	/** Declare the components. */
	private JButton registerBtn; // submit registration button.
	private JButton goBackBtn; // for go back to home page.
	private JLabel registrationLabel, firstNameLabel, lastNameLabel, phoneLabel, emailLabel, passwordLabel, reEnterLabel;
	private JPasswordField passwordField, reEnterPasswordField; // For password and re-enter password input field.
	private JTextField firstNameField, lastNameField, phoneField, emailField;

	/** Empty constructor. */
	public CustomerRegistration() {
	}

	/*
	 * Create customer registration form label to the window frame.
	 * @return the label to represent the label text.
	 */
	public JLabel setCustomerRegistrationPageLabel() {
		final String message = "Welcome to Customer Registration Form\nPlease enter your details with * is mandatory";
		// Setup the label.
		registrationLabel = new JLabel();
		registrationLabel.setText(message);
		registrationLabel.setOpaque(true);
		registrationLabel.setBackground(new Color(124, 165, 121));
		registrationLabel.setPreferredSize(new Dimension(180, 180));
		return registrationLabel;
	}

	/*
	 * Create the panel to hold the components.
	 * @return the panel of the components.
	 */
	public JComponent setCustomerRegistrationContentPane() {
		JPanel registrationPanel = new JPanel();
		registrationPanel.setLayout(new GridLayout(7, 2, 5, 5));

		// insert all the components into the panel.
		firstNameLabel = new JLabel("* Enter your first name: ");
		lastNameLabel = new JLabel("* Enter your last name: ");
		phoneLabel = new JLabel("* Enter your phone number: ");
		emailLabel = new JLabel("* Enter your email address: ");
		passwordLabel = new JLabel("* Create your new password: ");
		reEnterLabel = new JLabel("* Re-enter the password: ");

		firstNameField = new JTextField(40);
		firstNameField.setEditable(true);
		firstNameField.setOpaque(true);

		lastNameField = new JTextField(40);
		lastNameField.setEditable(true);
		lastNameField.setOpaque(true);

		phoneField = new JTextField(20);
		phoneField.setEditable(true);
		phoneField.setOpaque(true);

		emailField = new JTextField(40);
		emailField.setEditable(true);
		emailField.setOpaque(true);

		passwordField = new JPasswordField(40);
		passwordField.setOpaque(true);

		reEnterPasswordField = new JPasswordField(40);
		reEnterPasswordField.setOpaque(true);

		firstNameLabel.setLabelFor(firstNameLabel);
		lastNameLabel.setLabelFor(lastNameLabel);
		phoneLabel.setLabelFor(phoneLabel);
		emailLabel.setLabelFor(emailLabel);
		passwordLabel.setLabelFor(passwordLabel);
		reEnterLabel.setLabelFor(reEnterLabel);

		registrationPanel.add(firstNameLabel);
		registrationPanel.add(firstNameField);
		registrationPanel.add(lastNameLabel);
		registrationPanel.add(lastNameField);
		registrationPanel.add(phoneLabel);
		registrationPanel.add(phoneField);
		registrationPanel.add(emailLabel);
		registrationPanel.add(emailField);
		registrationPanel.add(passwordLabel);
		registrationPanel.add(passwordField);
		registrationPanel.add(reEnterLabel);
		registrationPanel.add(reEnterPasswordField);
		registrationPanel.add(setCustomerRegistrationButton());
		registrationPanel.add(setGoBackButton());
		return registrationPanel;
	}

	/*
	 * Create the registration button
	 * @return the submission of the registration.
	 */
	public JButton setCustomerRegistrationButton() {
		registerBtn = new JButton("Create My Account");
		registerBtn.setOpaque(true);
		registerBtn.setBackground(new Color(224, 224, 163));
		registerBtn.setPreferredSize(new Dimension(20, 20));
		// Allocate an instance of this inner class when click on fire.
		CustomerRegistrationButtonListener listener = new CustomerRegistrationButtonListener();
		registerBtn.addActionListener(listener);

		return registerBtn;
	}
/*
 * Create go back button
 * @return go back button the go back to the home page
 */
	public JButton setGoBackButton() {
		goBackBtn = new JButton("Go Back To Home");
		goBackBtn.setOpaque(true);
		goBackBtn.setBackground(new Color(224, 224, 163));
		goBackBtn.setPreferredSize(new Dimension(20, 20));

		// Allocate an instance of this inner class when click on fire.
		CustomerRegistrationButtonListener listener = new CustomerRegistrationButtonListener();
		goBackBtn.addActionListener(listener);

		return goBackBtn;
	}

	/*
	 * Create and setup the GUI. For thread safety, the method
	 * should be invoke in event-dispatching thread.
	 */
	public void createCustomerRegistrationUI() {
		// Setup the main window frame.
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Show the label and buttons here.
		frame.add(setCustomerRegistrationPageLabel(), BorderLayout.PAGE_START);

		// Setup the panel components.
		frame.add(setCustomerRegistrationContentPane(), BorderLayout.CENTER);

		frame.setTitle("Automated Booking System -> Customer Registration Form");
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * CustomerRegistrationButtonListener is an instance of the inner class of CustomerRegistration.
	 * It verifies the entries of customer registration details.
	 * It implements the ActionEvent. 
	 */
	private class CustomerRegistrationButtonListener implements ActionListener {
		/** Declare the instance outer class. */
		private CustomerRegistration cr = new CustomerRegistration();
		private CustomerUI customerUI = new CustomerUI();
private HomeUI homeUI = new HomeUI();

/** Establishing the SQL connection instances. */
		private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
				//private static final String JDBC_URL = "jdbc:mysql://localhost:3306/booking";

		/** MySQL user credential, you should alter if differ than default. */
//				private static final String USERNAME = "root";
//				private static final String PASSWORD = "kjtms55x99"; // if using root, change to "root"

		/*
		 * Check if all entries are valid.
		 * All valid entries should return and upon their registration status.
		 */
		public boolean getCustomerRegister(String first, String last, String phone, String email, String password) {
			//Initial the database connection and its statement.
			Connection connection = null;
			PreparedStatement pstmt = null;
			boolean result = false;
			try {
				// For testing purposes, this should be observation only.
				System.out.println("Connecting.. to the database");
				Class.forName(JDBC_DRIVER);
				connection = MyJDBCUtil.getConnection();

				// Create the prepare statement.
				System.out.println("Creating statement...");
				String sql = "INSERT INTO customers values" +
						"(?, ?, ?, ?, ?, ?, ?, ?, ?)";

				pstmt = connection.prepareStatement(sql);

				// Bind values into the parameter.
				/** hashed the password before insert into the database. */
				int hashed = Integer.parseInt(password);
				password = "" + hashed;

				pstmt.setInt(1, 0);
				pstmt.setString(2, first);
				pstmt.setString(3, last);
				pstmt.setString(4, phone);
				pstmt.setString(5, email);
				pstmt.setString(6, password);
				pstmt.setString(7, null);
				pstmt.setString(8, null);
				pstmt.setString(9, null);
				pstmt.setString(10, null);

				// Let's update the row.

				int rows = pstmt.executeUpdate();
				if(rows > 0) {
					result = true; // row greater than 0.
				} else {
					result = false;
				}

				/** For testing purposes, this should for observation only. */
				System.out.println("Rows impacted : " + rows );
				// Let us select all the records and display them.
				sql = "SELECT first_name, last_name, customer_email, past_booked, comment FROM customers";
				ResultSet rs = pstmt.executeQuery(sql);

				// Extract values from result set.
				while(rs.next()) {
					// Retrieve by column name.
					String f = rs.getString("first_name");
					String l = rs.getString("last_name");
					String e = rs.getString("customer_email");
					String past = rs.getString("past_booked");
					String comment = rs.getString("comment");

					// For testing purposes, display result.
					System.out.println("First Name: " + f);
					System.out.println("LastName: "+ l);
					System.out.println("Email Address: " + e);
					System.out.println("Passed Booking Data and Time: " + past);
					System.out.println("Comment: " + comment);
				}
				// Clean the environment.
				rs.close(); pstmt.close(); connection.close();

			} catch(SQLException ex) {
				System.err.println(ex.getMessage());
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}		catch(SQLException ex) {
				}
				try {
					if(connection != null) {
						connection.close();
					}
				}catch(SQLException e) {
				}
			}
			return result;
		}
/*
 * Checks if passwords are met specified criteria.
 */
		public boolean isPasswordValid(String p1, String p2) {
			// initial boolean values of result.
			boolean result = false;
			p1 = String.valueOf(passwordField.getPassword());
			p2 = String.valueOf(reEnterPasswordField.getPassword());

			/** First check the length of the array stored size,
			 * exit the loop if the input value  is either null or 0,
			 * or greater than 8 and less than 12
			 * At least 1 Upper case, 1 number and 1 special marks.
			 */
			if(! p1.isEmpty() || ! p2.isEmpty()) {
				result = true;
			} else if((p1.length() >= 8 && p1.length() <= 12) && (p2.length() >= 8 && p2.length() <= 12)) {
					result = true;
				} else if(isPasswordOkay(p1)) {
					result = true;
				} else {
					result = false;
				}
			return result;
		}

		protected boolean isPasswordOkay(String p) {
			return p.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*\\d)(?!.*(AND|NOT)).*[a-z].*");
		}

		/*
		 * Implements the action listener.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String phone = phoneField.getText();
			String email = emailField.getText();
			String pwd1 = String.valueOf(passwordField.getPassword());
			String pwd2 = String.valueOf(reEnterPasswordField.getPassword());

			JButton source = (JButton) event.getSource();
			if(source == registerBtn) {
			if((getCustomerRegister(firstName, lastName, phone, email, pwd1)) && (isPasswordValid(pwd1, pwd2))) {
					JOptionPane.showMessageDialog(frame, "Success! You had created a new account\nPlease go to login page to access your account");
					customerUI.createCustomerUI();
					frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, "Errored! Something gone wrong, please try again");
			}
		} else if(source == goBackBtn) {
			homeUI.createHomeUI();
			frame.dispose();
		}
		}
	} // End of the inner class CustomerRegistrationButtonListener.

	/** For testing purposes, invoke main method here. */
	public static void main(String [] args) {
		CustomerRegistration customerRegistration = new CustomerRegistration();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				customerRegistration.createCustomerRegistrationUI();
			}
		});
	}
}
