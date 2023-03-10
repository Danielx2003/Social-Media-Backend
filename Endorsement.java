public class Endorsement extends Post {
	
	private int referenceId;
	

	//Constructors 
	
	public Endorsement(int accountId, String accountHandle int referenceId, String message){
		super(accountId, "EP@" + [accountHandle] + ": " + [message]);
		this.referenceId = referenceId;
	}
	
	//Methods
	public void deletePost(){
		
	}
}
