package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Appointment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
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

    Iterable<Appointment> findAppointmentsByDateGreaterThanEqualAndDateLessThanEqual(Timestamp date,  Timestamp date2);
    Iterable<Appointment> findAppointmentsByDateCreatedGreaterThanEqualAndDateCreatedLessThanEqual(Timestamp date, Timestamp date2);
}