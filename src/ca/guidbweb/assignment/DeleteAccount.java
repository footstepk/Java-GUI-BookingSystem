package ca.guidbweb.assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DeleteAccount extends JFrame {
	
	/** Declare the GUI component instances. */
	private JLabel l1; // for user name label text.
	private JButton deleteAccountBtn; // for delete account button.
	private JButton goBackBtn; // for go back to panel.
	private JTextField t1; // for user name entry field.
	
	public DeleteAccount() {
	}
	
	/*
	 * Upon click on delete button, this method should delete a customer,
	 * or barber account from the system.
	 */
	public void createDeleteContentPane() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create the GUI component and show it.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, 10, 10));
		
		l1 = new JLabel();
		l1.setOpaque(true);
		l1.setText("Enter username: ");
		l1.setToolTipText("Account username (email) associated to be deleted");
		
		t1 = new JTextField(40);
		t1.setOpaque(true);
		t1.setEditable(true);
		
		l1.setLabelFor(t1);
		
		deleteAccountBtn = new JButton("Delete Account");
		deleteAccountBtn.setOpaque(true);
		
		goBackBtn = new JButton("Return to Home");
		deleteAccountBtn.setOpaque(true);
		
// Allocate an instance of the inner class of DeleteAccount.		
		DeleteButtonListener listener = new DeleteButtonListener();
		deleteAccountBtn.addActionListener(listener);
		goBackBtn.addActionListener(listener);
		
		// Add it in to the container.
		panel.add(l1);
		panel.add(t1);
		panel.add(deleteAccountBtn);
		panel.add(goBackBtn);
		add(panel);
		
		// display to the screen
		pack();
		setTitle("Administrator Control -> Delete Account");
		setVisible(true);
	}
	
	/*
	 * DeleteButtonListener is an inner class of the DeleteAccount instance.
	 * It implements the ActionListener of listener.
	 */
	private class DeleteButtonListener implements ActionListener {
		/* Declare some of instances class. */
		AdministratorUI administratorUI = new AdministratorUI();
		DeleteAccount da = new DeleteAccount();

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
			String input = t1.getText();
			
			if(source == deleteAccountBtn) {
				if(getDeleteAccount(input)) {
					JOptionPane.showMessageDialog(da, "Success! The associated account has been deleted");
				} else {
					JOptionPane.showMessageDialog(da, "Errored! The associated account cannot be deleted");
				}
				} else if(source == goBackBtn) {
					administratorUI.createAdministratorUI(); // go back to home panel.
				} else {
					JOptionPane.showMessageDialog(da, "Errored! The return home cannot be made");
				}
				}
			}
		
		/* This method to determine to delete an account from either
		 * customer(s) or barber(s) account.
		 * @return boolean value the success deleted an account.
		 */
		public boolean getDeleteAccount(String email) {
			//Initials the database connection and its statement.
			Connection connection = null;
			PreparedStatement pstmt = null;
			boolean result = false;
			try {
				System.out.println("Connecting.. to the database");
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = MyJDBCUtil.getConnection();
				
				// Create the prepare statement.
				System.out.println("Creating statement...");
				String sql = "DELETE C.*, B.* " +
				"FROM customers C LEFT JOIN barbers B " +
						"ON C.customer_email = B.barber_email " +
						"WHERE C.customer_email = ? OR B.barber_email = ?";
						
				pstmt = connection.prepareStatement(sql);

				// Bind values into the parameter.
				pstmt.setString(1, email);
				
				// Let's update the row.
				
			      int rows = pstmt.executeUpdate();
			      if(rows == 0) {
			    	  result = true;
			      } else {
			    	  result = false;
			      }
			      System.out.println("Rows impacted : " + rows );
			      // Let us select all the records and display them.
	sql = "SELECT C.first_name, C.last_name, C.customer_email, B.first_name, B.last_name, B.barber_email " +
			      "FROM customers C INNER JOIN barbers B" +
			"ON C.customer_email = B.customer_email";
	ResultSet rs = pstmt.executeQuery(sql);

	// Extract values from result set.
	while(rs.next()) {
		// Retrieve by column name.
		String f = rs.getString("first_name");
		String l = rs.getString("last_name");
		String e = rs.getString("customer_email");
		
		// For testing purposes, display result.
		System.out.println("First Name: " + f);
		System.out.println("LastName: "+ l);
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

	}

