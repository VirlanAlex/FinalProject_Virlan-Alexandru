package backEnd.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUserModel {
    @JsonProperty("first_name")
    private String first_name;
    @JsonProperty("last_name")
    private String last_name;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("email")
    private String email;
    @JsonProperty("id")
    private String id;
    @JsonProperty("created_at")
    private String created_at;
    @JsonProperty("address")
    private AddressModel address;

    public ResponseUserModel() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public AddressModel getAddress() {
        return address;
    }
}
