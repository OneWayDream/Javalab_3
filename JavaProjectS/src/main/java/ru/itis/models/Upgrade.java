package ru.itis.models;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Upgrade {

    protected String name;
    protected String groupName;
}
