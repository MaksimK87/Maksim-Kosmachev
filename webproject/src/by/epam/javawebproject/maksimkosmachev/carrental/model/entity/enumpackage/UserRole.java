package by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage;

public enum UserRole {

    CLIENT(0), MANAGER(1);

    private int role;

    UserRole(int role) {
        this.role = role;
    }

    public int getRoleCode() {
        return role;
    }

    public void setRoleCode(int role) {
        this.role = role;
    }

    public static UserRole getRoleByCode(int code) {
        for (UserRole role : UserRole.values()) {
            if (role.getRoleCode() == (code)) {
                return role;
            }
        }
        return CLIENT;
    }
}