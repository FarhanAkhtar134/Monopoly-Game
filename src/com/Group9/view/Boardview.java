package com.Group9.view;

public class Boardview {
    //private Gameboard board;
    private static Playerview playerview;
    //private Gamelogs gamelogs



    public static void displayGameBoard(int Round){
        System.out.printf("%s%d%n","ROUND NUMBER:",Round);
        System.out.printf("%s","PLAYER NUMBER");
        System.out.printf("%20s","PLAYER NAME");
        System.out.printf("%25s","PLAYER MONEY");
        System.out.printf("%30s","PLAYER POSITION");
        System.out.printf("%70s","PLAYER OWNED PROPERTY");
        System.out.println();

       // playerview = new Playerview();
//        System.out.printf("%s","-------------------");
        //playerview.displayPlayerView();
//        System.out.printf("%s%n","|11 12 13 14 15 16|");
//        System.out.printf("%s%n","|10             17|");
//        System.out.printf("%s%n","|9              18|");
//        System.out.printf("%s%n","|8              19|");
//        System.out.printf("%s%n","|7              20|");
//        System.out.printf("%s%n","|6   5  4  3  2  1|");
//        System.out.printf("%s%n","-------------------");

    }
}
