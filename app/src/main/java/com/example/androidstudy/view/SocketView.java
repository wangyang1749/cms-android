package com.example.androidstudy.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.GnssMeasurementsEvent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidstudy.R;
import com.example.androidstudy.pojo.CmsConst;
import com.example.androidstudy.socket.TcpClient;
import com.example.androidstudy.utils.FileUtils;
import com.example.androidstudy.utils.PhotoUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SocketView extends AppCompatActivity {

    private EditText mEditText;
    private TextView mTextView;
    private static final String TAG = "TAG";
    private static final String HOST = "192.168.0.109";
    private static final int PORT = 9999;
    private PrintWriter printWriter;
    private BufferedReader in;
    private ExecutorService mExecutorService = null;
    private String receiveMsg;
    private Button button_open;
    private Button button_disconnect;
    private Button button_send;
    private Button button_upload;
    private Button button_camera;
    public static final int RC_TAKE_PHOTO = 1;
    public static final int RC_CHOOSE_PHOTO = 2;
    String token;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String filePath = FileUtils.getFilePathByUri(this, uri);
                    File file = new File(filePath);
                    String fileName = file.getName();


                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", "android_"+fileName, fileBody)
                            .addFormDataPart("imagetype", "multipart/form-data")
//                            .addFormDataPart("originalFilename","head_image.jpg")
                            .build();
                    Request request = new Request.Builder()
                            .header("Authorization","Bearer "+token)
                            .url(CmsConst.BASE_URL+"/api/attachment/upload")
                            .post(requestBody)
                            .build();

                    final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
                    OkHttpClient okHttpClient = httpBuilder
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SocketView.this, "上传失败！!", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SocketView.this, "上传成功！!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    if (!TextUtils.isEmpty(filePath)) {
                        mTextView.setText(filePath);

                     }
                }
                if (resultCode == RESULT_OK) {
//                    Uri uri = data.getData();
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(uri, this);
//
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //0-100 100为不压缩
//                    InputStream is = new ByteArrayInputStream(baos.toByteArray());

                }
                break;
            case RC_TAKE_PHOTO:
//                RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
//                //将图片显示在ivImage上
//                Glide.with(this).load(mTempPhotoPath).apply(requestOptions).into(ivImage);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        if(token==null){
//            ComponentName componentName = new ComponentName(getContext(), LoginActivity.class);
//            intent.putExtra("className", intent.getComponent().getClassName());
//            intent.setComponent(componentName);
//            super.startActivity(intent);


            Intent intent = new Intent(SocketView.this,MyLoginActivity.class);
            startActivity(intent);
            SocketView.this.finish();
        }

        mEditText =  findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);
        button_camera = findViewById(R.id.button_camera);
        button_upload = findViewById(R.id.button_upload);

        // 创建线程池
        mExecutorService = Executors.newCachedThreadPool();

        button_open = findViewById(R.id.socket_open);
        button_disconnect = findViewById(R.id.socket_disconnent);
        button_send = findViewById(R.id.button_send);
        button_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(v);

            }
        });

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(v);
            }
        });

        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token");
                editor.commit();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketView.this, "删除Token成功!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
//                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, RC_CHOOSE_PHOTO);

            }
        });

    }

    @Override
    protected void onDestroy() {
//        disconnect();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_TAKE_PHOTO:   //拍照权限申请返回
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }
                break;
            case RC_CHOOSE_PHOTO:   //相册选择照片权限申请返回
                choosePhoto();
                break;
        }
    }



    private String mTempPhotoPath;
    private Uri imageUri;

    private void takePhoto() {
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File fileDir = new File(Environment.getExternalStorageDirectory() + File.separator + "photoTest" + File.separator);
//        if (!fileDir.exists()) {
//            fileDir.mkdirs();
//        }

//        File photoFile = new File(fileDir, "photo.jpeg");
//        mTempPhotoPath = photoFile.getAbsolutePath();
//        imageUri = FileUtils.getUriForFile(this, photoFile);
//        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, RC_TAKE_PHOTO);
    }
    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }


    //上传图片文件的操作
    public void uploadImg() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //上传图片参数需要与服务端沟通，我就不多做解释了，我添加的都是我们服务端需要的
        //你们根据情况自行更改
        //另外网络请求我就不多做解释了
//        FormBody body = new FormBody.Builder().add("dir", "c/image")
//                .add("data", imgString)
//                .add("file", "headicon")
//                .add("ext", "jpg").build();
//        Request request = new Request.Builder().url(url).post(body).build();
//        okHttpClient.newCall(request).enqueue(new GnssMeasurementsEvent.Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String data = response.body().string();
//                Gson gson = new Gson();
//                final Beans bean = gson.fromJson(data, Beans.class);
//                Log.d(TAG, "onResponse: " + data);
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //加载图片用的Gilde框架，也可以自己自由选择，
//                        //""里面取决于服务端的返回值是否需要自行添加地址
//                        Glide.with(MainActivity.this).load(""+bean.getData().getUrl()).into(headIv);
//                    }
//                });
//            }
//        });
    }


    public void connect(View view) {
        mExecutorService.execute(new connectService());  //在一个新的线程中请求 Socket 连接
    }
//
    public void send(View view) {
        String sendMsg = mEditText.getText().toString();
        mExecutorService.execute(new sendService(sendMsg));
    }
//
    public void disconnect() {
        mExecutorService.execute(new sendService("0"));
    }

    private class sendService implements Runnable {
        private String msg;

        sendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            printWriter.println(this.msg);
        }
    }

    private class connectService implements Runnable {

        @Override
        public void run() {//可以考虑在此处添加一个while循环，结合下面的catch语句，实现Socket对象获取失败后的超时重连，直到成功建立Socket连接
            try {
                Socket socket = new Socket(HOST, PORT);      //步骤一
//                socket.setSoTimeout(60000);
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream(), "UTF-8")), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                receiveMsg();
//                printWriter.println("connecting!!!");
//
//                while ((receiveMsg = in.readLine())!=null) {
//                    System.out.println(receiveMsg);
//                }
            } catch (Exception e) {
                Log.e(TAG, ("connectService:" + e.getMessage()));   //如果Socket对象获取失败，即连接建立失败，会走到这段逻辑
            }
        }
    }

    private void receiveMsg() {
        try {
            while (true) {                                      //步骤三
                if ((receiveMsg = in.readLine()) != null) {
                    Log.d(TAG, "receiveMsg:" + receiveMsg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mTextView.setText(receiveMsg + "\n\n" + mTextView.getText());
                            mTextView.setText(receiveMsg);
                        }
                    });
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "receiveMsg: ");
            e.printStackTrace();
        }
    }





}
