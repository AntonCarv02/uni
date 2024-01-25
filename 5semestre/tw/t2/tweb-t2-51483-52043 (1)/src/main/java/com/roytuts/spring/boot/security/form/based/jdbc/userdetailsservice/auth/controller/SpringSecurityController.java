package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.controller;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.UserDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Event;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.EventDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Participant;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.ParticipantDao;

import org.springframework.security.core.Authentication;

@Controller
public class SpringSecurityController {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ParticipantDao ParticipantDao;

    @GetMapping("/")
    public String defaultPage(Model model) {

        List<Event> eventList = eventDao.getAllEvents();
        model.addAttribute("eventList", eventList);

        model.addAttribute("msg", "Bem-Vindo à Iron Runners");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Username ou Password Inválidas");

            return "login";
        }
        if (logout != null) {
            model.addAttribute("msg", "A sua sessão foi encerrada com sucesso");

            return "login";
        }

        return "login";
    }

    @GetMapping("/userinscricao")
    public String userinscricaoPage(Model model, @RequestParam(value = "eventid", required = false) Integer Event_ID,
            @RequestParam(value = "amount", required = false) Float amount,
            @RequestParam(value = "eventDorsal", required = false) Integer event_Dorsal) {
        if (Event_ID != null && amount == null) {
            Participant P = ParticipantDao.getParticipant(Event_ID, event_Dorsal);
            P.setPago(true);
            ParticipantDao.pay(P);
            model.addAttribute("message", "Pago com Sucesso");
            model.addAttribute("showpaybt", false);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        List<Participant> pList = ParticipantDao.getinscricoesusername(username);
        List<String> nomes = new ArrayList<String>();
        List<Float> valor = new ArrayList<Float>();

        for (Participant participant : pList) {
            nomes.add(eventDao.eventName(participant.getEvent_ID()));
            valor.add(eventDao.eventGetPrice(participant.getEvent_ID()));
        }

        model.addAttribute("pList", pList);
        model.addAttribute("nList", nomes);
        model.addAttribute("aList", valor);

        if (Event_ID != null && amount != null) {
            Participant p = ParticipantDao.getParticipant(Event_ID, event_Dorsal);
            model.addAttribute("message", "Entidade:" + p.getEntity()
                    + "\n Referência:" + p.getRef() + "\n Quantia:" + eventDao.eventGetPrice(p.getEvent_ID()) + "€");
            model.addAttribute("event_ID", p.getEvent_ID());
            model.addAttribute("event_Dorsal", p.getEvent_Dorsal());
            model.addAttribute("showpaybt", true);

        }

        return "userinscricao";
    }

    @GetMapping("/index")
    public String userPage(Model model, @RequestParam(value = "eventName", required = false) String name,
            @RequestParam(value = "eventDate", required = false) String date,
            @RequestParam(value = "type", required = false) String type) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));

        model.addAttribute("isUser", isUser);

        if (name == null && date == null) {
            List<Event> eventList = eventDao.getAllEvents();
            model.addAttribute("eventList", eventList);
        } else if (name != "" || date != "") {
            List<Event> eventList = eventDao.searchEvents(name, date);
            model.addAttribute("eventList", eventList);
        } else {
            List<Event> eventList = eventDao.getAllEvents();
            model.addAttribute("eventList", eventList);
        }

        if (type != null) {
            List<Event> eventList = eventDao.searchTypeEvents(type);
            model.addAttribute("eventList", eventList);
        }

        String username = authentication.getName();
        if (username != null) {
            model.addAttribute("msg", "Bem-Vindo, " + username);

        }

        return "index";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model, HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @RequestParam(value = "eventName", required = false) String eventName,
            @RequestParam(value = "eventDate", required = false) Date date,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "Timestamp", required = false) Timestamp timestamp,
            @RequestParam(value = "eventID", required = false) String eventID,
            @RequestParam(value = "eventDorsal", required = false) String eventDorsal,
            @RequestParam(value = "local", required = false) String local) {
        if (eventName != null) {
            float p = 0;
            try {
                p = Float.parseFloat(price);
            } catch (Exception e) {
                model.addAttribute("msg", "Preço Inválido!(Não númerico)");
                return "admin";
            }
            Event e = new Event(0, eventName, date, description, p);
            eventDao.saveEvent(e);
            model.addAttribute("msg", "Evento Salvo com Sucesso");
        }
        if (local != null) {
            int Event_Id = 0;
            int Event_Dorsal = 0;
            try {
                Event_Id = Integer.parseInt(eventID);
                Event_Dorsal = Integer.parseInt(eventDorsal);
            } catch (Exception e) {
                model.addAttribute("message", "Valores Inválidos!(Não númerico)");
                return "admin";
            }
            ParticipantDao.saveTime(Event_Dorsal, Event_Id, local, timestamp);
            model.addAttribute("message", "Evento Salvo com Sucesso");
        }
        model.addAttribute("title", "Página de Controlo");

        return "admin";
    }

    @GetMapping("/newuser")
    public String newuser(Model model) {
        model.addAttribute("title", "Registo");
        model.addAttribute("message", "Coloque a sua Informação");
        return "newuser";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String email1,
            @RequestParam String email2,
            Model model) {
        if (userDao.getUser(username) != null) {
            model.addAttribute("title", "Registro");
            model.addAttribute("message", "Coloque a sua Informação");
            model.addAttribute("error", "Username já existe. Por favor escolha outro.");
            return "newuser";
        } else if (!email1.equals(email2)) {
            model.addAttribute("title", "Registro");
            model.addAttribute("message", "Coloque a sua Informação");
            model.addAttribute("error", "Emails diferentes");
            return "newuser";
        } else {
            model.addAttribute("message", "registration is OK");
            model.addAttribute("title", "registration page");
            String encodedPassword = new BCryptPasswordEncoder().encode(password);
            User u = new User(username, encodedPassword, email1, "ROLE_USER");
            userDao.saveUser(u); // escrever na BD
            System.out.println("GRAVAR na BD: " + u.toString());
            model.addAttribute("user", u);
            return "login";
        }

    }

    @GetMapping("/detailsPage")
    public String detalhes(Model model, @RequestParam(required = false) int eventId,
            @RequestParam(required = false) Integer event_Dorsal,
            @RequestParam(value = "ok", required = false) String ok) {

        if (ok != null) {

            JSONObject obj = getReferenciaMB(eventDao.eventGetPrice(eventId));
            String ent = obj.getString("mb_entity");
            String ref = obj.getString("mb_reference");
            String amount = obj.getString("mb_amount");

            model.addAttribute("msg", "You have been successfully registered as a participant<br>Entidade:" + ent
                    + "<br>Referência:" + ref + "<br>Quantia:" + amount + "€");

            ParticipantDao.payment(ent, ref, event_Dorsal, eventId);

        }

        boolean eventdate = eventDao.eventDate(eventId);
        model.addAttribute("eventDate", eventdate);

        List<Participant> participantList = ParticipantDao.getAllParticipants(eventId);
        model.addAttribute("pList", participantList);
        for (Participant participant : participantList) {
            if (participant.getStart() != null && participant.getFinish() != null) {
                long timeDifference = calculateTimeDifferenceInMinutes(participant.getStart(), participant.getFinish());
                participant.settotaltime(timeDifference);
                ParticipantDao.saveTotalTime(participant);
            }
        }
        List<Participant> tList = ParticipantDao.getAllParticipantsByTime(eventId);
        model.addAttribute("tList", tList);
        model.addAttribute("eventId", eventId);

        String eventname = eventDao.eventName(eventId);

        model.addAttribute("eventname", eventname);

        if (event_Dorsal != null && ok == null) {
            Participant P = ParticipantDao.getParticipant(eventId, event_Dorsal);
            String answer = "";
            if (P != null) {
                if (P.gettotaltime() != 0) {
                    answer = "Participante: " + P.getName() + " , terminou com tempo: " + P.gettotaltime() + "m.";
                } else if (P.getStart() == null) {
                    answer = "Participante: " + P.getName() + " , não começou a Prova ainda.";
                } else if (P.getP3() != null) {
                    long timeDifference = calculateTimeDifferenceInMinutes(P.getStart(), P.getP3());
                    answer = "Participante: " + P.getName() + " , passou em P3 com tempo de Prova: " + timeDifference
                            + "m.";
                } else if (P.getP2() != null) {
                    long timeDifference = calculateTimeDifferenceInMinutes(P.getStart(), P.getP2());
                    answer = "Participante: " + P.getName() + " , passou em P2 com tempo de Prova: " + timeDifference
                            + "m.";
                } else if (P.getP1() != null) {
                    long timeDifference = calculateTimeDifferenceInMinutes(P.getStart(), P.getP1());
                    answer = "Participante: " + P.getName() + " , passou em P1 com tempo de Prova: " + timeDifference
                            + "m.";
                }
                model.addAttribute("statusMessage", answer);
            } else {
                answer = "Nenhum Participante com o Dorsal Indicado.";
                model.addAttribute("statusMessage", answer);
            }

        }
        return "detailsPage";
    }

    public static long calculateTimeDifferenceInMinutes(Timestamp start, Timestamp end) {
        long timeDifferenceMillis = end.getTime() - start.getTime();
        return timeDifferenceMillis / (60 * 1000);
    }

    @GetMapping("/inscricao")
    public String inscricao(Model model,
            @RequestParam int event) {

        Event eventl = eventDao.getEvent(event);
        model.addAttribute("event", eventl.getName());
        model.addAttribute("eventid", event);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {

            String userauth = authentication.getName();
            model.addAttribute("username", userauth);

            return "inscricao";

        }

        return "redirect:/login?event=" + event;

    }

    @GetMapping("/registerparticipant")
    public String participant(Model model, @RequestParam int event,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String escalao,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nif) {
        int nif_int = 0;
        try {
            nif_int = Integer.parseInt(nif);
        } catch (Exception e) {
            model.addAttribute("msg", "Valores Inválidos!(Não númerico)");
            return "inscricao";
        }

        int dorsal = ParticipantDao.countDorsal(event);
        Participant p = new Participant(dorsal, username, event, nome, genero, escalao, null,
                null, null, null, null, false, 0, 0, 0, nif_int);
        ParticipantDao.saveParticipant(p);
        return "redirect:/detailsPage?eventId=" + event + "&event_Dorsal=" + dorsal + "&ok";
    }

    public static JSONObject getReferenciaMB(float amount) {
        try {
            URL url = new URL("https://magno.di.uevora.pt/tweb/t2/mbref4payment");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); 
            con.setDoOutput(true); 

            String postData = "amount=" + amount;
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = postData.getBytes();
                os.write(input, 0, input.length);
            }

            StringBuilder result = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null;) {

                    result.append(line);
                }
            }
            System.out.println(result);
            return new JSONObject(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
