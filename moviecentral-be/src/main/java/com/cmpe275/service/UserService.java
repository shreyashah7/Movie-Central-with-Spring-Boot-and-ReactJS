/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmpe275.service;

import com.cmpe275.entity.User;
import com.cmpe275.repository.UserRepository;
import com.cmpe275.utility.Constant;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author shahs
 */
@Service
public class UserService {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;

    public User getUserByCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

    public void addOrUpdateUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Long userId) {
        Optional<User> userObj = userRepository.findById(userId);
        User dbUserObj = null;
        if (userObj.isPresent()) {
            dbUserObj = userObj.get();
        }
        return dbUserObj;
    }

    public List<User> getAllActiveUsers() {
        List<User> allActiveUsers = (List<User>) userRepository.getAllActiveUsers();
        return allActiveUsers;
    }

    public List<User> getTopUsersBasedOnTime(String timeDef) {
        Calendar currentCal = Calendar.getInstance();
        Date currentDate = currentCal.getTime();
        Calendar cal = Calendar.getInstance();
        Date previousDate;
        if (timeDef.equals(Constant.LAST_24_HOURS)) {
            cal.setTime(currentDate);
            cal.add(Calendar.HOUR, -24);
        } else if (timeDef.equals(Constant.LAST_MONTH)) {
            cal.setTime(currentDate);
            cal.add(Calendar.MONTH, -1);
        } else if (timeDef.equals(Constant.LAST_WEEK)) {
            cal.setTime(currentDate);
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        }
        previousDate = cal.getTime();
        List<User> topUsers = userRepository.getTopUsersBasedOnTime(previousDate, currentDate, new PageRequest(0, 10));
        return topUsers;
    }

    public List<User> getUsersBySubscriptionType(List<String> subscriptionType, Integer month) {
        Calendar currentCal = Calendar.getInstance();
        currentCal = Constant.getDateFromTimestamp(currentCal);
        Date currentDate = currentCal.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        Date previousDate;
        if (!month.equals(-1)) {
            currentCal.set(Calendar.MONTH, month);
            currentCal.set(Calendar.DAY_OF_MONTH, 1);
            previousDate = currentCal.getTime();
            currentCal.add(Calendar.MONTH, 1);
            currentCal.add(Calendar.DAY_OF_MONTH, -1);
            currentDate = currentCal.getTime();
        } else {
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.YEAR, 1050);
            cal = Constant.getDateFromTimestamp(cal);
            previousDate = cal.getTime();
            currentCal.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = currentCal.getTime();
        }
        System.out.println("previousDate :" + previousDate);
        System.out.println("currentDate :" + currentDate);
        System.out.println("subscriptionType :" + subscriptionType);
        List<User> topUsers = userRepository.getUsersBySubscriptionType(previousDate, currentDate, subscriptionType);
        return topUsers;
    }

    public List<User> getActiveUsersByMonth(Integer month) {
        Calendar currentCal = Calendar.getInstance();
        currentCal = Constant.getDateFromTimestamp(currentCal);
        Date currentDate = currentCal.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        Date previousDate;
        if (!month.equals(-1)) {
            currentCal.set(Calendar.MONTH, month);
            currentCal.set(Calendar.DAY_OF_MONTH, 1);
            previousDate = currentCal.getTime();
            currentCal.add(Calendar.MONTH, 1);
            currentCal.add(Calendar.DAY_OF_MONTH, -1);
            currentDate = currentCal.getTime();
        } else {
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.YEAR, 1050);
            cal = Constant.getDateFromTimestamp(cal);
            previousDate = cal.getTime();
            currentCal.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = currentCal.getTime();
        }
        System.out.println("previousDate :" + previousDate);
        System.out.println("currentDate :" + currentDate);
        List<User> topUsers = userRepository.getActiveUsersByMonth(previousDate, currentDate);
        return topUsers;
    }

    public List<User> getActiveUserPlayBackByMonth(Integer month) {
        Calendar currentCal = Calendar.getInstance();
        currentCal = Constant.getDateFromTimestamp(currentCal);
        Date currentDate = currentCal.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        Date previousDate;
        if (!month.equals(-1)) {
            currentCal.set(Calendar.MONTH, month);
            currentCal.set(Calendar.DAY_OF_MONTH, 1);
            previousDate = currentCal.getTime();
            currentCal.add(Calendar.MONTH, 1);
            currentCal.add(Calendar.DAY_OF_MONTH, -1);
            currentDate = currentCal.getTime();
        } else {
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.YEAR, 1050);
            cal = Constant.getDateFromTimestamp(cal);
            previousDate = cal.getTime();
            currentCal.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = currentCal.getTime();
        }
        System.out.println("previousDate :" + previousDate);
        System.out.println("currentDate :" + currentDate);
        List<User> topUsers = userRepository.getActiveUserPlayBackByMonth(previousDate, currentDate);
        return topUsers;
    }

}