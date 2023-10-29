package com.example.codingevents.controllers;


import com.example.codingevents.data.EventData;
import com.example.codingevents.models.Event;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    //earlier before model class
//    private static List<Event> events = new ArrayList<>();


    @GetMapping
    public String showEvents( Model model){
        model.addAttribute("title","All Events");
//        model.addAttribute("events",events);
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }

    // /events/create
    @GetMapping("create")
    public String renderCreateEventForm(Model model){
        model.addAttribute("title","Create Event");

        model.addAttribute(new Event());
        return "events/create";
    }

    // at /events/create and model binding
    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event newEvent,
                              Errors errors,Model model){
//        events.add(new Event(eventName,eventDescription));
        if(errors.hasErrors())
        {
            model.addAttribute("title","Create Event");

            return "events/create";
        }
        EventData.add(newEvent);
        return "redirect:/events"; //goes to root path to /events can also be writtrwn as "redirect:"

    }
     // /events/delete
    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title","Delete Events");
        model.addAttribute("events",EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }

        return "redirect:/events";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId){
        Event eventToEdit = EventData.getById(eventId);
        model.addAttribute("event", eventToEdit);
        String title = "Edit Event " + eventToEdit.getName() + " (id=" + eventToEdit.getId() + ")";
        model.addAttribute("title", title );
        return "events/edit";
    }
    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description) {
        Event eventToEdit = EventData.getById(eventId);
        eventToEdit.setName(name);
        eventToEdit.setDescription(description);
        return "redirect:/events";
    }
}
