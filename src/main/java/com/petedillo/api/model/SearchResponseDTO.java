package com.petedillo.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchResponseDTO {
    private String searchTerm;
    private List<BlogPost> results;

    public SearchResponseDTO(String searchTerm, List<BlogPost> results) {
        this.searchTerm = searchTerm;
        this.results = results;
    }

}
