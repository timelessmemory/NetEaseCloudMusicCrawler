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

/*
 * 主逻辑
 * author mario.li
 * 2016-10-26
 * */
public class NetEaseCrawler implements Runnable {
	
	private int totalMusicList = Constants.MUSIC_LIST_COUNT;
	private int limit = Constants.PER_PAGE;
	private int offset =Constants.OFFSET;
	private HSSFWorkbook commentMessageWorkbook = new HSSFWorkbook();
	private List<MusicCommentMessage> ms = null;
	private List<MusicCommentMessage> msl = null;
	private static Logger logger = Logger.getLogger(NetEaseCrawler.class);
	
	@Override
	public void run() {
		try {
			//初始化待爬取的歌单URL队列
			initUncrawledMusicListQueue();
			
			//记录所有爬取出来的歌曲数，包含重复歌曲
			int count = 0;
			
			//歌曲信息Excel初始化
			HSSFSheet commentMessageSheet = GenerateExcelUtils.generateCommentMessageExcelInit(commentMessageWorkbook);
			
			//开始根据歌单爬取
			while (!MusicListQueueService.isUncrawledMusicListEmpty()) {
				
				//填充待爬取歌曲队列
				fillUncrawledMusicQueue(MusicListQueueService.getTopMusicList());
				
				//歌曲队列为空就返回上层循环填充歌曲队列
				while (!MusicQueueService.isUncrawledMusicQueueEmpty()) {
					
					//取出待爬取歌曲ID
					String songId = MusicQueueService.getTopMusicUrl();
					
					//判断是否已经爬取过
					if (!MusicQueueService.isMusicCrawled(songId)) {
						//获取到爬取结果，歌曲信息
						MusicCommentMessage mcm = getCommentMessage(songId);
						
						//判断是否加入Top歌曲列表
						ms = TopMusicCalculateService.getTopMusic(mcm);
						
						//歌曲评论数是否大于某个值进行收录
						msl = TopMusicCalculateService.getMusicCommentsCountMore(mcm);
						
						//向歌曲信息Excel插入数据
						GenerateExcelUtils.generateCommentMessageExcelProcess(commentMessageWorkbook, commentMessageSheet, mcm, count);
						
						//生成歌曲评论Excel
						GenerateExcelUtils.generateCommentsExcel(mcm);
						
						//加入已经爬取的队列，供以后查重判断
						MusicQueueService.addCrawledMusic(songId);
						count++;
					}
				}
			}
			
			//生成歌曲信息Excel
			GenerateExcelUtils.generateCommentMessageExcelWrite(commentMessageWorkbook);
			
			//生成Top歌曲Excel
			GenerateExcelUtils.generateTopMusicExcel(ms, Constants.TOP_MUSIC_PATH);
			
			//生成评论数大于某个值的Top歌曲Excel
			GenerateExcelUtils.generateTopMusicExcel(msl, Constants.TOP_COMMENT_MORE_MUSIC_PATH);
			
			logger.info("count : " + count);
			
			//实际爬取的歌曲数，不包含重复
			logger.info("size : " + MusicQueueService.getCrawledMusicSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 循环请求获取所有歌单
	 * */
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
	
	//填充要爬取的歌曲队列
	public void fillUncrawledMusicQueue(String musicListUrl) throws IOException {
		HtmlParserService.parseMusicListAndGetMusics(musicListUrl);
	}
	
	//由于反爬的存在， 一旦被禁止爬取， 休眠几秒后再进行爬取
	public MusicCommentMessage getCommentMessage(String songId) {
		try {
			MusicCommentMessage mc = HtmlParserService.parseCommentMessage(songId);
			
			if (mc == null) {
				logger.info("warining: be interceptted by net ease music server..");
				Thread.sleep((long) (Math.random() * 30000));
				
				//递归
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
