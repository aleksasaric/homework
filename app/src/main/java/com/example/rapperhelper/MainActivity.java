package com.example.rapperhelper;

import android.content.Intent;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
  Button findRhyme;
  Button showRhymes;
  EditText word;
  Map<String, ArrayList<String>> dictionary;
  public static String data;
  String bestHelper="ml";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initValues();
    setSpinner();

    /*
     * On click calls thread responsible for fetching data and returns it to data property in presentable form.
     */
    findRhyme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String wordToFind = word.getText().toString();
        FetchData fetchData = new FetchData(wordToFind,dictionary,bestHelper);
        fetchData.execute();
      }
    });
    /*
     * On click calls moveToDisplayActivity function.
     */
    showRhymes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          moveToDisplayActivity();
      }
    });
  }
  /*
   * Shows display responsible for presenting fetched data.
   */
  private void moveToDisplayActivity() {
    Intent intent = new Intent(MainActivity.this, DisplayRhymesActivity.class);
    intent.putExtra("data", MainActivity.data);
    startActivity(intent);
  }
  /*
   * Initializes spinner by setting it's items from resource array and set's onclick event listener.
   */
  private void setSpinner() {
    Spinner spinner = findViewById(R.id.words_spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.filter, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
  }

  /*
   * Initializes values of class properties and instantiate new dictionary.
   */
  private void initValues() {
    word   = (EditText)findViewById(R.id.word);
    findRhyme = (Button) findViewById(R.id.findRhyme);
    showRhymes = (Button) findViewById(R.id.showRhymes);
    dictionary = new HashMap<>();
  }

 /*
  * On click sets helper for rapper. Whether query will fetch rhymes that sounds like (sl), have meaning like (ml) or rhyme (rel_rhy) with given word.
  */
  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String filter = parent.getItemAtPosition(position).toString();
    switch (filter){
      case "Approximate Rhymes":
        bestHelper = "rel_rhy";
        break;
      case "Synonyms":
        bestHelper = "ml";
        break;
      case "Sounds Like":
        bestHelper = "sl";
        break;
      default: bestHelper = "ml";
      break;


    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }
}
