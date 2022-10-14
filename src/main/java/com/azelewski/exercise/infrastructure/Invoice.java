package com.azelewski.exercise.infrastructure;

import com.azelewski.exercise.model.Computer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@JsonRootName("faktura")
public class Invoice {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("komputer")
    private List<Computer> computers;
}
