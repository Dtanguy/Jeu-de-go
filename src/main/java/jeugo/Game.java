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
	public static double score_blanc;
	public static double score_noir;
	private int victoire;
	private int type;
	
	//Couleur des pions	
	private int vide = 0;
	private int blanc = 1;
	private int noir = 2;
	
	//Joueur
	private Joueur joueur1;
	private Joueur joueur2;
	
	public Game(int sx,int sy,int handi,int typ){	
		Initialisation(sx,sy,handi);		
		type = typ;
		
		if (type == 0){
			joueur1 = new Joueur(noir,false);
			joueur2 = new Joueur(blanc,false);
		}else if (type == 1){
			joueur1 = new Joueur(noir,false);
			joueur2 = new Joueur(blanc,true);			
		}		
	
	}
	
	public Game(String file){			
		Point size = load_plateau(file);		
		Initialisation(size.x-1,size.y-1,0);		
		load_pierre(file,size.x,size.y);
		
		joueur1 = new Joueur(noir,false);
		joueur2 = new Joueur(blanc,false);
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
	     	
	     //Création du Panel
	     pan = (JPanel) frame.getContentPane();	 	    
	    	     
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
					String info;
					if (score_blanc > score_noir){
						info = "La partie est terminée\n"
							    + "La victoire revien au joueur blanc avec "+ score_blanc +" point.\n"
							    + " felicitation ! :)";
					}else{
						info = "La partie est terminée\n"
							    + "La victoire revien au joueur noir avec "+ score_noir +" point.\n"
							    + " felicitation ! :)";
					}
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
	
	private void IA(){
			
		Case tmp;
		do{							
			tmp = goban.cases[rnd(0, goban.size.x)][rnd(0, goban.size.y)];
		}while(tmp.get_pierre() != vide);
				
		try{
		
			Case min_case = tmp;
			int x = -1;
			int y = -1;
			
			for (int i=0; i < goban.size.x+1; i++) {
				for (int j=0; j < goban.size.y+1; j++) {
					if (goban.cases[i][j].get_pierre() == noir){	
							
						goban.actu_lib(goban.cases[i][j]);
							
						if (goban.cases[i][j].get_liberte() < min_case.get_liberte() && goban.cases[i][j].get_liberte() > 0){
							min_case = goban.cases[i][j];
							x = i;
							y = j;
						}						
							
					}
				}
			}	
			
			System.out.println(x + " " + y + " " + goban.cases[x][y].get_liberte());
				
			
			if(x-1 > 0 && goban.cases[x-1][y].get_pierre() == vide){	
				goban.actu_lib(goban.cases[x-1][y]);
				if (goban.cases[x-1][y].get_liberte() > 0){			
					tmp = goban.cases[x-1][y];
				}
			}else if(x+1 < goban.size.x+1 && goban.cases[x+1][y].get_pierre() == vide){	
				goban.actu_lib(goban.cases[x+1][y]);
				if (goban.cases[x+1][y].get_liberte() > 0){			
					tmp = goban.cases[x+1][y];
				}			
			}else if(y-1 > 0 && goban.cases[x][y-1].get_pierre() == vide){	
				goban.actu_lib(goban.cases[x][y-1]);
				if (goban.cases[x][y-1].get_liberte() > 0){			
					tmp = goban.cases[x][y-1];
				}	
			}else if(y+1 < goban.size.y+1 && goban.cases[x][y+1].get_pierre() == vide){
				goban.actu_lib(goban.cases[x][y+1]);
				if (goban.cases[x][y+1].get_liberte() > 0){			
					tmp = goban.cases[x][y+1];
				}
			}else if(x-1 > 0 && y-1 > 0 && goban.cases[x-1][y-1].get_pierre() == vide){			
				goban.actu_lib(goban.cases[x-1][y-1]);
				if (goban.cases[x-1][y-1].get_liberte() > 0){			
					tmp = goban.cases[x-1][y-1];
				}
			}else if(x-1 > 0 && y+1 < goban.size.y+1 && goban.cases[x-1][y+1].get_pierre() == vide){	
				goban.actu_lib(goban.cases[x-1][y+1]);
				if (goban.cases[x-1][y+1].get_liberte() > 0){			
					tmp = goban.cases[x-1][y+1];
				}
			}else if(x+1 < goban.size.x+1 && y+1 < goban.size.y+1 && goban.cases[x+1][y-1].get_pierre() == vide){	
				goban.actu_lib(goban.cases[x+1][y-1]);
				if (goban.cases[x+1][y-1].get_liberte() > 0){			
					tmp = goban.cases[x+1][y-1];
				}			
			}else if(x+1 < goban.size.x+1 && y+1 < goban.size.y+1 && goban.cases[x+1][y+1].get_pierre() == vide){			
				goban.actu_lib(goban.cases[x+1][y+1]);
				if (goban.cases[x+1][y+1].get_liberte() > 0){			
					tmp = goban.cases[x+1][y+1];
				}	
			}
		
		} catch (Exception e){
			e.printStackTrace();
		}
				
		if (tmp.get_pierre() == vide){
			tmp.set_pierre(current_player);
			goban.territoire(tmp);
			actualise_player();		
			victoire = 0;
			score_blanc++;
		}					
				
		goban.update();	
		
	}
	
	//Fonction qui gère le systeme de tour des joueurs et on l'affiche
	private void actualise_player(){				
		
		if (current_player == noir){
			current_player = blanc;
			player.setText("C'est au tour du joueur Blanc.");
			
			//IA
			if (joueur2.get_color() == blanc && joueur2.get_type()){
				IA();
			}
						
		}else if (current_player == blanc){
			current_player = noir;
			player.setText("C'est au tour du joueur Noir.");
		}
		
		
		 //Score j1
		 scoreblanc.setText("<html>Score des Blancs :<br> " + score_blanc + "  </html>");		
		 //Score j2
		 scorenoir.setText("<html>Score des Noirs :<br> " + score_noir + " </html>");		
		
		
	}
	
	//Detection des mouvements de la souris
	public void mouseMoved(MouseEvent e) {		
		if (joueur1.get_color() == current_player && joueur1.get_type()){
		}else if (joueur2.get_color() == current_player && joueur2.get_type()){					
		}else{
		
			//Previsualisation du pion sur la plateau dans la case sous la souris
			if (victoire < 2){
				int x = e.getX() - goban.mx;
				int y =	e.getY() - goban.my;
				goban.set_cursor(x,y,current_player);		
				goban.update();
			}
			
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
			
		if (joueur1.get_color() == current_player && joueur1.get_type()){
			
		}else if (joueur2.get_color() == current_player && joueur2.get_type()){
			
		}else{
					
		
				// On pause le pion dans la case sous la souris
				if (victoire < 2){
					int x = e.getX() - goban.mx;
				    int y =	e.getY() - goban.my;
					Case tmp = goban.find_case(x,y);
					if (tmp != null){
						if (tmp.get_pierre() == vide){
							
							goban.actu_lib(tmp);
							if (tmp.get_liberte() > 0){
										
								if (handicape > 1){
									tmp.set_pierre(current_player);
									handicape -=1;
									player.setText("<html>Le joueur aux pierre Noir <br> place les " + handicape + " handicape !</html>");
								}else{		
									
									if (current_player == blanc){
										score_blanc++;
									}else if (current_player == noir){
										score_noir++;
									}
									
									tmp.set_pierre(current_player);
									goban.territoire(tmp);
									actualise_player();		
									victoire = 0;
								}
								
							}
							
						}		
						goban.update();	
					}
				}
		
		}
	}
	
	// nombre aleatoire entre MIN et MAx
	private int rnd(int min, int max) {
		return (int) (Math.random() * (max - min)) + min;
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
