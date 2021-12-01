package com.covid19.coronarg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.coronarg.R;
import com.covid19.coronarg.adapter.CovidHPAdapter;
import com.covid19.coronarg.model.CovidHP;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CovidHPRecycler extends Fragment {

    private RecyclerView recyclerView;
    private CovidHPAdapter adapter3;
    private List<CovidHP> covidHPList;
    Spinner spinner;

    String[] items = {"서울특별시", "경기도", "인천광역시", "경상북도", "경상남도", "강원도",
            "충청남도", "충청북도", "세종특별자치시", "대전광역시", "광주광역시", "전라남도", "전라북도",
            "대구광역시", "울산광역시", "부산광역시", "제주도"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        covidHPList = new ArrayList<>();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_recycler3, container, false);

        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                covidHPList.clear();

                if (items[position].equals("서울특별시")) {
                    getData("서울특별시");

                }
                if (items[position].equals("경기도")) {
                    getData("경기도");

                }
                if (items[position].equals("인천광역시")) {
                    getData("인천광역시");

                }
                if (items[position].equals("경상북도")) {
                    getData("경상북도");

                }
                if (items[position].equals("경상남도")) {
                    getData("경상남도");

                }
                if (items[position].equals("강원도")) {
                    getData("강원도");

                }
                if (items[position].equals("충청남도")) {
                    getData("충청남도");

                }
                if (items[position].equals("충청북도")) {
                    getData("충청북도");

                }
                if (items[position].equals("세종특별자치시")) {
                    getData("세종특별자치시");

                }
                if (items[position].equals("대전광역시")) {
                    getData("대전광역시");

                }
                if (items[position].equals("광주광역시")) {
                    getData("광주광역시");

                }
                if (items[position].equals("전라남도")) {
                    getData("전라남도");

                }
                if (items[position].equals("전라북도")) {
                    getData("전라북도");

                }
                if (items[position].equals("대구광역시")) {
                    getData("대구광역시");

                }
                if (items[position].equals("울산광역시")) {
                    getData("울산광역시");

                }
                if (items[position].equals("부산광역시")) {
                    getData("부산광역시");

                }
                if (items[position].equals("제주도")) {
                    getData("제주도");

                }
            }

            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // selectedText.setText("선택: ");
            }
        });

        recyclerView = view.findViewById(R.id.recycler3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter3 = new CovidHPAdapter(getActivity(), covidHPList);
        recyclerView.setAdapter(adapter3);

        return view;
    }

    void getData(String area) {

        String queryUrl = "https://api.odcloud.kr/api/apnmOrg/v1/list?" +
                "page=1&perPage=50&serviceKey=o9W84gRbkcCag%2BySFl4QRLdQyuGskfjfFdk9Oz08" +
                "%2Fxej0OMjzRfaYZMTUDQmoGCTTAILRTlbbucTUeYDFXQlMw%3D%3D";

        try {
            URL url = new URL(queryUrl);

            InputStream is = url.openStream();
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String result = new String(buffer, "utf-8");

            String orgZipaddr = "", lunchSttTm = "", lunchEndTm = "", lunch = "", sttTm = "", opening = "", orgTlno="";

            JSONObject json = new JSONObject(result);
            JSONArray jarr = json.getJSONArray("currentCount");

            for (int i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);

                String orgnm = json.getString("orgnm");
                if (orgnm.substring(0, 5).equals(area)) {
                    orgZipaddr = json.getString("orgZipaddr");
                    lunchSttTm = json.getString("lunchSttTm");
                    lunchEndTm = json.getString("lunchEndTm");
                    lunch = lunchSttTm + "~" + lunchEndTm;
                    sttTm = json.getString("sttTm");

                    if (sttTm.equals("null")) {
                        opening = "24시간 영업";
                    } else {
                        String endTm = json.getString("endTm");
                        opening = sttTm + "~" + endTm;
                    }
                   orgTlno = json.getString("orgTlno");

                } else if (orgnm.substring(0, 4).equals(area)) {
                    orgZipaddr = json.getString("orgZipaddr");
                    lunchSttTm = json.getString("lunchSttTm");
                    lunchEndTm = json.getString("lunchEndTm");
                    lunch = lunchSttTm + "~" + lunchEndTm;
                    sttTm = json.getString("sttTm");

                    if (sttTm.equals("null")) {
                        opening = "24시간 영업";
                    } else {
                        String endTm = json.getString("endTm");
                        opening = sttTm + "~" + endTm;
                    }
                    orgTlno = json.getString("orgTlno");

                } else if (orgnm.substring(0, 3).equals(area)) {
                    orgZipaddr = json.getString("orgZipaddr");
                    lunchSttTm = json.getString("lunchSttTm");
                    lunchEndTm = json.getString("lunchEndTm");
                    lunch = lunchSttTm + "~" + lunchEndTm;
                    sttTm = json.getString("sttTm");

                    if (sttTm.equals("null")) {
                        opening = "24시간 영업";
                    } else {
                        String endTm = json.getString("endTm");
                        opening = sttTm + "~" + endTm;
                    }
                    orgTlno = json.getString("orgTlno");

                } else if (orgnm.substring(0, 7).equals(area)) {
                    orgZipaddr = json.getString("orgZipaddr");
                    lunchSttTm = json.getString("lunchSttTm");
                    lunchEndTm = json.getString("lunchEndTm");
                    lunch = lunchSttTm + "~" + lunchEndTm;
                    sttTm = json.getString("sttTm");

                    if (sttTm.equals("null")) {
                        opening = "24시간 영업";
                    } else {
                        String endTm = json.getString("endTm");
                        opening = sttTm + "~" + endTm;
                    }
                    orgTlno = json.getString("orgTlno");

                }

                covidHPList.add(new CovidHP(orgnm, orgZipaddr, lunch, opening, orgTlno));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}