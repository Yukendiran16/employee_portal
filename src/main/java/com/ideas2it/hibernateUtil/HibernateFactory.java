package com.ideas2it.hibernateUtil;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
 
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException; 
import org.hibernate.SessionFactory;

/*
 *
 * <h1> HibernateFactory </h1>
 * <p>
 * The class HibernateFactory is used to generate factory object
 * for create sessions for connect hibernate sql database.
 * </p>
 * @author   Yukendiran K
 * @version  1.0
 * @since    24.8.2022
 *
 */
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