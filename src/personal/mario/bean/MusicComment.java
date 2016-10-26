package personal.mario.bean;

public class MusicComment {
	private String type;
	private String nickname;
	private String commentDate;
	private String content;
	private String appreciation;
	
	public MusicComment() {}
	
	public MusicComment(String type, String nickname, String commentDate, String content, String appreciation) {
		this.type = type;
		this.nickname = nickname;
		this.commentDate = commentDate;
		this.content = content;
		this.appreciation = appreciation;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getCommentDate() {
		return commentDate;
	}
	
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAppreciation() {
		return appreciation;
	}
	
	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}

	@Override
	public String toString() {
		return "MusicComment [type=" + type + ", nickname=" + nickname + ", commentDate=" + commentDate + ", content="
				+ content + ", appreciation=" + appreciation + "]";
	}
}
