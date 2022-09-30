package com.ideas2it.hibernateUtil;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

    private HibernateFactory() {
    }
    public static SessionFactory getFactory() throws ExceptionInInitializerError {

        return new Configuration().configure().addPackage("com.ideas2it.model")
                .addAnnotatedClass(Trainer.class).addAnnotatedClass(Trainee.class).buildSessionFactory();
    }
}