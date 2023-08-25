package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Appointment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    Iterable<Appointment> findAll();

    Appointment findById(int id);

    Appointment findAppointmentByAppointmentID(int appointmentID);

    Iterable<Appointment> findAppointmentByOrderID(int orderID);

    Iterable<Appointment> findAppointmentsByProfileID(int profileID);

    Iterable<Appointment> findAppointmentsByAppointmentDateGreaterThanEqualAndAppointmentDateLessThanEqual(Timestamp date, Timestamp date2);

    Iterable<Appointment> findAppointmentsByDateCreatedGreaterThanEqualAndDateCreatedLessThanEqual(Timestamp date, Timestamp date2);

    @Query(value = "select a  from Appointment a join a.orders o where date_trunc('month', CAST(a.appointmentDate as timestamp)) = date_trunc('month', CAST(?1 as timestamp)) AND o.location = ?2 ")
    Iterable<Appointment> findAppointmentsByMonth(Timestamp appointmentDate, String location);

    @Query(value = "SELECT COUNT(*) from Appointment where appointmentDate  = ?1")
    Long findAppointmentsByDateEquals(Timestamp date);


}