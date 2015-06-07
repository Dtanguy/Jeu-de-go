package jeugo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

//Listener des événements souris
public class Game implements MouseListener,MouseMotionListener{

	//Instance pour le singleton
	private static Game	instance;	
	
	//Object de la partie
	private JFrame frame;
	private JPanel pan;	
	private Plateau goban;	
	private JLabel player;
	private JButton passe;
	private JLabel scoreblanc;
	private JLabel scorenoir;
	
	//Autre informations
	private int current_player;
	private int handicape = 0;
	private double score_blanc;
	private double score_noir;
	private int victoire;
	
	//Couleur des pions	
	private int vide = 0;
	private int blanc = 1;
	private int noir = 2;
	
	public Game(int sx,int sy,int handi){	
		
		 //Creation et paramètrage de la Frame
		 frame = new JFrame("Jeu de Go  | Partie");	        
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	    
	     frame.setSize(590, 650);
	     frame.setLocationRelativeTo(null);
	     
	     //Décoration de la Frame
		 frame.setUndecorated(true);
	     frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
	     
	     frame.setLayout(new BorderLayout());;
	     
	     //Création du Panel
	     pan = (JPanel) frame.getContentPane();	 
	     
	     //On initialise le système de tour des joueurs et on l'affiche
		 current_player = noir;	
		 Font font = new Font("Vivaldi", Font.BOLD, 30);
		 player = new JLabel("",JLabel.CENTER);
		 player.setFont(font);		
		 player.setBounds(35,5,500,80);
		 pan.add(player);
		 
		 handicape = handi;
		 if (handicape > 0){
			 player.setText("<html>Le joueur aux pierre Noir <br> place les " + handicape + " handicape !</html>");
		 } else {
			 player.setText("Le joueur aux pierre Noir commence !");
		 }
	    
		 //On initialise les points
		 score_blanc = 7.5;
		 score_noir = 0;
		 victoire = 0;
		 
	     //Création du plateau au dimention choisie dans le menu
	     goban = new Plateau(sx,sy);
	     goban.addMouseListener(this);
	     goban.addMouseMotionListener(this);	     
	     goban.set_location(60+(19-sx)*goban.cx/2, 100+(19-sy)*goban.cy/2);
		 pan.add(goban);
		 
		 //Score j1
		 scoreblanc = new JLabel("<html>Score des Blancs :<br> " + score_blanc + "  </html>");
		 scoreblanc.setBounds(45, 530, 200, 30);		
		 pan.add(scoreblanc,BorderLayout.SOUTH);
		 
		 //Score j2
		 scorenoir = new JLabel("<html>Score des Noirs :<br> " + score_noir + " </html>");
		 scorenoir.setBounds(430, 530, 200, 30);		
		 pan.add(scorenoir,BorderLayout.SOUTH);
	
		 //Bouton passer son tour
	     passe = new JButton("Passer son tour");	
		 passe.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				if (victoire < 2){
					if (handicape > 1){
						handicape -=1;
						player.setText("<html>Le joueur aux pierre Noir <br> place les " + handicape + " handicape !</html>");
					}else{	
						actualise_player();	
						victoire += 1;
					}
				}
				if (victoire == 10){
					System.exit(0);
				}
				if (victoire == 2){					
					passe.setText("Quitter");
					victoire = 10;
					String info = "La partie est terminée\n"
							    + "La victoire reiven au joueur blanc avec X point.\n"
							    + " felicitation ! :)";
					javax.swing.JOptionPane.showMessageDialog(null,info); 
				}				
			 }
		 });	
		 passe.setBounds(100, 600, 100, 30);
		 pan.add(passe,BorderLayout.SOUTH);
		 
		 //On rend la fenêtre visible
		 frame.setVisible(true);
	}
	
	//Fonction qui gère le systeme de tour des joueurs et on l'affiche
	private void actualise_player(){		
		if (current_player == noir){
			current_player = blanc;
			player.setText("C'est au tour du joueur Blanc.");
		}else if (current_player == blanc){
			current_player = noir;
			player.setText("C'est au tour du joueur Noir.");
		}
	}
	
	//Singleton pour éviter de lancer le jeu plusieurs fois
	public static Game getInstance(int sx,int sy,int handi){
		if (instance == null){
			instance = new Game(sx, sy,handi);
		} else if (instance != null){
			return null;
		}
		return instance;
	}

	//Detection des mouvements de la souris
	public void mouseMoved(MouseEvent e) {		
		//Previsualisation du pion sur la plateau dans la case sous la souris
		if (victoire < 2){
			int x = e.getX() - goban.mx;
			int y =	e.getY() - goban.my;
			goban.set_cursor(x,y,current_player);		
			goban.update();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		// On pause le pion dans la case sous la souris
		if (victoire < 2){
			int x = e.getX() - goban.mx;
		    int y =	e.getY() - goban.my;
			Case tmp = goban.find_case(x,y);
			if (tmp != null){
				if (tmp.get_pierre() == vide){
								
					if (handicape > 1){
						tmp.set_pierre(current_player);
						handicape -=1;
						player.setText("<html>Le joueur aux pierre Noir <br> place les " + handicape + " handicape !</html>");
					}else{				
						tmp.set_pierre(current_player);
						goban.territoire(tmp);
						actualise_player();		
						victoire = 0;
					}
					
				}		
				goban.update();	
			}
		}
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
