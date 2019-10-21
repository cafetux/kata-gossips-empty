package io.shodo.kata.gossips.message;

import io.shodo.kata.gossips.time.Tic;
import io.shodo.kata.gossips.person.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageInbox {

    private List<Person> users = new ArrayList<>();
    private List<Message> waitingMessages = new ArrayList<>();

    public MessageInbox(Collection<Person> users) {
        this.users.addAll(users);
    }

    public void spread() {
        for (Person user : this.users) {
            pull(user);
        }
        sync();
    }
    private void pull(Person from) {
        MessageFactory messageFactory = new MessageFactory();
        from.visit(messageFactory);
        this.waitingMessages.addAll(messageFactory.getMessages());
    }

    private void sync() {
        for (Message waitingMessage : waitingMessages) {
            Person destination = waitingMessage.destination();
            destination.receive(waitingMessage);
        }
        waitingMessages.clear();
        Tic.INSTANCE.nextTic();
    }
}
