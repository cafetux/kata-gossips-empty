package io.shodo.kata.gossips.message;

import io.shodo.kata.gossips.time.Tic;
import io.shodo.kata.gossips.person.Person;

import java.util.ArrayList;
import java.util.List;

public class MessageInbox {
    private List<Message> waitingMessages = new ArrayList<>();

    public void pull(Person from) {
        MessageFactory messageFactory = new MessageFactory();
        from.visit(messageFactory);
        this.waitingMessages.addAll(messageFactory.getMessages());
    }

    public void sync() {
        for (Message waitingMessage : waitingMessages) {
            Person destination = waitingMessage.destination();
            destination.receive(waitingMessage);
        }
        waitingMessages.clear();
        Tic.INSTANCE.nextTic();
    }
}
