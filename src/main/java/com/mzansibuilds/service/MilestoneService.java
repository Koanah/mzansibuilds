package com.mzansibuilds.service;

import com.mzansibuilds.entity.Milestone;
import com.mzansibuilds.entity.Project;
import com.mzansibuilds.repository.MilestoneRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public Milestone addMilestone(String title, String description,
                                  LocalDate date, Project project) {
        Milestone milestone = new Milestone(title, description, date, project);
        return milestoneRepository.save(milestone);
    }

    public List<Milestone> getMilestonesForProject(Project project) {
        return milestoneRepository.findByProject(project);
    }

    public void deleteMilestone(Long id) {
        milestoneRepository.deleteById(id);
    }
}