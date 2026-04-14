package com.mzansibuilds.repository;

import com.mzansibuilds.entity.CollaborationRequest;
import com.mzansibuilds.entity.Project;
import com.mzansibuilds.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Long> {

    List<CollaborationRequest> findByProject(Project project);

    List<CollaborationRequest> findByUser(User user);

    boolean existsByUserAndProject(User user, Project project);
}