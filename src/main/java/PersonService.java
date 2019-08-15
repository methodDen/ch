import java.util.List;

public class PersonService {
    private static PersonDaoSecond personDaoSecond;

    public PersonService() {
        personDaoSecond = new PersonDaoSecond();
    }

    public void persist (Person p) {
        personDaoSecond.openCurrentSessionwithTransaction();
        personDaoSecond.persist(p);
        personDaoSecond.closeCurrentSessionwithTransaction();
    }

    public void update (Person p)
    {
        personDaoSecond.openCurrentSessionwithTransaction();
        personDaoSecond.update(p);
        personDaoSecond.closeCurrentSessionwithTransaction();
    }
    public Person findById(Integer id) {
        personDaoSecond.openCurrentSession();
        Person p = personDaoSecond.findById(id);
        personDaoSecond.closeCurrentSession();
        return p;
    }

    public void delete(Integer id) {
        personDaoSecond.openCurrentSessionwithTransaction();
        Person p = personDaoSecond.findById(id);
        personDaoSecond.delete(p);
        personDaoSecond.closeCurrentSessionwithTransaction();
    }

    public List<Person> findAll() {
        personDaoSecond.openCurrentSession();
        List<Person> personList = personDaoSecond.findAll();
        personDaoSecond.closeCurrentSession();
        return personList;
    }

    public void deleteAll() {
        personDaoSecond.openCurrentSessionwithTransaction();
        personDaoSecond.deleteAll();
        personDaoSecond.closeCurrentSessionwithTransaction();
    }

    public PersonDaoSecond personDaoSecond() {
        return personDaoSecond;
    }
}
