package com.example.dell.appcuxa.MainPage.MainPageViews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dell.appcuxa.CustomeView.AddPhotoBottomDialogFragment;
import com.example.dell.appcuxa.CustomeView.RobButton;
import com.example.dell.appcuxa.CustomeView.RobEditText;
import com.example.dell.appcuxa.R;
import com.example.dell.appcuxa.Utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentUpRoom extends DialogFragment implements View.OnClickListener,AddPhotoBottomDialogFragment.OnChooseReasonListener {

    private View mMainView;
    @BindView(R.id.btnUpRoom)
    public RobButton btnUpload;
    @BindView(R.id.btnReset)
    public RobButton btnReset;
    @BindView(R.id.img1)
    public ImageView img1;
    @BindView(R.id.img2)
    public ImageView img2;
    @BindView(R.id.img3)
    public ImageView img3;
    @BindView(R.id.img4)
    public ImageView img4;
    @BindView(R.id.img5)
    public ImageView img5;
    @BindView(R.id.img6)
    public ImageView img6;
    @BindView(R.id.img7)
    public ImageView img7;
    @BindView(R.id.img8)
    public ImageView img8;
    @BindView(R.id.img9)
    public ImageView img9;
    @BindView(R.id.img10)
    public ImageView img10;
    @BindView(R.id.edtRoomName)
    public RobEditText edtRoomName;
    @BindView(R.id.edtPrice)
    public RobEditText edtPrice;
    @BindView(R.id.edtDienTich)
    public RobButton edtDienTich;
    @BindView(R.id.edtTinh)
    public RobEditText edtTinh;
    @BindView(R.id.edtHuyen)
    public RobEditText edtHuyen;
    @BindView(R.id.edtXa)
    public RobEditText edtXa;
    @BindView(R.id.edtAddressMoreDetail)
    public RobEditText edtAddressMoreDetail;
    @BindView(R.id.edtPhoneNo)
    public RobEditText edtPhoneNo;
    @BindView(R.id.edtNumOfPpl)
    public RobEditText edtNumOfPeople;
    @BindView(R.id.edtGender)
    public RobEditText edtGender;
    @BindView(R.id.edtDesc)
    public RobEditText edtDesc;


    ImageView imgBack;

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
            case R.id.img1:
                showBottomDialog(AppUtils.PICK_IMAGE_1);
                break;
            case R.id.img2:
                showBottomDialog(AppUtils.PICK_IMAGE_2);
                break;
            case R.id.img3:
                showBottomDialog(AppUtils.PICK_IMAGE_3);
                break;
            case R.id.img4:
                showBottomDialog(AppUtils.PICK_IMAGE_4);
                break;
            case R.id.img5:
                showBottomDialog(AppUtils.PICK_IMAGE_5);
                break;
            case R.id.img6:
                showBottomDialog(AppUtils.PICK_IMAGE_6);
                break;
            case R.id.img7:
                showBottomDialog(AppUtils.PICK_IMAGE_7);
                break;
            case R.id.img8:
                showBottomDialog(AppUtils.PICK_IMAGE_8);
                break;
            case R.id.img9:
                showBottomDialog(AppUtils.PICK_IMAGE_9);
                break;
            case R.id.img10:
                showBottomDialog(AppUtils.PICK_IMAGE_10);
                break;

        }
    }

    public void init() {
        imgBack = mMainView.findViewById(R.id.imgBack);
        img1 = mMainView.findViewById(R.id.img1);
        img2 = mMainView.findViewById(R.id.img2);
        img3 = mMainView.findViewById(R.id.img3);
        img4 = mMainView.findViewById(R.id.img4);
        img5 = mMainView.findViewById(R.id.img5);
        img6 = mMainView.findViewById(R.id.img6);
        img7 = mMainView.findViewById(R.id.img7);
        img8 = mMainView.findViewById(R.id.img8);
        img9 = mMainView.findViewById(R.id.img9);
        img10 = mMainView.findViewById(R.id.img10);
        imgBack.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        img7.setOnClickListener(this);
        img8.setOnClickListener(this);
        img9.setOnClickListener(this);
        img10.setOnClickListener(this);
    }

    public void showBottomDialog(int pos) {
        AddPhotoBottomDialogFragment addPhotoBottomDialogFragment =
                AddPhotoBottomDialogFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        addPhotoBottomDialogFragment.setArguments(bundle);
        addPhotoBottomDialogFragment.setOnChooseReasonListener(this);
        addPhotoBottomDialogFragment.show(getActivity().getSupportFragmentManager(),
                "add_photo_dialog_fragment");
    }

    @Override
    public void onChooseReason(byte[] reportType, int pos) {
        Bitmap bmp = BitmapFactory.decodeByteArray(reportType, 0, reportType.length);
        switch (pos){
            case 1:
                img1.setImageBitmap(bmp);
                break;
            case 2:
                img2.setImageBitmap(bmp);
                break;
            case 3:
                img3.setImageBitmap(bmp);
                break;
            case 4:
                img4.setImageBitmap(bmp);
                break;
            case 5:
                img5.setImageBitmap(bmp);
                break;
            case 6:
                img6.setImageBitmap(bmp);
                break;
            case 7:
                img7.setImageBitmap(bmp);
                break;
            case 8:
                img8.setImageBitmap(bmp);
                break;
            case 9:
                img9.setImageBitmap(bmp);
                break;
            case 10:
                img10.setImageBitmap(bmp);
                break;

        }
    }
}

