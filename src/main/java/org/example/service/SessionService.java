package org.example.service;

import org.example.model.Session;
import org.example.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public void save(Session session) {
        sessionRepository.save(session);
    }

    public void deleteById(Long id) {
        sessionRepository.deleteById(id);
    }

    public Optional<Session> findById(Long id) {
        return sessionRepository.findById(id);
    }

    public List<Session> searchByMovieTitle(String title) {
        return sessionRepository.findByMovieTitleContainingIgnoreCase(title);
    }

    public List<Session> searchByStudio(String studio) {
        return sessionRepository.findByStudioContainingIgnoreCase(studio);
    }

    public List<Session> searchByDate(LocalDate date) {
        return sessionRepository.findByDate(date);
    }

    public List<Object[]> getSessionsCountByDate() {
        return sessionRepository.countSessionsByDate();
    }
}
