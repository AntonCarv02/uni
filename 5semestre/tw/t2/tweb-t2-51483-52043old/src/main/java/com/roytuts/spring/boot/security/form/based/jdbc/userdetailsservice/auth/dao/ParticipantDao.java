package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Participant;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper.ParticipantRowMapper;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class ParticipantDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Participant> getAllParticipants(int eventID) {
        return jdbcTemplate.query(
                "SELECT * FROM Participants WHERE Event_ID = ?",
                new Object[] { eventID },
                new ParticipantRowMapper());
    }

    public void saveParticipant(final Participant P) {
        String sql = "INSERT INTO participants (Event_Dorsal, Event_ID, user_name, Part_name, Part_gender, Part_escalao, Pago, NIF) VALUES ('"
                +
                P.getEvent_Dorsal() + "','" +
                P.getEvent_ID() + "','" +
                P.getUsername() + "','" +
                P.getName() + "','" +
                P.getGender() + "','" +
                P.getEscalao() + "','" +
                P.getPago() + "','" +
                P.getNif() +
                "')";

        jdbcTemplate.execute(sql);
        System.out.println("ParticipantDao - saved\n" + sql + "\n");
    }

    public int countDorsal(int eventid) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM participants WHERE Event_ID = ?",
                Integer.class,
                eventid) + 1;
    }

    public void saveTotalTime(final Participant P) {

        String sql = "UPDATE participants SET Ttime = " + P.gettotaltime() +
                " WHERE Event_Dorsal = " + P.getEvent_Dorsal() + " AND Event_ID = " + P.getEvent_ID();
        jdbcTemplate.execute(sql);

    }

    public void saveTime(int Event_Dorsal, int Event_ID, String local, Timestamp T) {

        String sql = "UPDATE participants SET " + local + " = " + T +
                " WHERE Event_Dorsal = " + Event_Dorsal + " AND Event_ID = " + Event_ID;
        jdbcTemplate.execute(sql);

    }

    public List<Participant> getAllParticipantsByTime(int eventId) {
        return jdbcTemplate.query(
                "SELECT * FROM participants WHERE Event_ID = ? ORDER BY CASE WHEN Ttime = 0 THEN 1 ELSE 0 END, Ttime",
                new Object[] { eventId },
                new ParticipantRowMapper());
    }

    public Participant getParticipant(int event_ID, int Event_Dorsal) {
        List<Participant> result = jdbcTemplate.query(
                "SELECT * FROM participants WHERE Event_ID =" + event_ID + " AND Event_Dorsal=" + Event_Dorsal,
                new ParticipantRowMapper());
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Participant> getinscricoesusername(String username) {
        return jdbcTemplate.query(
                "SELECT * FROM Participants WHERE user_name = ?",
                new Object[] { username },
                new ParticipantRowMapper());
    }

    public void payment(String ent, String ref, int event_Dorsal, int Event_ID) {

        String sql = "UPDATE participants SET entity= " + ent + ", ref=" + ref +
                " WHERE Event_Dorsal = " + event_Dorsal + " AND Event_ID = " + Event_ID;
        jdbcTemplate.execute(sql);

    }

    public void pay(final Participant p) {
        String sql = "UPDATE participants SET pago= " + p.getPago() + " WHERE Event_Dorsal = " + p.getEvent_Dorsal()
                + " AND Event_ID = " + p.getEvent_ID();
        jdbcTemplate.execute(sql);

    }
    /*
     * /
     * public boolean checknif(int nif) {
     * List<Participant> result = jdbcTemplate.query(
     * "SELECT * FROM participants WHERE Event_ID =" + event_ID +
     * " AND Event_Dorsal=" + Event_Dorsal,
     * new ParticipantRowMapper()
     * );
     * return result.isEmpty() ? null : result.get(0);
     * 
     * 
     * }
     */
}
