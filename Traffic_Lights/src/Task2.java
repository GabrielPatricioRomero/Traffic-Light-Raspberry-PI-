import swiftbot.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class Task2
{
static SwiftBotAPI sb;

public static void main(String args []) throws InterruptedException

{
      sb = new SwiftBotAPI();
     // SwiftBotAPIclass
     // sb is a object
      //Is an object of the SwiftBotAPI class  used to interact with the robot enabling movements

         
   	 // User interface messages printed in colours
    System.out.println(yellowcolour+"***********************************************************************************"+ whitecolour);
    System.out.println(yellowcolour+"***********************************************************************************"+ whitecolour);
    System.out.println(yellowcolour+"****************************** Traffic Light Program ******************************"+ whitecolour);
    System.out.println(yellowcolour+"***********************************************************************************"+ whitecolour);
    System.out.println(yellowcolour+"***********************************************************************************" + whitecolour);
    System.out.println("");
	System.out.println(redcolour+" -> Welcome To Traffic Light Program :)"+whitecolour);
	System.out.println(redcolour+" -> Intructions:\n"+ whitecolour);
	System.out.println("-SwiftBot will recognise (blue,green and red) traffic lights using the main camera.");
	System.out.println("-SwiftBot will respond to traffic lights when they are 20 cm or less ahead of the robot.");
	System.out.println("-Press button 'A' on SwiftBot to start the program.");
	System.out.println("-Press button 'X' on SwiftBot to stop the program.");
	System.out.println("-Press button 'Y' after pressing button 'X' if you want to be shown the game results");
	System.out.println("-Press button 'X' twice after pressing button 'A' if you want to store the game results in a text file.");
	System.out.println("                         ************************************");
	System.out.println("");
	System.out.println(magentacolour+"-> Press button 'A' to initiate program.\n"+whitecolour);
	
	try {
		
	        sb.enableButton(Button.A, () -> {
	        	
		   //if condition is true then will execute
	        	
	        if (!program)
	        {
	        	//Start recording time of execution
	        	starttime=System.currentTimeMillis();

		        System.out.println(magentacolour+"-> Button A has been pressed."+whitecolour);
	        	program = true; 
	        	
	        	//Calls ultrasound method
	            Ultrasound();
	            
	            sb.disableButton(Button. A );

			   					
				
	        }
	   
	        else 
	        {
	        	program = false;
	        	
	        }

	        });

	    
	        
	        
	        sb.enableButton(Button.X, () -> {
	          

	        	if (program)
	        	{
	        		        		
	        		program = false;
	        		
	        		endtime=System.currentTimeMillis();
	        		mills=endtime-starttime;
	        		mins=mills /(1000.0 * 60.0);
	        		
	        	//Calculates duration time in milliseconds then it is transformed into minutes
	        		
	        		//Disable all underlights and movements of the robot.
	        		
	        		sb.disableUnderlights();

	        		sb.stopMove();
	        		
	        		sb.disableButton(Button.X);
	        		StopProgram();
	        		


	             	
	        	}
	        });
	        
		}
	catch (Exception e )
	{
		e.printStackTrace();
		System.out.println("An ERROR at the moment of setting up buttons.");
		
	}
	 
		
	

}

public static void Ultrasound()

 
{

	System.out.println("-> Robot in Default Mode");
	System.out.println("-> Looking for traffic lights...");
	
	sb.fillUnderlights(YELLOW);
    sb.startMove(64, 72);
    
	    try
	    {
	    while(program)	
	    {
		double object = sb.useUltrasound();
		Thread.sleep(100);
		
		if (object<=30.0)
			
		{
			// Calls this method to identify what colour is if an object is 30cm or less ahead of it.
			
			Image_Recognition();
     	}
	
		//Thread.sleep(50);
		if (!program)
		{

			break;
		}
		
	    }
	    }
	    catch (Exception e)
		
		{
			e.printStackTrace();
		}
	   
}
/*
//This method contains Ultrasound which detects an object if it is 30cm  or less ahead of the robot
 * However, the robot would react 20 cm or less because it contains some delays 

*/
public static void Ultrasound2()
{
	System.out.println("-> Robot in Default Mode");
	System.out.println("-> Looking for traffic lights...");
	
	 sb.startMove(64, 72);
		// The robot should identify an object when it is 30 cm or less in front of it.
		    try
		    {
		    while(true)	
		    {
			double object = sb.useUltrasound();
			Thread.sleep(150);
			
			if (object<=30.0)
			{	
			
				// Calls this method to identify what colour is if an object is 30 or less ahead of it.
				Image_Recognition();
	     	}
			
		    if (!program)
		    {
			//Thread.sleep(50);
			break;
			
		    }
		    }
            }
		    
		    catch (Exception e)
			
			{
				e.printStackTrace();
			}
	
}
public static void Image_Recognition()
{
	
	try {
		
	// 1.- Taking a picture
	// Captures an image with a size of 480x480 pixels. And it is stored in the object Photo.
		
	 BufferedImage Photo = sb.takeStill(ImageSize.SQUARE_480x480);
	 // ->>> Photo an object of the BufferedImage used to take photos 
	 
    // 2.- Get image Dimensions.
	// It gets the width and height of the photo taken.
	
     int width = Photo.getWidth();
     int height = Photo.getHeight();

     
     // 3.- Calculation RGB sum
     // Declare variables to store the  sum of red,green and blue of all pixels in image.
     
     long Red = 0;
     long Green = 0;
     long Blue  = 0;

     // 4.- Iterates over pixels in the image
     
     int row = 0;
     
     while (row < height) 
     {
         for (int column = 0; column < width; column++)
        {
             // After looping all columns in the current row, it moves to the next row 

        	 
        	 
      // 5.- For each pixel, it extracts the RGB value using the RGB method
        	 
        int rgb = Photo.getRGB(row, column);


        
        // 6.- Extracts Red, green and blue  components and accumulates all RGB values 
        
         int rojo = (rgb >> 16) & 0xFF;
         int verde = (rgb >> 8) & 0xFF;
         int azul = rgb & 0xFF;



         // 7.- Accumulate the sum 
         // Here we add the red,green and blue components of each pixel 

         Red = Red + rojo;
         Green = Green + verde;
         Blue = Blue + azul;

        }
         
         //the counter row variable will increase until all the rows has been processed.
         row ++;
         // After iterating all columns in the current row, it moves to the next row 
         //by incrementing the row variable.
         
     }
     
     // Loop Termination When all rows are successfully processed.

     // 
     
     //Calculates the total number of pixels 
     
     int totalpixels = width * height;
     
     int averageRed = (int) (Red / totalpixels);
     
     int averageGreen = (int) (Green / totalpixels);
     
     int averageBlue = (int) (Blue / totalpixels);
     

     // Output the average colour
      
     
     // If the average red of a photo is more than green and blue then the red mode is called
     
     if (averageRed>averageGreen && averageRed>averageBlue)
     {
    	 redcount++;
    	 System.out.println(redcolour+"-> Traffic light (red) has been identified sucessfully."+whitecolour);
    	 System.out.print(redcolour+"-> Robot in red mode\n"+whitecolour);
    	 
    	 Red_mode();
    	
    	 

    	 }
     // If the average green of a photo is more than red and blue then the green mode is called

     else if (averageGreen>averageBlue && averageGreen>averageBlue)
     { 
    	 greencount++;
    	 System.out.println(greencolour+"-> Traffic light (green) has been identified succesfully."+whitecolour);
    	 System.out.print(greencolour+"-> Robot in green mode\n"+whitecolour);

    	 Green_mode();
    	

     }
     // If the average blue of a photo is more than red and green then the blue mode is called

     else if (averageBlue>averageRed && averageBlue>averageGreen)
     
     {
    	 bluecount++;
    	 System.out.println(cyancolour+"-> Traffic light (blue) has been identified succesfully."+whitecolour);
    	 System.out.print(cyancolour+"-> Robot in blue mode\n"+whitecolour);
    	 Blue_mode();
    

     }
     else 
     {
    	 System.out.print("-> Looking for traffic light\n");
     }
     
 }
	
catch (Exception e) 
 {
     e.printStackTrace();
 }
	
 }

public static void Red_mode()throws InterruptedException
{
	
	sb.fillUnderlights(RED);
    sb.move(44,50,850);
    sb.stopMove();
	Thread.sleep(500);
	sb.disableUnderlights();
	Ultrasound2();
	
    
	
}
//Red mode method contains the required functionalities in the specification

public static void Green_mode() throws InterruptedException
{
	sb.fillUnderlights(GREEN);
	sb.move(34,36,2000);
	sb.stopMove();
	Thread.sleep(500);

	Ultrasound();
	
}
//Green mode method contains the required functionalities in the specification


public static void Blue_mode() throws InterruptedException
{
	sb.stopMove();
	Thread.sleep(500);
	
	for (int i =0; i <7;i++)
	{
		sb.fillUnderlights(BLUE);
		Thread.sleep(350);
		sb.disableUnderlights();
		sb.stopMove();
		

	}

	
	sb.move(10, 70, 1050);
    sb.move(50, 62, 1000);
    Thread.sleep(500);
	sb.move(-50, -62, 1000); 
	sb.move(-10, -70, 1170);     
	
	// blink underlights
    Ultrasound2();

	


}
//Blue mode method contains the required functionalities in the specification


public static void Store_and_Calculation()
{
	System.out.println("-> Traffic Light Results:");
	System.out.println();
	
	
	int total= (greencount+redcount+bluecount);
	
	System.out.println("   -The number of times the SwiftBot encountered traffic lights: " + total);
	
	
	if (greencount>redcount && greencount>bluecount)
	{
		System.out.println("   -The most frequent traffic light colour encountered were: green");
	}
	
	else if (redcount>greencount && redcount>bluecount)
	{
		System.out.println("   -The most frequent traffic light colour encountered were: red");
		
	}
	
	else if(bluecount>redcount && bluecount>greencount)
	{
		System.out.println("   -The most frequent traffic light colour encountered were: blue");
	}
	
	else 
	{
		System.out.println("   -Red,blue and green traffic light has been encountered at the same frequency.");
	}
	
	
	
	
	if (greencount>redcount && greencount>bluecount)
	{
		System.out.println("   -The number of times the most frequent traffic light were detected: " + greencount );
	}
	
	else if  (redcount>greencount && redcount>bluecount)
	{
		System.out.println("   -The number of times the most frequent traffic light were detected: " + redcount);
	}
	
	else if (bluecount>redcount && bluecount>greencount)
	{
		System.out.println("   -The number of times the most frequent traffic light were detected: " + bluecount );
	}
	
	else 
	{
		System.out.println("   -Red,blue and green traffic light have been detected the same number of times. ");
	}
		System.out.println("   -The total duration of execution in minutes is: " + mins);
	
	System.out.println();
	System.out.println("   -SEE YOU SOON !");
	System.out.println();
	
 
	sb.stopMove();
	sb.disableUnderlights();	
    System.exit(5);
    //System will terminate but will show all the log of the execution
    





}
//The log of the execution will be shown here

public static void Text_file()
{
	
	// ]Specifies where the location will be stored.
	String file = "/home/pi/Pictures/LogUser.txt";
	
	//Objects 
	    try {
	    	//Writer,Stream are objects of filewriter and printstream classes to write data into a file.
	    	
	        // A 'File writer' is created to write to the file. This object can write text data directly to the file.
	        FileWriter Writer = new FileWriter(file);

	        // PrintStream object is created to write to the same file but with a different purpose
	        // Any text displayed using the System.out will be stored in the file assigned 
	        // This is achieved by creating a FileOutputStream which writes the bytes to the file.
	        
	        PrintStream Stream = new PrintStream(new FileOutputStream(file));
	        
	        System.setOut(Stream);

	        // Method is called which prints the results using a System.out.println();
	        //These prints will go to Log user text file.
	       
			Store_and_Calculation();
			Thread.sleep(1000);


	        // It closes the filewriter and pruntstream.To avoid memory leaks and other issues 
	        Writer.close();
	        Stream.close();
	                
	    } catch (Exception e) 

	    // If an exception occurs then it will print an error message. and the stacktrace of the exepction.
	    {
	        System.out.println("An error were detected.");
	        e.printStackTrace();

	    }
	    // It handles any errors that might ocurring during file writing or execution.
	    
	    System.exit(0);
	    
	    
	    
	
}
//The log of the execution will be stored in a text file

public static void StopProgram() 
{
	
	System.out.println();

	System.out.println(magentacolour+"-> Button X has been pressed.\n"+whitecolour);

	System.out.println("-> Do you want to be shown the log of the execution?\n");
	
	System.out.println("-> Press button 'Y' on SwiftBot to represent (yes) or press button 'X' AGAIN to represent (no):\n ");
	
	
	
       	 sb.enableButton(Button.Y, () -> {
       	//Activate button Y when button X is pressed 
       		 sb.stopMove();
       		 sb.disableUnderlights();
       		 
		 
		    System.out.println(magentacolour+"-> Button Y has been pressed.\n"+whitecolour);  
		   	Store_and_Calculation();
		   	//If user press button Y then will show the log of the execution to the user and terminate the program
		   
		    sb.disableButton(Button.Y);
	        	
	        });

	        
	        sb.enableButton(Button.X, () -> {

	   		 sb.stopMove();
       		 sb.disableUnderlights();
	        
	        System.out.println(magentacolour+"-> Button X has been pressed AGAIN.\n"+whitecolour);
		    System.out.println("-Results will be stored in Swiftbot (/home/pi/Pictures/LogUser.txt).\n");
			System.out.println("   -SEE YOU SOON !");
		    
	        Text_file();	
      		 //Stops program and store the log of the execution in the following path/location on swiftbot

	        sb.disableButton (Button.X);
	        
	        });
	        

	
}


//5.- Red count green count blue count,  are static variables of type int, They are used to count the occurrence of blue, green red traffic lights respectively, they are initialized to 0 and increments 
//as the program detects traffic lights


static int redcount = 0 ;
static int bluecount = 0 ; 
static int greencount = 0 ;
// 10.- Green count is a counter variable that increase by one each time the robot looks for green traffic lights.

// start counting from 0;
// store start time



//6.- These are static variables used to store timing information  Represent time values in miliseconds and min utes , these variables are used 
//to calculate the duration of program. 
static long starttime;
static long endtime;
static long mills;
static double mins;




private static String cyancolour="\u001B[36m";

static String whitecolour ="\u001B[37m";

static String yellowcolour="\u001B[33m";

static String greencolour="\u001B[32m";

static String redcolour="\u001B[31m";

static String magentacolour="\u001B[35m";




private static  boolean program = false;


static  int[] RED = new int[] { 255, 0, 0 };
static  int[] BLUE = new int[] { 0, 255, 0 };
static  int[] GREEN = new int[] { 0, 0, 255 };
static  int[] YELLOW = new int[] { 255, 0, 255 };




}