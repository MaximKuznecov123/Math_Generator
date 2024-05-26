package com.example.myapplication.DataManagers.FireBaseConstants;

import com.example.myapplication.R;
import java.util.HashMap;
public final class ErrorCodes {
    //TODO протестировать приложение получше и посмотреть каких ошибок нет
    // com.google.firebase.FirebaseError в помощь
    public static final String ERROR_INVALID_EMAIL = "ERROR_INVALID_EMAIL";
    public static final String ERROR_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD";
    public static final String ERROR_USER_MISMATCH = "ERROR_USER_MISMATCH";
    public static final String ERROR_INVALID_USERNAME = "ERROR_USERNAME";
    public static final String EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE";

    public static final HashMap<String, Integer> errorMap = new HashMap<String, Integer>(){{
        put(ERROR_INVALID_EMAIL, R.string.email_not_correct_error);
        put(ERROR_WRONG_PASSWORD, R.string.password_not_correct_error);
        put(ERROR_USER_MISMATCH, R.string.sign_in_error);
        put(ERROR_INVALID_USERNAME, R.string.name_must_be_filled_error);
        put(EMAIL_ALREADY_IN_USE, R.string.email_already_in_use_error);
    }};
}
