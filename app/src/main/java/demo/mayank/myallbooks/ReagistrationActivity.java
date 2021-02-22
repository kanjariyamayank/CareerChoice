package demo.mayank.myallbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import demo.mayank.myallbooks.models.CityModel;
import demo.mayank.myallbooks.models.CountryModel;
import demo.mayank.myallbooks.models.RegistrationResponseModel;
import demo.mayank.myallbooks.models.StateModel;
import demo.mayank.myallbooks.network.NetworkClient;
import demo.mayank.myallbooks.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReagistrationActivity extends AppCompatActivity {

    Spinner countrySpinner, stateSpinner, citySpinner;
    EditText inputFirstName, inputLastName, inputMobile, inputEmail, inputPassword, inputConfirmPassword;
    RadioButton radioMale, radioFemale;
    Button buttonRegister;
    boolean isGenderSelected;
    ImageView back_image;
    String selectedGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reagistration);
        back_image = findViewById(R.id.image_back);
        inputFirstName =  findViewById(R.id.input_first_name);
        inputLastName =  findViewById(R.id.input_last_name);
        inputMobile =  findViewById(R.id.input_mobile);
        inputEmail =  findViewById(R.id.input_email);
        inputPassword =  findViewById(R.id.input_password);
        inputConfirmPassword =  findViewById(R.id.input_confirm_password);
        radioMale =  findViewById(R.id.radio_male);
        radioFemale =  findViewById(R.id.radio_female);
        buttonRegister =  findViewById(R.id.button_register);
        countrySpinner =  findViewById(R.id.country_spinner);
        stateSpinner =  findViewById(R.id.state_spinner);
        citySpinner =  findViewById(R.id.city_spinner);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isGenderSelected = true;
                    selectedGender = "Male";
                }
            }
        });

        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isGenderSelected = true;
                    selectedGender = "Female";
                }
            }
        });


        initCountrySpinner();
        ArrayList<StateModel> states = new ArrayList<>();
        states.add(new StateModel("Select State"));
        ArrayAdapter<StateModel> statesAdapter =
                new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(statesAdapter);

        ArrayList<CityModel> cities = new ArrayList<>();
        cities.add(new CityModel("Select City"));
        ArrayAdapter<CityModel> citiesAdapter =
                new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(citiesAdapter);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputFirstName.getText().toString().equals("")) {
                    Toast.makeText(ReagistrationActivity.this, "Enter first name", Toast.LENGTH_SHORT).show();
                } else if (inputLastName.getText().toString().equals("")) {
                    Toast.makeText(ReagistrationActivity.this, "Enter last name", Toast.LENGTH_SHORT).show();
                } else if (!isGenderSelected) {
                    Toast.makeText(ReagistrationActivity.this, "Select gender", Toast.LENGTH_SHORT).show();
                } else if (inputMobile.getText().toString().equals("")) {
                    Toast.makeText(ReagistrationActivity.this, "Enter mobile", Toast.LENGTH_SHORT).show();
                } else if (inputMobile.getText().toString().length() < 10) {
                    Toast.makeText(ReagistrationActivity.this, "Enter valid mobile", Toast.LENGTH_SHORT).show();
                } else if (countrySpinner.getSelectedItem().toString().equals("Select Country")) {
                    Toast.makeText(ReagistrationActivity.this, "Select country", Toast.LENGTH_SHORT).show();
                } else if (stateSpinner.getSelectedItem().toString().equals("Select State")) {
                    Toast.makeText(ReagistrationActivity.this, "Select state", Toast.LENGTH_SHORT).show();
                } else if (citySpinner.getSelectedItem().toString().equals("Select City")) {
                    Toast.makeText(ReagistrationActivity.this, "Select city", Toast.LENGTH_SHORT).show();
                } else if (inputEmail.getText().toString().equals("")) {
                    Toast.makeText(ReagistrationActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(inputEmail.getText().toString())) {
                    Toast.makeText(ReagistrationActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.getText().toString().equals("")) {
                    Toast.makeText(ReagistrationActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (inputConfirmPassword.getText().toString().equals("")) {
                    Toast.makeText(ReagistrationActivity.this, "Confirm password", Toast.LENGTH_SHORT).show();
                } else if (!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
                    Toast.makeText(ReagistrationActivity.this, "Password and confirm password must be same", Toast.LENGTH_SHORT).show();
                } else {

                    HashMap<String, String> params = new HashMap<>();
                    params.put("first_name", inputFirstName.getText().toString());
                    params.put("last_name", inputLastName.getText().toString());
                    params.put("gender", selectedGender);
                    params.put("mobile", inputMobile.getText().toString());
                    params.put("country", countrySpinner.getSelectedItem().toString());
                    params.put("state", stateSpinner.getSelectedItem().toString());
                    params.put("city", citySpinner.getSelectedItem().toString());
                    params.put("name", inputFirstName.getText().toString());
                    params.put("email", inputEmail.getText().toString());
                    params.put("password", inputPassword.getText().toString());
                    register(params);
                    Toast.makeText(ReagistrationActivity.this, "success", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initCountrySpinner() {
        ArrayList<CountryModel> countries = new ArrayList<>();
        countries.add(new CountryModel("Select Country"));
        countries.add(new CountryModel("India"));
        countries.add(new CountryModel("United State"));
        countries.add(new CountryModel("United Kingdom"));
        countries.add(new CountryModel("Australia"));

        ArrayAdapter<CountryModel> countriesAdapter =
                new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, countries);
        countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countrySpinner.setAdapter(countriesAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("Gujarat"));
                    states.add(new StateModel("Maharashtra"));
                    states.add(new StateModel("Punjab"));

                    ArrayAdapter<StateModel> statesAdapter =
                            new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    stateSpinner.setAdapter(statesAdapter);

                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 1) {
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("Rajkot"));
                                cities.add(new CityModel("Ahmedabad"));
                                cities.add(new CityModel("Junagadh"));
                                cities.add(new CityModel("Baroda"));

                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            } else if (position == 2) {
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("Mumbai"));
                                cities.add(new CityModel("Pune"));

                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);

                            } else if (position == 3) {
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("Amritsar"));
                                cities.add(new CityModel("Ludhiana"));

                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);


                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (position == 2) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("Texas"));
                    states.add(new StateModel("California"));
                    states.add(new StateModel("Maryland"));

                    ArrayAdapter<StateModel> statesAdapter =
                            new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    stateSpinner.setAdapter(statesAdapter);
                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==1){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("La Salle"));
                                cities.add(new CityModel("Frio "));


                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            }
                            else if(position==2){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("nn1"));
                                cities.add(new CityModel("nn"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);


                            }
                            else if(position==3){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("s"));
                                cities.add(new CityModel("v"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);



                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }


                else if (position == 4) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("Mackay"));
                    states.add(new StateModel("Brisbane"));
                    states.add(new StateModel("Perth"));

                    ArrayAdapter<StateModel> statesAdapter =
                            new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    stateSpinner.setAdapter(statesAdapter);
                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position==1)
                            {
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("sa"));
                                cities.add(new CityModel("vx"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            }
                            else if(position==2){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("mac"));
                                cities.add(new CityModel("az"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            }
                            else if(position==3){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("dd"));
                                cities.add(new CityModel("qad"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 3) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("Avon"));
                    states.add(new StateModel("Cardiff"));
                    states.add(new StateModel("Down"));

                    ArrayAdapter<StateModel> statesAdapter =
                            new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    stateSpinner.setAdapter(statesAdapter);
                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==1){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("abcx"));
                                cities.add(new CityModel("dcb"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);

                            }else if(position==2){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("zse"));
                                cities.add(new CityModel("atr"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            }else if(position==3){
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("qa"));
                                cities.add(new CityModel("cc"));
                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(ReagistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void register(HashMap<String, String> params) {

        final ProgressDialog progressDialog = new ProgressDialog(ReagistrationActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<RegistrationResponseModel> registerCall = networkService.register(params);
        registerCall.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponseModel> call, @NonNull Response<RegistrationResponseModel> response) {
                RegistrationResponseModel responseBody = response.body();
                if (responseBody != null) {
                    if (responseBody.getSuccess().equals("1")) {
                        Toast.makeText(ReagistrationActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(ReagistrationActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ReagistrationActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponseModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
