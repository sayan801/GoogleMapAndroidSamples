package com.app.mycuratioui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by technicise on 13/4/14.
 * controls the app passcode 
 * verification process.
 */
public class SharedPreferenceClass {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_PASSCODE_GLOBAL = "passCodeGlobal";
    public static final String KEY_PASSCODE_LOCAL = "passCodeLocal";
    public static final String KEY_PASSWORD_GLOBAL = "PASSWORD";
    public static final String KEY_EMAIL_GLOBAL = "EMAIL";
    public static final String KEY_MOBILE_GLOBAL = "MOBILE";
    public static final String KEY_NAME_GLOBAL = "USERNAME";
    public static final String KEY_ZIP_GLOBAL = "USERZIP";
    public static final String KEY_DOB_GLOBAL = "USERDOB";
    public static final String SET_CURRENT_SESSION_USER_ID = "USERID";
    public static final String SET_CURRENT_CHART_ID = "CHARTID";
    public static final String SET_CURRENT_CONTROLLED_SUBSTANCE_ID = "CURRENT_CONTROLLED_SUBSTANCE_ID";
    public static final String SET_CURRENT_MEDICAL_ISSUE_ID = "CURRENT_MEDICAL_ISSUE_ID";
    public static final String SET_PROVIDER_FINDER_FROM_CHART_LOAD_FLAG = "FLAGID";
    public static final String SET_SUBSTANCE_SEARCH_AGAIN_FROM_SELECTED_SUBSTANCE_FLAG = "FLAG";
    public static final String SET_LAST_INSERTED_ROW_ID = "ROWID";
    public static final String SET_LAST_INSERTED_MEDICAL_ISSUE_ID = "MEDICAL_ISSUE_ID";
    public static final String SET_CURRENT_MEDICAL_ALLERGY_ID = "CURRENT_MEDICAL_ALLERGY_ID";
    public static final String KEY_SIGN_UP_TYPE = "SIGNUPTYPE";
    public static final String KEY_USER_AUTH_ID = "USERAUTHID";
    public static final String KEY_LAST_ACTIVITY = "LASTACTIVITY";
    public static final String KEY_VERIFICATION_CODE = "VERIFICATIONCODE";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_IS_GOOGLE_SIGN_UP = "ISGOOGLESIGNUP";
    public static final String KEY_PROVIDER_NAME = "ProviderName";
    public static final String KEY_PROVIDER_Address = "ProviderAddress";
    public static final String KEY_PROVIDER_Phone= "ProviderPhone";
    public static final String KEY_PROVIDER_Fax= "ProviderFax";
    public static final String KEY_SET_PROVIDER_TYPE = "PROVIDERTYPE";
    public static final String KEY_SET_PROVIDER_SEARCH_LOCATION = "PROVIDERSEARCHLOCATION";
    public static final String KEY_SET_PROVIDER_SEARCH_DISTANCE = "PROVIDERSEARCHDISTANCE";
    public static final String KEY_PROVIDER__BUSINESS_ADDRESS = "PROVIDERBUSINESSADDRESS";
    public static final String KEY_PROVIDER_PRACTICE_ADDRESS = "PROVIDERPRACTICEADDRESS";

    public static final String KEY_GENDER = "GENDER";
    public static final String KEY_SET_CURRENT_ZIP_CODE = "ZIPCODE";

    public static final String KEY_SET_REGISTER_EMAIL = "PROVIDEREMAIL";
    public static final String KEY_SET_LOGIN_TO_PRIMARY_DETAILS = "PROVIDER_EMAIL";
    public static final String KEY_SET_LOGIN_TYPE = "LOGIN_TYPE";
    public static final String KEY_SET_LOGIN_FROM = "LOGIN_FROM";
    public static final String KEY_SET_PROVIDER_PASSCODE = "PROVIDER_PASSCODE";
    public static final String KEY_SET_PHARMACY_HOSPITAL_FROM_DOCTOR_ACTIVITY = "PHARMACYHOSPITALFROMDOCTORACTIVITY";
    public static final String KEY_SET_PROVIDER_NPI_ID = "PROVIDERNPIID";
    public static final String KEY_SET_CURRENT_TAB_FRAGMENT = "PROVIDERDETAILSCURRENTTABFRAGMENT";
    public static final String KEY_SET_USER_COMMENTS = "USERCOMMENTS";
    public static final String KEY_SET_DISTANCE_VALUE = "DISTANCEVALUE";
    public static final String KEY_SET_EMERGENCY_0RURGENTCARE = "EMERGENCY0RURGENTCARE";
    public static final String KEY_PROVIDER_NAME_SEARCH_TERM = "PROVIDERNAMESEARCHTERM";
    public static final String KEY_SPECIALITY = "SPECIALITY";

    public static final String KEY_SET_DIALOG_PROVIDER_EMAIL = "DIALOG_PROVIDER_EMAIL";
    public static final String KEY_SET_DIALOG_PROVIDER_PASSWORD = "DIALOG_PROVIDER_PASSWORD";
    public static final String KEY_SET_SUBSTANCE_NAME = "SUBSTANCE_NAME";
    public static final String KEY_SET_SUBSTANCE_NAME_TWO = "SUBSTANCE_NAME_TWO";
    public static final String KEY_SET_MEDICAL_ISSUE_NAME = "MEDICAL_ISSUE_NAME";
    public static final String KEY_SET_TOBACCO_PRODUCT_NAME = "TOBACCO_PRODUCT_NAME";

    private static final String KEY_SET_PROVIDER_LOGIN_STATUS = "IsLoggedTrue";
    public static final String KEY_CHART_BASIC_INFO_EDIT_FLAG = "CHART_BASIC_INFO_EDIT_FLAG";
    public static final String KEY_CHART_SOCIAL_HISTORY_EDIT_FLAG = "CHART_SOCIAL_HISTORY_EDIT_FLAG";
    public static final String KEY_CHART_FAMILY_HISTORY_EDIT_FLAG = "CHART_FAMILY_HISTORY_EDIT_FLAG";
    public static final String KEY_CHART_FINANCIAL_HISTORY_EDIT_FLAG = "KEY_CHART_FINANCIAL_HISTORY_EDIT_FLAG";
    public static final String KEY_ALLERGY_DETAILS_EDIT_FLAG = "KEY_ALLERGY_DETAILS_EDIT_FLAG";
    public static final String KEY_ISSUE_DETAILS_EDIT_FLAG = "KEY_ISSUE_DETAILS_EDIT_FLAG";
    public static final String KEY_IMMUNIZATION_DETAILS_EDIT_FLAG = "KEY_IMMUNIZATION_DETAILS_EDIT_FLAG";
    public static final String KEY_MEDICATION_DETAILS_EDIT_FLAG = "KEY_IMMUNIZATION_DETAILS_EDIT_FLAG";
    public static final String KEY_SURGERY_DETAILS_EDIT_FLAG = "KEY_IMMUNIZATION_DETAILS_EDIT_FLAG";
    public static final String KEY_FAMILY_MEDICAL_ISSUE_ADD_FLAG = "FAMILY_MEDICAL_ISSUE_ADD_FLAG";
    public static final String KEY_SOCIAL_CONTROLLED_SUBSTANCE_ADD_FLAG = "SOCIAL_CONTROLLED_SUBSTANCE_ADD_FLAG";
    public static final String KEY_SOCIAL_CONTROLLED_SUBSTANCE_EDIT_FLAG = "SOCIAL_CONTROLLED_SUBSTANCE_EDIT_FLAG";
    public static final String KEY_CHART_FINANCE_HISTORY_LAST_INSERTED_INSURANCE_CARD_ID = "KEY_CHART_FINANCE_HISTORY_LAST_INSERTED_INSURANCE_CARD_ID";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_ID = "KEY_SET_CURRENT_INSURANCE_CARD_ID";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_TYPE = "KEY_SET_CURRENT_INSURANCE_CARD_TYPE";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_NAME = "KEY_SET_CURRENT_INSURANCE_CARD_NAME";
    private static final String KEY_SET_CURRENT_INSURED_NAME = "KEY_SET_CURRENT_INSURED_NAME";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_MEMBERSHIP_NUMBER = "KEY_SET_CURRENT_INSURANCE_CARD_MEMBERSHIP_NUMBER";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_SUFFIX = "KEY_SET_CURRENT_INSURANCE_CARD_SUFFIX";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_GUARANTOR = "KEY_SET_CURRENT_INSURANCE_CARD_GUARANTOR";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_GROUP_ID = "KEY_SET_CURRENT_INSURANCE_CARD_GROUP_ID";
    private static final String KEY_SET_CURRENT_INSURANCE_CARD_CUSTOMER_SERVICE_NUMBER = "KEY_SET_CURRENT_INSURANCE_CARD_CUSTOMER_SERVICE_NUMBER";
    private static final String KEY_SET_CURRENT_CHART_OR_NOT = "KEY_SET_CURRENT_CHART_OR_NOT";

    public static final String KEY_SET_MEDICAL_ALLERGY_ISSUE_ITEM = "KEY_SET_MEDICAL_ALLERGY_ISSUE_ITEM";
    public static final String KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PROVIDER = "KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PROVIDER";
    public static final String KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY = "KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY";
    public static final String KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PHARMACY = "KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PHARMACY";
    public static final String KEY_SET_MEDICAL_ADD_PROVIDER_FOR_HOSPITAL = "KEY_SET_MEDICAL_ADD_PROVIDER_FOR_HOSPITAL";


    public static final String KEY_SET_MEDICAL_ALLERGIES_ID = "KEY_SET_MEDICAL_ALLERGIES_ID";
    public static final String KEY_SET_MEDICAL_ISSUES_ID = "KEY_SET_MEDICAL_ISSUES_ID";
    public static final String KEY_SET_MEDICAL_IMMUNIZATIONS_ID = "KEY_SET_MEDICAL_IMMUNIZATIONS_ID";
    public static final String KEY_SET_MEDICAL_MEDICATIONS_ID = "KEY_SET_MEDICAL_MEDICATIONS_ID";
    public static final String KEY_SET_MEDICAL_SURGERIES_ID = "KEY_SET_MEDICAL_SURGERIES_ID";
    public static final String KEY_SET_MEDICAL_SELECT_ISSUE_LIST = "KEY_SET_MEDICAL_SELECT_ISSUE_LIST";
    public static final String KEY_SET_MEDICAL_ALLERGY_STATUS = "KEY_SET_MEDICAL_ALLERGY_STATUS";
    public static final String KEY_SET_MEDICAL_ISSUE_ADD_MY_PROVIDER = "MEDICAL_ISSUE_ADD_MY_PROVIDER";
    public static final String KEY_SET_MEDICAL_IMMUNIZATOIN_ADD_MY_PROVIDER = "MEDICAL_IMMUNIZATOIN_ADD_MY_PROVIDER";
    public static final String KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_FURTHER = "MEDICAL_ADD_PROVIDER_FOR_SURGERY_FURTHER";
    public static final String KEY_SET_MEDICAL_ALLERGY_ADD_ONLY_SUBSECTION = "MEDICAL_ALLERGY_ADD_ONLY_SUBSECTION";
    public static final String KEY_SET_MEDICAL_ISSUE_ADD_ONLY_SUBSECTION = "MEDICAL_ISSUE_ADD_ONLY_SUBSECTION";
    public static final String KEY_SET_MEDICAL_IMMUNIZATION_ADD_ONLY_SUBSECTION = "MEDICAL_IMMUNIZATION_ADD_ONLY_SUBSECTION";
    public static final String KEY_SET_MEDICAL_MEDICATION_ADD_ONLY_SUBSECTION = "MEDICAL_MEDICATION_ADD_ONLY_SUBSECTION";
    public static final String KEY_SET_MEDICAL_SURGERY_ADD_ONLY_SUBSECTION = "MEDICAL_SURGERY_ADD_ONLY_SUBSECTION";
    public static final String KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_LOCATION = "MEDICAL_ADD_PROVIDER_FOR_SURGERY_LOCATION";

    //
    public static boolean IS_PASSCODE_SET = false;
    public SharedPreferenceClass(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    /**
     * Create PASSCODE SESSION FOR THE FIRST TIME
     * */
    public void createPasscodeSession(String passCodeGlobal, String passCodeLocal)
    {
        editor.putString(KEY_PASSCODE_GLOBAL, passCodeGlobal);
        editor.putString(KEY_PASSCODE_LOCAL, passCodeLocal);
        editor.putBoolean(IS_LOGIN, true);
        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getPassCodeDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_PASSCODE_GLOBAL, pref.getString(KEY_PASSCODE_GLOBAL, null));

        user.put(KEY_PASSCODE_LOCAL, pref.getString(KEY_PASSCODE_LOCAL, null));

        // return user
        return user;
    }

    public void reCreatePasscodeSession( String passCodeLocal){
        // Storing email in pref
        editor.putString(KEY_PASSCODE_LOCAL, passCodeLocal);
        // commit changes
        editor.commit();
        Log.d("done ", "done");
    }

    public void clearPasscodeSession()
    {
        Log.d("pass code clearing","hi");
        editor.remove(KEY_PASSCODE_LOCAL);
        // commit changes
        editor.commit();
    }


    public void clearEntirePasscodeSession()
    {
        editor.remove(KEY_PASSCODE_LOCAL);
        editor.remove(KEY_PASSCODE_GLOBAL);
        editor.commit();
    }

    public void setUserEmail(String email)

    {
        editor.remove(KEY_EMAIL_GLOBAL);
        editor.putString(KEY_EMAIL_GLOBAL, email);
        editor.commit();

    }

    public String getUserEmail()
    {
        Log.d("inside email get","email");
        String  email= pref.getString(KEY_EMAIL_GLOBAL, null);
        if( email==null) {
            Log.d(" email null",email);
        }

        Log.d(" email got",email);
        return email;
    }

    public void setUserPassword(String password)

    {
        editor.remove(KEY_PASSWORD_GLOBAL);
        editor.putString(KEY_PASSWORD_GLOBAL, password);
        editor.commit();

    }
    public String getUserPassword()
    {

        String  password= pref.getString(KEY_PASSWORD_GLOBAL, null);

        return password;
    }
    public void setCurrentSessionUserId(String id)

    {
        editor.remove(SET_CURRENT_SESSION_USER_ID);
        editor.putString(SET_CURRENT_SESSION_USER_ID, id);
        editor.commit();

    }
    public String getCurrentSessionUserId()
    {
        String  id= pref.getString(SET_CURRENT_SESSION_USER_ID, null);
        return id;
    }

    public void setLastInsertedRowId(String id)

    {
        editor.remove(SET_LAST_INSERTED_ROW_ID);
        editor.putString(SET_LAST_INSERTED_ROW_ID, id);
        editor.commit();

    }
    public String getLastInsertedRowId()
    {
        String  id= pref.getString(SET_LAST_INSERTED_ROW_ID, null);
        return id;
    }


    public void setUserPhone(String phone)

    {
        editor.remove(KEY_MOBILE_GLOBAL);
        editor.putString(KEY_MOBILE_GLOBAL, phone);
        editor.commit();
    }

    public String getUserPhone()
    {
        String  phone= pref.getString(KEY_MOBILE_GLOBAL, null);
        return phone;
    }

    public void setUserName(String username)

    {
        editor.remove(KEY_NAME_GLOBAL);
        editor.putString(KEY_NAME_GLOBAL, username);
        editor.commit();
    }

    public String getUserName()
    {
        String  username= pref.getString(KEY_NAME_GLOBAL, null);
        return username;
    }

    public void setUserZip(String userZip)

    {
        editor.remove(KEY_ZIP_GLOBAL);
        editor.putString(KEY_ZIP_GLOBAL, userZip);
        editor.commit();
    }

    public String getUserZip()
    {
        String  userZip= pref.getString(KEY_ZIP_GLOBAL, null);
        return userZip;
    }

    public void setUserDob(String userDob)

    {
        editor.remove(KEY_DOB_GLOBAL);
        editor.putString(KEY_DOB_GLOBAL, userDob);
        editor.commit();
    }

    public String getUserDob()
    {
        String  userDob= pref.getString(KEY_DOB_GLOBAL, null);
        return userDob;
    }

    public void setSignUpType(String signUpType)

    {
        editor.remove(KEY_SIGN_UP_TYPE);
        editor.putString(KEY_SIGN_UP_TYPE, signUpType);
        editor.commit();
    }

    public String getSignUpType()
    {
        String  signUpType= pref.getString(KEY_SIGN_UP_TYPE, null);
        return signUpType;
    }

    public void setUserAuthId(String id)

    {
        editor.remove(KEY_USER_AUTH_ID);
        editor.putString(KEY_USER_AUTH_ID, id);
        editor.commit();

    }
    public String getUserAuthId()
    {

        String  id= pref.getString(KEY_USER_AUTH_ID, null);

        return id;
    }

    public void setLastActivity(String lastActivity)

    {
        editor.remove(KEY_LAST_ACTIVITY);
        editor.putString(KEY_LAST_ACTIVITY, lastActivity);
        editor.commit();

    }
    public String getLastActivity()
    {

        String  lastActivity= pref.getString(KEY_LAST_ACTIVITY, null);

        return lastActivity;
    }

    public void setVerificationCode(String verificationCode)

    {
        editor.remove(KEY_VERIFICATION_CODE);
        editor.putString(KEY_VERIFICATION_CODE, verificationCode);
        editor.commit();

    }
    public String getVerificationCode()
    {
        String  verificationCode= pref.getString(KEY_VERIFICATION_CODE, null);
        return verificationCode;
    }

    public void setPassword(String password)

    {
        editor.remove(KEY_PASSWORD);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();

    }
    public String getPassword()
    {
        String  password= pref.getString(KEY_PASSWORD, null);
        return password;
    }


    public void setSignUp(String signuptype)

    {
        Log.d("inside setsignuptype","hi");
        editor.putString(KEY_IS_GOOGLE_SIGN_UP, signuptype);
        editor.commit();

    }
    public String getSignUp()
    {

        String  signuptype= pref.getString(KEY_IS_GOOGLE_SIGN_UP, null);

        return signuptype;
    }
    public void clearSignUp()
    {
        Log.d("inside clear sign up","inside clear sign up");
        editor.remove(KEY_IS_GOOGLE_SIGN_UP);
        editor.commit();
    }

    public void setProviderName(String providerName)

    {
        editor.remove(KEY_PROVIDER_NAME);
        editor.putString(KEY_PROVIDER_NAME, providerName);
        editor.commit();
    }

    public String getProviderName()
    {
        String  providerName= pref.getString(KEY_PROVIDER_NAME, null);
        return providerName;
    }

    public void setProviderAddress(String providerAddress)

    {
        editor.remove(KEY_PROVIDER_Address);
        editor.putString(KEY_PROVIDER_Address, providerAddress);
        editor.commit();
    }

    public String getProviderAddress()
    {
        String  providerAddress= pref.getString(KEY_PROVIDER_Address, null);
        return providerAddress;
    }

    public void setProviderPhone(String providerPhone)

    {
        editor.remove(KEY_PROVIDER_Phone);
        editor.putString(KEY_PROVIDER_Phone, providerPhone);
        editor.commit();
    }

    public String getProviderPhone()
    {
        String  providerPhone= pref.getString(KEY_PROVIDER_Phone, null);
        return providerPhone;
    }
    public void setProviderFax(String setProviderFax)

    {
        editor.remove(KEY_PROVIDER_Fax);
        editor.putString(KEY_PROVIDER_Fax, setProviderFax);
        editor.commit();
    }

    public String getProviderFax()
    {
        String  getProviderFax= pref.getString(KEY_PROVIDER_Fax, null);
        return getProviderFax;
    }

    public void setProviderType(String providertype)

    {
        editor.remove(KEY_SET_PROVIDER_TYPE);
        editor.putString(KEY_SET_PROVIDER_TYPE, providertype);
        editor.commit();

    }
    public String getProviderType()
    {
        String  providertype= pref.getString(KEY_SET_PROVIDER_TYPE, null);
        return providertype;
    }

    public void setProviderSearchLocation(String providersearchlocation)

    {
        editor.remove(KEY_SET_PROVIDER_SEARCH_LOCATION);
        editor.putString(KEY_SET_PROVIDER_SEARCH_LOCATION, providersearchlocation);
        editor.commit();

    }
    public String getProviderSearchLocation()
    {
        String  providersearchlocation= pref.getString(KEY_SET_PROVIDER_SEARCH_LOCATION, null);
        return providersearchlocation;
    }
    public void setProviderSearchDistance(String providersearchdistance)

    {
        editor.remove(KEY_SET_PROVIDER_SEARCH_DISTANCE);
        editor.putString(KEY_SET_PROVIDER_SEARCH_DISTANCE, providersearchdistance);
        editor.commit();

    }
    public String getProviderSearchDistance()
    {
        String  providersearchdistance= pref.getString(KEY_SET_PROVIDER_SEARCH_DISTANCE, null);
        return providersearchdistance;
    }

    public void setProviderGender(String providerGender)

    {
        editor.remove(KEY_GENDER);
        editor.putString(KEY_GENDER, providerGender);
        editor.commit();

    }
    public String getProviderGender()
    {
        String  providerGender= pref.getString(KEY_GENDER, null);
        return providerGender;
    }
    public void setProviderSpeciality(String setProviderSpeciality)

    {
        editor.remove(KEY_SPECIALITY);
        editor.putString(KEY_SPECIALITY, setProviderSpeciality);
        editor.commit();

    }
    public String getProviderSpeciality()
    {
        String  getProviderSpeciality= pref.getString(KEY_SPECIALITY, null);
        return getProviderSpeciality;
    }
    public void setCurrentZipCode(String CurrentZipCode)

    {
        editor.remove(KEY_SET_CURRENT_ZIP_CODE);
        editor.putString(KEY_SET_CURRENT_ZIP_CODE, CurrentZipCode);
        editor.commit();

    }
    public String getCurrentZipCode()
    {
        String  getCurrentZipCode= pref.getString(KEY_SET_CURRENT_ZIP_CODE, null);
        return getCurrentZipCode;
    }
    public void setProviderEmail(String ProviderEmail)

    {
        editor.remove(KEY_SET_REGISTER_EMAIL);
        editor.putString(KEY_SET_REGISTER_EMAIL, ProviderEmail);
        editor.commit();

    }
    public String getProviderEmail()
    {
        String  getProviderEmail = pref.getString(KEY_SET_REGISTER_EMAIL, null);
        return  getProviderEmail;
    }
    public void ClearProviderEmail(String setProviderEmail)
    {
        editor.remove(KEY_SET_REGISTER_EMAIL);
        // editor.putString(KEY_SET_REGISTER_EMAIL, setProviderEmail);
        editor.commit();

    }


    public void setProviderEmailForRegistration(String ProviderEmailForRegistration)
    {
        editor.remove(KEY_SET_LOGIN_TO_PRIMARY_DETAILS);
        editor.putString(KEY_SET_LOGIN_TO_PRIMARY_DETAILS, ProviderEmailForRegistration);
        editor.commit();

    }
    public String getProviderEmailForRegistration()
    {
        String  getProviderEmailForRegistration = pref.getString(KEY_SET_LOGIN_TO_PRIMARY_DETAILS, null);
        return  getProviderEmailForRegistration;
    }


    public void setLoginType(String ProviderLoginType)
    {
        editor.remove(KEY_SET_LOGIN_TYPE);
        editor.putString(KEY_SET_LOGIN_TYPE, ProviderLoginType);
        editor.commit();

    }
    public String getLoginType()
    {
        String  getLoginType = pref.getString(KEY_SET_LOGIN_TYPE, null);
        return  getLoginType;
    }


    public void setLoginFrom(String ProviderLoginFrom)
    {
        editor.remove(KEY_SET_LOGIN_FROM);
        editor.putString(KEY_SET_LOGIN_FROM, ProviderLoginFrom);
        editor.commit();

    }
    public String getLoginFrom()
    {
        String  getLoginFrom = pref.getString(KEY_SET_LOGIN_FROM, null);
        return  getLoginFrom;
    }

    public void setProviderPassCode(String ProviderPassCode)
    {
        editor.remove(KEY_SET_PROVIDER_PASSCODE);
        editor.putString(KEY_SET_PROVIDER_PASSCODE, ProviderPassCode);
        editor.commit();

    }
    public String getProviderPassCode()
    {
        String  getProviderPassCode = pref.getString(KEY_SET_PROVIDER_PASSCODE, null);
        return  getProviderPassCode;
    }
    public void setProviderSearchType(String setPharmacyHospitalFromDoctorActivity)
    {
        editor.remove(KEY_SET_PHARMACY_HOSPITAL_FROM_DOCTOR_ACTIVITY);
        editor.putString(KEY_SET_PHARMACY_HOSPITAL_FROM_DOCTOR_ACTIVITY, setPharmacyHospitalFromDoctorActivity);
        editor.commit();

    }
    public String getProviderSearchType()
    {
        String  getPharmacyHospitalFromDoctorActivity = pref.getString(KEY_SET_PHARMACY_HOSPITAL_FROM_DOCTOR_ACTIVITY, null);
        return  getPharmacyHospitalFromDoctorActivity;
    }
    public void setProviderNPIid(String setProviderNPIid)
    {
        editor.remove(KEY_SET_PROVIDER_NPI_ID);
        editor.putString(KEY_SET_PROVIDER_NPI_ID, setProviderNPIid);
        editor.commit();

    }
    public String getProviderNPIid()
    {
        String  getProviderNPIid = pref.getString(KEY_SET_PROVIDER_NPI_ID, null);
        return  getProviderNPIid;
    }
    public void setCurrentTabFragment(String setCurrentTabFragment)
    {
        editor.remove(KEY_SET_CURRENT_TAB_FRAGMENT);
        editor.putString(KEY_SET_CURRENT_TAB_FRAGMENT, setCurrentTabFragment);
        editor.commit();

    }
    public String getCurrentTabFragment()
    {
        String  getCurrentTabFragment = pref.getString(KEY_SET_CURRENT_TAB_FRAGMENT, null);
        return  getCurrentTabFragment;
    }

    public void setUserComments(String setUserComments)
    {
        editor.remove(KEY_SET_USER_COMMENTS);
        editor.putString(KEY_SET_USER_COMMENTS, setUserComments);
        editor.commit();

    }
    public String getUserComments()
    {
        String  getUserComments = pref.getString(KEY_SET_USER_COMMENTS, null);
        return  getUserComments;
    }
    public void setDistance(String setDistance)
    {
        editor.remove(KEY_SET_DISTANCE_VALUE);
        editor.putString(KEY_SET_DISTANCE_VALUE, setDistance);
        editor.commit();

    }
    public String getDistance()
    {
        String  getDistance = pref.getString(KEY_SET_DISTANCE_VALUE, null);
        return  getDistance;
    }
   public void setEmergencyorUrgentcare(String setEmergencyorUrgentcare)
    {
        editor.remove(KEY_SET_EMERGENCY_0RURGENTCARE);
        editor.putString(KEY_SET_EMERGENCY_0RURGENTCARE, setEmergencyorUrgentcare);
        editor.commit();

    }
    public String getEmergencyorUrgentcare()
    {
        String  getEmergencyorUrgentcare = pref.getString(KEY_SET_EMERGENCY_0RURGENTCARE, null);
        return  getEmergencyorUrgentcare;
    }
    public void setProviderNameSearchTerm(String setProviderNameSearchTerm)

    {
        editor.remove(KEY_PROVIDER_NAME_SEARCH_TERM);
        editor.putString(KEY_PROVIDER_NAME_SEARCH_TERM, setProviderNameSearchTerm);
        editor.commit();
    }

    public String getProviderNameSearchTerm()
    {
        String  getProviderNameSearchTerm= pref.getString(KEY_PROVIDER_NAME_SEARCH_TERM, null);
        return getProviderNameSearchTerm;
    }
    public void setProviderBusinessAddress(String setProviderBusinessAddress)

    {
        editor.remove(KEY_PROVIDER__BUSINESS_ADDRESS);
        editor.putString(KEY_PROVIDER__BUSINESS_ADDRESS, setProviderBusinessAddress);
        editor.commit();
    }

    public String getProviderBusinessAddress()
    {
        String  getProviderBusinessAddress= pref.getString(KEY_PROVIDER__BUSINESS_ADDRESS, null);
        return getProviderBusinessAddress;
    }
    public void setProviderPracticeAddress(String setProviderPracticeAddress)

    {
        editor.remove(KEY_PROVIDER_PRACTICE_ADDRESS);
        editor.putString(KEY_PROVIDER_PRACTICE_ADDRESS, setProviderPracticeAddress);
        editor.commit();
    }

    public String getProviderPracticeAddress()
    {
        String  getProviderPracticeAddress= pref.getString(KEY_PROVIDER_PRACTICE_ADDRESS, null);
        return getProviderPracticeAddress;
    }

    public void setProviderEmailOnDialog(String setProviderEmailOnDialog)
    {
        editor.remove(KEY_SET_DIALOG_PROVIDER_EMAIL);
        editor.putString(KEY_SET_DIALOG_PROVIDER_EMAIL, setProviderEmailOnDialog);
        editor.commit();

    }
    public String getProviderEmailOnDialog()
    {
        String  getProviderEmailOnDialog = pref.getString(KEY_SET_DIALOG_PROVIDER_EMAIL, null);
        return getProviderEmailOnDialog;
    }

    public void setProviderPasswordOnDialog(String setProviderPasswordOnDialog)
    {
        editor.remove(KEY_SET_DIALOG_PROVIDER_PASSWORD);
        editor.putString(KEY_SET_DIALOG_PROVIDER_PASSWORD, setProviderPasswordOnDialog);
        editor.commit();

    }
    public String getProviderPasswordOnDialog()
    {
        String  getProviderPasswordOnDialog = pref.getString(KEY_SET_DIALOG_PROVIDER_PASSWORD, null);
        return getProviderPasswordOnDialog;
    }

    public void setSocialSubstanceName(String setSocialSubstanceName)
    {
        editor.remove(KEY_SET_SUBSTANCE_NAME);
        editor.putString(KEY_SET_SUBSTANCE_NAME, setSocialSubstanceName);
        editor.commit();

    }
    public String getSocialSubstanceName()
    {
        String  getSocialSubstanceName = pref.getString(KEY_SET_SUBSTANCE_NAME, null);
        return getSocialSubstanceName;
    }

    public void setSocialSubstanceNameTwo(String setSocialSubstanceNameTwo)
    {
        editor.remove(KEY_SET_SUBSTANCE_NAME_TWO);
        editor.putString(KEY_SET_SUBSTANCE_NAME_TWO, setSocialSubstanceNameTwo);
        editor.commit();

    }
    public String getSocialSubstanceNameTwo()
    {
        String  getSocialSubstanceNameTwo = pref.getString(KEY_SET_SUBSTANCE_NAME_TWO, null);
        return getSocialSubstanceNameTwo;
    }

    public void setCurrentChartId(String id)

    {
        editor.remove(SET_CURRENT_CHART_ID);
        editor.putString(SET_CURRENT_CHART_ID, id);
        editor.commit();

    }
    public String getCurrentChartId()
    {
        String  id= pref.getString(SET_CURRENT_CHART_ID, null);
        return id;
    }
    public void setProviderFromChartFlagId(String id)

    {
        editor.remove(SET_PROVIDER_FINDER_FROM_CHART_LOAD_FLAG);
        editor.putString(SET_PROVIDER_FINDER_FROM_CHART_LOAD_FLAG, id);
        editor.commit();

    }
    public String getProviderFromChartFlagId()
    {
        String  id= pref.getString(SET_PROVIDER_FINDER_FROM_CHART_LOAD_FLAG, null);
        return id;
    }

    public void setMedicalIssueName(String MedicalIssueName)
    {
        editor.remove(KEY_SET_MEDICAL_ISSUE_NAME);
        editor.putString(KEY_SET_MEDICAL_ISSUE_NAME, MedicalIssueName);
        editor.commit();

    }
    public String getMedicalIssueName()
    {
        String  MedicalIssueName = pref.getString(KEY_SET_MEDICAL_ISSUE_NAME, null);
        return MedicalIssueName;
    }

    public void setTobaccoProductName(String TobaccoProductName)
    {
        editor.remove(KEY_SET_TOBACCO_PRODUCT_NAME);
        editor.putString(KEY_SET_TOBACCO_PRODUCT_NAME, TobaccoProductName);
        editor.commit();

    }
    public String getTobaccoProductName()
    {
        String  TobaccoProductName = pref.getString(KEY_SET_TOBACCO_PRODUCT_NAME, null);
        return TobaccoProductName;
    }
    public void setSubstanceSearchAgainFromSubstanceSelectedFlag(String flag)

    {
        editor.remove(SET_SUBSTANCE_SEARCH_AGAIN_FROM_SELECTED_SUBSTANCE_FLAG);
        editor.putString(SET_SUBSTANCE_SEARCH_AGAIN_FROM_SELECTED_SUBSTANCE_FLAG, flag);
        editor.commit();

    }
    public String getSubstanceSearchAgainFromSubstanceSelectedFlag()
    {
        String  flag= pref.getString(SET_SUBSTANCE_SEARCH_AGAIN_FROM_SELECTED_SUBSTANCE_FLAG, null);
        return flag;
    }

    public void setProviderLoginStatus(String logged)

    {
        editor.remove(KEY_SET_PROVIDER_LOGIN_STATUS);
        editor.putString(KEY_SET_PROVIDER_LOGIN_STATUS, logged);
        editor.commit();

    }
    public String getProviderLoginStatus()
    {
        String  logged= pref.getString(KEY_SET_PROVIDER_LOGIN_STATUS, null);
        return logged;
    }

    public void setChartBasicInfoEditFlag(String ChartBasicInfoEditFlag)

    {
        editor.remove(KEY_CHART_BASIC_INFO_EDIT_FLAG);
        editor.putString(KEY_CHART_BASIC_INFO_EDIT_FLAG, ChartBasicInfoEditFlag);
        editor.commit();

    }
    public String getChartBasicInfoEditFlag()
    {
        String  ChartBasicInfoEditFlag= pref.getString(KEY_CHART_BASIC_INFO_EDIT_FLAG, null);
        return ChartBasicInfoEditFlag;
    }
    public void setChartSocialHistoryEditFlag(String ChartSocialHistoryEditFlag)

    {
        editor.remove(KEY_CHART_SOCIAL_HISTORY_EDIT_FLAG);
        editor.putString(KEY_CHART_SOCIAL_HISTORY_EDIT_FLAG, ChartSocialHistoryEditFlag);
        editor.commit();

    }
    public String getChartSocialHistoryEditFlag()
    {
        String  ChartSocialHistoryEditFlag= pref.getString(KEY_CHART_SOCIAL_HISTORY_EDIT_FLAG, null);
        return ChartSocialHistoryEditFlag;
    }
    public void setChartFamilyHistoryEditFlag(String ChartFamilyHistoryEditFlag)
        {
            editor.remove(KEY_CHART_FAMILY_HISTORY_EDIT_FLAG);
            editor.putString(KEY_CHART_FAMILY_HISTORY_EDIT_FLAG, ChartFamilyHistoryEditFlag);
            editor.commit();
        }
    public String getChartFamilyHistoryEditFlag()
        {
            String  ChartFamilyHistoryEditFlag= pref.getString(KEY_CHART_FAMILY_HISTORY_EDIT_FLAG, null);
            return ChartFamilyHistoryEditFlag;
        }

    public void setChartFinancialHistoryEditFlag(String ChartFinancialHistoryEditFlag)
        {
            editor.remove(KEY_CHART_FINANCIAL_HISTORY_EDIT_FLAG);
            editor.putString(KEY_CHART_FINANCIAL_HISTORY_EDIT_FLAG, ChartFinancialHistoryEditFlag);
            editor.commit();
        }
    public String getChartFinancialHistoryEditFlag()
        {
            return pref.getString(KEY_CHART_FINANCIAL_HISTORY_EDIT_FLAG, null);
        }

    public void setAllergyDetailsEditFlag(String AllergyDetailsEditFlag)
        {
            editor.remove(KEY_ALLERGY_DETAILS_EDIT_FLAG);
            editor.putString(KEY_ALLERGY_DETAILS_EDIT_FLAG, AllergyDetailsEditFlag);
            editor.commit();
        }
    public String getAllergyDetailsEditFlag()
        {
            return pref.getString(KEY_ALLERGY_DETAILS_EDIT_FLAG, null);
        }

    public void setIssueDetailsEditFlag(String IssueDetailsEditFlag)
        {
            editor.remove(KEY_ISSUE_DETAILS_EDIT_FLAG);
            editor.putString(KEY_ISSUE_DETAILS_EDIT_FLAG, IssueDetailsEditFlag);
            editor.commit();
        }
    public String getIssueDetailsEditFlag()
        {
            return pref.getString(KEY_ISSUE_DETAILS_EDIT_FLAG, null);
        }
    public void setImmunizationDetailsEditFlag(String ImmunizationDetailsEditFlag)
        {
            editor.remove(KEY_IMMUNIZATION_DETAILS_EDIT_FLAG);
            editor.putString(KEY_IMMUNIZATION_DETAILS_EDIT_FLAG, ImmunizationDetailsEditFlag);
            editor.commit();
        }
    public String getImmunizationDetailsEditFlag()
        {
            return pref.getString(KEY_IMMUNIZATION_DETAILS_EDIT_FLAG, null);
        }
    public void setMedicationDetailsEditFlag(String MedicationDetailsEditFlag)
        {
            editor.remove(KEY_MEDICATION_DETAILS_EDIT_FLAG);
            editor.putString(KEY_MEDICATION_DETAILS_EDIT_FLAG, MedicationDetailsEditFlag);
            editor.commit();
        }

    public String getMedicationDetailsEditFlag()
        {
            return pref.getString(KEY_MEDICATION_DETAILS_EDIT_FLAG, null);
        }
    public void setSurgeryDetailsEditFlag(String SurgeryDetailsEditFlag)
        {
            editor.remove(KEY_SURGERY_DETAILS_EDIT_FLAG);
            editor.putString(KEY_SURGERY_DETAILS_EDIT_FLAG, SurgeryDetailsEditFlag);
            editor.commit();
        }

    public String getSurgeryDetailsEditFlag()
        {
            return pref.getString(KEY_SURGERY_DETAILS_EDIT_FLAG, null);
        }
    public void setInsuranceCardId(String insurancecardid)
    {
        editor.remove(KEY_CHART_FINANCE_HISTORY_LAST_INSERTED_INSURANCE_CARD_ID);
        editor.putString(KEY_CHART_FINANCE_HISTORY_LAST_INSERTED_INSURANCE_CARD_ID, insurancecardid);
        editor.commit();
    }
    public String getInsuranceCardId()
    {
        String  insurancecardid= pref.getString(KEY_CHART_FINANCE_HISTORY_LAST_INSERTED_INSURANCE_CARD_ID, null);
        return insurancecardid;
    }

    public void setSocialControlledSubstanceAddFlag(String SocialControlledSubstanceAddFlag)
    {
        editor.remove(KEY_SOCIAL_CONTROLLED_SUBSTANCE_ADD_FLAG);
        editor.putString(KEY_SOCIAL_CONTROLLED_SUBSTANCE_ADD_FLAG, SocialControlledSubstanceAddFlag);
        editor.commit();
    }
    public String getSocialControlledSubstanceAddFlag()
    {
        String  SocialControlledSubstanceAddFlag= pref.getString(KEY_SOCIAL_CONTROLLED_SUBSTANCE_ADD_FLAG, null);
        return SocialControlledSubstanceAddFlag;
    }

    public void setCurrentControlledSubstanceId(String id)

    {
        editor.remove(SET_CURRENT_CONTROLLED_SUBSTANCE_ID);
        editor.putString(SET_CURRENT_CONTROLLED_SUBSTANCE_ID, id);
        editor.commit();

    }
    public String getCurrentControlledSubstanceId()
    {
        String  id= pref.getString(SET_CURRENT_CONTROLLED_SUBSTANCE_ID, null);
        return id;
    }

    public void setSocialControlledSubstanceEditFlag(String SocialControlledSubstanceEditFlag)
    {
        editor.remove(KEY_SOCIAL_CONTROLLED_SUBSTANCE_EDIT_FLAG);
        editor.putString(KEY_SOCIAL_CONTROLLED_SUBSTANCE_EDIT_FLAG, SocialControlledSubstanceEditFlag);
        editor.commit();
    }
    public String getSocialControlledSubstanceEditFlag()
    {
        String  SocialControlledSubstanceEditFlag= pref.getString(KEY_SOCIAL_CONTROLLED_SUBSTANCE_EDIT_FLAG, null);
        return SocialControlledSubstanceEditFlag;
    }

    public void setCurrentMedicalIssueId(String id)

    {
        editor.remove(SET_CURRENT_MEDICAL_ISSUE_ID);
        editor.putString(SET_CURRENT_MEDICAL_ISSUE_ID, id);
        editor.commit();

    }
    public String getCurrentMedicalIssueId()
    {
        String  id= pref.getString(SET_CURRENT_MEDICAL_ISSUE_ID, null);
        return id;
    }

    public void setFamilyMedicalIssueAddFlag(String FamilyMedicalIssueAddFlag)
    {
        editor.remove(KEY_FAMILY_MEDICAL_ISSUE_ADD_FLAG);
        editor.putString(KEY_FAMILY_MEDICAL_ISSUE_ADD_FLAG, FamilyMedicalIssueAddFlag);
        editor.commit();
    }
    public String getFamilyMedicalIssueAddFlag()
    {
        String  FamilyMedicalIssueAddFlag= pref.getString(KEY_FAMILY_MEDICAL_ISSUE_ADD_FLAG, null);
        return FamilyMedicalIssueAddFlag;
    }

    public void setCurrentInsuranceCardId(String CurrentInsuranceCardId)
        {
            editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_ID);
            editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_ID, CurrentInsuranceCardId);
            editor.commit();
        }
    public String getCurrentInsuranceCardId()
        {
            String  CurrentInsuranceCardId= pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_ID, null);
            return CurrentInsuranceCardId;
        }

    public void setCurrentInsuranceCardType(String CurrentInsuranceCardType)
        {
            editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_TYPE);
            editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_TYPE, CurrentInsuranceCardType);
            editor.commit();
        }
    public String getCurrentInsuranceCardType()
        {
            String  CurrentInsuranceCardType= pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_TYPE, null);
            return CurrentInsuranceCardType;
        }

    public void setCurrentInsuranceCardName(String CurrentInsuranceCardName)
        {
            editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_NAME);
            editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_NAME, CurrentInsuranceCardName);
            editor.commit();
        }
    public String getCurrentInsuranceCardName()
        {
            return pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_NAME, null);
        }

    public void setCurrentInsuredName(String CurrentInsuredName)
        {
            editor.remove(KEY_SET_CURRENT_INSURED_NAME);
            editor.putString(KEY_SET_CURRENT_INSURED_NAME, CurrentInsuredName);
            editor.commit();
        }
    public String getCurrentInsuredName()
        {
            return pref.getString(KEY_SET_CURRENT_INSURED_NAME, null);
        }

    public void setCurrentInsuranceCardMembershipNumber(String CurrentInsuranceCardMembershipNumber)
        {
            editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_MEMBERSHIP_NUMBER);
            editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_MEMBERSHIP_NUMBER, CurrentInsuranceCardMembershipNumber);
            editor.commit();
        }
    public String getCurrentInsuranceCardMembershipNumber()
        {
            return pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_MEMBERSHIP_NUMBER, null);
        }

    public void setCurrentInsuranceCardSuffix(String CurrentInsuranceCardSuffix)
        {
            editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_SUFFIX);
            editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_SUFFIX, CurrentInsuranceCardSuffix);
            editor.commit();
        }
    public String getCurrentInsuranceCardSuffix()
        {
            return pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_SUFFIX, null);
        }

    public void setCurrentInsuranceCardGuarantor(String CurrentInsuranceCardGuarantor)
        {
            editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_GUARANTOR);
            editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_GUARANTOR, CurrentInsuranceCardGuarantor);
            editor.commit();
        }
    public String getCurrentInsuranceCardGuarantor()
        {
            return pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_GUARANTOR, null);
        }

    public void setCurrentInsuranceCardGroupId(String CurrentInsuranceCardGroupId)
    {
        editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_GROUP_ID);
        editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_GROUP_ID, CurrentInsuranceCardGroupId);
        editor.commit();
    }
    public String getCurrentInsuranceCardGroupId()
    {
        return pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_GROUP_ID, null);
    }

    public void setCurrentInsuranceCardCustomerServiceNumber(String CurrentInsuranceCardCustomerServiceNumber)
        {
            editor.remove(KEY_SET_CURRENT_INSURANCE_CARD_CUSTOMER_SERVICE_NUMBER);
            editor.putString(KEY_SET_CURRENT_INSURANCE_CARD_CUSTOMER_SERVICE_NUMBER, CurrentInsuranceCardCustomerServiceNumber);
            editor.commit();
        }
    public String getCurrentInsuranceCardCustomerServiceNumber()
        {
            String  CurrentInsuranceCardCustomerServiceNumber= pref.getString(KEY_SET_CURRENT_INSURANCE_CARD_CUSTOMER_SERVICE_NUMBER, null);
            return CurrentInsuranceCardCustomerServiceNumber;
        }

    public void setCurrentChartOrNot(String CurrentChartOrNot)
        {
            editor.remove(KEY_SET_CURRENT_CHART_OR_NOT);
            editor.putString(KEY_SET_CURRENT_CHART_OR_NOT, CurrentChartOrNot);
            editor.commit();
        }
    public String getCurrentChartOrNot()
        {
            String  CurrentChartOrNot= pref.getString(KEY_SET_CURRENT_CHART_OR_NOT, null);
            return CurrentChartOrNot;
        }
    public void setMedicalAllergyIssue(String setMedicalAllergyIssue)
    {
        editor.remove(KEY_SET_MEDICAL_ALLERGY_ISSUE_ITEM);
        editor.putString(KEY_SET_MEDICAL_ALLERGY_ISSUE_ITEM, setMedicalAllergyIssue);
        editor.commit();
    }
    public String getMedicalAllergyIssue()
    {
        String  getMedicalAllergyIssue= pref.getString(KEY_SET_MEDICAL_ALLERGY_ISSUE_ITEM, null);
        return getMedicalAllergyIssue;
    }
    public void setMedicalSurgeryAddProvider(String setMedicalSurgeryAddProvider)
    {
        editor.remove(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY);
        editor.putString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY, setMedicalSurgeryAddProvider);
        editor.commit();
    }
    public String getMedicalSurgeryAddProvider()
    {
        return pref.getString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY, null);

    }
    public void setMedicalProviderAddProvider(String MedicalProviderAddProvider)
    {
        editor.remove(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PROVIDER);
        editor.putString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PROVIDER, MedicalProviderAddProvider);
        editor.commit();
    }
    public String getMedicalProviderAddProvider()
    {
        return pref.getString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PROVIDER, null);

    }
    public void setMedicalPharmacyAddProvider(String MedicalPharmacyAddProvider)
    {
            editor.remove(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PHARMACY);
            editor.putString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PHARMACY, MedicalPharmacyAddProvider);
            editor.commit();
    }
    public String getMedicalPharmacyAddProvider()
    {
    return pref.getString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_PHARMACY, null);

    }
    public void setMedicalHospitalAddProvider(String MedicalHospitalAddProvider)
    {
        editor.remove(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_HOSPITAL);
        editor.putString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_HOSPITAL, MedicalHospitalAddProvider);
        editor.commit();
    }
    public String getMedicalHospitalAddProvider()
    {
        return pref.getString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_HOSPITAL, null);

    }
    public void setLastInsertedMedicalIssueId(String id)

    {
        editor.remove(SET_LAST_INSERTED_MEDICAL_ISSUE_ID);
        editor.putString(SET_LAST_INSERTED_MEDICAL_ISSUE_ID, id);
        editor.commit();

    }
    public String getLastInsertedMedicalIssueId()
    {
        String  id= pref.getString(SET_LAST_INSERTED_MEDICAL_ISSUE_ID, null);
        return id;
    }

    public void setCurrentMedicalAllergyID(String currentMedicalAllergyID)
        {
            editor.remove(SET_CURRENT_MEDICAL_ALLERGY_ID);
            editor.putString(SET_CURRENT_MEDICAL_ALLERGY_ID, currentMedicalAllergyID);
            editor.commit();
        }
    public String getCurrentMedicalAllergyID()
        {
            String  currentMedicalAllergyID= pref.getString(SET_CURRENT_MEDICAL_ALLERGY_ID, null);
            return currentMedicalAllergyID;
        }

    public void setMedicalAllergyId(String MedicalAllergyId)
        {
            editor.remove(KEY_SET_MEDICAL_ALLERGIES_ID);
            editor.putString(KEY_SET_MEDICAL_ALLERGIES_ID, MedicalAllergyId);
            editor.commit();
        }
    public String getMedicalAllergyId()
        {
            return pref.getString(KEY_SET_MEDICAL_ALLERGIES_ID, null);
        }

    public void setMedicalIssuesId(String MedicalIssuesId)
        {
            editor.remove(KEY_SET_MEDICAL_ISSUES_ID);
            editor.putString(KEY_SET_MEDICAL_ISSUES_ID, MedicalIssuesId);
            editor.commit();
        }
    public String getMedicalIssuesId()
        {
            return pref.getString(KEY_SET_MEDICAL_ISSUES_ID, null);
        }

    public void setMedicalImmunizationsId(String MedicalImmunizationsId)
        {
            editor.remove(KEY_SET_MEDICAL_IMMUNIZATIONS_ID);
            editor.putString(KEY_SET_MEDICAL_IMMUNIZATIONS_ID, MedicalImmunizationsId);
            editor.commit();
        }
    public String getMedicalImmunizationsId()
        {
            return pref.getString(KEY_SET_MEDICAL_IMMUNIZATIONS_ID, null);
        }

    public void setMedicalMedicationsId(String MedicalMedicationsId)
        {
            editor.remove(KEY_SET_MEDICAL_MEDICATIONS_ID);
            editor.putString(KEY_SET_MEDICAL_MEDICATIONS_ID, MedicalMedicationsId);
            editor.commit();
        }
    public String getMedicalMedicationsId()
        {
            return pref.getString(KEY_SET_MEDICAL_MEDICATIONS_ID, null);
        }
    public void setMedicalSurgeriesId(String MedicalSurgeriesId)
        {
            editor.remove(KEY_SET_MEDICAL_SURGERIES_ID);
            editor.putString(KEY_SET_MEDICAL_SURGERIES_ID, MedicalSurgeriesId);
            editor.commit();
        }
    public String getMedicalSurgeriesId()
        {
            return pref.getString(KEY_SET_MEDICAL_SURGERIES_ID, null);
        }

    public void setMedicalSelectIssueList(String MedicalSelectIssueList)
        {
            editor.remove(KEY_SET_MEDICAL_SELECT_ISSUE_LIST);
            editor.putString(KEY_SET_MEDICAL_SELECT_ISSUE_LIST, MedicalSelectIssueList);
            editor.commit();
        }
    public String getMedicalSelectIssueList()
    {
        return pref.getString(KEY_SET_MEDICAL_SELECT_ISSUE_LIST, null);
    }
    //renamed setMedicalAllergyStatus To setMedicalAllergyAddMyProvider, This reName by Amitabha..KEY name shoud be change also
    public void setMedicalAllergyAddMyProvider(String MedicalAllergyStatus)
    {
        editor.remove(KEY_SET_MEDICAL_ALLERGY_STATUS);
        editor.putString(KEY_SET_MEDICAL_ALLERGY_STATUS, MedicalAllergyStatus);
        editor.commit();
    }
    public String getMedicalAllergyAddMyProvider()
    {
        return pref.getString(KEY_SET_MEDICAL_ALLERGY_STATUS, null);
    }
    public void setMedicalIssueAddMyProvider(String IssueAddMyProvider)
    {
        editor.remove(KEY_SET_MEDICAL_ISSUE_ADD_MY_PROVIDER);
        editor.putString(KEY_SET_MEDICAL_ISSUE_ADD_MY_PROVIDER, IssueAddMyProvider);
        editor.commit();
    }
    public String getMedicalIssueAddMyProvider()
    {
        return pref.getString(KEY_SET_MEDICAL_ISSUE_ADD_MY_PROVIDER, null);
    }
    public void setMedicalImmunizationAddMyProvider(String ImmunizationAddMyProvider)
    {
        editor.remove(KEY_SET_MEDICAL_IMMUNIZATOIN_ADD_MY_PROVIDER);
        editor.putString(KEY_SET_MEDICAL_IMMUNIZATOIN_ADD_MY_PROVIDER, ImmunizationAddMyProvider);
        editor.commit();
    }
    public String getMedicalImmunizationAddMyProvider()
    {
        return pref.getString(KEY_SET_MEDICAL_IMMUNIZATOIN_ADD_MY_PROVIDER, null);
    }
    public void setMedicalSurgeryAddProviderFurther(String SurgeryAddProviderFurther)
    {
        editor.remove(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_FURTHER);
        editor.putString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_FURTHER, SurgeryAddProviderFurther);
        editor.commit();
    }
    public String getMedicalSurgeryAddProviderFurther()
    {
        return pref.getString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_FURTHER, null);

    }

    public void setMedicalSurgeryAddProviderLocation(String SurgeryAddProviderLocation)
    {
        editor.remove(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_LOCATION);
        editor.putString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_LOCATION, SurgeryAddProviderLocation);
        editor.commit();
    }
    public String getMedicalSurgeryAddProviderLocation()
    {
        return pref.getString(KEY_SET_MEDICAL_ADD_PROVIDER_FOR_SURGERY_LOCATION, null);

    }


    public void setMedicalAllergyAddOnlySubSectionFlag(String MedicalAllergyAddOnlySubSection)
    {
        editor.remove(KEY_SET_MEDICAL_ALLERGY_ADD_ONLY_SUBSECTION);
        editor.putString(KEY_SET_MEDICAL_ALLERGY_ADD_ONLY_SUBSECTION, MedicalAllergyAddOnlySubSection);
        editor.commit();
    }
    public String getMedicalAllergyAddOnlySubSectionFlag()
    {
        return pref.getString(KEY_SET_MEDICAL_ALLERGY_ADD_ONLY_SUBSECTION, null);

    }

    public void setMedicalIssueAddOnlySubSectionFlag(String MedicalIssueAddOnlySubSectionFlag)
    {
        editor.remove(KEY_SET_MEDICAL_ISSUE_ADD_ONLY_SUBSECTION);
        editor.putString(KEY_SET_MEDICAL_ISSUE_ADD_ONLY_SUBSECTION, MedicalIssueAddOnlySubSectionFlag);
        editor.commit();
    }
    public String getMedicalIssueAddOnlySubSectionFlag()
    {
        return pref.getString(KEY_SET_MEDICAL_ISSUE_ADD_ONLY_SUBSECTION, null);

    }

    public void setMedicalImmunizationAddOnlySubSectionFlag(String MedicalImmunizationAddOnlySubSectionFlag)
    {
        editor.remove(KEY_SET_MEDICAL_IMMUNIZATION_ADD_ONLY_SUBSECTION);
        editor.putString(KEY_SET_MEDICAL_IMMUNIZATION_ADD_ONLY_SUBSECTION, MedicalImmunizationAddOnlySubSectionFlag);
        editor.commit();
    }
    public String getMedicalImmunizationAddOnlySubSectionFlag()
    {
        return pref.getString(KEY_SET_MEDICAL_IMMUNIZATION_ADD_ONLY_SUBSECTION, null);

    }

    public void setMedicalMedicationAddOnlySubSectionFlag(String MedicalMedicationAddOnlySubSectionFlag)
    {
        editor.remove(KEY_SET_MEDICAL_MEDICATION_ADD_ONLY_SUBSECTION);
        editor.putString(KEY_SET_MEDICAL_MEDICATION_ADD_ONLY_SUBSECTION, MedicalMedicationAddOnlySubSectionFlag);
        editor.commit();
    }
    public String getMedicalMedicationAddOnlySubSectionFlag()
    {
        return pref.getString(KEY_SET_MEDICAL_MEDICATION_ADD_ONLY_SUBSECTION, null);

    }

    public void setMedicalSurgeryAddOnlySubSectionFlag(String MedicalSurgeryAddOnlySubSectionFlag)
    {
        editor.remove(KEY_SET_MEDICAL_SURGERY_ADD_ONLY_SUBSECTION);
        editor.putString(KEY_SET_MEDICAL_SURGERY_ADD_ONLY_SUBSECTION, MedicalSurgeryAddOnlySubSectionFlag);
        editor.commit();
    }
    public String getMedicalSurgeryAddOnlySubSectionFlag()
    {
        return pref.getString(KEY_SET_MEDICAL_SURGERY_ADD_ONLY_SUBSECTION, null);

    }

}