package org.sonarsource.plugins.parser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class File {
    private String path;
    private List<Antipattern> antipatterns;

    @JsonIgnore
    public List<String> getPaths() {
        // handle maven multi-modules projects
        List<String> result = new ArrayList<>();
        result.add(path);
        int index = path.indexOf("/src/");
        if (index != -1) {
            result.add(path.substring(index + 1));
        }
        return result;
    }
}
