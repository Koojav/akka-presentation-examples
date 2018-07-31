package com.workfront.examples.akka.webcrawler.messages;

import java.util.ArrayList;

public class HeroesReadyToProcessMessage
{
    public final ArrayList<String> heroHTMLrows;

    public HeroesReadyToProcessMessage(ArrayList<String> heroHTMLrows)
    {
        this.heroHTMLrows = heroHTMLrows;
    }
}
