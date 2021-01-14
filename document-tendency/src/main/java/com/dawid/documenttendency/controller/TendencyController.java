package com.dawid.documenttendency.controller;

import com.dawid.documenttendency.model.document.DocumentPopularDto;
import com.dawid.documenttendency.model.document.DocumentTrendDto;
import com.dawid.documenttendency.model.document.DocumentService;
import com.dawid.documenttendency.util.InputParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@RestController
@RequestMapping("/tendencies")
public class TendencyController {


    private DocumentService documentService;
    private InputParser inputParser;

    public TendencyController(DocumentService documentService, InputParser inputParser) {
        this.documentService = documentService;
        this.inputParser = inputParser;
    }

    @GetMapping("/popular")
    public List<DocumentPopularDto> getPopularDocuments(){
        return documentService.getPopularForPreviousWeek();
    }

    @GetMapping("/popular/period")
    public List<DocumentPopularDto> getPopularDocumentsByPeriod(@RequestParam String fromDate, @RequestParam String toDate){
        return documentService.getPopularForPeriod(inputParser.parseDate(fromDate),inputParser.parseDate(toDate));
    }


    @GetMapping("/trending")
    public List<DocumentTrendDto> getTrendingDocuments(){
        return documentService.getTrendsForPreviousWeek();
    }

    @GetMapping("/trending/period")
    public List<DocumentTrendDto> getTrendingDocumentsByPeriod(@RequestParam @NotEmpty String fromDate, @RequestParam @NotEmpty String toDate){
        return documentService.getTrendsForPeriod(inputParser.parseDate(fromDate),inputParser.parseDate(toDate));
    }


}
