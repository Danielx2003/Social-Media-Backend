import java.util.ArrayList;

public class SocialMedia implements SocialMediaInterface, Serializable {
	
	//Attributes
	
	private int noAccounts;
	private int nextID;
	private int noPosts;
	private int noComments;
	private int noEndorsements;
	private ArrayList<Account> accounts;

	//Constructors
	public SocialMedia() {
		noAccounts = 0;
		nextID = 1;
		noPosts = 0;
		noComments = 0;
		noEndorsements = 0;
		accounts = new ArrayList<>();
	}

	//Account Creation, Deletion and Updating Methods
	int createAccount(String handle) throws IllegalHandleException, InvalidHandleException{
		if (handle.length() > 30 || handle.length() == 0){
			throw InvalidHandleException;
		}
		for (i = 0; i< accounts.size(); i++){
			if (accounts[i].getHandle() == handle) {
				throw IllegalHandleException;
			}
		}
		
		for (i = 0; i< accounts.size(); i++){
			if (accounts[i].getHandle() == handle) {
				throw IllegalHandleException;
			}
		}

		Account account = new Account(nextId++, handle);
		accounts.add(account);
		account.getID()
	}
	
	int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.length() == 0){
			throw InvalidHandleException;
		}
		
		for (int i=0; i< handle.length(); i++) {
			if (Character.isWhitespace(handle.charAt(i))){
				throw InvalidHandleException;
			}
		}
		
		for (i = 0; i< accounts.size(); i++){
			if (accounts[i].getHandle() == handle) {
				throw IllegalHandleException;
			}
		}
		
		Account account = new Account(nextId++, handle, description);
		accounts.add(account);
		account.getID();
	}
	
	void removeAccount(int id) throws AccountIDNotRecognisedException{
		//TO DO
		Account account = getAccountById(id);
				
	}
	
	void removeAccount(String handle) throws HandleNotRecognisedException {
		//TO DO
	}
	
	void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException{

			}
	
	void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		Account account = getAccountByHandle(handle);
		account.setDescription(description);
		
	}
	
	Account getAccountByHandle(String handle) throws HandleNotRecognisedException{
		for (int i = 0; i< accounts.size(); i++){
			if (accounts[i].getHandle() == handle){
				return accounts[i];
			}
			
		}
		throw HandleNotRecognisedException;
	}
	
	Account getAccountById(int id) throws IDNotRecognisedException{
		for (int i = 0; i< accounts.size(); i++){
			if (accounts[i].getId() == id){
				return accounts[i];
			}
		throw IDNotRecognisedException;
		}
	
	String showAccount(String handle) throws HandleNotRecognisedException{
		return "";
	}
	
	//Post Methods
	
	int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException{
		Account account = getAccountByHandle(handle);
		if (message.length() == 0 || message.length() > 100) {
			throw InvalidPostException;
		}
		Post post = new Post(account, message);
		account.addPost(post);
		return post.getPostId();
	}
	
	
	
	int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException{
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
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException{
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
			
	void deletePost(int id) throws PostIDNotRecognisedException{
		//TO DO
	}
	
	String showIndividualPost(int id) throws PostIDNotRecognisedException{
		return "";
	}
	
	StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException{
		return "";
	}
	
	//Analytics Methods
	int getNumberOfAccounts() {
		return accounts.size();
	}
	int getTotalOriginalPosts() {
		int count = 0;
		for (int i = 0; i< accounts.size(); i++){
			count += accounts[i].getNoOriginalPosts();
		}

		return count;
	}
	int getTotalEndorsmentPosts() {
		int count = 0;
		for (int i = 0; i< accounts.size(); i++){
			count += accounts[i].getNoEndorsements();
		}

		return count;

	}
	int getTotalCommentPosts() {
		int count = 0;
		for (int i = 0; i< accounts.size(); i++){
			count += accounts[i].getNoComments();
		}

		return count;
	}
	int getMostEndorsedPost(){
		return 0;
	}
	
	int getMostEndorsedAccount(){
		return 0;
	}
	
	//Management related methods
	
	void erasePlatform(){
		//TO DO
	}
	
	void savePlatform(String filename) throws IOException{
		//TO DO
	}
	
	void loadPlatform(String filename) throws IOException, ClassNotFoundException{
		//TO DO
	}
}
