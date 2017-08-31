package com.bishe.wozai;
//第四个页面
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class TActivity extends Activity {
	
	private Button bt1;
	private Context mContext;
	CheckBox chk1;
//	CheckBox chk_pass;
	
	EditText etUserEmail,etUserPhone, etUserName1,etUserPass1 ,etUserPassResure ;
	SharedPreferences pref;
    Editor editor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tactivity);
		bt1=(Button) findViewById(R.id.button_Zhuce);
		final Context mContext=this;
//		chk1=(CheckBox) findViewById(R.id.checkBox_saveusername);
		//chk_pass=(CheckBox) findViewById(R.id.checkBox_savepassword);
		
		etUserPhone=(EditText) findViewById(R.id.editText_phone);
		etUserName1=(EditText) findViewById(R.id.editText_UserName);
		etUserPass1=(EditText) findViewById(R.id.editText_password);
		etUserPassResure=(EditText) findViewById(R.id.editText_passwordResure);
		pref=getSharedPreferences("UserInfo", MODE_PRIVATE);
		editor=pref.edit();
		
		 bt1.setOnClickListener(new MyClickListener(){
	    	  public void onClick(View v) {
					//调用父类的onclick事件
					super.onClick(v);
					
					Intent intent=new Intent(mContext, MainActivity.class);//调转到下一页面的意图
					
					
					String phone =etUserPhone.getText().toString().trim();
					String name =etUserName1.getText().toString().trim();
					String pass =etUserPass1.getText().toString().trim();
					String repass =etUserPassResure.getText().toString().trim();
					
					
					 SharedPreferences preferences=getSharedPreferences("UserInfo",MODE_WORLD_WRITEABLE);  
                     Editor edit=preferences.edit();
                     editor.putString("etUserPhone", phone);
                     editor.putString("etUserName1", name);
                     editor.putString("etUserPass1", pass);
                     editor.putString("etUserPassResure", repass);
                     editor.commit();
                     
                     
                     if (name.length()!=0&&phone.length()==11&&(pass).equals(repass)) {
                    	 Toast.makeText(TActivity.this, "注册成功，即将跳转至登录界面，请稍候",Toast.LENGTH_LONG).show();  
                         startActivity(intent);
					} else{Toast.makeText(TActivity.this, "注册失败,请再次核对您的信息无误并勾选免责申明",Toast.LENGTH_LONG).show();}
					
	    	  }
	    	  });
		
		
		
		
	}

}
