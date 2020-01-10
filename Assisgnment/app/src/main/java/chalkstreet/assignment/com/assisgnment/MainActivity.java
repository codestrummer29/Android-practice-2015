package chalkstreet.assignment.com.assisgnment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView cardLayout;
    List<ImageList> myList;
    RecyclerView.Adapter myadapter;
    ImageButton add;
    Button preview;
    LinearLayoutManager card_manager;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    int CAMERA_REQUEST = 1888;
    int pos,preview_status;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        preview_status = 0;

        myList = new ArrayList<>();
        cardLayout = (RecyclerView)findViewById(R.id.cardView);
        add = (ImageButton)findViewById(R.id.buttonAdd);
        preview = (Button)findViewById(R.id.preview);

        cardLayout.setHasFixedSize(true);
        LinearLayoutManager card_manager = new LinearLayoutManager(getApplicationContext());
        card_manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        cardLayout.setLayoutManager(card_manager);
        myadapter = new MYAdapter(getApplication(), myList);
        myadapter.setHasStableIds(false);
        cardLayout.setAdapter(myadapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myList.add(myList.size(),new ImageList());
                myadapter.notifyItemInserted(myList.size()-1);
                cardLayout.scrollToPosition(myList.size()-1);
                Log.d("size",myList.size()+"");
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preview_status == 0){
                    preview_status = 1;
                    add.setVisibility(View.INVISIBLE);
                    preview.setText("Back");
                    setMargins(cardLayout,0,0,0,0);
                }else {
                    preview_status = 0;
                    add.setVisibility(View.VISIBLE);
                    preview.setText("Preview");
                    setMargins(cardLayout,10,100,10,10);
                }
            }
        });


        verifyStoragePermissions(this);
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public class MYAdapter extends RecyclerView.Adapter<MainActivity.MYAdapter.viewHolder>{
        private Context mContext;
        public List<ImageList> thisList;
        public MYAdapter(Context mContext , List<ImageList> list) {
            this.mContext = mContext;
            this.thisList = list;
        }

        @Override
        public MainActivity.MYAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.view_card, parent, false);

            return new viewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MainActivity.MYAdapter.viewHolder holder, final int position) {

            if (thisList.get(holder.getAdapterPosition()).getImage() != null) {
                holder.myImage.setVisibility(View.VISIBLE);
                holder.cam.setVisibility(View.INVISIBLE);
                holder.storage.setVisibility(View.INVISIBLE);
                holder.myImage.setImageBitmap(thisList.get(holder.getAdapterPosition()).getImage());
            } else {
                holder.cam.setVisibility(View.VISIBLE);
                holder.storage.setVisibility(View.VISIBLE);
                holder.myImage.setVisibility(View.INVISIBLE);
                holder.cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get image from camera
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "temp.jpg")));
                        Log.d(Environment.getExternalStorageDirectory().toString(), Environment.getExternalStorageDirectory().toString());
                        pos = holder.getAdapterPosition();
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });
                Log.d("reached", pos + "");
                holder.storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get image from storage
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        pos = holder.getAdapterPosition();
                        Log.d("click", myList.size() + " " + thisList.size() + "");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                });
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //remove the Current Card from the list
                        try {
                            Log.d("remove", position + "");
                            thisList.remove(holder.getAdapterPosition());
                            myadapter.notifyItemRemoved(holder.getAdapterPosition());
                            myadapter.notifyItemRangeChanged(holder.getAdapterPosition(), thisList.size());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return thisList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public class viewHolder extends RecyclerView.ViewHolder{
            ImageButton cam,storage;
            Button remove;
            ImageView myImage;
            public viewHolder(View itemView) {
                super(itemView);
                // declare variables here
                cam = (ImageButton)itemView.findViewById(R.id.camera);
                storage = (ImageButton)itemView.findViewById(R.id.storage);
                myImage = (ImageView)itemView.findViewById(R.id.imageCard);
                remove = (Button)itemView.findViewById(R.id.removeCard);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("new", pos + "");
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                ImageList object = myList.get(pos);
                object.setImage(bitmap);
                myadapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                File picture = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/temp.jpg");
                Uri imgUri = Uri.fromFile(picture);
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imgUri);
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                ImageList object = myList.get(pos);
                object.setImage(scaled);
                myadapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                ImageList object = myList.get(pos);
                object.setImage(null);
                myadapter.notifyDataSetChanged();
            }
        }
    }
    public void verifyStoragePermissions(Activity activity) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }
}
