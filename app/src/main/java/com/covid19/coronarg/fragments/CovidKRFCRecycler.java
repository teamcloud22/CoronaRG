package com.covid19.coronarg.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.coronarg.R;
import com.covid19.coronarg.adapter.CovidFCAdapter;
import com.covid19.coronarg.adapter.CovidKRAdapter;
import com.covid19.coronarg.model.CovidFC;
import com.covid19.coronarg.model.CovidKR;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.covid19.coronarg.R.drawable.*;
import static com.covid19.coronarg.R.drawable.busan;
import static com.covid19.coronarg.R.drawable.chungbuk;
import static com.covid19.coronarg.R.drawable.chungnam;
import static com.covid19.coronarg.R.drawable.color_change1;
import static com.covid19.coronarg.R.drawable.daegu;
import static com.covid19.coronarg.R.drawable.daejeon;
import static com.covid19.coronarg.R.drawable.gangwondo;
import static com.covid19.coronarg.R.drawable.gwangju;
import static com.covid19.coronarg.R.drawable.gyeongbuk;
import static com.covid19.coronarg.R.drawable.gyeonggi;
import static com.covid19.coronarg.R.drawable.gyeongnam;
import static com.covid19.coronarg.R.drawable.incheon;
import static com.covid19.coronarg.R.drawable.jeju;
import static com.covid19.coronarg.R.drawable.sejong;
import static com.covid19.coronarg.R.drawable.seoul;
import static com.covid19.coronarg.R.drawable.ulsan;

public class CovidKRFCRecycler extends Fragment {
    private Button btn1, btn2;
    private RecyclerView recyclerView;
    private CovidKRAdapter adapter;
    private CovidFCAdapter adapter2; //추가
    private List<CovidKR> covidkrList;
    private List<CovidFC> covidfcList;//추가

    int image;
    String gubun = "", defCnt = "", incDec = "", overFlowCnt = "", deathCnt = "", isolIngCnt = "", isolClearCnt = "";
    String areaNm = "", nationNm = "", natDefCnt = "", natDeathRate = "";

    Spinner spinner;
    String[] items = {"아시아", "중동", "아메리카", "유럽", "오세아니아", "아프리카"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
        getData2("아시아");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_recycler, container, false);

        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                covidfcList.clear();

                if (items[position].equals("아시아")) {
                    getData2("아시아");
                }
                if (items[position].equals("중동")) {
                    getData2("중동");
                }
                if (items[position].equals("아메리카")) {
                    getData2("아메리카");
                }
                if (items[position].equals("유럽")) {
                    getData2("유럽");
                }
                if (items[position].equals("오세아니아")) {
                    getData2("오세아니아");
                }
                if (items[position].equals("아프리카")) {
                    getData2("아프리카");
                }
            }

            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // selectedText.setText("선택: ");
            }
        });

        btn1 = view.findViewById(R.id.re1_btn1);
        btn2 = view.findViewById(R.id.re1_btn2);

        recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CovidKRAdapter(getActivity(), covidkrList);
        recyclerView.setAdapter(adapter);

        btn1.setTextColor(Color.parseColor("#FF6200EE"));
        btn2.setTextColor(Color.parseColor("#FF000000"));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.GONE);

                btn1.setTextColor(Color.parseColor("#FF6200EE"));
                btn2.setTextColor(Color.parseColor("#FF000000"));

                btn1.setBackgroundResource(color_change1);
                btn2.setBackgroundResource(color_change1);

                recyclerView = view.findViewById(R.id.recycler1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                adapter = new CovidKRAdapter(getActivity(), covidkrList);
                recyclerView.setAdapter(adapter);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                btn2.setTextColor(Color.parseColor("#FF6200EE"));
                btn1.setTextColor(Color.parseColor("#FF000000"));

                btn1.setBackgroundResource(color_change1);
                btn2.setBackgroundResource(color_change1);

                recyclerView = view.findViewById(R.id.recycler1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                adapter2 = new CovidFCAdapter(getActivity(), covidfcList);
                recyclerView.setAdapter(adapter2);

            }
        });

        return view;
    }

    void getData() {
        StringBuffer buffer = new StringBuffer();

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");

        String getTime = sdf.format(date);

        String queryUrl = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=o9W84gRbkcCag%2BySFl4QRLdQyuGskfjfFdk9Oz08%2Fxej0OMjzRfaYZMTUDQmoGCTTAILRTlbbucTUeYDFXQlMw%3D%3D"
                + "&pageNo=1&numOfRows=10&startCreateDt=" + getTime + "&endCreateDt=" + getTime;

        try {
            URL url = new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            // inputstream 으로부터 xml 입력받기

            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작 단계 \n\n");
                        covidkrList = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) ;

                        else if (tag.equals("gubun")) {
                            gubun = xpp.getText();
                            if (gubun.equals("서울")) {
                                image = seoul;
                            } else if (gubun.equals("경기")) {
                                image = gyeonggi;
                            } else if (gubun.equals("인천")) {
                                image = incheon;
                            } else if (gubun.equals("경남")) {
                                image = gyeongnam;
                            } else if (gubun.equals("경북")) {
                                image = gyeongbuk;
                            } else if (gubun.equals("강원")) {
                                image = gangwondo;
                            } else if (gubun.equals("충남")) {
                                image = chungnam;
                            } else if (gubun.equals("충북")) {
                                image = chungbuk;
                            } else if (gubun.equals("세종")) {
                                image = sejong;
                            } else if (gubun.equals("대전")) {
                                image = daejeon;
                            } else if (gubun.equals("광주")) {
                                image = gwangju;
                            } else if (gubun.equals("전남")) {
                                image = jeonnam;
                            } else if (gubun.equals("전북")) {
                                image = jeonbuk;
                            } else if (gubun.equals("대구")) {
                                image = daegu;
                            } else if (gubun.equals("울산")) {
                                image = ulsan;
                            } else if (gubun.equals("부산")) {
                                image = busan;
                            } else if (gubun.equals("제주")) {
                                image = jeju;
                            }

                            xpp.next();
                        } else if (tag.equals("defCnt")) {
                            defCnt = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("incDec")) {
                            incDec = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("overFlowCnt")) {
                            overFlowCnt = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("deathCnt")) {
                            deathCnt = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("isolIngCnt")) {
                            isolIngCnt = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("isolClearCnt")) {
                            isolClearCnt = xpp.getText();
                            xpp.next();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) {
                            covidkrList.add(new CovidKR(image, gubun, defCnt, incDec, overFlowCnt, deathCnt, isolIngCnt, isolClearCnt));
                        }
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getData2(String area) { //해외 코로나 발생
        StringBuffer buffer = new StringBuffer();

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");

        String getTime = sdf.format(date);

        String queryUrl = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19NatInfStateJson?serviceKey=" +
                "o9W84gRbkcCag%2BySFl4QRLdQyuGskfjfFdk9Oz08%2Fxej0OMjzRfaYZMTUDQmoGCTTAILRTlbbucTUeYDFXQlMw%3D%3D" +
                "&pageNo=1&numOfRows=10&startCreateDt=" + getTime + "&endCreateDt=" + getTime;

        try {
            URL url = new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            // inputstream 으로부터 xml 입력받기

            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작 단계 \n\n");
                        covidfcList = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) ;

                        else if (tag.equals("areaNm")) {
                            areaNm = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("nationNm")) {
                            if (areaNm.equals("아시아") && area.equals("아시아")) {
                                nationNm = xpp.getText();
                            } else if (areaNm.equals("중동") && area.equals("중동")) {
                                nationNm = xpp.getText();
                            } else if (areaNm.equals("아메리카") && area.equals("아메리카")) {
                                nationNm = xpp.getText();
                            } else if (areaNm.equals("유럽") && area.equals("유럽")) {
                                nationNm = xpp.getText();
                            } else if (areaNm.equals("오세아니아") && area.equals("오세아니아")) {
                                nationNm = xpp.getText();
                            } else if (areaNm.equals("아프리카") && area.equals("아프리카")) {
                                nationNm = xpp.getText();
                            }
                            xpp.next();

                        } else if (tag.equals("natDefCnt") && nationNm != null) {
                            natDefCnt = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("natDeathRate") && nationNm != null) {
                            natDeathRate = xpp.getText() + "%";
                            xpp.next();
                        } else if (tag.equals("natDeathCnt") && nationNm != null) {
                            natDeathRate += "(" + xpp.getText() + ")";
                            xpp.next();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item") && nationNm != null) {
                            covidfcList.add(new CovidFC(areaNm, nationNm, natDefCnt, natDeathRate));
                        }
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}