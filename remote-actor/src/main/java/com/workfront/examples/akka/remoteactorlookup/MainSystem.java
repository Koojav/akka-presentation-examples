package com.workfront.examples.akka.remoteactorlookup;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;

public class MainSystem
{
    public static void main(String[] args)
    {
        // Create system that will be referred to as Main (local) Actor System
        final ActorSystem system = ActorSystem.create
                ("MainSystem", ConfigFactory.load("main_system"));

        // Define path to the remote actor that needs to be accessed locally
        final String path = "akka.tcp://RemoteLoggingSystem@127.0.0.1:2552/user/logger";

        // Find that Actor
        ActorSelection actor = system.actorSelection(path);

        // Print status info to the console
        System.out.println("Sending message to remote LoggingActor.");

        // Use it in the same manner as any locally created one
        actor.tell(new SimpleMessage("Hello Remote World!"), ActorRef.noSender());

    }
}