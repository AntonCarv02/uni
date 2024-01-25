package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Event;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper.EventRowMapper;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Repository
public class EventDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Event> getAllEvents() {
        return jdbcTemplate.query(
                "SELECT * FROM Events",
                new EventRowMapper());

    }

    public Event getEvent(int event_ID) {
        return jdbcTemplate.query(
                "SELECT * FROM Events Where Event_ID =" + event_ID,
                new EventRowMapper()).get(0);

    }

    public List<Event> searchEvents(String name, String Data) throws ParseException {
        Date date = new Date();
        if (Data != "") {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(Data);
        }
        if (name != "" && Data != "") {
            String sql = "SELECT * FROM events WHERE DATE(Event_date) = ? AND Event_Name LIKE ?";
            return jdbcTemplate.query(sql, new Object[] { date, "%" + name + "%" }, new EventRowMapper());
        } else if (name != "") {
            String sql = "SELECT * FROM events WHERE Event_Name LIKE ?";
            return jdbcTemplate.query(sql, new Object[] { name }, new EventRowMapper());
        } else {
            String sql = "SELECT * FROM events WHERE DATE(Event_date) = ?";
            return jdbcTemplate.query(sql, new Object[] { date }, new EventRowMapper());
        }
    }

    public List<Event> searchTypeEvents(String type) {
        LocalDate today = LocalDate.now();
        if ("A".equals(type)) {
            String sql = "SELECT * FROM events WHERE Event_date < ?";
            return jdbcTemplate.query(sql, new Object[] { today }, new EventRowMapper());
        } else if ("P".equals(type)) {
            String sql = "SELECT * FROM events WHERE DATE(Event_date) = ?";
            return jdbcTemplate.query(sql, new Object[] { today }, new EventRowMapper());
        } else {
            String sql = "SELECT * FROM events WHERE Event_date > ?";
            return jdbcTemplate.query(sql, new Object[] { today }, new EventRowMapper());
        }
    }

    public void saveEvent(final Event E) {
        String sql = "INSERT INTO Events(Event_name, Event_date, Event_price, Event_description)  VALUES ('"
                + E.getName() + "','"
                + E.getDate() + "','"
                + E.getPrice() + "','"
                + E.getDescription() + "')";
        jdbcTemplate.execute(sql);
        System.out.println("EventDao - saved\n" + sql + "\n");
    }

    public boolean eventDate(int eventid) {
        LocalDate today = LocalDate.now();
        LocalDate eventDate = jdbcTemplate.queryForObject("SELECT Event_date FROM events WHERE Event_ID = ?", new Object[]{eventid}, LocalDate.class);

        return eventDate != null && eventDate.isAfter(today);
    }

    public String eventName(int eventid) {
        
        return jdbcTemplate.queryForObject("SELECT Event_name FROM events WHERE Event_ID = ?", new Object[]{eventid}, String.class);
    }
    public float eventGetPrice(int eventid) {
        return jdbcTemplate.queryForObject("SELECT Event_price FROM events WHERE Event_ID = ?", new Object[]{eventid}, Float.class);
    }
}
