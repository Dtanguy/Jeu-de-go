package jeugo;


public class Game {

	private static Game	instance;	
	private Fenetre fenetre;	
	
	public Game(){
		 fenetre= new Fenetre();
	}
	
	//Singleton pour eviter de le lancer plusiur fois
	public static Game getInstance(){
		if (instance == null){
			instance = new Game();
		} else if (instance != null){
			return null;
		}
		return instance;
	}
	
}
