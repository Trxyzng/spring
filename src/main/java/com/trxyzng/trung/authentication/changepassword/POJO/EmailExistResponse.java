package com.trxyzng.trung.authentication.changepassword.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailExistResponse {
    @JsonProperty("emailExist")
    private boolean emailExist;
}
