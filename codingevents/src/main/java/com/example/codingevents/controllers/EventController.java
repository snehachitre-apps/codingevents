package com.example.codingevents.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    private static List<String> events = new ArrayList<>();


    @GetMapping
    public String showEvents( Model model){

        model.addAttribute("events",events);

        return "events/index";
    }

    // /events/create
    @GetMapping("create")
    public String renderCreateEventForm(){
        return "events/create";
    }

    // at /events/create
    @PostMapping("create")
    public String createEvent(@RequestParam String eventName){
        events.add(eventName);
        return "redirect:/events"; //goes to root path to /events

    }
}
