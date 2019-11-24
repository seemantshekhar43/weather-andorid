package comandydevo.wixsite.seemantshekhar43.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    /* paste the following in gradle(Module:app) in dependencies
    "compile 'com.android.volley:volley:1.0.0'"
*/

/* paste the following in AndroidManifest.xml
    <uses-permission android:name="android.permission.INTERNET"/>
 */
/* create a new file in java> com.andydevo.wixsite.seemantshekhar43....
      named  MySingelton
 */


    //introducing the variables
    Button button;
    EditText city;
    TextView result;
    String baseURL ="http://api.openweathermap.org/data/2.5/weather?q=";
    String apiKey ="&appid=4959852fea06e3893bbc40ecf08dcc10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating instance of the things
        button = (Button) findViewById(R.id.button);
        city = (EditText) findViewById(R.id.city);
        result = (TextView) findViewById(R.id.result);

        // on clicking the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set our url
                String myUrl = baseURL + city.getText().toString() + apiKey;

                // now copy the code to get the data from the site
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // lets dig out another data from json
                                try {
                                    String weather = response.getString("weather");

                                    //lets watch the data
                                    Log.i("weather","weather" + weather);

                                    // getting under weather array
                                    JSONArray array = new JSONArray(weather);

                                    // use loop to extract the elements of the array
                                    for (int i = 0; i<array.length(); i++){
                                        JSONObject parObject = array.getJSONObject(i);
                                        String main = parObject.getString("main");
                                        result.setText(main);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                // lets watch the error if any
                                Log.i("Error","Something went wrong:" + error);
                            }
                        });
                MySingelton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }


        });
    }
}
