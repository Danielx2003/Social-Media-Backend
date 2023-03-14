import java.util.ArrayList;

public class Account implements Serializable{
	//Attributes
	private int id;
	private String handle;
	private String description;
	private int endorsementsRecieved;
	private ArrayList<Post> originalPosts;
	private ArrayList<Comment> comments;
	private ArrayList<Endorsement> endorsements;

	//Constructors
	Account(int id, String handle) {
		this.id = id;
		this.handle = handle;
		description = "";
		endorsementsRecieved = 0;
		originalPosts = new ArrayList<>();
		comments = new ArrayList<>();
		endorsements = new ArrayList<>();
	}
	Account(int id, String handle, String description) {
		super(id, handle);
		this.description = description;
	}
	
	//Methods
	
	public void removeAccount() {
		for (int i = 0; i<= originalPosts.size(); i++) {
			originalPosts[i].deletePost();
 		}
		originalPosts = null;
		
	}
	
	public void addEndorsement(Endorsement endorsement) {
		endorsements.add(endorsement);
	}
	
	public void addPost(Post post){
		originalPosts.add(post);
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	//Getters and Setters
	public int getID(){
		return this.id;
	}
	
	public void setHandle(String newHandle){
		this.handle = newHandle;
	}
	
	public int getNoOriginalPosts(){
		return originalPosts.size();
	}

	public int getNoEndorsements(){
		return endorsements.size();
	}
	
	public int getNoComments(){
		return comments.size();
	}

		
	public String getHandle(){
		return this.handle;
	}
	
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public int getEndorsementsRecieved(){
		return endorsementsRecieved;
	}
	
	public void addEndorsementsRecieved(){
		endorsementsRecieved ++;
	}
	
	public Post getPostById(int postId) throws PostIDNotRecognisedException{
		for (int i = 0; i<= originalPosts.size(); i++){
			if (originalPosts[i].getPostId() == postId){
				return originalPosts[i];
			}
		for (int i = 0; i<= comments.size(); i++){
			if (comments[i].getPostId() == postId){
				return comments[i];
			}
		for (int i = 0; i<= endorsement.size(); i++){
			if (endorsement[i].getPostId() == postId){
				return endorsement[i];
			}
		throw PostIDNotRecognisedException;
		}
	}
}