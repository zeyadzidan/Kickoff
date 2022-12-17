package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void setId() {
        Long idValue = 4L;
        player.setId(idValue);
        assertEquals(idValue, player.getId());
    }

    @Test
    void setName() {
        String name = "Cristiano Ronaldo";
        player.setName(name);
        assertEquals(name, player.getName());
    }

    @Test
    void setEmail() {
        String email = "cr7Goat@gmail.com";
        player.setEmail(email);
        assertEquals(email, player.getEmail());
    }

    @Test
    void setPhoneNumber() {
        String phoneNumber = "01103878799";
        player.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, player.getPhoneNumber());
    }

    @Test
    void setPlayerType() {
        PlayerType playerType = PlayerType.Registered;
        player.setPlayerType(playerType);
        assertEquals(playerType, player.getPlayerType());
    }

    @Test
    void setPassword() {
        String password = "cr7IsTheRealGoat";
        player.setPassword(password);
        assertEquals(password, player.getPassword());
    }

    @Test
    void setLocation() {
        String location = "483H+WCC, Ahmed Ali Hussein, El-Nozha, Cairo Governorate 4470011";
        player.setLocation(location);
        assertEquals(location, player.getLocation());
    }

    @Test
    void setImage() {
        String image = "www.imagePlayer.com/7.png";
        player.setImage(image);
        assertEquals(image, player.getImage());
    }

    @Test
    void setXAxis() {
        Double xAxis = 66.2;
        player.setXAxis(xAxis);
        assertEquals(xAxis, player.getXAxis());
    }

    @Test
    void setYAxis() {
        Double yAxis = 36.2;
        player.setYAxis(yAxis);
        assertEquals(yAxis, player.getYAxis());
    }

    @Test
    void setReservations() {
        Set<Reservation> reservationSet = new HashSet<>();
        player.setReservations(reservationSet);
        assertEquals(reservationSet, player.getReservations());
    }
}