package com.workfront.examples.akka.remoteactorlookup;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;

public class RemoteSystem
{
    public static void main(String[] args) throws IOException
    {
        // Create Actor System that will be connected to remotely from MainSystem
        final ActorSystem system = ActorSystem.create("RemoteLoggingSystem",

        // Load appropriate configuration
        ConfigFactory.load(("remote_system")));

        // Create Actor on /user level
        system.actorOf(Props.create(LoggingActor.class), "logger");
    }
}