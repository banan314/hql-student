import model.Laptop;
import model.Student;

import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.metamodel.EntityType;

import java.util.Map;
import java.util.Random;

public class Main {
    private static SessionFactory ourSessionFactory;
    private static final Configuration configuration;

    static {
        try {
            configuration = new Configuration();
            configuration.configure();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        ourSessionFactory = configuration.buildSessionFactory();
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        lookAround();
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            session.beginTransaction();

            Random random = new Random();
            for(int i = 1; i <= 50; i++) {
                Student s = new Student();
                s.setRollNo(i);
                s.setName("Name " + i);
                s.setMarks(random.nextInt(6) + 1);
                session.save(s);
            }

            session.getTransaction().commit();

            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for(EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from "+entityName);
                System.out.println("executing: "+query.getQueryString());
                for(Object o : query.list()) {
                    System.out.println("  "+o);
                }
            }
        } finally {
            session.close();
        }
    }

    private static void lookAround() {
        configuration.addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    }
}