package skosko.imageapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class UploadImage extends AppCompatActivity implements LocationListener{
    private String filePath;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_LOCATION = "location";
    private String name = "asd";

    String UPLOAD_URL = "http://takku.eu/ImageApp/index.php/uploadImage";
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        Intent intent = getIntent();
        filePath = intent.getStringExtra("filePath");
        name = intent.getStringExtra("name");
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeFile(filePath, options);

        ImageView imgviw = (ImageView) findViewById(R.id.preview);
        imgviw.setImageBitmap(bitmap);



        if (Integer.valueOf(Build.VERSION.SDK_INT) >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    double latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                    double longdirude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

                    TextView asd = (TextView) findViewById(R.id.textViewLocation);
                    asd.setText(latitude + "-" + longdirude);
                }
            }
        }


        findViewById(R.id.buttonUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();


            }
        });

    }


    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}



    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(UploadImage.this, s , Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(UploadImage.this, "Failure" , Toast.LENGTH_LONG).show();
                        finish();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String image = getStringImage(bitmap);
                Map<String,String> params = new Hashtable<String, String>();
                TextView asd = (TextView) findViewById(R.id.textViewLocation);
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);
                params.put(KEY_LOCATION, asd.getText().toString());
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}

