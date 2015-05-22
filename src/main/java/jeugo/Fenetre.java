package jeugo;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre {
	
	private JFrame frame;
	private JPanel pan;
	private JButton bouton = new JButton("Mon bouton");
	public Plateau goban;
	
	public Fenetre() {
				
		 frame = new JFrame("Jeu de Go");	        
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	    
	     frame.setSize(800, 500);
	     frame.setLocationRelativeTo(null);
	     pan = (JPanel) frame.getContentPane();
		 pan.add(bouton);
		 pan.add(new Plateau(30,30));		 
		 frame.setVisible(true);
	}
	
}
