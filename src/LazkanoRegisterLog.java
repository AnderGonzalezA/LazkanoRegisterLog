import java.io.File;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.zubiri.*;
public class LazkanoRegisterLog {
	public static void main(String[] args) throws IOException {
		Users users = new Users();
		Scanner sc = new Scanner(System.in);
	    File usersFile = new File("C:\\Users\\ik013043z1\\eclipse-workspace\\LazkanoRegisterLog\\src\\users.txt");
	    Scanner sc2 = new Scanner(usersFile);
	    while(sc2.hasNext()) {
	    	String userInformation = sc2.nextLine();
	    	String[] userInformationArray=userInformation.split(" ");
	    	String username = userInformationArray[0];
	    	String password = userInformationArray[1];
	    	users.register(new User(username,password));
	    }
		System.out.println("Please, select an option:");
		boolean finished = false;
		while (!finished) {
			System.out.print("1 Register.\n2 Log in.\n0 Exit.");
			// Check that the user enters a number
			if (sc.hasNextInt()) {
				int option = sc.nextInt();
				// Jump the token '/n'
				sc.nextLine();
				switch (option) {
				case 1: /* Register */
					System.out.println("Please, enter the username.");
					boolean usernameEntered = false;
					while (!usernameEntered) {
						String entered = sc.nextLine();
						boolean userAlready = false;
						for (int i=0;i<users.getUsers().size();i++) {
							if (users.getUsers().get(i).getUsername().equals(entered)) {
								userAlready = true;
							}
						}
							if (!userAlready) {
								usernameEntered = true;
								String username = entered;
								System.out.println(
										"Please, enter the password (It must be no less than 8 characters and must contain letters, simbols and numbers).");
								boolean passwordEntered = false;
								while (!passwordEntered) {
									entered = sc.nextLine();
									User newUser = new User(username, entered);
									if (newUser.verifyPassword(entered)) {
										passwordEntered = true;
										users.register(newUser);
										System.out.println("You were registered successfully.");
									} else {
										System.out.println("Please, enter a valid password.");
									}
								}
							}else {
								System.out.println("That username is already used, please enter another one.");
							}
					}
					break;
				case 2: /* Log in */
					usernameEntered = false;
					while (!usernameEntered) {
						System.out.println("Please, enter the username.");
						String entered = sc.nextLine();
						boolean userAlready = false;
						for (int i=0;i<users.getUsers().size();i++) {
							if (users.getUsers().get(i).getUsername().equals(entered)) {
								userAlready = true;
							}
						}
							if (userAlready) {
								usernameEntered=true;
								String username = entered;
								System.out.println(
										"Please, enter the password.");
								boolean passwordEntered = false;
								while (!passwordEntered) {
									entered = sc.nextLine();
									User user = new User(username,entered);
									if (user.verifyPassword(entered)) {
										if (users.login(username,entered)) {
											passwordEntered = true;
											System.out.println("You loggined in successfully, " + username + ".");
											boolean logOut=false;
											while (!logOut) {
												System.out.print("1 Change the password.\n2 Remove the user.\n0 Log out.");
												// Check that the user enters a number
												if (sc.hasNextInt()) {
													option = sc.nextInt();
													// Jump the token '/n'
													sc.nextLine();
													switch (option) {
													case 1: /* Change password */
														System.out.println("Please, enter the new password (It must be no less than 8 characters and must contain letters, simbols and numbers).");
														passwordEntered = false;
														while (!passwordEntered) {
															entered=sc.nextLine();
															User modifiedUser = new User(username,entered);
															if (modifiedUser.verifyPassword(entered)) {
																passwordEntered=true;
																users.changePassword(username,entered);
																System.out.println("You changed the password successfully");
															}else {
																System.out.println("Please, enter a valid password.");
															}
														}
														break;
													case 2: /* Remove user */
														users.getUsers().remove(user);
														System.out.println("You removed the user successfully");
														logOut=true;
														break;
													case 0:
														logOut = true;
														break;
													}
												} else {
													System.out.println("You didn't select a possible option");
													// Jump the token '/n'
													sc.nextLine();
												}
											}
										}else {
											System.out.println("The password was incorrect, try it again please.");
										}
									} else {
										System.out.println("Please, enter a valid password.");
									}
								}
							}else {
								System.out.println("That username does not exist, try it again");
							}
					}
					break;
				case 0:
				    BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile));
					String userInformation = "";
					for (int i=0; i<users.getUsers().size();i++) {
						String username = users.getUsers().get(i).getUsername();
						String password = users.getUsers().get(i).getPassword();
						userInformation = username + " " + password;
						writer.write(userInformation);
						writer.newLine();
					}
				    writer.close();
					finished = true;
					break;
				}
			} else {
				System.out.println("You didn't select a possible option");
				// Jump the token '/n'
				sc.nextLine();
			}
		}
		sc.close();
		sc2.close();
	}
}