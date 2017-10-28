package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
@Data
@RequiredArgsConstructor
public class UserInfo {
    private String username;
    private String address;

    /*Try Zipcode*/
    private int zipcode;
    private String state;
    @JsonCreator
    public UserInfo(
            @JsonProperty("username") String username,
            @JsonProperty("address") String address,
            @JsonProperty("zipcode") int zipcode,
            @JsonProperty("state") String state) {
        this.username = username;
        this.address = address;
        this.zipcode = getZipcode();
        this.state = getState();
    }

    /*Test Feature*/
    public int getZipcode() {
        int len = address.length();
        String str = address.substring(len-5,len);
        Pattern pattern = Pattern.compile("[0-9]*");
        if (pattern.matcher(str).matches()){
            this.zipcode = Integer.parseInt(str);
        }
        else{
            this.zipcode = Integer.parseInt(null);
        }
        return this.zipcode;
    }

    public String getState() {
        int startIndex = address.lastIndexOf(',');
        String str = address.substring(startIndex + 1,address.length()-5).trim();
        return str;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }
}
