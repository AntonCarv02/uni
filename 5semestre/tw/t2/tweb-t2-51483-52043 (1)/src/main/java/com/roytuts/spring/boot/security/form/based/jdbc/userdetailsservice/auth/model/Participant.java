package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

import java.sql.Timestamp;

public class Participant {
    private int Event_Dorsal;
    private int entity;
    private int ref;
    private int nif;
    private String Username;
    private int Event_ID;
    private String name;
    private String gender;
    private String escalao;
    private Timestamp start;
    private Timestamp P1;
    private Timestamp P2;
    private Timestamp P3;
    private Timestamp finish;
    private long totaltime;
    private Boolean pago;
        
    
    public long gettotaltime() {
        return totaltime;
    }


    public void settotaltime(long totaltime) {
        this.totaltime = totaltime;
    }
    public Timestamp getStart() {
        return start;
    }


    public void setStart(Timestamp start) {
        this.start = start;
    }


    public Timestamp getP1() {
        return P1;
    }


    public void setP1(Timestamp p1) {
        P1 = p1;
    }


    public Timestamp getP2() {
        return P2;
    }


    public void setP2(Timestamp p2) {
        P2 = p2;
    }


    public Timestamp getP3() {
        return P3;
    }


    public void setP3(Timestamp p3) {
        P3 = p3;
    }


    public Timestamp getFinish() {
        return finish;
    }


    public void setFinish(Timestamp finish) {
        this.finish = finish;
    }


    

    
    public int getNif() {
        return nif;
    }


    public void setNif(int nif) {
        this.nif = nif;
    }


    public Participant(int event_Dorsal, String username, int event_ID, String name, String gender, String escalao,
            Timestamp start, Timestamp p1, Timestamp p2, Timestamp p3, Timestamp finish, Boolean pago, long totaltime
            ,int entity,int ref, int nif) {
        Event_Dorsal = event_Dorsal;
        Username = username;
        Event_ID = event_ID;
        this.name = name;
        this.gender = gender;
        this.escalao = escalao;
        this.start = start;
        P1 = p1;
        P2 = p2;
        P3 = p3;
        this.finish = finish;
        this.pago = pago;
        this.totaltime = totaltime;
        this.entity  = entity;
        this.ref = ref;
        this.nif = nif;
    }


    public int getEvent_Dorsal() {
        return Event_Dorsal;
    }

    public void setEvent_Dorsal(int event_Dorsal) {
        Event_Dorsal = event_Dorsal;
    }

    public int getEvent_ID() {
        return Event_ID;
    }

    public void setEvent_ID(int event_ID) {
        Event_ID = event_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEscalao() {
        return escalao;
    }

    public void setEscalao(String escalao) {
        this.escalao = escalao;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }



    public int getEntity() {
        return entity;
    }


    public void setEntity(int entity) {
        this.entity = entity;
    }


    public int getRef() {
        return ref;
    }


    public void setRef(int ref) {
        this.ref = ref;
    }




    @Override
    public String toString() {
        return "Participant [Event_Dorsal=" + Event_Dorsal + ", Username=" + Username + ", Event_ID=" + Event_ID
                + ", name=" + name + ", gender=" + gender + ", escalao=" + escalao + ", start=" + start + ", P1=" + P1
                + ", P2=" + P2 + ", P3=" + P3 + ", finish=" + finish + ", pago=" + pago+ ", totaltime=" + totaltime + "]";
    }

    
}

    