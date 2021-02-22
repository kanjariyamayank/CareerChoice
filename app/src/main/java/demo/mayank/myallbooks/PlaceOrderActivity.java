package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlaceOrderActivity extends AppCompatActivity {
    EditText inputFirstName, inputLastName, inputMobile, inputEmail, inputPassword, inputadress;
    Button place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        inputFirstName = findViewById(R.id.input_first_name);
        ;
        inputMobile = findViewById(R.id.input_mobile);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputadress = findViewById(R.id.input_Address);
        place = findViewById(R.id.place_order);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFirstName.getText().toString().equals("")) {
                    Toast.makeText(PlaceOrderActivity.this, "Enter first name", Toast.LENGTH_SHORT).show();
                } else if (inputEmail.getText().toString().equals("")) {
                    Toast.makeText(PlaceOrderActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.getText().toString().equals("")) {
                    Toast.makeText(PlaceOrderActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (inputMobile.getText().toString().equals("")) {
                    Toast.makeText(PlaceOrderActivity.this, "Enter mobile", Toast.LENGTH_SHORT).show();
                }
                if (inputadress.getText().toString().equals("")) {
                    Toast.makeText(PlaceOrderActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlaceOrderActivity.this, "Order Place Is Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PlaceOrderActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}