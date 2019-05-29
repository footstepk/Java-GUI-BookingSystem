package ca.guidbweb.assignment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DeleteView is a class to enable administrator to view a delete a user(s) form 
 * @author Kim Jang Wong | 2017300
 *
 */
public class DeleteView {

	/** Declare the GUI container instances. */
	private JFrame frame;

	/** Declare the GUI components instances. */
	private JButton deleteBtn; // for delete a user.
	private JButton goBackBtn; // for go back to previously.
	private JLabel pageLabel; // for the delete view page label text.
	private JLabel usernameLabel; //for label text for user name.
	private JTextField usernameField; // for user name input text field.

	/** Constructors to setup the GUI and event handlers. */
	public DeleteView() {
	}

	/*
	 * Create label text to describe the page.
	 * @return label text the page label text.
	 */
	public JLabel setPageLabel() {
		final String message = "To delete a user please insert user(s) username\nand click delete button.";
		pageLabel = new JLabel("------");
		pageLabel.setOpaque(true);
		pageLabel.setText(message);
		return pageLabel;
	}

	/*
	 * Create the GUI panel to hold the GUI component.
	 * @return component the GUI component.
	 */
	public JComponent setDeletePanelContentPane() {
		// Setup the panel container.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, 10, 10));

		// Setup the GUI component and add into the panel.
		usernameLabel = new JLabel("---");
		usernameLabel.setOpaque(true);
		usernameLabel.setText("* Enter user's email: ");

		usernameField = new JTextField(40);
		usernameField.setOpaque(true);
		usernameField.setEditable(true);

		usernameLabel.setLabelFor(usernameField);

		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(setDeleteButton());
		panel.add(setGoBackButton());

		return panel;
	}

	/*
	 * Create the delete button.
	 * @return delete an account from user the delete button.
	 */
	public JButton setDeleteButton() {
		deleteBtn = new JButton("Delete User Account");
		deleteBtn.setOpaque(true);

		// Allocate an instance of the inner class ActionListener upon fire.
		DeleteButtonListener listener = new DeleteButtonListener();

		deleteBtn.addActionListener(listener);
		return deleteBtn;
	}

	/*
	 * Create go back button.
	 * @return go back to previous page the administrator control panel.	
	 */
	public JButton setGoBackButton() {
		goBackBtn = new JButton("Go Back");
		goBackBtn.setOpaque(true);

		// Allocate an instance of the inner class ActionListener upon fire.
		DeleteButtonListener listener = new DeleteButtonListener();

		goBackBtn.addActionListener(listener);
		return goBackBtn;
	}

	/*
	 * Create and show it the GUI. For thread safety,
	 * this method should be invoked in thread event-dispatching. 
	 */
	public void createDeleteView() {
		// Setup the window frame.
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Add the Label and the component here.
		frame.add(setPageLabel(), BorderLayout.PAGE_START);
		frame.add(setDeletePanelContentPane(), BorderLayout.PAGE_END);

		// Setup the frame's title and display it.
		frame.setTitle("Automated Booking System -> Delete View Control");
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * DeleteButtonListener is an inner class of the inner class of DeleteView.
	 * It implements the ActionListener upon the button is caused the fire.
	 */
	private class DeleteButtonListener implements ActionListener {
		/** Declare the outer class instances. */
		private AdministratorUI administratorUI = new AdministratorUI();

		/** The SQL connection driver. */
		  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		@Override
		public void actionPerformed(ActionEvent event) {
			// Read input text field.
			String email = usernameField.getText();
			JButton source = (JButton) event.getSource();

			// Check which button caused the fire.
			if(source == deleteBtn) {
				if((! email.isEmpty()) && (isUserDeleted(email))) {
					JOptionPane.showMessageDialog(frame, "You got it! The user(s) account has been deleted!", "Information Success Message", JOptionPane.showConfirmDialog(frame, "Suecessful deleted"));
				usernameField.repaint();
				} else {
					JOptionPane.showMessageDialog(frame, "Hey, something is wrong here! The user(s) account cannot deleted!", "Information Error Message", JOptionPane.showConfirmDialog(frame, "Failure deleted"));
					usernameField.repaint();
				}
				} else if(source == goBackBtn) {
				administratorUI.createAdministratorUI();
				frame.dispose();
			}
		}
		
		/*
		 * Check if input user name has been deleted
		 * from the database.
		 * @return boolean values the user account has been deleted.
		 */
		protected boolean isUserDeleted(String email) {
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
				String sql = "DELETE " +
				"FROM barbers " +
						"WHERE barber_email = ?";

				pstmt = connection.prepareStatement(sql);

				// Bind values into the parameter.
				pstmt.setString(1, email);
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
				sql = "SELECT c.first_name, c.last_name, c.customer_email, b.first_name, b.last_name, b.barber_email " +
				"FROM customers c, barbers b";
				ResultSet rs = pstmt.executeQuery(sql);

				// Extract values from result set.
				while(rs.next()) {
					// Retrieve by column name.
					String cf = rs.getString("first_name");
					String cl = rs.getString("last_name");
					String ce = rs.getString("customer_email");
					String bf = rs.getString("first_name");
					String bl = rs.getString("last_name");
					String be = rs.getString("barber_email");

					// For testing purposes, display result.
					System.out.println("First Name: " + cf);
					System.out.println("LastName: "+ cl);
					System.out.println("Email Address: " + ce);
					System.out.println("First Name: " + bf);
					System.out.println("LastName: "+ bl);
					System.out.println("Email Address: " + be);
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
	}
}
