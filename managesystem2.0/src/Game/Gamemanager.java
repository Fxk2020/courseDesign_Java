package Game;

public class Gamemanager extends Thread {

	public void run() {
		new SnakeGame();
	}
	
	
	
	
	public static void main(String[] args) {
		new Gamemanager().start();

	}

}
