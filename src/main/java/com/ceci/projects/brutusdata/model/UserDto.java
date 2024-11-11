package com.ceci.projects.brutusdata.model;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        int age,
        String street,
        String city,
        String state,
        double latitude,
        double longitude,
        String maskedCcnumber
) {
    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private int age;
        private String street;
        private String city;
        private String state;
        private double latitude;
        private double longitude;
        private String maskedCcnumber;

        public Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder ccnumber(String ccnumber) {
            if (ccnumber != null && ccnumber.length() >= 4) {
                this.maskedCcnumber = "**** **** **** " + ccnumber.substring(ccnumber.length() - 4);
            } else {
                this.maskedCcnumber = "Invalid ccnumber:";
            }
            return this;
        }

        public UserDto build() {
            return new UserDto(id, firstName, lastName, age, street, city, state, latitude, longitude, maskedCcnumber);
        }
    }
}