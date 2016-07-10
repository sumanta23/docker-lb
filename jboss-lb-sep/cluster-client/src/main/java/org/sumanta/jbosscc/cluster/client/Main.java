package org.sumanta.jbosscc.cluster.client;

import org.sumanta.jbosscc.api.RemoteStateless;

public class Main {

    public static void main(String[] args) {
        try {

            RemoteEJBClient rec=new RemoteEJBClient();
            
            String jndi = "ejb:cluster-ear/cluster/ClusteredStatelessBean!org.sumanta.jbosscc.api.RemoteStateless";
            RemoteStateless ejb = rec.locateEJB(jndi);

            //RemoteStateless ejb = rec.locateEJBStateless(RemoteStateless.class, "cluster-ear", "cluster", "ClusteredStatelessBean", "");

            System.out.println("nodenamd:" + ejb.getNodeName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
