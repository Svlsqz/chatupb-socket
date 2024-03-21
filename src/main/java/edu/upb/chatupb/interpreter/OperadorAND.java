package edu.upb.chatupb.interpreter;
public class OperadorAND implements Expression{

    private Expression expresion1;
    private Expression expresion2;

    public OperadorAND(Expression expresion1, Expression expresion2){
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
    }

    @Override
    public boolean evalua(String sentence) throws Exception {
        return expresion1.evalua(sentence) && expresion2.evalua(sentence);
    }

}
