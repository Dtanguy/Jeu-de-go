package jeugo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

public class Game implements MouseListener,MouseMotionListener{

	private static Game	instance;	
	
	private JFrame frame;
	private JPanel pan;	
	private Plateau goban;	
	private JLabel current_player;
	
	//Couleurs
	private int vide = 0;
	private int blanc = 1;
	private int noir = 2;
	
	public Game(int sx,int sy){		
		 //Frame
		 frame = new JFrame("Jeu de Go  | Partie");	        
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	    
	     frame.setSize(590, 660);
	     frame.setLocationRelativeTo(null);
	     
	     //Decoration
		 frame.setUndecorated(true);
	     frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
	     
	     //Panel
	     pan = (JPanel) frame.getContentPane();	    
	     
	     //Plateau
	     goban = new Plateau(sx,sy);
	     goban.addMouseListener(this);
	     goban.addMouseMotionListener(this);
	     goban.set_location(50, 100);
		 pan.add(goban);		 
		 frame.setVisible(true);
		 
		// current_player = 
	}
	
	//Singleton pour eviter de le lancer plusiur fois
	public static Game getInstance(int sx,int sy){
		if (instance == null){
			instance = new Game(sx, sy);
		} else if (instance != null){
			return null;
		}
		return instance;
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("clik");
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
