import java.util.ArrayList;

public class SocialMedia implements SocialMediaInterface, Serializable {

	// Attributes
	private int nextID;
	private ArrayList<Account> accounts;

	// Constructors
	public SocialMedia() {
		nextID = 1;
		accounts = new ArrayList<>();
	}

	// Account Creation, Deletion and Updating Methods
	int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.length() == 0) {
			throw InvalidHandleException;
		}

		for (int i = 0; i < handle.length(); i++) {
			if (Character.isWhitespace(handle.charAt(i))) {
				throw InvalidHandleException;
			}
		}

		for (i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				throw IllegalHandleException;
			}
		}

		Account account = new Account(nextId++, handle);
		accounts.add(account);
		return account.getID();
	}

	int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.length() == 0) {
			throw InvalidHandleException;
		}

		for (int i = 0; i < handle.length(); i++) {
			if (Character.isWhitespace(handle.charAt(i))) {
				throw InvalidHandleException;
			}
		}

		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				throw IllegalHandleException;
			}
		}

		Account account = new Account(nextId++, handle, description);
		accounts.add(account);
		return account.getID();
	}

	void removeAccount(int id) throws AccountIDNotRecognisedException {
		Account account = getAccountById(id);
		account.removeAccount();
		accounts.remove(account);

	}

	void removeAccount(String handle) throws HandleNotRecognisedException {
		Account account = getAccountByHandle(handle);
		account.removeAccount();
		accounts.remove(account);
	}

	void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		Account account = getAccountByHandle(oldHandle);

		if (newHandle.length() > 30 || newHandle.length() == 0) {
			throw InvalidHandleException;
		}

		for (int i = 0; i < newHandle.length(); i++) {
			if (Character.isWhitespace(newHandle.charAt(i))) {
				throw InvalidHandleException;
			}
		}

		for (i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == newHandle) {
				throw IllegalHandleException;
			}
		}

		account.setHandle(newHandle);

	}

	void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		Account account = getAccountByHandle(handle);
		account.setDescription(description);

	}

	Account getAccountByHandle(String handle) throws HandleNotRecognisedException {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				return accounts.get(i);
			}

		}
		throw HandleNotRecognisedException;
	}

	Account getAccountById(int id) throws IDNotRecognisedException{
		for (int i = 0; i< accounts.size(); i++){
			if (accounts.get(i).getId() == id){
				return accounts.get(i);
			}
		throw IDNotRecognisedException;
		}

	String showAccount(String handle) throws HandleNotRecognisedException {
		Account account = getAccountByHandle(handle);
		int noPosts = account.getNoOriginalPosts() + account.getNoComments() + account.getNoEndorsements();
		String toReturn = "ID: " + account.getID()
				+ "\nHandle: " + account.getHandle()
				+ "\nDescription: " + account.getDescription()
				+ "\nPost Count: " + noPosts
				+ "\nEndorse Count: " + account.getEndorsementsRecieved();
		return toReturn;
	}

	// Post Methods

	int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		Account account = getAccountByHandle(handle);
		if (message.length() == 0 || message.length() > 100) {
			throw InvalidPostException;
		}
		Post post = new Post(account, message);
		account.addPost(post);
		return post.getPostId();
	}

	int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		Account account = getAccountByHandle(handle);
		Post post = getPostById(id);
		if (post instanceof Endorsement) {
			throw NotActionablePostException;
		}
		Endorsement endorsement = new Endorsement(account, post);
		account.addEndorsement(endorsement);
		post.addEndorsement(endorsement);
		return endorsement.getPostId();
	}

	Post getPostById(int postId) throws PostIdNotRecognisedException{
		for (i=0; i< accounts.size(); i++){
			try {
				Post post = accounts.getPostById(postId);
			} catch (PostIdNotRecognisedException e) {
			} else {
				return post;
			}
		}
		throw PostIdNotRecognisedException;
	}

	int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		Account account = getAccountByHandle(handle);
		Post post = getPostById(id);
		if (post instanceof Endorsement) {
			throw NotActionablePostException;
		}
		if (message.length() == 0 || message.length() > 100) {
			throw InvalidPostException;
		}
		Comment comment = new Comment(account, post, message);
		account.addCommment(comment);
		post.addComment(comment);
		return comment.getPostId();
	}

	void deletePost(int id) throws PostIDNotRecognisedException {
		Post post = getPostByID(id);
		post.deletePost();

	}

	String showIndividualPost(int id) throws PostIDNotRecognisedException {
		Post post = getPostByID(id);

		string toReturn = "ID: " + id
				+ "\nAccount: " + getAccountHandleByPostId(id)
				+ "\nNo. Endorsements: " + post.getNoEndorsements() + "No. Comments: " + post.getNoComments() // need to
																												// add
																												// in
																												// post
																												// class
				+ "\n" + post.getMessage();

		return toReturn;
	}

	StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException{
		Post post = getPostByID();
		if (post instanceof Endorsement) {
			throw NotActionablePostException;
		}
		StringBuilder toReturn = StringBuilder(showIndividualPost(id));
		if (comments.size() == 0){
			return toReturn;
		} else {
			toReturn.append("\n|");
			for (int i =0; i<= comments.size(); i++){
				toReturn.append(showPartialChildrenDetails(post, depth + 1);
			}
			return toReturn;
		}
	}

	StringBuilder showPartialChildrenDetails(Post post, int depth) throws PostIDNotRecognisedException, NotActionablePostException{
		int postId = post.getId();
		String space = " ";
		String lineStart = "\n" + space.repeat(4*depth);
		String thisPost = "\n" + space.repeat(4*(depth-1))
			+ "| > ID: " + post.getId()
			+ lineStart + "Account: " + getAccountHandleByPostId(postId)
			+ lineStart + "No. endorsements: " + post.getNoEndorsements()
			+ " | No. comments: " + post.getNoComments()
			+ lineStart + post.getMessage();
		StringBuilder toReturn = StringBuilder(thisPost);
		
		ArrayList<Comment> comments = post.getComments();
		
		if (comments.size() == 0){
			return toReturn;
		} else {
			toReturn.append(lineStart + "|");
			for (int i =0; i<= comments.size(); i++){
				toReturn.append(showPartialChildrenDetails(post, depth + 1);
			}
			return toReturn;
		}
		
		
		string toReturn = "ID: " + id 
		+ "\nAccount: " + getAccountHandleByPostId(id)
		+ "\nNo. Endorsements: " + post.getNoEndorsements() + "No. Comments: " + post.getNoComments() //need to add in post class
		+ "\n" + post.getMessage();
	
	
	}

	public Post getPostByID(int id) throws PostIdNotRecognisedException {
		Post post = null;

		for (int i = 0; i <= accounts.size(); i++) {
			if (accounts.get(i).checkForPost(id)) {
				Post post = accounts.get(i).getPostById(id);
				accounts.get(i).removePost(post);
			}
		}

		if (post == null) {
			throw PostIDNotRecognisedException;
		}
		return post;
	}

	public String getAccountHandleByPostId(int id) {
		String handle = "";
		for (int i = 0; i <= accounts.size(); i++) {
			if (accounts.get(i).checkForPost(id)) {
				handle = accounts.get(i).getHandle();

			}
		}
		return handle;
	}

	// Analytics Methods
	int getNumberOfAccounts() {
		return accounts.size();
	}

	int getTotalOriginalPosts() {
		int count = 0;
		for (int i = 0; i < accounts.size(); i++) {
			count += accounts.get(i).getNoOriginalPosts();
		}

		return count;
	}

	int getTotalEndorsmentPosts() {
		int count = 0;
		for (int i = 0; i < accounts.size(); i++) {
			count += accounts.get(i).getNoEndorsements();
		}

		return count;

	}

	int getTotalCommentPosts() {
		int count = 0;
		for (int i = 0; i < accounts.size(); i++) {
			count += accounts.get(i).getNoComments();
		}

		return count;
	}

	int getMostEndorsedPost() {
		int count = -1;
		int highestId = -1;
		for (int i = 0; i <= accounts.size(); i++) {
			if (accounts.get(i).getMostEndorsedPost().getNoEndorsements() > count) {
				highestId = accounts.get(i).getMostEndorsedPost();
				count = accounts.get(i).getMostEndorsedPost().getNoEndorsements();
			}
		}
		return highestId;
	}

	int getMostEndorsedAccount() {
		int count = -1;
		int highestId = -1;
		for (int i = 0; i <= accounts.size(); i++) {
			if (accounts.get(i).getEndorsementsRecieved() > count) {
				highestId = accounts.get(i).getID();
				count = accounts.get(i).getEndorsementsRecieved();
			}
		}
		return highestId;
	}

	// Management related methods

	void erasePlatform() {
		for (int i = 0; i <= accounts.size(); i++) {
			removeAccount(accounts.get(i).getID());

		}
	}

	void savePlatform(String filename) throws IOException {
		// TO DO
	}

	void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TO DO
	}
}
