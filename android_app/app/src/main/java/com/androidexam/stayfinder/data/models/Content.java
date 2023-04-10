package com.androidexam.stayfinder.data.models;

import java.util.List;

public class Content {
    private List<Hostel> content;
    private int page;
    private int size;
    private int totalElement;
    private int totalPages;
    private boolean last;

    public Content(List<Hostel> content, int page, int size, int totalElement, int totalPages, boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElement = totalElement;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<Hostel> getContent() {
        return content;
    }

    public void setContent(List<Hostel> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(int totalElement) {
        this.totalElement = totalElement;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
