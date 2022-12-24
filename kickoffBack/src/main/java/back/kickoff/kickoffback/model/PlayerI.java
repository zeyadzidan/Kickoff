package back.kickoff.kickoffback.model;

public interface PlayerI {
    Long getId() ;

    void setId(Long id) ;

    String getName() ;

    void setName(String name) ;

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    PlayerType getPlayerType() ;

    void setPlayerType(PlayerType playerType) ;

    boolean equals(Object o);
    String toString();
}
