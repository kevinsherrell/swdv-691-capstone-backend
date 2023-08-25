package com.dev.mcc_tools.services;

import com.dev.mcc_tools.controllers.ErrorResponse;
import com.dev.mcc_tools.controllers.FormattedResponse;
import com.dev.mcc_tools.domain.Appointment;
import com.dev.mcc_tools.respositories.AppointmentRepository;
import com.dev.mcc_tools.validation.AppointmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.Format;
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

        try {

            Iterable<Appointment> found = appointmentRepository.findAppointmentsByProfileID(profileID);

            return new FormattedResponse(httpStatus.value(), true, found);
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse findByOrderID(int orderID) {
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, ArrayList<String>> errors = appointmentValidator.getErrors();

        try {

            Iterable<Appointment> found = appointmentRepository.findAppointmentByOrderID(orderID);

            return new FormattedResponse(httpStatus.value(), true, found);
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse findByDatesBetween( Timestamp date, Timestamp date2) {
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String , ArrayList<String>> errors = appointmentValidator.getErrors();
        try {

            Iterable<Appointment> found = appointmentRepository.findAppointmentsByAppointmentDateGreaterThanEqualAndAppointmentDateLessThanEqual(date, date2);

            return new FormattedResponse(httpStatus.value(), true,found);

        }catch(Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            e.printStackTrace();
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse findByCreatedDatesBetween( Timestamp date, Timestamp date2) {
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String , ArrayList<String>> errors = appointmentValidator.getErrors();
        try {


            Iterable<Appointment> found = appointmentRepository.findAppointmentsByDateCreatedGreaterThanEqualAndDateCreatedLessThanEqual(date, date2);

            return new FormattedResponse(httpStatus.value(), true,found);

        }catch(Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            e.printStackTrace();
            System.out.println("bad request");
            return new ErrorResponse(httpStatus.value(), false, errors);
        }
    }

    public FormattedResponse findCountDateEqual(Timestamp date){
        Long count = appointmentRepository.findAppointmentsByDateEquals(date);
        return new FormattedResponse(HttpStatus.OK.value(), true, count);
    }

    public FormattedResponse findDatesForMonth(Timestamp date ,String location){
       Iterable <Appointment> found = appointmentRepository.findAppointmentsByMonth(date, location);

       return new FormattedResponse((HttpStatus.OK.value()), true, found);
    }
}