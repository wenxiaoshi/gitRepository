package com.bishe.wozai;



//���ǵ�һ��ҳ��




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
	
	EditText etUserName,etUserPass;//��¼��Ҫ������Ϣ���༭�����ʱ���õ�
	SharedPreferences pref;//��¼��Ҫ������Ϣ��ͬ��
    Editor editor;//��¼��Ҫ������Ϣ��ͬ��
    private static final String MAG="MainActivity";
 
	   	
	       @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt1=(Button) findViewById(R.id.button_denglu);//bt1�͵�¼��ť��
		bt2=(Button) findViewById(R.id.button_zhuce);//bt2��ע�ᰴť��
		
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
					// TODO �Զ����ɵķ������
					Intent intent=new Intent(MainActivity.this,GpsService.class);
					startService(intent);
					System.out.println("GPS������");
					Toast.makeText(MainActivity.this, "GPS������", Toast.LENGTH_SHORT).show();
				
				}
			});
             stopService.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					Intent intent=new Intent(MainActivity.this,GpsService.class);
					stopService(intent);
					System.out.println("GPS����ر�");
					Toast.makeText(MainActivity.this, "GPS����ر�", Toast.LENGTH_SHORT).show();
				}
			});
			
		  bt1.setOnClickListener(new MyClickListener(){
	    	  public void onClick(View v) {
					//���ø����onclick�¼�
					super.onClick(v);
					
					Intent intent=new Intent(mContext, FActivity.class);//��ת����һҳ�����ͼ
					
					
					String name =etUserName.getText().toString().trim();
					String pass =etUserPass.getText().toString().trim();//��¼��Ҫ������Ϣ��ȡ����
					
					
					
					SharedPreferences sp = getSharedPreferences( "UserInfo", 0);
					String shuru_userName = sp.getString("etUserName1", "");
					String shuru_userPass = sp.getString("etUserPass1", "");
					 Log.i(MAG, "�û�����"+etUserName.getText());
					 Log.i(MAG, "���룺��"+etUserPass.getText());
					//�û������������Ѵ���������ת�����ѡ�򱻹�ѡ����ס�û���
					if ( (shuru_userName).equals(name)&&(shuru_userPass).equals(pass)&&name.length()!=0&&pass.length()!=0) {
						 
						Toast.makeText(MainActivity.this, "��¼�ɹ�O(��_��)O", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(MainActivity.this, "�ñ�Ǹ��������Ϣ���󣬵�¼ʧ�����أ�Ҫ�����ȵ��ע���᣿", 1).show();
			}
	    	  }//����û������ڣ�����������ڣ�������startActivity(intent)��ִ����ͼ����ʵ��ҳ����ת�����������¼�ɹ���ʾ��Ϣ
	    	  });
	    		
		  
		  
		  
		  
       
      bt2.setOnClickListener(new MyClickListener(){
    	  public void onClick(View v) {
				//���ø����onclick�¼�
				super.onClick(v);
				
				Intent intent=new Intent(mContext, TActivity.class);
				//��ת����һҳ�����ͼ
				startActivity(intent);
				Toast.makeText(MainActivity.this, "ע���ʼ����,���Ժ�", 1).show();
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

//���ע��ʱ����Ҫ�û����������ǿղ�����ʾ��ʾ��Ϣ
	
}
class MyClickListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		
	}}