package com.example.rapperhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/*
 * Class responsible for viewing corresponding words for queried words.
 */
public class DisplayRhymesActivity extends AppCompatActivity {
  TextView showRhymes;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_rhymes);
    initValues();
    String data = getData();
    showData(data);
  }
  /*
   * Initializes properties.
   */
  private void initValues() {
    showRhymes = (TextView) findViewById(R.id.allRhymes);
  }
  /*
   * Returns data passed from MainActivity.
   */
  private String getData() {
    Intent intent = getIntent();
    String data = intent.getStringExtra("data");
    return data;
  }
 /*
  * Shows passed data to screen.
  */
  private void showData(String data) {
    System.out.println(data);
    showRhymes.setText(data);
  }
}
