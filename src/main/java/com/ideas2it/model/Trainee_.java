package com.ideas2it.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Trainee.class)
public abstract class Trainee_ extends com.ideas2it.model.Employee_ {

	public static volatile SingularAttribute<Trainee, String> currentTask;
	public static volatile SingularAttribute<Trainee, Integer> traineeId;
	public static volatile SingularAttribute<Trainee, String> currentTechknowledge;

	public static final String CURRENT_TASK = "currentTask";
	public static final String TRAINEE_ID = "traineeId";
	public static final String CURRENT_TECHKNOWLEDGE = "currentTechknowledge";

}

