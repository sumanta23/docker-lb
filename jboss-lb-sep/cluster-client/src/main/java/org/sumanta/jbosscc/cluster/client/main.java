package org.sumanta.jbosscc.cluster.client;

import org.sumanta.jbosscc.api.RemoteStateless;

public class main {

    public static void main(String[] args) {
        try {

            RemoteEJBClient rec=new RemoteEJBClient();
            
            RemoteStateless ejb =  rec.lookupRemoteStatelessBean();

            System.out.println("nodenamd:" + ejb.getNodeName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
