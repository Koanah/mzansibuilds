package com.mzansibuilds.repository;

import com.mzansibuilds.entity.Comment;
import com.mzansibuilds.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByProject(Project project);
}