package com.blog.blog.services;

import com.blog.blog.model.Post;
import com.blog.blog.model.User;
import com.blog.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

            // Trigger lazy loading of comments
            post.getComments().size();

            return post;
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Post> getPostsByAuthor(User author) {
        return postRepository.findByAuthor(author);
    }

    public Post createPost(Post post) {
        // Perform validation or additional logic if needed
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post postDetails) {
        Post post = getPostById(postId);
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setImages(postDetails.getImages());
        // Update other fields as needed
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
