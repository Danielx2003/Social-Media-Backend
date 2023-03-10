import java.util.ArrayList;

public class Post {
	//Attributes
	private static int nextId = 0;
	private int postId;
	private int accountId;
	private String message;
	private ArrayList<Comment> comments;
	private ArrayList<Endorsement> endorsements;
	
	//Constructors
	public Post(int accountId, String message) {
		this.postId = nextId++;
		this.accountId = accountId;
		this.message = message;
		comments = new ArrayList<>();
		endorsements = new ArrayList<>();
	}
	
	//Methods
	public void deletePost() {
		for (int i = 0; i<= comments.size(); i++) {
			comments[i].deleteReference();
 		}
		comments = null;
		
		for (int i = 0; i<= endorsements.size(); i++) {
			endorsements[i].deletePost();
 		}
		endorsements = null;
	
	public void deleteComment(int commentId) {
		for (int i = 0; i<= comments.size(); i++) {
			if (comments[i].getPostId() == commentId) {
				comments.remove(i);
				break;
			}
 		}
	}
	
	public void deleteEndorsement(int endorsementId) {
		for (int i = 0; i<= endorsements.size(); i++) {
			if (endorsements[i].getPostId() == endorsementId) {
				endorsements.remove(i);
				break;
			}
 		}
	}
		
	//Getters And Setters
	public int getPostId(){
		return this.postId;
	}
	
	public int getAccountId(){
		return this.accountId;
	}
	
	public String getMesssage(){
		return this.message;
	}
	
	public int getNoEndorsements(){
		return endorsements.size();
	}
	
		
	} 
	
}