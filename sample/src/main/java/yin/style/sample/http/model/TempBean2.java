package yin.style.sample.http.model;

import java.util.List;

public class TempBean2{

    /**
     * result : -5
     * message : 密签不能为空
     * data : []
     * page : {}
     */

    private int result;
    private String message;
    private PageBean page;
    private List<?> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class PageBean {
    }
}
