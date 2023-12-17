package com.blog.blog.services;

import com.blog.blog.model.Comment;
import com.blog.blog.model.Post;
import com.blog.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("sanchessganza@gmail.com")
    private String adminEmail;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public Comment createComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);

        // Notify admin about the new comment
        sendAdminNotification(savedComment);

        // Notify the user who posted the comment
        sendUserNotification(savedComment);

        return savedComment;
    }

    public Comment updateComment(Long commentId, Comment commentDetails) {
        Comment comment = getCommentById(commentId);
        comment.setContent(commentDetails.getContent());
        // Update other fields as needed
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
    }

    private void sendAdminNotification(Comment comment) {
        String subject = "New Comment on Post #"+comment.getPost().getId() + ", " + comment.getPost().getTitle();
        String message = "A new comment has been posted on Post #"+comment.getPost().getId() + ", " + comment.getPost().getTitle() +
                " by user " + comment.getEmail() +
                "\n\nComment Content:\n" + comment.getContent();

        sendEmail(adminEmail, subject, message);
    }

    private void sendUserNotification(Comment comment) {
        String subject = "Your Comment on Post #" + comment.getPost().getId() + ", " + comment.getPost().getTitle() + " Received";
        String message = "Thank you for your comment on Post #" + comment.getPost().getId() + ", " + comment.getPost().getTitle() +
                "\n\nComment Content:\n" + comment.getContent() +
                "\n\nYour comment has been received successfully.";

        sendEmail(comment.getEmail(), subject, message);
    }

    private void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }
}
