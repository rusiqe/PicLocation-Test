package com.rusiqe.piclocation;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



public class PicMapActivity extends FragmentActivity implements OnMapReadyCallback {
 // I need to show pics on this map - edit later
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")); //can change location to whatever client wants
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
	
//once map renders and we have location where pictures go, lets make the button to take more pics and upload them to server

private Button mTakePhoto;
	private ImageView mImageView;
	private static final String TAG = "upload";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTakePhoto = (Button) findViewById(R.id.take_photo);
		mImageView = (ImageView) findViewById(R.id.imageview);

		mTakePhoto.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.take_photo:
			takePhoto();
			break;
		}
	}

	private void takePhoto() {
//		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//		startActivityForResult(intent, 0);
		dispatchTakePictureIntent();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onActivityResult: " + this);
		if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
			setPic();
//			Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//			if (bitmap != null) {
//				mImageView.setImageBitmap(bitmap);
//				try {
//					sendPhoto(bitmap);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}
	}
	
 public void uploadImgur() {

    String API_KEY = "f62417f32168541 7888ae5de0278b14627b474235f42c708e219b81";
    String uploadpath = ((MyVariables) getApplication()).getUploadPath();

    Bitmap bitmap = BitmapFactory.decodeFile(uploadpath);

    // Creates Byte Array from picture
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);


    try {
        URL url = new URL("http://api.imgur.com/2/upload");
        String  data = URLEncoder.encode("image", "UTF-8")
                + "="
                + URLEncoder.encode(
                        Base64.encode(baos.toByteArray(), Base64.DEFAULT)
                                .toString(), "UTF-8");
        data += "&" + URLEncoder.encode("key", "UTF-8") + "="
                + URLEncoder.encode(API_KEY, "UTF-8");

        URLConnection   conn = url.openConnection();

        conn.setDoOutput(true);
        OutputStreamWriter  wr = new OutputStreamWriter(conn.getOutputStream());

        wr.write(data);
        wr.flush();

        Log.e("wr.write", "Writing data: " + data);
		
		//to ping later for file list
}
