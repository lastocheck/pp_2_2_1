package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("series1", 1)));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("series2", 2)));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("series3", 3)));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car("series4", 4)));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar());
            System.out.println();
        }

        //getting user by car
        String series = "series3";
        int model = 3;
//      User user = userService.getUserByCar(series, model).orElse(null);
        User user = userService.getUserByCar(series, model);
        String result = user == null ? "not found" : ": " + user.toString();

        System.out.printf("User with series %s and model %d %s%n", series, model, result);

        context.close();
    }
}
