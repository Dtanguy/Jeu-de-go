package jeugo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;

public class Plateau extends JComponent{
	
	private static final long serialVersionUID = 1L;
	//Attribut
	public Point size;
	// Plateau contenant des pi�ces
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
				// On cr�e une pi�ce � chaque fois
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

		    BufferedImage img = null;
		    try {
		        img = ImageIO.read(new File("ressource/goban.png"));
		    } catch (IOException e) {
		    }
		    g = img.getGraphics();
		    g.drawImage(img,0, 0, new JLayeredPane());
	}

	
}
