package bryan.mobilepaindiary.ObjectClass;

/**
 * Created by Bryanyhy on 27/4/16.
 */
// registartion class for storing info from rest server
public class Registration {

    private String password;
    private String regDatetime;
    private String username;

    public Registration (String password, String regDatetime, String username)
    {
        this.password = password;
        this.regDatetime = regDatetime;
        this.username = regDatetime;
    }

}
