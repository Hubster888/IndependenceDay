
package Backend;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.io.BufferedReader;
import java.io.FileReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class ProfileSave {

  /**
   * @return
   */
  private static Boolean fileExists() {
    File file  = new File("profileList.txt");
    if (file.exists()) {
      return true;
    } else {
      return false;
    }
  }

  public static void createFile() {
    File file  = new File("profileList.txt");
    try {
      file.createNewFile();
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  private static Boolean profileExists(Profile profile) {
    File file  = new File("profileList.txt");
    try(Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (isContain(line, profile.getName())) {
              return true;
            }
        }
    } catch(FileNotFoundException e) {
          System.out.println(e);
    }
        return false;
  }

  public static void addProfile(Profile profile) {
    if(fileExists() && profileExists(profile)) {
      final JFrame parent = new JFrame();

      parent.pack();
      parent.setVisible(true);

      JOptionPane.showMessageDialog(parent,"This profile already exists!");
    } else {
      if(fileExists()) {


        try (BufferedWriter bw = new BufferedWriter(new FileWriter("profileList.txt", true))) {
          bw.write(profile.getName() + " " + profile.getWins() + " " + profile.getLosses());
          bw.newLine();
          bw.close();
        }catch(Exception e) {
          System.out.println(e);
        }
      } else {
        System.out.println("creating file");
        createFile();
        addProfile(profile);
      }

    }
  }

  public static void updateProfile(Profile profile, Boolean playerWon) {
      if(profileExists(profile)) {
        try {
          // PrintWriter object for output.txt
          PrintWriter pw = new PrintWriter("output.txt");

          // BufferedReader object for input.txt
          BufferedReader br1 = new BufferedReader(new FileReader("profileList.txt"));

          String line1 = br1.readLine();
          Profile tempProfile = new Profile();
          // loop for each line of input.txt
          while(line1 != null) {
              // if line is not present in delete.txt
              // write it to output.txt
              if(!isContain(line1, profile.getName())) {
                  pw.println(line1);
              } else {
                String[] lineBreakDown = line1.split(" ");
                tempProfile.setName(lineBreakDown[0]);
                tempProfile.setWins(Integer.parseInt(lineBreakDown[1]));
                tempProfile.setLosses(Integer.parseInt(lineBreakDown[2]));
              }

              line1 = br1.readLine();
          }

          pw.flush();

          Path source = Paths.get("output.txt");
          File file1 = new File("profileList.txt");
          file1.delete();
          Files.move(source, source.resolveSibling("profileList.txt"));
          if(playerWon){tempProfile.addWin();}else{tempProfile.addLoss();}
          addProfile(tempProfile);
          // closing resources
          br1.close();
          pw.close();
        }catch(Exception e){
          System.out.println(e);
        }

    } else {
      final JFrame parent = new JFrame();

      parent.pack();
      parent.setVisible(true);

      JOptionPane.showMessageDialog(parent, "This profile does not exists!");
    }
  }

  private static boolean isContain(String source, String subItem) {
         String pattern = "\\b" + subItem + "\\b";
         Pattern p = Pattern.compile(pattern);
         Matcher m = p.matcher(source);
         return m.find();
    }

  public static void main(String args[]) {
    Profile prof = new Profile("huber");
    updateProfile(prof, false);
  }
  
  public static Profile getProfile(String profileName) {
	  File file  = new File("profileList.txt");
	    try(Scanner scanner = new Scanner(file)) {
	      while (scanner.hasNextLine()) {
	        String line = scanner.nextLine();
	        if (isContain(line, profileName)) {
	              String[] splitLine = line.split(" ");
	              return new Profile(profileName, Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]));
	            }
	        }
	    } catch(FileNotFoundException e) {
	          System.out.println(e);
	    }
	    Profile prof = new Profile(profileName, 0, 0);
	    addProfile(prof);
	    return prof;
  }
}

// File format
// String Int Int /n
// name wins losses /n
// eg.
//Hubert 23 43
//Jhon 34 1
