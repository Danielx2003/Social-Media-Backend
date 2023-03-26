package socialmedia;

public class Comment extends Post {

	private static Post emptyPost = new Post();
	private Post post;

	public Comment(Account account, Post post, String message) {
		super(account, message); // added semi colon
		this.post = post;

	}

	public void deleteReference() {
		post = emptyPost;
	}

	@Override
	public void deletePost() {
		post.removeComment(this);
		post = null;
		super.deletePost();
	}

}