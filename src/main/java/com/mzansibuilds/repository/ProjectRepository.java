package com.mzansibuilds.repository;

import com.mzansibuilds.entity.Project;
import com.mzansibuilds.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUser(User user);

    List<Project> findByStatus(String status);

    List<Project> findByUserNot(User user);
}