package personal.mario.utils;

public class Constants {
	//歌单url
	public static final String SOURCE_URL = "http://music.163.com/discover/playlist/?";
	
	//163主域名
	public static final String DOMAIN = "http://music.163.com";
	public static final String BASE_URL = "http://music.163.com/";
	
	//获取评论的API路径(被加密)
	public static final String NET_EASE_COMMENT_API_URL = "http://music.163.com/weapi/v1/resource/comments/R_SO_4_";
	
	//解密用的文本
	public static final String TEXT = "{\"username\": \"\", \"rememberLogin\": \"true\", \"password\": \"\"}";
	
	//存储歌曲信息文本路径
	public static final String COMMENT_MESSAGE_PATH = "/home/user/workspace/NetEaseMusicCrawler/log/comment_message.xls";
	
	//存储评论内容文本路径
	public static final String COMMENTS_PATH = "/home/user/workspace/NetEaseMusicCrawler/log/comments_";
	
	public static final String COMMENTS_SUFFIX = ".xls";
	
	//TOP歌曲文本路径
	public static final String TOP_MUSIC_PATH = "/home/user/workspace/NetEaseMusicCrawler/log/top_music.xls";
	
	//歌曲评论大于某个值文本路径
	public static final String TOP_COMMENT_MORE_MUSIC_PATH = "/home/user/workspace/NetEaseMusicCrawler/log/music_comment_gt.xls";
	
	//要爬取的歌单数
	public static final int MUSIC_LIST_COUNT = 100;
	
	//分页数
	public static final int PER_PAGE = 35;
	
	//偏移量
	public static final int OFFSET = 0;
	
	//要爬取的TOP歌曲数
	public static final int TOP_MUSIC_COUNT = 20;
	
	//获取评论数大于该值的歌曲
	public static final int COMMENTS_LIMIT = 100000;
}