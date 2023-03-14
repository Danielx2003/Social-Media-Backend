public class Comment extends Post {
	
	private Post post;
	
	public Comment(Account account, Post post, String message){
		super(Account, message)
		this.post = post;
		
	}
	
}