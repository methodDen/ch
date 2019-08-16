import io.javalin.Context;
import io.javalin.apibuilder.CrudHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PersonController implements CrudHandler {
    private static PersonDao personDao;
    private Logger logger;

    public PersonController() {
        logger = LoggerFactory.getLogger(this.getClass());
        try {
            personDao = new PersonDao();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured creating Dao");
        }

    }

//    public void delete(Integer id) {
//        personDao.openCurrentSessionwithTransaction();
//        Person p = personDao.findById(id);
//        personDao.delete(p);
//        personDao.closeCurrentSessionwithTransaction();
//    }


//
//    public void deleteAll() {
//        personDao.openCurrentSessionwithTransaction();
//        personDao.deleteAll();
//        personDao.closeCurrentSessionwithTransaction();
//    }

    public PersonDao personDao() {
        return personDao;
    }
//    public void persist (Person p) {
//
//    }
    @Override
    public void create(@NotNull Context context) {
        Person person = context.bodyAsClass(Person.class);
        try {
            personDao.openCurrentSessionwithTransaction();
            personDao.persist(person);
            personDao.closeCurrentSessionwithTransaction();
            context.status(Constants.CREATED_201);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured creating a record");
        }
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        int personId = Integer.valueOf(s);
        try{
            personDao.openCurrentSessionwithTransaction();
            Person p = personDao.findById(personId);
            personDao.delete(p);
            personDao.closeCurrentSessionwithTransaction();
            context.status(Constants.OK_200);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured deleting a record");
            context.status(Constants.INTERNAL_SERVER_ERROR);
        }
    }
//    public List<Person> findAll() {
//        personDao.openCurrentSession();
//        List<Person> personList = personDao.findAll();
//        personDao.closeCurrentSession();
//        return personList;
//    }
    @Override
    public void getAll(@NotNull Context context) {
        try {
            personDao.openCurrentSession();
            context.json(personDao().findAll());
            personDao().closeCurrentSession();
            context.status(Constants.OK_200);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured getting records");
            context.status(Constants.INTERNAL_SERVER_ERROR);
        }
    }
//    public Person findById(Integer id) {
//        personDao.openCurrentSession();
//        Person p = personDao.findById(id);
//        personDao.closeCurrentSession();
//        return p;
//    }
    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        int personId = Integer.valueOf(s);
        try {
            personDao.openCurrentSession();
            Person p = personDao.findById(personId);
            if (p != null){
                context.json(p);
            } else {
                context.status(Constants.NOT_FOUND);
            }
            personDao().closeCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured getting a record");
            context.status(Constants.INTERNAL_SERVER_ERROR);
        }
    }
//    public void update (Person p)
//    {
//        personDao.openCurrentSessionwithTransaction();
//        personDao.update(p);
//        personDao.closeCurrentSessionwithTransaction();
//    }
    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        int personId = Integer.valueOf(s);
        Person newPerson = context.bodyAsClass(Person.class);
        newPerson.setId(personId);
        try {
            personDao().openCurrentSessionwithTransaction();
            personDao().update(newPerson);
            personDao().closeCurrentSessionwithTransaction();
            context.status(Constants.OK_200);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured updating a record");
            context.status(Constants.INTERNAL_SERVER_ERROR);
        }
    }

}
