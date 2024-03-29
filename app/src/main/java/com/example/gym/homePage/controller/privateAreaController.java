package com.example.gym.homePage.controller;

import com.example.gym.homePage.interfaces.I_privateAreaController;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class privateAreaController implements I_privateAreaController {
    protected FirebaseFunctions Functions = FirebaseFunctions.getInstance();


    public Task<HttpsCallableResult> getPersonalDetails(String email) {
        Map<String, Object> email_data = new HashMap<>();
        email_data.put("email", email);
        return Functions.getHttpsCallable("getPersonalDetails").call(email_data);
    }

    public Task<HttpsCallableResult> addDate(String email,String date) {
        Map<String, Object> date_map = new HashMap<>();
        date_map.put("email", email);
        date_map.put("date", date);
        return Functions.getHttpsCallable("addDate").call(date_map);
    }
    public Task<HttpsCallableResult> addDetails(String email, double height, double weight, String date, String gender) {
        Map<String, Object> details_map = new HashMap<>();
        details_map.put("height", height);
        details_map.put("weight", weight);
        details_map.put("dateBirth", date);
        details_map.put("gender", gender);
        HashMap<String, Object> data = new HashMap<>();
        data.put("details", details_map);
        data.put("email", email);
        return Functions.getHttpsCallable("addDetails").call(data);
    }
    public Task<HttpsCallableResult> addGender(String email,String gender) {
        Map<String, Object> gender_map = new HashMap<>();
        gender_map.put("email", email);
        gender_map.put("gender", gender);
        return Functions.getHttpsCallable("addGender").call(gender_map);
    }
    public Task<HttpsCallableResult> getName(String email) {
        Map<String, Object> name_map = new HashMap<>();
        name_map.put("email", email);
        return Functions.getHttpsCallable("getName").call(name_map);
    }

    public Task<HttpsCallableResult> getContact() {
        return Functions.getHttpsCallable("getContact").call();
    }

    public Task<HttpsCallableResult> updateContact(String email, String phone) {
        Map<String, Object> contact = new HashMap<>();
        contact.put("email", email);
        contact.put("phone", phone);
        return Functions.getHttpsCallable("updateContact").call(contact);
    }

}