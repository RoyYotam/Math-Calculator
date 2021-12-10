/*
	Author - Roy Yotam
	Version - 1.72
	Version Date - 03/12/2021

	TODO -
		(*) abs (||), return the absulote result (|-1| = 1).
		(*) round (~), return the round result (1.2 ~ = 1).
		(*) Maybe a little bit graphics.

	Future versions minor bugs - 
		(*) update the help menu to a one relative to the new version.
		(*) upgrade calculator to be able to calculate without blackspaces.
 */

/*
	What's new?
		(*) bug fixed for XcY function,
				you may use now memory variable only if they are alone in the line (F.E '3 + a + 5'),
				don't attach them togther.

		(*) watch out of memory variable 'c', you mat use it as 'c' / 'C',
				if you will write it together with number it will think you may want to use XcY.
 */

import java.util.Scanner;

public class calc {

	public static int countNotice = 0;
	public static double a = 0.0, b = 0.0, c = 0.0; // Memory variables
	public static double result = 0.0; // The last result.


	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
  
        String input;
        System.out.println("Please insert wanted formula");
        input = reader.nextLine();
        // throws AlsCustomException
        //// throw our custom exception
      	// throw new AlsCustomException("Anything but zero ...");

        while (!input.equals("")) {
        	if (input.equals("?")) {
        		help();

        	} else if (input.indexOf("?") != -1) {
        		help(input);

        	} else if (input.equals("m") || input.equals("M")) {
        		System.out.println("Insert memory variable");
        		input = reader.nextLine();

        		memoryUpdate(input, result);

	        } else {
		        try {

		        	//input = updateFromMemory(input, result);
		        	//// Old version, may be used in future one.

				  		result = calcFormula(input); // new result
				  		System.out.println("= " + result);
				  		

		        	if (countNotice / 2 >= 1) {
		        		countNotice = 0;
		        		throw new AlsCustomException("\nNote: you may use XcY wrong, it return 0.");
		        	}
				}
				catch(Exception e) {
				  System.err.println("something may be wrong,");
				  // print the stack trace
				  e.printStackTrace();
				}
			}


	        // new input
	        System.out.println("Please insert wanted formula");
    	    input = reader.nextLine();
        }
	}

	public static double calcFormula(String input) throws AlsCustomException {
		while (input.indexOf("(") != -1) {
			if (input.indexOf(")") == -1) {
				throw new AlsCustomException("UNFINISHED FORMULA, MORE '(' THAN ')'.");
			}

			int indexStart = input.indexOf("(");
			int indexEnd = input.lastIndexOf(")");

			String part1 = input.substring(0, indexStart);
			String part2 = input.substring(indexStart + 1, indexEnd);
			String part3 = input.substring(indexEnd + 1);

			String newPart2 = "" + calcFormula(part2); // calculated part 2.

			input = String.join("", part1, newPart2, part3);
			//debug(input);

		}
		if (input.indexOf(")") != -1) {
			throw new AlsCustomException("UNFINISHED FORMULA, MORE ')' THAN '('.");
		}

		// calc the regular formula without '()'.

		String[] mathFormula = input.split(" ");
		//debug(mathFormula);
				  	
		
		double[] sums = new double[(mathFormula.length / 2) + 1];

  		calcSubFormula(input, sums);

 		return resultCalc(sums, mathFormula);
	}

	// full menu
	public static void help() {
		String[] menu = {
			"	Hello there, and thanks for using roy's discrete math calculator.",
			"	This menu will describe the functions the calc has and how to use it, so read it carefully.",
			"",
			"	First of all, the calc is using whitespaces for understanding the formula, and please watch out,",
			"	any wrong line of the protocol will cause an exception or a note.",
			"",
			"	The calc has 4 base known maths function, + - / *",
			"	each one do the math of the variable next right and left (with whitespace), for example:",
			"	the formula: '1 + 3 - 2 + 4' will print 6,",
			"	the formula: '1+2 -3' will throw exception for wrong using of protocol at '1+2'.",
			"",
			"	Importent notice! to avoid hard work for code, if you want to multiple or devide, do it inside ().",
			"	Full format example: '((3 * 2) * 2) + 2' will print 14.",
			"",
			"	Few last things, we also has more functions:",
			"	factorial(!), power(** or ^), root(//) and choose(c). For example:",
			"	the formula: '3!' will print 6,",
			"	the formula: '3^2' will print 9, same as '3**2',",
			"	the formula: '8//3' will print 2,",
			"	the formula: '4^2' will print 6.,",
			"",
			"	Final examples: (w.p. - will print)",
			"	2 + 6c4 w.p. 17.0",
			"	8! - 16c10 w.p. 32312.0",
			"	8c5 * 3 + 11 w.p 179.0",
			"	9// + 8//2 + 3 w.p. 8.0",
			"",
			"	For CMD users, to reuse the last formula(of more),",
			"	just click UP on keyboard, and the formula will be there",
			"",
			"",
			"	In future version there will be option to absulote the result",
			"	and to round it, for absulote use || and for round use ~ , both at the end of each formula!.",
			"",
			"",
			"	For more information search - ?sqrt, ?pow, ?fact, ?binom.",
			"	Ohh by the way, if you ever want to leave, press enter.",
			"	Good luck."
			};
		for (int menuLine = 0; menuLine < menu.length; menuLine ++) {
			System.out.println(menu[menuLine]);
		}
	}

	// part of menu
	public static void help(String s) {
		String[] menu;
		String[] menu1 = {
				"	SQRT Function (sqrt).",
				"	A function that calculate the root of numbers.",
				"",
				"	The root has two optionals ways, square root, and other root.",
				"",
				"	The first way of use, gets only one arguement, and return the number sqaure root.",
				"	For example:",
				"	the formula: '9//' will print 3,",
				"	the formula: '16//' will print 4.",
				"",
				"	The second way of use, gets two arguement, and return the first number root the second.",
				"	For example:",
				"	the formula: '8//3' will print 2,",
				"	the formula: '9//2' will print 3, same as '9//'.",
				"",
				"	Watch out of syntacs, good luck."
			};
		String[] menu2 = {
				"	POW Function (pow).",
				"	A function that calculate the power of numbers.",
				"",
				"	The function has only one way of use,",
				"	but, you may use two different types of syntacs.",
				"",
				"	The function gets two arguement, and return the first number power the second.",
				"	For example:",
				"	the formula: '2**3' will print 8,",
				"	the formula: '3^3' will print 27, same as '3**3'.",
				"",
				"	Watch out of syntacs, good luck."
			};
		String[] menu3 = {
				"	FACT Function (fact).",
				"	A function that calculate the factorial of a number.",
				"",
				"	The function gets only one argument, and calc the factorial.",
				"	For example:",
				"	the formula: '3!' will print 6,",
				"	the formula: '5!' will print 120.",
				"",
				"	Watch out of syntacs, good luck."
			};
		String[] menu4 = {
				"	BINOM Function (binom).",
				"	A function that calculate the binom of numbers.",
				"",
				"	The function gets two argument, and calc the first one choose sencond one binom.",
				"	For example:",
				"	the formula: '4c2' will print 6,",
				"	the formula: '8c5' will print 56.",
				"",
				"	Watch out of syntacs, good luck."
			};
		String[] menu5 = {"		UNKNOWN HELP COMMAND."};
		if (s.equals("?sqrt")) {
			menu = menu1;
		} else if (s.equals("?pow")) {
			menu = menu2;
		} else if (s.equals("?fact")) {
			menu = menu3;
		} else if (s.equals("?binom")) {
			menu = menu4;
		} else {
			menu = menu5;
		}

		for (int menuLine = 0; menuLine < menu.length; menuLine ++) {
			System.out.println(menu[menuLine]);
		}
	}

	public static void memoryUpdate(String s, double result) {
		if (s.equals("a") || s.equals("A")) {
			a = result;

		} else if (s.equals("b") || s.equals("B")) {
			b = result;

		} else if (s.equals("c") || s.equals("C")) {
			c = result;

		} else {
			System.err.println("Unrecornized memory variable.");
		}
	}

	// Update the current formula with the values in memory and return new formula.
	public static String updateFromMemory(String s, double result) {
		s = s.replace("ans", "" + result);
  	s = s.replace("Ans", "" + result);

  	s = s.replace("a", "" + a);
  	s = s.replace("A", "" + a);

  	s = s.replace("b", "" + b);
  	s = s.replace("B", "" + b);

  	s = s.replace("c", "" + c);
  	s = s.replace("C", "" + c);

  	return s;
	}

	// Update the result to the memory variable.
	// return true if found memory variable, no if not.
	public static boolean updateFromMemory(String s, int index, double[] sums) {
		if (s.equals("ans") || s.equals("Ans")) {
			sums[index] = result;
			return true;

		} else if (s.equals("a") || s.equals("A")) {
			sums[index] = a;
			return true;

		} else if (s.equals("b") || s.equals("B")) {
			sums[index] = b;
			return true;

		} else if (s.equals("c") || s.equals("C")) {
			sums[index] = c;
			return true;

		}

		return false;
	}

	public static double resultCalc(double[] sums, String[] mathFormula) throws AlsCustomException{
		double sum = 0.0;
		if (mathFormula.length > 0) {
			sum = sums[0];

			for (int i = 0; i < sums.length - 1; i ++) {
	    		if (mathFormula[(2 * i) + 1].equals("+")) {
    				sum += sums[i + 1];
    			} else if (mathFormula[(2 * i) + 1].equals("-")) {
    				sum -= sums[i + 1];
    			} else if (mathFormula[(2 * i) + 1].equals("*") || mathFormula[(2 * i) + 1].equals("/")) {
    				int k = 0;
    				double sum1 = sums[i + k];

    				while (((2 * (i + k)) + 1) < mathFormula.length && (mathFormula[(2 * (i + k)) + 1].equals("*") || mathFormula[(2 * (i + k)) + 1].equals("/"))) {
    					if (mathFormula[(2 * (i + k)) + 1].equals("*")) {
	    					sum1 *= sums[i + k + 1];
    					} else {
    						sum1 /= sums[i + k + 1];	
    					} 
    					k ++;
    					//debug(sum1);
    				}

    				if ((2 * i) - 1 < 0) { // first element
    					sum -= sums[0];
    					sum += sum1;
    				} else if (mathFormula[(2 * i) - 1].equals("+")) {
    					sum -= sums[i];
    					sum += sum1;
    				} else {
    					sum += sums[i];
    					sum -= sum1;
    				}

    				i += k; // updated to be after the last moltiplyed element.

    			} else {
    				throw new AlsCustomException("SOMETHING UNKNOWN OR OF PROTOCOL." + "\nAT: " + mathFormula[i + 1]);
    			}
    		}

    		return sum;
		} else {
			return sum;
		}
		
	}

	public static void calcSubFormula(String subFormula1, double[] sums) throws AlsCustomException {
		String[] subFormula = subFormula1.split(" ");
		//debug(subFormula);

		// Calculate sub formula
		for (int i = 0; i < subFormula.length; i += 2) { // Go through each step of the sub-formula
	        		
    		if (updateFromMemory(subFormula[i], i / 2, sums)) {
    			// found a memory variable, go to next round.

    		} else if (subFormula[i].indexOf("c") != -1) { // n choose k
    			binom(subFormula[i], i / 2, sums);
    			//debug(sums[i / 2]);

    		} else if (onlyNums2(subFormula[i])) {
    			sums[i / 2] = Double.parseDouble(subFormula[i]);

    		} else if (subFormula[i].indexOf("!") != -1) {
    			fact(subFormula[i], i / 2, sums);

    		} else if (subFormula[i].indexOf("^") != -1) {
    			pow(subFormula[i], i / 2, sums);

    		} else if (subFormula[i].indexOf("**") != -1) {
    			pow(subFormula[i], i / 2, sums);

				} else if (subFormula[i].indexOf("//") != -1) {
    			sqrt(subFormula[i], i / 2, sums);

    		} else {
    			throw new AlsCustomException("SOMETHING UNKNOWN OR OF PROTOCOL." + "\nAT: " + subFormula[i]);
    		}
    		
    	}

	}

	public static void sqrt(String s, int index, double[] sums) throws AlsCustomException {
		String[] nums = s.split("//", 2);
		//debug(nums);

		double n1 = 0.0, n2 = 0.0;
		boolean rootTwo = false; // whether square root or not.
		
		if (nums[1].equals("")) {
			if (onlyNums2(nums[0])) {
				n1 = Double.parseDouble(nums[0]);
				//debug(n1);
			} else {
				throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '//'.");
			}
			rootTwo = true;
			//debug("root two is true!");
		} else {
			if (onlyNums2(nums[0])) {
				n1 = Double.parseDouble(nums[0]);
			} else {
				throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '//'.");
			}

			if (onlyNums2(nums[1])) {
				n2 = Double.parseDouble(nums[1]);
			} else {
				throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '//'.");
			}
		}
		

		if (n1 > 100000) {
			throw new AlsCustomException("TOO LARGE INPUT AT '//'.");
		} else {
			//debug("value is" + rootTwo);
			if (rootTwo) {
				sums[index] = (Math.sqrt(n1));
				//debug(sums[index]);
			} else {
				if (n2 > 100) {   //  || (n2 - n1) < - 10
					throw new AlsCustomException("TOO LARGE INPUT AT '//'.");
				} else {
					sums[index] = (Math.round(Math.pow(n1, 1.0 / n2)));
					//debug(sums[index]);
				}
			}
		}
		
	}

	public static void pow(String s, int index, double[] sums) throws AlsCustomException {
		String[] nums = s.split("\\*\\*", 2);
		//debug(nums);

		String[] nums2 = s.split("\\^", 2);
		//debug(nums2);

		double n1, n2;
		if (nums.length == 1) {
			if (nums2.length == 1) {
				throw new AlsCustomException("OF THE PROTOCOL AT '**' or '^'.");
			} else {
				if (onlyNums2(nums2[0])) {
					n1 = Double.parseDouble(nums2[0]);
				} else {
					throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '**' or '^'.");
				}

				if (onlyNums2(nums2[1])) {
					n2 = Double.parseDouble(nums2[1]);
				} else {
					throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '**' or '^'.");
				}
			}
		} else {
			if (onlyNums2(nums[0])) {
				n1 = Double.parseDouble(nums[0]);
			} else {
				throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '**' or '^'.");
			}

			if (onlyNums2(nums[1])) {
				n2 = Double.parseDouble(nums[1]);
			} else {
				throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '**' or '^'.");
			}
		}

		if (n1 > 10 || n2 > 10) {
			throw new AlsCustomException("TOO LARGE INPUT AT '**' or '^'.");
		} else {
			sums[index] = (Math.pow(n1, n2));
			//debug(sums[index]);
		}
	}

	public static void fact(String s, int index, double[] sums) throws AlsCustomException {
		String[] nums = s.split("!", 2);
		//debug(nums);

		int n1 = 0;
		if (onlyNums2(nums[0])) {
			if (Math.round(Double.parseDouble(nums[0])) == Double.parseDouble(nums[0])) {
				n1 = (int) Math.round(Double.parseDouble(nums[0]));
			} else {
				throw new AlsCustomException("OF THE PROTOCOL AT '!', ONLY INTEGERS.");
			}
		} else {
			throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT '!'.");
		}

		if (!nums[1].equals("")) {
			throw new AlsCustomException("OF THE PROTOCOL AT '!'.");
		}

		sums[index] = ((Long)factorial.factorial(n1)).doubleValue();
		//debug(sums[index]);

	}

	public static void binom(String s, int index, double[] sums) throws AlsCustomException {
		String[] nums = s.split("c", 2);
		//debug(nums);

		if (nums.length == 1) {
			throw new AlsCustomException("TOO SHORT INPUT AT XcY.");
		}

		int n1, n2;
		if (onlyNums2(nums[0])) {
			if (Math.round(Double.parseDouble(nums[0])) == Double.parseDouble(nums[0])) {
				n1 = (int) Math.round(Double.parseDouble(nums[0]));
			} else {
				throw new AlsCustomException("OF THE PROTOCOL AT XcY, ONLY INTEGERS.");
			}
		} else {
			throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT XcY.");
		}
		if (onlyNums2(nums[1])) {
			if (Math.round(Double.parseDouble(nums[1])) == Double.parseDouble(nums[1])) {
				n2 = (int) Math.round(Double.parseDouble(nums[1]));
			} else {
				throw new AlsCustomException("OF THE PROTOCOL AT XcY, ONLY INTEGERS.");
			}
		} else {
			throw new AlsCustomException("NON NUMBER OR OF THE PROTOCOL AT XcY.");
		}

		if (n1 > 100 || n2 > 100) {
			throw new AlsCustomException("TOO LARGE INPUT AT XcY.");
		} else {
			sums[index] = ((Long)Binomial.binomial(n1, n2)).doubleValue();
			//debug(sums[index]);

			if (sums[index] == 0) {
				countNotice ++;
			}
		}
	}

	// integer - positive
	public static boolean onlyNums(String s) {
		for (int i = 0; i < s.length(); i ++) {
			char c = s.charAt(i);
			if (c > 57 || c < 48) {
				return false;
			}
		}
		return true;
	}

	// double - also negative
	public static boolean onlyNums2(String s) {
		int countDots = 0;
		for (int i = 0; i < s.length(); i ++) {
			char c = s.charAt(i);
			if (c == 46) {
				countDots ++;
			} else if (s.length() > 1 && s.charAt(0) == '-') {
				// That ok, negative number.
			} else if (c > 57 || c < 48) {
				return false;
			}
		}
		if (countDots > 1)
			return false;
		return true;
	}

	// debug for [] string
	public static void debug(String[] s) {
		System.out.println("debug String[] :");
		for (int i = 0; i < s.length; i ++) {
			System.out.println("element " + (i + 1) + " - " + s[i]);
		}
	}

	// debug for double
	public static void debug(double d) {
		System.out.println("debug double :");
		System.out.println("element is - " + d);
		
	}

	// debug (just prints string)
	public static void debug(String s) {
		System.out.println("debug message :");
		System.out.println(s);
		
	}
}

/**
 * My custom exception class.
 */
class AlsCustomException extends Exception
{
  public AlsCustomException(String message)
  {
    super(message);
  }
}