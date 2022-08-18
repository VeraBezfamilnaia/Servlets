package repository;

import model.Post;
import validator.PostValidator;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final static int NEW_POST_INDEX = 0;
    private final static int START_POST_INDEX = 1;
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(START_POST_INDEX);

    public Collection<Post> all() {
        return posts.values();
    }

    public Optional<Post> getById(long id) {
        boolean isKeyContains = posts.containsKey(id);
        if (isKeyContains) {
            return Optional.of(posts.get(id));
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        if (post.getId() == NEW_POST_INDEX) {
            addNewPost(post);
        } else {
            updatePost(post);
        }
        return post;
    }

    public void removeById(long id) {
        var existingPost = PostValidator.validate(posts, id);
        posts.remove(existingPost.getId());
    }

    private void addNewPost(Post post) {
        post.setId(counter.get());
        posts.put(Long.parseLong(counter.toString()), post);
        counter.incrementAndGet();
    }

    private void updatePost(Post post) {
        var existingPost = PostValidator.validate(posts, post.getId());
        existingPost.setContent(post.getContent());
    }
}