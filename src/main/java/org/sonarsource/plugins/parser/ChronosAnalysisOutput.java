package org.sonarsource.plugins.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChronosAnalysisOutput {
    private Collection<File> files;

    public ChronosAnalysisOutput() {
        this.files = new ArrayList<>();
    }
}
