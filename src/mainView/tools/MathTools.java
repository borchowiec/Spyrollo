package mainView.tools;

public class MathTools {
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
