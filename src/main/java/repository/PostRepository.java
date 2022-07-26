package repository;

import model.Post;
import validator.PostValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final static int NEW_POST_INDEX = 0;
    private final static int START_POST_INDEX = 1;
    private final Map<Post, AtomicInteger> posts = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(START_POST_INDEX);

    public Set<Post> all() {
        return getPosts();
    }

    public Optional<Post> getById(long id) {
        for (var post : getPosts()) {
            if (post.getId() == id) {
                return Optional.of(post);
            }
            break;
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        if ( post.getId() == NEW_POST_INDEX) {
            addNewPost(post);
        } else {
            updatePost(post);
        }
        return post;
    }

    public void removeById(long id) {
        var currentPosts = getPosts();
        var existingPost = PostValidator.validate(currentPosts, id);
        posts.remove(existingPost);
    }

    private void addNewPost(Post post) {
        post.setId(counter.getAndIncrement());
        posts.put(post, counter);
    }

    private void updatePost(Post post) {
        var currentPosts = getPosts();
        var existingPost = PostValidator.validate(currentPosts, post);
        existingPost.setContent(post.getContent());
    }

    private Set<Post> getPosts() {
        return posts.keySet();
    }
}