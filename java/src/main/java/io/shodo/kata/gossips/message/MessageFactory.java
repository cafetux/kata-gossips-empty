package io.shodo.kata.gossips.message;

import io.shodo.kata.gossips.time.Tic;
import io.shodo.kata.gossips.person.Visitor;
import io.shodo.kata.gossips.person.*;

import java.util.ArrayList;
import java.util.List;

public class MessageFactory implements Visitor {

    private List<Message> messages = new ArrayList<>();

    @Override
    public void visit(Mister person) {
        Person to = person.confident();
        if(to != null && !person.dumpMemory().equals("")) {
            messages.add(new Message(person, to, person.lastConfidence().content()));
        }
        person.forget();
    }

    @Override
    public void visit(Agent person) {
        person.forget();
    }

    @Override
    public void visit(Doctor doctor) {
        Person to = doctor.confident();
        if(to != null && !doctor.dumpMemory().equals("")) {
            messages.add(new Message(doctor, to, doctor.lastConfidence().content()));
        }
    }

    @Override
    public void visit(Professor professor) {
        Person to = professor.confident();
        if(to != null && !professor.dumpMemory().equals("")) {
            Message content = professor.lastConfidence();
            if(Tic.INSTANCE.getTic()-content.tic()>2) {
                messages.add(new Message(professor, to, content.content()));
                professor.forget();
            }
        }
    }

    @Override
    public void visit(Lady lady) {
        Person to = lady.confident();
        if(to != null && !lady.dumpMemory().equals("")) {
            Message content = lady.lastConfidence();
            if(content.source() instanceof Doctor) {
                messages.add(new Message(lady, to, content.content()));
                lady.forget();
            }
        }

    }

    @Override
    public void visit(Sir sir) {
        if(!sir.dumpMemory().equals("")) {
            String content = reverse(sir.lastConfidence().content());
            messages.add(new Message(sir, sir.lastConfidence().source(), content));
        }
        sir.forget();
    }

    private String reverse(String content) {
        StringBuilder str = new StringBuilder();
        for (char c : content.toCharArray()) {
            str.insert(0,c);
        }
        return str.toString();
    }

    public List<Message> getMessages() {
        return messages;
    }
}
