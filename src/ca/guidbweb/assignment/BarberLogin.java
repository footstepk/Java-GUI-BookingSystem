package ca.guidbweb.assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class BarberLogin extends JFrame {
	/** Declare the GUI component instances. */
	private JFrame frame;
	private JPanel panel;
	private JButton loginBtn, newBtn; // A login and create a new account buttons.
	private JButton forgotBtn;
	private JLabel homeLabel; // A text label describing the home page.
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/* Constructor to setup the GUI component and event handlers. */
	public BarberLogin() {
		createBarberLoginHomeUI();
	}

	public JLabel setLoginHomeLabel() {
		final String message = "Welcome to Automated Booking System\n\nPlease login below or create a new account";
		homeLabel = new JLabel("---------", JLabel.CENTER);
		homeLabel.setOpaque(true);
		homeLabel.setText(message);
		return homeLabel;
	}

	/*
	 * Create the GUI component.
	 */
	public JComponent setBarberLoginPanelContentPane() {
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 4));
		panel.setOpaque(true);

		usernameLabel = new JLabel();
		usernameLabel.setText("Enter your username:");

		passwordLabel = new JLabel();
		passwordLabel.setText("Enter your password: ");

		usernameField = new JTextField(40);
		usernameField.setOpaque(true);
		usernameField.setEditable(true);

		passwordField = new JPasswordField();
		passwordField.setOpaque(true);

		// Setup the buttons.
		loginBtn = new JButton("Log In");
		loginBtn.setOpaque(true);

		forgotBtn = new JButton("Forgot Login Details");
		forgotBtn.setOpaque(true);

		newBtn = new JButton("Create New Account");
		newBtn.setOpaque(true);

		usernameLabel.setLabelFor(usernameField);
		passwordLabel.setLabelFor(passwordLabel);

		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(loginBtn);
		panel.add(forgotBtn);
		panel.add(newBtn);

		// Allocate of instance of the inner class when fire.
		BarberLoginButtonListener listener = new BarberLoginButtonListener();

		loginBtn.addActionListener(listener);
		forgotBtn.addActionListener(listener);
		newBtn.addActionListener(listener);

		return panel;
	}

	public void createBarberLoginHomeUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(setLoginHomeLabel(), BorderLayout.PAGE_START);
		frame.add(setBarberLoginPanelContentPane(), BorderLayout.PAGE_END);

		frame.setTitle("Automated Booking System - Barber Login");
				frame.setSize(150000, 150000);
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * BarberLoginButtonListener is a inner class for use of ActionListener
	 */

	private class BarberLoginButtonListener implements ActionListener {
		/** Declare an instance of new registration option. */
		private ForgottenForm forgot = new ForgottenForm();
		private BarberUI barberUI = new BarberUI();
		private BarberRegistration barber = new BarberRegistration();

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
			String user = usernameField.getText();
			String pwd = String.valueOf(passwordField.getPassword());

			if(source == loginBtn) {
				if(getLogin(user, pwd)) {
					// display login message.
					JOptionPane.showMessageDialog(frame, "Success! You are now logged in to the system");
					barberUI.createBarberUI();
				}
				else {
					JOptionPane.showMessageDialog(frame, "Error! You cannot log in this time, you either got your username or password wrong, try again");
				}
			} else if(source == newBtn) {
				barber.createBarberRegistrationUI();
			} else if(source == forgotBtn) {
				forgot.createForgottenForm();
			}
			else {
				JOptionPane.showMessageDialog(frame, "Invalid input, try again");
			}
		}
	}

	/*
	 * Check if all entries are valid.
	 * All valid entries should return and upon their registration status.
	 */
	public boolean getLogin(String email, String password) {
		//Initial the database connection and its statement.
		final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

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
			String sql = "SELECT * " +
					"FROM barbers " +
					"WHERE barber_email = ? AND password = ?;";

			pstmt = connection.prepareStatement(sql);

			// Bind values into the parameter.
			pstmt.setString(1, email);
			pstmt.setString(2, password);
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
			Statement stmt = null;
			String query = "SELECT password " +
			"FROM barbers " +
					"WHERE password = " + password + ";";
			
			ResultSet rs = stmt.executeQuery(query);
			String pass = "";
			while(rs.next()) {
				pass = rs.getString("password");
			
			if(password.equals(pass)) {
				result = true; // password matched!
			}
			else {
				result = false; // password doesn't matched!
			}
			}

			// Clean the environment.
			pstmt.close(); stmt.close(); connection.close();

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

}
