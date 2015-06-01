package jeugo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JComponent;

public class Plateau extends JComponent {
	
	private static final long serialVersionUID = 1L;
	//Attribut
	public Point size;
	// Plateau contenant des pièces
	public Case cases[][];
	//Parametre de tailled es case et des marge
	public int mx = 50;
	public int my = 50;		 
	public int cx = 24;
	public int cy = 22;
	//Couleurs
	private int vide = 0;
	private int blanc = 1;
	private int noir =2;
	//curseur
	public Point cursor;
	
	//Constructeur
	public Plateau(int x, int y) {
		size = new Point(x,y);	
		cursor = new Point(-1,-1);
		initialize();		
	}
	
	//Initialisation
	public void initialize(){
		cases = new Case[size.x+1][size.y+1];
		for (int i=0; i < size.x+1; i++) {
			for (int j=0; j < size.y+1; j++) {
				// On crée une pièce à chaque fois
				//pieces[i][j] = new Piece(rnd(0,2));
				cases[i][j] = new Case(0,i,j);
			}
		}	
		repaint();
	}

	
	private Case start_case;
	public void territoire(Case start){
		
		//System.out.println("la !");
		
		
		
		int c = 0;
		if (start.get_pierre() == blanc){
			c = noir;
		}else if (start.get_pierre() == noir){
			c = blanc;
		}
				
		
		for (int i=0; i < size.x+1; i++) {
			for (int j=0; j < size.y+1; j++) {		
				
				
				if (cases[i][j].get_pierre() == c){										
					actu_lib(cases[i][j],start.get_pierre());						
					System.out.println(c + " " + cases[i][j].get_liberte());
				}
				
			}
		}	
		
		for (int i=0; i < size.x+1; i++) {
			for (int j=0; j < size.y+1; j++) {				
				if (cases[i][j].get_pierre() == c){
					
					

					for (int ii=0; ii < size.x+1; ii++) {
						for (int jj=0; jj < size.y+1; jj++) {	
							cases[ii][jj].unset_marque();
						}
					}
					
					if (territoire_rec(cases[i][j],c) == 0){
						System.out.println(territoire_rec(cases[i][j],c));
						
						for (int ii=0; ii < size.x+1; ii++) {
							for (int jj=0; jj < size.y+1; jj++) {	
								if (cases[ii][jj].get_marque()){
									cases[ii][jj].set_pierre(vide);
								}
							}
						}
									
					}
					
					
				}
			}
		}	
		
		
		
		
	}	
	
	
	public int territoire_rec(Case d,int good){
			
		d.set_marque();
		int lib = d.get_liberte();
		
		if (d.get_position().y>0 && !cases[d.get_position().x][d.get_position().y-1].get_marque() && cases[d.get_position().x][d.get_position().y-1].get_pierre() == good){				
			lib += territoire_rec(cases[d.get_position().x][d.get_position().y-1],good);		
		}
		
		if (d.get_position().x>0 && !cases[d.get_position().x-1][d.get_position().y].get_marque() && cases[d.get_position().x-1][d.get_position().y].get_pierre() == good){	
			lib += territoire_rec(cases[d.get_position().x-1][d.get_position().y],good);	
		}
		
		if (d.get_position().x<size.x+1 && !cases[d.get_position().x+1][d.get_position().y].get_marque() && cases[d.get_position().x+1][d.get_position().y].get_pierre() == good){				
			lib += territoire_rec(cases[d.get_position().x+1][d.get_position().y],good);
		}
		
		if (d.get_position().y<size.y+1 && !cases[d.get_position().x][d.get_position().y+1].get_marque() && cases[d.get_position().x][d.get_position().y+1].get_pierre() == good){				
			lib += territoire_rec(cases[d.get_position().x][d.get_position().y+1],good);
		}	
		
		/*if (lib == 0){
			d.set_pierre(vide);
		}*/
		return lib;	
		
	}
	
	
	public void actu_lib(Case x,int bady){
		
		int lib = 0;
		
		if (x.get_position().y>0 && cases[x.get_position().x][x.get_position().y-1].get_pierre() == vide){				
			lib +=1;		
		}
		
		if (x.get_position().x>0 && cases[x.get_position().x-1][x.get_position().y].get_pierre() == vide){	
			lib +=1;	
		}
		
		if (x.get_position().x<size.x+1 && cases[x.get_position().x+1][x.get_position().y].get_pierre() == vide){				
			lib +=1;
		}
		
		if (x.get_position().y<size.y+1 && cases[x.get_position().x][x.get_position().y+1].get_pierre() == vide){				
			lib +=1;
		}
		
	    x.set_liberte(lib);
		
	}
	
	

	
	//Actualisation du visuel de la piece
	public void update() {		
		repaint();		    
	}
	
	//Fonction de dessin du motif
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);		
				
		 //Texture des cases
		 for (int i=-1; i < size.x+1; i++) {
			for (int j=-1; j < size.y+1; j++) {				 
				 paint_img(g,"ressource/texture_case.png", mx+i*cx, my+j*cy,cx,cy);
			}
		 }	
		 		 
		 //Ligne noire entre chaque case
		 g.setColor(Color.BLACK);
		 for (int i=0; i < size.x+1; i++) {			
				g.drawLine(mx-1+i*cx, my-1, mx-1+i*cx, my-1+size.y*cy);						
		 }
		 for (int j=0; j < size.y+1; j++) {			
			 g.drawLine(mx-1, my-1+j*cy, mx-1+size.x*cx, my-1+j*cy);
		 }
		 
		//Pions
		 for (int i=0; i < size.x+1; i++) {
			for (int j=0; j < size.y+1; j++) {	
				 
				 if(cases[i][j].get_pierre() == vide){
						//Si aucune piece sur la case
				 }else if(cases[i][j].get_pierre() == blanc){
						//Si un blanc sur la case
						paint_img(g,"ressource/blanc.png", mx-(cx/2)-cx+(i+1)*cx, my-(cy/2)-cy+(j+1)*cy, cx-2, cy-2);	
				 }else if(cases[i][j].get_pierre() == noir){						 
						//Si un noir sur la case
						paint_img(g,"ressource/noir.png", mx-(cx/2)-cx+(i+1)*cx, my-(cy/2)-cy+(j+1)*cy, cx-2, cy-2);							 
				 }
				 
			}
		 }

		 //Curseur
		 g.setColor(Color.RED);
		 if(cursor.x != -1 && cursor.y != -1){
			 if(play == 1){
				//Si un blanc sur la case
				paint_img(g,"ressource/blanc.png", mx-(cx/2)-cx+(cursor.x+1)*cx, my-(cy/2)-cy+(cursor.y+1)*cy, cx-2, cy-2);
			 }else if(play == 2){
				//Si un noir sur la case
				paint_img(g,"ressource/noir.png", mx-(cx/2)-cx+(cursor.x+1)*cx, my-(cy/2)-cy+(cursor.y+1)*cy, cx-2, cy-2);							  
			 }			 
		 }		
		
	}
	
	public Point find_point(int x,int y){
		x += cx/2;
		y += cy/2;		
		return new Point((int)((x/cx)),(int)((y/cy)));
	}
	
	public Case find_case(int x,int y){
		Point tmp = find_point(x,y);
		return cases[tmp.x][tmp.y];
	}
	
	//Fonction qui dessine l'image donnée en parametre sur le Jcomponent a l'emplassement et a la size donnée
	private void paint_img(Graphics g,String file, int x, int y , int sx,int sy){
		 Graphics2D g2 = (Graphics2D) g;
		 Image img1 = Toolkit.getDefaultToolkit().getImage(file);
		 g2.drawImage(img1, x, y,sx,sy, this);
		 g2.finalize();
	}

	//fonction qui renvois un nombre aleatoire entre MIN et MAX en parametre
	private int rnd(int min, int max) {
		return (int) (Math.random() * ((max+1) - min)) + min;
	}	
	
	public void set_location(int x,int y){		
		mx = x;
		my = y;
	}
	
	int play;
	public void set_cursor(int x,int y,int p){		
		if (x > -cx && y > -cy && x < size.x*cx+cx/2 & y < size.y*cy+cy/2){
			if (find_case(x,y).get_pierre() == vide){
				cursor =  find_point(x,y);			
				play = p;
			}			
		}else{
			cursor = new Point(-1,-1);
		}
	}
	
	
}
