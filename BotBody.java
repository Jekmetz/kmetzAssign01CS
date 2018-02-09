/*README
 * github
 * ---------------------------------------
 * 1. make sure you have git installed. if you don't just type in "install git" in google. pretty simple
 * 2. navigate to your command line (type "cmd" in your windows search bar in the bottom left of your screen)
 * 3. open command line
 * 4. navigate to wherever is convient by using dir to find out what folders you have, and then cd <directory name> until the bit before the '>' says the correct directory
 * 5. type 'git clone <link>' (copy and paste the github link from the google doc and put that where '<link>' is)
 * 6. javac BotMain.java
 * 7. java BotMain
 * 
 * google drive
 * ---------------------------------------
 * 1. download the folder
 * 2. go to your downloads, click on the folder, and then click on extract all
 * 3. navigate to wherever is convenient
 * 4. click extract.
 * 5. javac BotMain.java
 * 6. java BotMain
 * 
 *Before using this program, you must make sure that you have all of the text files listed below in the same folder with the java files:
 *defaultlines.txt
 *hellofile.txt
 *hellotest.txt
 *nothingput.txt
 *thankstest.txt
 *welcome.txt
 *colortime.txt
 *commands.txt
 *defaultcolor.txt
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class BotBody {
	
	//Initialize the interface components
	private JFrame frame = new JFrame();
	private JTextField txtEntr = new JTextField();
	private JTextArea txtArea = new JTextArea();
	private JScrollPane sp = new JScrollPane(txtArea);

	public BotBody() {

		// Initialize Colors... The most important part... I'm sorry if the colors are
		// bad
		Color frameColor = new Color(11, 8, 214);
		Color textAreaColor = new Color(132, 175, 244);
		Color textColor = new Color(0, 0, 0);

		// Initialize Area
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setTitle("CS110 Assinment 01 Jay Kmetz");
		frame.getContentPane().setBackground(frameColor);

		// Initialize TxtEntr
		txtEntr.setLocation(12, 520);
		txtEntr.setSize(560, 30);
		txtEntr.setBackground(textAreaColor);
		txtEntr.setForeground(textColor);

		// Initialize txtArea
		sp.setSize(560, 500);
		sp.setLocation(12, 10);
		sp.setWheelScrollingEnabled(true);
		txtArea.setEditable(false);
		txtArea.setBackground(textAreaColor);
		txtArea.setForeground(textColor);
		txtArea.setLineWrap(true);

		// these next two lines of code just make sure that it always scrolls down
		// I had quite a bit of trouble figuring this one out
		DefaultCaret caret = (DefaultCaret) txtArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// Let the program know what files it is going to have to check for user input in a later method
		ArrayList<String> filesCheck = new ArrayList<String>();
		filesCheck.add("hellotest.txt");
		filesCheck.add("thankstest.txt");

		// add the enter bar and the text area box
		frame.add(txtEntr);
		frame.add(sp);

		// Initial message
		menuText();

		// Alright, Let's get down to business!
		txtEntr.addActionListener(new ActionListener() {		//Triggers whenever you press enter
			public void actionPerformed(ActionEvent arg0) {
				String text = txtEntr.getText(); 				//Initialize the text in the text box to variable text
				txtArea.append("You: " + text + "\n");
				txtEntr.setText("");

				if (checkFiles(text, filesCheck)) {				//If there is string text in files in arraylist filetext
					// I was stuck on this for a long time and I put this in the code
					// in order to check if the method checkFiles works
					// The second I saw "Youve done it" in the system.out, I was
					// So happy. So happy in fact that I'm gonna leave it in the code.
					System.out.println("Youve done it");

					switch (whichFile(text, filesCheck)) {
					case "hellotest.txt":
						botSay(randLine(readFile("hellofile.txt")));		//Chose a random line from the file hellofile.txt
						break;
					case "thankstest.txt":
						botSay(randLine(readFile("welcome.txt")));			//Chose a random line from the file welcome.txt
					}

				} else {
					switch (text) {

					default: // This is if somebody puts some random stuff
						botSay(randLine(readFile("defaultlines.txt")));		//Chose a random line from the file defaultlines.txt
						break;
					case "": // This is if somebody puts just a blank line
						botSay(randLine(readFile("nothingput.txt")));		//Well you get the gist now
						break;
					case "\\color":
						colorTime();										//Randomly switch the color of the interface
						botSay(randLine(readFile("colortime.txt")));
						break;
					case "\\colordefault":
						colorDefault();
						botSay(randLine(readFile("defaultcolor.txt")));
						break;
					case "\\gas":
						// Oh boy, here we go!
						gasScript();										//Run the gas script in the system console
						break;
					case "\\menu":
						menuText();											//Display the menu text
						break;
					case "\\commands":
						txtArea.append("\nCommands\n--------------------------------------------------------------\n");
						blortLines(readFile("commands.txt")); 				//To be quite honest here, I love the word "blort" so I included it here. It is 'snotting' the lines on the screen.
						break;
					}
				}
			}
		});
	}

	//User defined function that appends whatever arg0 is in botSay(String arg0) to the text area
	public void botSay(String words) {
		txtArea.append("GasBot2.0: " + words + "\n\n");
	}

	// User defined function readFile that takes the name of a file and gives out an
	// ArrayList of all of the lines in it
	public ArrayList<String> readFile(String file) {
		
		//Initialize the file, the reader, and the list of words that will come out of this thing
		File newfile = new File(file);
		FileReader fr;
		BufferedReader br = null;
		ArrayList<String> wordlist = new ArrayList<String>();

		//Standard adding each line from the file specified into an array
		try {
			fr = new FileReader(newfile);
			br = new BufferedReader(fr);

			while (br.ready()) {
				wordlist.add(br.readLine());
			}
			
			//Handling Exceptions from the previous try section
		} catch (FileNotFoundException e) {
			System.out.printf("This file could not be found: %s", e.toString(), newfile.toString());
		} catch (IOException e) {
			System.out.printf("The file %s launched an IOException.", newfile.toString());
		}

		//Close the buffered reader which in turn closes down the filereader, and the file.
		try {
			br.close();
		} catch (IOException e) {
			// Don't even worry about it.
		}

		return wordlist;		//give that ArrayList back into whatever is calling it. (Usually randLine())
	}

	// User defined function that takes an ArrayList (Usually generated by readFile())
	// and returns a random string from it
	public String randLine(ArrayList<String> list) {
		Random r = new Random();
		return list.get(r.nextInt(list.size()));
	}
	
	//User defined function that prints the whole file to the txtArea in line by line style
	public void blortLines(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			txtArea.append(list.get(i) + "\n");
		}
	}

	// User defined function that checks an array of files
	// and cross references them with some text to see if they were in it
	public boolean checkFiles(String text, ArrayList<String> files) {
		
		//Initialize the reader and the thing that will number my ArrayList (that's what the iterator is)
		FileReader fr;
		BufferedReader br = null;
		Iterator<String> itr = files.iterator();

		while (itr.hasNext()) {						//While the arraylist has a next line...
			File newfile = new File(itr.next());	//Set the file to be checked, the next file in that arrayList. (Remember the ArrayList that we made for checking files? Here it is)

			try {
				fr = new FileReader(newfile);
				br = new BufferedReader(fr);

				while (br.ready()) {
					if (text.toLowerCase().equals(br.readLine())) {		//Turn whatever was in the textbox to lowercase and check it. So the user could type HeLlO and it would still be ok
						return true;									//If that check works, then return true, a file does contain that word
					}
				}

				//Handle Exceptions
			} catch (FileNotFoundException e) {
				System.out.printf("This file could not be found: %s", e.toString(), newfile.toString());
			} catch (IOException e) {
				System.out.printf("The file %s launched an IOException.", newfile.toString());
			}

			//Close the reader and consequently the file as well.
			try {
				br.close();
			} catch (IOException e) {
				// Don't even worry about it.
			}
		}

		return false;		//If we went through all of that nonsense for naught, then return false. There is no file with that stuff in it
	}

	// User defined function that checks which file a certain line is from
	public String whichFile(String text, ArrayList<String> files) {
		
		//Initialize reader and the iterator again
		FileReader fr;
		BufferedReader br = null;
		Iterator<String> itr = files.iterator();

		while (itr.hasNext()) {							//If there is a next file to check...					
			String fileHolder = itr.next();				/*Store that value into a variable fileHolder
														 *We do this so that we can call it without calling the next one with itr.next()
														 */
			File newfile = new File(fileHolder);		//Set the file to the fileHolder

			try {
				fr = new FileReader(newfile);			//Standard file reading stuff
				br = new BufferedReader(fr);

				while (br.ready()) {									//While there is a next line in the file
					if (text.toLowerCase().equals(br.readLine())) {		//Do the same check that we did in checkFile
						return fileHolder;								//but this time, return the name of the file
					}
				}

				//Handle Exceptions
			} catch (FileNotFoundException e) {
				System.out.printf("This file could not be found: %s", e.toString(), fileHolder.toString());
			} catch (IOException e) {
				System.out.printf("The file %s launched an IOException.", fileHolder.toString());
			}

			//Close the reader and consequently the file
			try {
				br.close();
			} catch (IOException e) {
				// Don't even worry about it.
			}
		}

		/*
		 * Now the function will never get to this point because this function is only called after
		 * checkFiles has been called and not only that, but returned a value of true
		 * That means that there is a file that has the droids we're looking for.
		 */

		return "";
	}

	// User defined function that makes the screen change color
	public void colorTime() {

		Random r = new Random();
		Random g = new Random();
		Random b = new Random();

		Color newframeColor = new Color(r.nextInt(255), g.nextInt(255), b.nextInt(255));
		Color textAreaColor = new Color(r.nextInt(255), g.nextInt(255), b.nextInt(255));
		Color textEntrColor = new Color(r.nextInt(255), g.nextInt(255), b.nextInt(255));
		Color newTextColor = new Color(r.nextInt(255), g.nextInt(255), b.nextInt(255));

		frame.getContentPane().setBackground(newframeColor);
		txtArea.setBackground(textAreaColor);
		txtEntr.setBackground(textEntrColor);
		txtArea.setForeground(newTextColor);
		txtEntr.setForeground(newTextColor);
	}

	// User defined function that makes the screen change back to the default color
	// scheme
	public void colorDefault() {
		Color frameColor = new Color(11, 8, 214);
		Color textAreaColor = new Color(132, 175, 244);
		Color textColor = new Color(0, 0, 0);

		frame.getContentPane().setBackground(frameColor);
		txtArea.setBackground(textAreaColor);
		txtEntr.setBackground(textAreaColor);
		txtArea.setForeground(textColor);
		txtEntr.setForeground(textColor);
	}

	// User defined function that will be the whole gas thing.... Supposedly the
	// standing point of this project
	public void gasScript() {
		// Let's initialize some variables and the scanner why don't we?
		Scanner scan = new Scanner(System.in);
		int numGasStations = 0;
		boolean isGood = false;
		HashMap<String, double[]> gasMap = new HashMap<String, double[]>();
		double gasMileage = 0;
		double wantedGas = 0;
		String choice = null;
		double holdVal = 1000000000;
		
		//Let's start requesting things and validating input
		
		/*
		 * Before you go and get all mad at me for not making a function that does this repetitive action
		 * I would just like to say that I did that and it did not work because it would initialize the scanner and scan before
		 * it had a chance to actually put anything to the console... I did make a method like that and it did not work.
		 * I apologize for how sloppy the result looks.
		 * 
		 * For those doubters out there, this is what it looked like:
		 * 
		 public double dubAuthent(String dispString) {
			boolean isGood = false;
			double dub = 0;
			Scanner scan = new Scanner(System.in);
			
			System.out.printf(dispString);
			do {
				if (scan.hasNextDouble()) {
					dub = scan.nextDouble();
					scan.nextLine();
					isGood = true;
				}else {
					System.out.printf("That wasn't a number I could understand... Please try again!: ");
					scan.nextLine();
				}
			} while(!isGood);
			
			scan.close();
			
			return dub;
		}
		 *
		 *Much to my chagrin, it worked the first time you called it, and no more. So here we are
		 */
		
		System.out.printf("What is the average gas mileage of your car? (mpg): ");						//Car Mileage
		do {
			if (scan.hasNextDouble()) {
				gasMileage = scan.nextDouble();
				scan.nextLine();
				isGood = true;
			}else {
				System.out.printf("That wasn't a number I could understand... Please try again!: ");	
				scan.nextLine();
			}
		} while(!isGood);
		isGood = false;

		System.out.printf("How many gallons of gas do you want? (gallons): ");							//Gallons of Gas needed
		do {
			if(scan.hasNextDouble()) {
				wantedGas = scan.nextDouble();
				scan.nextLine();
				isGood = true;
			}else {
				System.out.println("That wasn't a number I could understand... Please try again!: ");
				scan.nextLine();
			}
		}while(!isGood);
		isGood = false;
		

		System.out.printf("How many gas Stations would you like to consider?: ");					//Number of stations
		do {
			if (scan.hasNextInt()) {
				numGasStations = scan.nextInt();
				scan.nextLine();
				isGood = true;
			} else {
				System.out.println("That wasn't a number I could understand... Please try again!: ");
				scan.nextLine();
			}
		}while (!isGood);
		isGood = false;

		//For the amount of gastations there are, do this
		for (int i = 0; i < numGasStations; i++) {
			System.out.printf("Name of Station #%d: ", i + 1);		//Name of station #1,2,etc.
			double[] doubles = { 0, 0, 0 };							//Gotta initialize it all the time or all of the values will be the same
			gasMap.put(scan.nextLine(), doubles);					//Put the name of the station in as the key, and the array {0,0,0} in for the value
		}
		
		for(Map.Entry<String, double[]> entry : gasMap.entrySet()) {

			System.out.printf("\nHow much is the price of gas at %s gas station? (dollars/gallon): ", entry.getKey());	//first value in value array is price at gas station
			do {
				if(scan.hasNextDouble()) {
					entry.getValue()[0] = scan.nextDouble();
					scan.nextLine();
					isGood = true;
				}else {
					System.out.println("That wasn't a number I could understand... Please try again!: ");
					scan.nextLine();
				} 
			}while(!isGood);
			isGood = false;

			System.out.printf("\nHow far away is %s gas station? (miles): ",entry.getKey());							//second value is the distance away
			do {
				if(scan.hasNextDouble()) {
					entry.getValue()[1] = scan.nextDouble();
					scan.nextLine();
					isGood = true;
				}else {
					System.out.println("That wasn't a number I could understand... Please try again!: ");
					scan.nextLine();
				} 
			}while(!isGood);
			isGood = false;
			
			entry.getValue()[2] = (2 * entry.getValue()[1] * entry.getValue()[0]) / gasMileage + wantedGas * entry.getValue()[0]; //third value is cost: 2 trips of a mile multiplied by gallons per mile times times dollars per gallon = dollars for trip. + how many gallons needed times dollars per gallon.
			
			//Esentially this is just battling all of the third values in the array. Which one is the smallest. Hold value is just a big number at the start
			if (entry.getValue()[2] < holdVal) {
				holdVal = entry.getValue()[2];
				choice = entry.getKey();
			}
		}
		
		
		System.out.printf("\nI would reccomend going to %s gas station.\n\n",choice);
		
		for(Map.Entry<String, double[]> entry : gasMap.entrySet()) {
			System.out.printf("If you go to %s gas station, it will cost you $%.2f.\n",entry.getKey(),entry.getValue()[2]);		//Display all of the prices
		}
		
		System.out.println("\nNow that you've done this, you can go back to the bot interface again!");
		
		scan.close();
		botSay("Well that was neat!");		//Ahh what the heck
	}
	
	//The menu text... it just looks better up top if this bit is down here.
	public void menuText() {
		botSay("Hi! I am GasBot2.0! I murde-... well GasBot1.0 is not here anymore. Anyhoo, I've been\n"
				 + "brought to this world to help you decide where to go get gas! All you have to do is "
				 + "answer simple\nquestions! To get this menu again, type \\menu. This way you don't get "
			 	 + "confused. To get a list of\ncommands, type '\\commands' To find out the best place to"
				 + " get gas, type in '\\gas'. If you do use the gascommand, please type all of your answers "
				 + "in the console in Eclipse. Let's have some fun!");
	}
}
