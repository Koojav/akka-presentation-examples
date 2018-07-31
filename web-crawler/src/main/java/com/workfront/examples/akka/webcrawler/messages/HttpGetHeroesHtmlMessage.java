package com.workfront.examples.akka.webcrawler.messages;

public class HttpGetHeroesHtmlMessage
{
    public final String url;

    public HttpGetHeroesHtmlMessage(String url)
    {
        this.url = url;
    }
}
