package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CourtOwnerTest {
    CourtOwner courtOwner;

    @BeforeEach
    void setUp() {
        courtOwner = new CourtOwner();
    }

    @Test
    void getIdTest() {
        Long idValue = 4L;
        courtOwner.setId(idValue);
        assertEquals(idValue, courtOwner.getId());
    }

    @Test
    void getUserNameTest() {
        String userName = "Nasr Club";
        courtOwner.setUserName(userName);
        assertEquals(userName, courtOwner.getUserName());
    }

    @Test
    void getEmailTest() {
        String email = "nasrClub@gmail.com";
        courtOwner.setEmail(email);
        assertEquals(email, courtOwner.getEmail());
    }

    @Test
    void getPasswordTest() {
        String password = "123456789010";
        courtOwner.setPassword(password);
        assertEquals(password, courtOwner.getPassword());
    }

    @Test
    void getLocationTest() {
        String location = "El-Nasr Sporting Club, 483H+WCC, Ahmed Ali Hussein, El-Nozha, El Nozha, Cairo Governorate 4470011";
        courtOwner.setLocation(location);
        assertEquals(location, courtOwner.getLocation());
    }

    @Test
    void getRatingTest() {
        float rating = 0.2f;
        courtOwner.setRating(rating);
        assertEquals(rating, courtOwner.getRating());
    }

    @Test
    void getImageTest() {
        String image = "www.imageClub.com/1234.png";
        courtOwner.setImage(image);
        assertEquals(image, courtOwner.getImage());
    }


}