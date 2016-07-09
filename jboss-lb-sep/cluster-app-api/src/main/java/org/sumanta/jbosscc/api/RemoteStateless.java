package org.sumanta.jbosscc.api;

import javax.ejb.Remote;

@Remote
public interface RemoteStateless
{
    String getNodeName();
}
