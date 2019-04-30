package com.example.rapperhelper;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
 * Class is responsible for making queries/fetching data and returning it as presentable String to MainActivity.
 */
public class FetchData extends AsyncTask<Void,Void,Void> {

  String word;
  Map<String, ArrayList<String>> dictionary;
  String bestHelper;

  public FetchData(String word, Map<String,ArrayList<String>> dictionary, String bestHelper) {
    this.word = word;
    this.dictionary = dictionary;
    this.bestHelper = bestHelper;
  }

  @Override
  protected Void doInBackground(Void... voids) {

    try {
      JSONArray JA = fetchData();
      fillDictionary(JA);

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
  /*
   * Makes api call and returns fetched data as JSONArray.
   */
  private JSONArray fetchData() throws IOException, JSONException {
    String parsedData = "";
    URL url = new URL("https://api.datamuse.com/words?"+bestHelper+"="+word+"&max=15");
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    InputStream inputStream = httpURLConnection.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    String line = "";

    while(line!= null){
      line = bufferedReader.readLine();
      parsedData += line;

    }
    JSONArray JA = new JSONArray(parsedData);
    return JA;
  }

  /*
   * Extracts all words from JSONArray and places them in Map (dictionary).
   */
  private void fillDictionary(JSONArray JA) throws JSONException {
    List<String> translation = new ArrayList<>();
    for (int i=0;i<JA.length();i++){
      JSONObject JO = (JSONObject) JA.get(i);
      translation.add((String) JO.get("word"));
    }
    dictionary.put(word, (ArrayList<String>) translation);
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    String data = dictionaryToString();
    MainActivity.data = data;
  }

  /*
   * Gets all key-values from dictionary and returns them in String.
   */
  private String dictionaryToString() {
    String data = "";
    for (Map.Entry<String, ArrayList<String>> entry : dictionary.entrySet()) {
      String key = entry.getKey();
      List<String> values = entry.getValue();
      System.out.println("Key = " + key);
      System.out.println("Values = " + values + "n");
      data += key+":"+values+"\n";
    }
    return data;
  }
}
