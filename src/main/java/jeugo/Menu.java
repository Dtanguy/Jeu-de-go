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
	
	private static Menu instance;
	
	private JFrame frame;
	private JPanel pan;
	private JLabel txt1;
	private CheckboxGroup type; 
	private JLabel txt2;
	private JSpinner sx;
	private JSpinner sy;
	private JButton start;
	
	public Menu() {		
		
		//Frame
		frame = new JFrame("Jeu de Go | Menu");	        
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	    
	    frame.setSize(300, 370);
	    frame.setLocationRelativeTo(null);
	    
	    //Decoration
	    frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
	    
	    //Panel
	    pan = (JPanel) frame.getContentPane();		
	    pan.setLayout(null);
	    
	    
	    //Type de partie
	    txt1 = new JLabel("Type de partie :");
	    txt1.setBounds(45, 10, 200, 30);		
	    type=new CheckboxGroup(); 
	    Checkbox box1=new Checkbox("Joueur contre Joueur",type,true); 
	    box1.setBounds(70,50, 200, 30);	    
	    Checkbox box2=new Checkbox("Joueur contre IA",type,false); 
	    box2.setBounds(85,80,200, 30);	    
	    Checkbox box3=new Checkbox("IA contre IA",type,false); 
	    box3.setBounds(100,110, 200, 30);	
	    pan.add(txt1);
	    pan.add(box1);
	    pan.add(box2); 
	    pan.add(box3);	
	    
	    //Taille du plateau
	    txt2 = new JLabel("Dimention du plateau :");
	    txt2.setBounds(45, 150, 200, 30);		
	    SpinnerNumberModel model1 = new SpinnerNumberModel(19, 1, 30, 1);  
	    SpinnerNumberModel model2 = new SpinnerNumberModel(19, 1, 30, 1); 
		sx = new JSpinner(model1);		
		sx.setBounds(80, 200, 50, 30);
		sy = new JSpinner(model2);
	    sy.setBounds(150, 200, 50, 30);
	    pan.add(txt2);
	    pan.add(sx);
	    pan.add(sy); 
	    
	    //Bouton lancer la partie
		start = new JButton("Nouvelle partie");	
		start.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    	int v1 = (Integer)sx.getValue();
		    	int v2 = (Integer)sy.getValue();	
		    	//System.out.println();
		    	Game.getInstance(v1,v2);	
		    	frame.dispose();
		    }
		});		
		start.setBounds(45, 270, 200, 30);
		pan.add(start);
		
		
		frame.setVisible(true);  
	}
	
	//Singleton pour eviter de le lancer plusiur fois
	public static Menu getInstance(){
		if (instance == null){
			instance = new Menu();
		} else if (instance != null){
			return null;
		}
		return instance;
	}
	
}
