package io.shodo.kata.gossips;

import io.shodo.kata.gossips.message.MessageInbox;
import io.shodo.kata.gossips.person.*;
import io.shodo.kata.gossips.time.Tic;

import java.util.*;

class Gossips {

    private final Map<String, Person> people = new HashMap<>();
    private MessageInbox inbox = new MessageInbox();


    Gossips(String... names) {
        Arrays.stream(names)
                .map(s -> s.split(" "))
                .map(this::create)
                .peek(person -> people.put(person.name(),person))
                .forEach(Tic.INSTANCE::abonne);
    }

    private Person create(String[] person) {
        switch (person[0]) {
            case "Mr" :
                return new Mister(person[1]);
            case "Dr" :
                return new Doctor(person[1]);
            case "Pr" :
                return new Professor(person[1]);
            case "Agent" :
                return new Agent(person[1]);
            case "Lady" :
                return new Lady(person[1]);
            case "Sir" :
                return new Sir(person[1]);
            default:throw new IllegalArgumentException("unknown "+ Arrays.toString(person));
        }
    }


    PersonRelationStep from(String personName) {
        return new PersonRelationStep(people.get(personName),this);
    }

    PersonMessageStep say(String message) {
        return new PersonMessageStep(message,this);
    }

    String ask(String persoToAsk) {
        return people.get(persoToAsk).dumpMemory();
    }

    void spread() {
        Tic.INSTANCE.nextTic();
        for (Person origin : this.people.values()) {
            inbox.pull(origin);
        }
        inbox.sync();
    }

    public class PersonRelationStep {
        private final Person from;
        private final Gossips gossips;

        PersonRelationStep(Person from, Gossips gossips) {
            this.from = from;
            this.gossips = gossips;
        }

        Gossips to(String personName) {
            from.setConfident(people.get(personName));
            return gossips;
        }
    }

    public class PersonMessageStep {
        private final String message;
        private final Gossips gossips;

        PersonMessageStep(String message, Gossips gossips) {
            this.message = message;
            this.gossips = gossips;
        }

        Gossips to(String personName) {
            people.get(personName).say(message);
            return gossips;
        }
    }
}

