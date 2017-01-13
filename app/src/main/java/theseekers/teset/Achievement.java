package theseekers.teset;

/**
 * Created by chule_000 on 2016/3/9.
 */
public class Achievement {

    private String week;
    private String hour;
    private String month;
    private String date;
    private String achieve;


    public Achievement(String week,String hour,String month,String date,String achieve)
    {
        this.week=week;
        this.hour=hour;
        this.month=month;
        this.date=date;
        this.achieve=achieve;
    }

    public Achievement()
    {
        ;
    }

    public String getweek(){
        return week;
    }

    public String getmonth(){
        return month;
    }

    public String gethour(){
        return hour;
    }

    public String getdate(){
        return date;
    }

    public String getachieve(){
        return achieve;
    }

}
