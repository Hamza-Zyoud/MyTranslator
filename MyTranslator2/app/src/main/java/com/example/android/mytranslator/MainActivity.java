package com.example.android.mytranslator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String Selectedlanguage = "";
    private EditText EnteredText;
    private Button Translate;
    private TextView Translation;
    private Locale srcLanguage = Locale.ENGLISH;
    private Locale dstLanguage = Locale.GERMAN;
    Spinner spin;
    private final String TAG = "URL Reader: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        dstLanguage = Locale.GERMAN;
                        Selectedlanguage = "GERMAN";
                        break;
                    case 1:
                        dstLanguage = Locale.FRENCH;
                        Selectedlanguage = "FRENCH";
                        break;
                    case 2:
                        dstLanguage = Locale.ITALIAN;
                        Selectedlanguage = "ITALIAN";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EnteredText = (EditText) findViewById(R.id.editText);
        Translate = (Button) findViewById(R.id.button);
        Translation = (TextView) findViewById(R.id.textView);
        Translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //String url=GetURL(EnteredText.getText().toString());
                    String text = EnteredText.getText().toString();
                    new TranslatorClass(text).execute();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Something Worng", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Hamza Zyoud -- this class is used to create background thread then call fetchURL method to send
     *                the request to the API
     */

    private class TranslatorClass extends AsyncTask<Void, Void, Void> {
        private String textTranslate;
        private String result;
        private String text;

        public TranslatorClass(String str) {
            this.textTranslate = str;
        }

        @Override
        protected Void doInBackground(Void... params) {

            text = new Translator().fetchURL(textTranslate, srcLanguage, dstLanguage);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Translation.setText(text);
                }
            });

            return null;
        }

    }

}
