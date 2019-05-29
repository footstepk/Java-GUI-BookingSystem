package ca.guidbweb.assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorConfirmBarber {
	/* Declare the GUI instances. */
	private JFrame frame;
	private JPanel panel;
	private JButton confirmBarberBtn, goBackBtn;
	private JLabel pageLabel, displayLabel;

	/* Constructors to create the GUI component and the event handlers. */
	public AdministratorConfirmBarber() {
	}

	/*
	 * Create the window label text to
	 * describe the window frame.
	 * @return label text the window frame description text.
	 */
	public JLabel setPageLabel() {
		final String page_text = "You can confirm all the  barbers here.";
		pageLabel = new JLabel();
		pageLabel.setText(page_text);
		pageLabel.setOpaque(true);
		pageLabel.setBackground(new Color(120, 120, 120));
		pageLabel.setFont(new Font("Courier New", Font.BOLD, 14));
		pageLabel.setForeground(Color.GRAY);
		pageLabel.setPreferredSize(new Dimension(100, 100));
		return pageLabel;
	}

	/*
	 * Create the GUI component and
	 * add it into the content pane.
	 * @return component the GUI content pane.
	 */
	public JComponent setPageContentPane() {
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setOpaque(true);

		// Add a result label text here.
		displayLabel = new JLabel();
		displayLabel.setText("The booking were made by customers and barbers are here below");
		displayLabel.setOpaque(true);
		displayLabel.setBackground(new Color(120, 120, 120));
		displayLabel.setFont(new Font("Courier New", Font.BOLD, 14));
		displayLabel.setForeground(Color.GRAY);
		displayLabel.setPreferredSize(new Dimension(100, 100));

		panel.add(displayLabel);

		// Setup the show booking button.
		confirmBarberBtn = new JButton("Confirm Booking");
		confirmBarberBtn.setBackground(new Color(231, 164, 123));
		confirmBarberBtn.setPreferredSize(new Dimension(20, 20));

		goBackBtn = new JButton("Return to Home");
		goBackBtn.setBackground(new Color(231, 164, 123));
		goBackBtn.setPreferredSize(new Dimension(20, 20));

		panel.add(confirmBarberBtn);
		panel.add(goBackBtn);	        

		// Allocate an instance of the inner class upon click cause on fire event.
		ConfirmBarberButtonListener listener = new ConfirmBarberButtonListener();
		confirmBarberBtn.addActionListener(listener);
		goBackBtn.addActionListener(listener);
		return panel;
	}

	/*
	 * Create the GUI component and add it
	 * into the frame.
	 */ 	 
	public void createPageContentPane() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Setup the label text.
		frame.add(setPageLabel(), BorderLayout.PAGE_START);

		// Add all content pane here.
		frame.add(setPageContentPane(), BorderLayout.PAGE_END);

		// Display the result.
		frame.setTitle("Automated Booking System - Display View Booking");
		frame.setSize(350000,  350000);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * ConfirmBarberButtonListener is an instance of the inner class of AdministratorConfirmBarber
	 * It implements the event listener.
	 */
	private class ConfirmBarberButtonListener implements ActionListener {
		AdministratorUI administratorUI = new AdministratorUI();

		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();

			if(source == confirmBarberBtn) {
				if(isConfirmed() ) {
					JOptionPane.showMessageDialog(frame, "Success! You had confirmed the barber");
			} else {
				JOptionPane.showMessageDialog(frame, "Invalid barber in the system, try again");
			}
		} else if(source == goBackBtn) {
			// return to administrator.
			administratorUI.createAdministratorUI();
		} else {
			JOptionPane.showMessageDialog(frame, "System errored! Try again.");
		}
	}
	}
/*
 * check if barber is confirmed.
 */
public boolean isConfirmed() {
	//Initial the database connection and its statement.
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	Connection connection = null;
	Statement stmt = null;
	boolean result = false;
	try {
		// For testing purposes, this should be only observation.
		System.out.println("Connecting.. to the database");
		Class.forName(JDBC_DRIVER);
		connection = MyJDBCUtil.getConnection();

		// Create the prepare statement.
		System.out.println("Creating statement...");
		String sql = "UPDATE barbers SET registered = true";
				
		stmt = connection.createStatement();
        
		// Let's update the row.
		int countRows = stmt.executeUpdate(sql);
		if(countRows > 0) {
			result = true;// return boolean values if column is greater than 1
		} else {
			result = false; // duplicated entries
		}
		
		/** For testing purposes, this should for observation only!. */
		System.out.println("Rows impacted : " + countRows);
		// Clean the environment.
	stmt.close(); connection.close();

	} catch(SQLException ex) {
		System.err.println(ex.getMessage());
	} catch(Exception ex) {
		ex.printStackTrace();
	} finally {
		try {
			if(stmt != null) {
				stmt.close();
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
