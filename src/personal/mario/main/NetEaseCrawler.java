package personal.mario.main;

public class NetEaseCrawler {
	
	public static void main(String[] args) {
		new Thread(new MainProgram(), "netEaseCrawler").start();
	}
}
