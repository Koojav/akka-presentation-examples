package com.workfront.examples.akka.actorcreation;

public class SimpleMessage
{
    // Reminder: Messages in Actor Model are immutable
    public final String content;

    public SimpleMessage(String content)
    {
        this.content = content;
    }
}
