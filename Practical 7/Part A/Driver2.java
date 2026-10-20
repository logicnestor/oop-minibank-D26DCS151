public class Driver2 {

    public static void main(String[] args) throws Exception {

        String[] headers = {
            "name",
            "id",
            "email"
        };

        String[] data = {
            "Riya",
            "A07",
            "riya@gmail.com"
        };

        User user =
                Mapper.populate(
                    User.class,
                    headers,
                    data
                );

        System.out.println(user);
    }
}