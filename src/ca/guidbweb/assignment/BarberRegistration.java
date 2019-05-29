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
import java.sql.*;

/*
 * BarberRegistration class is a class that represents the barber registration forms.
 */

public class BarberRegistration {

	private JFrame frame;
	/** Declare the components. */
	private JButton registerBtn; // submit registration button.
	private JButton goBackBtn; // for go back to home page.
	private JLabel registrationLabel, firstNameLabel, lastNameLabel, phoneLabel, emailLabel, passwordLabel, reEnterLabel, locationLabel;
	private JPasswordField passwordField, reEnterPasswordField; // For password and re-enter password input field.
	private JTextField firstNameField, lastNameField, phoneField, emailField, locationField;

	/** Empty constructor. */
	public BarberRegistration() {
	}

	/*
	 * Create barber registration form label to the window frame.
	 * @return the label to represent the label text.
	 */
	public JLabel setBarberRegistrationPageLabel() {
		final String message = "Welcome to Barber Registration\nPlease enter your details with * is mandatory";
		// Setup the label.
		registrationLabel = new JLabel(" ");
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
	public JComponent setBarberRegistrationContentPane() {
		JPanel registrationPanel = new JPanel();
		registrationPanel.setLayout(new GridLayout(8, 2, 5, 5));

		// insert all the components into the panel.
		firstNameLabel = new JLabel("* Enter your first name: ");
		lastNameLabel = new JLabel("* Enter your last name: ");
		locationLabel = new JLabel("* Enter your location: ");
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

		locationField = new JTextField(40);
		locationField.setEditable(true);
		locationField.setOpaque(true);

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
		locationLabel.setLabelFor(locationField);
	phoneLabel.setLabelFor(phoneLabel);
		emailLabel.setLabelFor(emailLabel);
		passwordLabel.setLabelFor(passwordLabel);
		reEnterLabel.setLabelFor(reEnterLabel);
		
		registrationPanel.add(firstNameLabel);
		registrationPanel.add(firstNameField);
		registrationPanel.add(lastNameLabel);
		registrationPanel.add(lastNameField);
		registrationPanel.add(locationLabel);
		registrationPanel.add(locationField);
				registrationPanel.add(phoneLabel);
		registrationPanel.add(phoneField);
		registrationPanel.add(emailLabel);
		registrationPanel.add(emailField);
		registrationPanel.add(passwordLabel);
		registrationPanel.add(passwordField);
		registrationPanel.add(reEnterLabel);
		registrationPanel.add(reEnterPasswordField);
		registrationPanel.add(setBarberRegistrationButton());
		registrationPanel.add(setGoBackButton());
		return registrationPanel;
	}

	/*
	 * Create the registration button
	 * @return the submission of the registration.
	 */
	public JButton setBarberRegistrationButton() {
		registerBtn = new JButton("Create My Account");
		registerBtn.setOpaque(true);
		registerBtn.setBackground(new Color(224, 224, 163));
		registerBtn.setPreferredSize(new Dimension(20, 20));

		// Allocate an instance of the inner class when click on fire.
		BarberRegistrationButtonListener listener = new BarberRegistrationButtonListener();

		registerBtn.addActionListener(listener);
		return registerBtn;
	}

	public JButton setGoBackButton() {
		goBackBtn = new JButton("Go Back To Home");
		goBackBtn.setOpaque(true);
		goBackBtn.setBackground(new Color(224, 224, 163));
		goBackBtn.setPreferredSize(new Dimension(20, 20));

		// Allocate an instance of the inner class when click on fire.
		BarberRegistrationButtonListener listener = new BarberRegistrationButtonListener();

		goBackBtn.addActionListener(listener);
		return goBackBtn;
	}

	/*
	 * Create and setup the GUI. For thread safety, the method
	 * should be invoke in event-dispatching thread.
	 */
	public void createBarberRegistrationUI() {
		// Setup the main window frame.
		frame = new JFrame();
		frame.setLayout(new BorderLayout(10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Show the label and buttons here.
		frame.add(setBarberRegistrationPageLabel(), BorderLayout.PAGE_START);

		// Setup the panel components.
		frame.add(setBarberRegistrationContentPane(), BorderLayout.CENTER);

		frame.setTitle("Automated Booking System -> Barber Registration Form");
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * BarberRegistrationButtonListener is an instance of the inner class of BarberRegistration.
	 * It implements the ActionEvent when button click on fire.
	 */
	private class BarberRegistrationButtonListener implements ActionListener {
		/** Declare the instance outer class. */
		private BarberUI barberUI = new BarberUI();
private HomeUI homeUI = new HomeUI();
		/** Establishing the SQL connection instances. */
		private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

		/*
		 * Check if all entries are valid.
		 * All valid entries should return and upon their registration status.
		 */
				public boolean getBarberRegister(String first, String last, String location, String phone, String email, String password) {
			//Initial the database connection and its statement.
			Connection connection = null;
			PreparedStatement pstmt = null;
			boolean result = false;
			try {
				// For testing purposes, this should be only observation.
				System.out.println("Connecting.. to the database");
				Class.forName(JDBC_DRIVER);
				connection = MyJDBCUtil.getConnection();

				// Create the prepare statement.
				System.out.println("Creating statement...");
				String sql = "INSERT INTO barbers values" +
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				pstmt = connection.prepareStatement(sql);
				
				/** hashed the password before insert into the database. */
int hashed = Integer.parseInt(password);
password = "" + hashed;
				// Bind values into the parameter.
				pstmt.setInt(1, 0);
				pstmt.setString(2, first);
				pstmt.setString(3, last);
				pstmt.setString(4, location);
				pstmt.setString(5, phone);
				pstmt.setString(6, email);
				pstmt.setString(7, password);
				pstmt.setBoolean(8, false);
				pstmt.setString(9, null);
				pstmt.setString(10, null);
				pstmt.setString(11, null);
				pstmt.setString(12, null);
				pstmt.setString(13, null);

				// Let's update the row.
				int countRows = pstmt.executeUpdate();
				if(countRows > 0) {
					result = true;// return boolean values if column is greater than 1
				} else {
					result = false; // duplicated entries
				}
				
				/** For testing purposes, this should for observation only!. */
				System.out.println("Rows impacted : " + countRows);
				// Let us select all the records and display them.
				sql = "SELECT first_name, last_name, location, phone, barber_email FROM barbers";
				ResultSet rs = pstmt.executeQuery(sql);

				// Extract values from result set.
				while(rs.next()) {
					// Retrieve by column name.
					String f = rs.getString("first_name");
					String l = rs.getString("last_name");
					String local = rs.getString("location");
										String p = rs.getString("phone");
					String e = rs.getString("barber_email");
					
					// For testing purposes, display result.
					System.out.println("First Name: " + f);
					System.out.println("LastName: "+ l);
					System.out.println("Location: " + local);
										System.out.println("Phone: " + p);
					System.out.println("Email Address: " + e);
					
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
//							p1 = String.valueOf(passwordField.getPassword());
//							p2 = String.valueOf(reEnterPasswordField.getPassword());

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
			String location = locationField.getText();
						String phone = phoneField.getText();
			String email = emailField.getText();
			String pwd1 = String.valueOf(passwordField.getPassword());
			String pwd2 = String.valueOf(reEnterPasswordField.getPassword());
			
			JButton source = (JButton) event.getSource();
			
			if(source == registerBtn) {
			if((getBarberRegister(firstName, lastName, location, phone, email, pwd1)) && (isPasswordValid(pwd1, pwd2))) {
				JOptionPane.showMessageDialog(frame, "Success! You had created a new account\nPlease go to login to access your account", "Information", JOptionPane.YES_NO_OPTION);
				barberUI.createBarberUI();
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, "Errored! Something is gone wrong here, please try again");
			}
		} else if(source == goBackBtn) {
			homeUI.createHomeUI();
			frame.dispose();
		}
	}
	}
	/** For testing purposes, invoke main method here. */
	public static void main(String [] args) {
		BarberRegistration barberRegistration = new BarberRegistration();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				barberRegistration.createBarberRegistrationUI();
			}
		});
	}
}