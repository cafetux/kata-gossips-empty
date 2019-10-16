package io.shodo.kata.gossips.person;

import io.shodo.kata.gossips.person.*;

public interface Visitor {


    void visit(Mister person);

    void visit(Agent person);

    void visit(Doctor doctor);

    void visit(Professor professor);

    void visit(Lady lady);

    void visit(Sir sir);
}
