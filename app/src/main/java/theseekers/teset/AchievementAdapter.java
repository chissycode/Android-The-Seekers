package theseekers.teset;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * Created by chule_000 on 2016/3/9.
 */
public class AchievementAdapter extends ArrayAdapter<Achievement>{

    private int resourceID;
    public AchievementAdapter(Context context, int textViewResourceId,List<Achievement> objects)
    {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        resourceID=textViewResourceId;
    }

    public View getView(int position,View convertView,ViewGroup parent)
    {
        Achievement achievement=getItem(position);
        View view;
        if(convertView==null)
        {
            view=LayoutInflater.from(getContext()).inflate(resourceID, null);
        }
        else
        {
            view=convertView;
        }
        TextView achieveweek=(TextView)view.findViewById(R.id.week);
        TextView achievemonth=(TextView)view.findViewById(R.id.month);
        TextView achievedate=(TextView)view.findViewById(R.id.date);
        TextView achievehour=(TextView)view.findViewById(R.id.hour);
        TextView achieve=(TextView)view.findViewById(R.id.achievement);
        achieveweek.setText(achievement.getweek());
        achievemonth.setText(achievement.getmonth());
        achievedate.setText(achievement.getdate());
        achievehour.setText(achievement.gethour());
        achieve.setText(achievement.getachieve());
        return view;
    }

}
