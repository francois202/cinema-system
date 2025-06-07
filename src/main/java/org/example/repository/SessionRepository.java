package org.example.repository;

import org.example.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByMovieTitleContainingIgnoreCase(String title);
    List<Session> findByStudioContainingIgnoreCase(String studio);

    @Query("SELECT s FROM Session s WHERE DATE(s.sessionDateTime) = :date")
    List<Session> findByDate(LocalDate date);

    @Query("SELECT DATE(s.sessionDateTime) as date, COUNT(s) as count FROM Session s GROUP BY DATE(s.sessionDateTime)")
    List<Object[]> countSessionsByDate();
}