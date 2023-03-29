package socialmedia;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class SocialMedia implements SocialMediaPlatform, java.io.Serializable {

	// Attributes
	private int nextID;
	private ArrayList<Account> accounts;

	// Constructors
	public SocialMedia() {
		nextID = 1;
		accounts = new ArrayList<>();
	}

	// Account Creation, Deletion and Updating Methods
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.length() == 0) {
			throw new InvalidHandleException();
		}

		for (int i = 0; i < handle.length(); i++) {
			if (Character.isWhitespace(handle.charAt(i))) {
				throw new InvalidHandleException();
			}
		}

		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				throw new IllegalHandleException();
			}
		}

		Account account = new Account(nextID++, handle); // nextId to nextID
		accounts.add(account);
		return account.getID();
	}

	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.length() == 0) {
			throw new InvalidHandleException();
		}

		for (int i = 0; i < handle.length(); i++) {
			if (Character.isWhitespace(handle.charAt(i))) {
				throw new InvalidHandleException();
			}
		}

		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				throw new IllegalHandleException();
			}
		}

		Account account = new Account(nextID++, handle, description); // corrected name
		accounts.add(account);
		return account.getID();
	}

	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		Account account = getAccountById(id);
		account.removeAccount();
		accounts.remove(account);

	}

	public void removeAccount(String handle) throws HandleNotRecognisedException {
		Account account = getAccountByHandle(handle);
		account.removeAccount();
		accounts.remove(account);
	}

	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		Account account = getAccountByHandle(oldHandle);

		if (newHandle.length() > 30 || newHandle.length() == 0) {
			throw new InvalidHandleException();
		}

		for (int i = 0; i < newHandle.length(); i++) {
			if (Character.isWhitespace(newHandle.charAt(i))) {
				throw new InvalidHandleException();
			}
		}

		for (int i = 0; i < accounts.size(); i++) { // added int
			if (accounts.get(i).getHandle() == newHandle) {
				throw new IllegalHandleException();
			}
		}

		account.setHandle(newHandle);

	}

	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		Account account = getAccountByHandle(handle);
		account.setDescription(description);

	}

	private Account getAccountByHandle(String handle) throws HandleNotRecognisedException {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				return accounts.get(i);
			}

		}
		throw new HandleNotRecognisedException();
	}
  
	private Account getAccountById(int id) throws AccountIDNotRecognisedException {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getID() == id) {
				return accounts.get(i);
			}
		} // Added Bracket
		throw new AccountIDNotRecognisedException();
	}

	public String showAccount(String handle) throws HandleNotRecognisedException {
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

	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		Account account = getAccountByHandle(handle);
		if (message.length() == 0 || message.length() > 100) {
			throw new InvalidPostException();
		}
		Post post = new Post(account, message);
		account.addPost(post);
		return post.getPostId();
	}

	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		Account account = getAccountByHandle(handle);
		Post post = getPostById(id);
		if (post instanceof Endorsement) {
			throw new NotActionablePostException();
		}
		Endorsement endorsement = new Endorsement(account, post);
		account.addEndorsement(endorsement);
		post.addEndorsement(endorsement);
		return endorsement.getPostId();
	}

	private Post getPostById(int postId) throws PostIDNotRecognisedException {
		Post post = null;
		for (int i = 0; i < accounts.size(); i++) { // added int
			try {
				post = accounts.get(i).getPostById(postId);
			} catch (PostIDNotRecognisedException e) {
			}
		}
		if (post != null) { // Added if statement
			return post;
		} else {
			throw new PostIDNotRecognisedException();
		}
	}

	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		Account account = getAccountByHandle(handle);
		Post post = getPostById(id);
		if (post instanceof Endorsement) {
			throw new NotActionablePostException();
		}
		if (message.length() == 0 || message.length() > 100) {
			throw new InvalidPostException();
		}
		Comment comment = new Comment(account, post, message);
		account.addComment(comment);
		post.addComment(comment);
		return comment.getPostId();
	}

	public void deletePost(int id) throws PostIDNotRecognisedException {
		Post post = getPostByID(id);
		post.deletePost();

	}

	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		Post post = getPostByID(id);

		String toReturn = "ID: " + id
				+ "\nAccount: " + getAccountHandleByPostId(id)
				+ "\nNo. Endorsements: " + post.getNoEndorsements() + "No. Comments: " + post.getNoComments() // need to
																												// add
																												// in
																												// post
																												// class
				+ "\n" + post.getMessage();

		return toReturn;
	}

	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		Post post = getPostById(id);
		if (post instanceof Endorsement) {
			throw new NotActionablePostException();
		}
		StringBuilder toReturn = new StringBuilder(showIndividualPost(id));
		ArrayList<Comment> comments = post.getCommentsOnPost(); // Added Line
		if (comments.size() == 0) {
			return toReturn;
		} else {
			toReturn.append("\n|");
			for (int i = 0; i <= comments.size(); i++) {
				toReturn.append(showPartialChildrenDetails(post, 1)); // Changed depth + 1 -> 1 in function call, added
																		// final bracket
			}
			return toReturn;
		}
	}

	private StringBuilder showPartialChildrenDetails(Post post, int depth)
			throws PostIDNotRecognisedException {
		int postId = post.getPostId();
		String space = " ";
		String lineStart = "\n" + space.repeat(4 * depth);
		String thisPost = "\n" + space.repeat(4 * (depth - 1))
				+ "| > ID: " + post.getPostId()
				+ lineStart + "Account: " + getAccountHandleByPostId(postId)
				+ lineStart + "No. endorsements: " + post.getNoEndorsements()
				+ " | No. comments: " + post.getNoComments()
				+ lineStart + post.getMessage();
		StringBuilder toReturn = new StringBuilder(thisPost);

		ArrayList<Comment> comments = post.getCommentsOnPost();

		if (comments.size() == 0) {
			return toReturn;
		} else {
			toReturn.append(lineStart + "|");
			for (int i = 0; i <= comments.size(); i++) {
				toReturn.append(showPartialChildrenDetails(post, depth + 1)); // Added bracket
			}
			return toReturn;
		}
	}

	private Post getPostByID(int id) throws PostIDNotRecognisedException {
		Post post = null;

		for (int i = 0; i <= accounts.size(); i++) {
			if (accounts.get(i).checkForPost(id)) {
				post = accounts.get(i).getPostById(id);
				accounts.get(i).removePost(post);
			}
		}

		if (post == null) {
			throw new PostIDNotRecognisedException();
		}
		return post;
	}

	private String getAccountHandleByPostId(int id) {
		String handle = "";
		for (int i = 0; i <= accounts.size(); i++) {
			if (accounts.get(i).checkForPost(id)) {
				handle = accounts.get(i).getHandle();

			}
		}
		return handle;
	}

	// Analytics Methods
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public int getTotalOriginalPosts() {
		int count = 0;
		for (int i = 0; i < accounts.size(); i++) {
			count += accounts.get(i).getNoOriginalPosts();
		}

		return count;
	}

	public int getTotalEndorsmentPosts() {
		int count = 0;
		for (int i = 0; i < accounts.size(); i++) {
			count += accounts.get(i).getNoEndorsements();
		}

		return count;

	}

	public int getTotalCommentPosts() {
		int count = 0;
		for (int i = 0; i < accounts.size(); i++) {
			count += accounts.get(i).getNoComments();
		}

		return count;
	}

	public int getMostEndorsedPost() {
		int count = -1;
		int highestId = -1;
		for (int i = 0; i <= accounts.size(); i++) {
			if (accounts.get(i).getMostEndorsedPost().getNoEndorsements() > count) {
				highestId = accounts.get(i).getMostEndorsedPost().getPostId();
				count = accounts.get(i).getMostEndorsedPost().getNoEndorsements();
			}
		}
		return highestId;
	}

	public int getMostEndorsedAccount() {
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

	public void erasePlatform() {
		for (int i = 0; i <= accounts.size(); i++) {
			accounts.get(i).removeAccount();

		}
	}

	public void savePlatform(String filename) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this);
		} catch (IOException e) {
			throw e;
		}
	}

	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try (FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			SocialMedia loaded = (SocialMedia) ois.readObject();
			this.nextID = loaded.nextID;
			this.accounts = loaded.accounts;
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		}
	}
}
