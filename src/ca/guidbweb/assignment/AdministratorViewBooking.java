package ca.guidbweb.assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class AdministratorViewBooking {
	/* Declare the GUI instances. */
	private JFrame frame;
	private JPanel panel;
	private JButton showBookingBtn, goBackBtn;
	private JLabel pageLabel, displayLabel;
	private JTextArea textArea;
	
	/* Construct the GUI component and the event handlers. */
	public AdministratorViewBooking() {
	}
	
	/*
	 * Create the window label text to
	 * describe the window frame.
	 * @return label text the window fram description text.
	 */
	public JLabel setPageLabel() {
		final String page_text = "You can view all the barber and customer bookings here.";
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

		// Setup the text area to get the result field.
		textArea = new JTextArea();
		textArea.setOpaque(true);
		textArea.setEditable(false);
		 JScrollPane scrollPane = new JScrollPane(textArea);
		 
	        //Add Components to this panel.
	        GridBagConstraints c = new GridBagConstraints();
	        c.gridwidth = GridBagConstraints.REMAINDER;
	 
	        c.fill = GridBagConstraints.HORIZONTAL;
	        panel.add(textArea, c);
	 
	        c.fill = GridBagConstraints.BOTH;
	        c.weightx = 1.0;
	        c.weighty = 1.0;
	        panel.add(scrollPane, c);

	        // Setup the show booking button.
	        showBookingBtn = new JButton("Show Booking");
	        showBookingBtn.setBackground(new Color(231, 164, 123));
	        showBookingBtn.setPreferredSize(new Dimension(20, 20));
	       
	        goBackBtn = new JButton("Return to Home");
	        goBackBtn.setBackground(new Color(231, 164, 123));
	        goBackBtn.setPreferredSize(new Dimension(20, 20));
	        
	        panel.add(showBookingBtn);
panel.add(goBackBtn);	        

// Allocate an instance of the inner class upon click cause on fire event.
	        ViewBookingButtonListener listener = new ViewBookingButtonListener();
	        showBookingBtn.addActionListener(listener);
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
	 * ViewBookingButtonListener is an instance of the inner class of AdministratorViewBooking
	 * It implements the event listener.
	 */
	private class ViewBookingButtonListener implements ActionListener {
		AdministratorUI administratorUI = new AdministratorUI();
		/** Establishing the SQL connection instances. */
		
		@Override
		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton) event.getSource();
String myresult = "";			
			if(source == showBookingBtn) {
				if(isBooking()) {
				textArea.setText(textArea.getText());
			} else {
				JOptionPane.showMessageDialog(frame, "There were no booking made by customers and barbers yet", "Alert: Message Information", JOptionPane.showInternalConfirmDialog(frame, "GO Back YES_NO"));
			}
		} else if(source == goBackBtn) {
			administratorUI.createAdministratorUI();
		} else {
			JOptionPane.showMessageDialog(frame, "Errored! Try again, system error!");
		}
	}
	
	/*
	 * Check if any booking made from database.
	 */
	public boolean isBooking() {
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
			String sql = "SELECT c.first_name, c.current_booked as 'Customer booked', b.first_name, b.last_name " +
			"FROM barbers b, customers c " +
					"WHERE b.customer_email = c.customer_email;";
					
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
			// Let us select all the records and display them.
			ResultSet rs = stmt.executeQuery(sql);

			// Extract values from result set.
			while(rs.next()) {
				// Retrieve by column name.
				String f = rs.getString("first_name");
				String date = rs.getString("current_booke");
				String first = rs.getString("first_name");
									String last = rs.getString("last_name");

									// get display to text area.
									//text = f + ", " + date + ", " + first + ", " + last + "\n";
									textArea.setText(f);
									textArea.setText(date);
									textArea.setText(first);
									textArea.setText(last);
									
				System.out.println("First Name: " + f);
				System.out.println("Date: " + date);
				System.out.println("first name: " + first);
									System.out.println("last name: " + last);
				
			}
			// Clean the environment.
			rs.close(); stmt.close(); connection.close();

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
}	