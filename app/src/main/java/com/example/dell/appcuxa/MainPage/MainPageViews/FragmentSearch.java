package com.example.dell.appcuxa.MainPage.MainPageViews;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.appcuxa.CustomeView.RobButton;
import com.example.dell.appcuxa.MainPage.Adapter.ListRoomAdapter;
import com.example.dell.appcuxa.ObjectModels.RoomInfo;
import com.example.dell.appcuxa.Utils.AppUtils;
import com.example.dell.appcuxa.R;
import com.example.dell.appcuxa.base.FragmentCommon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentSearch extends FragmentCommon{
    @BindView(R.id.recRoomList)
    public RecyclerView recyclerView;
    @BindView(R.id.lnQuickSearch)
    RobButton btnUpRoom;
    RobButton btnFindRoom;
    public LinearLayout edtQuickSearch;
    public ListRoomAdapter listRoomAdapter;
    public FragmentSearch(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(mView);
        recyclerView = mView.findViewById(R.id.recRoomList);
        edtQuickSearch = mView.findViewById(R.id.lnQuickSearch);
        btnUpRoom = mView.findViewById(R.id.btnUpRoom);
        btnFindRoom = mView.findViewById(R.id.btnFindRoom);
        btnUpRoom.setOnClickListener(this);
        List<RoomInfo> roomInfos = new ArrayList<>();
        RoomInfo info1 = new RoomInfo("https://cdn.homedit.com/wp-content/uploads/2011/05/kids-room-design3.jpg","Cho thuê phòng rộng 100m2","6tr2/phòng","Nguyễn Trãi, quận Thanh Xuân, Hà Nội","Tìm người thuê trọ");
        RoomInfo info2 = new RoomInfo("http://marceladick.com/wp-content/uploads/2016/01/beautiful-dining-rooms-awesome-with-picture-of-beautiful-dining-property-new-at-ideas.jpg","Cho thuê phòng rộng 100m2","6tr2/phòng","Nguyễn Trãi, quận Thanh Xuân, Hà Nội","Tìm người ở ghép");
        RoomInfo info3 = new RoomInfo("https://nhadepso.com/wp-content/uploads/2017/06/phong-khach-dep-den-kho-ta-nho-vao-thiet-ke-tuong-gach-doc-dao-7.jpg","Cho thuê phòng rộng 100m2","6tr2/phòng","Nguyễn Trãi, quận Thanh Xuân, Hà Nội","Tìm người thuê trọ");
        RoomInfo info4 = new RoomInfo("https://media-cdn.tripadvisor.com/media/photo-s/09/72/ad/99/radisson-blu-aqua-hotel.jpg","Cho thuê phòng rộng 100m2","6tr2/phòng","Nguyễn Trãi, quận Thanh Xuân, Hà Nội","0902219496");
        RoomInfo info5 = new RoomInfo("http://proinsar.co/wp-content/uploads/2018/09/living-room-beautiful-curtains-ideas-modern-curtain-inside-awesome-for-pretty.jpg","Cho thuê phòng rộng 100m2","6tr2/phòng","Nguyễn Trãi, quận Thanh Xuân, Hà Nội","Tìm người thuê trọ");
        roomInfos.add(info1);
        roomInfos.add(info2);
        roomInfos.add(info3);
        roomInfos.add(info4);
        roomInfos.add(info5);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        listRoomAdapter = new ListRoomAdapter(getContext(),roomInfos);
        recyclerView.setAdapter(listRoomAdapter);
        if(AppUtils.isServiceOk(getActivity())){
            btnFindRoom.setOnClickListener(this);
        }
        edtQuickSearch.setOnClickListener(this);

        return mView;
    }

    @Override
    protected void unit(View v) {

    }


    @Override
    protected void setPermission() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id){
            case R.id.lnQuickSearch:
               FragmentSearQuickAdva fragmentSearQuickAdva = new FragmentSearQuickAdva();
               //fragmentSearQuickAdva.setCancelable(false);
                fragmentSearQuickAdva.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
               fragmentSearQuickAdva.show(getFragmentManager(),"fragment_search");
                break;
            case R.id.btnUpRoom:
                FragmentUpRoom fragmentUpRoom= new FragmentUpRoom();
                //fragmentSearQuickAdva.setCancelable(false);
                fragmentUpRoom.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                fragmentUpRoom.show(getFragmentManager(),"fragment_uproom");
                break;
            case R.id.btnFindRoom:
                GoogleMapFragment mapFragment = new GoogleMapFragment();
                mapFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                mapFragment.show(getFragmentManager(),"fragment_map");
                break;
        }
    }
}
