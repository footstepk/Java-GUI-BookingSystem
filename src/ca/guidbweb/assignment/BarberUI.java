package ca.guidbweb.assignment;

/*
 * BarberUI is a class that represents barber(s), it features
 * view booking, set availability, set status and logout to system.
 * it has a menu bar.
 */

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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.*;

public class BarberUI extends JFrame {

	/** Declare GUI components. */
	private JButton viewBookingBtn; // for view booking button.
	private JButton setAvailableBtn; // for set availability  button.
	private JButton setStatusBtn; // for set status of complete or cancel booking  button.
	private JButton logoutBtn; // for log out to the system button.
	private JLabel pageLabel; // for page label.
	private JMenuBar menuBar; // for menu bar.
	private JMenu menu; // for menu.
	private JMenuItem menuItem; // for menu item.

	/** Empty constructor. */
	public BarberUI() {
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
		//		menuItem = new JMenuItem();
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
	 * Create label text to describe the barber page.
	 * @return label the label text.
	 */
	public JLabel setBarberPageLabel() {
		/** Create label to describe the page and set its colour to yellow. */

		pageLabel = new JLabel(" ");
		pageLabel.setText("Welcome to Barber Control");
		pageLabel.setOpaque(true);
		pageLabel.setBackground(new Color(248, 213, 131));
		pageLabel.setPreferredSize(new Dimension(200, 180));

		return pageLabel;
	}

	/*
	 * Create container to holds it components.
	 */
	public JComponent setBarberContentPane() {
		/** Create panel to holds four feature buttons. */

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setOpaque(true);

		/** Create four feature buttons. */
		setAvailableBtn = new JButton("Set Availability");
		setAvailableBtn.setOpaque(true);

		viewBookingBtn = new JButton("View Booking");
		viewBookingBtn.setOpaque(true);

		setStatusBtn = new JButton("Set Booking Status");
		setStatusBtn.setOpaque(true);

		logoutBtn = new JButton("Log Out");
		logoutBtn.setOpaque(true);

		panel.add(setAvailableBtn);
		panel.add(viewBookingBtn);
		panel.add(setStatusBtn);
		panel.add(logoutBtn);

		// Allocate an instance of this inner class listener when click on fire.
		BarberButtonListener listener = new BarberButtonListener();

		setAvailableBtn.addActionListener(listener);
		viewBookingBtn.addActionListener(listener);
		setStatusBtn.addActionListener(listener);
		logoutBtn.addActionListener(listener);
		
		return panel;
	}

	/*
	 * Create the GUI and show the container. For thread safety,
	 * this method should be invoked from
	 * event-dispatching thread.
	 */
	public void createBarberUI() {
		/** Create and setup the GUI container. */
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/** Setup the menu bar and add the label to the content-pane. */
		add(setJMenuBar());
		add(setBarberPageLabel(), BorderLayout.NORTH);
		add(setBarberContentPane(), BorderLayout.PAGE_END);

		/** Display to window. */
		setTitle("Automated Booking System -> Barbers Control");
		pack();
		setVisible(true);
	}

	/*
	 * BarberButtonListener is instance of inner class of BarberUI.
	 */
	private class BarberButtonListener implements ActionListener {
		BarberViewBooking barberView = new BarberViewBooking();

		@Override
		public void actionPerformed(ActionEvent event) {
			// Create source object upon which button click on fire.
			JButton source = (JButton) event.getSource();

			// Check which button caused on fire.
			if(source == setAvailableBtn) {
			} else if(source == viewBookingBtn) {
				barberView.createPageContentPane();
			} else if(source == setStatusBtn) {
			} else if(source == logoutBtn) {
				System.exit(0);
			} else {
				}
		}
	}

	/** For testing purposes, include this main method here! */
	public static void main(String [] args) {
		/** Create instance of customer ui. */
		BarberUI barberUI = new BarberUI();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				barberUI.createBarberUI();
			}
		});
	}

}

