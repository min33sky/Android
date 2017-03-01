package com.example.heo_mh.registeration;

/**
 * Created by Heo-MH on 2017-02-24.
 * 시간표
 */

public class Schedule {
    // 0~13교시까지 있다.
    private String monday[] = new String[14];
    private String tuesday[] = new String[14];
    private String wednesday[] = new String[14];
    private String thursday[] = new String[14];
    private String friday[] = new String[14];

    public Schedule() {
        for(int i=0; i<14; i++){
            monday[i] = "";
            tuesday[i] = "";
            wednesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
        }
    }

    /**
     * 해당 요일에 수업을 추가한다.
     * @param scheduleText
     */
    public void addSchedule(String scheduleText){
        int temp;
        /*
            ex) 월:[3][4]화:[2][3] 에서 숫자를 파싱해서 해당 요일에 집어넣는다.
         */
        if((temp = scheduleText.indexOf("월")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("화")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("수")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("목")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("금")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))] = "수업";
                }
            }
        }

    }

    /**
     * 시간표 내 시간 중복 확인
     */
    public boolean validate(String scheduleText){
        // 수강 시간이 적혀있지 않을 땐 삽입 가능
        if(scheduleText.equals("")){
            return true;
        }

        // 중복 확인
        int temp;
        if((temp = scheduleText.indexOf("금")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    // 히당 요일의 시간이 공백이 아니다 -> 수업이 있으므로 시간표에 삽입 불가능하다.
                    if(!friday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }
        if((temp = scheduleText.indexOf("월")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    // 히당 요일의 시간이 공백이 아니다 -> 수업이 있으므로 시간표에 삽입 불가능하다.
                    if(!monday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }
        if((temp = scheduleText.indexOf("화")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    // 히당 요일의 시간이 공백이 아니다 -> 수업이 있으므로 시간표에 삽입 불가능하다.
                    if(!tuesday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }
        if((temp = scheduleText.indexOf("수")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    // 히당 요일의 시간이 공백이 아니다 -> 수업이 있으므로 시간표에 삽입 불가능하다.
                    if(!wednesday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }
        if((temp = scheduleText.indexOf("목")) > -1){
            temp += 2;                      // '[' 부터 시작하도록 변수 설정
            int startPoint = temp;          // 숫자를 파싱하기 위한 시작점
            int endPoint = temp;            // 숫자를 파싱하기 위한 종착점

            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i) != ':'; i++){
                // 시작점 설정
                if(scheduleText.charAt(i) == '['){
                    startPoint = i;
                }
                // 종착점 설정 및 해당 요일에 수업 추가
                if(scheduleText.charAt(i) == ']'){
                    endPoint = i;
                    // 히당 요일의 시간이 공백이 아니다 -> 수업이 있으므로 시간표에 삽입 불가능하다.
                    if(!thursday[Integer.parseInt(scheduleText.substring(startPoint+1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }

        // 중복된 게 없으므로 수강 신청이 가능하다.
        return true;
    }
}
