package no.hvl.dat109.Uno.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientForwardController {
    
    @GetMapping(value= "/**")
    public String forward(){
        return "forward:/";
    }
}
