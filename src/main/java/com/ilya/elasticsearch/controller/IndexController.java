package com.ilya.elasticsearch.controller;

import com.ilya.elasticsearch.document.Person;
import com.ilya.elasticsearch.service.IndexService;
import com.ilya.elasticsearch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/index")
public class IndexController {
    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/recreate")
    public void recreateAllIndices(@PathVariable final String id) {
        this.indexService.recreateIndices(true);
    }
}
