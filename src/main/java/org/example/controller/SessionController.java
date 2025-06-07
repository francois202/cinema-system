package org.example.controller;

import org.example.model.Session;
import org.example.service.SessionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String listSessions(Model model) {
        model.addAttribute("sessions", sessionService.findAll());
        model.addAttribute("sessionsCount", sessionService.findAll().size());
        return "sessions";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("session", new Session());
        return "add_session";
    }

    @PostMapping("/add")
    public String addSession(@ModelAttribute Session session) {
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Session session = sessionService.findById(id).orElseThrow();
        model.addAttribute("session", session);
        return "edit_session";
    }

    @PostMapping("/edit/{id}")
    public String updateSession(@PathVariable Long id, @ModelAttribute Session session) {
        session.setId(id);
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.deleteById(id);
        return "redirect:/sessions";
    }

    @GetMapping("/search")
    public String searchSessions(
            @RequestParam(required = false) String movieTitle,
            @RequestParam(required = false) String studio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<Session> results = null;

        if (movieTitle != null && !movieTitle.isEmpty()) {
            results = sessionService.searchByMovieTitle(movieTitle);
        } else if (studio != null && !studio.isEmpty()) {
            results = sessionService.searchByStudio(studio);
        } else if (date != null) {
            results = sessionService.searchByDate(date);
        } else {
            results = sessionService.findAll();
        }

        model.addAttribute("sessions", results);
        model.addAttribute("sessionsCount", results.size());
        return "sessions";
    }

    @GetMapping("/stats")
    @ResponseBody
    public List<Object[]> getSessionsStats() {
        return sessionService.getSessionsCountByDate();
    }
}
