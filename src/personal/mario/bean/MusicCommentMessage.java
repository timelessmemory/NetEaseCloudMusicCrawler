package personal.mario.bean;

import java.util.ArrayList;
import java.util.List;

public class MusicCommentMessage {
	private String songTitle;
	private String songUrl;
	private int commentCount;
	private List<MusicComment> comments = new ArrayList<MusicComment>();
	
	public MusicCommentMessage() {}
	
	public MusicCommentMessage(String songTitle, String songUrl, int commentCount, List<MusicComment> comments) {
		this.songTitle = songTitle;
		this.songUrl = songUrl;
		this.commentCount = commentCount;
		this.comments = comments;
	}
	
	public String getSongTitle() {
		return songTitle;
	}
	
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	
	public String getSongUrl() {
		return songUrl;
	}
	
	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}
	
	public int getCommentCount() {
		return commentCount;
	}
	
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	public void setComments(List<MusicComment> comments) {
		this.comments = comments;
	}
	
	public List<MusicComment> getComments() {
		return comments;
	}

	@Override
	public String toString() {
		return "[songTitle=" + songTitle + ", songUrl=" + songUrl + ", commentCount=" + commentCount
				+ ", comments=" + comments + "]";
	}
}