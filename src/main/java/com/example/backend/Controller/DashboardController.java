package com.example.backend.Controller;

import com.example.backend.Services.AuthenticationStatistics;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/Dashboard")
public class DashboardController {

    private final AuthenticationStatistics authenticationStatisticsService;

    public DashboardController(AuthenticationStatistics authenticationStatisticsService) {
        this.authenticationStatisticsService = authenticationStatisticsService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        int totalUserCount = authenticationStatisticsService.getTotalUserCount();
        int lockedUserCount = authenticationStatisticsService.getLockedUserCount();
        List<String> topFailedLoginUsers = authenticationStatisticsService.getTopFailedLoginUsers(10);

        model.addAttribute("totalUserCount", totalUserCount);
        model.addAttribute("lockedUserCount", lockedUserCount);
        model.addAttribute("topFailedLoginUsers", topFailedLoginUsers);

        return "dashboard";
    }
}
