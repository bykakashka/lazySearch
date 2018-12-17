package com.byka.controller;

import com.byka.service.SearchEngine;
import com.byka.service.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private SearchEngine searchEngine;

    @RequestMapping("/")
    public String index(final Model model) {
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchTerm") String searchTerm, Model model) throws Exception {
        model.addAttribute("result", searchEngine.doSearch(searchTerm));
        return "searchResult";
    }
}
