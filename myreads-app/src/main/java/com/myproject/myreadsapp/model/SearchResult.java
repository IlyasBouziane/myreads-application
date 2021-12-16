package com.myproject.myreadsapp.model;

import java.util.List;

public class SearchResult {
    private String numFound;
    private List<SearchResultBooks> docs;
    
    public SearchResult() {
    }
    public String getNumFound() {
        return numFound;
    }
    public void setNumFound(String numFound) {
        this.numFound = numFound;
    }
    public List<SearchResultBooks> getDocs() {
        return docs;
    }
    public void setDocs(List<SearchResultBooks> docs) {
        this.docs = docs;
    }
    

}
