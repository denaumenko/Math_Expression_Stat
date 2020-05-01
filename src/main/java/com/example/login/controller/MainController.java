package com.example.login.controller;

import com.example.login.domain.TimeTable;
import com.example.login.domain.User;
import com.example.login.domain.UserStat;
import com.example.login.repos.TimeRepo;
import com.example.login.repos.UserStatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {

    //for one session, after it will be stored in database
    //each cell for each user, but in the end of session
    //cells will be deleted
    int [] count_of_questions = new int[1000];
    int [] count_of_correct_answers = new int[1000];
    int [] count_of_incorrect_answers = new int[1000];
    String [] dates = new String[1000];
    String [] time_of_question = new String[1000];
    int [][] time_per_question = new int[1000][100];




    //for user-accounts
    @Autowired
    private UserStatRepo userStatRepo;

    //for manupulating data with different time stamps
    @Autowired
    private TimeRepo timeRepo;




    @GetMapping("/")
    public String greeting(Map<String, Object> model,@AuthenticationPrincipal User user) {
        //all counters equals to null, for new session
        count_of_questions[Math.toIntExact(user.getId())] = 0;
        count_of_correct_answers[Math.toIntExact(user.getId())] = 0;
        count_of_incorrect_answers[Math.toIntExact(user.getId())] = 0;
        dates[Math.toIntExact(user.getId())] = null;
        time_of_question[Math.toIntExact(user.getId())] = null;
        for (int i =0 ; i < 99 ;i++) {
            time_per_question[Math.toIntExact(user.getId())][i] = 0;
        }

        // data of enter user was stamped
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        dates[Math.toIntExact(user.getId())] = strDate;
        TimeTable timeTable = new TimeTable("Enter in system",  date,user, count_of_correct_answers[Math.toIntExact(user.getId())] ,count_of_incorrect_answers[Math.toIntExact(user.getId())], count_of_questions[Math.toIntExact(user.getId())],0,0,0);
        timeRepo.save(timeTable);
        model.put("username", user.getUsername());
        return "greeting";
    }

    @GetMapping("/endtesting")
    public String endtesting(Map<String, Object> model,@AuthenticationPrincipal User user) throws ParseException {
        // data of end testing was stamped
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date enddate = new Date();
        Date startdate = dateFormat.parse(dates[Math.toIntExact(user.getId())]);


        //metrics are calculating
        double percentage =  ((double) 100 * count_of_correct_answers[Math.toIntExact(user.getId())]) / count_of_questions[Math.toIntExact(user.getId())] ;

        int len = 0;
        for (int i=0; i<time_per_question[Math.toIntExact(user.getId())].length; i++){
            if (time_per_question[Math.toIntExact(user.getId())][i] != 0)
                len++;
        }
        int [] cleanarr = new int[len];
        for (int i=0, j=0; i<time_per_question[Math.toIntExact(user.getId())].length; i++){
            if (time_per_question[Math.toIntExact(user.getId())][i] != 0) {
                cleanarr[j] = time_per_question[Math.toIntExact(user.getId())][i];
                j++;
            }
        }

        int max;
        int min;
        double avg;
        if (count_of_questions[Math.toIntExact(user.getId())] != 0){
            max = Arrays.stream(cleanarr).max().getAsInt();
            min = Arrays.stream(cleanarr).min().getAsInt();
            avg = Arrays.stream(cleanarr).average().getAsDouble();
        }else {
            max = 0;
            min = 0;
            avg = Double.valueOf(0);
        }


        // report was stored in db
        TimeTable timeTable = new TimeTable("Exit",  enddate,user, count_of_correct_answers[Math.toIntExact(user.getId())] ,count_of_incorrect_answers[Math.toIntExact(user.getId())], count_of_questions[Math.toIntExact(user.getId())],max,min,avg);
        timeRepo.save(timeTable);

        // Instatiation new variables for view

        // for all time
        int totalMaxAllTime;
        int totalMinAllTime;
        Double totalAvgAllTime;
        int totalEnters;
        int totalNumberofQustions;
        int totalNumberofQuestionsCorect;
        double totalpercentage;

        //for last day
        int dayMaxAllTime;
        int dayMinAllTime;
        Double dayAvgAllTime;
        int dayEnters;
        int dayNumberofQustions;
        int dayNumberofQuestionsCorect;
        double daypercentage;

        //for last month
        int monthMaxAllTime;
        int monthMinAllTime;
        Double monthAvgAllTime;
        int monthEnters;
        int monthNumberofQustions;
        int monthNumberofQuestionsCorect;
        double monthpercentage;

        //for last year
        int yearMaxAllTime;
        int yearMinAllTime;
        Double yearAvgAllTime;
        int yearEnters;
        int yearNumberofQustions;
        int yearNumberofQuestionsCorect;
        double yearpercentage;


        // retrieving data from database
        if (timeRepo.selectTotalsMax(Math.toIntExact(user.getId()))!=null){

            //for all time
            totalAvgAllTime = timeRepo.selectTotalsAvg(Math.toIntExact(user.getId()));
            totalMaxAllTime = timeRepo.selectTotalsMax(Math.toIntExact(user.getId()));
            totalMinAllTime = timeRepo.selectTotalsMin(Math.toIntExact(user.getId()));
            totalEnters = timeRepo.selectTotalsEnters(Math.toIntExact(user.getId()));
            totalNumberofQustions = timeRepo.amountquestions(Math.toIntExact(user.getId()));
            totalNumberofQuestionsCorect = timeRepo.amountquestionscorect(Math.toIntExact(user.getId()));
            totalpercentage =  ((double) (totalNumberofQuestionsCorect *100)/totalNumberofQustions);

            //for last day

            dayAvgAllTime = timeRepo.selectdayAvg(Math.toIntExact(user.getId()));
            dayMaxAllTime = timeRepo.selectdayMax(Math.toIntExact(user.getId()));
            dayMinAllTime = timeRepo.selectdayMin(Math.toIntExact(user.getId()));
            dayEnters = timeRepo.selectdayEnters(Math.toIntExact(user.getId()));
            dayNumberofQustions = timeRepo.amountquestionsday(Math.toIntExact(user.getId()));
            dayNumberofQuestionsCorect = timeRepo.amountquestionscorectday(Math.toIntExact(user.getId()));
            daypercentage =  ((double) (dayNumberofQuestionsCorect *100)/dayNumberofQustions);

            //for last month

            monthAvgAllTime = timeRepo.selectmonthAvg(Math.toIntExact(user.getId()));
            monthMaxAllTime = timeRepo.selectmonthMax(Math.toIntExact(user.getId()));
            monthMinAllTime = timeRepo.selectmonthMin(Math.toIntExact(user.getId()));
            monthEnters = timeRepo.selectmonthEnters(Math.toIntExact(user.getId()));
            monthNumberofQustions = timeRepo.amountquestionsmonth(Math.toIntExact(user.getId()));
            monthNumberofQuestionsCorect = timeRepo.amountquestionscorectmonth(Math.toIntExact(user.getId()));
            monthpercentage =  ((double) (monthNumberofQuestionsCorect *100)/monthNumberofQustions);

            //for last year

            yearAvgAllTime = timeRepo.selectyearAvg(Math.toIntExact(user.getId()));
            yearMaxAllTime = timeRepo.selectyearMax(Math.toIntExact(user.getId()));
            yearMinAllTime = timeRepo.selectyearMin(Math.toIntExact(user.getId()));
            yearEnters = timeRepo.selectyearEnters(Math.toIntExact(user.getId()));
            yearNumberofQustions = timeRepo.amountquestionsyear(Math.toIntExact(user.getId()));
            yearNumberofQuestionsCorect = timeRepo.amountquestionscorectyear(Math.toIntExact(user.getId()));
            yearpercentage =  ((double) (yearNumberofQuestionsCorect *100)/yearNumberofQustions);

        }else {
            //if it first attempt of testing
            totalAvgAllTime = avg;
            totalMaxAllTime = max;
            totalMinAllTime = min;
            totalEnters = 1;
            totalNumberofQustions = count_of_questions[Math.toIntExact(user.getId())];
            totalNumberofQuestionsCorect = count_of_correct_answers[Math.toIntExact(user.getId())] ;
            totalpercentage = percentage;

            dayAvgAllTime = avg;
            dayMaxAllTime = max;
            dayMinAllTime = min;
            dayEnters = 1;
            dayNumberofQustions = count_of_questions[Math.toIntExact(user.getId())];
            dayNumberofQuestionsCorect = count_of_correct_answers[Math.toIntExact(user.getId())] ;
            daypercentage = percentage;

            monthAvgAllTime = avg;
            monthMaxAllTime = max;
            monthMinAllTime = min;
            monthEnters = 1;
            monthNumberofQustions = count_of_questions[Math.toIntExact(user.getId())];
            monthNumberofQuestionsCorect = count_of_correct_answers[Math.toIntExact(user.getId())] ;
            monthpercentage = percentage;

            yearAvgAllTime = avg;
            yearMaxAllTime = max;
            yearMinAllTime = min;
            yearEnters = 1;
            yearNumberofQustions = count_of_questions[Math.toIntExact(user.getId())];
            yearNumberofQuestionsCorect = count_of_correct_answers[Math.toIntExact(user.getId())] ;
            yearpercentage = percentage;
        }



        // all tags for view
        model.put("enddata",enddate);
        model.put("percent", percentage);
        model.put("startdata", startdate);

        model.put("max", max);
        model.put("min", min);
        model.put("avg", avg);
        model.put("count", count_of_questions[Math.toIntExact(user.getId())]);
        model.put("durration_per_question", cleanarr);
        model.put("CorectAnswers", count_of_correct_answers[Math.toIntExact(user.getId())]);
        model.put("IncorectAnswers", count_of_incorrect_answers[Math.toIntExact(user.getId())]);

        //for all time
        model.put("TotalAvgAllTime",totalAvgAllTime);
        model.put("TotalMinAllTime", totalMinAllTime);
        model.put("TotalMaxAllTime", totalMaxAllTime);
        model.put("TotalEnters", totalEnters);
        model.put("Questions", totalNumberofQustions);
        model.put("TotalNumberCorect", totalNumberofQuestionsCorect);
        model.put("TotalPercent", totalpercentage);

        //for day
        model.put("dayAvgAllTime",dayAvgAllTime);
        model.put("dayMinAllTime", dayMinAllTime);
        model.put("dayMaxAllTime", dayMaxAllTime);
        model.put("dayEnters", dayEnters);
        model.put("dayQuestions", dayNumberofQustions);
        model.put("dayNumberCorect", dayNumberofQuestionsCorect);
        model.put("dayPercent", daypercentage);

        //for month
        model.put("monthAvgAllTime",monthAvgAllTime);
        model.put("monthMinAllTime", monthMinAllTime);
        model.put("monthMaxAllTime", monthMaxAllTime);
        model.put("monthEnters", monthEnters);
        model.put("monthQuestions", monthNumberofQustions);
        model.put("monthNumberCorect", monthNumberofQuestionsCorect);
        model.put("monthPercent", monthpercentage);

        //for year
        model.put("yearAvgAllTime",yearAvgAllTime);
        model.put("yearMinAllTime", yearMinAllTime);
        model.put("yearMaxAllTime", yearMaxAllTime);
        model.put("yearEnters", yearEnters);
        model.put("yearQuestions", yearNumberofQustions);
        model.put("yearNumberCorect", yearNumberofQuestionsCorect);
        model.put("yearPercent", yearpercentage);

        return "endtesting";
    }


    @GetMapping("/main")
    public String main(Map<String, Object> model, @AuthenticationPrincipal User user) {

        //retrieving data about user
        Iterable<UserStat> userStats = userStatRepo.findByPersonId(user.getId());

        //data of start testing
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String time = dateFormat.format(date);

        time_of_question[Math.toIntExact(user.getId())] = time;

        //varaibles for random expressions
        int a = getRandomInteger(1, 100);
        int b = getRandomInteger(1, 100);
        int c = getRandomInteger(0, 5);
        int solutionInit = 0;

        // generating random expressions
        String experession = null;
        String [] GeneratedExpression = generateRandomExpression(a,b,c,solutionInit,experession);

        experession = GeneratedExpression[0];
        String solution = GeneratedExpression[1];

        // all tags for view
        model.put("expression", experession);
        model.put("solution", solution);
        model.put("count", count_of_questions[Math.toIntExact(user.getId())]);
        model.put("userStats", userStats);
        model.put("user_id", user.getId());
        model.put("user_name", user.getUsername());

        return "main";
    }

    @PostMapping("/main")
    public String giveAnswer(
            @AuthenticationPrincipal User user,
            @RequestParam String expression,
            @RequestParam String solution,
            @RequestParam String answer,
            Map<String, Object> model) throws ParseException {
        int solutioninint;
        int anserinint;
        boolean accuracy;

        //handling user reply
        if (isNumeric(solution) == true && isNumeric(answer)==true){
            solutioninint = Integer.parseInt(solution);
            anserinint = Integer.parseInt(answer);
            accuracy = isCorect(anserinint, solutioninint);
        }else {
            solutioninint=0;
            anserinint=0;
            accuracy=false;
        }
        //store answer in db
        UserStat userStat = new UserStat(expression, solutioninint, anserinint, accuracy, user);

        userStatRepo.save(userStat);



        //time was stamped
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");


        //variables for storing time, after end of session it will be in db
        String end = dateFormat.format(date);
        String start = time_of_question[Math.toIntExact(user.getId())];
        Date start_question = dateFormat.parse(start);
        Date end_question = dateFormat.parse(end);
        int duration = (int) ((end_question.getTime() - start_question.getTime())/1000);
        int number_of_question = count_of_questions[Math.toIntExact(user.getId())];

        time_per_question[Math.toIntExact(user.getId())][number_of_question] = duration;



        count_of_questions[Math.toIntExact(user.getId())] = count_of_questions[Math.toIntExact(user.getId())] + 1;


        if (accuracy == true){
            count_of_correct_answers[Math.toIntExact(user.getId())]= count_of_correct_answers[Math.toIntExact(user.getId())] +1;
        }else {
            count_of_incorrect_answers[Math.toIntExact(user.getId())]= count_of_incorrect_answers[Math.toIntExact(user.getId())]+1;
        }



        Iterable<UserStat> userStats = userStatRepo.findByPersonId(user.getId());
        // generating random expression
        int a = getRandomInteger(1, 100);
        int b = getRandomInteger(1, 100);
        int c = getRandomInteger(0, 5);
        int solutionInit = 0;
        String experession = null;
        String [] GeneratedExpression = generateRandomExpression(a,b,c,solutionInit,experession);


        String experessionupdated = GeneratedExpression[0];
        String solutionupdated = GeneratedExpression[1];

        model.put("userStats", userStatRepo);
        model.put("count", count_of_questions[Math.toIntExact(user.getId())]);
        model.put("expression", experessionupdated);
        model.put("solution", solutionupdated);
        model.put("userStats", userStats);
        model.put("user_id", user.getId());
        model.put("user_name", user.getUsername());


        String time = dateFormat.format(Calendar.getInstance().getTime());

        time_of_question[Math.toIntExact(user.getId())] = time;


        return "main";
    }

    // functions which can generate random expression
    public static int getRandomInteger(int min, int max) {
        return ((int) (Math.random() * (min - max))) + max;
    }

    public static String[] generateRandomExpression(int operatorFirst, int operatorSecond, int operation, int solution, String experession) {
        String[] strings = new String[2];

        if (operation == 1) {
            solution = operatorFirst - operatorSecond;
            String solutionstring = Integer.toString(solution);
            experession = operatorFirst + " - " + operatorSecond + " = ";
            strings[0] = experession;
            strings[1] = solutionstring;
            return strings;
        } else if (operation == 2) {
            solution = operatorFirst + operatorSecond;
            String solutionstring = Integer.toString(solution);
            experession = operatorFirst + " + " + operatorSecond + " = ";
            strings[0] = experession;
            strings[1] = solutionstring;
            return strings;
        } else if (operation == 3) {
            solution = operatorFirst * operatorSecond;
            String solutionstring = Integer.toString(solution);
            experession = operatorFirst + " * " + operatorSecond + " = ";
            strings[0] = experession;
            strings[1] = solutionstring;
            return strings;
        } else {
            solution = operatorFirst / operatorSecond;
            String solutionstring = Integer.toString(solution);
            experession = operatorFirst + " / " + operatorSecond + " = ";
            strings[0] = experession;
            strings[1] = solutionstring;
            return strings;
        }

    }

    // functions for checking accuracy and type
    public static boolean isCorect(int answer, int sollution){
        if (sollution == answer) {
            return true;

        }else {
            return false;
        }
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }




}
