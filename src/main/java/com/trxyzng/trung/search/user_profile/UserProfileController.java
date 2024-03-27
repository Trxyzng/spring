package com.trxyzng.trung.search.user_profile;

import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @RequestMapping(value = "/find-user-profile", method = RequestMethod.GET)
    public ResponseEntity<String> findUserProfilesByName(@RequestParam("name") String name) {
        UserProfileEntity[] result = userProfileService.findUserProfileEntitiesByName(name, 4);
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}