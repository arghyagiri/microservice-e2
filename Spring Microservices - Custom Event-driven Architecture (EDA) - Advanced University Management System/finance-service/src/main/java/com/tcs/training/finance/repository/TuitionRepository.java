package com.tcs.training.finance.repository;

import com.tcs.training.finance.entity.Tuition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuitionRepository extends JpaRepository<Tuition, Long> {

	Tuition findByCourseId(Long courseId);

}
