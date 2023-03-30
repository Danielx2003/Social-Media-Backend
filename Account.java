package socialmedia;

import java.util.ArrayList;

public class Account implements java.io.Serializable {
	// Attributes
	private int id;
	private String handle;
	private String description;
	private int endorsementsRecieved;
	private ArrayList<Post> originalPosts;
	private ArrayList<Comment> comments;
	private ArrayList<Endorsement> endorsements;

	// Constructors
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
		this(id, handle);
		this.description = description;
		assert this.id == id;
		assert this.handle == handle;
	}

	// Methods

	public void removeAccount() {
		for (int i = 0; i < originalPosts.size(); i++) {
			originalPosts.get(i).deletePost();
		}
		
		for (int i = 0; i < endorsements.size(); i++) {
			endorsements.get(i).deletePost();
		}
		
		for (int i = 0; i < comments.size(); i++) {
			comments.get(i).deletePost();
		}
		
		assert originalPosts.size() == 0;
		assert endorsements.size() == 0;
		assert comments.size() == 0;
		originalPosts = null;
		endorsements = null;
		comments = null;

	}

	public void addEndorsement(Endorsement endorsement) {
		int noEndorsements = endorsements.size();
		endorsements.add(endorsement);
		assert noEndorsements  + 1 == endorsements.size();
	}

	public void removePost(Post post) {
		if (post instanceof Endorsement) {
			endorsements.remove(post);

		} else if (post instanceof Comment) {
			comments.remove(post);

		} else {
			originalPosts.remove(post);
		}

	}

	public void addPost(Post post) {
		int noPosts = originalPosts.size();
		originalPosts.add(post);
		assert noPosts  + 1 == originalPosts.size();
	}

	public void addComment(Comment comment) {
		int noComments = comments.size();
		comments.add(comment);
		assert noComments  + 1 == comments.size();
	}

	public boolean checkForPost(int postId) {
		boolean present = false;
		for (int i = 0; i < originalPosts.size(); i++) {
			if (originalPosts.get(i).getPostId() == postId) {
				present = true;
			}
		}
		for (int i = 0; i < comments.size(); i++) {
			if (comments.get(i).getPostId() == postId) {
				present = true;
			}
		}
		for (int i = 0; i < endorsements.size(); i++) {
			if (endorsements.get(i).getPostId() == postId) {
				present = true;
			}
		}
		return present;
	}

	// Getters and Setters
	public int getID() {
		return this.id;
	}

	public void setHandle(String newHandle) {
		this.handle = newHandle;
	}

	public int getNoOriginalPosts() {
		return originalPosts.size();
	}

	public int getNoEndorsements() {
		return endorsements.size();
	}

	public int getNoComments() {
		return comments.size();
	}

	public String getHandle() {
		return this.handle;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getEndorsementsRecieved() {
		return endorsementsRecieved;
	}

	public void addEndorsementsRecieved() {
		endorsementsRecieved++;
	}

	public Post getPostById(int postId) throws PostIDNotRecognisedException {
		for (int i = 0; i < originalPosts.size(); i++) {
			if (originalPosts.get(i).getPostId() == postId) {
				return originalPosts.get(i);
			}
		}
		for (int i = 0; i < comments.size(); i++) {
			if (comments.get(i).getPostId() == postId) {
				return comments.get(i);
			}
		}
		for (int i = 0; i < endorsements.size(); i++) {
			if (endorsements.get(i).getPostId() == postId) {
				return endorsements.get(i);
			}

		}
		throw new PostIDNotRecognisedException(); //No post was found with correct Id
	}

	public Post getMostEndorsedPost() {
		int count = -1;
		Post mostEndorsed = null;

		for (int i = 0; i < originalPosts.size(); i++) {
			if (originalPosts.get(i).getNoEndorsements() > count) {
				mostEndorsed = originalPosts.get(i);
				count = originalPosts.get(i).getNoEndorsements();
			}
		}

		for (int i = 0; i < comments.size(); i++) {
			if (comments.get(i).getNoEndorsements() > count) {
				mostEndorsed = comments.get(i);
				count = comments.get(i).getNoEndorsements();
			}
		}
		return mostEndorsed;
	}
}
