package com.bmi.internship.example.model.sampleGet;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
class Data{
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;
}

@Getter
@Setter
public class SampleResponseGet {
    public String page;
    public String per_page;
    public String total;
    public String total_pages;
    public List<Data> data;


}
