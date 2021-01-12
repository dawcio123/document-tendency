package com.dawid.documenttendency.controller;

import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentTrendInfo;
import com.dawid.documenttendency.service.TrendService;
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



    @GetMapping("/popular")
    public List<DocumentDto> getPopularDocuments(@RequestParam(required = false) Integer resultLimit){
        return trendService.getPopular(resultLimit);
    }



    @GetMapping("/trending")
    public List<DocumentTrendInfo> getTrendingDocuments(){
        return trendService.getTrendsForPreviousWeek();
    }


}
