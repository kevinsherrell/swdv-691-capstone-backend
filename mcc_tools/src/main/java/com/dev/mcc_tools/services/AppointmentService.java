package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.Appointment;
import com.dev.mcc_tools.respositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveOrUpdateAppointment(Appointment appointment){
        try{
            return appointmentRepository.save(appointment);
        }catch(Exception e){
            e.getCause();
        }
        return null;
    }

    public Iterable<Appointment> findAllAppointments(){
        return appointmentRepository.findAll();
    }

    public Appointment findByAppointmentID(int appointmentID){
        return appointmentRepository.findAppointmentByAppointmentID(appointmentID);
    }

    public Iterable<Appointment> findByProfileID(int profileID){
        return appointmentRepository.findAppointmentsByProfileID(profileID);
    }

    public Iterable<Appointment> findByOrderID(int orderID){
        return appointmentRepository.findAppointmentByOrderID(orderID);
    }
}