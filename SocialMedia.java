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
		if (handle.length() > 30 || handle.length() == 0) { //Test for correct handle length
			throw new InvalidHandleException();
		}

		for (int i = 0; i <= handle.length(); i++) {
			if (Character.isWhitespace(handle.charAt(i))) { //Test for whitespace
				throw new InvalidHandleException();
			}
		}

		for (int i = 0; i < accounts.size(); i++) { 
			if (accounts.get(i).getHandle() == handle) { //Test for existing handle
				throw new IllegalHandleException();
			}
		}

		Account account = new Account(nextID++, handle); // Create new Account
		assert account.getID() == nextID - 1;
		assert account.getHandle() == handle;
		accounts.add(account);
		return account.getID();
	}

	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.length() == 0) { //Test for correct length
			throw new InvalidHandleException();
		}

		for (int i = 0; i <= handle.length(); i++) {
			if (Character.isWhitespace(handle.charAt(i))) { //Test for whitespace
				throw new InvalidHandleException();
			}
		}

		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) { //Test for existing handle
				throw new IllegalHandleException();
			}
		}

		Account account = new Account(nextID++, handle, description); // Create new Account
		assert account.getID() == nextID - 1;
		assert account.getHandle() == handle;
		assert account.getDescription() == description;
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

		for (int i = 0; i <= newHandle.length(); i++) {
			if (Character.isWhitespace(newHandle.charAt(i))) { //Tests new handle doesn't have whitespace
				throw new InvalidHandleException();
			}
		}

		for (int i = 0; i < accounts.size(); i++) { 
			if (accounts.get(i).getHandle() == newHandle) { //Tests new handle is unique/ not used
				throw new IllegalHandleException();
			}
		}

		account.setHandle(newHandle);
		assert account.getHandle() == newHandle;

	}

	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		Account account = getAccountByHandle(handle);
		account.setDescription(description);
		assert account.getDescription() == description;

	}

	private Account getAccountByHandle(String handle) throws HandleNotRecognisedException {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) { //Handle matches handle of account in arrayList
				return accounts.get(i);
			}

		}
		throw new HandleNotRecognisedException(); //Error thrown because no account with a matching handle was found
	}
  
	private Account getAccountById(int id) throws AccountIDNotRecognisedException {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getID() == id) { 
				return accounts.get(i);
			}
		} 
		throw new AccountIDNotRecognisedException(); //Error thrown because no account with a matching ID was found
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
		if (message.length() == 0 || message.length() > 100) { //Tests message length is valid
			throw new InvalidPostException();
		}
		Post post = new Post(account, message); //Creates new post object
		account.addPost(post);
		assert post.getMessage() == message;
		return post.getPostId();
	}

	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
				
		Account account = getAccountByHandle(handle);
		Post post = getPostById(id);
		
		int noEndorsements = post.getNoEndorsements();
		int accountNoEndorsement = post.getAccount().getEndorsementsRecieved();
		
		if (post instanceof Endorsement) { //Checks if object is an instance of endorsement
			throw new NotActionablePostException(); 
		}
		Endorsement endorsement = new Endorsement(account, post);
		account.addEndorsement(endorsement);
		post.addEndorsement(endorsement);
		assert post.getNoEndorsements() == noEndorsements + 1;
		assert post.getAccount().getEndorsementsRecieved() == accountNoEndorsement + 1;
		return endorsement.getPostId();
	}

	private Post getPostById(int postId) throws PostIDNotRecognisedException {
		Post post = null;
		for (int i = 0; i < accounts.size(); i++) { 
			try {
				post = accounts.get(i).getPostById(postId);
			} catch (PostIDNotRecognisedException e) {
			}
		}
		if (post != null) { 
			return post;
		} else {
			throw new PostIDNotRecognisedException(); //Thrown if post ID is in use
		}
	}

	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		Account account = getAccountByHandle(handle);
		Post post = getPostById(id);
				
		int noPostComments = post.getNoComments();
		if (post instanceof Endorsement) {
			throw new NotActionablePostException(); //Thrown if object is an endorssement
		}
		if (message.length() == 0 || message.length() > 100) { //Tests message length is valid
			throw new InvalidPostException();
		}
		Comment comment = new Comment(account, post, message);
		account.addComment(comment);
		post.addComment(comment);
		assert comment.getMessage() == message;
		assert post.getNoComments() == noPostComments + 1;
		return comment.getPostId();
	}

	public void deletePost(int id) throws PostIDNotRecognisedException {
		Post post = getPostById(id);
		post.deletePost();

	}

	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		Post post = getPostById(id);

		String toReturn = "ID: " + id
				+ "\nAccount: " + getAccountHandleByPostId(id)
				+ "\nNo. Endorsements: " + post.getNoEndorsements() + "No. Comments: " + post.getNoComments()
																												
																											
																												
																												
				+ "\n" + post.getMessage();

		return toReturn;
	}

	public StringBuilder showPostChildrenDetails(int id) //Starts a recursive method to search for comments using depth-first search
			throws PostIDNotRecognisedException, NotActionablePostException {
		Post post = getPostById(id);
		if (post instanceof Endorsement) { //Checks if post is of type Endorsement
			throw new NotActionablePostException();
		}
		StringBuilder toReturn = new StringBuilder(showIndividualPost(id)); //Creates new string builder
		ArrayList<Comment> comments = post.getCommentsOnPost(); //Gets comments on current post
		if (comments.size() == 0) {
			return toReturn;
		} else {
			toReturn.append("\n|");
			for (int i = 0; i < comments.size(); i++) {
				toReturn.append(showPartialChildrenDetails(post, 1)); 
																	
			}
			return toReturn;
		}
	}

	private StringBuilder showPartialChildrenDetails(Post post, int depth) //Continues depth-first search from previous method
			throws PostIDNotRecognisedException {
		int postId = post.getPostId();
		String space = " ";
		String lineStart = "\n" + space.repeat(4 * depth); //Determines how much indentation should be used
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
			for (int i = 0; i < comments.size(); i++) {
				toReturn.append(showPartialChildrenDetails(post, depth + 1));
			}
			return toReturn;
		}
	}

	private String getAccountHandleByPostId(int id) {
		String handle = "";
		for (int i = 0; i < accounts.size(); i++) {
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
		for (int i = 0; i < accounts.size(); i++) {
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
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getEndorsementsRecieved() > count) {
				highestId = accounts.get(i).getID();
				count = accounts.get(i).getEndorsementsRecieved();
			}
		}
		return highestId;
	}

	// Management related methods

	public void erasePlatform() {
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).removeAccount();

		}
		assert accounts.size() == 0;
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
