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

//Listener des evenement souris
public class Game implements MouseListener,MouseMotionListener{

	//Instance pour le singleton
	private static Game	instance;	
	
	//Object de la partie
	private JFrame frame;
	private JPanel pan;	
	private Plateau goban;	
	private JLabel player;
	private int current_player;
	
	//Couleur des pions
	private int vide = 0;
	private int blanc = 1;
	private int noir = 2;
	
	public Game(int sx,int sy){	
		
		 //Creation et parametrage de la Frame
		 frame = new JFrame("Jeu de Go  | Partie");	        
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	    
	     frame.setSize(590, 600);
	     frame.setLocationRelativeTo(null);
	     
	     //Décoration de la Frame
		 frame.setUndecorated(true);
	     frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
	     
	     //Creation du Panel
	     pan = (JPanel) frame.getContentPane();	 
	     
	     //On initialise le systeme de tour des joueurs et on l'affiche
		 current_player = noir;	
		 Font font = new Font("Vivaldi", Font.BOLD, 30);
		 player = new JLabel("Le joueur aux pierre Noir commence !");
		 player.setFont(font);
		 //player.setForeground(new Color(240, 240, 240));			
		 player.setBounds(60,15,500,50);
		 pan.add(player);
	     
	     //Creation du plateau au dimention choisie dans le menu
	     goban = new Plateau(sx,sy);
	     goban.addMouseListener(this);
	     goban.addMouseMotionListener(this);	     
	     goban.set_location(60+(19-sx)*goban.cx/2, 100+(19-sy)*goban.cy/2);
		 pan.add(goban);		 
		 
		 //On rend la fenetre visible
		 frame.setVisible(true);
	}
	
	//Fonction qui gère le systeme de tour des joueurs et on l'affiche
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
	
	//Singleton pour eviter de lancer le jeux plusieur fois
	public static Game getInstance(int sx,int sy){
		if (instance == null){
			instance = new Game(sx, sy);
		} else if (instance != null){
			return null;
		}
		return instance;
	}

	//Detection des mouvement de la souris
	public void mouseMoved(MouseEvent e) {
		//Previsualisation du pions sur la plateau dans la case sous la souris
		int x = e.getX() - goban.mx;
		int y =	e.getY() - goban.my;
		goban.set_cursor(x,y,current_player);		
		goban.update();
	}
	
	public void mousePressed(MouseEvent e) {
		// On pause le pion dans la case sosu la souris
		int x = e.getX() - goban.mx;
		int y =	e.getY() - goban.my;	
		Case tmp = goban.find_case(x,y);
		if (tmp.get_pierre() == vide){
			tmp.set_pierre(current_player);
			goban.territoire(tmp);
			actualise_player();			
		}		
		goban.update();	
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	
}
