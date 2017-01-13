package theseekers.teset;

/**
 * Created by xkgoo on 2016/3/30.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SettingPage extends Activity{
    private ImageView map;
    private ImageView achievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKiller.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        ImageView Exit = (ImageView) findViewById(R.id.Exit);

        Exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityKiller.finishAll();
                Toast.makeText(SettingPage.this, "Quit the SEEKER!", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView about=(ImageView) findViewById(R.id.AboutUs);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(SettingPage.this, AboutUs.class);
                startActivity(inten);
            }
        });

        Button ResetMap = (Button) findViewById(R.id.ResetMap);
        ResetMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                File file1 = new File(getFilesDir(), "achievement");
                if (file1.exists()) {
                    file1.delete();
                    AchievementActivity.line = 0;
                    Toast.makeText(SettingPage.this, "Reset achievement successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingPage.this, "This operation cannot be done!", Toast.LENGTH_SHORT).show();
                }
                File file = new File(getFilesDir(), "mapdata");
                if (file.exists()) {
                    file.delete();
                    Toast.makeText(SettingPage.this, "Reset map successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingPage.this, "This operation cannot be done!", Toast.LENGTH_SHORT).show();
                }


            }
        });

      ImageView function=(ImageView) findViewById(R.id.Function);
        function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(SettingPage.this, Introduce.class);
                startActivity(inten);
            }
        });




        map=(ImageView)findViewById(R.id.first);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingPage.this,MainActivity.class);
                startActivity(intent);
            }
        });

        achievement=(ImageView)findViewById(R.id.second);
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SettingPage.this, AchievementActivity.class);
                startActivity(intent1);
            }
        });




        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setChecked(loadMap());
        switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    saveScreen(true);
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    saveScreen(false);
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveScreen(boolean i)
    {
        File file = new File("screen");
        file.delete();
        String inputText="";
        if(i==true){
            inputText="1";
        }else{
            inputText="0";
        }
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("screen", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveMap(boolean i)
    {
        File file = new File("map");
        file.delete();
        String inputText="";
        if(i==true){
            inputText="1";
        }else{
            inputText="0";
        }
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("map", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loadScreen()
    {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        int i=0;
        try {
            in = openFileInput("screen");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                if(Integer.parseInt(line)==1)
                {
                    i=1;
                }
                else
                {
                    i=0;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(i==1){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean loadMap()
    {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        int i=0;
        try {
            in = openFileInput("screen");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                if(Integer.parseInt(line)==1)
                {
                    i=1;
                }
                else
                {
                    i=0;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(i==1){
            return true;
        }
        else{
            return false;
        }
    }
}
