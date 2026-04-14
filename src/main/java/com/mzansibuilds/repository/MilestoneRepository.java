package com.mzansibuilds.repository;

import com.mzansibuilds.entity.Milestone;
import com.mzansibuilds.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findByProject(Project project);
}