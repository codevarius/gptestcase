/*
author: Kirill Koshaev aka codevarius 06.04.2018
 */
package core;

import java.util.HashMap;

public abstract class Vertex {
    /*
    Abstract vertex class with base fields, getters & setters
     */

    private  String articleName;
    private String articleLink;
    private String publicDate;
    public HashMap<Integer,String> tags;
    public HashMap<Integer,String> parentArticles;
    private String rating;
    private String views;
    private String saves;

    public Vertex(){
        tags = new HashMap<Integer, String>();
        parentArticles = new HashMap<Integer, String>();

    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }
    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setViews(String views) {
        this.views = views;
    }
    public void setSaves(String saves) {
        this.saves = saves;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getArticleLink() {
        return articleLink;
    }
    public String getPublicDate() {
        return publicDate;
    }
    public String getRating() {
        return rating;
    }
    public String getViews() {
        return views;
    }
    public String getSaves() {
        return saves;
    }
}
