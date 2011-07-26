/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author andre
 */
public class Matriz {

    int matrix [][];
    boolean booleanMatrix[][];

    public Matriz(){
        

        matrix = new int[3][3];
        booleanMatrix = new boolean[3][3];

        for(int i=0; i<=2; i++){

            for(int j=0; j<=2; j++){
                matrix[i][j] = -2;
                booleanMatrix[i][j] = false;
            }
        }
    }

public void atualizaMatriz(int round, String position){

       int selectRound = round%2;

       int sw = Integer.parseInt(position);

       switch(sw){
           case 11:
               if( selectRound != 0 ){
                    matrix[0][0] = 1;
                 break;}
               else{
                    matrix[0][0] = 2;
                    break;}
           case 12:
               if( selectRound != 0 ){
                matrix[0][1] = 1;
                 break;}
               else{
                    matrix[0][1] = 2;
                    break;}
           case 13:
               if( selectRound != 0 ){
                matrix[0][2] = 1;
                 break;}
               else{
                    matrix[0][2] = 2;
                    break;}
           case 21:
               if( selectRound != 0 ){
                 matrix[1][0] = 1;
                 break;}
               else{
                    matrix[1][0] = 2;
                    break;}
           case 22:
               if( selectRound != 0 ){
               matrix[1][1] = 1;
                 break;}
               else{
                   matrix[1][1] = 2;
                    break;}
            case 23:
               if( selectRound != 0 ){
                matrix[1][2] = 1;
                 break;}
               else{
                    matrix[1][2] = 2;
                    break;}
             case 31:
               if( selectRound != 0 ){
                matrix[2][0] = 1;
                 break;}
               else{
                    matrix[2][0] = 2;
                    break;}
           case 32:
               if( selectRound != 0 ){
                matrix[2][1] = 1;
                 break;}
               else{
                    matrix[2][1] = 2;
                    break;}
           case 33:
               if( selectRound != 0 ){
                matrix[2][2] = 1;
                 break;}
               else{
                    matrix[2][2] = 2;
                    break;}
       }
  }

public int checaPosicao(int i, int j){

    return matrix[i][j];
}


public boolean checaBooleanMatriz(String position){

       boolean retorno = false;

       int sw = Integer.parseInt(position);

       switch(sw){
           case 11:
               if(booleanMatrix[0][0] == false){
                  booleanMatrix[0][0] = true;
                  retorno = true;
                   break;}
          case 12:
               if( booleanMatrix[0][1] == false ){
                   booleanMatrix[0][1] = true;
                   retorno = true;
                   break;}
           case 13:
               if( booleanMatrix[0][2] == false ){
                   booleanMatrix[0][2] = true;
                   retorno = true;
                   break;}
           case 21:
               if( booleanMatrix[1][0] == false ){
                   booleanMatrix[1][0] = true;
                   retorno = true;
                   break;}
           case 22:
               if( booleanMatrix[1][1] == false){
                   booleanMatrix[1][1] = true;
                   retorno = true;
                   break;}
            case 23:
               if( booleanMatrix[1][2] == false){
                   booleanMatrix[1][2] = true;
                   retorno = true;
                   break;}
             case 31:
               if(booleanMatrix[2][0] == false){
                  booleanMatrix[2][0] = true;
                  retorno = true;
                  break;}
               
           case 32:
               if(booleanMatrix[2][1] == false ){
                  booleanMatrix[2][1] = true;
                  retorno = true;
                  break;}
               
           case 33:
               if(booleanMatrix[2][2] == false ){
                  booleanMatrix[2][2] = true;
                  retorno = true;
                  break;}
           
           }

       return retorno;
    }

public boolean checaBooleanMatriz2(String position){

       boolean retorno = false;

       int sw = Integer.parseInt(position);

       if(sw == 11){           
               if(booleanMatrix[0][0] == false){
                  booleanMatrix[0][0] = true;
                  retorno = true;
                   }}
          else if (sw == 12){
               if( booleanMatrix[0][1] == false ){
                   booleanMatrix[0][1] = true;
                   retorno = true;
                   }}
           else if (sw == 13){
               if( booleanMatrix[0][2] == false ){
                   booleanMatrix[0][2] = true;
                   retorno = true;
                   }}
           else if(sw == 21){
               if( booleanMatrix[1][0] == false ){
                   booleanMatrix[1][0] = true;
                   retorno = true;
                   }}
           else if (sw ==22){
               if( booleanMatrix[1][1] == false){
                   booleanMatrix[1][1] = true;
                   retorno = true;
                   }}
            else if (sw ==23){
               if( booleanMatrix[1][2] == false){
                   booleanMatrix[1][2] = true;
                   retorno = true;
                   }}
             else if (sw ==31){
               if(booleanMatrix[2][0] == false){
                  booleanMatrix[2][0] = true;
                  retorno = true;
                   }}
             else if (sw ==32){
               if(booleanMatrix[2][1] == false ){
                  booleanMatrix[2][1] = true;
                  retorno = true;
                  }}
            else if (sw == 33){
               if(booleanMatrix[2][2] == false ){
                  booleanMatrix[2][2] = true;
                  retorno = true;
                 }}
           
           return retorno;
}
  

public int calculaVitoria(){

   if((matrix[0][0]+matrix[0][1]+matrix[0][2]) == 3 ||
      (matrix[1][0]+matrix[1][1]+matrix[1][2]) == 3 ||
      (matrix[2][0]+matrix[2][1]+matrix[2][2]) == 3 ||
      (matrix[0][0]+matrix[1][0]+matrix[2][0]) == 3 ||
      (matrix[0][1]+matrix[1][1]+matrix[2][1]) == 3 ||
      (matrix[0][2]+matrix[1][2]+matrix[2][2]) == 3 ||
      (matrix[0][0]+matrix[1][1]+matrix[2][2]) == 3 ||
      (matrix[0][2]+matrix[1][1]+matrix[2][0]) == 3
     )
        return 3;

   else if((matrix[0][0]+matrix[0][1]+matrix[0][2]) == 6 ||
           (matrix[1][0]+matrix[1][1]+matrix[1][2]) == 6 ||
           (matrix[2][0]+matrix[2][1]+matrix[2][2]) == 6 ||
           (matrix[0][0]+matrix[1][0]+matrix[2][0]) == 6 ||
           (matrix[0][1]+matrix[1][1]+matrix[2][1]) == 6 ||
           (matrix[0][2]+matrix[1][2]+matrix[2][2]) == 6 ||
           (matrix[0][0]+matrix[1][1]+matrix[2][2]) == 6 ||
           (matrix[0][2]+matrix[1][1]+matrix[2][0]) == 6
     )
       return 6;

   return 0;
}

}