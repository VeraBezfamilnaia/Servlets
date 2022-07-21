package netology.repository;

import netology.model.Post;
import netology.validator.PostValidator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostRepositoryImpl implements PostRepository {
    private final static int NEW_POST_INDEX = 0;
    private final static int START_POST_INDEX = 1;
    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private long counter = START_POST_INDEX;

    @Override
    public List<Post> all() {
        PostValidator.validate(posts);
        return posts;
    }

    @Override
    public Optional<Post> getById(long id) {
        for (var post : posts) {
            if (post.getId() == id) {
                return Optional.of(post);
            }
            break;
        }
        return Optional.empty();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == NEW_POST_INDEX) {
            addNewPost(post);
        } else {
            updatePost(post);
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        var existingPost = PostValidator.validate(posts, id);
        posts.remove(existingPost);
    }

    private void addNewPost(Post post) {
        post.setId(counter++);
        posts.add(post);
    }

    private void updatePost(Post post) {
        var existingPost = PostValidator.validate(posts, post);
        existingPost.setContent(post.getContent());
    }
}