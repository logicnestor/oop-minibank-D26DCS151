public class SignupForm {

    @NotBlank
    @MaxLength(20)
    private String username;

    @NotBlank
    @MaxLength(50)
    private String email;

    @MaxLength(15)
    private String mobile;

    public SignupForm(String username, String email, String mobile) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
    }
}