package jeugo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JComponent;

public class Plateau extends JComponent{
	
	private static final long serialVersionUID = 1L;
	//Attribut
	public Point size;
	// Plateau contenant des pièces
	public Piece pieces[][];
	
	//Constructeur
	public Plateau(int x, int y) {
		size = new Point(x,y);
		//this.setLocation(x, y);
	    //this.setSize(x, y);
		init();
		repaint();
	}
	
	//Initialisation
	public void init(){
		pieces = new Piece[size.x+2][size.y+2];
		for (int i=0; i < size.x+2; i++) {
			for (int j=0; j < size.y+2; j++) {
				// On crée une pièce à chaque fois
				pieces[i][j] = new Piece(2);
			}
		}	
	}
	
	//Actualisation du visuel de la piece
	public void update() {
		repaint();		    
	}
	
	//Fonction de dessin du motif
	public void paintComponent(Graphics g) {
				
		 Graphics2D g2 = (Graphics2D) g;
		/* Image img1 = Toolkit.getDefaultToolkit().getImage("ressource/Goban.png");
		 g2.drawImage(img1, 10, 10, this);
		 g2.finalize();
		*/
		 
		int mx = 10;
		int my = 10;
		 
		int cx=20;
		int cy=20;
		 
		 for (int i=0; i < size.x; i++) {
			for (int j=0; j < size.y; j++) {				
				 Image img1 = Toolkit.getDefaultToolkit().getImage("ressource/texture_case.png");
				 g2.drawImage(img1, mx+i*cx, my+j*cy,cx,cy, this);
				 g2.finalize();
			}
		}	
		
		 g.setColor(Color.BLACK);
		 for (int i=0; i <= size.x; i++) {
			for (int j=0; j <= size.y; j++) {				
				 g.drawLine(mx-1+i*cx, my-1, mx-1+i*cx, my-1+size.y*cy);
				 g.drawLine(mx-1, my-1+j*cy, mx-1+size.x*cx, my-1+j*cy);
			}
		}
		
		 for (int i=0; i < size.x+2; i++) {
				for (int j=0; j < size.y+2; j++) {				
					
					if(pieces[i][j].state == 0){
						
					}else if(pieces[i][j].state == 1){
						 g.setColor(Color.WHITE);
						 g.fillOval(mx-cx+i*cx, my-cy+j*cy, cx-2, cy-2);
					}else if(pieces[i][j].state == 2){
						 g.setColor(Color.BLACK);
						 g.fillOval(mx-cx+i*cx, my-cy+j*cy, cx-2, cy-2);
					}


			}
		}
	 
		 
		 /*
		super.paintComponent(g);
	    g.setColor(Color.BLUE);
	    g.fillRect(0, 0, 500, 500);*/	    	       
	}

	
}
