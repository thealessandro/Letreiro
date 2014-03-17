package com.kyxadious.qx.letreiro;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int progressoTamanhoTexto;
	private int progressoVelocidade;
	private boolean exibirHora;
	private boolean piscarTexto;
	private boolean fonteLed;
	private EditText editTextTexto;
	private SeekBar seekBarTamanhoTexto;
	private SeekBar seekBarTempo;
	private CheckBox checkBoxPiscarTexto;
	private CheckBox checkBoxFonteLed;
	private CheckBox checkBoxExibirHora;
	private Button buttonsalvarConfiguracao;
	private Button buttonLimparTexto;
	

	public static final String TEXTO = "com.kyxadious.letreiro.texto";
	public static final String TAMANHO_TEXTO = "com.kyxadious.letreiro.tamanhotexto";
	public static final String TEMPO_TEXTO = "com.kyxadious.letreiro.tempotexto";
	public static final String PISCAR_TEXTO = "com.kyxadious.letreiro.piscartexto";
	public static final String FONTE_LED = "com.kyxadious.letreiro.ponteled";
	public static final String EXIBIR_HORA = "com.kyxadious.letreiro.exibirhora";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);

		
		editTextTexto = (EditText) findViewById(R.id.edit_text_texto);
		seekBarTamanhoTexto = (SeekBar) findViewById(R.id.seek_bar_tamanho_texto);
		seekBarTempo = (SeekBar) findViewById(R.id.seek_bar_tempo);
		checkBoxExibirHora = (CheckBox) findViewById(R.id.exibir_hora_letreiro);
		checkBoxPiscarTexto = (CheckBox) findViewById(R.id.check_box_piscar);
		checkBoxFonteLed = (CheckBox) findViewById(R.id.fonte_letreiro_led);
		buttonsalvarConfiguracao = (Button) findViewById(R.id.button_save);
		buttonLimparTexto = (Button) findViewById(R.id.button_clear);
	
		progressoTamanhoTexto = seekBarTamanhoTexto.getProgress();
		progressoVelocidade = seekBarTempo.getProgress();
		exibirHora = checkBoxExibirHora.isChecked();
		piscarTexto = checkBoxPiscarTexto.isChecked();
		fonteLed = checkBoxFonteLed.isChecked();
			
		seekBarTamanhoTexto.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if ( fromUser ) {
					Log.d("SEEK BAR TAMANHO TEXTO", String.valueOf(progress));
					progressoTamanhoTexto = progress;
				}
			}
		});
		
		seekBarTempo.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					int maxProcesso = seekBarTempo.getMax();
					maxProcesso = Math.abs(maxProcesso - progress); 
					Log.d("SEEK BAR VELOCIDADE", String.valueOf(maxProcesso));
					progressoVelocidade = maxProcesso;
				}
			}
		});
		
		checkBoxPiscarTexto.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					Log.d("PISCAR", String.valueOf(isChecked));
					piscarTexto = isChecked;
			}
		});
		
		checkBoxFonteLed.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("FONTE LED", String.valueOf(isChecked));
				fonteLed = isChecked;
			}
		});
		
		
		checkBoxExibirHora.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("EXIBIR HORA", String.valueOf(isChecked));
				exibirHora = isChecked;
				
				if(isChecked) {
					editTextTexto.setText("");
				}
			}
		});
			

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case R.id.action_sobre:
			sobreApp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	public void salvarConfiguracao(View view) {
		
		if (editTextTexto.length() != 0 || exibirHora) {
			Intent intent = new Intent(getApplicationContext(), LetreiroActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(TEXTO, editTextTexto.getText().toString());
			intent.putExtra(TAMANHO_TEXTO, String.valueOf(progressoTamanhoTexto));
			intent.putExtra(TEMPO_TEXTO, String.valueOf(progressoVelocidade));
			intent.putExtra(EXIBIR_HORA, String.valueOf(exibirHora));
			intent.putExtra(PISCAR_TEXTO, String.valueOf(piscarTexto));
			intent.putExtra(FONTE_LED, String.valueOf(fonteLed));
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "Ops! Nenhum texto digitado, tenha mais sorte da próxima vez :D", Toast.LENGTH_LONG).show();
		}
	}
	
	public void limparTexto(View view){
		if (editTextTexto.length() != 0 ) {
			editTextTexto.setText("");
		} else {
			Toast.makeText(getApplicationContext(), "Não tem mais texto cara. Não precisa estuprar o botão!", Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void sobreApp(){
		
		Intent intent = new Intent(getApplicationContext(), SobreActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		startActivity(intent);
		
		/*
		AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this);
		msgBuilder.setTitle("Sobre o aplicativo");
		msgBuilder.setMessage("Aplicativo desenvolvido por Alessandro Menezes.\nContato: alessandromenezes22@gmail.com");
		msgBuilder.setPositiveButton("OK", null);
		msgBuilder.show();
		*/
	}

}
