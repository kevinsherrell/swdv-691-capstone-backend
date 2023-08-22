//package com.dev.mcc_tools.validation;
//
//import com.dev.mcc_tools.domain.PhoneNumber;
//import com.dev.mcc_tools.domain.Profile;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class PhoneNumberValidator extends MccValidator{
//
////    public HashMap<String, String> checkPhoneNumber(PhoneNumber phoneNumber){
////        if(phoneNumber == null){
////            setErrors("phoneNumber", "profile not found");
////        }
////        return this.errors;
////    }
//public void checkPhoneNumber(PhoneNumber phoneNumber){
//    if(phoneNumber == null){
//        setErrors("phoneNumber", "profile not found");
//    }
//
//}
////    public HashMap<String, String> checkPhoneNumber(Iterable<PhoneNumber> phoneNumbers){
//////        System.out.println(phoneNumbers == null);
////        int count = 0;
////        for(PhoneNumber phoneNumber: phoneNumbers){
////            count++;
////        }
////        if(count < 1){
////            setErrors("phoneNumber", "no available phone numbers");
////        }
////        return this.errors;
////    }
//public void checkPhoneNumber(Iterable<PhoneNumber> phoneNumbers){
////        System.out.println(phoneNumbers == null);
//    int count = 0;
//    for(PhoneNumber phoneNumber: phoneNumbers){
//        count++;
//    }
//    if(count < 1){
//        setErrors("phoneNumber", "no available phone numbers");
//    }
//
//}
//
//}