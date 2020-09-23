package com.company.wordapp.controller;

import com.company.wordapp.model.Sentence;
import com.company.wordapp.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/sentence")
public class SentenceController {
    
    @Autowired
    SentenceService sentenceService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<String> search(@RequestParam String id) {
        
        List<Sentence> sentences = sentenceService.searchByValue(id);
        StringBuilder result = new StringBuilder();
        sentences.forEach(item -> result.append(item.getValue()));
        
        return ResponseEntity.status(HttpStatus.OK).body(result.toString());
    }
}
