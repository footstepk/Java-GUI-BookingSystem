package ca.guidbweb.assignment;

/*
 * AdministratorUI is a class that represents administrator(s), it features
 * view booking activities, verify service provider, view complain, delete account  and logout to system.
 * it has a menu bar.
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.*;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.sql.*;

public class AdministratorUI {
	private JFrame frame;
	/** Declare GUI components. */
	private JButton viewBookingActivityBtn; // for view booking activities button.
	private JButton verifyBtn; // for verify service provider button.
	private JButton viewComplainBtn; // for view booking complain button.
	private JButton deleteBtn; // For delete account.
	private JButton logoutBtn; // for log out button.
	private JLabel pageLabel; // for page label.
	private JMenuBar menuBar; // for menu bar.
	private JMenu menu; // for menu.
	private JMenuItem menuItem; // for menu item.

	/** Empty constructor. */
	public AdministratorUI() {
	}

	/*
	 * Create a menu bar and holds its menu and menu items.
	 */ 
	public JMenuBar setJMenuBar() {
		/** Create menu bar, make it has a green colour background. */
		menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(new Color(154, 165, 127));
		menuBar.setPreferredSize(new Dimension(20, 20));

		/** Create menu into the menu bar. */
		menu = new JMenu("Navigation Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("Menu Items");
		menuBar.add(menu);

		/** Create menu items and adds into the menu. */
		menuItem = new JMenuItem();
		//a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menu.add(menuItem);
		return menuBar;		
	}

	/*
	 * Create the label text to describe the administrator page.
	 * @return label the label text to administrator page.
	 */
	public JLabel setAdministratorPageLabel() {
		pageLabel = new JLabel(" ");
		pageLabel.setText("Welcome to Administrator Control");
		pageLabel.setOpaque(true);
		pageLabel.setBackground(new Color(248, 213, 131));
		pageLabel.setPreferredSize(new Dimension(200, 180));

		return pageLabel;
	}

	/*
	 * Create container to holds it components.
	 */
	public JComponent setAdministratorUI() {

		/** Create panel to holds 3 feature buttons. */
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setOpaque(true);

		/** Create four feature buttons. */
		viewBookingActivityBtn = new JButton("View Activities");
		viewBookingActivityBtn.setOpaque(true);

		verifyBtn = new JButton("Verify Account");
		verifyBtn.setOpaque(true);

		viewComplainBtn = new JButton("View Complain");
		viewComplainBtn.setOpaque(true);

		deleteBtn = new JButton("Delete Account");
		deleteBtn.setOpaque(true);

		logoutBtn = new JButton("Log Out");
		logoutBtn.setOpaque(true);
		panel.add(viewBookingActivityBtn);
		panel.add(verifyBtn);
		panel.add(viewComplainBtn);
		panel.add(deleteBtn);
panel.add(logoutBtn);
		// Allocate an instance of this listener when click fire.
		AdministratorButtonListener listener = new AdministratorButtonListener();

		viewBookingActivityBtn.addActionListener(listener);
		verifyBtn.addActionListener(listener);
		viewComplainBtn.addActionListener(listener);
		deleteBtn.addActionListener(listener);
		logoutBtn.addActionListener(listener);
		return panel;
	}

	/*
	 * Create the GUI and show the container. For thread safety,
	 * this method should be invoked from
	 * event-dispatching thread.
	 */
	public void createAdministratorUI() {
		/** Create and setup the GUI container. */
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		/** Setup the menu bar and add the label to the content-pane. */
		frame.add(setJMenuBar());
		frame.add(setAdministratorPageLabel(), BorderLayout.PAGE_START);
		frame.add(setAdministratorUI(), BorderLayout.PAGE_END);

		/** Display to window. */
		frame.setTitle("Automated Booking System -> Administrator Control");
		frame.setSize(500000, 500000);
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * AdministratorButtonListener is instance of inner class of AdministratorUI.
	 */
	private class AdministratorButtonListener implements ActionListener {
		DeleteAccount da = new DeleteAccount();
		private DeleteView deleteView = new DeleteView();
		private AdministratorViewBooking viewBooking = new AdministratorViewBooking();
		AdministratorConfirmBarber confirmBarber = new AdministratorConfirmBarber();

		@Override
		public void actionPerformed(ActionEvent event) {
			// Create source upon which button click on fire.
			JButton source = (JButton) event.getSource();

			if(source == viewBookingActivityBtn) {
				viewBooking.createPageContentPane();
			} else if(source == verifyBtn) {
				confirmBarber.createPageContentPane();
			} else if(source == viewComplainBtn) {
			} else if(source == deleteBtn) {
				deleteView.createDeleteView();
			} else {
			}
		}
	}

	/** For testing purposes, include this main method here! */
	public static void main(String [] args) {
		/** Create instance of customer ui. */
		AdministratorUI adminUI = new AdministratorUI();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				adminUI.createAdministratorUI();
			}
		});
	}
}
