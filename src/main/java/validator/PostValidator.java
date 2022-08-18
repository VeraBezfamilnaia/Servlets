package validator;

import exception.NotFoundException;
import model.Post;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class PostValidator {
    public static Post validate(Map<Long, Post> posts, long id) {
        var existingPost = posts.get(id);
        if (existingPost != null) {
            return existingPost;
        }
        throw new NotFoundException("Post with this id wasn't found");
    }
}
