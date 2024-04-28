package tbd.group3.control2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbd.group3.control2.services.TareaService;

@RestController
@RequestMapping("/tasks")
public class TareaController {
    @Autowired
    TareaService tareaService;

}
