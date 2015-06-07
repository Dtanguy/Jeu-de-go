package jeugo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

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
	private JButton save;
	private JLabel scoreblanc;
	private JLabel scorenoir;
	
	//Autre informations
	private int current_player;
	private int handicape = 0;
	private double score_blanc;
	private double score_noir;
	private int victoire;
	private int type;
	
	//Couleur des pions	
	private int vide = 0;
	private int blanc = 1;
	private int noir = 2;
	
	public Game(int sx,int sy,int handi,int type){	
		Initialisation(sx,sy,handi);		
		type = this.type;
	}
	
	public Game(String file){			
		Point size = load_plateau(file);		
		Initialisation(size.x-1,size.y-1,0);		
		load_pierre(file,size.x,size.y);
	}
	
	
	public Point load_plateau(String file){
		
		//On cherche les information sur la sauvegarde
		int sx=0;
		int sy=0; 		
			
		File partie = new File(file);
		Scanner scanner;	
		
		//nombre de ligne sy et d ecolone sx
		try {			
			
			scanner = new Scanner(partie);
			sx = scanner.nextLine().length();
			 while (scanner.hasNextLine()) {
				 sy +=1; 
				 scanner.nextLine();
           }
		} catch (Exception e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null,	"Le fichier partie.txt ne peut être ouvert :'(");
			System.exit(0);
		}
				
		return new Point(sx,sy);
		
	}
	
	public void load_pierre(String file,int sx, int sy){
		
		File partie = new File(file);
		Scanner scanner;	
		
		//On lit les valeurs des case
		try {
			
			scanner = new Scanner(partie);
			
			for (int i=0; i < sy; i++) {
				
				 
				 String tmp = scanner.nextLine();
				 
				 for (int j=0; j < tmp.length();j++){
					
					 if(tmp.charAt(j) == 'b'){
						 goban.cases[j][i].set_pierre(blanc);
					 }else if(tmp.charAt(j) == 'N'){
						 goban.cases[j][i].set_pierre(noir);
					 }else if(tmp.charAt(j) == 32){
						 goban.cases[j][i].set_pierre(vide);
					 }
                     
                 }
				
			}	
			
			char tmp[] = scanner.next().toCharArray();			
		
			if(tmp[0] == 'b'){
				current_player = blanc;
				player.setText("C'est au tour du joueur Blanc.");
			}else if(tmp[0] == 'N'){
				current_player = noir;
				player.setText("C'est au tour du joueur Noir.");
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null,	"Le fichier " + file +" ne peut être ouvert :'(");
			System.exit(0);
		}
	}

	public void save(String file){
		
		try{
			
			FileWriter scoreFile = new FileWriter(file, false);
			
			for (int i=0; i < goban.size.y+1; i++) {
				for (int j=0; j < goban.size.x+1; j++) {
					
					if(goban.cases[j][i].get_pierre() == vide ){
						scoreFile.write(' ');						
					}else if(goban.cases[j][i].get_pierre() == blanc ){
						scoreFile.write('b');								
					}else if(goban.cases[j][i].get_pierre() == noir ){
						scoreFile.write('N');								
					}	
					
				}
				scoreFile.write("\n");
			}
			
			
			if(current_player == blanc ){
				scoreFile.write('b');								
			}else if(current_player == noir ){
				scoreFile.write('N');								
			}	
			
			scoreFile.close();			

		} catch (Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null,	"Impossible d'écrire dans " + file +" :'(");
			System.exit(0);
		}
		
	}
	
	public void Initialisation(int sx,int sy,int handi){
		
		 //Creation et paramètrage de la Frame
		 frame = new JFrame("Jeu de Go  | Partie");	        
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	    
	     frame.setSize(590, 670);
	     frame.setLocationRelativeTo(null);
	     
	     //Décoration de la Frame
		 frame.setUndecorated(true);
	     frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
	     
	    // frame.setLayout(new BorderLayout());
	     //Création du Panel
	     pan = (JPanel) frame.getContentPane();	 	    
	    // pan.setLayout(new FlowLayout());
	     
	     //On initialise le système de tour des joueurs et on l'affiche
		 current_player = noir;	
		 Font font = new Font("Vivaldi", Font.BOLD, 30);
		 player = new JLabel("",JLabel.CENTER);
		 player.setFont(font);		
		 player.setBounds(35,30,500,80);
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
		 scoreblanc.setBounds(45, 565, 200, 30);		
		 pan.add(scoreblanc,BorderLayout.SOUTH);
		 
		 //Score j2
		 scorenoir = new JLabel("<html>Score des Noirs :<br> " + score_noir + " </html>");
		 scorenoir.setBounds(430, 565, 200, 30);		
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
		 passe.setBounds(0, 600, 100, 30);
		 pan.add(passe,BorderLayout.SOUTH);
		 
		 //Bouton sauvegarder
	     save = new JButton("Sauvegarder dans partie.txt");	
	     save.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 save("ressource/partie.txt");
			 }
		 });	
	     save.setBounds(430, 530, 200, 30);	
		 frame.add(save,BorderLayout.NORTH);
		 
		 
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
	public static Game getInstance(int sx,int sy,int handi,int type){
		if (instance == null){
			instance = new Game(sx, sy,handi,type);
		} else if (instance != null){
			return null;
		}
		return instance;
	}
	public static Game getInstance(String file){
		if (instance == null){
			instance = new Game(file);
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
