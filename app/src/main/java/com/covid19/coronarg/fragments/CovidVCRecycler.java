package com.covid19.coronarg.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.coronarg.R;
import com.covid19.coronarg.adapter.CovidVC2Adapter;
import com.covid19.coronarg.adapter.CovidVCAdapter;
import com.covid19.coronarg.model.CovidVC;
import com.covid19.coronarg.model.CovidVC2;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import static com.covid19.coronarg.R.drawable.jeonbuk;
import static com.covid19.coronarg.R.drawable.jeonnam;
import static com.covid19.coronarg.R.drawable.sejong;
import static com.covid19.coronarg.R.drawable.seoul;
import static com.covid19.coronarg.R.drawable.ulsan;

public class CovidVCRecycler extends Fragment {
    private Button btn1, btn2;
    private RecyclerView recyclerView;
    private CovidVCAdapter adapter;
    private CovidVC2Adapter adapter2;
    private List<CovidVC> covidVCList;
    private List<CovidVC2> covidVC2List;

    int person = 51672400;
    double percent = 0.0;
    String tpcd = "", firstCnt = "", secondCnt = "";
    int image;
    String sidoNm = "", sidofirstTot = "", sidofirstCnt = "", sidosecondTot = "", sidosecondCnt = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
        getData2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_recycler2, container, false);

        btn1 = view.findViewById(R.id.re1_btn1);
        btn2 = view.findViewById(R.id.re1_btn2);

        recyclerView = view.findViewById(R.id.recycler2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CovidVCAdapter(getActivity(), covidVCList);
        recyclerView.setAdapter(adapter);

        btn1.setTextColor(Color.parseColor("#FF6200EE"));
        btn2.setTextColor(Color.parseColor("#FF000000"));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(Color.parseColor("#FF6200EE"));
                btn2.setTextColor(Color.parseColor("#FF000000"));

                btn1.setBackgroundResource(color_change1);
                btn2.setBackgroundResource(color_change1);

                recyclerView = view.findViewById(R.id.recycler2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                adapter = new CovidVCAdapter(getActivity(), covidVCList);
                recyclerView.setAdapter(adapter);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn2.setTextColor(Color.parseColor("#FF6200EE"));
                btn1.setTextColor(Color.parseColor("#FF000000"));

                btn1.setBackgroundResource(color_change1);
                btn2.setBackgroundResource(color_change1);

                recyclerView = view.findViewById(R.id.recycler2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                adapter2 = new CovidVC2Adapter(getActivity(), covidVC2List);
                recyclerView.setAdapter(adapter2);
            }
        });

        return view;
    }

    void getData() {
        StringBuffer buffer = new StringBuffer();

        String queryUrl = "https://nip.kdca.go.kr/irgd/cov19stats.do?list=all";

        try {
            URL url = new URL(queryUrl); // ???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is = url.openStream(); // url ????????? ??????????????? ??????
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            // inputstream ???????????? xml ????????????

            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("?????? ?????? ?????? \n\n");
                        covidVCList = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) ;

                        else if (tag.equals("tpcd")) {
                            tpcd = xpp.getText();
                            if (tpcd.equals("????????????(A)")) {
                                tpcd = "?????? 1??? ??????";
                            } else if (tpcd.equals("????????????(C): (A)+(B)")) {
                                tpcd = "?????? ?????? ??????";
                            }

                            xpp.next();
                        } else if (tag.equals("firstCnt")) {
                            if (tpcd.equals("?????? 1??? ??????")) {
                                firstCnt = "+" + xpp.getText();
                            } else if (tpcd.equals("?????? ?????? ??????")) {
                                firstCnt = "+" + xpp.getText();
                            }

                            xpp.next();
                        } else if (tag.equals("secondCnt")) {
                            if (tpcd.equals("?????? 1??? ??????")) {
                                secondCnt = xpp.getText();
                            } else if (tpcd.equals("?????? ?????? ??????")) {
                                secondCnt = xpp.getText();
                            }

                            percent = person / Integer.parseInt(secondCnt);

                            xpp.next();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) {

                            covidVCList.add(new CovidVC(tpcd, String.format("%.f", percent), secondCnt, firstCnt));
                        }
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getData2() {
        StringBuffer buffer = new StringBuffer();

        String queryUrl = "https://nip.kdca.go.kr/irgd/cov19stats.do?list=sido";

        try {
            URL url = new URL(queryUrl); // ???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is = url.openStream(); // url ????????? ??????????????? ??????
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            // inputstream ???????????? xml ????????????

            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("?????? ?????? ?????? \n\n");
                        covidVC2List = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) ;

                        else if (tag.equals("sidoNm")) {
                            sidoNm = xpp.getText();

                            if (sidoNm.equals("???????????????")) {
                                image = seoul;
                            } else if (sidoNm.equals("?????????")) {
                                image = gyeonggi;
                            } else if (sidoNm.equals("???????????????")) {
                                image = incheon;
                            } else if (sidoNm.equals("????????????")) {
                                image = gyeongnam;
                            } else if (sidoNm.equals("????????????")) {
                                image = gyeongbuk;
                            } else if (sidoNm.equals("?????????")) {
                                image = gangwondo;
                            } else if (sidoNm.equals("????????????")) {
                                image = chungnam;
                            } else if (sidoNm.equals("????????????")) {
                                image = chungbuk;
                            } else if (sidoNm.equals("?????????????????????")) {
                                image = sejong;
                            } else if (sidoNm.equals("???????????????")) {
                                image = daejeon;
                            } else if (sidoNm.equals("???????????????")) {
                                image = gwangju;
                            } else if (sidoNm.equals("????????????")) {
                                image = jeonnam;
                            } else if (sidoNm.equals("????????????")) {
                                image = jeonbuk;
                            } else if (sidoNm.equals("???????????????")) {
                                image = daegu;
                            } else if (sidoNm.equals("???????????????")) {
                                image = ulsan;
                            } else if (sidoNm.equals("???????????????")) {
                                image = busan;
                            } else if (sidoNm.equals("?????????????????????")) {
                                image = jeju;
                            }
                            xpp.next();
                        } else if (tag.equals("firstCnt")) {
                            sidofirstCnt = "+" + xpp.getText();
                            xpp.next();
                        } else if (tag.equals("firstTot")) {
                            sidofirstTot = xpp.getText();
                            xpp.next();
                        } else if (tag.equals("secondCnt")) {
                            sidosecondCnt = "+" + xpp.getText();
                            xpp.next();
                        } else if (tag.equals("secondTot")) {
                            sidosecondTot = xpp.getText();
                            xpp.next();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) {
                            covidVC2List.add(new CovidVC2(image, sidoNm,sidofirstTot, sidofirstCnt, sidosecondTot, sidosecondCnt));
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