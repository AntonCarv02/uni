package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Participant;

public class ParticipantRowMapper implements RowMapper<Participant> {

        @Override
        public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
                int Event_ID = rs.getInt("Event_ID");
                String User_name = rs.getString("user_name");
                String name = rs.getString("Part_name");
                String gender = rs.getString("Part_gender");
                String escalao = rs.getString("Part_escalao");
                Boolean Pago = rs.getBoolean("Pago");
                int dorsal = rs.getInt("Event_Dorsal");
                Timestamp start = rs.getTimestamp("start");
                Timestamp P1 = rs.getTimestamp("P1");
                Timestamp P2 = rs.getTimestamp("P2");
                Timestamp P3 = rs.getTimestamp("P3");
                Timestamp finish = rs.getTimestamp("finish");
                long Ttime = rs.getLong("Ttime");
                int ent = rs.getInt("entity");
                int ref = rs.getInt("ref");
                int nif = rs.getInt("NIF");
                return new Participant(dorsal, User_name, Event_ID, name, gender, escalao, start, P1, P2, P3, finish,
                                Pago,Ttime, ent, ref, nif);
        }

}
