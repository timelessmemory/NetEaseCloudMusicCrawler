package personal.mario.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*歌曲队列*/
public class MusicQueueService {
	private static Queue<String> uncrawledMusics = new ConcurrentLinkedQueue<String>();
	private static Queue<String> crawledMusics = new ConcurrentLinkedQueue<String>();
	
	public static void addUncrawledMusic(String e) {
		uncrawledMusics.offer(e);
	}
	
	public static String getTopMusicUrl() {
		if (!uncrawledMusics.isEmpty()) {
			return uncrawledMusics.poll();
		}
		
		return null;
	}
	
	public static void addCrawledMusic(String e) {
		crawledMusics.offer(e);
	}
	
	public static boolean isMusicCrawled(String id) {
		return crawledMusics.contains(id);
	}
	
	public static boolean isUncrawledMusicQueueEmpty() {
		return uncrawledMusics.isEmpty();
	}
	
	public static void printAll() {
		while (!uncrawledMusics.isEmpty()) {
			System.out.println(uncrawledMusics.poll());
		}
	}
	
	public static int getCrawledMusicSize() {
		return crawledMusics.size();
	}
}
