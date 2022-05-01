package ca.sheridancollege.vuhi.controllers;

import ca.sheridancollege.vuhi.beans.Team;
import ca.sheridancollege.vuhi.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private DatabaseAccess da;

    @GetMapping("/")
    public String home(Model model){
        return "home";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("team",new Team());
        model.addAttribute("teamList", da.selectAllTeams());
        return "add";
    }

    @PostMapping("/addTeam")
    public String addTeam(Model model, @ModelAttribute Team team){
        da.insertTeam(team);
        model.addAttribute("team", new Team());
        model.addAttribute("teamList",da.selectAllTeams());
        return "add";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        model.addAttribute("teamList", da.selectAllTeams());
        model.addAttribute("team", new Team());
        return "edit";
    }

    @GetMapping("/editTeamById/{teamID}")
    public String editTeamById(Model model, @PathVariable int teamID){
        Team team = da.selectTeamById(teamID).get(0);
        model.addAttribute("team",team);
        return "edit2";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Team team){
        da.updateTeam(team);
        model.addAttribute("teamList",da.selectAllTeams());
        model.addAttribute("team",new Team());
        return "edit";
    }

    @PostMapping("/editSearch")
    public String editSearch(Model model, @ModelAttribute Team team){
        model.addAttribute("teamList", da.selectTeamsByCondition(team));
        model.addAttribute("team",new Team());
        return "edit";
    }

    @PostMapping("/deleteSearch")
    public String deleteSearch(Model model, @ModelAttribute Team team){
        model.addAttribute("teamList", da.selectTeamsByCondition(team));
        model.addAttribute("team",new Team());
        return "delete";
    }


    @GetMapping("/delete")
    public String delete(Model model){
        model.addAttribute("teamList", da.selectAllTeams());
        model.addAttribute("team", new Team());
        return "delete";
    }

    @GetMapping("/deleteTeamById/{teamID}")
    public String deleteTeamById(Model model, @PathVariable int teamID) {
        da.deleteTeamById(teamID);
        model.addAttribute("teamList", da.selectAllTeams());
        model.addAttribute("team", new Team());
        return "delete";
    }

    @GetMapping("/display")
    public String display(Model model){
        model.addAttribute("teamList",da.selectAllTeams());
        model.addAttribute("team",new Team());
        return "display";
    }

    @PostMapping("/sort")
    public String sort(Model model, @ModelAttribute Team team){
        model.addAttribute("teamList",da.sortTeam(team));
        model.addAttribute("team",new Team());
        return "display";
    }
}
