package fr.coursiut.coursandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button send = (Button) this.findViewById(R.id.envoi);
		send.setOnClickListener(new EcouteurSend());
		
		Button bouton2 = (Button) this.findViewById(R.id.button_affiche_ok2);
		bouton2.setOnClickListener(new EcouteurFarida());
		
		}
	
	// INNER CLASSE : eccouteurs du 1er bouton ! 
	class EcouteurFarida implements View.OnClickListener{
		public void onClick(View v){
			Toast.makeText(MainActivity.this, 
					"Eh ! tu viens de cliquer sur un bouton!", 
						Toast.LENGTH_SHORT).show();
		}
	}
	
	class EcouteurSend implements View.OnClickListener{
		public void onClick(View v){
			Context context = getApplicationContext();
			EditText  editText  = (EditText) findViewById(R.id.text_send);
			String message = editText.getText().toString();
			CharSequence text = "Votre message est : \n " + message;
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}
	
}
	
	
//DIFFERENT TYPE DAFFICHAGE	
//Log.i("information", "Farida va vous affiche une information g\'en\'erale");
//Log.d("information", "Farida va vous affiche une information pour le debbogage");
//Log.w("information", "Farida va vous affiche une information de warning");
//Log.e("information", "Farida va vous affiche une erreeeur");



--------------------------------------------



<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    tools:context="${relativePackage}.${activityClass}" 
    >
    

    <EditText
        android:id="@+id/text_send"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dip"
        android:background="#00FFFF"
        android:ems="10"
        android:gravity="center"
        android:layout_gravity="center"
        android:hint="@string/text_send"
        android:inputType="textMultiLine|textAutoCorrect"
        android:lines="2"
        android:textColor="#000" >
    </EditText>
    
	<EditText 
      android:id="@+id/text_phone"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dip"
        android:background="#00FFFF"
        android:gravity="center"
        android:layout_gravity="center"
        android:hint="@string/text_phone"
        android:inputType="textMultiLine|textAutoCorrect"
        android:lines="2"
        android:textColor="#000" 
    />

    <Button
        android:id="@+id/envoi"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/envoi"
        />

    <Button
        android:id="@+id/button_affiche_ok2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="80sp"
        android:layout_marginTop="80sp"
        android:paddingLeft="20sp"
        android:paddingTop="10sp"
        android:text="@string/appui_bouton_ok2" />
    
</LinearLayout>



---------------------------------------------


<?xml version="1.0" encoding="utf-8"?>
<resources>

    <string name="app_name">MonApplication</string>
    <string name="hello_world">Hello world!</string>
    
    <string name="envoi"> Envoyer </string>
    
    <string name="appui_bouton_ok2"> Rien </string> 
    
    <string name="text_send"> Taper votre nom </string>
    
    <string name="text_phone"> Taper votre numero </string>
</resources>