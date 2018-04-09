/*
author: Kirill Koshaev aka codevarius 06.04.2018
 */
package core;

import java.util.Map;
import java.util.Scanner;

public class Main {


    /*
            core.Main class with case logic
             */
    //start post id
    public static String entranceArticle;
    //start post name
    public static String entranceArticleName;

    //commands hash
    public static final int  MLOAD = 103990995;
    private static final int MLOAD_P = 1324343888;
    public static final int  HELP = 3198785;
    private static final int QUIT = 3482191;
    private static final int PRINT_GRAPH = -934599625;
    private static final int RATING1 = -934599624;
    private static final int RATING2 = -934599623;
    private static final int RATING3 = -934599622;
    private static final int INDI1 = 1943325002;
    private static final int INDI2 = 1943325003;
    private static final int INDI3 = 1943325004;

    //other consts
    public static final String BORDER = "============================================================\n";
    public static final String LINK = "/post/1000/";
    public static final String LOGO = "\n" +
            "              _            _                     \n" +
            "             | |          | |                    \n" +
            "   __ _ _ __ | |_ ___  ___| |_ ___ __ _ ___  ___ \n" +
            "  / _` | '_ \\| __/ _ \\/ __| __/ __/ _` / __|/ _ \\\n" +
            " | (_| | |_) | ||  __/\\__ \\ || (_| (_| \\__ \\  __/\n" +
            "  \\__, | .__/ \\__\\___||___/\\__\\___\\__,_|___/\\___|\n" +
            "   __/ | |                                       \n" +
            "  |___/|_|                                       \n";


    //work objects
    private Graph graph;
    private Parser parser;
    //private String startPostId;

    public Main(Graph graph, Parser parser){
        this.graph = graph;
        this.parser = parser;
    }

    public static void main(String[] args) {

        //some start parameters for graph generation
        Graph graph;
        Parser parser;
        Main m;

        Scanner scanner = new Scanner(System.in);
        String command = "";
        System.out.println(LOGO);
        System.out.println("created by: codevarius\ngithub: https://github.com/codevarius");
        System.out.print("gptestcase launched --> type 'help' for info -->\n");

        //if graph command will be the first command user type in
        graph = new Graph();
        m = new Main(null, null);

        while (!command.matches("quit")){
            System.out.print("new command -->\n");
            command = scanner.nextLine();

            switch (command.hashCode()){

                case MLOAD:
                    graph = new Graph();
                    parser = new Parser(graph);
                    m = new Main(graph, parser);
                    System.out.print("mload launch --> \n");
                    Parser.article_limit_count = 1000;
                    Main.entranceArticle = LINK;
                    m.mainLoadCmd(graph,parser,LINK);
                    break;

                case MLOAD_P:
                    graph = new Graph();
                    parser = new Parser(graph);
                    m = new Main(graph, parser);
                    System.out.print("mload -p launch --> enter start article index -->\n");
                    String index = scanner.nextLine();
                    Main.entranceArticle = "/post/" + index + "/";
                    System.out.print("mload -p launch --> enter start article index --> " +
                            "enter graph size -->\n");
                    Parser.article_limit_count = Integer.valueOf(scanner.nextLine());
                    m.mainLoadCmd(graph,parser,Main.entranceArticle);
                    break;

                case RATING1:
                    if (!graph.isEmpty()){
                        System.out.print("rep -b launch --> enter tag for search -->\n");
                        String tag = scanner.nextLine();
                        graph.sort(tag);
                        graph.printSortList(1);
                    }else {
                        System.out.print("graph is empty --> nothing to show -->\n");
                    }
                    break;

                case RATING2:
                    if (!graph.isEmpty()){
                        System.out.print("rep -c launch --> enter tag for search -->\n");
                        String tag = scanner.nextLine();
                        graph.sort(tag);
                        graph.printSortList(2);
                    }else {
                        System.out.print("graph is empty --> nothing to show -->\n");
                    }
                    break;

                case RATING3:
                    if (!graph.isEmpty()){
                        System.out.print("rep -d launch --> enter tag for search -->\n");
                        String tag = scanner.nextLine();
                        graph.sort(tag);
                        graph.printSortList(3);
                    }else {
                        System.out.print("graph is empty (or wrong input) --> nothing to show -->\n");
                    }
                    break;

                case INDI1:
                    if (!graph.isEmpty()){
                        //System.out.print("indi launch --> enter tag for search -->\n");
                        String tag = ""; //scanner.nextLine();
                        System.out.print("indi launch --> enter graph existing article hash\n");
                        String artHash = scanner.nextLine();
                        graph.sortIndividual(tag,artHash);
                        graph.printSortList(1);
                    }else {
                        System.out.print("graph is empty (or wrong input) --> nothing to show -->\n");
                    }
                    break;

                case INDI2:
                    if (!graph.isEmpty()){
                        //System.out.print("indi launch --> enter tag for search -->\n");
                        String tag = ""; //scanner.nextLine();
                        System.out.print("indi launch --> enter graph existing article hash\n");
                        String artHash = scanner.nextLine();
                        graph.sortIndividual(tag,artHash);
                        graph.printSortList(2);
                    }else {
                        System.out.print("graph is empty (or wrong input) --> nothing to show -->\n");
                    }
                    break;

                case INDI3:
                    if (!graph.isEmpty()){
                        //System.out.print("indi launch --> enter tag for search -->\n");
                        String tag = ""; //scanner.nextLine();
                        System.out.print("indi launch --> enter graph existing article hash\n");
                        String artHash = scanner.nextLine();
                        graph.sortIndividual(tag,artHash);
                        graph.printSortList(3);
                    }else {
                        System.out.print("graph is empty (or wrong input) --> nothing to show -->\n");
                    }
                    break;

                case PRINT_GRAPH:
                    if (graph != null)
                        m.printGraph();
                    else
                        System.out.print("graph is empty (or wrong input) --> nothing to show -->\n");
                    break;

                case HELP:
                    System.out.print("+ + + + + help description + + + + +\n");
                    System.out.print(Main.geHelp());
                    break;

                case QUIT:
                    System.out.print("goodbye --> *");
                    System.exit(0);
                    break;

                default:
                    System.out.print("wrong command -->\n");
                    break;
            }
        }
        
    }

    public static String geHelp(){
        return BORDER + "'mload'   - generates default 1000 vertex graph starting from /post/1000/\n" +
                        "'mload -p'- generates custom X vertex graph starting from /post/X/\n" +
                        "'rep -a'  - shows list of all graph objects\n" +
                        "'rep -b'  - shows list of all graph objects containing a tag and ranked by rating value\n" +
                        "'rep -c'  - shows list of all graph objects containing a tag and ranked by views value\n" +
                        "'rep -d'  - shows list of all graph objects containing a tag and ranked by saves value\n" +
                        "'indi -a' - for individual object shows list of all relative and existing objects in range " +
                        "of 1 level above and down and ranked by rating value\n" +
                        "'indi -b' - for individual object shows list of all relative and existing objects in range " +
                        "of 1 level above and down and ranked by views value\n" +
                        "'indi -c' - for individual object shows list of all relative and existing objects in range " +
                        "of 1 level above and down and ranked by saves value\n" +
                        "'help'    - displays help description\n" +
                        "'quit'    - closes program\n\n";
    }

    public void mainLoadCmd(Graph graph, Parser parser, String startPostId){
        /*
        Loader function (connects to site & load graph with vertex objects
         */
        long start = System.currentTimeMillis(); //timer start
        parser.parseThrough(startPostId,"", "");

        //clean graph garbage
        graph.remove(0); //removing null default element from graph
        for(Map.Entry<Integer, Article> entry : this.graph.entrySet()){
            //Integer key = entry.getKey();
            Article article = entry.getValue();
            article.tags.remove(0);
            String views = article.getViews();
            if (views.contains("k") || views.contains(",")) {
                views = views.replace("k", "");
                views = views.replace(",", ".");
                double v = Double.valueOf(views);
                v *= 1000;
                article.setViews(String.valueOf((int)v));
            }
        }

        printGraph();
        long finish = System.currentTimeMillis(); //timer break
        System.out.println("Finished in: " + (finish - start) / 60000 + " minutes\n");
    }

    public void printGraph(){
        //console output
        System.out.println("\nOUTPUT ->");
        System.out.println(BORDER);
        int i = 0;

        for(Map.Entry<Integer, Article> entry : graph.entrySet()) {
            //Integer key = entry.getKey();
            Article value = entry.getValue();

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

        System.out.println("\nTotal vertex added: " + (graph.size()) + "\n");
    }

}
