package main.days.day18Support;

import java.util.Stack;

public class Calculator {

    private static Stack<Double> stack = new Stack<>();

    public static void expr()	throws Exception {
        term();
        while (Scanner.la == Token.TIMES || Scanner.la == Token.SLASH) {
            Scanner.scan();
            int op = Scanner.token.kind;
            term();
            if(op == Token.TIMES){
                stack.push(stack.pop() * stack.pop());
            }else{
                double val2 = stack.pop();
                double val1 = stack.pop();
                stack.push(val1 / val2);
            }
        }
    }

    public static void term()	throws Exception {
        factor();
        while (Scanner.la == Token.PLUS
                || Scanner.la == Token.MINUS) {
            Scanner.scan();
            int op = Scanner.token.kind;
            term();
            if(op == Token.PLUS){
                stack.push(stack.pop() + stack.pop());
            }else{
                stack.push(-stack.pop() + stack.pop());
            }
        }
    }

    public static void factor() throws Exception {
        if (Scanner.la == Token.LBRACK) {
            Scanner.scan();
            expr();
            Scanner.check(Token.RBRACK);
        } else if (Scanner.la == Token.NUMBER) {
            Scanner.scan();
            stack.push(Scanner.token.val);
        }else if (Scanner.la == Token.IDENT){
            Scanner.scan();
            if(Scanner.token.str.equals("PI")){
                stack.push(Math.PI);
            }else if(Scanner.token.str.equals("E")){
                stack.push(Math.E);
            }
        }
    }

    public static double pop(){
        return stack.pop();
    }
}