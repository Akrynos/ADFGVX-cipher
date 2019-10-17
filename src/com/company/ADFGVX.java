package com.company;

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

    Scanner console = new Scanner(System.in);
    private HashMap<Character, String> encryptValues = new HashMap<Character, String>();
    private char[] charArray;
    private LinkedList<String> coordsArray = new LinkedList<>();
    private HashMap<String, Character> tempDecryptValues = new HashMap<String, Character>();
    private final String ENCRYPTED_FILE = "Encrypted.txt";
    private final String DECRYPTED_FILE = "Decrypted.txt";
    String key;
    String text;
    private int totalRows;
    private Character[][] encrypted;
    private Character[][] tempDecrypted;
    private Character[][] fullDecryption;

    public ADFGVX() {
        charArray = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U', 'W', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
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
        System.out.println(Collections.singletonList(encryptValues));

    }

    public void loadDecryptValues() {
        for (Integer i = 0; i < charArray.length; i++) {
            tempDecryptValues.put(encryptValues.get(charArray[i]), charArray[i]);
        }
        System.out.println(Collections.singletonList(tempDecryptValues));
    }

    //take the key from the form
    public void getKey(String key) {
        this.key = key.toUpperCase();
    }

    //determine how many rows will be needed in the array
    public void determineArrayRows(String text) {
        this.text = text;
        int cnt = text.length()*2;
        if (cnt % key.length() > 0) //divide characters by code words length and if theres a remainder
        {
            totalRows = cnt / key.length();
            totalRows += 2;   //add two extra rows, one for key and one for remaining characters
        } else {
            totalRows = cnt / key.length();
            totalRows += 1; //just one extra row for the key
        }
    }//end method

    public String encryptData() {

        int c;
        int keyLength = key.length();
        int charCnt = 0;
        int textSize = text.length();
        char[] charArray = key.toCharArray();
        ArrayList<String> sortedKey = new ArrayList<>();
        char[] charText = text.toUpperCase().toCharArray();
        System.out.println(charText);
        String result = new String();
        encrypted = new Character[totalRows][keyLength]; //2D array to hold encrypted values

        //loop through the first row of array and give it the codeword
        for (int columnCnt = 0; columnCnt < keyLength; ++columnCnt) {
            encrypted[0][columnCnt] = charArray[columnCnt];
        }
        //filling the array with chars
        for(int rowCnt=1; rowCnt<totalRows; rowCnt++){
            for(int columnCnt=0; columnCnt<keyLength;columnCnt+=2){
                if(charCnt < textSize){
                if (Character.isLetter(charText[charCnt])) //if its a letter
                {
                    encrypted[rowCnt][columnCnt] = encryptValues.get(charText[charCnt]).toCharArray()[0];
                    encrypted[rowCnt][columnCnt+1] = encryptValues.get(charText[charCnt]).toCharArray()[1];
                    charCnt++;
                }
                }else {
                    encrypted[rowCnt][columnCnt] = 'S';
                    encrypted[rowCnt][columnCnt+1] = 'S';
                }

            }
        }
        //swaping columns
        int tempVal = keyLength;
        System.out.println(tempVal);
        for(int i=0; i<keyLength;i++){

            sortedKey.add(charArray[i] + Integer.toString(i));
        }
        System.out.println(sortedKey);
        String temp;
        do{
            for(int j=0; j<tempVal-1; j++){
                if(sortedKey.get(j).charAt(0) > sortedKey.get(j+1).charAt(0)){
                    temp=sortedKey.get(j);
                    sortedKey.set(j,sortedKey.get(j+1));
                    sortedKey.set(j+1,temp);
                    System.out.println(sortedKey.get(j));
                }
            }
            tempVal--;
        }while(tempVal>1);
        System.out.println(sortedKey);

        return result;
    }


 /*   public void readEncryptedData() {
        try {
            int c;
            char fileChar;
            int rowCnt = 0;
            int colCnt = 0;
            int decryptCnt = 0;
            String encryptVal = "";
            tempDecrypted = new Character[totalRows][codeWord.length()];//char array for decrypted but unsorted characters
            InputStream in = new FileInputStream(ENCRYPTED_FILE);
            Reader reader = new InputStreamReader(in);
            char[] charArray = codeWord.toCharArray(); //create array containing code word
            Arrays.sort(charArray); //sort it aplhabetically

            for (int i = 0; i < 1; ++i) {
                for (int j = 0; j < codeWord.length(); ++j) {
                    tempDecrypted[i][j] = charArray[j]; //unsorted but decryped array first row gets sorted alpha keyword
                }
            }

            while ((c = reader.read()) != -1) //start looping by character through file
            {
                ++decryptCnt; //increment character counter
                fileChar = (char) c; //cast int to char

                encryptVal += Character.toString(fileChar); //string encryptedVal get converted value of char
                if (decryptCnt == 2) //if character counter is 2 means we have read two characters
                {
                    ++rowCnt; //increment row (row is now on index 1 if its first loop (index 0 is for the codeword))

                    if (encryptVal == "NA") //if special null character
                    {
                        tempDecrypted[rowCnt][colCnt] = ' ';//just print a whitespace null only possible at end of file
                    } else //else
                    {
                        Character decryptedChar = (Character) tempDecryptValues.get(encryptVal);//get char value for key
                        tempDecrypted[rowCnt][colCnt] = decryptedChar; //add that char value to decrypted but unsorted arrar

                        decryptCnt = 0; //set char counter back to zero
                        encryptVal = ""; //set string for encrypt values back to blank
                    }

                    if (rowCnt == (totalRows - 1)) //if row count has reached the end of array
                    {
                        rowCnt = 0; //reset row
                        ++colCnt; // increment column
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e)                            //catch blocks for exceptions
        {
            System.out.println("IO Problem");
        }
    }
    */

    public String returnEncryptHashMap() {
        return encryptValues.toString();
    }

    public String returnDecryptHashMap() {
        return tempDecryptValues.toString();
    }
}
