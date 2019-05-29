package ca.guidbweb.assignment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.sql.*;
/*
 * ForgottenForm is a class that represents a forgotten form for 
 * user to retrieve the reset login details via email as credential.
 */
public class ForgottenForm extends JFrame {
	
	/** Declare the GUI components instances. */
	private JButton submitBtn; // for retrieve reset login detail button.
	private JLabel pageLabel, usernameLabel; // for page and user name label text.
	private JTextField usernameField; // for entry of user name.
	
	/** Constructor (Empty) to setup the GUI component and event handlers. */
	public ForgottenForm() {
	}
	
	/*
	 * Create label text to describe the ForgottenForm page.
	 * @return label text the text to describe the ForgottenForm page.
	 */
	public JLabel setForgottenFormPageLabel() {
		String message = "Hey, no worries you can retrieve your login details\nPlease enter your username with * is mandatory";
		// Setup the label.
		pageLabel = new JLabel(" ");
		pageLabel.setText(message);
		pageLabel.setOpaque(true);
		pageLabel.setBackground(new Color(124, 165, 121));
		pageLabel.setPreferredSize(new Dimension(180, 180));
		return pageLabel;
	}
	
	/*
	 * Create the GUI container to hold the components.
	 */
	public JComponent setForgottenFormContentPane() {
		// Create the panel container to hold the GUI components.
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
		panel.setOpaque(true);
		
		// Setup the label and user name field.
		usernameLabel = new JLabel(" ");
		usernameLabel.setOpaque(true);
		usernameLabel.setToolTipText("Your username is your email address");
		usernameLabel.setText("* Enter your username: ");
		
		usernameField = new JTextField(40);
		usernameField.setEditable(true);
		usernameField.setOpaque(true);
		// Adhere to user name field.
		usernameLabel.setLabelFor(usernameField);
		
		// Add into the pane's container.
		panel.add(usernameLabel);
		panel.add(usernameField);
		
		return panel;
	}
	
	/*
	 * Create button for submitting reset login instructions.
	 * @return button the button  that caused event listener.
	 */
	public JButton setSubmittingButton() {
		submitBtn = new JButton("Retrieve My Login Detail");
		submitBtn.setOpaque(true);
		submitBtn.setBackground(new Color(224, 224, 163));
		submitBtn.setPreferredSize(new Dimension(25, 25));

		// Allocate an instance of the inner class when click on fire.
		ForgottenFormButtonListener listener = new ForgottenFormButtonListener();

		submitBtn.addActionListener(listener);
		return submitBtn;
	}
	
	/*
	 * Create the GUI and show it. For thread safety,
	 * this method should invoke in event dispatching thread.
	 */
	public void createForgottenForm() {
		// Create the frame to hold its components.
		setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// show the label and buttons here.
		add(setForgottenFormPageLabel(), BorderLayout.PAGE_START);
		
		// show panel's container its component here.
		add(setForgottenFormContentPane(), BorderLayout.CENTER);
		
		add(setSubmittingButton(), BorderLayout.PAGE_END);
		
		// Display the frame.
		setTitle("Automated Booking System -> Forgotten Form");
		pack();
		setVisible(true);
	}
	
	/*
	 * ForgottenFormButtonListener is an instance of the inner class of ForgoteenForm.
	 * It allows to retrieve their login details via submit button.
	 * It implements the ActionEvent when click on fire.
	 */
	private class ForgottenFormButtonListener implements ActionListener {
		/** Declare the instance outer class. */
		private ForgottenForm ff = new ForgottenForm();
		private HomeUI homeUI = new HomeUI();

		/** Establishing the SQL connection instances. */
		private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		private static final String JDBC_URL = "jdbc:mysql://localhost:3306/booking";

		/** MySQL user credential, you should alter if differ than default. */
		private static final String USERNAME = "root";
		private static final String PASSWORD = "kjtms55x99"; // if using root, change to "root"

		/*
		 * It allows user(s) to reset their login details, if all entries are valid.
		 * All valid entries should return and upon their registration status.
		 * @return true or false the request been sent to the administrator(s).
		 */
		public boolean getLoginReset(String description) {
			//Initial the database connection and its statement.
			Connection connection = null;
			PreparedStatement pstmt = null;
			boolean result = false;
			try {
				// For testing purposes, this should be observation only.
				System.out.println("Connecting.. to the database");
				Class.forName(JDBC_DRIVER);
				connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

				// Create the prepare statement.
				System.out.println("Creating statement...");
				String sql = "UPDATE administrators " +
						"SET request_files = ? " +
						"WHERE title = 'Admin'";

				pstmt = connection.prepareStatement(sql);

				// Bind values into the parameter.
				pstmt.setString(1, description);

				// Let's update the row.

				int rows = pstmt.executeUpdate();
				if(rows > 0) {
					result = true; // row greater than 0.
				} else {
					result = false;
				}
				System.out.println("Rows impacted : " + rows );
				// Let us select all the records and display them.
				sql = "SELECT first_name, last_name, title, request_files FROM administrators";
				ResultSet rs = pstmt.executeQuery(sql);

				// Extract values from result set.
				while(rs.next()) {
					// Retrieve by column name.
					String f = rs.getString("first_name");
					String l = rs.getString("last_name");
					String t = rs.getString("title");
					String request = rs.getString("request_files");

					// For testing purposes, display result.
					System.out.println("First Name: " + f);
					System.out.println("LastName: "+ l);
					System.out.println("Title: " + t);
					System.out.println("Request Files: " + request);
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
		 * Implements the action listener.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			String email = usernameField.getText();
			
			
// Check if user(s) had enter their user name and click submit button.
			if(getLoginReset(email)) {
				JOptionPane.showMessageDialog(ff, "You had requested a reset login, you'll shortly receive email how to reset your login details");
				ff.createForgottenForm();
				submitBtn.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(ff, "Errored! Something gone wrong, please try again");
			}
		}
	} // End of the inner class CustomerRegistrationButtonListener.
}
