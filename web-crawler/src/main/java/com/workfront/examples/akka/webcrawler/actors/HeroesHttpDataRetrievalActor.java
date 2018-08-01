package com.workfront.examples.akka.webcrawler.actors;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.workfront.examples.akka.webcrawler.messages.HeroesReadyToProcessMessage;
import com.workfront.examples.akka.webcrawler.messages.HttpGetHeroesHtmlMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HeroesHttpDataRetrievalActor extends AbstractActor
{
    // Logger for display purposes
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // The static props method creates and returns a Props instance.
    // Props is a configuration class to specify options for the creation of actors,
    // think of it as an immutable and thus freely shareable recipe for creating an actor
    // that can include associated deployment information.
    static public Props props()
    {
        return Props.create(HeroesHttpDataRetrievalActor.class, () -> new HeroesHttpDataRetrievalActor());
    }

    // createReceive() handles messages that are being passed to the Actor from it's Mailbox
    @Override
    public Receive createReceive()
    {
        return receiveBuilder().
                match(HttpGetHeroesHtmlMessage.class, message ->
                {
                    log.info("HTTP GET: " + message.url);

                    Document doc = Jsoup.connect(message.url).get();
                    Elements heroHtmlRows = doc.selectFirst("table.wikitable").select("tbody tr");
                    heroHtmlRows.remove(0);

                    HeroesReadyToProcessMessage heroesReadyToProcessMessage = new HeroesReadyToProcessMessage(heroHtmlRows);
                    sender().tell(heroesReadyToProcessMessage, Actor.noSender());
                })
                .build();
    }
}
