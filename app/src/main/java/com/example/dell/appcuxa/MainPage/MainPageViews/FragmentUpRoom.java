package com.example.dell.appcuxa.MainPage.MainPageViews;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.appcuxa.CustomeView.AddPhotoBottomDialogFragment;
import com.example.dell.appcuxa.CustomeView.MyGridView;
import com.example.dell.appcuxa.CustomeView.RobAutoCompleteTextView;
import com.example.dell.appcuxa.CustomeView.RobButton;
import com.example.dell.appcuxa.CustomeView.RobEditText;
import com.example.dell.appcuxa.CuxaAPI.CuXaAPI;
import com.example.dell.appcuxa.CuxaAPI.NetworkController;
import com.example.dell.appcuxa.MainPage.Adapter.ImageAdapter;
import com.example.dell.appcuxa.MainPage.Adapter.PlaceAutoCompleteAdapter;
import com.example.dell.appcuxa.ObjectModels.ImageItem;
import com.example.dell.appcuxa.ObjectModels.ImageObject;
import com.example.dell.appcuxa.ObjectModels.LocationRoom;
import com.example.dell.appcuxa.ObjectModels.RoomObject;
import com.example.dell.appcuxa.ObjectModels.UtilityObject;
import com.example.dell.appcuxa.R;
import com.example.dell.appcuxa.Utils.AppUtils;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FragmentUpRoom extends DialogFragment implements ILogicDeleteImage,
        View.OnClickListener,AddPhotoBottomDialogFragment.OnChooseReasonListener ,ImageAdapter.OnRecyclerViewItemClickListener {
    public static List<byte[]> imageBytes = new ArrayList<>();
    public List<ImageItem>  selImageList = new ArrayList<>();
    ImageAdapter imageAdapter;
    SharedPreferences sharedPreferences;
    public static final int IMAGE_ITEM_ADD = -1;
    List<ImageItem> imageItemList = new ArrayList<>();
    CuXaAPI fileService;
    private View mMainView;
    @BindView(R.id.btnUpRoom)
    public RobButton btnUpload;
    @BindView(R.id.btnReset)
    public RobButton btnReset;
    @BindView(R.id.btnSelectImage)
    public RobButton btnSelectImage;
    @BindView(R.id.edtRoomName)
    public RobEditText edtRoomName;
    @BindView(R.id.edtPrice)
    public RobEditText edtPrice;
    @BindView(R.id.edtDienTich)
    public RobEditText edtDienTich;
    @BindView(R.id.edtPhoneNo)
    public RobEditText edtPhoneNo;
    @BindView(R.id.edtNumOfPpl)
    public RobEditText edtNumOfPeople;
    @BindView(R.id.edtGender)
    public RobEditText edtGender;
    @BindView(R.id.edtDesc)
    public RobEditText edtDesc;
    RobAutoCompleteTextView edtAddress;
    MyGridView gridView;
    Double[] latlon;
    RecyclerView recyclerView;
    List<Double> doubleList = new ArrayList<>();
    public static ArrayList<String> imageHinhId = new ArrayList<>();
    List<UtilityObject> utilityObjectList = new ArrayList<>();
    String token = "";
    ImageView imgBack;
    protected GeoDataClient mGeoDataClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40,-168),new LatLng(71,136));
    SelectedImageAdapter selectedImageAdapter;
    PlaceAutoCompleteAdapter placeAutoCompleteAdapter;
    public FragmentUpRoom() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_dang_phong, container, false);
        ButterKnife.bind(getActivity());
        init();
        fileService = NetworkController.upload();
        sharedPreferences = getActivity().getSharedPreferences("login_data", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        mGeoDataClient = Places.getGeoDataClient(getContext(), null);
        placeAutoCompleteAdapter = new PlaceAutoCompleteAdapter(getContext(),mGeoDataClient,LAT_LNG_BOUNDS,null);
        edtAddress.setAdapter(placeAutoCompleteAdapter);
        edtAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER
                     ){
                    // execute method for searching
                    doubleList = geoLocate();
                }
                return false;
            }
        });
        getAllUtilities();
        if(utilityObjectList!=null){
            int a = utilityObjectList.size();
            Log.d("abca",a+"");
        }

        //initWidget();
        Bundle bundle = getArguments();
        if (bundle != null) {
            int pos = bundle.getInt("position", -1);
            Log.d("poss", pos + "");
        }

        return mMainView;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgBack:
                FragmentUpRoom.this.dismiss();
                break;
            case R.id.btnUpRoom:
                break;
            case R.id.btnReset:
                break;
            case R.id.btnSelectImage:
                showBottomDialog(AppUtils.PICK_IMAGE_1);
                break;
            case R.id.btnUpload:
                if(imageHinhId.size()<3){
                    Toast.makeText(getActivity(), "Bạn cần ít nhất 3 tấm ảnh phòng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtDienTich.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "Bạn chưa nhập diện tích phòng", Toast.LENGTH_SHORT).show();
                }

                latlon = new Double[doubleList.size()];
                latlon = doubleList.toArray(latlon);
                if(latlon.length!=2 || edtAddress.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "Bạn chưa chọn địa điểm", Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadRoom();
                break;
        }
    }

    private void uploadRoom() {

        String roomName = edtRoomName.getText().toString();
        String type = "empty";
        String price = edtPrice.getText().toString();
        String electricPrice = "120000";
        String wterPrice = "12345";
        String downPayment = "12343";

        LocationRoom locationRoom = new LocationRoom("Point",latlon);

        //LocationRoom locationRoom = new LocationRoom("Point",new double[]{105.795662,20.988332});
        //String[] images = new String[]{"5bab1c7ad17a1e4453d5675f","5bab1c7ad17a1e4453d56760","5bab1d02d17a1e4453d56762"};
        String[] images = new String[imageHinhId.size()];
        images = imageHinhId.toArray(images);
        String address = edtAddress.getText().toString();
        String area = edtDienTich.getText().toString();
        RoomObject roomObject = new RoomObject(type,roomName,price,electricPrice,wterPrice,downPayment,locationRoom,address,images,area);
        Call<ResponseBody> call = fileService.uploadRoom("Bearer " + token,roomObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Upload thành công", Toast.LENGTH_SHORT).show();
                    imageHinhId.clear();
                    FragmentUpRoom.this.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Upload thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void init() {
        edtAddress = mMainView.findViewById(R.id.edtSearch);
        btnUpload = mMainView.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);
        imgBack = mMainView.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        edtDienTich = mMainView.findViewById(R.id.edtDienTich);
        edtPrice = mMainView.findViewById(R.id.edtPrice);
        edtRoomName = mMainView.findViewById(R.id.edtRoomName);
        btnSelectImage = mMainView.findViewById(R.id.btnSelectImage);
        gridView = mMainView.findViewById(R.id.gridImage);
        btnSelectImage.setOnClickListener(this);
        recyclerView = mMainView.findViewById(R.id.recyclerView);
        edtAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    public void showBottomDialog(int pos) {
        AddPhotoBottomDialogFragment addPhotoBottomDialogFragment =
                AddPhotoBottomDialogFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", imageBytes.size());
        if(imageBytes.size()>0){
            Toast.makeText(getActivity(), imageBytes.size()+"", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), imageBytes.size()+"", Toast.LENGTH_SHORT).show();
        }

        addPhotoBottomDialogFragment.setArguments(bundle);
        addPhotoBottomDialogFragment.setOnChooseReasonListener(this);
        addPhotoBottomDialogFragment.show(getActivity().getSupportFragmentManager(),
                "add_photo_dialog_fragment");
    }

    @Override
    public void onChooseReason(List<byte[]> listByte, int pos) {
        imageBytes.addAll(listByte);
        selectedImageAdapter = new SelectedImageAdapter(imageBytes,getContext(),this);
        gridView.setAdapter(selectedImageAdapter);



       /* selectedImageAdapter.notifyDataSetChanged();
        if(imageBytes.size()>=10){
            btnSelectImage.setEnabled(false);
        }else{
            btnSelectImage.setEnabled(true);
        }*/
       /* for (int i = 0;i<listByte.size();i++){
            ImageItem imageItem = new ImageItem();
            imageItem.setUri(listByte.get(pos));
            imageItemList.add(imageItem);
        }*/
       /* ImageAdapter imageAdapter = new ImageAdapter(imageItemList,getContext());
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(this);
        imageAdapter.setImages(imageItemList);
        imageAdapter.notifyDataSetChanged();*/
        selectedImageAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, int position) {
        showBottomDialog(AppUtils.PICK_IMAGE_1);
    }

    @Override
    public void deleteImage(final int pos, String id) {

        Call<ResponseBody> call = fileService.deleteImage("Bearer " + token,imageHinhId.get(pos) );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    imageBytes.remove(imageBytes.get(pos));
                    imageHinhId.remove(imageHinhId.get(pos));
                    selectedImageAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getAllUtilities(){
        Call<ResponseBody> call = fileService.getAllUtilities("code");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        //JSONArray  jsonArray = jsonObject.getJSONArray("rows");
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        for (int i = 0; i < 12; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            UtilityObject utilityObject =
                                    new UtilityObject(object.getString("id"),object.getString("name"),object.getString("code"));
                            utilityObjectList.add(utilityObject);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private List<Double> geoLocate() {
        List<Double> listPos = new ArrayList<>();
        String searchContent = edtAddress.getText().toString();
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchContent,1);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("geoLocate",e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d("geoLocate, a location",address.toString());
            listPos.add(address.getLatitude());
            listPos.add(address.getLongitude());
        }
        return listPos;
    }
}

