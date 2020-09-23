package com.company.wordapp.repository;

import com.company.wordapp.model.Sentence;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SentenceRepository extends CassandraRepository<Sentence, UUID> {
    List<Sentence> searchById(String id);
}
