package org.yourcompany.yourproject.Entity;

import java.util.regex.Pattern;

public class User {
    private int id;
    private String name;
    private String email;
    private String phonenumber;
    private String address;
    private String password;

    // Các biểu thức chính quy để kiểm tra định dạng
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID phải là một số nguyên dương.");
        }
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống.");
        }
        this.name = name.trim();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống.");
        }
        // Kiểm tra định dạng email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Định dạng email không hợp lệ.");
        }
        this.email = email.trim();
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        if (phonenumber == null) {
            throw new IllegalArgumentException("Số điện thoại không được để trống.");
        }
        String cleaned = phonenumber.trim();
        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống.");
        }
        if (!cleaned.matches("\\d+")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ. Chỉ được chứa chữ số.");
        }
        this.phonenumber = cleaned;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        if (address != null && address.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được chỉ chứa khoảng trắng.");
        } else if (address != null) {
            this.address = address.trim();
        } else {
            this.address = null;
        }
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống.");
        }
        this.password = password.trim();
    }


    public User() {}

    public User(int id, String name, String email, String phonenumber, String address, String password) {
        setId(id);
        setName(name);
        setEmail(email);
        setPhonenumber(phonenumber);
        setAddress(address);
        setPassword(password);
    }
}