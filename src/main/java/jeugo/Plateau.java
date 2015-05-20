package jeugo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JComponent;

public class Plateau extends JComponent{
	
	private static final long serialVersionUID = 1L;
	//Attribut
	public Point size;
	// Plateau contenant des pièces
	public Piece[][] plateau;
	
	//Constructeur
	public Plateau(int x, int y) {
		size = new Point(x,y);
		 this.setLocation(x, y);
	        this.setSize(x, y);
		repaint();
	}
	
	//Initialisation
	public void init(){
		for (int i=0; i < size.x; i++) {
			for (int j=0; j < size.y; j++) {
				// On crée une pièce à chaque fois
				plateau[i][j]=new Piece();
			}
		}	
	}
	
	//Actualisation du visuel de la piece
	public void update() {
		repaint();		    
	}
	
	//Fonction de dessin du motif
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    g.setColor(Color.BLUE);
	    g.fillRect(0, 0, 500, 500);	    	       
	}

	
}
