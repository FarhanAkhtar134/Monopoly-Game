package com.Group9.model;

import com.Group9.GameConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class Gameboard implements Serializable {
    private ArrayList<Square> monopolyBoard;

    public ArrayList<Square> getMonopolyBoard() {
        return monopolyBoard;
    }

    public void setMonopolyBoard(ArrayList<Square> monopolyBoard) {
        this.monopolyBoard = monopolyBoard;
    }

    public void createBoard(){
        ArrayList<Square> board = new ArrayList<Square>(20);

        //this loop is for intiliazing a empty square of size 20.
        Square emptySquare = new Square("EmptySquare");
        for (int i = 0 ; i <20 ; i++){
            board.add(emptySquare );
        }

        GoSquare goSquare = new GoSquare(GameConstants.STARTING_SALARY);
        GoToJail goToJail = new GoToJail(GameConstants.Go_To_Jail,GameConstants.JailFine,new ArrayList<Player>());
        JustVisiting justVisiting = new JustVisiting(GameConstants.Just_Visiting);
        Propertysquare central = new Propertysquare(
                GameConstants.CENTRAL_PROPERTY,
                GameConstants.CENTRAL_PROPERTY_PRICE,
                GameConstants.CENTRAL_PROPERTY_RENT,
                false);
        Propertysquare wanchai = new Propertysquare(
                GameConstants.WANCHAI_PROPERTY,
                GameConstants.WANCHAI_PROPERTY_PRICE,
                GameConstants.WANCHAI_PROPERTY_RENT,
                false);
        Propertysquare stanley = new Propertysquare(
                GameConstants.STANLEY_PROPERTY,
                GameConstants.STANLEY_PROPERTY_PRICE,
                GameConstants.STANLEY_PROPERTY_RENT,
                false);
        Propertysquare sheko = new Propertysquare(
                GameConstants.SHEKO_PROPERTY,
                GameConstants.SHEKO_PROPERTY_PRICE,
                GameConstants.SHEKO_PROPERTY_RENT,
                false);
        Propertysquare mongkok = new Propertysquare(
                GameConstants.MONGKOK_PROPERTY,
                GameConstants.MONGKOK_PROPERTY_PRICE,
                GameConstants.MONGKOK_PROPERTY_RENT,
                false);
        Propertysquare tsingYi = new Propertysquare(
                GameConstants.TSINGYI_PROPERTY,
                GameConstants.TSINGYI_PROPERTY_PRICE,
                GameConstants.TSINGYI_PROPERTY_RENT,
                false);
        Propertysquare shatin = new Propertysquare(
                GameConstants.SHATIN_PROPERTY,
                GameConstants.SHATIN_PROPERTY_PRICE,
                GameConstants.SHATIN_PROPERTY_RENT,
                false);
        Propertysquare tuenMun = new Propertysquare(
                GameConstants.TUENMUN_PROPERTY,
                GameConstants.TUENMUN_PROPERTY_PRICE,
                GameConstants.TUENMUN_PROPERTY_RENT,
                false);
        Propertysquare taiPo = new Propertysquare(
                GameConstants.TAIPO_PROPERTY,
                GameConstants.TAIPO_PROPERTY_PRICE,
                GameConstants.TAIPO_PROPERTY_RENT,
                false);
        Propertysquare saiKung = new Propertysquare(
                GameConstants.SAIKUNG_PROPERTY,
                GameConstants.SAIKUNG_PROPERTY_PRICE,
                GameConstants.SAIKUNG_PROPERTY_RENT,
                false);
        Propertysquare yuenLong = new Propertysquare(
                GameConstants.YUENLONG_PROPERTY,
                GameConstants.YUENLONG_PROPERTY_PRICE,
                GameConstants.YUENLONG_PROPERTY_RENT,
                false);
        Propertysquare taiO = new Propertysquare(
                GameConstants.TAIO_PROPERTY,
                GameConstants.TAIO_PROPERTY_PRICE,
                GameConstants.TAIO_PROPERTY_RENT,
                false);
        ChanceSquare chanceSquare1 = new ChanceSquare(GameConstants.CHANCE_SQUARE,0,0,0,0);
        ChanceSquare chanceSquare2 = new ChanceSquare(GameConstants.CHANCE_SQUARE,0,0,0,0);
        ChanceSquare chanceSquare3 = new ChanceSquare(GameConstants.CHANCE_SQUARE,0,0,0,0);
        Incometax incometax = new Incometax(GameConstants.INCOME_TAX_SQUARE,0.01);
        FreeParking freeParking = new FreeParking(GameConstants.Free_Parking);


        board.add(0,goSquare);
        board.add(1,central);
        board.add(2,wanchai);
        board.add(3,incometax);
        board.add(4,stanley);
        board.add(5,justVisiting);
        board.add(6,sheko);
        board.add(7,mongkok);
        board.add(8,chanceSquare1);
        board.add(9,tsingYi);
        board.add(10,freeParking);
        board.add(11,shatin);
        board.add(12,chanceSquare2);
        board.add(13,tuenMun);
        board.add(14,taiPo);
        board.add(15,goToJail);
        board.add(16,saiKung);
        board.add(17,yuenLong);
        board.add(18,chanceSquare3);
        board.add(19,taiO);

        setMonopolyBoard(board);


    }






}
