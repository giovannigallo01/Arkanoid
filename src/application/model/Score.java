package application.model;

public class Score {

	private String username;
	private int score;
	
	public Score() {}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		if(username != null)
			return username + ": " + score + "pt";
		return "";
	}
}
