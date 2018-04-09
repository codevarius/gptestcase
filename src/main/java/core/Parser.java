/*
author: Kirill Koshaev aka codevarius 06.04.2018
 */
package core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class Parser {


    public static int article_limit_count = 1000; //Limit of graph vertex number
    public static int tcount = 0; //counter for recursive method parseThrough
    public static final String SOURCE = "https://habrahabr.ru";

    private Document document;
    private Graph graph;

    public Parser(Graph graph){
        this.graph = graph;
    }

    @Deprecated
    public void parseSet(){
        /*
        Method to parse each article in range between /post/n/ to /post/ARTICLE_LIMIT_COUNT/
        Not in use...
         */

        long startTime = System.currentTimeMillis();

        int count = 0; //counter for parseSet method
        int startPos = 1000; //start article for parseSet method
        boolean parsed = true; //bool marker to switch between log messages


        while (graph.size() < article_limit_count){
            try {
                //connect to article
                document = Jsoup.connect("https://habrahabr.ru/post/" + (startPos + count) +"/").get();

                //new article object
                Article article = new Article();

                //fill article name, date
                article.setArticleName(document.getElementsByClass("post__title-text").text());
                article.setPublicDate(document.select("span.post__time").text());

                //fill tag list
                String rating = document.select("span.voting-wjt__counter").text();
                if (rating.contains(" "))
                    article.setRating(rating.substring(0, rating.indexOf(" ")));
                else
                    article.setRating(rating);

                //parsing views and saves info
                article.setViews(document.select("span.post-stats__views-count").text());
                article.setSaves(document.select("span.bookmark__counter").text());

                //article relatives
                for (Element tag : document.select("ul.inline-list")
                     ) {
                    if (tag.hasText())
                        article.tags.put(tag.text().hashCode(),tag.text());
                    else
                        article.tags.put(0,null);
                }

                for (Element relative : document.select("a.post-info__title")
                        ) {

                    String url = relative.attr("href");
                    if (url.matches("/post/(.*)"))
                        article.parentArticles.put(relative.text().hashCode(),relative.text().substring(0, 10));
                }

                //pack parsed info and put in graph
                graph.put(article.getArticleName().hashCode(),article);

                document = null; //free mem

            } catch (IOException e) {
                parsed = false;
                //e.printStackTrace();
            }

            // if article parsing has problems
            if (!parsed){
                System.out.println("article №" + (startPos + count)
                        + " missed. Articles parsed " + graph.size() + "/" + article_limit_count);
                parsed = true;
            }

            //increase counter
            count++;
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("\nTotal missed calls: " + (count - article_limit_count) +
        "\nProcess finished in " + (finishTime - startTime) / 60000 + " minutes");
    }

    public void parseThrough(String postId){
        /*
        Method to parse articles recursively while graph size <= ARTICLE_LIMIT_COUNT
        Not in use...
         */
        //new article obj
        Article article = new Article();

        //the same as parseSet but recursive nature
        while (graph.size() < article_limit_count){
            try {
                Document document = Jsoup.connect(SOURCE + postId).get();
                article.setArticleLink(SOURCE + postId);
                article.setArticleName(document.getElementsByClass("post__title-text").text());

                if (Main.entranceArticleName == null)
                    Main.entranceArticleName = article.getArticleName();

                article.setPublicDate(document.select("span.post__time").text());

                String rating = document.select("span.voting-wjt__counter").text();
                if (rating.contains(" "))
                    article.setRating(rating.substring(0, rating.indexOf(" ")));
                else
                    article.setRating(rating);

                article.setViews(document.select("span.post-stats__views-count").text());
                article.setSaves(document.select("span.bookmark__counter").text());

                for (Element tag : document.select("a.post__tag")
                        ) {
                    if (tag.hasText())
                        article.tags.put(tag.text().toLowerCase().hashCode(),tag.text().toLowerCase());
                    else
                        article.tags.put(0,null);
                }

                for (Element relative : document.select("a.post-info__title")) {

                        String newLink = relative.attr("href");
                        if (newLink.matches("/post/(.*)") && !graph.containsKey(relative.text().hashCode())) {
                            article.parentArticles.put(relative.text().hashCode(), relative.text()+ " hash: "+
                                    relative.text().hashCode() + " link: " +
                                    SOURCE + newLink);
                            if (!graph.containsKey(article.getArticleName().hashCode()))
                                graph.put(article.getArticleName().hashCode(),article);
                            else
                                System.out.println(newLink + " already exists --> " + "graph size: "
                                        + graph.size() + "/" + article_limit_count);

                            Parser.tcount++;

                            System.out.println(newLink + " №" + Parser.tcount + " parsed --> " + "graph size: "
                                    + graph.size() + "/" + article_limit_count);
                            parseThrough(newLink); //recursive call
                        }else{
                            System.out.println(newLink + " dismissed --> " + "graph size: "
                                    + graph.size() + "/" + article_limit_count);
                        }

                }

                graph.put(article.getArticleName().hashCode(),article);

                Parser.tcount++; //increase static counter tcount

            } catch (IOException e) {
                System.out.print("unreachable site or blocked article found...\n");
                //e.printStackTrace(); //if somethig wrong
            } catch (org.jsoup.UncheckedIOException jsoupExcept){
                System.out.print("bad internet connection maybe...\n");
            }

            break; //if article has dead-end
        }
    }
}
