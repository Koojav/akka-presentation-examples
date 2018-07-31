package com.workfront.examples.akka.webcrawler.messages;

import java.util.ArrayList;

public class ProcessHeroMessage
{
    public final String heroHTMLrow;

    public ProcessHeroMessage(String heroHTMLrow)
    {
        this.heroHTMLrow = heroHTMLrow;
    }
}
