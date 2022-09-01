package com.ideas2it.hibernateUtil;

import java.util.Date;
import java.util.Iterator; 
import java.util.List; 

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
 

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateFactory {

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