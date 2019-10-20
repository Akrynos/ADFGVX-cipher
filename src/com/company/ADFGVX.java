package com.company;

import javax.naming.ldap.SortKey;
import javax.sql.rowset.spi.SyncProvider;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collections;
import java.util.*;

public class ADFGVX {

    private HashMap<Character, String> encryptValues = new HashMap<Character, String>();
    private HashMap<String, Character> tempDecryptValues = new HashMap<String, Character>();
    private char[] charArray;
    private LinkedList<String> coordsArray = new LinkedList<>();
    String key;
    String text;
    private int totalRows;
    private Character[][] encrypted;
    private Character[][] tempDecrypted;
    int keyLength, textSize;

    public ADFGVX() {
        charArray = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        List<String> anotherList = Arrays.asList("AA", "AD", "AF", "AG", "AV", "AX", "DA", "DD", "DF", "DG", "DV", "DX", "FA", "FD", "FF", "FG", "FV", "FX", "GA", "GD", "GF", "GG", "GV", "GX", "VA", "VD", "VF", "VG", "VV", "VX", "XA", "XD", "XF", "XG", "XV", "XX");
        coordsArray.addAll(anotherList);
    }

    private void randomizeList() {
        Collections.shuffle(coordsArray);
    }
    public void loadEncryptValues() {
        randomizeList();
        for (Integer i = 0; i < charArray.length; i++) {
            encryptValues.put(charArray[i], coordsArray.get(i));
        }
        //System.out.println(Collections.singletonList(encryptValues));

    }
    public void loadDecryptValues() {
        for (Integer i = 0; i < charArray.length; i++) {
            tempDecryptValues.put(encryptValues.get(charArray[i]), charArray[i]);
        }
        //System.out.println(Collections.singletonList(tempDecryptValues));
    }
    //take the key from the form
    public void getKey(String key) {
        this.key = key.toUpperCase();
        this.keyLength = key.length();
    }
    //determine how many rows will be needed in the array
    public void determineArrayRows(String text, boolean con) {
        //System.out.println(text.length());
        this.text = text.replaceAll("\\s","");

        //System.out.println(this.text.length());
        if(con) {
            int cnt = this.text.length() * 2;
            if (cnt % key.length() > 0) //divide characters by code words length and if theres a remainder
            {
                totalRows = cnt / key.length();
                totalRows += 2;   //add two extra rows, one for key and one for remaining characters
            } else {
                totalRows = cnt / key.length();
                totalRows += 1; //just one extra row for the key
            }
        }else{
            int cnt = this.text.length();
            if (cnt % key.length() > 0) //divide characters by code words length and if theres a remainder
            {
                totalRows = cnt / key.length();
                totalRows += 2;   //add two extra rows, one for key and one for remaining characters
            } else {
                totalRows = cnt / key.length();
                totalRows += 1; //just one extra row for the key
            }
        }
        this.textSize = text.length();
        //System.out.println("Max rows " + totalRows);
    }//end method
    public String encryptData() {
        int charCnt = 0;

        char[] charArray = key.toCharArray();
        ArrayList<String> sortedKey = new ArrayList<>();
        char[] charText = text.toUpperCase().toCharArray();
        //System.out.println(charText);
        String result = new String();
        encrypted = new Character[totalRows][keyLength]; //2D array to hold encrypted values

        //loop through the first row of array and give it the codeword
        for (int columnCnt = 0; columnCnt < keyLength; ++columnCnt) {
            encrypted[0][columnCnt] = charArray[columnCnt];
        }
        //filling the array with chars
        for (int rowCnt = 1; rowCnt < totalRows; rowCnt++) {
            for (int columnCnt = 0; columnCnt < keyLength; columnCnt += 2) {
                if (charCnt < textSize) {
                    if (Character.isLetter(charText[charCnt])) //if its a letter
                    {
                        encrypted[rowCnt][columnCnt] = encryptValues.get(charText[charCnt]).toCharArray()[0];
                        encrypted[rowCnt][columnCnt + 1] = encryptValues.get(charText[charCnt]).toCharArray()[1];
                        charCnt++;
                    }
                } else {
                    encrypted[rowCnt][columnCnt] = 'S';
                    encrypted[rowCnt][columnCnt + 1] = 'S';
                }
            }
        }
        sortedKey = sortArray(true, sortedKey);
        swapColumns(createOrderArray(sortedKey), encrypted);
        result = (toStringTableContent(encrypted));
        return result;
    }
    public String decryptData() {
        String result = new String(text);
        fillTempDecrypted(result);
        //System.out.println(result);
        ArrayList<String> sortedKey = new ArrayList<>();
        sortedKey = sortArray(true, sortedKey);
        swapColumns(createOrderArray(sortedKey), tempDecrypted);

//        for (Character[] r : tempDecrypted){
//            for(Character c : r){
//                System.out.print(c);
//            }
//            System.out.println("# ");
//        }
//        for (Character[] r : encrypted){
//            for(Character c : r){
//                System.out.print(c);
//            }
//            System.out.println("- ");
//        }
        return ReturnDecryptedCode();
    }
    private void fillTempDecrypted(String encText){
        ArrayList<String> sortedKey = new ArrayList<>();
        sortedKey = sortArray(false, sortedKey);

        tempDecrypted = new Character[totalRows][keyLength];
        char[] charText = encText.toUpperCase().toCharArray();
        //System.out.println(charText);
        int charCnt = 0;
        //System.out.println("Text size: " + textSize);
        //loop through the first row of array and give it the codeword
        for (int columnCnt = 0; columnCnt < keyLength; ++columnCnt) {
            tempDecrypted[0][columnCnt] = (sortedKey.get(columnCnt)).toCharArray()[0];
        }
        for (int columnCnt = 0; columnCnt < keyLength; columnCnt++) {
            for (int rowCnt = 1; rowCnt < totalRows; rowCnt++) {
                //System.out.print(rowCnt + " -> ");
                if (charCnt < textSize) {
                    if (Character.isLetter(charText[charCnt])) {
                        tempDecrypted[rowCnt][columnCnt] = charText[charCnt];
                        //System.out.print(charText[charCnt] + " - ");
                        charCnt++;
                    }
                }else{
                        tempDecrypted[rowCnt][columnCnt]='S';
                        //System.out.print("S" + " - ");
                    }
                //System.out.println("Next row");
            }
            //System.out.println("Next column");
        }
    }
    private String ReturnDecryptedCode(){
        String decryptedCode = new String();
        for (int rowCnt = 1; rowCnt < totalRows; rowCnt++) {
            for (int columnCnt = 0; columnCnt < keyLength; columnCnt+=2) {
                //System.out.print(rowCnt + " -> ");
                if(tempDecrypted[rowCnt][columnCnt]!='S'){
                    decryptedCode+=tempDecryptValues.get(Character.toString(tempDecrypted[rowCnt][columnCnt])+ Character.toString(tempDecrypted[rowCnt][columnCnt+1]));
                }
                //System.out.println("Next row");
            }
            //System.out.println("Next column");
        }
        //System.out.println(decryptedCode);
        return decryptedCode;
    }
    private ArrayList<String> sortArray(boolean con, ArrayList<String> sortedKey){

        int tempVal = key.length();

        for (int i = 0; i < tempVal; i++) {
            if(con){
            sortedKey.add(charArray[i] + Integer.toString(i));}
            else sortedKey.add(Character.toString(charArray[i]));
        }

        String temp;
        do {
            for (int j = 0; j < tempVal - 1; j++) {
                if (sortedKey.get(j).charAt(0) > sortedKey.get(j + 1).charAt(0)) {
                    temp = sortedKey.get(j);
                    sortedKey.set(j, sortedKey.get(j + 1));
                    sortedKey.set(j + 1, temp);
                    //System.out.println(sortedKey.get(j));
                }
            }
            tempVal--;
        } while (tempVal > 1);
        return sortedKey;
    }
    private Integer[] createOrderArray(ArrayList<String> SK) {
        Integer[] OrderArray = new Integer[SK.size()];
        int i = 0;
        for (String s : SK) {
            OrderArray[i] = Character.getNumericValue(s.toCharArray()[1]);
            //System.out.print(" # " + OrderArray[i]);
            i++;
        }
        return OrderArray;
    }
    private void swapColumns(Integer[] order, Character[][] opTable) {
        Character[][] tempOpTable = new Character[opTable.length][opTable[0].length];
        int a = 0, b = 0;
        for (Character[] c : opTable) {
            for (Character d : c) {
                tempOpTable[a][b] = d;
                b++;
            }
            b = 0;
            a++;
        }
        if (order.length != opTable[0].length) {
            System.out.println("Error - Order table and opTable has different length - error with key translation");
        } else {
            for (int i = 0; i < order.length; i++) {
                for (int j = 0; j < opTable.length; j++) {
                    opTable[j][i] = tempOpTable[j][order[i]];
                }
            }
            /*for (Character[] c : opTable) {
                System.out.println(" ");
                for (Character d : c) {
                    System.out.print(d + ". ");
                }
            }*/
        }
    }
    private String toStringTableContent(Character[][] table) {
        String res = new String();
        int space = 0;
        for (int i = 0; i < table[0].length; i++) {
            for (int j = 1; j < table.length; j++) {
                if (space > 1) {
                    res += " ";
                    space = 0;
                }
                res += Character.toString(table[j][i]);
                space++;
            }
        }
        return res;
    }
    public String returnEncryptHashMap() {
        return encryptValues.toString();
    }
    public String returnDecryptHashMap() {
        return tempDecryptValues.toString();
    }
}