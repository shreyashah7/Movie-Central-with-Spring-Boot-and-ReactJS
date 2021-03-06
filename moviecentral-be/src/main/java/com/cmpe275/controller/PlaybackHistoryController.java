/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmpe275.controller;

import com.cmpe275.entity.Movie;
import com.cmpe275.entity.PlaybackHistory;
import com.cmpe275.entity.User;
import com.cmpe275.service.MovieServ;
import com.cmpe275.service.PlaybackHistoryService;
import com.cmpe275.utility.MovieActivity.MovieActivityAggregateResults;
import com.cmpe275.service.UserService;
import com.cmpe275.utility.ResponseFormat;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Shreya Shah
 */
@Controller
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class PlaybackHistoryController {

    @Autowired
    private PlaybackHistoryService playbackHistoryService;

    @Autowired
    private UserService userServ;

    @Autowired
    private MovieServ movieServ;

    ResponseFormat responseObject = new ResponseFormat();

    @PostMapping(path = "/playbackhistory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPlaybackHistory(@RequestBody PlaybackHistory playbackHistory, HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");

            if(userId == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("401");
            }
            if(playbackHistory.getMovieObj() == null || playbackHistory.getMovieObj().getMovieId() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie id required");
            }
            User u = userServ.getUserById(userId);
            if(u.getRole().equals("admin")){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no userplayback recording for admins");
            }

            PlaybackHistory p = new PlaybackHistory();
            p.setUserObj(u);
            p.setMovieObj(movieServ.getOneMovie(playbackHistory.getMovieObj().getMovieId()));
//            User userObj = new User();
//            userObj.setUserId(userId);
//            playbackHistory.setUserObj(userObj);
            PlaybackHistory playbackHistoryObj = playbackHistoryService.addPlaybackHistory(p);
            if (playbackHistoryObj != null) {
                responseObject.setData(playbackHistoryObj);
                responseObject.setMeta("User Plays recorded succesfully");
                return new ResponseEntity(responseObject, HttpStatus.OK);
            } else {
                responseObject.setData(null);
                responseObject.setMeta("User Already Played Movie within 24 hrs");
                return new ResponseEntity(responseObject, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseObject.setData(e);
            return new ResponseEntity(responseObject, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/playbackhistorys/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllPlaybackHistoryByUser(@PathVariable("userId") Long userId, HttpSession session) {
        try {
            Long sessionUserId = (Long) session.getAttribute("userId");
            User user = new User();
            if(userId != null){
                user.setUserId(userId);
            }else{
                user.setUserId(sessionUserId);
            }
            List<MovieActivityAggregateResults> playbackHistorys = playbackHistoryService.getAllPaybackHistoryByUser(user);
            if (!CollectionUtils.isEmpty(playbackHistorys)) {
                responseObject.setData(playbackHistorys);
                responseObject.setMeta("Playback History retrieved succesfully");
                return new ResponseEntity(responseObject, HttpStatus.OK);
            } else {
                responseObject.setData(null);
                responseObject.setMeta("No Playback History exists");
                return new ResponseEntity(responseObject, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setData(e);
            return new ResponseEntity(responseObject, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/movies/mostwatched")
    public ResponseEntity<?> getMostWatched() {
        return ResponseEntity.ok(playbackHistoryService.getMostWatchedMovies());
    }

    @GetMapping(path = "/movie/haswatched")
    public ResponseEntity<?> hasWatched(@RequestParam(value = "movie") Long movie_id,HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        if(userId == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("401");
        }

        if(movie_id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie id required");
        }
        User u = userServ.getUserById(userId);
        Movie m = movieServ.getOneMovie(movie_id);
        return ResponseEntity.ok(playbackHistoryService.hasWacthed(m,u));
    }
}
