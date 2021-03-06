package com.workfront.examples.akka.webcrawler.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.workfront.examples.akka.webcrawler.messages.ProcessHeroMessage;

public class HeroProcessorActor extends AbstractActor
{
    // Logger for display purposes
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // The static props method creates and returns a Props instance.
    // Props is a configuration class to specify options for the creation of actors,
    // think of it as an immutable and thus freely shareable recipe for creating an actor
    // that can include associated deployment information.
    static public Props props()
    {
        return Props.create(HeroProcessorActor.class, () -> new HeroProcessorActor());
    }

    // createReceive() handles messages that are being passed to the Actor from it's Mailbox
    @Override
    public Receive createReceive()
    {
        return receiveBuilder().
                match(ProcessHeroMessage.class, message ->
                {
                    log.info("Processing hero: " + message.heroHtmlRow.select("td a").get(1).attr("title"));
                })
                .build();
    }
}
