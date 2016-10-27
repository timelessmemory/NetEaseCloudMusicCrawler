package personal.mario.service;

import java.util.ArrayList;
import java.util.List;
import personal.mario.bean.MusicCommentMessage;
import personal.mario.utils.Constants;

/*计算获取TOP 歌曲*/
public class TopMusicCalculateService {
	private static List<MusicCommentMessage> ms = new ArrayList<MusicCommentMessage>();
	private static List<MusicCommentMessage> msl = new ArrayList<MusicCommentMessage>();
	
	//获取歌曲
	public static List<MusicCommentMessage> getTopMusic(MusicCommentMessage mcm) {

		int topSize = ms.size();
		
		if (topSize == 0) {
			ms.add(mcm);
		}
		
		if (topSize > 0 && topSize < Constants.TOP_MUSIC_COUNT) {
			for (int j = 0; j < topSize; j++) {
				if (mcm.getCommentCount() > ms.get(j).getCommentCount()) {
					ms.add(j, mcm);
					break;
				}
				
				if (j == topSize - 1) {
					ms.add(mcm);
				}
			}
		}
		
		if (topSize >= Constants.TOP_MUSIC_COUNT) {
			for (int j = 0; j < topSize; j++) {
				if (mcm.getCommentCount() > ms.get(j).getCommentCount()) {
					ms.add(j, mcm);
					ms.remove(topSize);
					break;
				}
			}
		}
		
		return ms;
	}
	
	//获取评论数大于该值的歌曲
	public static List<MusicCommentMessage> getMusicCommentsCountMore(MusicCommentMessage mcm) {
		
		int size = msl.size();
		
		if (mcm.getCommentCount() > Constants.COMMENTS_LIMIT) {
			if (size == 0) {
				msl.add(mcm);
			} else {
				for (int i = 0; i < size; i++) {
					if (mcm.getCommentCount() > msl.get(i).getCommentCount()) {
						msl.add(i, mcm);
						break;
					}
					
					if (i == size - 1) {
						msl.add(mcm);
					}
				}
			}
		}
		
		return msl;
	}
}
