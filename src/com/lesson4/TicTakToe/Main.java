package com.lesson4.TicTakToe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        startGame();
    }

    static void startGame(){
        Scanner scanner= new Scanner(System.in);
        int count =0;
        int size=getTheFieldSize(scanner);
        char [][] myField= getNewField(size);
        int rangeToWin= getTheWinRange(size);
        boolean isAIWin=false;
        boolean isPlayerWin=false;
        boolean isDraw;
        System.out.printf("Вы выбрали игру на поле размером %d х %d.%n" +
                "Для победы вы должны выстроить линию длинной в %d %s %n",
                size,size,rangeToWin,conjugateText(rangeToWin));

       while (true){
           if (count%2==0) {
               playerTurn(scanner,myField);
               drawField(myField);
               isPlayerWin=checkTheWinner(myField,playerSymbol(),rangeToWin);
           }
            else {
                aiTurn(myField, rangeToWin);
                drawField(myField);
                isAIWin=checkTheWinner(myField,aiSymbol(),rangeToWin);
            }
            System.out.println();
            count++;
            isDraw=checkTheDraw(count,myField);

            if (isAIWin) {
                System.out.println("Вы проиграли!");
                break;
            }
            else if (isPlayerWin){
                System.out.println("Вы выиграли!");
                break;
            }
            else if (isDraw){
                System.out.println("Ничья!");
                break;
            }
       }
    }

    /******************************************************************************************
    ============================= Инициализация объектов=======================================
     ******************************************************************************************/

    static int getTheFieldSize(Scanner scanner){
        int size;
        do{
            System.out.println("Введите размер поля(от 3 до 19): ");
            size=scanner.nextInt();
        }while (size<3||size>19);
        return size;
    }
    static int getTheWinRange(int size){
        if(size<5) return 3;
        else if(size<15) return 4;
        else return 5;
    }

    static char[][] getNewField(int size){
        char  field[][]= new char[size][size];
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j]= defaultSymbol();
            }

        }
        return field;
    }
    static void drawField(char [][] field){
        for (int i = 0; i <field.length ; i++) {
            System.out.println(Arrays.toString(field[i]));
        }
    }
  /***************************************************************************************************
   =====================================Реализация ходов==============================================
   ****************************************************************************************************/

    static void playerTurn(Scanner scanner, char[][] myField ){
        int x,y;
        do{
            System.out.println("Введите X:");
            x= getCoordinate(scanner,myField)-1;
            System.out.println("Введите Y:");
            y= getCoordinate(scanner,myField)-1;

        }while (isBusyCeil(x,y,myField));
            myField[x][y]=playerSymbol();
    }
    static int getCoordinate(Scanner scanner, char[][] myField){
        int coordinate;
        do {

            coordinate=scanner.nextInt();
            if (coordinate<1||coordinate>(myField.length)){
                System.out.printf("Введенное значение некорректно, диапазон координат от %d до %d %n",1,(myField.length));
            }
        }
        while (coordinate<1||coordinate>(myField.length));
        return coordinate;
    }


    static void aiTurn(char[][] myField, int rangeToWin){
        int x,y;

            for (int i = 0; i < myField.length; i++) {
                for (int j = 0; j < myField.length ; j++) {
                    if(!isBusyCeil(i,j,myField)){
                       myField[i][j]=aiSymbol();
                       //Проверка на победу компьютера
                       if (checkTheWinner(myField,aiSymbol(),rangeToWin)==true) return;
                       else myField[i][j]=playerSymbol();
                       //проверку на победу игрока
                        if (checkTheWinner(myField,playerSymbol(),rangeToWin)==true) {
                            myField[i][j]=aiSymbol();
                            return;
                        }

                        else myField[i][j]=defaultSymbol();
                     }
                }
            }
         do {
            x=(int)(Math.random()*(myField.length));
            y=(int)(Math.random()*(myField.length));

        }while (isBusyCeil(x,y,myField));
        myField[x][y]=aiSymbol();
    }

    static boolean isBusyCeil(int x, int y, char[][] field){

        return field[x][y]!=defaultSymbol();
    }

    /**********************************************************************************************
    ====================================Проверки победы или ничьей=================================
    **********************************************************************************************/

    static boolean checkTheWinner(char[][] myField, char symbol, int rangeToWin){
        for (int i = 0; i <myField.length ; i++) {
            for (int j = 0; j <myField.length ; j++) {
                if (myField[i][j]==symbol) {
                    if (checkTheLinesOnWin(myField, i, j, symbol, rangeToWin)) return true;
                }
            }
        }
        return false;
    }
    static boolean checkTheLinesOnWin(char[][] myField, int x, int y, char symbol, int rangeToWin)
    {   int horizontal=0;
        int vertical=0;
        int forwardDiagonal=0;
        int backwardDiagonal=0;

        for (int i = 0; i <rangeToWin ; i++) {
            if (x+rangeToWin<=myField.length&&y+rangeToWin<=myField.length){
                //Проверяем прямую диагональ
                if (myField[x + i][y + i] == symbol) forwardDiagonal++;
            }
            if(x+rangeToWin<=myField.length&&y-rangeToWin+1>=0)
            {   //Проверяем обратную диагональ
                if (myField[x + i][y - i] == symbol) backwardDiagonal++;
            }
            if (x+rangeToWin<=myField.length){
                //Проверяем горизонтали
                if (myField[x + i][y] == symbol) horizontal++;

            }
            if(y+rangeToWin<=myField.length){
                //Проверяем вертикали
                if (myField[x][y + i] == symbol) vertical++;
            }
        }

        if( vertical==rangeToWin||
            horizontal==rangeToWin||
            forwardDiagonal==rangeToWin||
            backwardDiagonal==rangeToWin) {
                return true;
        }

        else return false;
    }
    static boolean checkTheDraw(int count, char [][] myField){
        if(count==(myField.length*myField.length)){
            System.out.println("Ничья");
            return true;
        } return false;
    }

    /**********************************************************************************************
     =================================Вспомогательные методы=======================================
     **********************************************************************************************/

    static String conjugateText(int rangeToWin){
        if(rangeToWin>4) return "ячеек";
        else return "ячейки";
    }

    static char defaultSymbol(){
        return '-';
    }

    static char playerSymbol(){
        return 'X';
    }

    static char aiSymbol(){
        return 'O';
    }


}

