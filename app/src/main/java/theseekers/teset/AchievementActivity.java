package theseekers.teset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by chule_000 on 2016/3/9.
 */
public class AchievementActivity extends Activity{

    private List<Achievement> achievementlist=new ArrayList<Achievement>();
    static int line;//保存解锁地点数
    private ImageView map;
    private ImageView setting;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKiller.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.achievement);
        initAchievement();
        AchievementAdapter adapter=new AchievementAdapter(AchievementActivity.this,R.layout.achievement_item,achievementlist);
        ListView listview=(ListView)findViewById(R.id.achievement_list);
        listview.setAdapter(adapter);
        TextView textview=(TextView)findViewById(R.id.number);
        textview.setText("已经解锁了"+line+"个地点！");
        map=(ImageView)findViewById(R.id.first);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(AchievementActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
        setting=(ImageView)findViewById(R.id.third);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(AchievementActivity.this,SettingPage.class);
                startActivity(intent2);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityKiller.removeActivity(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String[] input()throws IOException{
        FileInputStream in=null;
        BufferedReader reader=null;
        String[] content=null;
        int i=0;//获得行数
        try{
            in=openFileInput("achievement");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                content=line.split(" ");//按照空格分离字符串
                i++;
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if(reader!=null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    private void initAchievement(){
        try
        {
            String[] content=input();
            String[] data=new String[5];
            if(content!=null) {
                int number = content.length;
                line = number / 5;
                int i = number;
                while (i > 0) {
                    data[4] = content[i - 1];
                    data[3] = content[i - 2];
                    data[2] = content[i - 3];
                    data[1] = content[i - 4];
                    data[0] = content[i - 5];
                    i = i - 5;
                    Achievement achievement = new Achievement(data[0], data[1], data[2], data[3], data[4]);
                    achievementlist.add(achievement);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /*achievement one=new achievement("Tuesday","19~21h","Oct","21st","白石桥");
        achievementlist.add(one);
        achievement two=new achievement("Wednesday","13~15h","May","3rd","不高山");
        achievementlist.add(two);
        achievement three=new achievement("Saturday","7~9h","Apr","1st","青春广场");
        achievementlist.add(three);
        achievement four=new achievement("Friday","21~23h","Jun","15th","图书馆");
        achievementlist.add(four);
        achievement five=new achievement("Sunday","9~11h","Dec","22nd","东门");
        achievementlist.add(five);
        achievement six=new achievement("Monday","13~15h","Sep","8th","综合楼");
        achievementlist.add(six);
        achievement seven=new achievement("Thursday","19~21h","Feb","12th","网球场");
        achievementlist.add(seven);*/

    }


}
