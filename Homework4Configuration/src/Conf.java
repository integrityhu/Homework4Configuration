import java.io.Writer;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.MergeCombiner;
import org.apache.commons.configuration.tree.OverrideCombiner;

/*
 * http://stackoverflow.com/questions/19138637/apache-commons-configuration-duplicate-entries-with-enabled-auto-save
 */
public class Conf {

    private static final String XML_FILE_NAME = "c:/temp/demo-config.xml";
    private static AbstractConfiguration xmlConf;
    private static AbstractConfiguration dbConfig;

    public static Configuration getConfiguration() {
        Configuration result = null;
        try {
            xmlConf = new XMLConfiguration("appconf.xml");
            // ConfigConnectionFactory.getPGConnection("localhost","conf","5432","sa","sa",false);
            DataSource datasource = ConfigConnectionFactory.getMySQLDataSource("localhost", "3306", "conf", "sa", "sa");
            dbConfig = new DatabaseConfiguration(datasource, "config", "confkey", "value");
            XMLConfiguration xml2Conf = new XMLConfiguration("appconf2.xml");
            
            MergeCombiner combiner = new MergeCombiner();            
            CombinedConfiguration combinedConf = new CombinedConfiguration(
                    combiner) {

                private static final long serialVersionUID = 1L;

                @Override
                public void setProperty(String key, Object value) {
                    Configuration conf = getConfiguration("db");
                    Object o = conf.getProperty(key);
                    System.out.println("Value in db config in db "+key+": "+o);
                    conf.setProperty(key, value);
                }
            };
            
            combinedConf.addConfiguration(dbConfig,"db");
            combinedConf.addConfiguration(xmlConf,"xml");
            combinedConf.addConfiguration(xml2Conf,"xml2");
            
            //String interpolatedAuthScope = combinedConf.interpolatedConfiguration().getString("auth.scope");            
            //System.out.println("interpolated auth.scope: "+interpolatedAuthScope);
            System.out.println("origin appconfig.xml auth.scope:"+xmlConf.getString("auth.scope"));
            System.out.println("origin appconfig.xml dns.synch:"+xmlConf.getString("dns.synch"));
            System.out.println("origin appconfig2.xml auth.scope: "+combinedConf.getConfiguration("xml2").getString("auth.scope"));
            System.out.println("combined dns.synch:"+combinedConf.getString("dns.synch"));
            System.out.println("combined dns.border:"+combinedConf.getProperty("dns.border"));
            System.out.println("combined dns.schema:"+combinedConf.getProperty("dns.schema"));
            System.out.println("combined auth.scope: "+combinedConf.getString("auth.scope"));
            combinedConf.setProperty("auth.scope", "dynamic");
            xmlConf.addProperty("mapped.by", "me");
            
            System.out.println("combined mapped.by: "+combinedConf.getString("mapped.by"));
            combinedConf.clearTree("dns");
            System.out.println("combined dns.border after clearTree: "+combinedConf.getProperty("dns.border"));
            //This will enough
            xmlConf.copy(dbConfig);
            System.out.println("copy dbConfig to appconfig.xml auth.scope: "+xmlConf.getString("auth.scope"));
            combinedConf.clearTree("auth.scope");
            System.out.println("get auth.scope after clearTree: "+combinedConf.getString("auth.scope"));
            combinedConf.setProperty("auth.scope", null);
            System.out.println("get auth.scope after set null: "+combinedConf.getString("auth.scope"));
            xmlConf.clearProperty("mapped.by");
            XMLConfiguration  interpolatedConf = (XMLConfiguration )xmlConf.interpolatedConfiguration();
            interpolatedConf.save(XML_FILE_NAME);
            System.out.println("interpolated config saved to "+XML_FILE_NAME);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
