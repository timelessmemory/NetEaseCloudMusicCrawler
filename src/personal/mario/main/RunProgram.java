package personal.mario.main;

/*主运行程序*/
public class RunProgram {
	
	public static void main(String[] args) {
		new Thread(new NetEaseCrawler(), "netEaseCrawler").start();
	}
}
