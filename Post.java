package socialmedia;

import java.util.ArrayList;

public class Post implements java.io.Serializable {
	// Attributes
	private static int nextId = 0;
	private int postId;
	protected Account account;
	private String message;
	private ArrayList<Comment> comments;
	private ArrayList<Endorsement> endorsements;

	// Constructors
	public Post() { //This Post constructor is for comments when their post is deleted
		this.postId = -1;
		this.message = "The original content was removed from the system and is no longer available.";
	}

	public Post(Account account, String message) {
		this.postId = nextId++;
		this.account = account;
		this.message = message;
		comments = new ArrayList<>();
		endorsements = new ArrayList<>();
	}

	// Methods
	public void addEndorsement(Endorsement endorsement) {
		endorsements.add(endorsement);
		account.addEndorsementsRecieved();
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public void deletePost() {
		for (int i = 0; i<= comments.size(); i++) {
			comments.get(i).deleteReference();
 		}
		comments = null;
		
		for (int i = 0; i<= endorsements.size(); i++) {
			endorsements.get(i).deletePost();
 		}
		endorsements = null;
		
		account.removePost(this);
	}

	public void removeComment(Comment comment) {
		comments.remove(comment);
	}

	public void removeEndorsement(Endorsement endorsement) {
		endorsements.remove(endorsement);
	}

	// Getters And Setters
	public int getPostId() {
		return this.postId;
	}

	public Account getAccount() {
		return this.account;
	}

	public String getMessage() { 
		return this.message;
	}

	public int getNoEndorsements() {
		return endorsements.size();
	}

	public int getNoComments() {
		return comments.size();
	}

	public ArrayList<Comment> getCommentsOnPost() {
		return comments;
	}

}
