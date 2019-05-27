package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.UserRole;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.UserIllegalValueException;
import org.apache.log4j.Logger;

import java.util.Objects;

public class User extends Entity {

    private static final Logger logger = Logger.getLogger(User.class);

    private String login;
    private String password;
    private String name;
    private String surname;
    private String passportNumber;
    private String phoneNumber;
    UserRole userRole;

    public User(int id, String login, String password, String name, String surname, String passportNumber,
                String phoneNumber, UserRole userRole) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }

    public User() {
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login != null) {
            this.login = login;
        } else {
            logger.error("Incorrect value of login ", new UserIllegalValueException());
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = password;
        } else {
            logger.error("Incorrect value of password ", new UserIllegalValueException());
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) &&
                password.equals(user.password) &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                passportNumber.equals(user.passportNumber) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, surname, passportNumber, phoneNumber, userRole);
    }

    @Override
    public String toString() {
        return super.toString() + "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}

