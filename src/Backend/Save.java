package Backend;

import Frontend.GameController;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Save {

    public void newIncrementingFile(Board board, ArrayList<Profile> profiles){
        String fileName = "Testing";
        int fileNumber = 0;
        try{
            File newFile = new File(fileName + fileNumber + ".txt");
            if (newFile.createNewFile()){
                FormatBoard(board, profiles, fileName + fileNumber + ".txt");
            }
            else{
                while(newFile.createNewFile() == false){
                    fileNumber = fileNumber + 1;
                    newFile = new File(fileName + fileNumber + ".txt");
                }
                FormatBoard(board, profiles, fileName + fileNumber + ".txt");
            }
        }catch(Exception e){
            System.err.println(e);
        }
    }


    public void FormatBoard(Board board, ArrayList<Profile> profiles, String fileName) {
        ArrayList BoardAList = new ArrayList();
        FloorTile[][] T = board.getBoard();
        ArrayList<Player> Players = board.getListOfPlayers();

        BoardAList.add(Integer.toString(profiles.size()));
        for (int i = 0; i < Players.size(); i++) {
            int[] lastPosition = Players.get(i).getLastPosition();
            BoardAList.add(Players.get(i).getName() + ", " + lastPosition[0] + ", " + lastPosition[1]);
        }


        for (int y = 0; y < T[0].length; y++) {
            for (int x = 0; x < T.length; x++) {
                BoardAList.add((x + 1) + ", " + (y + 1) + ", " + T[x][y].getTileType().toUpperCase() + ", " + T[x][y].getOrientation());

            }
        }
        WriteToFile(BoardAList, fileName);
    }



    public void WriteToFile(ArrayList<String> aList, String namingFile) {
        File fileName = new File(namingFile);
        try {
            FileWriter fw = new FileWriter(fileName);
            Writer output = new BufferedWriter(fw);
            int sz = aList.size();
            for (int i = 0; i < sz; i++) {
                output.write(aList.get(i) + "\n");
            }

            output.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void DeleteFile(String fileName){
        //File
    }

    //Not needed
    public ArrayList<String> getBoardData(String fileName) {
        ArrayList contents = new ArrayList();
        File specifiedFile = new File(fileName);
        try {
            Scanner myReader = new Scanner(specifiedFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                contents.add(data);
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return contents;
    }

    //Not needed
    public int[] getBoardSize() {
        ArrayList cList;
        String tempString;
        cList = getBoardData("Testing.txt");
        tempString = cList.get(0).toString();
        String[] stringToArray = tempString.split(", ", 0);
        int[] stringAToIntA = {Integer.parseInt(stringToArray[0]), Integer.parseInt(stringToArray[1])};
        return (stringAToIntA);
    }

}