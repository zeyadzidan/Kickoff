package com.pxp.kickoff3.repository;
import com.pxp.kickoff3.entity.employeeentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface empolyeerepostiorty extends JpaRepository<employeeentity,Integer> {
    public boolean existbyfirstnameandlastname(String firstname,String lastname);
    public boolean existbyid(int id);
}
