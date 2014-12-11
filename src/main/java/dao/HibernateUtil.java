package dao; /**
 * Created by Андрей on 06.11.2014.
 */

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class  HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;
  static {
        try {
            Properties properties=new Properties();
            properties.setProperty("user","root");
            properties.setProperty("password","root");
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","utf8");
            //creates the session factory from hibernate.cfg.xml
            Configuration configuration = (new Configuration().setProperties(properties));
            //configuration.addProperties(properties);
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}