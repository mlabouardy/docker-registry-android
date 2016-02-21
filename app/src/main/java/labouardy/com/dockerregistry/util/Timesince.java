package labouardy.com.dockerregistry.util;

import java.util.Date;

/**
 * Created by mlabouardy on 21/02/16.
 */
public class Timesince {
    private static Timesince instance;

    private Timesince(){}

    public static Timesince getInstance(){
        if(instance==null)
            instance=new Timesince();
        return instance;
    }

    public String calculateSince(Date time){
        String since="";
        Date cTime=new Date();
        long sinceMin=Math.round((cTime.getTime()-time.getTime())/60000);
        if(sinceMin==0){
            long sinceSec=Math.round((cTime.getTime()-time.getTime())/1000);
            if(sinceSec<10){
                since="less than 10 seconds ago";
            }else if(sinceSec<20){
                since="less than 20 seconds ago";
            }else{
                since="half a minute ago";
            }
        }else if(sinceMin==1){
            long sinceSec=Math.round((cTime.getTime()-time.getTime())/1000);
            if(sinceSec==30){
                since="half a minute ago";
            }else if(sinceSec<60){
                since="less than a minute ago";
            }else{
                since="1 minute ago";
            }
        }else if(sinceMin<45){
            since=sinceMin+" minutes ago";
        }else if(sinceMin>44 && sinceMin<60){
            since="about 1 hour ago";
        }else if(sinceMin<1440){
            long sinceHr=Math.round(sinceMin/60);
            if(sinceHr==1){
                since="about 1 hour ago";
            }else{
                since="about "+sinceHr+" hours ago";
            }
        }else if(sinceMin>1439 && sinceMin<2880){
            since="1 day ago";
        }else{
            long sinceDay=Math.round(sinceMin/1440);
            since=sinceDay+" days ago";
        }
        return since;
    }
}
