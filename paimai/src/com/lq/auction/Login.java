package com.lq.auction;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.paimai.R;
import com.lq.client.util.HttpUtil;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager.Query;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	EditText etName, etPass;
	Button bnLogin, bnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);

		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnLogin = (Button) findViewById(R.id.bnCancel);

		bnLogin.setOnClickListener(new HomeListener(this));
		bnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					if (loginPro()) {
						Intent intent = new Intent(Login.this,
								AuctionClientActivity.class);
						startActivity(intent);
						finish();

					} else {
						DialogUtil.showDialog(Login.this
								, "�û����ƻ�����������������룡",
								false);
					}
				}

			}

		});

	}

	private boolean loginPro() {
		//��ȡ�û�������û���������
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		JSONObject jsonObject;
		try {
			jsonObject = query(username,pwd);  //��ѯ�û����������Ƿ���ȷ
			//���userId����0
			if (jsonObject.getInt("userId")>0) {
				return true;
			}
		} catch (Exception e) {
			DialogUtil.showDialog(this
					, "�������쳣�����Ժ�����", false);
			e.printStackTrace();
		}
		return false;
	}

	private boolean validate() {
		String username = etName.getText().toString().trim();
		if (username.equals("")) {
			DialogUtil.showDialog(this, "�û��˻�����", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();  //trim():ȥ��ǰ��ո�
		if (pwd.equals("")) {
			DialogUtil.showDialog(this, "�û��������", false);
			return false;
		}
		
		return true;
	}
	
	private JSONObject query(String username, String password) 
			throws Exception{
		//ʹ��Map��װ����
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		
		//���巢�������URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		
		return new JSONObject(HttpUtil.postReuest(url, map));
	}

}
