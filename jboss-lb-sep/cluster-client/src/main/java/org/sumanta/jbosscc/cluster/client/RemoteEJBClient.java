package org.sumanta.jbosscc.cluster.client;

import java.util.Properties;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.EJBClient;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.StatelessEJBLocator;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

/**
 *  * Class EJBLocator is the class to connect with EJB  *
 */
public class RemoteEJBClient {

    @SuppressWarnings("unchecked")
    public static <T> T locateEJB(String jndi) throws NamingException {
        Properties clientProperties = new Properties();
        Random random = new Random();
        IpaddressHolder.read();
        
        clientProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        clientProperties.put("remote.connections", "default");
        clientProperties.put("remote.connection.default.port", "4447");
        clientProperties.put("remote.connection.default.host",  IpaddressHolder.ipa.get(random.nextInt((IpaddressHolder.ipa.size()))));
        clientProperties.put("remote.connection.default.username", "app");
        clientProperties.put("remote.connection.default.password", "Pass@1234");
        clientProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
        clientProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT","false");

        EJBClientContext.setSelector(new ConfigBasedEJBClientContextSelector(new PropertiesBasedEJBClientConfiguration(clientProperties)));

        Properties properties = new Properties();
        
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        Context context = new InitialContext(properties);
        return (T) context.lookup(jndi);
    }

    /**
     * Method locateEJBStateless locates an Stateless EJB for the given parameters
     * 
     * @author Sumanta
     * @since 1.0
     * @param viewType
     *              - the view type
     * @param appName
     *              - the application name
     * @param moduleName
     *              - the module name
     * @param beanName
     *              - the bean name
     * @param distinctName
     *              - the distinct name
     * @return an instance of EJB
     */
    public static <T> T locateEJBStateless(Class<T> viewType, String appName, String moduleName, String beanName, String distinctName) {
        Properties properties = new Properties();
        Random random = new Random();
        IpaddressHolder.read();
        
        properties.put("endpoint.name", "client-endpoint");
        properties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        properties.put("remote.connections", "default");
        properties.put("remote.connection.default.port", "4447");
        properties.put("remote.connection.default.host",  IpaddressHolder.ipa.get(random.nextInt((IpaddressHolder.ipa.size()))));
        properties.put("remote.connection.default.username", "app");
        properties.put("remote.connection.default.password", "Pass@1234");
        properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
        properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT","false");

        EJBClientContext.setSelector(new ConfigBasedEJBClientContextSelector(new PropertiesBasedEJBClientConfiguration(properties)));
        StatelessEJBLocator<T> locator = new StatelessEJBLocator<T>(viewType, appName, moduleName, beanName, distinctName);
        T ejb = EJBClient.createProxy(locator);
        return ejb;
    }
}