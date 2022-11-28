package back.kickoff.kickoffback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public long Court_Owner_id;
    public int Court_Number;
    public String Court_Type;
    public Court(long id ,long Court_Owner_id,int Court_Number,String Court_Type)
    {
      this.id=id;
      this.Court_Owner_id=Court_Owner_id;
      this.Court_Number=Court_Number;
      this.Court_Type=Court_Type;
    }

    @Override
    public String toString() {
        return "Court{" +
                "id=" + id +
                ", Court_Owner_id=" + Court_Owner_id +
                ", Court_Number=" + Court_Number +
                ", Court_Type='" + Court_Type + '\'' +
                '}';
    }

    public Court()
    {}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourt_Owner_id() {
        return Court_Owner_id;
    }

    public void setCourt_Owner_id(long court_Owner_id) {
        Court_Owner_id = court_Owner_id;
    }

    public int getCourt_Number() {
        return Court_Number;
    }

    public void setCourt_Number(int court_Number) {
        Court_Number = court_Number;
    }

    public String getCourt_Type() {
        return Court_Type;
    }

    public void setCourt_Type(String court_Type) {
        Court_Type = court_Type;
    }
}
