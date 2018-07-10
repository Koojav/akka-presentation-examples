package com.workfront.examples.akka.actorcreation;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        // Create System that handles Actors' interaction
        final ActorSystem actorSystem = ActorSystem.create("SampleActorSystem");

        // Create an instance of an Actor
        final ActorRef loggingActor = actorSystem.actorOf(LoggingActor.props(), "loggingActor");

        // Define message that will be sent to the Actor
        SimpleMessage simpleMessage = new SimpleMessage("Hello World!");

        // Send message
        loggingActor.tell(simpleMessage, ActorRef.noSender());
    }
}
