package com.example1.demo.controller;

import com.example1.demo.DAO.UserDAO;
import com.example1.demo.model.User;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class BootstrapController {

    @Autowired
    UserDAO uDAO = new UserDAO();

    @RequestMapping(value = "/bootstrap", method = RequestMethod.GET, produces = "application/json")
    public String bootstrapDB(){
        List<User> userList = getUsersFromFile();
        for(User user:userList){
            if(user!=null){
                uDAO.insertUser(user);
            }
        }
        return "Data has been successfully loaded.";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public String usersWithValidSalary(){
        JsonObject response = new JsonObject();
        List<User> userList = uDAO.getAllUsersWithSpecificSalary(0,4000);
        JsonArray jsonArray = new JsonArray();
        for(User user:userList){
            JsonObject jsonUser = new JsonObject();
            jsonUser.addProperty("name",user.getName());
            jsonUser.addProperty("salary",user.getSalary());
            jsonArray.add(jsonUser);
        }
        response.add("result",jsonArray);
        return response.toString();
    }

    public List<User> getUsersFromFile() {
        ArrayList<User> userList = new ArrayList<>();
        String line = "";
        String cvsSplitBy = ",";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("salary.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int data = 0;
        ArrayList<String> rowList = new ArrayList<>();
        try {
            String row = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                String name = line.substring(0,line.indexOf(','));
                Double salary = Double.parseDouble(line.substring(line.indexOf(',')+1,line.length()));
                userList.add(new User(name,salary));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
