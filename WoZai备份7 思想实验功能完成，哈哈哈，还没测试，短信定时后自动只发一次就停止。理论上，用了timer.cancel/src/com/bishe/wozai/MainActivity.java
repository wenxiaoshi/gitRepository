package com.bishe.wozai;



//这是第一个页面




import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button bt1;
	private Button bt2,startService,stopService;
	private Context mContext;
	CheckBox chk_name;
//	CheckBox chk_pass;
	
	EditText etUserName,etUserPass;//登录需要的新信息，编辑存入的时候用到
	SharedPreferences pref;//登录需要的新信息，同上
    Editor editor;//登录需要的新信息，同上
    private static final String MAG="MainActivity";
 
	   	
	       @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt1=(Button) findViewById(R.id.button_denglu);//bt1和登录按钮绑定
		bt2=(Button) findViewById(R.id.button_zhuce);//bt2和注册按钮绑定
		
		startService=(Button) findViewById(R.id.button_startService);
		stopService=(Button) findViewById(R.id.button_stopService);
		
		final Context mContext=this;
		chk_name=(CheckBox) findViewById(R.id.checkBox_saveusername);
		//chk_pass=(CheckBox) findViewById(R.id.checkBox_savepassword);
		
		etUserName=(EditText) findViewById(R.id.editText1);//
		etUserPass=(EditText) findViewById(R.id.editText2);//
		pref=getSharedPreferences("UserInfo", MODE_PRIVATE);//
		editor=pref.edit();//
		
		 String name=pref.getString("userName", "");
			if (name==null) {
				chk_name.setChecked(false);	
			}else {
				chk_name.setChecked(true);
			etUserName.setText(name);
			}
		
			startService.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Intent intent=new Intent(MainActivity.this,GpsService.class);
					startService(intent);
					System.out.println("GPS服务开启");
					Toast.makeText(MainActivity.this, "GPS服务开启", Toast.LENGTH_SHORT).show();
				
				}
			});
             stopService.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Intent intent=new Intent(MainActivity.this,GpsService.class);
					stopService(intent);
					System.out.println("GPS服务关闭");
					Toast.makeText(MainActivity.this, "GPS服务关闭", Toast.LENGTH_SHORT).show();
				}
			});
			
		  bt1.setOnClickListener(new MyClickListener(){
	    	  public void onClick(View v) {
					//调用父类的onclick事件
					super.onClick(v);
					
					Intent intent=new Intent(mContext, FActivity.class);//调转到下一页面的意图
					
					
					String name =etUserName.getText().toString().trim();
					String pass =etUserPass.getText().toString().trim();//登录需要的新信息，取密码
					
					
					
					SharedPreferences sp = getSharedPreferences( "UserInfo", 0);
					String shuru_userName = sp.getString("etUserName1", "");
					String shuru_userPass = sp.getString("etUserPass1", "");
					 Log.i(MAG, "用户名："+etUserName.getText());
					 Log.i(MAG, "密码：："+etUserPass.getText());
					//用户名。密码与已存的相符，跳转，如果选框被勾选，记住用户名
					if ( (shuru_userName).equals(name)&&(shuru_userPass).equals(pass)&&name.length()!=0&&pass.length()!=0) {
						 
						Toast.makeText(MainActivity.this, "登录成功O(∩_∩)O", Toast.LENGTH_SHORT).show();
						startActivity(intent);
						if (chk_name.isChecked()) {
							editor.putString("userName", name);
							editor.commit();	
							
						} 
						
						else {
							editor.remove("userName");
							editor.commit();

						}
			}else {
				Toast.makeText(MainActivity.this, "好抱歉，您的信息错误，登录失败了呢，要不，先点击注个册？", 1).show();
			}
	    	  }//如果用户名等于，而且密码等于，就运行startActivity(intent)；执行意图，即实现页面跳转，并且输出登录成功提示信息
	    	  });
	    		
		  
		  
		  
		  
       
      bt2.setOnClickListener(new MyClickListener(){
    	  public void onClick(View v) {
				//调用父类的onclick事件
				super.onClick(v);
				
				Intent intent=new Intent(mContext, TActivity.class);
				//调转到下一页面的意图
				startActivity(intent);
				Toast.makeText(MainActivity.this, "注册初始化中,请稍候", 1).show();
		}
      });
    		
	}



	private void LoadUserData() {
		SharedPreferences sp = getSharedPreferences( "UserInfo", 0);
		
	 if (sp.getBoolean("isSave", false)){
	     String shuru_userName = sp.getString("etUserName", "");
		 String shuru_userPass = sp.getString("etUserPass", "");
	
		         }
		
	}

//点击注册时必须要用户名和密码是空才能显示提示信息
	
}
class MyClickListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		
	}}