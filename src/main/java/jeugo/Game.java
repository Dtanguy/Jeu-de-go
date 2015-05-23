package jeugo;

import java.awt.Color;
import java.awt.Font;
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
	private JLabel player;
	private int current_player;
	
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
	     
	     //Player
		 current_player = noir;	
		 Font font = new Font("Vivaldi", Font.BOLD, 30);
		 player = new JLabel("Le joueur aux pierre Noir commence !");
		 player.setFont(font);
		 //player.setForeground(new Color(240, 240, 240));			
		 player.setBounds(60,15,500,50);
		 pan.add(player);
	     
	     //Plateau
	     goban = new Plateau(sx,sy);
	     goban.addMouseListener(this);
	     goban.addMouseMotionListener(this);
	     
	     goban.set_location(50+(19-sx)*goban.cx/2, 100+(19-sy)*goban.cy/2);
		 pan.add(goban);		 
		 
		 frame.setVisible(true);
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
		int x = e.getX() - goban.mx;
		int y =	e.getY() - goban.my;
		goban.set_cursor(x,y);
		System.out.println("Mouse move" + x + " "+y);
		goban.update();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
		int x = e.getX() - goban.mx;
		int y =	e.getY() - goban.my;
		System.out.println("Mouse clik" + x + " "+y);
		goban.find_case(x,y).set_pierre(current_player);
		actualise_player();
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
	
	private void actualise_player(){
		player.setBounds(100,15,500,50);
		if (current_player == noir){
			System.out.println("Joueur noir pose un pion");
			current_player = blanc;
			player.setText("C'est au tour du joueur Blanc.");
		}else if (current_player == blanc){
			System.out.println("Joueur blanc pose un pion");
			current_player = noir;
			player.setText("C'est au tour du joueur Noir.");
		}
	}
	
}
