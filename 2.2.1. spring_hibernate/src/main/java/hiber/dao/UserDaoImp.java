package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User u LEFT JOIN FETCH u.car");
      return query.getResultList();
   }

   @Override
   public Optional<User> getUserByCar(String model, int series) {
      String hql = "from User u where u.car.model = :model AND u.car.series = :series"; //also use join fetch here??
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      query.setMaxResults(1);

      try {
         User user = query.getSingleResult();
         return Optional.of(user);
      } catch (NoResultException e) {
         return Optional.empty();
      }
   }
}
