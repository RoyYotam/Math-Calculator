import java.awt.Font;


public class drawCalculator {
	public static void main(String[] args) {
		drawCalculator();
		// Font cahnged in drawTop
		// regular is 16
	}

	public static void drawCalculator() {

        drawBackground();

        drawFormulaRectangle();


        // calculator rectangle.
        StdDraw.rectangle(20.0, 20.0, 20.0, 20.0);

        
        drawTop();
		
		drawBottom();

        StdDraw.show(10);
	}

	public static void drawFormulaRectangle() {
		// formula rectangle.
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(20.0, 45.0, 20.0, 4.0);
	}

	public static void drawBackground() {
		// draws background grey.

		StdDraw.setCanvasSize(400, 500);

		StdDraw.setXscale(0, 40);
        StdDraw.setYscale(0, 50);


		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledSquare(20.0, 30.0, 40.0);
	}

	public static void drawTop() {

		// top
        StdDraw.line(30.0, 15.0, 30.0, 40.0);
        StdDraw.line(30.0, 16.0, 40.0, 16.0);

   		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 20));

		drawMemory();
		drawFunctions();
	}

	public static void drawMemory() {
		StdDraw.line(30.0, 24.0, 40.0, 24.0);
        StdDraw.line(30.0, 32.0, 40.0, 32.0);

        StdDraw.text(34.0, 20.0, "C");
        StdDraw.text(34.0, 28.0, "B");
        StdDraw.text(34.0, 36.0, "A");
	}

	public static void drawFunctions() {
		StdDraw.line(0.0, 24.0, 30.0, 24.0);
        StdDraw.line(0.0, 32.0, 30.0, 32.0);
        StdDraw.line(10.0, 15.0, 10.0, 40.0);
        StdDraw.line(20.0, 15.0, 20.0, 40.0);

		StdDraw.text(4.2, 20.0, "X");
        StdDraw.text(4.0, 27.0, "/");
        StdDraw.text(4.5, 28.0, "/");
        StdDraw.text(4.0, 36.0, "XcY");

        StdDraw.text(15.0, 20.0, "__");
        StdDraw.text(15.0, 20.0, "|");
        StdDraw.text(15.0, 19.0, "|");
        StdDraw.text(15.0, 28.0, "/\\");
        StdDraw.text(14.1, 27.0, "/");
        StdDraw.text(15.7, 27.0, "\\");
        StdDraw.text(15.0, 37.0, "|");
        StdDraw.text(15.0, 36.0, "|");
        StdDraw.text(15.0, 34.0, "o");


		StdDraw.text(25.0, 20.0, "__");
		StdDraw.text(24.0, 27.0, "\\/");
        StdDraw.text(23.3, 28.0, "\\");
        StdDraw.text(24.9, 28.0, "/");
        StdDraw.text(26.2, 29.2, "__");
        StdDraw.text(24.0, 36.0, "");
	}

	public static void drawBottom() {
		StdDraw.rectangle(15.0, 8.0, 15.0, 8.0); // 1-9 rectangle
		drawNumbers(); // fill numbers.

		drawRightBottom();
	}

	public static void drawRightBottom() {
		// draw the right bottom.

		StdDraw.line(30.0, 12.0, 40.0, 12.0);
		StdDraw.line(30.0, 8.0, 40.0, 8.0);
		StdDraw.line(30.0, 4.0, 40.0, 4.0);

		
		StdDraw.text(34.0, 13.0, "Del / C");

		StdDraw.text(34.0, 9.0, "Ans");

		StdDraw.text(34.0, 6.5, "___");
		StdDraw.text(34.0, 5.75, "___");

		StdDraw.text(34.0, 1.0, "0");
	}

	public static void drawNumbers() {
		// draw the left bottom rectangle - numbers.

		// horizonal
		StdDraw.line(.0, 10.5, 30.0, 10.5);
		StdDraw.line(.0, 5.0, 30.0, 5.0);

		// vertical
		StdDraw.line(10.0, .0, 10.0, 16.0);
		StdDraw.line(20.0, .0, 20.0, 16.0);

		// numbers
		
		StdDraw.text(5.0, 2.0, "7");
		StdDraw.text(5.0, 7.0, "4");
		StdDraw.text(5.0, 13.0, "1");

		StdDraw.text(15.0, 2.0, "8");
		StdDraw.text(15.0, 7.0, "5");
		StdDraw.text(15.0, 13.0, "2");

		StdDraw.text(25.0, 2.0, "9");
		StdDraw.text(25.0, 7.0, "6");
		StdDraw.text(25.0, 13.0, "3");
	}

}

