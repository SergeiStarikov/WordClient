package com.company.wordapp.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.stereotype.Indexed;
import java.util.UUID;

@Table
@Indexed
@Data
public class Sentence {
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    
    private String value;

}
