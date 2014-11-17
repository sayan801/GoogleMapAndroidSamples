package com.example.technicise_mac3.technicisemapbox;

/**
 * Created by technicise on 20/6/14.
 */
public class ApiUrls
{

    //public String BaseUrl = "http://festsocially.com/github/code/index.php?";
    public String BaseUrl = "http://curatehealth.net:81/webservice/sayan801/code/index.php?";
    public String BaseUrlFestSocially = "http://festsocially.com/github/code/index.php?";

    public String getTaxonomyDetails = BaseUrl + "/provider/getTaxonomyDetails/";
    public String getProviderLogin = BaseUrl + "/userAccounts/getUserAccountsDetails/";
    public String getProviderInfoByEmergencyUrgentCareZip = BaseUrl + "/provider/getProviderInfoByEmergencyUrgentCareZip";
    public String getProviderInfoBySpecialityZipDistance = BaseUrl + "/provider/getProviderInfoBySpecialityZipDistance/";
    public String getProviderInfoByPartialNameZipDistance = BaseUrl + "/provider/getProviderInfoByPartialNameZipDistance/";
    public String getLatLongFromAddress = BaseUrl + "/geocoding/getLatLongFromAddress/";
    public String getProviderPharmacyByZipDistance = BaseUrl + "/provider/getProviderByPharmacyZipDistance/";
    public String getProviderHospitalByZipDistance = BaseUrl + "/provider/getProviderByHospitalZipDistance/";
    public String getProviderInfoByZip = BaseUrl + "/provider/getProviderInfoByZip/";
    public String getProviderInfoByNPI = BaseUrl + "/provider/getProviderInfoByNPI/";
    public String getUserAccountsDetails = BaseUrl + "/userAccounts/getUserAccountsDetails/";
    public String addUserPasscode = BaseUrl + "/userAccounts/addUserPasscode/";
    public String getProviderInfoByHospitalNPI = BaseUrl + "/provider/getProviderInfoByHospitalNPI";
    public String getPHARMACYnameAddressPhone = BaseUrl + "/provider/getProviderInfoByPharmacyNPI";
    public String getProvidernameAddressPhone = BaseUrl + "/provider/getProviderNameAddressPhone";
    public String msCheckEmailExistance = BaseUrl + "/microsoftUserAccounts/checkEmailExistance/";
    public String msLoginAccount = BaseUrl + "/microsoftUserAccounts/loginAccount/";
    public String msRegisterUserAccount = BaseUrl + "/microsoftUserAccounts/registerUserAccount/";

    public String googleCheckEmailExistance = BaseUrl + "/googleUserAccounts/checkEmailExistance/";
    public String googleLoginAccount = BaseUrl + "/googleUserAccounts/loginAccount/";
    public String googleRegisterUserAccount = BaseUrl + "/googleUserAccounts/registerUserAccount/";

    public String nativeCheckEmailExistance = BaseUrl + "/nativeUserAccounts/checkEmailExistance/";
    public String nativeLoginAccount = BaseUrl + "/nativeUserAccounts/loginAccount/";
    public String nativeRegisterUserAccount = BaseUrl + "/nativeUserAccounts/registerUserAccount/";

    public String addUserTermsAndConditionAcceptance = BaseUrl + "/userAccounts/addUserTermsAndConditionAcceptance/";

    public String addUserDob = BaseUrl + "/userProfiles/addUserDob/";
    public String getUserProfileDetails = BaseUrl + "/userProfiles/getUserProfileDetails/";
    public String getUserPhone = BaseUrl + "/userAccounts/getUserPhone/";

    public String addUserFirstLastName = BaseUrl + "/userProfiles/addUserFirstLastName/";
    public String addUserPhoneNumber = BaseUrl + "/userAccounts/addUserPhoneNumber/";

    public String addUserVerifiedStatus = BaseUrl + "/userAccounts/addUserVerifiedStatus/";
    public String email_verification = BaseUrlFestSocially + "/email_verification/user/id/";
    public String sms_verification = BaseUrlFestSocially + "/sms_verification/user/id/";
    public String getUserEmail = BaseUrl + "/userAccounts/getUserEmail/";
    public String addUserZipcode = BaseUrl + "/userProfiles/addUserZipcode/";
    public String zipTestUrl = "http://ziptasticapi.com/";
    public String getAllComments = BaseUrl + "/comments/getAllComments/";
    public String getAllClassifications = BaseUrl + "/provider/getAllSpecialityDisplayNames/";
    public String registerOrUpdateComment = BaseUrl + "/comments/registerOrUpdateComment/";

    public String ProviderUpdatePassword = BaseUrl + "/nativeUserAccounts/updateUserPassword/";
    public String sendBugReport = BaseUrl + "/bugreport/setBugDetails/";

    public String EventsTabHomeScreen = BaseUrl + "/events/getEventsByUser/290/";

    public String RemindersTabHomeScreen = BaseUrl + "/reminders/getRemindersByUser/291/";
    public String getHospitalOfficeDataByNpi = BaseUrl + "/provider/getHospitalOfficeBioDataByNpi/";
    public String getProviderInfoByFullNameZipDistance = BaseUrl + "/provider/getProviderInfoByFullNameZipDistance/";
    public String getZipFromCityName = BaseUrl + "/geocoding/getZipFromCityName/";
    public String autocomplete = "https://maps.googleapis.com/maps/api/place/autocomplete/";

}