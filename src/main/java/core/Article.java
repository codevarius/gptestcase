/*
author: Kirill Koshaev aka codevarius 06.04.2018
 */
package core;

import java.util.Map;

public class Article extends Vertex {
    /*
    class to define article
     */

    public Article(){
        //empty
    }

    public String getTagVals() {
        /*
        Method to convert tag hash map to string output
         */
        StringBuilder output = new StringBuilder();
        output.append("tags:{ \n              ");

        for(Map.Entry<Integer, String> entry : this.tags.entrySet()){
            //Integer key = entry.getKey();
            String value = entry.getValue();

            output.append(value + "\n              ");
        }

        output.append("\b\b}");

        return String.valueOf(output);
    }

    public String getRelatives() {
        /*
        Method to convert relative articles hash map to string output
         */
        StringBuilder output = new StringBuilder();
        output.append("relatives:{ \n                   ");

        for(Map.Entry<Integer, String> entry : this.parentArticles.entrySet()){
            //Integer key = entry.getKey();
            String value = entry.getValue();

            output.append(value + "\n                   ");
        }

        output.append("\b\b}");

        return String.valueOf(output);
    }
}
