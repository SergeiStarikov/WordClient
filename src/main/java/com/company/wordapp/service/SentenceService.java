package com.company.wordapp.service;

import com.company.wordapp.model.Sentence;
import com.company.wordapp.repository.SentenceRepository;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SentenceService {

    private final SentenceRepository sentenceRepository;

    public void save(Exchange exchange) {
        Sentence sentence = new Sentence();
        sentence.setValue((String) exchange.getMessage().getBody());
        sentenceRepository.insert(sentence);
    }
    
    public List<Sentence> searchByValue(String id) {
        return sentenceRepository.searchById(id);
    }
}
