public class Endorsement extends Post {
	
	private Post post;
	

	//Constructors 
	
	public Endorsement(Account account, Post post){
		super(account, "EP@" + [account.getHandle()] + ": " + [post.getMessage()]);
		this.post = post;
	}
	
	//Methods
	public void deletePost(){
		
	}
}
