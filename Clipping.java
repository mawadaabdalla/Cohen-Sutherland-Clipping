
import java.util.*;


public class Clipping{
    //defining region code
    static final int INSIDE = 0; 
    static final int LEFT = 1; 
    static final int RIGHT = 2; 
    static final int BOTTOM = 4; 
    static final int TOP = 8; 
    //defining limits of window
    static int x_max,y_max , x_min, y_min;
    static int x1,x2,y1,y2;
     static int computeCode(double x, double y)
    {
        // initialized as being inside
        int code = INSIDE;
 
        if (x < x_min) // to the left of rectangle
            code |= LEFT;
        else if (x > x_max) // to the right of rectangle
            code |= RIGHT;
        if (y < y_min) // below the rectangle
            code |= BOTTOM;
        else if (y > y_max) // above the rectangle
            code |= TOP;
 
        return code;
    }
    public static void input(){
     Scanner sc = new Scanner(System.in);
     // Defining x_max, y_max and x_min, y_min
     System.out.print("Enter x max==>");
     x_max = sc.nextInt();
     System.out.print("Enter y max==>");
     y_max = sc.nextInt();
     System.out.print("Enter x min==>");
     x_min = sc.nextInt();
     System.out.print("Enter y min==>");
     y_min = sc.nextInt();  
     System.out.print("Enter x1==>");
     x1 = sc.nextInt();
     System.out.print("Enter y2==>");
     y1 = sc.nextInt();
     System.out.print("Enter x2==>");
     x2 = sc.nextInt();
     System.out.print("Enter y2==>");
     y2 = sc.nextInt();
    }
    static void cohenSutherlandClip(double x1, double y1, double x2, double y2)
    {
        // Compute region codes for P1, P2
        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);
        System.out.println("code for A" + Integer.toBinaryString(code1));
        System.out.println("code for B" + Integer.toBinaryString(code2) );
        double m;
        m= (y2-y1) / (x2- x1);
        System.out.println("slope is " + m);
        // Initialize line as outside the rectangular window
        boolean accept = false;
        
        while (true) {
            if ((code1 == 0) && (code2 == 0)) {
                // If both endpoints lie within rectangle
                accept = true;
                break;
            }
            else if ((code1 & code2) != 0) {
                // If both endpoints are outside rectangle,
                // in same region
                break;
            }
            else {
                // Some segment of line lies within the
                // rectangle
                int code_out;
                double x = 0, y = 0;
 
                // At least one endpoint is outside the
                // rectangle, pick it.
                if (code1 != 0)
                    code_out = code1;
                else
                    code_out = code2;
 
                // Find intersection point;
               
                if ((code_out & TOP) != 0) {
                    // point is above the clip rectangle
                    x = Math.round((1/m) * (y_max - y1) + x1);
                    y = y_max;
                }
                else if ((code_out & BOTTOM) != 0) {
                    // point is below the rectangle
                    x = Math.round((1/m) * (y_min - y1) + x1);
                    y = y_min;
                }
                else if ((code_out & RIGHT) != 0) {
                    // point is to the right of rectangle
                    y = Math.round(y1 + (m * (x_max - x1) ));
                    x = x_max;
                }
                else if ((code_out & LEFT) != 0) {
                    // point is to the left of rectangle
                    y = Math.round(y1 +( m * (x_min - x1)));
                    x = x_min;
                }
 
                // Now intersection point x, y is found
                // We replace point outside rectangle
                // by intersection point
                if (code_out == code1) {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1);
                }
                else {
                    x2 = x;
                    y2 = y;
                    code2 = computeCode(x2, y2);
                }
            }
        }
        if (accept) {
            System.out.println("Line accepted from " + x1
                               + ", " + y1 + " to " + x2
                               + ", " + y2);
            // Here the user can add code to display the
            // rectangle along with the accepted (portion
            // of) lines
        }
        else
            System.out.println("Line rejected");
    }
    
    public static void main(String[] args) {
     input();
     cohenSutherlandClip(x1,y1,x2,y2);
    }
    
}

