import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Properties;
// https://examples.javacodegeeks.com/enterprise-java/hibernate/hibernate-jpa-dao-example/
public class PersonDAO implements DaoInterface<Person, Integer> {
    private Session session;
    private Transaction transaction;
    private static SessionFactory sessionFactory;
    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings = Settings.getSettings();
                configuration.addAnnotatedClass(Person.class);
                configuration.setProperties(settings);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public PersonDAO() {
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    } // noodles

    public Transaction getTransaction() {
        return transaction;
    } // noodles

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        PersonDAO.sessionFactory = sessionFactory;
    }

    public Session openSession() { // noodles
        session = getSessionFactory().openSession();
        return session;
    }
    public Session openSessionWithTransaction() { // noodles
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        session.close();
    }
    public void closeSessionWithTransaction() {
        transaction.commit();
        session.close();
    }


    @Override
    public void persist(Person entity) {
        getSession().save(entity);
    }

    @Override
    public void update(Person entity) {
        getSession().update(entity);
    }

    @Override
    public Person findById(Integer id) {
        Person person = (Person) getSession().get(Person.class, id);
        return person;
    }

    @Override
    public void delete(Person entity) {
        getSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> findAll() {
        return (List<Person>)getSession().createQuery("from Person");
    }

    @Override
    public void deleteAll() {
        List<Person> personList = findAll();
        for (Person p : personList)
        {
            delete(p);
        }
    }
}
