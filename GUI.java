import java.awt.Font;

public class GUI {
	public static int FORMULA_MAX_LENGTH = 25;
	public static String VALID_VARIABLES_TO_MULTI = "0123456789abcABC"; // (
	public static String VALID_VARIABLES_TO_MULTI2 = "0123456789abcABC"; // )
	public static double FIXED_PIXELS = 0.25; // fixed pixels at 'fix' function.
	public static int ZERO = 0;
	public static int ONE = 1;
	public static int C_TO_CC = 2;
	public static String EMPTY_STRING = "";

	// ASCII table values
	public static char ESCAPE = 27;
	public static char Q_UPPERCASE = 81;  // Q
	public static char Q_LOWERCASE = 113;  // q
	public static char ENTER = 10;
	public static char EQUALS = 61;  // =
	public static char OPEN_BRACKETS = 40;  // (
	public static char CLOSE_BRACKETS = 41;  // }
	public static char BACKSPACE = 8;
	public static char C_LOWERCASE = 99;  // c
	public static char MULTI = 42;  // *
	public static char DEVIDER = 47;  // /
	public static char SPACE = 32;
	public static char Z_VALUE = 122;  // zero value for char array.

	// Graphics values
	public static double FORMULA_START = 5.0;
	public static double FORMULA_HEIGHT = 45.0;


	public static double[][] COORDINATES = {{30.0, 0.0, 40.0, 4.0}, {0.0, 10.5, 10.0, 16.0}, {10.0, 10.5, 20.0, 16.0}, {20.0, 10.5, 30.0, 16.0}, // 0, 1, 2, 3
											{0.0, 5.0, 10.0, 10.5}, {10.0, 5.0, 20.0, 10.5}, {20.0, 5.0, 30.0, 10.5}, // 4, 5, 6
											{0.0, 0.0, 10.0, 5.0}, {10.0, 0.0, 20.0, 5.0}, {20.0, 0.0, 30.0, 5.0}}; // 7, 8, 9

	public static String lastResult = "0.0";


	public static void main(String[] args) {
		drawCalculator.drawCalculator();
		mainGraphics();
	}

	public static void mainGraphics() {
		char userInput;
		StdDraw.setPenColor(StdDraw.WHITE);
		double add = ZERO;
		char[] formula = new char[FORMULA_MAX_LENGTH];
		cleanFormula(formula);
		boolean resultOnScreen = false;

		int index = ZERO;
		int countCs = ZERO;  // Twice 'c' will clear.
		int countCsMouse = ZERO;  // Twice 'c' will clear.
		boolean cMouse = false;
		int countEnters = ZERO;

		boolean hasMouseClicked = false;
		boolean hasMouseInput = false;
		int mouseInput = 0;
		double mouseX = 0.0;
		double mouseY = 0.0;

		while (true) {
			if (StdDraw.hasNextKeyTyped()) {
				userInput = StdDraw.nextKeyTyped();

				if (userInput == ESCAPE || userInput == Q_UPPERCASE || 
					userInput == Q_LOWERCASE) {  // enter, escape, q, Q (quit)
					break;
				} else if (userInput == ENTER || userInput == EQUALS) { // Enter, =
					String[] result = calculateFormula(createFormula(formula));

					if (result[0].equals("ERROR") && result[1].equals("empty String")) {  // Exit on double Enter.
						lastResult = result[0];
						countEnters ++;
						if (countEnters == 2) {
							break;
						}
					} else {
						lastResult = result[0];
						countEnters = 0;
					}

					cC(formula);
					StdDraw.text(FORMULA_START, FORMULA_HEIGHT, EMPTY_STRING + EQUALS + SPACE + result[0]);
					StdDraw.show();
					
					index = ZERO;
					add = ZERO;
					countCs = ZERO;
					resultOnScreen = true;

				} else if (userInput == BACKSPACE) {
					add -= deleteLast(formula, index - ONE, (FORMULA_START + add));
					index --;
					if (!positive(add)) {
						add = ZERO;
					}
					if (!positive(index)) {
						index = ZERO;
					}
				}
				if (userInput == C_LOWERCASE) {
					countCs ++;
				} else {
					countCs = ZERO;
				}

				if (countCs == C_TO_CC) {
					cC(formula);
					index = ZERO;
					add = ZERO;
					countCs = ZERO;

				} else if (index != FORMULA_MAX_LENGTH && validInput(userInput)) {
					if (resultOnScreen) {
						resultOnScreen = false;
						cC();
					}
					StdDraw.text((FORMULA_START + add), FORMULA_HEIGHT, createString(userInput));
					StdDraw.show();

					formula[index] = userInput;
					add += calculateSpace(userInput);
					//if (fixFormula(formula, index, (FORMULA_START + add))) {
					//	index ++;
					//	add += calculateSpace(MULTI);
					//}  //// not yet wotking
					index ++;
				}

			} 

			if (StdDraw.mousePressed()) {
				hasMouseClicked = true;
			} else if (hasMouseClicked) {
				mouseX = StdDraw.mouseX();
				mouseY = StdDraw.mouseY();
				cMouse = false;
				hasMouseInput = false;

				if (mouseX > 30.0 && mouseX < 40.0 && mouseY > 4.0 && mouseY < 8.0) {  // =

				} else if (mouseX > 30.0 && mouseX < 40.0 && mouseY > 12.0 && mouseY < 16.0) {  // backspace
					add -= deleteLast(formula, index - ONE, (FORMULA_START + add));
					index --;
					if (!positive(add)) {
						add = ZERO;
					}
					if (!positive(index)) {
						index = ZERO;
					}

					countCsMouse ++;
					cMouse = true;
					
				} else if (mouseX > COORDINATES[0][0] && mouseX < COORDINATES[0][2] && mouseY > COORDINATES[0][1] && mouseY < COORDINATES[0][3]) {  // 0
					hasMouseInput = true;
					mouseInput = 0;
				} else if (mouseX > COORDINATES[1][0] && mouseX < COORDINATES[1][2] && mouseY > COORDINATES[1][1] && mouseY < COORDINATES[1][3]) {  // 1
					hasMouseInput = true;
					mouseInput = 1;
				} else if (mouseX > COORDINATES[2][0] && mouseX < COORDINATES[2][2] && mouseY > COORDINATES[2][1] && mouseY < COORDINATES[2][3]) {  // 2
					hasMouseInput = true;
					mouseInput = 2;
				} else if (mouseX > COORDINATES[3][0] && mouseX < COORDINATES[3][2] && mouseY > COORDINATES[3][1] && mouseY < COORDINATES[3][3]) {  // 3
					hasMouseInput = true;
					mouseInput = 3;
				} else if (mouseX > COORDINATES[4][0] && mouseX < COORDINATES[4][2] && mouseY > COORDINATES[4][1] && mouseY < COORDINATES[4][3]) {  // 4
					hasMouseInput = true;
					mouseInput = 4;
				} else if (mouseX > COORDINATES[5][0] && mouseX < COORDINATES[5][2] && mouseY > COORDINATES[5][1] && mouseY < COORDINATES[5][3]) {  // 5
					hasMouseInput = true;
					mouseInput = 5;
				} else if (mouseX > COORDINATES[6][0] && mouseX < COORDINATES[6][2] && mouseY > COORDINATES[6][1] && mouseY < COORDINATES[6][3]) {  // 6
					hasMouseInput = true;
					mouseInput = 6;
				} else if (mouseX > COORDINATES[7][0] && mouseX < COORDINATES[7][2] && mouseY > COORDINATES[7][1] && mouseY < COORDINATES[7][3]) {  // 7
					hasMouseInput = true;
					mouseInput = 7;
				} else if (mouseX > COORDINATES[8][0] && mouseX < COORDINATES[8][2] && mouseY > COORDINATES[8][1] && mouseY < COORDINATES[8][3]) {  // 8
					hasMouseInput = true;
					mouseInput = 8;
				} else if (mouseX > COORDINATES[9][0] && mouseX < COORDINATES[9][2] && mouseY > COORDINATES[9][1] && mouseY < COORDINATES[9][3]) {  // 9
					hasMouseInput = true;
					mouseInput = 9;
				}


				if (hasMouseInput) {
					if (resultOnScreen) {
						resultOnScreen = false;
						cC();
					}
					StdDraw.text((FORMULA_START + add), FORMULA_HEIGHT, createString(mouseInput));
					StdDraw.show();

					formula[index] = (char)(mouseInput + 48);  // to represent the number
					add += calculateSpace(mouseInput);
				}

				if (countCsMouse == C_TO_CC) {
					cC(formula);
					index = ZERO;
					add = ZERO;
					countCsMouse = ZERO;
				} else if (!cMouse) {
					countCsMouse = 0;
				}


				hasMouseClicked = false;
			}
		}
		leavingEffect();
	}

	public static void cC(char[] formula) {
		drawCalculator.drawFormulaRectangle();
		StdDraw.show();
		StdDraw.setPenColor(StdDraw.WHITE);

		cleanFormula(formula);
	}

	public static void cC() {
		drawCalculator.drawFormulaRectangle();
		StdDraw.show();
		StdDraw.setPenColor(StdDraw.WHITE);
	}

	public static String createFormula(char[] s) {
		String formula = EMPTY_STRING;
		int lastMulti = - ONE - FORMULA_MAX_LENGTH;
		int lastDev = - ONE - FORMULA_MAX_LENGTH;
		boolean defaultAddition, withoutSpace;

		for (int i = ZERO; i < s.length; i ++) {
			defaultAddition = true;
			withoutSpace = false;
			if (s[i] != Z_VALUE) {
				if (s[i] == OPEN_BRACKETS) {  // (
					defaultAddition = false;
					withoutSpace = true;
				} else if (s[i] == CLOSE_BRACKETS) {  // )
					if (positive(formula.length())) {
						formula = formula.substring(ZERO, formula.length() - ONE);
					}
				} else if (s[i] == '!') {  // !
					if (positive(formula.length())) {
						formula = formula.substring(ZERO, formula.length() - ONE);
					}
				} else if (s[i] == '^') {  // ^
					if (positive(formula.length())) {
						formula = formula.substring(ZERO, formula.length() - ONE);
					} 
					defaultAddition = false;
					withoutSpace = true;
				} else if (s[i] == MULTI) {  // *
					if (i - lastMulti == ONE) {
						formula = formula.substring(ZERO, formula.length() - 3);
						formula += createString(MULTI) + createString(MULTI);

						if (i < s.length) {
							i ++;
							formula += s[i] + createString(SPACE);
						}
						defaultAddition = false;
					} else {
						lastMulti = i;
					}
				} else if (s[i] == DEVIDER) {  // /
					if (i - lastDev == ONE) {
						formula = formula.substring(ZERO, formula.length() - 3);
						formula += createString(DEVIDER);

						String notVaild = "+-*/^!";

						if (i < s.length && notVaild.indexOf(s[i + ONE]) == -1 && s[i + ONE] != Z_VALUE) {
							i ++;
							formula += createString(DEVIDER) + s[i] + createString(SPACE);
							defaultAddition = false;
						}
					} else {
						lastDev = i;
					}
				} else if (s[i] >= 48 && s[i] <= 57) {  // 0-9
					if (positive(formula.length())) {
						char c = formula.charAt(formula.length() - 2);
						char p = formula.charAt(formula.length() - ONE);
						String notAttached = EMPTY_STRING + createString(C_LOWERCASE) + createString('^');
						if (c >= 48 && c <= 57 && notAttached.indexOf(p) == -1) {
							formula = formula.substring(ZERO, formula.length() - ONE);
						}
					}

				} else if (s[i] == C_LOWERCASE) {
					if (positive(formula.length())) {
						formula = formula.substring(ZERO, formula.length() - ONE);
					}
					defaultAddition = false;
					withoutSpace = true;
				}

				if (defaultAddition) {
					formula += s[i] + createString(SPACE);
				} else if (withoutSpace) {
					formula += s[i];
				}
				
			}
		}

		System.out.println(formula);
		if (formula.length() != ZERO) {
			formula.substring(ZERO, formula.length() - ONE);
			return formula;
		} else {
			return EMPTY_STRING;
		}
	}

	public static void cleanFormula(char[] s) {
		for (int i = 0; i < s.length; i ++) {
			s[i] = 122;  // z - stand for zero
		}
	}

	public static boolean validInput(char c) {
		if (c >= 48 && c <= 57) {  // 0 - 9
			return true;
		} else if ((c <= 67 && c >= 65) || (c >= 97 && c <= 99)) {  // Memory: A - C, a - c
			return true;
		} else if (c == 110 || c == 115) {  // Ans: n, s 
			return true;
		} else if ((c <= 47 && c >= 40 && c != 44) ||
					c == 94 || c == 33) {  // Functions: c (already been checked), /, *, ^, !, +, -, ), (, .
			return true;
		}
		return false;
	}

	public static double calculateSpace(char c) {
		if ((c >= 97 && c <= 99) || c == 110 || c == 115) {  // a, b, c, n, s
			return 1.4;
		} else if (c <= 67 && c >= 65) {  // A, B, C
			return 1.5;
		} else if (c == 43) {  // +
			return 1.4;
		} else if (c == 45 || c == 48) {  // -, 0
			return 1.2;
		}
		return 1.0;  // default.
	}

	// with int
	public static double calculateSpace(int c) {
		if ((c >= 97 && c <= 99) || c == 110 || c == 115) {  // a, b, c, n, s
			return 1.4;
		} else if (c <= 67 && c >= 65) {  // A, B, C
			return 1.5;
		} else if (c == 43) {  // +
			return 1.4;
		} else if (c == 45 || c == 48) {  // -, 0
			return 1.2;
		}
		return 1.0;  // default.
	}

	public static void leavingEffect() {
		drawCalculator.drawFormulaRectangle();
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(10.0, FORMULA_HEIGHT, "Bye Bye");
		StdDraw.show(1000);
		StdDraw.text(15.0, FORMULA_HEIGHT, ".");
		StdDraw.show(1000);
		StdDraw.text(16.0, FORMULA_HEIGHT, ".");
		StdDraw.show(1000);
		StdDraw.text(17.0, FORMULA_HEIGHT, ".");
		StdDraw.show(1000); // second
		
		
		StdDraw.clear();
		StdDraw.show();
	}

	public static String[] calculateFormula(String formula) {
		String[] resultText = new String[2];
		resultText[0] = ""; 
		try {
		  		double result = calc.calcFormula(formula); // new result
		  		resultText[0] += result;  //round(result, 5);		  		

        	/* 
        	if (countNotice / 2 >= 1) {
        		countNotice = 0;
        		throw new AlsCustomException("\nNote: you may use XcY wrong, it return 0.");
        		result += " Note,";
        	}
        	 */
        	return resultText;
		}
		catch(Exception e) {
		  System.err.println("something may be wrong,");
		  // print the stack trace
		  e.printStackTrace();

		  resultText[0] = "ERROR";
		  resultText[1] = e.getMessage();

		  return resultText;
		}
	}

	public static double deleteLast(char[] formula, int index, double cursor) {
		// Delete the character and return how much backward should go.
		if (index >= 0) {
			double space = calculateSpace(formula[index]);

			StdDraw.setPenColor();
			if ((formula[index] >= 97 && formula[index] <= 99) || formula[index] == 110) {  // a, b, c, n
				StdDraw.filledRectangle(cursor - space, 45.0, .75, space);
			} else if (formula[index] == 115) {  // s
				StdDraw.filledRectangle(cursor - space, 45.0, .8, space);
			} else if (formula[index] == 43) {  // +
				StdDraw.filledRectangle(cursor - space, 45.0, .7, space);
			} else if (formula[index] >= 65 && formula[index] <= 67) {  // A, B, C
				StdDraw.filledRectangle(cursor - space, 45.0, .9, space);
			} else {
				StdDraw.filledRectangle(cursor - space, 45.0, .6, space);
			}
			
			StdDraw.show();
			StdDraw.setPenColor(StdDraw.WHITE);

			formula[index] = 122;  // z - stand for zero

			return space;
		}
		return 0.0;
	}

	public static boolean fixFormula(char[] formula, int index, double cursor) {
		// Go over the last two places,
		// return true if needed to add *.
		if (index > 0) {
			if (VALID_VARIABLES_TO_MULTI.indexOf(formula[index]) != -1 &&
				VALID_VARIABLES_TO_MULTI2.indexOf(formula[index - 1]) != -1) {
				char c = formula[index];
				deleteLast(formula, index, cursor + FIXED_PIXELS);  // 0.25 is the fixed value needed to add... IDK.
				formula[index] = 42;
				formula[index + 1] = c;

				StdDraw.text(cursor - calculateSpace(c), 45.0, "*");
				StdDraw.text(cursor, 45.0, "" + c);
				StdDraw.show();

				return true;
			}
		}
		return false;
	}


	//// Help functions area
	public static boolean positive(int n) {
		return n > ZERO;
	}

	public static boolean positive(double n) {
		return n > ZERO;
	}

	public static String createString(char a) {
		return EMPTY_STRING + a;
	}

	public static String createString(int a) {
		return EMPTY_STRING + a;
	}

}