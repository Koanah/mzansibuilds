package com.mzansibuilds.service;

import com.mzansibuilds.entity.Project;
import com.mzansibuilds.entity.User;
import com.mzansibuilds.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String title, String description,
                                 String stage, String supportRequired, User user) {
        Project project = new Project(title, description, stage, supportRequired, user);
        return projectRepository.save(project);
    }

    public List<Project> getProjectsByUser(User user) {
        return projectRepository.findByUser(user);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getFeedForUser(User user) {
        return projectRepository.findByUserNot(user);
    }

    public List<Project> getCompletedProjects() {
        return projectRepository.findByStatus("COMPLETED");
    }

    public Project markCompleted(Project project) {
        project.setStatus("COMPLETED");
        return projectRepository.save(project);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }
}