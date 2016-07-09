package org.sumanta.jbosscc.cluster.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class IpaddressHolder {

    public static String proppath = "/tmp/ipa.properties";
    public static List<String> ipa = new ArrayList<String>();

    public static void read() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(proppath);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            String ips = prop.getProperty("ipa");

            List<String> list;
            if (ips.contains(",")) {
                list = new ArrayList<String>(Arrays.asList(ips.split(",")));
            } else {
                list = new ArrayList<String>();
                list.add(ips);
            }
            ipa = list;

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
