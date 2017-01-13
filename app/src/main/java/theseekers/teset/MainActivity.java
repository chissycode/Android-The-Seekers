package theseekers.teset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    public MapView mMapView = null;
    public BaiduMap mBaiduMap = null;
    CircleOptions circles[][];
    double main_loca[][], extra_loca[][];
    double cur_x, cur_y;
    boolean pos[][];
    boolean target_unlock[];
    BitmapDescriptor mCurrentMarker = null;
    public LocationClient mLocationClient = null;

    static final double[][] target={
            {30.564619 , 104.00007}, //不高山
            {30.564619 , 104.001409},//白石桥
            {30.562197 , 104.001024},//网球场
            {30.56194 , 103.999346},//游泳池
            {30.561482 , 104.001869},//艺术学院
            {30.564424 , 103.998272},//保卫处
            {30.564619 , 104.00007},//不高山
            {30.566309 , 104.004606},//工科实习基地
            {30.56498 , 104.006988},//建环学院
            {30.562788 , 104.006269},//图书馆
            {30.563247 , 104.007559},//二基楼
            {30.564299 , 104.009417},//计算机学院（软件学院）
            {30.565369 , 104.010353},//法学院
            {30.562096 , 104.006707},//知识广场
            {30.560424 , 104.001487},//青春广场
            {30.56104 , 104.004532},//长桥
            {30.55953 , 104.000318},//商业街
            {30.563247 , 104.007559},//二基楼
            {30.560424 , 104.008973},//综合楼
            {30.561031 , 104.006609},//第一教学楼A
            {30.560012 , 104.005849},//第一教学楼B
            {30.560316 , 104.006543},//第一教学楼C
            {30.560697 , 104.00715},//第一教学楼D
            {30.563068 , 104.012504},//一号运动场
            {30.563744 , 104.012912},//体育馆
            {30.559231 , 104.005872},//江安河水闸
            {30.559181 , 104.003206},//二号运动场（体育学院）
            {30.563068 , 104.012504},//一号运动场
            {30.561986 , 104.0101886},//德水
            {30.564619 , 104.008569},//沫熙
            {30.565365 , 104.005083},//巴渠
            {30.566632 , 104.013932},//东门
            {30.559033 , 104.008405},//南门
            {30.561443 , 104.013455},//东南门
            {30.55768 , 104.003307},//西南门
            {30.557333 , 103.999228},//1舍
            {30.557582 , 103.999544},//2舍
            {30.557931 , 104.00032},//3舍
            {30.557483 , 104.000751},//4舍
            {30.557483 , 104.000751},//5舍
            {30.558038 , 103.998953},//6舍
            {30.558125 , 103.998939},//7舍
            {30.558622 , 103.999672},//8舍
            {30.558909 , 104.000721},//9舍
            {30.559443 , 103.99868},//10舍
            {30.559916 , 103.999715},//11舍
            {30.559992 , 103.99983},//12舍
            {30.559992 , 103.99983},//13舍
            {30.561008 , 104.000114},//14舍
            {30.560858 , 103.999215},//15舍
            {30.561369 , 103.998845},//16舍
            {30.56122 , 103.997724},//17舍
            {30.560598 , 103.998335},//18舍
            {30.56015 , 103.998464},//19舍
            {30.562598 , 103.997398},//20舍
            {30.563256 , 103.997246}//21舍
    };
    //LatLng[] target_pos;
    static final String[] target_name={"不高山","白石桥","网球场","游泳池","艺术学院","保卫处","不高山","工科实习基地","建环学院","图书馆","二基楼",
            "计算机学院（软件学院）","法学院","知识广场","青春广场","长桥","商业街","二基楼","综合楼", "第一教学楼A", "第一教学楼B", "第一教学楼C", "第一教学楼D",
            "一号运动场","体育馆","江安河水闸","二号运动场（体育学院）","一号运动场","德水","沫熙","巴渠","东门","南门","东南门","西南门",
            "1舍","2舍","3舍","4舍","5舍","6舍","7舍","8舍","9舍","10舍","11舍","12舍","13舍","14舍","15舍","16舍","17舍","18舍","19舍","20舍","21舍",
    };
    final int radius=100;
    public BDLocationListener myListener=new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);	//设置定位数据

            //Receive Location
            cur_x = location.getLatitude();//获取纬度
            cur_y = location.getLongitude();//获取经度
            for(int i=0;i<num-1;i++)
            {
                for(int j=0;j<num;j++)
                {
                    if(i<num-2)
                    {
                        //if(DistanceUtil.getDistance(new LatLng(cur_x, cur_y), new LatLng(main_loca[10 * i + j][0], main_loca[10 * i + j][1]))<30)
                        if(SpatialRelationUtil.isCircleContainsPoint(new LatLng(main_loca[10 * i + j][0], main_loca[10 * i + j][1]),radius,new LatLng(cur_x,cur_y)))
                        {

                            for(int m=0;m<target_num;m++)
                            {
                                if(target_unlock[m]&&pos[i][j]&&SpatialRelationUtil.isCircleContainsPoint(new LatLng(main_loca[10 * i + j][0], main_loca[10 * i + j][1]), radius, new LatLng(target[m][0], target[m][1])))
                                {
                                    //System.out.println(target_name[i]+" 解锁");
                                    Toast.makeText(MainActivity.this, target_name[m] + " 已解锁", Toast.LENGTH_SHORT).show();
                                    Date curDate=new Date(System.currentTimeMillis());//获取系统当前时间
                                    SimpleDateFormat sdformatweek = new SimpleDateFormat("EEEE");
                                    SimpleDateFormat sdformathour = new SimpleDateFormat("HH:mm");
                                    SimpleDateFormat sdformatmonth = new SimpleDateFormat("MMMM");
                                    SimpleDateFormat sdformatdate = new SimpleDateFormat("dd");
                                    String week = sdformatweek.format(curDate);
                                    String hour=sdformathour.format(curDate);
                                    String month=sdformatmonth.format(curDate);
                                    String date=sdformatdate.format(curDate);
                                    String achieve=target_name[m];
                                    save_achievement(week, hour, month, date, achieve);//保存数据
                                    pos[i][j]=false;
                                    target_unlock[m]=false;
                                    changeValue();
                                }
                            }
                            break;
                        }
                    }
                    else
                    {
                        //if(DistanceUtil.getDistance(new LatLng(cur_x,cur_y),new LatLng(extra_loca[j][0],extra_loca[j][1]))<300)
                        if(SpatialRelationUtil.isCircleContainsPoint(new LatLng(extra_loca[j][0],extra_loca[j][1]),radius,new LatLng(cur_x,cur_y)))
                        {
                            for(int m=0;m<target_num;m++)
                            {
                                if(target_unlock[m]&&pos[i][j]&&SpatialRelationUtil.isCircleContainsPoint(new LatLng(extra_loca[j][0],extra_loca[j][1]),radius,new LatLng(target[m][0],target[m][1])))
                                {
                                    Toast.makeText(MainActivity.this,target_name[m]+" 已解锁",Toast.LENGTH_SHORT).show();
                                    Date curDate=new Date(System.currentTimeMillis());//获取系统当前时间
                                    SimpleDateFormat sdformatweek = new SimpleDateFormat("EEEE");
                                    SimpleDateFormat sdformathour = new SimpleDateFormat("HH:mm");
                                    SimpleDateFormat sdformatmonth = new SimpleDateFormat("MMMM");
                                    SimpleDateFormat sdformatdate = new SimpleDateFormat("dd");
                                    String week = sdformatweek.format(curDate);
                                    String hour=sdformathour.format(curDate);
                                    String month=sdformatmonth.format(curDate);
                                    String date=sdformatdate.format(curDate);
                                    String achieve=target_name[m];
                                    save_achievement(week, hour, month, date, achieve);//保存数据
                                    pos[i][j]=false;
                                    target_unlock[m]=false;
                                    changeValue();
                                }
                            }

                            break;
                        }
                    }
                }
            }
            mBaiduMap.clear();
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num - 2; j++) {
                    if (pos[j][i]) {
                        circles[j][i] = new CircleOptions()
                                .center(new LatLng(main_loca[j * 10 + i][0], main_loca[j * 10 + i][1]))
                                .fillColor(0xCCCCCCCC)
                                .radius(100)
                                .visible(true)
                                .stroke(new Stroke(0, 0xCCCCCCCC));
                        mBaiduMap.addOverlay(circles[j][i]);
                    }

                }
            }
            for (int i = 0; i < num; i++) {
                if (pos[8][i]) {
                    circles[num - 1][i] = new CircleOptions()
                            .center(new LatLng(extra_loca[i][0], extra_loca[i][1]))
                            .fillColor(0xCCCCCCCC)
                            .radius(100)
                            .visible(true)
                            .stroke(new Stroke(0, 0xCCCCCCCC));
                    mBaiduMap.addOverlay(circles[num - 1][i]);
                }

            }
            for(int i=0;i<target_num;i++)
            {
                //定义Maker坐标点
                LatLng point = new LatLng(target[i][0],target[i][1]);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.location);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
            }

        }
    };

    final int num = 10;
    final int target_num=56;

    private ImageView achievement;
    private ImageView setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKiller.addActivity(this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.ellipse);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);

        LatLng cenpt = new LatLng(30.563391, 104.00472);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(16.0f)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        init_fog(mBaiduMap);
        //迷雾初始化
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类

        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
        changeValue();
        achievement=(ImageView)findViewById(R.id.second);
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, AchievementActivity.class);
                startActivity(intent1);
            }
        });

        setting=(ImageView)findViewById(R.id.third);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, SettingPage.class);
                startActivity(intent2);
            }
        });

    }

    private void changeValue() {
        ProgressBar progressHorizontal = (ProgressBar) findViewById(R.id.progressBar);
        progressHorizontal.setVisibility(ProgressBar.VISIBLE);
        int progress=0;
        for (int i=0;i<target_num;i++)
        {
            if(!target_unlock[i])
                progress++;
        }
        progressHorizontal.setProgress(progress);
    }

    @Override
    protected void onDestroy() {
        save();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        ActivityKiller.removeActivity(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        load();
    }

    @Override
    protected void onPause() {
        save();
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        save();
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,SettingPage.class);
            startActivity(intent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void init_fog(BaiduMap mBaiduMap) {
        pos = new boolean[num][num];
        circles = new CircleOptions[num][num];
        target_unlock=new boolean[target_num];
        double unit = 0.0015;
        main_loca = new double[80][2];
        extra_loca = new double[num][2];

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                pos[i][j] = true;
            }
        }
        load();
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num - 2; j++) {
                main_loca[j * 10 + i][1] = 103.99767 + (double) i * unit;
                main_loca[j * 10 + i][0] = 30.556689 + (j + 0.2 * i) * unit;
            }
        }
        for (int i = 0; i < num; i++) {
            if (i < 5) {
                extra_loca[i][0] = 30.561401 + (double) i * unit;
                extra_loca[i][1] = 104.01267;
            } else if (i < 9) {
                extra_loca[i][0] = 30.561401 + (i - 4.0) * unit;
                extra_loca[i][1] = 104.01267 + unit;
            } else {
                extra_loca[i][0] = 30.563652;
                extra_loca[i][1] = 104.01267 + 2 * unit;
            }


        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num - 2; j++) {
                if (pos[j][i]) {
                    circles[j][i] = new CircleOptions()
                            .center(new LatLng(main_loca[j * 10 + i][0], main_loca[j * 10 + i][1]))
                            .fillColor(0xCCCCCCCC)
                            .radius(radius)
                            .visible(true)
                            .stroke(new Stroke(0, 0xCCCCCCCC));
                    mBaiduMap.addOverlay(circles[j][i]);
                }

            }
        }
        for (int i = 0; i < num; i++) {
            if (pos[8][i]) {
                circles[num - 1][i] = new CircleOptions()
                        .center(new LatLng(extra_loca[i][0], extra_loca[i][1]))
                        .fillColor(0xCCCCCCCC)
                        .radius(radius)
                        .visible(true)
                        .stroke(new Stroke(0, 0xCCCCCCCC));
                mBaiduMap.addOverlay(circles[num - 1][i]);
            }

        }
        for(int i=0;i<target_num;i++)
        {
            //定义Maker坐标点
            LatLng point = new LatLng(target[i][0],target[i][1]);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.location);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
    }

    public void save()
    {
        String inputText="";
        for(int i=0;i<num;i++) {
            for(int j=0;j<num;j++) {
                if (pos[i][j]) {
                    inputText+="1"+"\n";
                }
                else {
                    inputText+="0"+"\n";
                }

            }
        }
        for(int i=0;i<target_num;i++)
        {
            if(target_unlock[i])
                inputText+="1"+"\n";
            else
                inputText+="0"+"\n";
        }
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("mapdata", Context.MODE_PRIVATE);
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
    public boolean[][] load()
    {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        int cnt=0;
        try {
            in = openFileInput("mapdata");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            for(int i=0;i<num*num;i++)
            {
                line = reader.readLine();
                if(Integer.parseInt(line)==1)
                {
                    pos[cnt/num][cnt%num]=true;
                }
                else
                {
                    pos[cnt/num][cnt%num]=false;
                }
                cnt++;
            }
            for(int i=0;i<target_num;i++)
            {
                if(Integer.parseInt(line)==1)
                {
                    target_unlock[i]=true;
                }
                else
                {
                    target_unlock[i]=false;
                }
            }
            /*while ((line = reader.readLine()) != null) {
                if(Integer.parseInt(line)==1)
                {
                    pos[cnt/num][cnt%num]=true;
                }
                else
                {
                    pos[cnt/num][cnt%num]=false;
                }
                cnt++;
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                for(int i=0;i<num;i++)
                {
                    for (int j=0;j<num;j++)
                    {
                        pos[i][j]=true;
                    }
                }
                for(int i=0;i<target_num;i++)
                {
                    target_unlock[i]=true;
                }
                save();
            }
        }
        return pos;
    }
    public void save_achievement(String week,String hour,String month,String date,String achieve)//保存已经解锁的成就到txt文件中
    {
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=openFileOutput("achievement", Context.MODE_APPEND);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(week);
            writer.write(" ");
            writer.write(hour);
            writer.write(" ");
            writer.write(month);
            writer.write(" ");
            writer.write(date);
            writer.write(" ");
            writer.write(achieve);
            writer.write(" ");
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
