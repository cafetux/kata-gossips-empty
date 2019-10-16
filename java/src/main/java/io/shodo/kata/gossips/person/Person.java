package io.shodo.kata.gossips.person;

import io.shodo.kata.gossips.message.Message;
import io.shodo.kata.gossips.time.TicObersver;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Person implements TicObersver {

    public static final Person NOBODY = new Person("NOBODY") {
        @Override
        public void observeTic(long tic) {

        }

        @Override
        public void visit(Visitor visitor) {

        }
    };

    private final String name;
    private final List<Message> memory = new ArrayList<>();
    private Person confident;

    Person(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

    public String name() {
        return name;
    }

    public String dumpMemory() {
        return memory.stream().map(Message::content).collect(Collectors.joining(", "));
    }

    public void setConfident(Person confident) {
        this.confident = confident;
    }

    public Person confident() {
        return confident;
    }

    public void say(String message) {
        this.memory.add(new Message(NOBODY,this, message));
    }
    public void receive(Message message) {
        this.memory.add(message);
    }

    public void forget() {
        this.memory.clear();
    }

    List<Message> memory() {
        return memory;
    }

    public abstract void visit(Visitor visitor);

    public Message lastConfidence() {
        return memory.isEmpty()?Message.NO_MESSAGE:memory.get(memory.size()-1);
    }

    List<Message> retrieveMessagesFromCurrentTic(long tic) {
        return memory().stream()
                .filter(m -> m.tic() == tic - 1)
                .collect(Collectors.toList());
    }

}
