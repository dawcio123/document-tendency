package com.dawid.documenttendency.controller;

import com.dawid.documenttendency.model.document.Document;
import com.dawid.documenttendency.model.document.TrendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tendencies")
public class TendencyController {


    private TrendService trendService;

    public TendencyController(TrendService trendService) {
        this.trendService = trendService;
    }



    @GetMapping("/popular/period")
    public List<Document> getPopularDocuments(@RequestParam String fromDate, @RequestParam String toDate){
        return trendService.getPopularForPeriod(fromDate,toDate);
    }


    @GetMapping("/trending")
    public List<Document> getTrendingDocuments(){
        return trendService.getTrendsForPreviousWeek();
    }

    @GetMapping("/trending/period")
    public List<Document> getTrendingDocumentsByPeriod(@RequestParam String fromDate, @RequestParam String toDate){

        return trendService.getTrendsForPeriod(fromDate,toDate);
    }


}
