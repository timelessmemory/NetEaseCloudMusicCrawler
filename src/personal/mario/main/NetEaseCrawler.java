package personal.mario.main;

import java.io.IOException;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import personal.mario.bean.MusicCommentMessage;
import personal.mario.service.HtmlFetcherService;
import personal.mario.service.HtmlParserService;
import personal.mario.service.MusicListQueueService;
import personal.mario.service.MusicQueueService;
import personal.mario.service.TopMusicCalculateService;
import personal.mario.utils.Constants;
import personal.mario.utils.GenerateExcelUtils;

public class NetEaseCrawler implements Runnable {
	
	private int totalMusicList = Constants.MUSIC_LIST_COUNT;
	private int limit = Constants.PER_PAGE;
	private int offset =Constants.OFFSET;
	private static Logger logger = Logger.getLogger(NetEaseCrawler.class);
	private HSSFWorkbook commentMessageWorkbook = new HSSFWorkbook();
	private List<MusicCommentMessage> ms = null;
	
	@Override
	public void run() {
		try {
			initUncrawledMusicListQueue();
			
			int count = 0;
			HSSFSheet commentMessageSheet = GenerateExcelUtils.generateCommentMessageExcelInit(commentMessageWorkbook);
			
			while (!MusicListQueueService.isUncrawledMusicListEmpty()) {
				fillUncrawledMusicQueue(MusicListQueueService.getTopMusicList());
				
				while (!MusicQueueService.isUncrawledMusicQueueEmpty()) {
					
					String songId = MusicQueueService.getTopMusicUrl();
					
					if (!MusicQueueService.isMusicCrawled(songId)) {
						MusicCommentMessage mcm = getCommentMessage(songId);
						ms = TopMusicCalculateService.getTopMusic(mcm);
						GenerateExcelUtils.generateCommentMessageExcelProcess(commentMessageWorkbook, commentMessageSheet, mcm, count);
						GenerateExcelUtils.generateCommentsExcel(mcm);
						
						MusicQueueService.addCrawledMusic(songId);
						count++;
					}
				}
			}
			
			GenerateExcelUtils.generateCommentMessageExcelWrite(commentMessageWorkbook);
			GenerateExcelUtils.generateTopMusicExcel(ms);
			
			logger.info("count : " + count);
			logger.info("size : " + MusicQueueService.getCrawledMusicSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initUncrawledMusicListQueue() throws ClientProtocolException, IOException {
		
		if (totalMusicList > limit) { 
			int tmpLimit = limit;
			int tmpOffset = offset;
			
			while (totalMusicList > tmpOffset) {
				
				String suffix = "limit=" + tmpLimit + "&offset=" + tmpOffset;
				tmpOffset += tmpLimit;
				
				if (tmpOffset + tmpLimit > totalMusicList) {
					tmpLimit =  totalMusicList - tmpOffset;
				}
				
				HtmlParserService.parseAndSaveMusicListUrl(HtmlFetcherService.fetch(Constants.SOURCE_URL + suffix));
			}
		} else {
			String suffix = "limit=" + totalMusicList + "&offset=" + offset;
			HtmlParserService.parseAndSaveMusicListUrl(HtmlFetcherService.fetch(Constants.SOURCE_URL + suffix));
		}
	}
	
	public void fillUncrawledMusicQueue(String musicListUrl) throws IOException {
		HtmlParserService.parseMusicListAndGetMusics(musicListUrl);
	}
	
	public MusicCommentMessage getCommentMessage(String songId) {
		try {
			MusicCommentMessage mc = HtmlParserService.parseCommentMessage(songId);
			if (mc == null) {
				logger.info("warining: be interceptted by net ease music server..");
				Thread.sleep((long) (Math.random() * 30000));
				return getCommentMessage(songId);
			} else {
				return mc;
			}
		} catch (Exception e) {
			logger.info("error: be refused by net ease music server..");
			return getCommentMessage(songId);
		}
	}
}
