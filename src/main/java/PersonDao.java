import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;
// https://examples.javacodegeeks.com/enterprise-java/hibernate/hibernate-jpa-dao-example/
public class PersonDao implements DaoInterface<Person, Integer> {
    private Session session;
    private Transaction transaction;

    public PersonDao() {
    }

    public Session openCurrentSession() {
        session = getSessionFactory().openSession();
        return session;
    }

    public Session openCurrentSessionwithTransaction() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeCurrentSession() {
        session.close();
    }

    public void closeCurrentSessionwithTransaction() {
        transaction.commit();
        session.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Person.class);
        configuration.configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
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
    public List<Person>findAll() {
        List<Person> persons = (List<Person>)getSession().createQuery("from Person").list();
        return persons;
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
