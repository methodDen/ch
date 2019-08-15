import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
//
//        PersonDAO personDAO = new PersonDAO();
//        Session session = PersonDAO.getSessionFactory().openSession();
        Session session = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();
        Transaction t = session.beginTransaction();


        File imagePath = new File("C:\\ua.jpg");
        byte[] imageInBytes = new byte[(int)imagePath.length()];
        FileInputStream fileInputStream = new FileInputStream(imagePath);
        fileInputStream.read(imageInBytes);
        fileInputStream.close();

        Person person = new Person();
        person.setFirstName("Adik and Saya");
        person.setLastName("Absatov");
        person.setImage(imageInBytes);

        session.save(person);
        t.commit();
        session.close();
//        personDAO.persist(person);


    }
}
