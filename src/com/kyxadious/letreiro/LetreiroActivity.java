package com.kyxadious.letreiro;


import android.R.bool;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LetreiroActivity extends Activity {
	
	private int i;
	private int tempo;
	private boolean flag;
	private boolean piscarTexto;
	private boolean fonteLed;
	private Handler handlerLetreiro;
	private Handler handlerPiscar;
	private TextView textView;
	private String texto, meuTexto;

	private Runnable runnableLetreiro = new Runnable() {

		@Override
		public void run() {
			
			texto = "";
			int n = meuTexto.length();

			for (int k=i, j=0; j < n; j++) {
				texto += String.valueOf(meuTexto.charAt(k % n));
				k = (k%n)+1;
			}
			
			i = (i%n)+1;
			textView.setText(texto);
			handlerLetreiro.postDelayed(this, tempo);
		}
	};
	
	private Runnable runnablePiscar = new Runnable() {

		@Override
		public void run() {
			if (flag) {
				textView.setTextColor(Color.BLACK);
				flag = false;
			} else {
				textView.setTextColor(Color.WHITE);
				flag = true;
			}
			handlerLetreiro.postDelayed(this, 100);
		}
	};

	
	public static final String TEXTO = "com.kyxadious.letreiro.texto";
	public static final String TAMANHO_TEXTO = "com.kyxadious.letreiro.tamanhotexto";
	public static final String TEMPO_TEXTO = "com.kyxadious.letreiro.tempotexto";
	public static final String PISCAR_TEXTO = "com.kyxadious.letreiro.piscartexto";
	public static final String FONTE_LED = "com.kyxadious.letreiro.ponteled";

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		// Toma toda tela
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Deixar a tela ativada
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letreiro);
		
		textView = (TextView) findViewById(R.id.text_view);	
		/* 
		 TextView txt = (TextView) findViewById(R.id.custom_font);  
		 Typeface font = Typeface.createFromAsset(getAssets(), "Chantelli_Antiqua.ttf");  
		 txt.setTypeface(font);  
		*/
		
		
		Intent intent = getIntent();
		meuTexto = "       ";
		meuTexto+= intent.getStringExtra(TEXTO);
		textView.setTextSize(Integer.valueOf(intent.getStringExtra(TAMANHO_TEXTO)));
		tempo = Integer.valueOf(intent.getStringExtra(TEMPO_TEXTO));
		piscarTexto = Boolean.valueOf(intent.getStringExtra(PISCAR_TEXTO));
		fonteLed = Boolean.valueOf(intent.getStringExtra(FONTE_LED));
		
		Log.d("TAMANHO TEXTO", intent.getStringExtra(TAMANHO_TEXTO));
		Log.d("TEMPO TEXTO", intent.getStringExtra(TEMPO_TEXTO));
		Log.d("PISCAR TEXTO", intent.getStringExtra(PISCAR_TEXTO));
		Log.d("FONTE LED", intent.getStringExtra(FONTE_LED));
	
		
		i = 0;
		handlerLetreiro = new Handler();
		handlerPiscar = new Handler();
		
		handlerLetreiro.post(runnableLetreiro);
		
		if (piscarTexto) {
			flag = true;
			handlerPiscar.post(runnablePiscar);
		}
		
		// Setando uma fonte
		if (fonteLed) {
			Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.nome_fonte_led));
			textView.setTypeface(font);
		}
	
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.letreiro, menu);
		return true;
	}

}
