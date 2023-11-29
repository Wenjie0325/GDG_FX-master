package com.example.gdg_fx;

import GameFunctions.Functions;
import unity.Board;
import unity.Player;

public class Main {
    public static void main(String[] args) {

        Functions functions = new Functions();
        Board chessBoard = functions.init();
        Player player1 = chessBoard.players.get(0);
        Player player2 = chessBoard.players.get(1);
        while(player1.pos < chessBoard.end || player2.pos < chessBoard.end){
            System.out.println("player1:" + functions.move(functions.DiceRoll(), chessBoard,player1).pos);
            if(player1.pos > chessBoard.end){
                System.out.println("1 victory");
                break;
            }
            System.out.println("player2:" + functions.move(functions.DiceRoll(), chessBoard,player2).pos);
            if(player2.pos > chessBoard.end){
                System.out.println("2 victory");
                break;
            }
        }


    }
}
