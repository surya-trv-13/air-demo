package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.EDOSubmissionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EDOSubmissionHistoryRepository extends JpaRepository<EDOSubmissionHistory, Long> {
}
