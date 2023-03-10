import java.util.ArrayList;

public class SocialMedia implements SocialMediaInterface {
	
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
		
		accounts.add(new Account(nextId++, handle);
	}
	
	int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		accounts.add(new Account(nextId++, handle, description);
	}
	
	void removeAccount(int id) throws AccountIDNotRecognisedException{
		//TO DO
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
		for (int i = 0; i<= accounts.size(); i++){
			if (accounts[i].getHandle() == handle){
				return accounts[i];
			}
			
		}
		throw HandleNotRecognisedException;
	}
	
	String showAccount(String handle) throws HandleNotRecognisedException{
		return "";
	}
	
	//Post Methods
	
	int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException{
		return 0;
	}
	
	int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException{
				return 0;
			}
			
	int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException{
				return 0;
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
		for (int i = 0; i<= accounts.size(); i++){
			count += accounts[i].getNoOriginalPosts();
		}

		return count;
	}
	int getTotalEndorsmentPosts() {
		int count = 0;
		for (int i = 0; i<= accounts.size(); i++){
			count += accounts[i].getNoEndorsements();
		}

		return count;

	}
	int getTotalCommentPosts() {
		int count = 0;
		for (int i = 0; i<= accounts.size(); i++){
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
