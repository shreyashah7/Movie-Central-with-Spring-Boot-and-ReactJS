/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmpe275.repository;

import com.cmpe275.entity.Movie;
import com.cmpe275.entity.PlaybackHistory;
import com.cmpe275.entity.User;
import com.cmpe275.utility.MovieActivity.MovieActivityAggregateResults;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Shreya Shah
 */
@Repository
public interface PlaybackHistoryRepository extends CrudRepository<PlaybackHistory, Long> {

    @Override
    <S extends PlaybackHistory> S save(S s);

    @Query(value = "SELECT new com.cmpe275.utility.MovieActivity.MovieActivityAggregateResults(m.title,m.availability,m.genre,u.timestamp) from "
            + " PlaybackHistory as u JOIN Movie as m ON m.movieId = u.movieObj where u.userObj = :user ORDER BY timestamp DESC")
    List<MovieActivityAggregateResults> getAllPaybackHistoryByUser(@Param("user") User user);

    @Query(value = "SELECT ph FROM PlaybackHistory as ph where ph.timestamp >= :previousDate "
            + "and ph.timestamp <= :currentDate and ph.userObj = :userId AND ph.movieObj = :movieId")
    public List<PlaybackHistory> getAllPlayBackHistoryByUserByTime(@Param("previousDate") Date previousDate, @Param("currentDate") Date currentDate, @Param("userId") User userId, @Param("movieId") Movie movieId);

    @Query(value = "select p.movieObj from PlaybackHistory p group by p.movieObj order by count(p.id) desc")
    public Page<Movie> getMostWatchedMovies(Pageable p);

    public Long countAllByMovieObjAndUserObj(Movie m,User u);
}
