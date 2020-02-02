
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Project2Digits { 

	/**
	 * @param args
	 * @throws FileNotFoundException
	 *             //
	 */
	public static void main(String[] args) throws FileNotFoundException { // implements
																			// naive
																			// bayes
		// read text files
		// Read image from test file, output predicted label
		// Training, validation, and test data
		// Need to implement naive bayes and perceptron
		// needs to read both faces and numbers
		// what number it is
		// face or not face

		
		
		System.out.println("Hello, what percentage would you like to train with?");
		
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    int percentage = myObj.nextInt();  // Read user input
	    System.out.println("Train with: " + percentage+"%");
	    
	    int n = (5000*percentage/100);
	    
	    int selectionCount = n;
	    
	    System.out.println("Training cases = "+n);
	    
	    int [] selection = new int [n];
	    for (int h = 0; h<n; h++) {
	    	selection [h] = -1;
	    }
	    while (selectionCount>0) {
	    	int startpointX = (int) (Math.random() * 5000);
	    	boolean used = false;
	    	for (int i = 0; i<n; i++) {
	    		if (selection[i]==startpointX) {used = true; break;} // pick a new number
	    	}
	    	if (used) continue;
	    	for (int i = 0; i<n; i++) {
	    		if (selection[i]==-1) {selection[i]=startpointX; selectionCount--; break;} // pick a new number
	    	} 
	    }

		// store all digit labels
		int digitdatatrainlabel[] = new int[5000]; // 451 training images
		int digitdatavalidatelabel[] = new int[1000]; // 301 validation images
		int digitdatatestlabel[] = new int[1000]; // 150 test images

		int i = 0;

		File file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\digitdatatraininglabels");
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			digitdatatrainlabel[i] = sc.nextInt();
			i++;
		} // just prints out the labels for faces

//		 for (i = 0; i < 5000; i++) {
//		 System.out.println(digitdatatrainlabel[i]);
//		 }

		i = 0;
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\digitdatavalidationlabels");
		// C:\\Users\\Tim\\Documents\\data.zip\\facedata\\facedatatrainlabels
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			digitdatavalidatelabel[i] = sc.nextInt();
			i++;
		}

		i = 0;
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\digitdatatestlabels");
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			digitdatatestlabel[i] = sc.nextInt();
			i++;
		}
		
//		for (i = 0; i < 1000; i++) {
//			 System.out.println(digitdatatestlabel[i]);
//			 }

		// store the images

		char[][] digitdatatrainimages = new char[28][140000];
		String a;
		int j = 0;

		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\digitdatatrainingimages");
		sc = new Scanner(file);
		for (j = 0; sc.hasNextLine() && j < 140000; j++) {
			a = sc.nextLine();
			for (i = 0; i < 28; i++) {
				digitdatatrainimages[i][j] = a.charAt(i);
			}
		}
		
//		for (j = 0; j < 140000; j++) {
//			for (i = 0; i < 28; i++) {
//				System.out.print(digitdatatrainimages[i][j]);
//			}
//			System.out.println();
//		}

		char[][] digitdatavalidateimages = new char[28][28000];
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\digitdatavalidationimages");
		sc = new Scanner(file);
		for (j = 0; sc.hasNextLine() && j<28000; j++) {
			a = sc.nextLine();
			for (i = 0; i < 28; i++) {
				digitdatavalidateimages[i][j] = a.charAt(i);
			}
		}

		char[][] digitdatatestimages = new char[28][28000];
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\digitdatatestimages");
		sc = new Scanner(file);
		for (j = 0; sc.hasNextLine() && j<28000; j++) {
			a = sc.nextLine();
			for (i = 0; i < 28; i++) {
				digitdatatestimages[i][j] = a.charAt(i);
			}
		}
		// copies facetraining data into an array
		// need to calculate blackspace usage per image
			// extract other features to use for predictions as well

		// int[][] facedatatrain = new int [61][140000];

		// read labels into array?
		
		int py0 = 0;
		int py1 = 0;
		int py2 = 0;
		int py3 = 0;
		int py4 = 0;
		int py5 = 0;
		int py6 = 0;
		int py7 = 0;
		int py8 = 0;
		int py9 = 0;
		
		py0 = calculatePY(0, digitdatatrainlabel, selection, n);
		py1 = calculatePY(1, digitdatatrainlabel, selection, n);
		py2 = calculatePY(2, digitdatatrainlabel, selection, n);
		py3 = calculatePY(3, digitdatatrainlabel, selection, n);
		py4 = calculatePY(4, digitdatatrainlabel, selection, n);
		py5 = calculatePY(5, digitdatatrainlabel, selection, n);
		py6 = calculatePY(6, digitdatatrainlabel, selection, n);
		py7 = calculatePY(7, digitdatatrainlabel, selection, n);
		py8 = calculatePY(8, digitdatatrainlabel, selection, n);
		py9 = calculatePY(9, digitdatatrainlabel, selection, n);
		
		int [] zeropixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			zeropixelusage[b] = 0;
		}
		int [] onepixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			zeropixelusage[b] = 0;
		}
		int [] twopixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			twopixelusage[b] = 0;
		}
		int [] threepixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			threepixelusage[b] = 0;
		}
		int [] fourpixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			fourpixelusage[b] = 0;
		}
		int [] fivepixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			fivepixelusage[b] = 0;
		}
		int [] sixpixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			sixpixelusage[b] = 0;
		}
		int [] sevenpixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			sevenpixelusage[b] = 0;
		}
		int [] eightpixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			eightpixelusage[b] = 0;
		}
		int [] ninepixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			ninepixelusage[b] = 0;
		}
		int [] imagepixelusage = new int [784]; // initialize all to 0
		for (int b = 0; b<784; b++) {
			ninepixelusage[b] = 0;
		}
		
		//long startTime = System.nanoTime();
		
		train(n, selection, digitdatatrainlabel, digitdatatrainimages, zeropixelusage, onepixelusage, twopixelusage, 
				threepixelusage, fourpixelusage, fivepixelusage, sixpixelusage, sevenpixelusage, 
				eightpixelusage, ninepixelusage, imagepixelusage); // 500 = 10%, randomly
														// picked
		
		//long endTime = System.nanoTime();
		
		//long timeElapsed = endTime - startTime;
		
		//long milliseconds = timeElapsed/1000000;
		
		//System.out.println("milliseconds "+milliseconds);
		

		//validate(digitdatavalidatelabel, digitdatavalidateimages); // see how good
																	// it
																	// performs,
																	// make
		// adjustments
		// 301 face validation labels
		// 21070 in face val images
		
//		for (int b = 0; b<784; b++) { // bunch of zeros and random 217's
//			System.out.println(imagepixelusage[b]);
//		}

		test(n, selection ,digitdatatestlabel, digitdatatestimages, zeropixelusage, onepixelusage, twopixelusage, 
				threepixelusage, fourpixelusage, fivepixelusage, sixpixelusage, sevenpixelusage, 
				eightpixelusage, ninepixelusage,  imagepixelusage, py0, py1, py2, py3, py4, 
				py5, py6, py7, py8, py9); // calculate accuracy of
														// algorithms, don't get
		// to adjust
		// run the naive bayes on the new faces, produce an output, check
		// against real output
		// 150 in face data test labels
		// 10500 in face data test images
	}

	public static void train(int n, int [] selection, int[] digitdatatrainlabel, char[][] digitdatatrainimages, 
			int[] zeropixelusage, int[] onepixelusage, int[] twopixelusage, 
			int[] threepixelusage, int[] fourpixelusage, int[] fivepixelusage, 
			int[] sixpixelusage, int[] sevenpixelusage, int[] eightpixelusage, 
			int[] ninepixelusage, int[] imagepixelusage) {
		
		//int n = 5000; // different with less input

		// }
		// completely empty line: move to next image
		// won't have to do this if lines correlate perfectly to images
		// doesn't do this until first line encountered
		
		for (int i1 = 0; i1<5000; i1++ ){ // for each training example, change to n
			
			// if i1 is in selection
			boolean found = false;
			for (int z = 0;z<n; z++ ) {if (selection[z]==i1) {found = true;}}
			if (found) {
			
			for (int j = 0; j < 28; j++) { // for each line in that example
				for (int k = 0; k < 28; k++) { // for each char in that line
					if (digitdatatrainimages[k][j+i1*28] != ' ') { // for that char
						//trainpixels[i1][70*j + k] = 1;
						if (digitdatatrainlabel[i1]==0) { // if its a face, increment that
							zeropixelusage[28*j+k]++;
						}
						else if (digitdatatrainlabel[i1]==1) {
							onepixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==2) {
							twopixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==3) {
							threepixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==4) {
							fourpixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==5) {
							fivepixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==6) {
							sixpixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==7) {
							sevenpixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==8) {
							eightpixelusage[28*j+k]++; // else, increment other
						}
						else if (digitdatatrainlabel[i1]==9) {
							ninepixelusage[28*j+k]++; // else, increment other
						}
						imagepixelusage[28*j+k]++; // pixel used either way
					}	
//					} else {
//						trainpixels[i1][70*j + k] = 0;
//					}
				}
			}
		} // end if
		} // end for

		

		// use these to calculate the naive bayes
	}

	public static int calculatePY(int input, int[] digitdatatrainlabel, int[]selection, int n) {
		// read all training data
		int x = 0;// x = number of times yi = true
		int PY = 0; // py
		// n = number of examples
		for (int i = 0; i < 5000; i++) {
			boolean found = false;
			for (int z = 0;z<n; z++ ) {if (selection[z]==i) {found = true;}}
			if (found) {
			if (digitdatatrainlabel[i] == input) {
				x++;
			}
		}
		}
		PY = x;// /n; // p(y = true) = p y true / n
		//System.out.println("There are " + PY +" "+input+" digits in the training data set, out of 5000");
		// 217 faces in test data set
		return PY;
	}

	
	
	public static int calculateDigit (int a, int n, char [][] digitdatatestimages, int[] zeropixelusage,
			int[] onepixelusage, int[] twopixelusage, 
			int[] threepixelusage, int[] fourpixelusage, int[] fivepixelusage, 
			int[] sixpixelusage, int[] sevenpixelusage, int[] eightpixelusage, 
			int[] ninepixelusage, int [] imagepixelusage, int py0, int py1, int py2, int py3, int py4,
			int py5, int py6, int py7, int py8, int py9) { // when run, returns whether an image is a face or not
		
		int pixelstats [] = new int [784]; // for specific number image in question
		
		for (int b = 0; b<784; b++) { // records usage for current image
			pixelstats[b] = 0;
		}
		
		double zerochance = 1.0;
		double onechance = 1.0; // gets reset each time
		double twochance = 1.0;
		double threechance = 1.0;
		double fourchance = 1.0;
		double fivechance = 1.0;
		double sixchance = 1.0;
		double sevenchance = 1.0;
		double eightchance = 1.0;
		double ninechance = 1.0;
		
//		System.out.println(facechance);
//		System.out.println(notfacechance);
		
		for (int j = 0; j < 28; j++) { // Extract this particular image
			for (int k = 0; k < 28; k++) {
				if (digitdatatestimages[k][j+a*28] != ' ') {
					pixelstats[28*j + k] = 1;
				//	System.out.println("pixel "+(60*j+k)+" is used");
				} else {
					pixelstats[28*j + k] = 0;
				//	System.out.println("pixel "+(60*j+k)+" is not used");
				}
			}
		}
		
//		for (int b = 0; b<784; b++) { // records usage for current image
//			System.out.print(pixelstats[b]+"+ ");
//		}
		
		//pixel stats seems to work
		
		// use stats from training as well as extraced from test image to produce either face or not
		
		// pytrue = 217
		// pyfalse = 234;
		
		//facechance *= all features (4200 multiplications) *= pytrue
		
		int failstatezero = 0;
		int failstateone = 0;
		int failstatetwo = 0;
		int failstatethree = 0;
		int failstatefour = 0;
		int failstatefive = 0;
		int failstatesix = 0;
		int failstateseven = 0;
		int failstateeight = 0;
		int failstatenine = 0;
		
		for (int i = 0; i<784; i++) { //4200
			if (zerochance==0.0) {failstatezero = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				zerochance=zerochance*((double)zeropixelusage[i]/py0); // need when pixels were used, for faces
				//System.out.println("facechance = "+facechance);
//				System.out.println("1 if uses pixel = "+pixelstats[i]);
//				System.out.println("facepixelusage = "+facepixelusage[i]);
//				System.out.println("number of faces = "+pytrue);
			} else {
				// what we do when the pixel is not used in the image
				zerochance=zerochance*((double)(py0-zeropixelusage[i])/py0);
				//System.out.println("facechance = "+facechance);
//				System.out.println(pixelstats[i]);
//				System.out.println(451-facepixelusage[i]);
//				System.out.println(pytrue);
			}
			
		}
		zerochance*=py0;
		
		//System.out.println("failstatezero= "+failstatezero);
				
		//notfacechance *= all features (4200) *= pyfalse;
		
		for (int i = 0; i<784; i++) {
			if (onechance==0.0) {failstateone = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				onechance=onechance*((double)onepixelusage[i]/py1); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				onechance=onechance*((double)(py1-onepixelusage[i])/py1);
			}
			
		}
		onechance*=py1;
		
		//System.out.println("failstateone= "+failstateone);
		//System.out.println("onechance= "+onechance);
		
		for (int i = 0; i<784; i++) {
			if (twochance==0.0) {failstatetwo = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				twochance=twochance*((double)twopixelusage[i]/py2); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				twochance=twochance*((double)(py2-twopixelusage[i])/py2);
			}
			
		}
		twochance*=py2;
		
		//System.out.println("failstatetwo= "+failstatetwo);
		//System.out.println("twochance= "+twochance);
		
		for (int i = 0; i<784; i++) {
			if (threechance==0.0) {failstatethree = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				threechance=threechance*((double)threepixelusage[i]/py3); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				threechance=threechance*((double)(py3-threepixelusage[i])/py3);
			}
			
		}
		threechance*=py3;
		
		//System.out.println("failstatethree= "+failstatethree);
		//System.out.println("threechance= "+threechance);
		
		for (int i = 0; i<784; i++) {
			if (fourchance==0.0) {failstatefour = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				fourchance=fourchance*((double)fourpixelusage[i]/py4); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				fourchance=fourchance*((double)(py4-fourpixelusage[i])/py4);
			}
			
		}
		fourchance*=py4;
		
		//System.out.println("failstatefour= "+failstatefour);
		//System.out.println("fourchance= "+fourchance);
		
		for (int i = 0; i<784; i++) {
			if (fivechance==0.0) {failstatefive = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				fivechance=fivechance*((double)fivepixelusage[i]/py5); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				fivechance=fivechance*((double)(py5-fivepixelusage[i])/py5);
			}
			
		}
		//System.out.println("fivechance= "+fivechance);
		fivechance*=py5;
		
		//System.out.println("failstatefive= "+failstatefive);
		//System.out.println("fivechance= "+fivechance);
		
		for (int i = 0; i<784; i++) {
			if (sixchance==0.0) {failstatesix = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				sixchance=sixchance*((double)sixpixelusage[i]/py6); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				sixchance=sixchance*((double)(py6-sixpixelusage[i])/py6);
			}
			
		}
		sixchance*=py6;
		
		//System.out.println("failstatesix= "+failstatesix);
		//System.out.println("sixchance= "+sixchance);
		
		for (int i = 0; i<784; i++) {
			if (sevenchance==0.0) {failstateseven = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				sevenchance=sevenchance*((double)sevenpixelusage[i]/py7); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				sevenchance=sevenchance*((double)(py7-sevenpixelusage[i])/py7);
			}
			
		}
		//System.out.println("sevenchance= "+sevenchance);
		sevenchance*=py7;
		
		//System.out.println("failstateseven= "+failstateseven);
		//System.out.println("sevenchance= "+sevenchance);
		
		for (int i = 0; i<784; i++) {
			if (eightchance==0.0) {failstateeight = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				eightchance=eightchance*((double)eightpixelusage[i]/py8); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				eightchance=eightchance*((double)(py8-eightpixelusage[i])/py8);
			}
			
		}
		eightchance*=py8;
		
		//System.out.println("failstateeight= "+failstateeight);
		//System.out.println("eightchance= "+eightchance);
		
		for (int i = 0; i<784; i++) {
			if (ninechance==0.0) {failstatenine = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				ninechance=ninechance*((double)ninepixelusage[i]/py9); // need when pixels were used, for faces
				
			} else { // what we do when the pixel is not used in the image
				ninechance=ninechance*((double)(py9-ninepixelusage[i])/py9);
			}
			
		}
		//System.out.println("ninechance= "+ninechance);
		ninechance*=py9;
		
		//System.out.println("failstatenine= "+failstatenine);
		//System.out.println("ninechance= "+ninechance);
		
		double px = 1.0;
		
		for (int i = 0; i<784; i++) { //4200
			if (pixelstats[i]==1) {
				px*=((double)(imagepixelusage[i])/5000); // need those added together usage
				//System.out.println((double)(imagepixelusage[i])/451);
			} else {
				px*=((double)(5000-imagepixelusage[i])/5000);
				//System.out.println((double)(451-imagepixelusage[i])/451);
			}
			//System.out.println("px= "+px);
		}
		
		// either use highest failstates or highest chance value
		
		// eliminate the ones that fail
		
		double chance = 0.0; int number = 0;
		if (zerochance>chance) {chance = zerochance; number = 0;}
		if (onechance>chance) {chance = onechance; number = 1;}
		if (twochance>chance) {chance = twochance; number = 2;}
		if (threechance>chance) {chance = threechance; number = 3;}
		if (fourchance>chance) {chance = fourchance; number = 4;}
		if (fivechance>chance) {chance = fivechance; number = 5;}
		if (sixchance>chance) {chance = sixchance; number = 6;}
		if (sevenchance>chance) {chance = sevenchance; number = 7;}
		if (eightchance>chance) {chance = eightchance; number = 8;}
		if (ninechance>chance) {chance = ninechance; number = 9;}
		// 
		
		return number;
	}

	public static void validate(int[] facedatavalidatelabel, char[][] facedatavalidateimages) {

		// see how good algorithm is, make adjustments

		// int n = 301;
		
		// 156 of the 301 are not faces
		
		int x = 0; // number of cases gotten correct
		int n = 301;
		// int a = 0;
		int[] facedatavalidatetoutput = new int[301];
		for (int a = 0; a < 301; a++) {
			facedatavalidatetoutput[a] = 0; // initialize it to default to being a
										// face
		}
		for (int i = 0; i < 301; i++) {
			if (facedatavalidatetoutput[i] == facedatavalidatelabel[i])
				x++;
		} // facedatatestlabel[i] == output
			// run the algorithm, see if output label is same as actual label
		System.out.println("Validation Matches: " + x + "/301"); // number of examples that
														// passed the test
		double percentcorrect = ((double) x / n);
		System.out.println("You got " + percentcorrect + "% correct!");
		
		

	}

	public static void test(int n, int [] selection, int[] digitdatatestlabel, char[][] digitdatatestimages, 
			int[] zeropixelusage, int[] onepixelusage, int[] twopixelusage, 
			int[] threepixelusage, int[] fourpixelusage, int[] fivepixelusage, 
			int[] sixpixelusage, int[] sevenpixelusage, int[] eightpixelusage, int[] ninepixelusage, 
			int [] imagepixelusage, int py0, int py1, int py2, int py3, int py4, int py5, int py6,
			int py7, int py8, int py9) {

		// 77 are not faces, 73 are

		int x = 0; // number of cases gotten correct
		//int n = 1000;
		// int a = 0;
		int[] digitdatatestoutput = new int[1000];
		for (int a = 0; a < 1000; a++) {
			digitdatatestoutput[a] = 0; // initialize it to default to being 0
		}
		for (int a = 0; a < 1000; a++) {
			//System.out.println();
			digitdatatestoutput[a] = calculateDigit( a,n, digitdatatestimages, zeropixelusage,
			 onepixelusage,  twopixelusage, 
			 threepixelusage,  fourpixelusage,  fivepixelusage, 
			 sixpixelusage,  sevenpixelusage,  eightpixelusage, 
			 ninepixelusage, imagepixelusage, py0, py1, py2, py3, py4,
			 py5, py6, py7, py8, py9); // initialize it to default to being 0
		}
		for (int i = 0; i < 1000; i++) {
			if (digitdatatestoutput[i] == digitdatatestlabel[i])
				x++;
		} // facedatatestlabel[i] == output
			// run the algorithm, see if output label is same as actual label
		System.out.println("Test Matches: " + x + "/1000"); // number of examples that
														// passed the test
		double percentcorrect = ((double) x / 1000);
		System.out.println("You got " + (percentcorrect*100) + "% correct!");

	}
}