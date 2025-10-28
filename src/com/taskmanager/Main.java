package com.taskmanager;

import java.util.Scanner;
import com.taskmanager.entity.*;

public class Main {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		System.out.println("Task: ");
		String task = scn.nextLine();
		
		Task.createTask(task);
		
	}
}
