package fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.activity.Albums;
import com.example.myapplication.activity.Camera;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Fragment_main extends Fragment {

    private ImageView picture;
    Intent intent1, intent2;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //changeImage();
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeImage();
        Button chooseFromAlbum = (Button) getView().findViewById(R.id.choose_from_album);
        // action bar
        final ImageView back = (ImageView) getView().findViewById(R.id.back);

        intent1=new Intent(getActivity(), Albums.class);//???????????????Albums??????????????????Intent
        intent2=new Intent(getActivity(), Camera.class);//???????????????Camera??????????????????Intent



        CheckAndroidPermission();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //???????????????
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String foodname = myfoodname.getText().toString(); //??????????????????
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    startActivity(intent1);//??????album???????????????
//                }
//                else {
//                    Log.v("tag", "----?????????");
//                }
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("????????????????????????????????????")
                            .setContentText("????????????????????????")
                            .setConfirmText("??????")
                            .show();
                } else {
                    startActivity(intent1);//??????album???????????????
                }
            }
        });

        //????????????
        Button takePhoto = (Button) getView().findViewById(R.id.take_photo);
        picture = (ImageView) getView().findViewById(R.id.picture);

        //????????????????????????????????????
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("??????????????????????????????")
                            .setContentText("????????????????????????")
                            .setConfirmText("??????")
                            .show();
                } else {
                    //String foodname = myfoodname.getText().toString();
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    startActivity(intent2);//??????camera???????????????
//                }
//                else {
//                    Log.v("tag", "----?????????");
//                }
                    startActivity(intent2);
                }
            }
        });

        Button example = (Button) getView().findViewById(R.id.examples);

        example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }


    private void showDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View view_par2 = inflater.inflate(R.layout.par_dialog2,null,false);
        ImageView examples1 = (ImageView) super.getView().findViewById(R.id.example1);
        SweetAlertDialog dialog = new SweetAlertDialog(view_par2.getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("??? ??? ??? ???")
                .setCustomView(view_par2)
                .setConfirmText("????????????")
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.example1);
//                        File appDir = new File(Environment.getExternalStorageDirectory(),"examples");
//                        if (!appDir.exists()) {
//                            appDir.mkdir();
//                        }
//                        String fileName = System.currentTimeMillis() + ".jpeg";
//                        File file = new File(appDir, fileName);
//                        try {
//                            FileOutputStream fos = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                            fos.flush();
//                            fos.close();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        // ????????????????????????????????????
//                        try {
//                            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
//                                    file.getAbsolutePath(), fileName, null);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
////                        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
////                        Uri uri = Uri.fromFile(file);
////                        intent.setData(uri);
////                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
//
//                        Toast toast1 = Toast.makeText(getContext(), "???????????????????????????????????????????????????", Toast.LENGTH_SHORT);
//                        toast1.setGravity(Gravity.CENTER, 0, 0);
//                        toast1.show();
                        sweetAlertDialog.dismiss();
                    }
                });


        dialog.show();
        //???????????????????????????????????????????????????????????????????????????3/4  ??????????????????show????????????????????????????????????????????????????????????????????????

    }

    private void imgMerge() {
        new Thread(() -> {
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.example1);
                File file = new File(Environment.getExternalStorageDirectory(),"1");
                if (!file.exists()) {
                    file.createNewFile();
                }
                //???????????????????????????
                //?????????????????????
                savePhotoAlbum(bitmap, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * ???????????????
     *
     * @param src  ?????????
     * @param file ?????????????????????
     */
    private void savePhotoAlbum(Bitmap src, File file) {

        //??????????????????
        OutputStream outputStream;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            src.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            if (!src.isRecycled()) {
                src.recycle();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //???????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
            values.put(MediaStore.MediaColumns.MIME_TYPE, "JPG");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            ContentResolver contentResolver = getActivity().getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  values);
            if (uri == null) {
                return;
            }
            try {
                outputStream = contentResolver.openOutputStream(uri);
                FileInputStream fileInputStream = new FileInputStream(file);
                FileUtils.copy(fileInputStream, outputStream);
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MediaScannerConnection.scanFile(
                    getContext(),
                    new String[]{file.getAbsolutePath()},
                    new String[]{"image/jpeg"},
                    (path, uri) -> {
                        // Scan Completed
                    });
        }
    }


    private void CheckAndroidPermission() {
        List<String> permissionLists = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            permissionLists.add(Manifest.permission.CAMERA);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionLists.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionLists.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!permissionLists.isEmpty()){//??????????????????????????????
            ActivityCompat.requestPermissions(getActivity(), permissionLists.toArray(new String[permissionLists.size()]), 100);
        }else{

        }
    }

    public void changeImage() {
        int[] mArray = {
                R.drawable.suggestion1,
                R.drawable.suggestion2,
                R.drawable.suggestion3,
//                R.drawable.suggestion4,
//                R.drawable.suggestion5,
//                R.drawable.suggestion6,
                R.drawable.suggestion7,
                R.drawable.suggestion8,
                R.drawable.suggestion9,
                R.drawable.suggestion10,
                R.drawable.suggestion11,
                R.drawable.suggestion12,
                R.drawable.suggestion13,
                R.drawable.suggestion14,
                R.drawable.suggestion15,
                R.drawable.suggestion16,
                R.drawable.suggestion17,
                R.drawable.suggestion18,
                R.drawable.suggestion20,
                R.drawable.suggestion21,
                R.drawable.suggestion22,
                R.drawable.suggestion23,
                R.drawable.suggestion24,
                R.drawable.suggestion25,
                R.drawable.suggestion26,
                R.drawable.suggestion27,
                R.drawable.suggestion28,
                R.drawable.suggestion29,
                R.drawable.suggestion30,
                R.drawable.suggestion31,
                R.drawable.suggestion32,
                R.drawable.suggestion33,
                R.drawable.suggestion34,
                R.drawable.suggestion35,
                R.drawable.suggestion36,
                R.drawable.suggestion37,
                R.drawable.suggestion38,
                R.drawable.suggestion39,
        };
        LinearLayout[] lArray = {
                (LinearLayout) getView().findViewById(R.id.layout1),
                (LinearLayout) getView().findViewById(R.id.layout2),
                (LinearLayout) getView().findViewById(R.id.layout3),
                (LinearLayout) getView().findViewById(R.id.layout4),
                (LinearLayout) getView().findViewById(R.id.layout5),
        };
        ImageView[] ivBg = {
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
        };
        ImageView[] ivBar = {
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
                new ImageView(this.getContext()),
        };
        Drawable[] drawable = new Drawable[5];

        //??????????????????????????????????????????R??????????????????????????????int??????
        int[] ar = getArray();
        //????????????
        for (int m = 0; m < ar.length; m++) {
            drawable[m] = getContext().getResources().getDrawable(mArray[ar[m]]);
            ivBg[m].setImageDrawable(drawable[m]);
            ivBg[m].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            lArray[m].addView(ivBg[m]);
//            ivBar[m].setImageDrawable(getContext().getResources().getDrawable(R.drawable.bar));
//            lArray[m].addView(ivBar[m],1);
        }
    }

    public int[] getArray() {
        Random r1 = new Random();
        int b = r1.nextInt(35);

        //??????????????????5??????????????????, ???????????????
        int[] a = new int[5];

        //??????????????????,???????????????????????????,??????????????????
        a[0] = b;

        //??????,??????????????????????????????,?????????1??????
        for (int i = 1; i < a.length; i++) {
            b = r1.nextInt(35);

            //?????????????????????,????????????????????????????????????????????????=0????????????
            for (int num = 0; num < i; num++) {
                //?????????????????????????????????,????????????????????????,???????????????????????????????????????a[0]????????????,?????????????????????????????????
                while (b == a[num]) {
                    b = r1.nextInt(35);
                    num = 0;
                }
            }

            a[i] = b;
        }
        return a;
    }

}