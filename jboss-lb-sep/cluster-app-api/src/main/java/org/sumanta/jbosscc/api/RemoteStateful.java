package org.sumanta.jbosscc.api;

public interface RemoteStateful
{
    int getAndIncrementCounter();
    String getNodeName();

    void activate();
    void passivate();
    void destroy();
}
