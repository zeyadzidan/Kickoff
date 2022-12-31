package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnnouncementTest {
    Announcement announcement;
    @BeforeEach
    void setUp() {
        announcement = new Announcement();
    }

    @Test
    void getId() {
        Long id = 222L;
        announcement.setId(id);
        assertEquals(id, announcement.getId());
    }

    @Test
    void getCourtOwner() {
        CourtOwner courtOwner = new CourtOwner();
        announcement.setCourtOwner(courtOwner);
        assertEquals(courtOwner, announcement.getCourtOwner());
    }

    @Test
    void getBody() {
        String body = "Hello World";
        announcement.setBody(body);
        assertEquals(body, announcement.getBody());
    }

    @Test
    void getImg() {
        String img = "image.png";
        announcement.setImg(img);
        assertEquals(img, announcement.getImg());
    }

    @Test
    void getTime() {
        Time time = new Time(22L);
        announcement.setTime(time);
        assertEquals(time, announcement.getTime());
    }

    @Test
    void getDate() {
        Date date = new Date(12L);
        announcement.setDate(date);
        assertEquals(date, announcement.getDate());
    }
}