package com.simplilearn.project.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Application {
	
	private static Scanner userSelection;
	private static Scanner credentialsInput;
	private static Scanner keyboard;
	
	private static ArrayList<String> credentials;
	
	
	public static void main(String[] args) throws Exception {
		checkFiles();
		welcomeScreen();
		logout();
	}
	

	public static void welcomeScreen() throws Exception {
		String welcomeMessage = "----------------------------------------------------" +
				                "\n|                                                  |" + 
		                        "\n|           Welcome to LockedMe.com                |" +
		                        "\n|    Your all in one solution for passwords...     |" +
		                        "\n|                                                  |" + 
		                        "\n|    Version 0.0.1-PROTYPE (Dev - Bryan Finn)      |" +
		                        "\n----------------------------------------------------";
		
		//Code to Select Next Page Based On User Input
		Map <Integer, String> userOptions = new TreeMap <Integer, String>();
		userOptions.put(1, "1. Registration for First Time Users");
		userOptions.put(2, "2. Login for Existing Users");
		
		System.out.println(welcomeMessage);
		displayUserSelection(userOptions);
		
		int input = gatherUserSelection(userOptions);
		if (userOptions.containsKey(input)) {
			if (input == 1) {
				registration();
			} else {
				login();
			}
		}else {
			System.out.println("Ensure you've entered an option that is displayed above.");
		}
		

	}
	
	public static void checkFiles() {
		File database = new File("database.txt");
		File locker = new File("locker.txt");
		try {
			database.createNewFile();
			locker.createNewFile();
		} catch (IOException e) {
			System.out.println("database.txt and locker.txt not available");
			e.printStackTrace();
		}		

	}
	
	public static int gatherUserSelection(Map<Integer, String> userOptions) {
		try {
				userSelection = new Scanner(System.in);

				int userOption = userSelection.nextInt();

			String optionTrue = userOptions.get(userOption);

			if (optionTrue != null) {
				return userOption;
			} else {
				return -1;
			}
		} catch (InputMismatchException e) {
			System.out.println("That's not a valid selection. You need to enter an integer value only");
			return -1;
		}
	}
	
	public static void displayUserSelection (Map<Integer, String> userOptions) {	
		StringBuffer message = new StringBuffer("\nPlease select one of the following options: \n\n  ");
		for (int key : userOptions.keySet()) {
			message.append(userOptions.get(key) +  
	                "\n  ");
		}
		System.out.println(message);
		System.out.println("\nType in your selection: ");
	}
	
	public static void returnToWelcomeOrExit() throws Exception {
		Map <Integer, String> userOptions = new TreeMap <Integer, String>();
		userOptions.put(1, "1. Return to Welcome Page");
		userOptions.put(2, "2. Exit Program");
		
		displayUserSelection(userOptions);
		
		int input = gatherUserSelection(userOptions);
		if (userOptions.containsKey(input)) {
			try {
			if (input == 1) {
				welcomeScreen();
			} else {
				throw new Exception ("Program Ended. Have a great day!");			
			}
			} catch (Exception e) { 
				System.out.println(e.getMessage());
			}
		}else {
			System.out.println("Ensure you've entered an option that is displayed above.");
		}
		
	}
	
	public static void registration() throws Exception {
		
		String registrationMessage = "----------------------------------------------------" +
                					 "\n|                                                  |" + 
					                 "\n|        Welcome to the Registration Page          |" +
					                 "\n|    Your all in one solution for passwords...     |" +
					                 "\n|                                                  |" + 
					                 "\n|    Version 0.0.1-PROTYPE (Dev - Bryan Finn)      |" +
					                 "\n----------------------------------------------------";
		//Collect Username/Password and define rules with regex
		System.out.println(registrationMessage);
		System.out.println("Requirements: ");
		System.out.println("\n\n  Your username should be no more than 12 characters");
		System.out.println("  Your password should be between 6-20 characters\n\n");
		
		boolean continueProgram = true;
		credentialsInput = new Scanner(System.in);
		
		//Check Username
		String username = null;
		String filePath = "database.txt";
		try {
			System.out.println("Enter Username :");
			username = credentialsInput.nextLine();
			checkUsernameCredentialsLockedMe(filePath, username);
			boolean usernameCheck = Pattern.matches("[a-zA-Z0-9]{1,12}+", username);
			if (!usernameCheck) {
				continueProgram = false;
				throw new Exception(
						"Please try again." + "\nYour username should be no more than 12 characters.");
			} 
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			returnToWelcomeOrExit();
			
		  }
		
		
		//Check Password
		String password = null;
		if (continueProgram) {
		try {
			System.out.println("Enter Password :");
			password = credentialsInput.next();
			boolean passwordCheck = Pattern.matches("[a-zA-Z0-9].+{5,20}", password);
			if (!passwordCheck) {
				continueProgram = false;
				throw new Exception(
						"Please try again." + "\nYour password should be between 6-20 characters.");
			}
			System.out.println("Congratulation! You're registered!");
			writeToCredentialsLockedMe(filePath, username, password);
		} catch (Exception e) {
			System.out.println("Program Ended: " + e.getMessage());
			returnToWelcomeOrExit();
		  }
		}
	
		//Code to Select Next Page Based On User Input
		try {
			returnToWelcomeOrExit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeToCredentialsLockedMe(String file, String username, String password) {
        PrintWriter write;
//        credentialsLockedMe = new CredentialsLockedMe(username, password);
//		Map <String, CredentialsLockedMe> storeCredentials = new TreeMap <String, CredentialsLockedMe>();
//		storeCredentials.put(username, credentialsLockedMe);
		try {
			write = new PrintWriter(new FileWriter(file,true));
			write.println(username);
			write.println(password);
			write.close();
		} catch (IOException e) {
			System.out.println("Sorry that file doesn't exist");
		}      
	}
	
	public static void readFromCredentialsLockedMe(String file) {
	    BufferedReader read = null;
	    try {
	        String credentials;
	        read = new BufferedReader(new FileReader(file));
	        while ((credentials = read.readLine()) != null) {
	            System.out.println(credentials);
	        }
	        read.close();
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	
	public static void checkUsernameCredentialsLockedMe(String file, String username) throws Exception {
	    BufferedReader read = null;
	    //boolean usernameCheck = Pattern.matches("[a-zA-Z0-9]+(.+){6,20}", read.readLine());
	    try {
	        read = new BufferedReader(new FileReader(file));
	        int count = 0;
	        String line = null;
	        while ((line = read.readLine()) != null) {
	            if (username.equalsIgnoreCase((line)) && count % 2 == 0) {
	            	read.close();
	            	throw new Exception("\n***Sorry that username already exists.***"+"\n***Try Again***");
	            }
	        }
	        read.close();
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        registration();
	    }
	}
	public static void login() throws Exception {
		String loginMessage = "----------------------------------------------------" +
				 			  "\n|                                                  |" + 
				 			  "\n|                 Welcome Back!                    |" +
				 			  "\n|    Your all in one solution for passwords...     |" +
				 			  "\n|                                                  |" + 
				 			  "\n|    Version 0.0.1-PROTYPE (Dev - Bryan Finn)      |" +
				 			  "\n----------------------------------------------------";
		//Collect Username/Password and define rules with regex
		System.out.println(loginMessage);
		System.out.println("Please Login: \n");
		credentialsInput = new Scanner(System.in);
		
		String username = null;
		String filePath = "database.txt";
		
		System.out.println("Enter Username :");
		username = credentialsInput.nextLine();
		
		String password = null;
		System.out.println("Enter Password :");
		password = credentialsInput.next();
		
		boolean continueProgram = loginCheckCredentialsLockedMe(filePath, username, password);
		
		if (continueProgram) {
			System.out.println("You have successfully logged in!");
			
			//Code to Select Next Page Based On User Input
			Map <Integer, String> userOptions = new TreeMap <Integer, String>();
			userOptions.put(1, "1. Check Your Stored Credentials");
			userOptions.put(2, "2. Add Credentials");
			userOptions.put(3, "3. Delete Credentials");
			userOptions.put(4, "4. Exit");
			
			displayUserSelection(userOptions);
			
			int input = gatherUserSelection(userOptions);
			if (userOptions.containsKey(input)) {
				if (input == 1) {
					//checkStoredCredentials(username);
					printStoredCredentials(username);
					returnToWelcomeOrExit();
				} else if (input == 2) {
					String [] userInformation = addCredentials();
					writeToCredentialsLocker(username, userInformation[0], userInformation[1], userInformation[2]);
					returnToWelcomeOrExit();
				
				} else if (input == 3) {
					System.out.println("Please enter the name of the application that you'd like to remove: ");
					keyboard = new Scanner(System.in);
					String application = keyboard.nextLine();
					removeApplication(username, application, credentialsInput);
				} else {
					returnToWelcomeOrExit();
				}
			}else {
				System.out.println("Ensure you've entered an option that is displayed above.");
			}
		} else {
			System.out.println("***SORRY, YOUR CREDENTIALS ARE INVALID***");
				returnToWelcomeOrExit();
		}
	}
	
	public static boolean loginCheckCredentialsLockedMe(String file, String usernameInput, String passwordInput) {
	    BufferedReader read = null;
	    //boolean usernameCheck = Pattern.matches("[a-zA-Z0-9]+(.+){6,20}", read.readLine());
	    try {
	        read = new BufferedReader(new FileReader(file));
	        int count = 0;
	        String username = null;
	        String password = null;
	        //System.out.println("before while : " + read.readLine());
	        String line = null;
	        while ((line = read.readLine()) != null) {
	            if (usernameInput.equalsIgnoreCase((line)) && count % 2 == 0) {
	            	username = line;
	            	password = read.readLine();
	            	break;
	            }
	            count++;
	        }
	        read.close();
	        if (usernameInput.equalsIgnoreCase(username) && passwordInput.equals(password)) {
	        	return true;
	        }
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        System.exit(400);
	    }
	    return false;
	}
	//CheckStored Credentials
	public static ArrayList<String> checkStoredCredentials (String masterUsername) {
		String file = "locker.txt";
	    BufferedReader read = null;
	    credentials = new ArrayList<String>();
	    credentials.add("Credentials Stored for LockedMe Account: " + masterUsername);
	    credentials.add("--------------------------");
	    
	    try {
	        read = new BufferedReader(new FileReader(file));
	        int count = 0;
	        //System.out.println("before while : " + read.readLine());
	        String line = null;
	        while ((line = read.readLine()) != null) {
	            if (masterUsername.equalsIgnoreCase((line)) && count % 4 == 0) {
	            	credentials.add(read.readLine()); //application
	            	credentials.add(read.readLine()); //username
	            	credentials.add(read.readLine()); //password
	            	credentials.add("");
	            	count=+3;
	            }
	            count++;
	        }
	        read.close();
	        if(credentials.size() < 3) {
	        	credentials.add("\nWe couldn't find any credentials for that username!"
	        			+ "\nTry to add credentials first before checking if you have any stored.");
	        }	        	
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        System.exit(400);
	    }
	    return credentials;
	}
	
	public static void printStoredCredentials(String username) {
		credentials = checkStoredCredentials(username);
		for (String credential : credentials) {
        	System.out.println(credential);
        }
	}
	public static String[] addCredentials () {

		System.out.println("Please fill out of the following information: \n");
		credentialsInput = new Scanner(System.in);
		
		String application = null;
		System.out.println("Enter the name of the application :");
		application = credentialsInput.nextLine();
		
		String username = null;
		System.out.println(application + " username:");
		username = credentialsInput.next();
		
		String password = null;
		System.out.println(application + " password:");
		password = credentialsInput.next();
		
		String[] input = {application, username, password};
		
		return input;
		
	}
	//Write master username, username, application, and password to locker.txt
	public static void writeToCredentialsLocker (String masterUsername, String application, String username, String password) {
		PrintWriter write;
		String file = "locker.txt";
		try {
			write = new PrintWriter(new FileWriter(file,true));
			write.println(masterUsername);
			write.println("Application Name: " + application);
			write.println("Username: " + username);
			write.println("Password: " + password);
			write.close();
			System.out.println("Awesome! Your information has been added for " + application);
		} catch (IOException e) {
			System.out.println("Sorry that file doesn't exist");
		}      
	}
	//Remove Credentials
	public static void removeApplication (String masterUsername, String keyboardInput, Scanner option) throws Exception{
		boolean remove = confirmCredentialsToRemove(masterUsername, keyboardInput, option);
		if (remove) {
			ArrayList<String> newLockerFile = readLockerFileToArrayList(masterUsername, keyboardInput, option);
			writeLockerFileFromArrayList(newLockerFile, keyboardInput, masterUsername);
		} else {
				login();
		}
	}
	public static boolean confirmCredentialsToRemove (String masterUsername, String application, Scanner option) throws Exception {
		String file = "locker.txt";
	    BufferedReader read = null;
	    credentials = new ArrayList<String>();
	    credentials.add("Credentials To Remove for LockedMe Account: " + masterUsername);
	    credentials.add("--------------------------");
	    application = "Application Name: " + application;
	    
	    try {
	        read = new BufferedReader(new FileReader(file));
	        //System.out.println("before while : " + read.readLine());
	        String line = null;
	        int count = 0;
	        while ((line = read.readLine()) != null) {
	            if (masterUsername.equalsIgnoreCase((line)) && count % 4 == 0) {
	            	//System.out.println("Logic check1");
	            	line = read.readLine();
	            	count++;
	            	//System.out.println("Line variable is " + line);
	            	if (application.equalsIgnoreCase((line))) {
	            		//System.out.println("Logic check 2");
		            	credentials.add(line); //application
		            	credentials.add(read.readLine()); //username
		            	credentials.add(read.readLine()); //password
		            	credentials.add("");
		            	count=+3;
	            	}
	            }
	            count++;
	        }
	        read.close();
	        if(credentials.size() < 3) {
	        	System.out.println("\nWe couldn't find any credentials for that username and/or application!"
	        			+ "\n\nEnsure the following..."
	        			+ "\n  Check which passwords you've stored with us"
	        			+ "\n  The application name is spelled correct");
	        	
	        } else {
	        	for (String each : credentials) {
	        		System.out.println(each);
	        	}
	        	System.out.println("\nAre you sure you want to remove this application? (Removes first option only)"
	        					 + "\n   1. Yes"
	        					 + "\n   2. No");
	        	int answer = option.nextInt();
	        	if (answer == 1) {
	        		return true;
	        	} else {
	        		login();
	        	}
	        	}
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	        returnToWelcomeOrExit();
	    }
	    return false;
	}

	public static ArrayList<String> readLockerFileToArrayList(String masterUsername, String application, Scanner option) throws Exception {
		String file = "locker.txt";
	    BufferedReader read = null;
	    credentials = new ArrayList<String>();
	    application = "Application Name: " + application;
	    try {
	        read = new BufferedReader(new FileReader(file));
	        //System.out.println("before while : " + read.readLine());
	        String line = null;
	        
	        //Add everything to arraylist for easy data manipulation
	        //If masterUsername and application line up, then remove it from the arraylist
	        //We'll simply write the updated arraylist again
	        //in the writeLockerFileFromArrayList method
	        while ((line = read.readLine()) != null) {
	        	credentials.add(line);
	        }
	        for (int index=0; index < credentials.size(); index++) {
	        	if((credentials.get(index)).equalsIgnoreCase(masterUsername) 
	        			&& (credentials.get(index+1)).equalsIgnoreCase(application)) {
	        	credentials.remove(index);
	        	credentials.remove(index);
	        	credentials.remove(index);
	        	credentials.remove(index);
	        	}
	        }
	        read.close();
	        if(credentials.size() < 3) {
	        	System.out.println("We couldn't find any credentials for that username and/or application!"
	        			+ "\nEnsure the following..."
	        			+ "\n  Check which passwords you've stored with us"
	        			+ "\n  The application name is spelled correct");
	        	login();
	        	
	        }
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			returnToWelcomeOrExit();
		}
		return credentials;
		}
	public static void writeLockerFileFromArrayList (ArrayList<String> credentials, String application, String username) throws Exception {
		PrintWriter write;
		String file = "locker.txt";
		try {
			write = new PrintWriter(new FileWriter(file));
			for (String credential : credentials) {
				if(credential !=null) {
					write.println(credential);
				}
			}
			write.close();
			System.out.println("Awesome! We've removed your account for " + application
					+ "\nHere's an updated list of your stored credentials");
			printStoredCredentials(username);
			login();
		} catch (IOException e) {
			System.out.println("Sorry that file doesn't exist");
		} 
	}
	public static void logout() {
		System.out.println(  "----------------------------------------------------" +
				           "\n|                                                  |" + 
		                   "\n|         Thanks for using LockedMe.com!           |" +
		                   "\n|    Your all in one solution for passwords...     |" +
		                   "\n|                                                  |" + 
		                   "\n|    Version 0.0.1-PROTYPE (Dev - Bryan Finn)      |" +
		                   "\n----------------------------------------------------");
		System.out.println("\n***Have a great day!***");
		System.exit(0);
	}
	}
