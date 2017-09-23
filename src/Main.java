import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import lombok.val;
import model.Laptop;
import model.Student;

import java.util.List;
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

            Query query = session.createQuery("from Student where marks > 3");
            List<Student> goodStudents = query.list();

            for(val s : goodStudents) {
                System.out.println(s);
            }

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    private static void lookAround() {
        configuration.addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    }

    public static Session getSession() throws HibernateException {
        ourSessionFactory = configuration.buildSessionFactory();
        return ourSessionFactory.openSession();
    }
}