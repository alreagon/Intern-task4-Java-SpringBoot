package com.bmi.internship.example.model.samplePost;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SampleResponseAPIPost {
    public String name;
    public String job;
    public String id;
    public String createdAt;

}
