package personal.mario.main;

public class RunProgram {
	
	public static void main(String[] args) {
		new Thread(new NetEaseCrawler(), "netEaseCrawler").start();
	}
}
