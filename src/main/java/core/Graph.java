/*
author: Kirill Koshaev aka codevarius 06.04.2018
 */
package core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Graph extends HashMap<Integer,Article> {
    /*
    class to store & manage graph vertex
     */
    private static Integer opt;
    private ArrayList<Article> sortedResultList;
    private String tg;

    public Graph(){
        sortedResultList = new ArrayList<Article>();
    }

    public void sortIndividual(String tag, String artHash ){
        sortedResultList = new ArrayList<>();
        tg = tag;
        searchIndi(Integer.valueOf(artHash).hashCode());
    }

    private void searchIndi(int artHash) {
        for(Map.Entry<Integer, String> entry : this.get(artHash).parentArticles.entrySet()){
            String value = entry.getValue();

            if (this.get(value.substring(6,value.indexOf("*")).hashCode()) != null)
                sortedResultList.add(this.get(value.substring(6,value.indexOf("*")).hashCode()));
            else
                System.out.println(value.substring(6,value.indexOf("*")) + " is not contained in the graph");
        }
    }


    public void sort(String tag){
        sortedResultList = new ArrayList<>();
        tg = tag;
        search(Main.entranceArticleName.hashCode(), tag);
    }

    private void search(int artHash, String tag) {
        /*
        by deep search alg find articles with selected tag
         */

        if (this.containsKey(artHash)){

            if (this.get(artHash).tags.containsKey(tag.hashCode()) && tag.hashCode() != 0){

                if (!sortedResultList.contains(this.get(artHash))) {
                    sortedResultList.add(this.get(artHash));
                }

                for(Map.Entry<Integer, String> entry : this.get(artHash).parentArticles.entrySet()){
                    Integer k = entry.getKey();
                    String value = entry.getValue();

                    //if (this.containsKey(k) && this.get(artHash).getArticleName().hashCode() == value.hashCode())
                    search(k,tag);
                }
            }else{

                for(Map.Entry<Integer, String> entry : this.get(artHash).parentArticles.entrySet()){
                    Integer k = entry.getKey();
                    String value = entry.getValue();

                    //if (this.containsKey(k) && this.get(artHash).getArticleName().hashCode() == value.hashCode())
                    search(k,tag);
                }
            }

        }
    }

    public void printSortList(int option) {
        /*
        prints search result sorted by selected rating
         */
        int i = 0;
        Graph.opt = option;
        String r = "default";

        switch (option){
            case 1: r = "rating"; break;
            case 2: r = "views"; break;
            case 3: r = "saves"; break;
        }

        sortedResultList.sort(new Comparator<Article>() {
            public int compare(Article o1, Article o2) {
                switch (Graph.opt){
                    case 1:
                        return Integer.valueOf(o1.getRating()).compareTo(Integer.valueOf(o2.getRating()));

                    case 2:
                        return Integer.valueOf(o1.getViews()).compareTo(Integer.valueOf(o2.getViews()));

                    case 3:
                        return Integer.valueOf(o1.getSaves()).compareTo(Integer.valueOf(o2.getSaves()));

                    default:
                        return 0;
                }

            }
        });

        System.out.println("\nOUTPUT sorted by: " + r + " and generated by tag: " + tg + " -->");
        System.out.println(Main.BORDER);

        for (Article value : sortedResultList
             ) {
            System.out.println(String.format("%4d: {\n       hash: %d\n       name: %s\n       date: %s\n       %s  " +
                            "\n       %s\n       rating: %s\n       views: %s\n       sav: %s\n      }",
                    i++,
                    value.getArticleName().hashCode(),
                    value.getArticleName() + " " + value.getArticleLink(),
                    value.getPublicDate(),
                    value.getTagVals(),
                    value.getRelatives(),
                    value.getRating(),
                    value.getViews(),
                    value.getSaves()));
        }
        System.out.print("\nreport completed --> sorted by: " + r + " and generated by tag: " + tg + " -->");
        System.out.print("\narticles found: " + i + " -->\n");
    }
}
