package br.edu.iff.cinetrack.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class viewMainController {

    @GetMapping("/home")
    @ResponseBody
    public String homeUnderConstruction() {
        return "Página em construção! Volte em breve para conferir as novidades.";
    }

    @GetMapping("/error")
    @ResponseBody
    public String errorPage() throws Exception {
        throw new Exception();
    }
    
}
