package jeugo;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
	
	private JPanel pan = new JPanel();
	private JButton bouton = new JButton("Mon bouton");
	Plateau goban;
	
	public Fenetre() {
		 this.setTitle("Jeu de Go");
		    this.setSize(800, 500);
		    this.setLocationRelativeTo(null);
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
		    //Ajout du bouton à notre content pane
		    pan.add(bouton);
		    this.setContentPane(pan);
		    this.setVisible(true);
		    
		    goban= new Plateau(5,6);
		    
	}
	
}
