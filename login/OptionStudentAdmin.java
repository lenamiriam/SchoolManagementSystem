package login;

public enum OptionStudentAdmin {
    Student, Admin;

    private OptionStudentAdmin(){}

    public String value() {
        return name();
    }

    public static OptionStudentAdmin fromValue(String v) {
        return valueOf(v);
    }
}
