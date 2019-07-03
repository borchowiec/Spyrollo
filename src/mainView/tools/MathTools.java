package mainView.tools;

/**
 * This class contains methods that solves math problems.
 * @author Patryk Borchowiec
 */
public class MathTools {
    /**
     * This method is looking for greatest common divisor of two numbers
     * @param first First number
     * @param second Second number
     * @return Greatest common divisor of two numbers
     */
    public static int gcd(int first, int second) {
        while (first != second) {
            if (first > second)
                first = first - second;
            else
                second = second - first;
        }
        return first;
    }
}
