package personal.mario.service;

import java.util.ArrayList;
import java.util.List;
import personal.mario.bean.MusicCommentMessage;
import personal.mario.utils.Constants;

/*计算获取TOP 歌曲*/
public class TopMusicCalculateService {
	private static List<MusicCommentMessage> ms = new ArrayList<MusicCommentMessage>();
	
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
}
