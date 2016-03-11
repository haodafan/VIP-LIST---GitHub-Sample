
package viplist;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author haoda fan
 * @serial fdjksf0834fu458dsuf943uf9ousdu323fzapa0w31lvki9h9de
 */

public class VIPList {

    /**
     * @param args the command line arguments
     */
    static File fileList = new File("Y:\\TheList.txt");
    static String path = "Y:\\TheList.txt";
    
    //THE MAIN MENU
    public static void Menu(){
        Scanner in = new Scanner(System.in);
        String inChoice;
        int choice;
        
        //The menu
        System.out.println("\n\n\t--- MENU ---\nEnter a number to select an option ... ");
        System.out.println("1. View total list  \t 2. Search list");
        System.out.println("3. Update list      \t 4. Exit Program (Party over) ");
        inChoice = in.nextLine(); //User enters a number
        
        try {
            //The user enters a number
            choice = Integer.parseInt(inChoice);
            switch (choice) {
                case 1: 
                    DisplayAll();
                    Menu();
                    break;
                case 2: 
                    System.out.println("Enter a name: ");
                    String name = in.nextLine();
                    String name2 = name.trim();
                    name2 = name2.replaceAll(" ", "_"); //Formats it correctly in the list
                    int index = -1;
                    index = FindName(name2);
                    if (index != -1){
                        System.out.println(name + " is #" + index + " on the list. He's cool, you can let him in.");
                    }
                    else {
                        System.out.println(name + " was not invited. Sorry, no losers allowed. SECURITY!!");
                    }
                    Menu();
                    break;
                case 3:
                    int choice2;
                    System.out.println("1. Add to list \n2. Remove from list");
                    choice2 = in.nextInt();
                    if (choice2 == 1) {
                        UpdateChoice(true);
                    }
                    else if (choice2 == 2){
                        UpdateChoice(false);
                    }
                    else {
                        System.out.println("Sigh ... your thing is wrong.");
                    }
                    Menu();
                    break;
                case 4:
                    System.out.println("Exit...");
                    break;
            }
        }
        catch (NumberFormatException e){
            //If the user does not enter a number
            System.out.println("\n\n\nHey, you! It says enter a number! A NUMBER!");
            Menu();
        }
    }
    
    //Method: FindName()
    //Finds a name on a particular list
    public static int FindName(String name){
        /**
         * Input a string, 
         * Returns an integer, representing the line in the list - If it returns -1, it doesn't exist. 
         */
        int indexNum = -1;
        int counter = 0;
        String textLine;
        
        try{
            FileReader in = new FileReader(path);
            BufferedReader fileRead = new BufferedReader(in);
            
            //While the current line contains text, and the index is -1 (meaning it hasn't found it yet)
            while ((textLine = fileRead.readLine()) != null && indexNum == -1){   
                if (textLine.equals(name)){
                    indexNum = counter;
                }   
                counter++; 
            }
            in.close();
            fileRead.close();
        }
        catch (FileNotFoundException e){
            //If the file isn't found
            System.out.println("Somebody screwed up, because the file does not exist, cannot be found, or is stolen.");
            System.err.println(e.getMessage());
        }
        catch (IOException e){
            //other errors
            System.out.println("Ya don goofed.");
            System.err.println(e.getMessage());
        }
        return indexNum;
    }
    
    //Method: UpdateChoice()
    //An extension of the previous decision
    public static void UpdateChoice(boolean add){
        String name;
        int list;
        Scanner sc = new Scanner(System.in); //The other one wasn't working so ...
        if (add){
            System.out.println("Enter a name: ");
            name = sc.nextLine();
            Update(name);
        }
        else {
            System.out.println("Enter a name: ");
            name = sc.nextLine();
            Remove(name);
        }
    }
    
    //Method: Update()
    //This method writes in the file
    public static void Update(String name){
        FileWriter out;
        BufferedWriter writeFile;
        
        try{
            out = new FileWriter(path, true);
            writeFile = new BufferedWriter(out);
            String name2;
            name2 = name.trim();
            name2 = name2.replaceAll(" ", "_"); //Replaces spaces with underscores
            
            writeFile.newLine();
            writeFile.write(name2);
            System.out.println("Added new member: " + name);
            
            writeFile.close();
            out.close();
        }
        catch (FileNotFoundException e){
            //If the file isn't found
            System.out.println("Somebody screwed up, because the file does not exist, cannot be found, or is stolen.");
            System.err.println(e.getMessage());
        }
        catch (IOException e){
            //Other errors
            System.out.println("Ya don goofed.");
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    //Method: Remove()
    //This method removes a person from the list
    public static void Remove(String name){
        FileReader in;
        BufferedReader readFile;
        FileWriter out, out2;
        BufferedWriter writeFile, deleted;
        
        try{
            in = new FileReader(path);
            readFile = new BufferedReader(in);
            
            out = new FileWriter(path, true);
            writeFile = new BufferedWriter(out);
            
            int index = -1;
            String name2;
            name2 = name.trim();
            name2 = name2.replaceAll(" ", "_");//replaces spaces with underscores
            
            //DEBUGGING
            System.out.println("name1: " + name + "\nname2: " + name2);      
            //DEBUGGING
            
            ArrayList<String> totalList = new ArrayList<>();            
            String currentItem;
            //Puts the list into the arraylist totalList
            while ( (currentItem = readFile.readLine()) != null){
                totalList.add(currentItem);
            }
            
            //Removes it
            if (totalList.contains(name2)){
                //DELETING WRITER
                out2 = new FileWriter(path, false);
                deleted = new BufferedWriter(out2);
                
                index = totalList.indexOf(name2);
                totalList.remove(index);
                
                //Deletes EVERYTHING in the list
                deleted.write("");
                //Writes out the new list
                for (int i = 0; i < totalList.size(); i++){
                    writeFile.newLine();
                    writeFile.write(totalList.get(i));
                }
                System.out.println("Removed " + name + " from the list. What a loser.");
                deleted.close();
                out2.close();
            }
            else {
                System.out.println("Name not found. ");
            }
            //Closes streams
            in.close();
            readFile.close();
            writeFile.close();
            out.close();
            
        }
        
        catch (FileNotFoundException e){
            //If the file isn't found
            System.out.println("Somebody screwed up, because the file does not exist, cannot be found, or is stolen.");
            System.err.println(e.getMessage());
        }
        catch (IOException e){
            //Other errors
            System.out.println("Ya don goofed.");
            System.err.println(e.getMessage());
        }
        
    }
    
    //Method: DisplayAll()
    //Displays the whole list of invitees
    public static void DisplayAll(){
        try{
            FileReader in = new FileReader(path);
            BufferedReader readFile = new BufferedReader(in);
                      
            ArrayList<String> totalList = new ArrayList<String>();            
            String currentItem;
            
            //Puts the list into the arraylist totalList
            while ( (currentItem = readFile.readLine()) != null){
                totalList.add(currentItem);
            }
            System.out.println("Here is our invitational list. Only cool people are allowed in.");
            for (int i = 0; i < totalList.size(); i++){
                System.out.println(totalList.get(i));
            }
            //closes everything
            in.close();
            readFile.close();
        }
        catch (FileNotFoundException e){
            //If the file isn't found
            System.out.println("Somebody screwed up, because the file does not exist, cannot be found, or is stolen.");
            System.err.println(e.getMessage());
        }
        catch (IOException e){
            //other errors
            System.out.println("Ya don goofed.");
            System.err.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // The start of the program
        System.out.println("YOOOOO WELCOME TO MY PARTAYYYYY~~~~~~~~~~~~");
        Menu(); 
    }
}
