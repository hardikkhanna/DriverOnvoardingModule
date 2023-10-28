package com.module.constants;

public class MysqlConstants {

    // user related queries
    public static final String FIND_BY_USERNAME_QUERY = "SELECT u FROM User u WHERE u.username = :username";

    public static final String FIND_USER_BY_ROLE = "SELECT u FROM User u WHERE u.role = :role";

    public static final String IS_USERNAME_TAKEN_QUERY = "SELECT COUNT(u.id) FROM User u WHERE u.username = :username";

    public static final String FIND_USER_BY_EMAIL = "SELECT u FROM User u WHERE u.email = :email";

    public static final String APPROVE_DOCUMENT_QUERY = "UPDATE Document SET verified = true where id = :id";

    public static final String GET_PENDING_DOCS = "SELECT d FROM Document d WHERE d.verified = :verified";

    public static final String FIND_DRIVER_BY_USER_ID = "SELECT COUNT(*) FROM Driver d WHERE d.user = :userId";
}
