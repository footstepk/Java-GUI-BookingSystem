package ca.guidbweb.assignment;

/*
 * CustomerUI is a class that represents customer(s), it features
 * view booking, make booking and add complain
 * it has a menu bar.
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.*;

public class CustomerUI extends JFrame {

	/** Declare GUI components. */
	private JButton viewBookingBtn; // for view booking button.
	private JButton makeBookingBtn; // for make booking button.
	private JButton complainBtn; // for complain button.
	private JButton logoutBtn; // for log out to the system.
	private JLabel pageLabel; // for page label.
	private JMenuBar menuBar; // for menu bar.
	private JMenu menu; // for menu.
	private JMenuItem menuItem; // for menu item.

	/** Empty constructor. */
	public CustomerUI() {
	}

	/*
	 * Create a menu bar and holds its menu and menu items.
	 */ 
	public JMenuBar setJMenuBar() {
		/** Create menu bar, make it has a green colour background. */
		menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(new Color(154, 165, 127));
		menuBar.setPreferredSize(new Dimension(200, 20));

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
	 * Create the label text to describe the customer page.
	 * @return label the label text to describe the customer page.
	 */
	public JLabel setCustomerPageLabel() {
		/** Create label to describe the page and set its colour to yellow. */

		pageLabel = new JLabel(" ");
		pageLabel.setOpaque(true);
		pageLabel.setText("Welcome to Customer Control");
		pageLabel.setBackground(new Color(248, 213, 131));
		pageLabel.setPreferredSize(new Dimension(200, 180));

		return pageLabel;
	}

	/*
	 * Create container to holds it components.
	 * @return panel the GUI components into the panel content pane.
	 */
	public JComponent setCustomerContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setOpaque(true);

		/** Create panel to holds four feature buttons. */

		/** Create four feature buttons. */
		makeBookingBtn = new JButton("Make Booking");
		makeBookingBtn.setOpaque(true);

		viewBookingBtn = new JButton("View Booking");
		viewBookingBtn.setOpaque(true);

		complainBtn = new JButton("Complain");
		complainBtn.setOpaque(true);

		logoutBtn = new JButton("Log Out");
		logoutBtn.setOpaque(true);

		panel.add(makeBookingBtn);
		panel.add(viewBookingBtn);
		panel.add(complainBtn);
		panel.add(logoutBtn);

		// Allocate an instance of the inner class when click on fire.
		CustomerButtonListener listener = new CustomerButtonListener();

		makeBookingBtn.addActionListener(listener);
		viewBookingBtn.addActionListener(listener);
		complainBtn.addActionListener(listener);
		logoutBtn.addActionListener(listener);

		return panel;
	}

	/*
	 * Create the GUI and show the container. For thread safety,
	 * this method should be invoked from
	 * event-dispatching thread.
	 */
	public void createCustomerUI() {
		/** Create and setup the GUI container. */
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/** Setup the menu bar and add the label to the content-pane. */
		add(setJMenuBar());
		add(setCustomerPageLabel(),  BorderLayout.PAGE_START);
		add(setCustomerContentPane(), BorderLayout.PAGE_END);

		/** Display to window. */
		setTitle("Automated Booking System -> Customer Control");
		pack();
		setVisible(true);
	}

	/*
	 * CustomerButtonListener is an instance of the inner class of CustomerUI.
	 * It implements the ActionListener.
	 */
	private class CustomerButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			// Create the button instance for when click on fire.
			JButton source = (JButton) event.getSource();

			// Check which button caused when click on fire.
			if(source == makeBookingBtn) {
			} else if(source == viewBookingBtn) {
			} else if(source == complainBtn) {
			} else if(source == logoutBtn) {
				System.exit(0);
			} else {
			}
		}
	}

/** For testing purposes, include this main method here! */
public static void main(String [] args) {
	/** Create instance of customer ui. */
	CustomerUI customerUI = new CustomerUI();
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
			customerUI.createCustomerUI();
		}
	});
}

}
