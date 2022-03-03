package com.swl.s3.poc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	private static SessionFactory factory;

	public static void main(final String[] str) throws Exception {


		final StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		final Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		final Session session = factory.openSession();
		final Transaction t = session.beginTransaction();

		final Employee employee = new Employee();
		employee.setId(1);
		employee.setName("Nelson");
		employee.setSalary(10);

		session.save(employee);
		
		//-----------------
		final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		final S3UploadBA s3UploadBA = (S3UploadBA) context.getBean("s3UploadBA");
		s3UploadBA.createAndPopulateSimpleBucket();
		//-----------------
		
		t.commit();
		
		System.out.println("successfully saved");
		factory.close();
		session.close();
		
		
	}
}
