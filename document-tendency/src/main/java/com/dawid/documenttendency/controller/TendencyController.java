package com.dawid.documenttendency.controller;

import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentTrendInfo;
import com.dawid.documenttendency.service.DocumentOpenInfoService;
import com.dawid.documenttendency.service.TrendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tendencies")
public class TendencyController {

    private DocumentOpenInfoService documentOpenInfoService;
    private TrendService trendService;

    public TendencyController(DocumentOpenInfoService documentOpenInfoService, TrendService trendService) {
        this.documentOpenInfoService = documentOpenInfoService;
        this.trendService = trendService;
    }

    @GetMapping
    public List<DocumentOpenInfo> getAll(){
        return documentOpenInfoService.getAll();
    }

    @GetMapping("/popular")
    public List<DocumentDto> getPopularDocuments(@RequestParam(required = false) Integer resultLimit){
        return trendService.getPopular(resultLimit);
    }

    @GetMapping("/pop")
    public DocumentDto getPopularDocument(){
        return trendService.getPopular(1).get(0);
    }

    @GetMapping("/trending")
    public List<DocumentTrendInfo> getTrendingDocuments(){
        return trendService.getTrendsForPreviousWeek();
    }



}
