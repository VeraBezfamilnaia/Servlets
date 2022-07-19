package validator;

import exception.NotFoundException;
import model.Post;

import java.util.List;

public class PostValidator {
    public static Post validate(List<Post> posts, Post post) {
        long id = post.getId();
        for (var existingPost : posts) {
            if (existingPost.getId() == id) {
                return existingPost;
            }
        }
        throw new NotFoundException("Post with this id wasn't found");
    }

    public static void validate(List<Post> posts) {
        if (posts.isEmpty()) {
            throw new NotFoundException("Any posts haven't been saved yet");
        }
    }

    public static Post validate(List<Post> posts, long id) {
        for (var post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        throw new NotFoundException("Post were not found");
    }

}
