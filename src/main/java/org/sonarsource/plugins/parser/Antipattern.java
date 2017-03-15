package org.sonarsource.plugins.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Antipattern {
    private String name;
    private String severity;
}
