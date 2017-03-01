package com.example.heo_mh.registeration;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
 * Created by Heo-MH on 2017-02-21.
 * 수강 리스트 어댑터
 */

public class CourseListAdapter extends BaseAdapter{

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    private Schedule schedule = new Schedule();
    private List<Integer> courseIDList;


    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
        schedule = new Schedule();
        courseIDList = new ArrayList<>();
        new BackgroundTask().execute();     // 해당 회원의 수강 목록을 불러온다.
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parentGroup) {
        View v = convertView.inflate(context, R.layout.course, null);

        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        // 뷰에 값들을 삽입
        if(courseList.get(position).getCourseGrade().equals("제한 없음") || courseList.get(position).getCourseGrade().equals("")){
            courseGrade.setText("모든 학년");
        }else{
            courseGrade.setText(courseList.get(position).getCourseGrade() + "학년");
        }

        courseTitle.setText(courseList.get(position).getCourseTitle());
        courseCredit.setText(courseList.get(position).getCourseCredit() + "학점");
        courseDivide.setText(courseList.get(position).getCourseDivide() + "분반");

        if(courseList.get(position).getCoursePersonnel() == 0){
            coursePersonnel.setText("인원 제한 없음");
        }else{
            coursePersonnel.setText("제한 인원 : " + courseList.get(position).getCoursePersonnel() + "명");
        }

        if(courseList.get(position).getCourseProfessor().equals("")){
            courseProfessor.setText("개인 연구");
        }else{
            courseProfessor.setText(courseList.get(position).getCourseProfessor() + "교수님");
        }

        courseTime.setText(courseList.get(position).getCourseTime() + "");

        // 태그 입력
        v.setTag(courseList.get(position).getCourseID());

        // 강의 추가 버튼
        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                    해당 시간대 중복 확인
                 */
                boolean validate = false;
                validate = schedule.validate(courseList.get(position).getCourseTime());

                // 똑같은 강의를 추가했을 경우
                if(!alreadyIn(courseIDList, courseList.get(position).getCourseID())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                            .setPositiveButton("다시 시도", null)
                            .create();
                    dialog.show();
                }else if(validate == false){
                    // 시간대가 중복되었을 경우
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
                            .setPositiveButton("다시 시도", null)
                            .create();
                    dialog.show();
                }else{
                    // 등록 리스너
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject obj = jsonObject.getJSONObject("response");
                                boolean success = obj.getBoolean("success");
                                // 사용 가능한 아이디일 경우
                                if(success){
                                    // parent.getActivity() : 프래그먼트에서 현재 액티비티를 가져온다.
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    courseIDList.add(courseList.get(position).getCourseID());
                                    schedule.addSchedule(courseList.get(position).getCourseTime());

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의 추가 실패.....")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();

                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    };

                    // 강의 추가 요청
                    AddRequest addRequest = new AddRequest(userID, courseList.get(position).getCourseID() + "", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }
            }
        });

        // 뷰 리턴
        return  v;
    }

    /**
     * 해당 회원이 수강 신청한 과목들을 가져오는 클래스
     */
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;  // 웹 서버 주소

        @Override
        protected void onPreExecute() {
            try{
                target = "http://121.137.217.100:3003/course/selectOne?userID="+ URLEncoder.encode(userID, "UTF-8");
            }catch(Exception e){
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
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            }catch (Exception e){
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
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    String courseProfessor = object.getString("courseProfessor");
                    String courseTime = object.getString("courseTime");
                    int courseID = object.getInt("courseID");
                    courseIDList.add(courseID);
                    schedule.addSchedule(courseTime);
                    count++;
                }
                // 새로 고침 (내가 추가했다)
                notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 현재 수업아이디(수강 번호)가 이미 들어가있다면 false
     * @param courseIDList
     * @param item
     * @return
     */
    public boolean alreadyIn(List<Integer> courseIDList, int item){
        for(int i=0; i<courseIDList.size(); i++){
            if(courseIDList.get(i) == item){
                return false;
            }
        }
        return true;
    }
}
