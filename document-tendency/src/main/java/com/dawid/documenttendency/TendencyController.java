package com.dawid.documenttendency;

import com.dawid.documenttendency.model.DocumentOpenInfo;
import com.dawid.documenttendency.service.DocumentOpenInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tendencies")
public class TendencyController {

    private DocumentOpenInfoService documentOpenInfoService;

    public TendencyController(DocumentOpenInfoService documentOpenInfoService) {
        this.documentOpenInfoService = documentOpenInfoService;
    }

    @GetMapping
    public List<DocumentOpenInfo> getAll(){
        return documentOpenInfoService.getAll();
    }

    @GetMapping("/popular")
    public List<DocumentOpenInfo> getPopularDocuments(){
        return documentOpenInfoService.getPopular();
    }
}
