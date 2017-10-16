package core.model;

/**
 * Created by chathuranga on 7/17/2015. SFAAndroid
 *
 * @email chathunbandara@gmail.com
 * @phone +94716271637
 */
public class Log {

    private String date;
    private int id;

    public Log(String date, int id) {
        this.date = date;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
