public class User {

    @Column(name = "name")
    private String name;

    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Override
    public String toString() {
        return "User{name='" + name
                + "', id='" + id
                + "', email='" + email + "'}";
    }
}