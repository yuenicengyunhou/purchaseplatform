package com.rails.purchaseplatform.Bean;

 public class AddressBean {
     private String name;
     private String phone;
     private String address;

   /*  public Boolean getXz() {
         return xz;
     }

     public void setXz(Boolean xz) {
         this.xz = xz;
     }

     private Boolean xz;*/

     public AddressBean(String name, String phone, String address) {
         this.name = name;
         this.phone = phone;
         this.address = address;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getPhone() {
         return phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }
 }
