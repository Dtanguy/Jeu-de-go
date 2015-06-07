package jeugo;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class Menu {
	
	//Instance pour le singleton
	private static Menu instance;
	
	//Object du menu
	private JFrame frame;
	private JPanel pan;
	private JLabel txt1;
	private CheckboxGroup type;
	private JLabel txthandi;
	private JSpinner handi;
	private JLabel txt2;
	private JSpinner sx;
	private JSpinner sy;
	private JButton start;
	
	public Menu() {		
		
		//Création et paramètrage de la Frame
		frame = new JFrame("Jeu de Go | Menu");	        
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	    
	    frame.setSize(300, 370);
	    frame.setLocationRelativeTo(null);
	    
	    //Décoration de la Frame
	    frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
	    
	    //Création du Panel
	    pan = (JPanel) frame.getContentPane();		
	    pan.setLayout(null);
	    
	    
	    //Choix du type de partie avec des Checkbox
	    txt1 = new JLabel("Type de partie :");
	    txt1.setBounds(45, 10, 200, 30);		
	    type=new CheckboxGroup(); 
	    Checkbox box1=new Checkbox("Joueur contre Joueur",type,true); 
	    box1.setBounds(50,50, 140, 20);
	    
	    txthandi = new JLabel("Nombre de handicap ?");
	    txthandi.setBounds(65,65, 150, 20);		
	 
	    SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 9, 1); 
	    handi = new JSpinner(model);		
	    handi.setBounds(200,51, 50, 30);
	    
	    Checkbox box2=new Checkbox("Joueur contre IA",type,false); 
	    box2.setBounds(85,90,200, 30);	    
	    Checkbox box3=new Checkbox("IA contre IA",type,false); 
	    box3.setBounds(100,120, 200, 30);	
	    pan.add(txt1);
	    pan.add(box1);
	    pan.add(txthandi);
	    pan.add(handi);
	    pan.add(box2); 
	    pan.add(box3);	
	    
	    //Choix de la taille du plateau avec des Spinner
	    txt2 = new JLabel("Dimention du plateau :");
	    txt2.setBounds(45, 160, 200, 30);		
	    SpinnerNumberModel model1 = new SpinnerNumberModel(19, 1, 30, 1);  
	    SpinnerNumberModel model2 = new SpinnerNumberModel(19, 1, 30, 1); 
		sx = new JSpinner(model1);		
		sx.setBounds(80, 210, 50, 30);
		sy = new JSpinner(model2);
	    sy.setBounds(150, 210, 50, 30);
	    pan.add(txt2);
	    pan.add(sx);
	    pan.add(sy); 
	    
	    //Bouton lancer la partie
		start = new JButton("Nouvelle partie");	
		start.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    	int v1 = (Integer)sx.getValue()-1;
		    	int v2 = (Integer)sy.getValue()-1;	
		    	int hand = (Integer)handi.getValue();
		    	//On créé ou o nrecupere une instance de Game avec les parametre de taille du plateau
		    	Game.getInstance(v1,v2,hand);	
		    	frame.dispose();
		    }
		});		
		start.setBounds(45, 280, 200, 30);
		pan.add(start);
		
		//On rend le tout visible
		frame.setVisible(true);  
	}
	
	//Singleton pour éviter de lancer le menu plusieurs fois
	public static Menu getInstance(){
		if (instance == null){
			instance = new Menu();
		} else if (instance != null){
			return null;
		}
		return instance;
	}
	
}
