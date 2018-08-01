package com.workfront.examples.akka.webcrawler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.workfront.examples.akka.webcrawler.actors.HeroProcessingBalancerActor;
import com.workfront.examples.akka.webcrawler.actors.HeroProcessorActor;
import com.workfront.examples.akka.webcrawler.actors.HeroesHttpDataRetrievalActor;
import com.workfront.examples.akka.webcrawler.messages.HttpGetHeroesHtmlMessage;
import com.workfront.examples.akka.webcrawler.messages.RegisterHeroProcessorMessage;
import java.io.IOException;
import static java.lang.Thread.sleep;

/**
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 *
 *    DISCLAIMER:
 *    This code works yet is *BORDERLINE OVERSIMPLIFIED*
 *
 *    You have been warned.
 *
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 */
public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        // Create System that handles Actors' interaction
        final ActorSystem actorSystem = ActorSystem.create("WebCrawlerActorSystem");

        // Create Actors responsible for HTTP data retrieval
        final ActorRef heroesStr = actorSystem.actorOf(HeroesHttpDataRetrievalActor.props(), "HeroesHttpDataRetrievalStr");
        final ActorRef heroesInt = actorSystem.actorOf(HeroesHttpDataRetrievalActor.props(), "HeroesHttpDataRetrievalInt");
        final ActorRef heroesAgi = actorSystem.actorOf(HeroesHttpDataRetrievalActor.props(), "HeroesHttpDataRetrievalAgi");

        // Create balancer which will distrubute processing jobs between multiple instances of HeroProcessorActor
        final ActorRef heroProcessingBalancer = actorSystem.actorOf(HeroProcessingBalancerActor.props(), "heroProcessingBalancer");

        // TODO: Actually move this inside the load balancer and make all HeroProcessingActor instances be in its' scope

        // Create and register Actors that will perform data processing
        // Those might be created within the context of the load balancer itself
        // Putting them here just for presentation purposes
        for (int i = 0; i < 5; i++)
        {
            final ActorRef heroProcessor = actorSystem.actorOf(HeroProcessorActor.props(), "heroProcessor" + i);
            heroProcessingBalancer.tell(new RegisterHeroProcessorMessage(), heroProcessor);
        }

        // Artificial timer just to make sure everything gets registered and setup before we send our first http requests
        sleep(100);

        // Order HTTP clients to start retrieving data from provided URLs
        heroesStr.tell(new HttpGetHeroesHtmlMessage("https://dota2.gamepedia.com/Strength"), heroProcessingBalancer);
        heroesInt.tell(new HttpGetHeroesHtmlMessage("https://dota2.gamepedia.com/Intelligence"), heroProcessingBalancer);
        heroesAgi.tell(new HttpGetHeroesHtmlMessage("https://dota2.gamepedia.com/Agility"), heroProcessingBalancer);
    }
}
