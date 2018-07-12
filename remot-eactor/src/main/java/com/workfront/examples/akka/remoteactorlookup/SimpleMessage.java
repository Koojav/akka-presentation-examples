package com.workfront.examples.akka.remoteactorlookup;

import java.io.Serializable;

// Needs to implement Serializable if is being passed between Actor Systems in different JVMs
public class SimpleMessage implements Serializable
{
    // Reminder: Messages in Actor Model are immutable
    public final String content;

    public SimpleMessage(String content)
    {
        this.content = content;
    }

    // Serializable interface methods

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
    }
}
