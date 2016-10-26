package personal.mario.service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.ImmutableMap;
import personal.mario.bean.MusicComment;
import personal.mario.bean.MusicCommentMessage;
import personal.mario.utils.Constants;
import personal.mario.utils.EncryptUtils;

/*解析HTML*/
public class HtmlParserService {
	
	//歌单列表页获取所有歌单URL
	public static void parseAndSaveMusicListUrl(String html) {
		
		Document doc = Jsoup.parse(html);
		Element content = doc.getElementById("m-pl-container");
		Elements as = content.select("li > div > a.msk");
		
		for (Element a : as) {
			MusicListQueueService.addMusicList(Constants.DOMAIN + a.attr("href"));
		}
	}
	
	//歌曲列表页获取所有歌曲ID
	public static void parseMusicListAndGetMusics(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Element content = doc.getElementById("song-list-pre-cache");
		Elements as = content.select("ul.f-hide li a");
		
		for (Element a : as) {
			String suffix = a.attr("href");
			MusicQueueService.addUncrawledMusic(suffix.substring(suffix.indexOf("id=") + 3));
		}
	}
	
	//通过歌曲ID获取评论API，网易对其进行了加密
	public static MusicCommentMessage parseCommentMessage(String songId) throws Exception {
		String songUrl = Constants.DOMAIN + "/song?id=" + songId;
		URL uri = new URL(songUrl);
		Document msdoc = Jsoup.parse(uri, 3000);
		
        String secKey = new BigInteger(100, new SecureRandom()).toString(32).substring(0, 16);
        String encText = EncryptUtils.aesEncrypt(EncryptUtils.aesEncrypt(Constants.TEXT, "0CoJUm6Qyw8W8jud"), secKey);
        String encSecKey = EncryptUtils.rsaEncrypt(secKey);
        Response response = Jsoup
                .connect(Constants.NET_EASE_COMMENT_API_URL + songId + "/?csrf_token=")
                .method(Connection.Method.POST).header("Referer", Constants.BASE_URL)
                .data(ImmutableMap.of("params", encText, "encSecKey", encSecKey)).execute();
        
        
        Object res = JSON.parse(response.body());
        
        if (res == null) {
        	return null;
        }

        MusicCommentMessage musicCommentMessage = new MusicCommentMessage();
        
        int commentCount = (int)JSONPath.eval(res, "$.total");
        int hotCommentCount = (int)JSONPath.eval(res, "$.hotComments.size()");
        int latestCommentCount = (int)JSONPath.eval(res, "$.comments.size()");
        
        musicCommentMessage.setSongTitle(msdoc.title());
        musicCommentMessage.setSongUrl(songUrl);
        musicCommentMessage.setCommentCount(commentCount);
        
        List<MusicComment> ls = new ArrayList<MusicComment>();
        
        if (commentCount != 0 && hotCommentCount != 0) {
        	
        	for (int i = 0; i < hotCommentCount; i++) {
        		String nickname = JSONPath.eval(res, "$.hotComments[" + i + "].user.nickname").toString();
        		String time = EncryptUtils.stampToDate((long)JSONPath.eval(res, "$.hotComments[" + i + "].time"));
        		String content = JSONPath.eval(res, "$.hotComments[" + i + "].content").toString();
        		String appreciation = JSONPath.eval(res, "$.hotComments[" + i + "].likedCount").toString();
        		ls.add(new MusicComment("hotComment", nickname, time, content, appreciation));
        	}
        } else if (commentCount != 0) {
        	
        	for (int i = 0; i < latestCommentCount; i++) {
        		String nickname = JSONPath.eval(res, "$.comments[" + i + "].user.nickname").toString();
        		String time = EncryptUtils.stampToDate((long)JSONPath.eval(res, "$.comments[" + i + "].time"));
        		String content = JSONPath.eval(res, "$.comments[" + i + "].content").toString();
        		String appreciation = JSONPath.eval(res, "$.comments[" + i + "].likedCount").toString();
        		ls.add(new MusicComment("latestCommentCount", nickname, time, content, appreciation));
        	}
        }
        
        musicCommentMessage.setComments(ls);
        
        return musicCommentMessage;
    }
}
