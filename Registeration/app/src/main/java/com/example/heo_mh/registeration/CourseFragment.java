package com.example.heo_mh.registeration;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //    Spinner, Adapter
    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;

    private String courseUniversity = "";

    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;


    /**
     * 액티비티가 생성되었을 때 처리하는 부분
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RadioGroup courseUniversityGroup = (RadioGroup) getView().findViewById(R.id.courseUniversityGroup);
        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        termSpinner = (Spinner) getView().findViewById(R.id.termSpinner);
        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        majorSpinner = (Spinner) getView().findViewById(R.id.majorSpinner);

        // 라디오 버튼 (학부 or 대학원)
        courseUniversityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton courseButton = (RadioButton) getView().findViewById(checkedId);
                courseUniversity = courseButton.getText().toString();

                // 년도, 학기 스피너 설정
                yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
                yearSpinner.setAdapter(yearAdapter);
                termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.term, android.R.layout.simple_spinner_dropdown_item);
                termSpinner.setAdapter(termAdapter);

                // 영역, 과목 설정
                if (courseUniversity.equals("학부")) {
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityRefinementMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                } else if (courseUniversity.equals("대학원")) {
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }
        });

        // 영역 스피너 설정
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (areaSpinner.getSelectedItem().equals("교양및기타")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityRefinementMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
                if (areaSpinner.getSelectedItem().equals("전공")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
                if (areaSpinner.getSelectedItem().equals("일반대학원")) {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*
            리스트 뷰 설정
         */
        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList, this);
        courseListView.setAdapter(adapter);

        //  검색 버튼 이벤트
        Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /**
     * 강의 목록을 가져오는 클래스
     */
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;  // 웹 서버 주소

        /**
         * 웹서버 주소와 요청 값들 설정
         */
        @Override
        protected void onPreExecute() {
            try {
                target = "http://121.137.217.100:3003/course/list?courseUniversity=" + URLEncoder.encode(courseUniversity, "UTF-8") +
                        "&courseYear=" + URLEncoder.encode(yearSpinner.getSelectedItem().toString().substring(0, 4), "UTF-8") +
                        "&courseTerm=" + URLEncoder.encode(termSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&courseArea=" + URLEncoder.encode(areaSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&courseMajor=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * 서버에서 응답한 것을 파싱해서 리턴해준다.
         * @param params : JSON Response Value
         * @return  : String
         */
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 실행 전에 값들을 전부 할당한다.
         * @param result : doInBackground() 리턴값
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                courseList.clear();         // 이전 결과 삭제
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int courseID;               // 강의 고유 번호
                String courseUniversity;    // 학부 혹은 대학원
                int courseYear;             // 해당 년도
                String courseTerm;          // 해당 학기
                String courseArea;          // 해당 영역
                String courseMajor;         // 해당 학과
                String courseGrade;         // 해당 학년
                String courseTitle;         // 강의 제목
                int courseCredit;           // 강의 학점
                int courseDivide;           // 강의 분반
                int coursePersonnel;        // 강의 제한 인원
                String courseProfessor;     // 강의 교수
                String courseTime;          // 강의 시간대
                String courseRoom;          // 강의실

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseUniversity = object.getString("courseUniversity");
                    courseYear = object.getInt("courseYear");
                    courseTerm = object.getString("courseTerm");
                    courseArea = object.getString("courseArea");
                    courseMajor = object.getString("courseMajor");
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseRoom = object.getString("courseRoom");

                    courseList.add(new Course(courseID, courseUniversity, courseYear, courseTerm,
                            courseArea, courseMajor, courseGrade, courseTitle, courseCredit, courseDivide,
                            coursePersonnel, courseProfessor, courseTime, courseRoom));

                    count++;
                }

                // 수강 신청 가능한 과목이 없을 경우
                if(count == 0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 강의가 없습니다.\n날짜를 확인하세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }

                // 새로고침
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
