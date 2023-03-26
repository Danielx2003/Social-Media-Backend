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
		super(id, handle);
		this.description = description;
	}

	// Methods

	public void removeAccount() {
		for (int i = 0; i <= originalPosts.size(); i++) {
			originalPosts.get(i).deletePost();
		}
		originalPosts = null;

	}

	public void addEndorsement(Endorsement endorsement) {
		endorsements.add(endorsement);
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
		originalPosts.add(post);
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public boolean checkForPost(int postId) { // updated from iD to postId
		boolean present = false;
		for (int i = 0; i <= originalPosts.size(); i++) {
			if (originalPosts.get(i).getPostId() == postId) {
				present = true;
			}
		}
		for (int i = 0; i <= comments.size(); i++) {
			if (comments.get(i).getPostId() == postId) {
				present = true;
			}
		}
		for (int i = 0; i <= endorsements.size(); i++) {
			if (endorsements.get(i).getPostId() == postId) {
				present = true;
			}
			return present;
		}
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
		for (int i = 0; i <= originalPosts.size(); i++) {
			if (originalPosts.get(i).getPostId() == postId) {
				return originalPosts.get(i);
			}
		}
		for (int i = 0; i <= comments.size(); i++) {
			if (comments.get(i).getPostId() == postId) {
				return comments.get(i);
			}
		}
		for (int i = 0; i <= endorsements.size(); i++) {
			if (endorsements.get(i).getPostId() == postId) {
				return endorsements.get(i);
			}

		}
		throw PostIDNotRecognisedException;
	}

	public int getMostEndorsedPost() {
		int count = -1;
		Post mostEndorsed = null;

		for (int i = 0; i <= originalPosts.size(); i++) {
			if (originalPosts.get(i).getNoEndorsements() > count) {
				mostEndorsed = originalPosts.get(i);
				count = originalPosts.get(i).getNoEndorsements();
			}
		}

		for (int i = 0; i <= comments.size(); i++) {
			if (comments.get(i).getNoEndorsements() > count) {
				mostEndorsed = comments.get(i);
				count = comments.get(i).getNoEndorsements();
			}
		}
		return mostEndorsed;
	}
}
