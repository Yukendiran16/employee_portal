package com.ideas2it;

import java.util.List; 
import java.util.Date;
import java.util.Iterator; 

import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageTrainer {

    private static SessionFactory factory;

    public static SessionFactory getFactory() throws ExceptionInInitializerError {

        try {
            factory = new Configuration().configure().addPackage("com.ideas2it.model").addAnnotatedClass(Trainer.class).addAnnotatedClass(Trainee.class).buildSessionFactory(); 
        } catch (Exception ex) { 
            throw ex;
        }
        return factory;
    }
}