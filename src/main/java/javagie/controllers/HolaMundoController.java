/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javagie.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author eduardo
 */
@Controller
@RequestMapping("/mvc")
public class HolaMundoController {
    private static Logger log = LoggerFactory.getLogger(HolaMundoController.class);
    
    @RequestMapping("/holamundo")
    public String irHolaMundo() {
        log.debug("entre a irHolaMundo");
        return "holamundo";
    }
    
}
