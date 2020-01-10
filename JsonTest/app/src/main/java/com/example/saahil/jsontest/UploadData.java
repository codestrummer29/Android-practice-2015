package com.example.saahil.jsontest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Saahil on 09/04/16.
 */
public class UploadData extends Fragment{

    private Button buttonChoose;
    private Button buttonUpload;
    private Spinner spin;

    private ImageView imageView;

    private EditText editTextNo;
    private EditText editTextPrice;
    private EditText editTextDes;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="http://www.abodemart.in/android_post.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_NO = "mobile";
    private String LOCATION = "location";
    private String PRICE = "price";
    private String DES = "description";
    private String FB = "fb_id";

    private CharSequence[] items = {"Camera","Gallery"};
    int CAMERA_REQUEST = 1888;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.upload_layout,container,false);
        super.onCreateView(inflater, container, savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity());
        buttonChoose = (Button)rootview.findViewById(R.id.buttonChoose);
        buttonUpload = (Button)rootview.findViewById(R.id.buttonUpload);
        spin = (Spinner)rootview.findViewById(R.id.spin);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Abode Valley");
        spinnerArray.add("Estancia");
        spinnerArray.add("Safa");
        spinnerArray.add("Green Pearl");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, spinnerArray);
        spin.setAdapter(adapter);

        editTextNo = (EditText)rootview.findViewById(R.id.editText1);
        editTextDes = (EditText)rootview. findViewById(R.id.uploaddes);
        editTextPrice = (EditText)rootview.findViewById(R.id.uploadprice);

        imageView  = (ImageView)rootview.findViewById(R.id.imageView);
        verifyStoragePermissions(getActivity());

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Image from ")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        cameraOpen();
                                        break;
                                    case 1:
                                        showFileChooser();
                                        break;
                                }
                            }
                        }).create().show();

            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNo.getText().toString().length() != 10) {
                    Toast.makeText(getActivity(), "Enter Correct mobile number", Toast.LENGTH_SHORT).show();
                } else if (editTextPrice.getText().toString().length()==0) {
                    Toast.makeText(getActivity(), "Enter Price", Toast.LENGTH_SHORT).show();
                }else if(editTextDes.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "Enter Description", Toast.LENGTH_SHORT).show();
                }else{
                    uploadImage();
                }
            }
        });
        return rootview;
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,25, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
            //Showing the progress dialog
            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Disimissing the progress dialog
                            loading.dismiss();
                            //Showing toast message of the response
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                            editTextNo.setText("");
                            editTextDes.setText("");
                            editTextPrice.setText("");
                            imageView.setImageResource(R.mipmap.ic_add_a_photo_white_24dp);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Dismissing the progress dialog
                            loading.dismiss();

                            //Showing toast
                            Toast.makeText(getActivity(), "Make sure You select a PHOTO and \nHave an Active Internet Connection", Toast.LENGTH_LONG).show();
                            //Toast.makeText(getActivity(), volleyError.getMessage().toString(),Toast.LENGTH_LONG).show();


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Converting Bitmap to String
                    String image = getStringImage(bitmap);

                    //Getting Image Name
                    String mobile = editTextNo.getText().toString().trim();
                    String loc = spin.getSelectedItem().toString().trim();
                    String p = editTextPrice.getText().toString().trim();
                    String des = editTextDes.getText().toString().trim();
                    String fbid = Profile.getCurrentProfile().getId();
                    String name = Profile.getCurrentProfile().getName();

                    //Creating parameters
                    Map<String, String> params = new Hashtable<String, String>();

                    //Adding parameters
                    params.put(KEY_IMAGE, image);
                    params.put(KEY_NAME, name);
                    params.put(KEY_NO, mobile);
                    params.put(LOCATION, loc);
                    params.put(DES, des);
                    params.put(PRICE, p);
                    params.put(FB, fbid);

                    //returning parameters
                    return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            //Adding request to the queue
            requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void cameraOpen(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "temp.jpg")));
        Log.d(Environment.getExternalStorageDirectory().toString(), Environment.getExternalStorageDirectory().toString());
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST) {
            try {

                File picture = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/temp.jpg");
                Uri imgUri=Uri.fromFile(picture);
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imgUri);
                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                imageView.setImageBitmap(scaled);

            }catch (NullPointerException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
