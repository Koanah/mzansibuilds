package com.mzansibuilds.service;

import com.mzansibuilds.entity.Comment;
import com.mzansibuilds.entity.Project;
import com.mzansibuilds.entity.User;
import com.mzansibuilds.repository.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(String message, User user, Project project) {
        Comment comment = new Comment(message, user, project);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForProject(Project project) {
        return commentRepository.findByProject(project);
    }
}