package netology.config;

import netology.controller.PostController;
import netology.repository.PostRepository;
import netology.repository.PostRepositoryImpl;
import netology.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean
    public PostController postController(PostService service) {
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepository repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepositoryImpl();
    }
}
