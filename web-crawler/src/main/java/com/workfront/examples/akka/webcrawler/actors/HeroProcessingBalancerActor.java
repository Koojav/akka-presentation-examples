package com.workfront.examples.akka.webcrawler.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.workfront.examples.akka.webcrawler.messages.HeroesReadyToProcessMessage;
import com.workfront.examples.akka.webcrawler.messages.ProcessHeroMessage;
import com.workfront.examples.akka.webcrawler.messages.RegisterHeroProcessorMessage;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class HeroProcessingBalancerActor extends AbstractActor
{
    // Logger for display purposes
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // The static props method creates and returns a Props instance.
    // Props is a configuration class to specify options for the creation of actors,
    // think of it as an immutable and thus freely shareable recipe for creating an actor
    // that can include associated deployment information.
    static public Props props()
    {
        return Props.create(HeroProcessingBalancerActor.class, () -> new HeroProcessingBalancerActor());
    }

    // Contains HTML rows with hero info, one row per one entry in ArrayList
    private Elements heroHtmlRows = new Elements();


    // List of Actors that have stated their willingness to process further hero rows provided in HTML form
    private ArrayList<ActorRef> heroProcessors = new ArrayList<>();

    // Iterator used to loop over the heroProcessors list.
    private Iterator<ActorRef> processorsIterator;

    // createReceive() handles messages that are being passed to the Actor from it's Mailbox
    @Override
    public Receive createReceive()
    {
        return receiveBuilder().
                match(HeroesReadyToProcessMessage.class, message ->
                {
                    log.info("Received new items to distribute.");
                    heroHtmlRows.addAll(message.heroHtmlRows);
                    performBalancedJobBroadcast();
                }).
                match(RegisterHeroProcessorMessage.class, message ->
                {
                    log.info("Registering HeroProcessorActor: " + sender().toString());
                    heroProcessors.add(getSender());
                })
                .build();
    }

    // Propagate messages with jobs to hero processors in a balanced manner.
    private void performBalancedJobBroadcast()
    {
        // Propagate all tasks waiting in the queue to attached HeroProcessorActor instances
        while (heroHtmlRows.size() > 0)
        {
            Element heroHTMLrow = heroHtmlRows.remove(0);
            ProcessHeroMessage processHeroMessage = new ProcessHeroMessage(heroHTMLrow);

            // Iterator might have not yet been created or was left at the last hero processor
            // It needs to be set and able to loop around the collection
            if (processorsIterator == null || !processorsIterator.hasNext())
            {
                // Reset iterator to position zero, Java style.
                processorsIterator = heroProcessors.iterator();
            }

            // Propagate single task via message system and put data in the inbox of one of the hero processors.
            processorsIterator.next().tell(processHeroMessage, ActorRef.noSender());
        }
    }
}
