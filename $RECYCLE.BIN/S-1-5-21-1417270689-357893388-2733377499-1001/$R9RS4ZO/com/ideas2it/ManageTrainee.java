import java.util.List; 
import java.util.Date;
import java.util.Iterator; 

import com.ideas2it.model.Trainee;
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageTrainee {

    private static SessionFactory factory;

    public SessionFactory getFactory() throws ExceptionInInitializerError {

        try {

            factory = new Configuration().configure().addPackage(com.ideas2it.model.Trainee).addAnnotatedClass(Trainee.class).buildSessionFactory(); 
                   
        } catch (Throwable ex) { 

            throw new ExceptionInInitializerError(ex); 
        }
        return factory;
    }
}