package com.bmi.internship.example.model.samplePost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SampleRequestAPIPost {
    public String name;
    public String job;
}
