package org.sumanta.jbosscc.cluster.client;

import java.util.Properties;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
import org.sumanta.jbosscc.api.RemoteStateless;

public class RemoteEJBClient {
    /**
     * The application may be initialized either via the properties files jndi.properties and jboss-ejb-client.properties, or purely programmatically without any properties files. If this is set to
     * true, an existing jboss-ejb-client.properties file may lead to conflicts. Also, the initial servers must all be up and running (unlike when using jboss-ejb-client.properties).
     */
    private static final boolean PROGRAMMATIC_INITIALIZATION = true;

    private Context context;

    public RemoteEJBClient() throws NamingException {
        context = initializeJNDIContext();

        // Initialize EJB client context by setting a new context selector
        if (PROGRAMMATIC_INITIALIZATION) {
            //initializeEJBClientContext();
        }
    }

    private Context initializeJNDIContext() throws NamingException {
        Context jndiContext;

        if (PROGRAMMATIC_INITIALIZATION) {
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiContext = new InitialContext(jndiProperties);
        } else {
            jndiContext = new InitialContext();
        }
        return jndiContext;
    }

    private void initializeEJBClientContext() throws NamingException {
        Random random = new Random();
        Properties properties = new Properties();

        properties.put("endpoint.name", "client-endpoint");

        properties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        IpaddressHolder.read();
        properties.put("remote.connections", "default");
        properties.put("remote.connection.default.host", IpaddressHolder.ipa.get(random.nextInt((IpaddressHolder.ipa.size()))));
        properties.put("remote.connection.default.port", "4447");
        properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
        properties.put("remote.connection.ejb.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
        properties.put("remote.connection.default.username", "app");
        properties.put("remote.connection.default.password", "Pass@1234");

        
        PropertiesBasedEJBClientConfiguration configuration = new PropertiesBasedEJBClientConfiguration(properties);
         
        final ContextSelector<EJBClientContext> ejbClientContextSelector = new ConfigBasedEJBClientContextSelector(configuration);
        
        final ContextSelector<EJBClientContext> previousSelector = EJBClientContext.setSelector(ejbClientContextSelector);
         

        /*EJBClientConfiguration ejbClientConfiguration = new PropertiesBasedEJBClientConfiguration(properties);
        ContextSelector<EJBClientContext> contextSelector = new ConfigBasedEJBClientContextSelector(ejbClientConfiguration);
        //EJBClientContext.setSelector(contextSelector);
*/    }

    public RemoteStateless lookupRemoteStatelessBean() throws NamingException {
        final String appName = "cluster-ear";
        final String moduleName = "cluster"; // module-name in ejb-jar.xml
        final String distinctName = ""; // jboss:distinct-name in jboss-ejb3.xml
        final String beanName = "ClusteredStatelessBean";

        final String jndiName = "ejb:" + appName + '/' + moduleName + '/' + distinctName+'/' + beanName + '!' + RemoteStateless.class.getName();
        System.out.println(jndiName);
        Properties properties = new Properties();
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        properties.put("jboss.naming.client.ejb.context", "true");
        Context context = new InitialContext(properties);
        return (RemoteStateless) context.lookup(jndiName);
    }

}
