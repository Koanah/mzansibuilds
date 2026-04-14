package com.mzansibuilds.service;

import com.mzansibuilds.entity.CollaborationRequest;
import com.mzansibuilds.entity.Project;
import com.mzansibuilds.entity.User;
import com.mzansibuilds.repository.CollaborationRequestRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CollaborationRequestService {

    private final CollaborationRequestRepository repo;

    public CollaborationRequestService(CollaborationRequestRepository repo) {
        this.repo = repo;
    }

    public CollaborationRequest sendRequest(User user, Project project) {
        if (repo.existsByUserAndProject(user, project)) {
            throw new IllegalStateException("You already requested to collaborate on this project =)");
        }
        CollaborationRequest request = new CollaborationRequest(user, project);
        return repo.save(request);
    }

    public List<CollaborationRequest> getRequestsForProject(Project project) {
        return repo.findByProject(project);
    }

    public CollaborationRequest updateStatus(CollaborationRequest request, String status) {
        request.setStatus(status);
        return repo.save(request);
    }
}