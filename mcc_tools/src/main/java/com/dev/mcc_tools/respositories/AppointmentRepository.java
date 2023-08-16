package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    Iterable<Appointment> findAll();

    Appointment findById(int id);

    Appointment findAppointmentByAppointmentID(int appointmentID);

    Iterable<Appointment> findAppointmentByOrderID(int orderID);

    Iterable<Appointment> findAppointmentsByProfileID(int profileID);

}