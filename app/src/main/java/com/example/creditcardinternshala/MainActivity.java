package com.example.creditcardinternshala;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    int cardType = 0;

    private static final Pattern AMEX = Pattern.compile("^3[47][0-9]{13}$");
    private static final Pattern DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$");
    private static final Pattern MASTERCARD = Pattern.compile("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$");
    private static final Pattern VISA = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$");
    private static final Pattern EXPIRY = Pattern.compile("^((0[1-9])|(1[0-2]))\\/(\\d{2})$");

    private TextInputLayout cardNo;
    private TextInputLayout expiry;
    private TextInputLayout cvv;
    private TextInputLayout fName;
    private TextInputLayout lName;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardNo = findViewById(R.id.card_number);
        expiry = findViewById(R.id.exp);
        cvv = findViewById(R.id.code);
        fName = findViewById(R.id.First_name);
        lName = findViewById(R.id.Last_name);

        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateCardNo() | !validateCVV() | !validateFirstName() | !validateLastName() | !validateCardExpiryDate()) {
                    return;
                }
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Payment successful")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert=builder.create();
                alert.show();


            }
        });


    }



    private boolean checkLuhn(String cards) {

        int nDigits = cards.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cards.charAt(i) - '0';

            if (isSecond)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return nSum % 10 == 0;

    }


    private boolean isString(String str) {
        return ((str != null)
                && (!str.equals(""))
                && (str.matches("^[a-zA-Z]*$")));
    }

    private boolean validateCardNo() {
        String cardNoInput = Objects.requireNonNull(cardNo.getEditText()).getText().toString().trim();
        if (cardNoInput.isEmpty()) {
            cardNo.setError("Field can't be empty");
            return false;
        } else if ((AMEX.matcher(cardNoInput).matches()) && (checkLuhn(cardNoInput))) {
            cardType = 3;
            return true;
        } else if ((DISCOVER.matcher(cardNoInput).matches()) && (checkLuhn(cardNoInput))) {
            cardType = 6;
            return true;
        } else if ((MASTERCARD.matcher(cardNoInput).matches()) && (checkLuhn(cardNoInput))) {
            cardType = 5;
            return true;
        } else if ((VISA.matcher(cardNoInput).matches()) && (checkLuhn(cardNoInput))) {
            cardType = 4;
            return true;
        }

        cardNo.setError("Invalid number");
        return false;

    }

    private boolean validateCVV() {
        String cvvInput = Objects.requireNonNull(cvv.getEditText()).getText().toString().trim();
        if (cvvInput.isEmpty())
            cvv.setError("Field can't be empty");
        else if (cardType == 3) {

            return true;
        } else if (cardType == 6) {

            return true;
        } else if (cardType == 5) {

            return true;
        } else if (cardType == 4) {

            return true;
        }
        cvv.setError("Invalid");
        return false;
    }

    private boolean validateFirstName() {
        String userFirstNameInput = Objects.requireNonNull(fName.getEditText()).getText().toString().trim();
        if (userFirstNameInput.isEmpty()) {
            fName.setError("Field cant be empty");
            return false;
        } else if (userFirstNameInput.length() <= 2) {
            fName.setError("Name too short");
            return false;
        } else if (isString(userFirstNameInput))
            return true;

        fName.setError("try again");
        return false;

    }

    private boolean validateLastName() {
        String userLastNameInput = Objects.requireNonNull(lName.getEditText()).getText().toString().trim();
        if (userLastNameInput.isEmpty()) {
            lName.setError("Field cant be empty");
            return false;
        } else if (userLastNameInput.length() <= 2) {
            lName.setError("Name too short");
            return false;
        } else if (isString(userLastNameInput)) {

            return true;
        }
        lName.setError("try again");
        return false;

    }

    private boolean validateCardExpiryDate() {
        String expiryDateInput = Objects.requireNonNull(expiry.getEditText()).getText().toString().trim();
        if (expiryDateInput.isEmpty()) {
            expiry.setError("Field cannot be Empty");
            return false;
        } else if (expiry.getEditText().getText().length() < 5){
            expiry.setError("Please check Card expiry & try again");
            return false;
        }
        else if (EXPIRY.matcher(expiryDateInput).matches())
            return true;
        expiry.setError("Write in the format of MM/YY");
        return false;
    }





}