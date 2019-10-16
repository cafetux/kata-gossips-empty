package io.shodo.kata.gossips.person;

public class Agent extends Person {
    public Agent(String name) {
        super(name);
    }



    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void observeTic(long tic) {
        //Agent keep all messages
    }
}
