package com.taskmanager;

import java.util.Scanner;
import com.taskmanager.entity.*;

public class Main {
	
	public static void menu() {
		System.out.println("1. Add new task\n2. Update a task\n3. Delete a task\n4. See all tasks\n5. See to do tasks\n6. See in-progress tasks\n7. See done tasks\n0. Exit");
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		/*System.out.println("Task: ");
		String task = scn.nextLine();
		
		Task.createTask(task);*/
		
		Main.menu();
		

		
	}
}
