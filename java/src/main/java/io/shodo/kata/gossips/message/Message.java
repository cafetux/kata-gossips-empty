package io.shodo.kata.gossips.message;

import io.shodo.kata.gossips.time.Tic;
import io.shodo.kata.gossips.person.Person;

public class Message {

    private static Tic TIC = Tic.INSTANCE;

    public static final Message NO_MESSAGE = new Message(Person.NOBODY,Person.NOBODY, "", -1);
    private final Person source;
    private final Person destination;
    private final String content;
    private final long tic;

    public Message(Person source,Person destination, String content) {
        this(source,destination, content, TIC.getTic());
    }

    private Message(Person source,Person destination, String content, long tic) {
        this.source = source;
        this.destination = destination;
        this.content = content;
        this.tic = tic;

    }

    public Person source() {
        return source;
    }
    public Person destination() {
        return destination;
    }

    public String content() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "actualTic='" + tic + '\'' +
                ",source='" + source + '\'' +
                ",destination='" + destination + '\'' +
                ",content='" + content + '\'' +
                '}';
    }

    public long tic() {
        return tic;
    }
}
