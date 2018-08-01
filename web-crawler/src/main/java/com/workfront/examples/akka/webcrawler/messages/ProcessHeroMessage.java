package com.workfront.examples.akka.webcrawler.messages;

import org.jsoup.nodes.Element;

public class ProcessHeroMessage
{
    public final Element heroHtmlRow;

    public ProcessHeroMessage(Element heroHtmlRow)
    {
        this.heroHtmlRow = heroHtmlRow;
    }
}
