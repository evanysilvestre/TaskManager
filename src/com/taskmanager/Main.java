package com.taskmanager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import com.taskmanager.Entity.*;

public class Main {
	
	public static void menu() {
		System.out.println("1. Add new task\n2. Update a task\n3. Delete a task\n4. See all tasks\n5. See to do tasks\n6. See in-progress tasks\n7. See done tasks\n0. Exit");
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		/*System.out.println("Task: ");
		String task = scn.nextLine();
		
		Task.createTask(task);
		
		Main.menu();*/
		
		try {
		      File myObj = new File("data.json"); // Create File object
		      if (myObj.createNewFile()) {           // Try to create the file
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace(); // Print error details
		    }
		scn.close();		

		
	}
}
