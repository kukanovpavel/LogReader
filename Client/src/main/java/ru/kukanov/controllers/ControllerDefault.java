package ru.kukanov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by kukanovpavel on 13.01.17.
 */
@Controller

public class ControllerDefault {
    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String Hello(){

        return "search";
    }





}
