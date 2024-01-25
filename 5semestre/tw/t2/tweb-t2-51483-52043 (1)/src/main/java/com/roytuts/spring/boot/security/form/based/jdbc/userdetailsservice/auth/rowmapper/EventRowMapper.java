package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Event;
import java.sql.Date;


public class EventRowMapper implements RowMapper<Event> {

	@Override
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		int Event_ID = rs.getInt("Event_ID");
        String name = rs.getString("Event_name"); 
        Date date = rs.getDate("Event_date");
		float price = rs.getFloat("Event_price");
		String description = rs.getString("Event_description");    
        return new Event(Event_ID, name, date, description, price);
	}

}
