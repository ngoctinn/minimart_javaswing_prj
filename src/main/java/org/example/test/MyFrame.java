package org.example.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MyFrame extends JFrame implements ActionListener {

	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu editMenu;
	JMenu helpMenu;
	JMenuItem loadItem;
	JMenuItem saveItem;
	JMenuItem exitItem;







	
	MyFrame(){		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setLayout(new FlowLayout());
		setUndecorated(false);

		
		// Create MenuBar
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		helpMenu = new JMenu("Help");


		
		loadItem = new JMenuItem("Load");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");

		FlatSVGIcon loadIcon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 16, 16);
		FlatSVGIcon saveIcon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 16, 16);
		FlatSVGIcon exitIcon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 16, 16);








		
		loadItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);

		loadItem.setIcon(loadIcon);
		saveItem.setIcon(saveIcon);
		exitItem.setIcon(exitIcon);

		fileMenu.setIcon(loadIcon);
		editMenu.setIcon(saveIcon);
		helpMenu.setIcon(exitIcon);


		fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
		editMenu.setMnemonic(KeyEvent.VK_E); // Alt + e for edit
		helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + h for help
		loadItem.setMnemonic(KeyEvent.VK_L); // l for load
		saveItem.setMnemonic(KeyEvent.VK_S); // s for save
		exitItem.setMnemonic(KeyEvent.VK_E); // e for exit
		
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);

		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		this.setJMenuBar(menuBar);
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==loadItem) {
			System.out.println("*beep boop* you loaded a file");
		}
		if(e.getSource()==saveItem) {
			System.out.println("*beep boop* you saved a file");
		}
		if(e.getSource()==exitItem) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		try
		{
			// Intellij IDEA
			UIManager.setLookAndFeel(new FlatMacLightLaf());

			// FlatLaf style
			// TitlePane color
			UIManager.put("RootPane.background", new Color(0, 0, 225));
			UIManager.put("TitlePane.background", new Color(0, 0, 225));
			UIManager.put("TitlePane.foreground", Color.WHITE);
			UIManager.put("TitlePane.borderColor", new Color(0, 10, 0));
			//Menu
			UIManager.put("MenuBar.foreground", Color.WHITE);
			UIManager.put("MenuBar.selectionForeground", Color.white);

			//JMenuItem
			UIManager.put("MenuItem.foreground", Color.WHITE);
			UIManager.put("MenuItem.selectionBackground", new Color(0, 0, 180));

			//Popup Menu
			UIManager.put("PopupMenu.background", Color.blue);



			new MyFrame();

			//Menu



		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
