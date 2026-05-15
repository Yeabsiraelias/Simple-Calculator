import javax.swing.JOptionPane;
import java.util.Stack;

public class SimpleCalculator {

    public static void main(String[] args) {

        String input = JOptionPane.showInputDialog("Enter your math expression:");

        if (input != null && !input.isEmpty()) {
            try {
                double result = evaluate(input);
                JOptionPane.showMessageDialog(null, "The result is: " + result);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage()):
                   }
    }

    public static double evaluate(String expression) {

        Stack<Double> values = new Stack<>();

        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ' ')
                continue;

            if ((c >= '0' && c <= '9') || c == '.') {
                StringBuilder buffer = new StringBuilder();
                while (i < expression.length() &&
                        ((expression.charAt(i) >= '0' && expression.charAt(i) <= '9') || expression.charAt(i) == '.')) {
                    buffer.append(expression.charAt(i++));
                }
                values.push(Double.parseDouble(buffer.toString()));
                i--;
            }

            else if (c == '(') {
                ops.push(c);
            }

            else if (c == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }

            else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {

                while (!ops.empty() && hasPrecedence(c, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c);
            }
        }

        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/' || op1 == '%') && (op2 == '+' || op2 == '-'))
            return false;
        return true;
    }

    public static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException("Cannot divide by zero");
                return a / b;
            case '%':
                return a % b;
        }
        return 0;
    }
}