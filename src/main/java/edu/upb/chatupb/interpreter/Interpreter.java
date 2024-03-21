package edu.upb.chatupb.interpreter;

public class Interpreter {
    private Expression expression;

    public Interpreter(Expression expression){
        this.expression = expression;
    }

    public boolean interpret(String sentence) throws Exception{
        return expression.evalua(sentence);
    }
}
