package com.ideas2it.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Trainer.class)
public abstract class Trainer_ extends com.ideas2it.model.Employee_ {

	public static volatile ListAttribute<Trainer, Trainee> trainees;
	public static volatile SingularAttribute<Trainer, String> achievement;
	public static volatile SingularAttribute<Trainer, String> currentProject;
	public static volatile SingularAttribute<Trainer, Integer> trainerId;

	public static final String TRAINEES = "trainees";
	public static final String ACHIEVEMENT = "achievement";
	public static final String CURRENT_PROJECT = "currentProject";
	public static final String TRAINER_ID = "trainerId";

}

