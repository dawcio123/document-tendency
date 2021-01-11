package com.dawid.documenttendency;

import com.dawid.documenttendency.model.DocumentDto;
import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.model.DocumentTrend;
import com.dawid.documenttendency.service.DocumentOpenInfoService;
import com.dawid.documenttendency.service.TrendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    public List<DocumentDto> getPopularDocuments(){
        return trendService.getPopular();
    }

    @GetMapping("/trending")
    public List<DocumentTrend> getTrendingDocuments(){
        return trendService.getTrendsForPreviousWeek();
    }



}
