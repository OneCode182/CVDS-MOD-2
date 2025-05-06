package eci.cvds.mod2.util;

public enum Role {
    STUDENT("Student"),
    TEACHER("Teacher"),
    ADMINISTRATIVE("Administrative"),
    GENERAL_SERVICES("General Services");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
