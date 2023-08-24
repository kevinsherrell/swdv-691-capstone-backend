package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.ErrorResponse;
import com.dev.mcc_tools.controllers.FormattedResponse;
import com.dev.mcc_tools.domain.Appointment;
import com.dev.mcc_tools.respositories.AppointmentRepository;
import com.dev.mcc_tools.respositories.ProfileRepository;
import com.dev.mcc_tools.validation.AppointmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.text.Format;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    private final AppointmentValidator appointmentValidator = new AppointmentValidator();

    public FormattedResponse saveAppointment(Appointment appointment) {
        appointmentValidator.initializeErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = appointmentValidator.getErrors();

        try {

            appointmentValidator.checkStatus(appointment.getStatus());

            Appointment created = appointmentRepository.save(appointment);
            return new FormattedResponse(httpStatus.value(), true, created);
        } catch (Exception e) {

            httpStatus = HttpStatus.BAD_REQUEST;
            System.out.println(appointment.getStatus());
            e.printStackTrace();

            e.getCause();
            return new ErrorResponse(httpStatus.value(), false, errors);
        }

    }

    public FormattedResponse updateAppointment(int appointmentID, Appointment appointment) {
        appointmentValidator.initializeErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = appointmentValidator.getErrors();

        try {

            Appointment found = appointmentRepository.findById(appointmentID);

            appointmentValidator.checkIDMatch(appointmentID, appointment.getAppointmentID());

            appointmentValidator.nullCheck("Appointment", found);

            appointmentValidator.checkStatus(appointment.getStatus());


            Appointment updated = appointmentRepository.save(appointment);

            return new FormattedResponse(httpStatus.value(), true, updated);

        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }

    }

    public FormattedResponse findAllAppointments() {
        HttpStatus httpStatus = HttpStatus.OK;
        Iterable<Appointment> found = appointmentRepository.findAll();
        return new FormattedResponse(httpStatus.value(), true, found);
    }

    public FormattedResponse findByAppointmentID(int appointmentID) {
        appointmentValidator.initializeErrors();
        HttpStatus httpStatus = HttpStatus.CREATED;
        HashMap<String, ArrayList<String>> errors = appointmentValidator.getErrors();
        try {
            Appointment found = appointmentRepository.findById(appointmentID);
            appointmentValidator.nullCheck("Appointment", found);
            return new FormattedResponse(httpStatus.value(), true, found);
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse findByProfileID(int profileID) {
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = appointmentValidator.getErrors();

        try{

            Iterable<Appointment> found = appointmentRepository.findAppointmentsByProfileID(profileID);

            return new FormattedResponse(httpStatus.value(), true, found);
        }catch(Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse findByOrderID(int orderID) {
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = appointmentValidator.getErrors();

        try{

            Iterable<Appointment> found = appointmentRepository.findAppointmentByOrderID(orderID);

           return new FormattedResponse(httpStatus.value(), true, found);
        }catch(Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }
}