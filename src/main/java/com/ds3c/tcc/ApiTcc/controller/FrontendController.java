package com.ds3c.tcc.ApiTcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping(value = { "/", "/{path:[^\\.]*}" })
    public String forward() {
        // Retorna o index.html do build React
        return "forward:/index.html";
    }
}
