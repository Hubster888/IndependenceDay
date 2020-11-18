import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;

public class ProfileSave{

  private static Boolean fileExists(){
    File file  = new File("profileList.txt");
    if (file.exists()) {
      return true;
    }else {
      return false;
    }
  }

  private static void createFile(){
    File file  = new File("profileList.txt");
    try{
      file.createNewFile();
    }catch(Exception e){
      System.out.println(e);
    }
  }

  private static Boolean profileExists(Profile profile){
    File file  = new File("profileList.txt");
    try {
      Scanner scanner = new Scanner(file);

      int lineNum = 0;
      while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          lineNum++;
          if(isContain(line, profile.getName())) {
              return true;
            }
          }
        } catch(FileNotFoundException e) {
          System.out.println(e);
        }
        return false;
  }

  public static void addProfile(Profile profile){
    if(fileExists() && profileExists(profile)){
      final JFrame parent = new JFrame();

      parent.pack();
      parent.setVisible(true);

      JOptionPane.showMessageDialog(parent,"This profile already exists!");
    }else {
      if(fileExists()){


        try (BufferedWriter bw = new BufferedWriter(new FileWriter("profileList.txt", true))) {
          bw.write(profile.getName() + " 0 0");
          bw.newLine();
          bw.close();
        }catch(Exception e){
          System.out.println(e);
        }
      }else{
        System.out.println("creating file");
        createFile();
        addProfile(profile);
      }

    }
  }

  public static void updateProfile(Profile profile, Boolean playerWon){
      if(profileExists(profile)){
        try{
          // input the (modified) file content to the StringBuffer "input"
          BufferedReader file = new BufferedReader(new FileReader("profileList.txt"));
          StringBuffer inputBuffer = new StringBuffer();
          String line;

          while ((line = file.readLine()) != null) {
            if(isContain(line, profile.getName())){
              Profile temp = new Profile(profile.getName(), profile.getWins(), profile.getLosses());
              if(playerWon){temp.addWin();} else {temp.addLoss();}
              line = temp.getName() + " " + temp.getWins() + " " + temp.getLosses();
              inputBuffer.append(line);
              inputBuffer.append('\n');
            }
          }
          file.close();


          // write the new string with the replaced line OVER the same file
          FileOutputStream fileOut = new FileOutputStream("profileList.txt");
          fileOut.write(inputBuffer.toString().getBytes());
          fileOut.close();
        }catch(Exception e){
          System.out.println(e);
        }

    }else {
      final JFrame parent = new JFrame();

      parent.pack();
      parent.setVisible(true);

      JOptionPane.showMessageDialog(parent,"This profile does not exists!");
    }
  }

  private static boolean isContain(String source, String subItem){
         String pattern = "\\b"+subItem+"\\b";
         Pattern p=Pattern.compile(pattern);
         Matcher m=p.matcher(source);
         return m.find();
    }

  public static void main(String args[]){
    Profile prof = new Profile("huber");
    updateProfile(prof, true);
  }
}

// File format
// String Int Int /n
// eg.
//Hubert 23 43
//Jhon 34 1

//Deleates whole file and dosent remember previous stats.
