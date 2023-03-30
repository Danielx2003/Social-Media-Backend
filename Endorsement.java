package socialmedia;

public class Endorsement extends Post {
	
	private Post post;
	

	//Constructors 
	
	public Endorsement(Account account, Post post){
		super(account, "EP@" + account.getHandle() + ": " + post.getMessage());
		this.post = post;
		assert this.account.getID() == account.getID();
	}
	
	//Methods
	@Override
	public void deletePost(){
		post.removeEndorsement(this);
		this.post = null;
		this.account.removePost(this);
		
	}
	
	
	
}
