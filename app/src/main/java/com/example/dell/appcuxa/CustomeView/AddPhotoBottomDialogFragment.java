package com.example.dell.appcuxa.CustomeView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.appcuxa.CuxaAPI.CuXaAPI;
import com.example.dell.appcuxa.CuxaAPI.NetworkController;
import com.example.dell.appcuxa.FileInfo;
import com.example.dell.appcuxa.MainPage.MainPageViews.FragmentUpRoom;
import com.example.dell.appcuxa.R;
import com.example.dell.appcuxa.Utils.AppUtils;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

import static android.content.Context.MODE_PRIVATE;

public class AddPhotoBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final int CAMERA = 111;
    public static final int GALLERY = 112;
    TextView tvCamera, tvGallery, tvTrash;
    public int position = -1;
    byte[] byteArray;
    CuXaAPI fileService;
    SharedPreferences sharedPreferences;
    List<File> fileList = new ArrayList<>();
    File[] files;
    SharedPreferences.Editor editor;
    String token;
    String imagePath = "";
    OnChooseReasonListener listener;

    public static AddPhotoBottomDialogFragment newInstance() {
        return new AddPhotoBottomDialogFragment();
    }

    public interface OnChooseReasonListener {
        void onChooseReason(byte[] bytes, int pos);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_choose_image, container,
                false);

        // get the views and attach the listener
        tvCamera = view.findViewById(R.id.tvCamera);
        tvGallery = view.findViewById(R.id.tvGallery);
        tvTrash = view.findViewById(R.id.tvTrash);
        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);
        tvTrash.setOnClickListener(this);
        sharedPreferences = getActivity().getSharedPreferences("login_data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token", "");

        fileService = NetworkController.upload();
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("pos", -1);
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCamera:
                intentCamera();
                break;
            case R.id.tvGallery:
                intentGetImage();
                break;
            case R.id.tvTrash:
                break;
        }
    }

    public void intentCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void intentGetImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                sendBackImage(photo);
            } else if (requestCode == GALLERY) {
                Uri imageUri = data.getData();
                imagePath = getRealPathFromURI_API19(getContext(), imageUri);
                List<MultipartBody.Part> parts = new ArrayList<>();
                parts.add(prepareFilePart("image", imageUri));
                //
                MultipartBody.Part[] parts1 = new MultipartBody.Part[parts.size()];
                parts.toArray(parts1);
                Call<ResponseBody> call = fileService.postImage("Bearer " + token, parts1);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d("hihhh", response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("hihhh", "failure");
                    }
                });
                //
              /*  File file = new File(imagePath);
                fileList.add(file);
                String[] simpleArray = new String[ fileList.size() ];
                fileList.toArray( simpleArray );
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),simpleArray);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
                Call<List<FileInfo>> call= fileService.postImage(body,"Bearer "+token);
                call.enqueue(new Callback<List<FileInfo>>() {
                    @Override
                    public void onResponse(Call<List<FileInfo>> call, Response<List<FileInfo>> response) {
                        if(response.isSuccessful()){
                            Log.d("hihi",response.toString());
                            Toast.makeText(getActivity(), "sucessful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FileInfo>> call, Throwable t) {
                        Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
                    }
                });*/
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                    sendBackImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setOnChooseReasonListener(OnChooseReasonListener listener) {
        this.listener = listener;
    }

    public void sendBackImage(Bitmap data) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        data.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
        listener.onChooseReason(byteArray, position);
        dismiss();
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(getContext(), fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getActivity().getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }
}
