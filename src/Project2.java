
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Project2 {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 *             //
	 */
	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("What percentage would you like to train with?");
		
		Scanner myObj = new Scanner(System.in);
	    int percentage = myObj.nextInt();  // Read user input
	    System.out.println("Train with: " + percentage+"%");
	    
	    int n = (451*percentage/100);
	    
	    int selectionCount = n;
	    
	    System.out.println("Training cases = "+n);
	    
	    int [] selection = new int [n];
	    for (int h = 0; h<n; h++) {
	    	selection [h] = -1;
	    }
	    while (selectionCount>0) {
	    	int startpointX = (int) (Math.random() * 451);
	    	boolean used = false;
	    	for (int i = 0; i<n; i++) {
	    		if (selection[i]==startpointX) {used = true; break;} // pick a new number
	    	}
	    	if (used) continue;
	    	for (int i = 0; i<n; i++) {
	    		if (selection[i]==-1) {selection[i]=startpointX; selectionCount--; break;} // pick a new number
	    	} 
	    }

		// store all face labels
		int facedatatrainlabel[] = new int[451]; // 451 training images
		int facedatavalidatelabel[] = new int[301]; // 301 validation images
		int facedatatestlabel[] = new int[150]; // 150 test images

		int i = 0;

		File file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\facedatatrainlabels.txt");
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			facedatatrainlabel[i] = sc.nextInt();
			i++;
		}

		// for (i = 0; i < 451; i++) {
		// System.out.println(facedatatrainlabel[i]);
		// }

		i = 0;
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\facedatavalidationlabels.txt");
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			facedatavalidatelabel[i] = sc.nextInt();
			i++;
		}

		i = 0;
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\facedatatestlabels.txt");
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			facedatatestlabel[i] = sc.nextInt();
			i++;
		}
	

		char[][] facedatatrainimages = new char[60][31570];
		String a;
		int j = 0;

		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\facedatatrain");
		sc = new Scanner(file);
		for (j = 0; sc.hasNextLine() && j < 31570; j++) {
			a = sc.nextLine();
			for (i = 0; i < 60; i++) {
				facedatatrainimages[i][j] = a.charAt(i);
			}
		}

		char[][] facedatavalidateimages = new char[60][21070];
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\facedatavalidation");
		sc = new Scanner(file);
		for (j = 0; sc.hasNextLine() && j<21070; j++) {
			a = sc.nextLine();
			for (i = 0; i < 60; i++) {
				facedatavalidateimages[i][j] = a.charAt(i);
			}
		}

		char[][] facedatatestimages = new char[60][10500];
		file = new File("C:\\Users\\Tim\\workspace\\Project2\\src\\facedatatest");
		sc = new Scanner(file);
		for (j = 0; sc.hasNextLine() && j<10500; j++) {
			a = sc.nextLine();
			for (i = 0; i < 60; i++) {
				facedatatestimages[i][j] = a.charAt(i);
			}
		}

		// sc.close();
		
		double pytrue = calculatePY(facedatatrainlabel, selection, n);
		double pyfalse = n-pytrue; // number of times not face

		
		int [] facepixelusage = new int [4200]; // initialize all to 0
		for (int b = 0; b<4200; b++) {
			facepixelusage[b] = 0;
		}
		int [] notfacepixelusage = new int [4200];
		for (int b = 0; b<4200; b++) {
			notfacepixelusage[b] = 0;
		}
		int [] imagepixelusage = new int [4200];
		for (int b = 0; b<4200; b++) {
			imagepixelusage[b] = 0;
		}
		//long startTime = System.nanoTime();

		train(n, selection, facedatatrainlabel, facedatatrainimages, facepixelusage, notfacepixelusage, imagepixelusage);
		
		//long endTime = System.nanoTime();
		
		//long timeElapsed = endTime - startTime;
		
		//long milliseconds = timeElapsed/1000000;
		
		//System.out.println("milliseconds "+milliseconds);
		
		//validate(facedatavalidatelabel, facedatavalidateimages, facepixelusage, notfacepixelusage, imagepixelusage, pytrue, pyfalse);

		test(facedatatestlabel, facedatatestimages, facepixelusage, notfacepixelusage, imagepixelusage, pytrue, pyfalse); // calculate // numfaces, numimages
																		
	}

	public static void train(int n, int [] selection, int[] facedatatrainlabel, char[][] facedatatrainimages, int [] facepixelusage, int [] notfacepixelusage, int [] imagepixelusage) {

		int k = 0;
		
		for (int i1 = 0; i1<451; i1++ ){
			// if it is present in selection array
			boolean found = false;
			for (int z = 0;z<n; z++ ) {if (selection[z]==i1) {found = true;}}
			if (found) {
			for (int j = 0; j < 70; j++) { // for each line in that example
				for (k = 0; k < 60; k++) { // for each char in that line
					if (facedatatrainimages[k][j+i1*70] != ' ') { // for that char
						if (facedatatrainlabel[i1]==1) { // if its a face, increment that
							facepixelusage[60*j+k]++;
						}
						else {
							notfacepixelusage[60*j+k]++; // else, increment other
						}
						imagepixelusage[60*j+k]++; // overall pixel usage
					}	
				}
			}
		}
		}	
	}

	public static int calculatePY(int[] facedatatrainlabel, int [] selection, int n) {
		// read all training data 
		int x = 0;// x = number of times yi = true
		int PY = 0; // py
		// n = number of examples
		for (int i = 0; i < 451; i++) {
			boolean found = false;
			for (int z = 0;z<n; z++ ) {if (selection[z]==i) {found = true;}}
			if (found) {
				if (facedatatrainlabel[i] == 1) {
					x++;
				}
			}
		}
		PY = x;// /n; // p(y = true) = p y true / n
		//System.out.println("There are " + PY + " faces in the training data set, out of 451");
		// 217 faces in test data set
		return PY;
	}

	public static int calculateFace(int a, char[][] facedatatestimages, int [] facepixelusage, int notfacepixelusage[], int [] imagepixelusage, double pytrue, double pyfalse) {

		// naively assume independence of variables, makes it easier
		
		int pixelstats [] = new int [4200]; // stores pixel data for specific image in question
		
		for (int b = 0; b<4200; b++) {
			pixelstats[b] = 0;
		}
		
		double facechance = 1.0;
		double notfacechance = 1.0;
		
		for (int j = 0; j < 70; j++) {
			for (int k = 0; k < 60; k++) {
				if (facedatatestimages[k][j+a*70] != ' ') {
					pixelstats[60*j + k] = 1;
				//	System.out.println("pixel "+(60*j+k)+" is used");
				} else {
					pixelstats[60*j + k] = 0;
				//	System.out.println("pixel "+(60*j+k)+" is not used");
				}
			}
		}
		
		// use stats from training as well as extraced from test image to produce either face or not
		
		// pytrue = 217
		// pyfalse = 234;
		
		//facechance *= all features (4200 multiplications) *= pytrue
		
		int failstatefc = 0;
		int failstatenfc = 0;
		
		for (int i = 0; i<4200; i++) { //4200
			if (facechance==0.0) {failstatefc = i; break;}
			if (pixelstats[i]==1) {// what we do when the pixel is used
				facechance=facechance*((double)facepixelusage[i]/pytrue);
			} else { // what we do when the pixel is not used in the image
				facechance=facechance*((double)(pytrue-facepixelusage[i])/pytrue);
			}
		}
		facechance*=pytrue/451;
				
		//notfacechance *= all features (4200) *= pyfalse;
		for (int i = 0; i<4200; i++) {
			if (notfacechance==0.0) {failstatenfc = i; break;}
			if (pixelstats[i]==1) {
				notfacechance=notfacechance*((double)notfacepixelusage[i]/pyfalse); // need when pixels were used, for not faces
			} else {
				notfacechance=notfacechance*((double)(pyfalse-notfacepixelusage[i])/pyfalse);
			}
		}
		notfacechance*=pyfalse/451;
		
		double px = 1.0;
		for (int i = 0; i<4200; i++) { //4200
			if (pixelstats[i]==1) {
				px*=((double)(imagepixelusage[i])/451); // need those added together usage
				//System.out.println((double)(imagepixelusage[i])/451);
			} else {
				px*=((double)(451-imagepixelusage[i])/451);
				//System.out.println((double)(451-imagepixelusage[i])/451);
			}
		}
		
		// calculate px
		
//		facechance/=px; //normalize
//		notfacechance/=px;
		
//		System.out.println(failstatefc);
//		System.out.println(failstatenfc);
//		
//		System.out.println("facechance= "+facechance);
//		System.out.println("notfacechance= "+notfacechance);
//		System.out.println("px= "+px);
		
		if ((failstatefc ==0) && (failstatenfc == 0)) { //if neither fails, return one with higher chance
			if (facechance>notfacechance) {return 1;}
			return 0;
		}
		if ((failstatefc == 0) || (failstatenfc == 0)) { //if one fails, return one that does not
			if (failstatefc == 0) return 1;
			return 0;
		}
		// if both fail
		if (failstatefc>failstatenfc) {return 1;}

		return 0;
	}

	public static void validate(int[] facedatavalidatelabel, char[][] facedatavalidateimages, int [] facepixelusage, int [] notfacepixelusage, int [] imagepixelusage, double pytrue, double pyfalse) {

		int x = 0; // number of cases gotten correct
		int n = 301;
		// int a = 0;
		int[] facedatavalidateoutput = new int[301];
		for (int a = 0; a < 301; a++) {
			facedatavalidateoutput[a] = 0; // initialize it to default to being
											// a not face
		}
		for (int a = 0; a < 301; a++) {
			facedatavalidateoutput[a] = calculateFace(a, facedatavalidateimages, facepixelusage, notfacepixelusage, imagepixelusage, pytrue, pyfalse); // use naive bayes, either face or not																			
		}
		for (int i = 0; i < 301; i++) {
			if (facedatavalidateoutput[i] == facedatavalidatelabel[i])
				x++;
		} // facedatatestlabel[i] == output
			// run the algorithm, see if output label is same as actual label
		//System.out.println("Validation Matches: " + x + "/301"); // number of
																	// examples
																	// that
		// passed the test
		double percentcorrect = ((double) x / n);
		//System.out.println("You got " + percentcorrect + "% correct!");

	}

	public static void test(int[] facedatatestlabel, char[][] facedatatestimages, int [] facepixelusage, int [] notfacepixelusage, int [] imagepixelusage, double pytrue, double pyfalse) {

		int x = 0; // number of cases gotten correct
		int n = 150;
		// int a = 0;
		int[] facedatatestoutput = new int[150];
		for (int a = 0; a < 150; a++) { // start as not face
			facedatatestoutput[a] = 0; 
		}
		for (int a = 0; a < 150; a++) { //switch back to 150
			facedatatestoutput[a] = calculateFace(a, facedatatestimages, facepixelusage, notfacepixelusage, imagepixelusage, pytrue, pyfalse); // use naive bayes, either face or not																			
		}
		for (int i = 0; i < 150; i++) {
			if (facedatatestoutput[i] == facedatatestlabel[i])
				x++;
			//System.out.println(facedatatestoutput[i]+" vs "+facedatatestlabel[i]);
		}
		
		System.out.println("Test Matches: " + x + "/150"); // number of examples
															// that
															// passed the test
		double percentcorrect = ((double) x / n);
		System.out.println("You got " + percentcorrect*100 + "% correct!");

	}
}