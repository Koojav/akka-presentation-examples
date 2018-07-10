package com.workfront.examples.akka.actorcreation;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LoggingActor extends AbstractActor
{
    // Logger for display purposes
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // The static props method creates and returns a Props instance.
    // Props is a configuration class to specify options for the creation of actors,
    // think of it as an immutable and thus freely shareable recipe for creating an actor
    // that can include associated deployment information.
    static public Props props()
    {
        return Props.create(LoggingActor.class, () -> new LoggingActor());
    }

    // createReceive() handles messages that are being passed to the Actor from it's Mailbox
    @Override
    public Receive createReceive()
    {
        return receiveBuilder().
                match(SimpleMessage.class, message ->
                {
                    log.info(message.content);
                })
                .build();
    }
}
