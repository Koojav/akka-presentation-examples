package com.workfront.examples.akka.webcrawler.messages;

import org.jsoup.select.Elements;

public class HeroesReadyToProcessMessage
{
    public final Elements heroHtmlRows;

    public HeroesReadyToProcessMessage(Elements heroHtmlRows)
    {
        this.heroHtmlRows = heroHtmlRows;
    }
}
