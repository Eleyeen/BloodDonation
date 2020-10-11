package com.example.blooddonation.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonation.R;
import com.example.blooddonation.adapter.AutoCompleteIngredientsAdapter;
import com.example.blooddonation.interfaces.BloodGroupItemId;
import com.example.blooddonation.models.bloodGroupNameModel.GetBloodGroupDataModel;
import com.example.blooddonation.models.bloodGroupNameModel.GetBloodGroupResponse;
import com.example.blooddonation.models.signUpModel.SignUpRespones;
import com.example.blooddonation.network.APIClient;
import com.example.blooddonation.network.ApiInterface;
import com.example.blooddonation.network.BaseNetworking;
import com.example.blooddonation.utils.AlertUtils;
import com.example.blooddonation.utils.Connectivity;
import com.example.blooddonation.utils.GeneralUtills;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, BloodGroupItemId, RadioGroup.OnCheckedChangeListener {
    final int CAMERA_CAPTURE = 1;
    final int RESULT_LOAD_IMAGE = 2;

    Uri image_uri, cameraImageUri;

    final int GALLERY_PIC = 2;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    File sourceFile;
    public static String fileName;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    ArrayList<GetBloodGroupDataModel> listModels = new ArrayList<>();

    AutoCompleteIngredientsAdapter adapter;
    private String strItemBloodGroup = "";

    @BindView(R.id.civProfilePic)
    CircleImageView civProfile;
    @BindView(R.id.etFullNameSignUp)
    EditText etNameSignUp;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rbtn_male)
    RadioButton radioMale;
    @BindView(R.id.rbtn_female)
    RadioButton radioFemale;

    @BindView(R.id.etEmailSignUp)
    EditText etEmailSignUp;
    @BindView(R.id.etPasswordSignUp)
    EditText etPassword;
    @BindView(R.id.etPhoneNumSignUp)
    EditText etPhoneNUmber;
    @BindView(R.id.etWeightSignUp)
    EditText etWeight;
    @BindView(R.id.etAgeSignUp)
    EditText etAge;
    @BindView(R.id.etLocationSignUp)
    EditText etLocation;
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    @BindView(R.id.spItemBloodGroup)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    Dialog dialog;
    String strName, strBloodGroup = "null", strEmail, strPassword, strPhoneNumber, strAge, strWeight, strArea, strGender;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                strBloodGroup = String.valueOf(listModels.get(i).getId());
            }
        });

        iniListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void iniListener() {
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        civProfile.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        getBloodGroupName();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.radioGroup:
                if (radioMale.isChecked()) {
                    strGender = "Male";
                } else if (radioFemale.isChecked()) {
                    strGender = "Female";
                }
                break;
        }
    }

    private boolean validate() {
        boolean valid = true;
        strName = etNameSignUp.getText().toString();
        strEmail = etEmailSignUp.getText().toString();
        strPassword = etPassword.getText().toString();
        strPhoneNumber = etPhoneNUmber.getText().toString();
        strAge = etAge.getText().toString();
        strWeight = etWeight.getText().toString();
        strArea = etLocation.getText().toString();


        if (sourceFile == null) {
            valid = false;
            Toast.makeText(this, "set profile image", Toast.LENGTH_SHORT).show();
        }

        if (strName.isEmpty()) {
            etNameSignUp.setError("enter a valid name");
            valid = false;
        } else {
            etNameSignUp.setError(null);
        }
        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            etEmailSignUp.setError("enter a valid email address");
            valid = false;
        } else {
            etEmailSignUp.setError(null);
        }
        if (strBloodGroup.equals("null")) {
            autoCompleteTextView.setError("enter a Blood Group");
            valid = false;
        } else {
            autoCompleteTextView.setError(null);
        }
        if (strPassword.isEmpty() || strPassword.length() < 6) {
            etPassword.setError("Password should be more than 6 characters");
            valid = false;
        } else {
            etPassword.setError(null);
        }
        if (strPhoneNumber.isEmpty()) {
            etPhoneNUmber.setError("Enter a Phone Number");
            valid = false;
        } else {
            etPassword.setError(null);
        }
        if (strAge.isEmpty()) {
            etAge.setError("Enter a Age");
        } else {
            etAge.setError(null);
        }
        if (strWeight.isEmpty()) {
            etWeight.setError("Enter a Weight");
        } else {
            etWeight.setError(null);
        }
        if (strArea.isEmpty()) {
            etLocation.setError("Enter a Location");
        } else {
            etLocation.setError(null);
        }

        if (strGender == null) {
            valid = false;
        }

        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }


        return valid;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                if (validate()) {
                    doSignUp();
                }
                break;
            case R.id.tvLogin:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
            case R.id.civProfilePic:
                checkPermission();
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getBloodGroupName() {
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);

        Call<GetBloodGroupResponse> call = services.getgroups();

        call.enqueue(new Callback<GetBloodGroupResponse>() {
            @Override
            public void onResponse(Call<GetBloodGroupResponse> call, Response<GetBloodGroupResponse> response) {
                if (response.isSuccessful()) {
                    listModels.addAll(response.body().getData());
                    adapter = new AutoCompleteIngredientsAdapter(SignUpActivity.this, listModels, SignUpActivity.this);
                    autoCompleteTextView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<GetBloodGroupResponse> call, Throwable t) {
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doSignUp() {
        dialog.show();
        Log.d("zma image path", String.valueOf(sourceFile));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), sourceFile.getAbsoluteFile());
        final MultipartBody.Part profileImage = MultipartBody.Part.createFormData("profile_image", sourceFile.getAbsoluteFile().getName(), requestFile);
        RequestBody BodyName = RequestBody.create(MediaType.parse("text/plain"), "upload-test");
        RequestBody BodyFullName = RequestBody.create(MediaType.parse("multipart/form-data"), strName);
        RequestBody BodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"), strEmail);
        RequestBody BodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), strPassword);
        RequestBody BodyPhoneNumber = RequestBody.create(MediaType.parse("multipart/form-data"), strPhoneNumber);
        RequestBody BodyAge = RequestBody.create(MediaType.parse("multipart/form-data"), strAge);
        RequestBody BodyWeight = RequestBody.create(MediaType.parse("multipart/form-data"), strWeight);
        RequestBody BodyGender = RequestBody.create(MediaType.parse("multipart/form-data"), strGender);
        RequestBody BodyArea = RequestBody.create(MediaType.parse("multipart/form-data"), strArea);
        RequestBody BodyBloodGroup = RequestBody.create(MediaType.parse("multipart/form-data"), strBloodGroup);


        Call<SignUpRespones> signupResponseModelCall = BaseNetworking.apiServices().createUser(BodyFullName, BodyEmail, BodyPassword, BodyPhoneNumber, BodyAge, BodyWeight, BodyGender, BodyArea, BodyBloodGroup, profileImage, BodyName);
        signupResponseModelCall.enqueue(new Callback<SignUpRespones>() {
            @Override
            public void onResponse(Call<SignUpRespones> call, Response<SignUpRespones> response) {
                if (response.isSuccessful()) {
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_id", String.valueOf(response.body().getData().getId()));
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_name", response.body().getData().getFullname());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_email", response.body().getData().getEmail());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_BloodGroup", response.body().getData().getGroupId());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_Age", response.body().getData().getAge());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_Weight", response.body().getData().getWeight());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_phoneNUmber", response.body().getData().getPhone());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_Location", response.body().getData().getArea());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_gender", response.body().getData().getGender());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_profile_image", response.body().getData().getProfileImage());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_area", response.body().getData().getArea());

                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SignUpRespones> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(SignUpActivity.this, "Successful error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("zama error", String.valueOf(t.getMessage()));

            }
        });
    }


    @Override
    public void bloodGroupItem(String id) {
        strItemBloodGroup = id;

    }

    private void checkPermission() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                cameraBuilder();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken
                    token) {

            }
        }).check();
    }

    public void cameraIntent() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureIntent, CAMERA_CAPTURE);
    }

    public void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    //open camera view
    public void cameraBuilder() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Open");
        String[] pictureDialogItems = {
                "\tGallery",
                "\tCamera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                galleryIntent();

                                break;
                            case 1:
                                cameraIntent();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImageUri = data.getData();
            String imagepath = getPath(selectedImageUri);
            sourceFile = new File(imagepath);

            civProfile.setImageURI(selectedImageUri);

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            sourceFile = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                sourceFile.createNewFile();
                fo = new FileOutputStream(sourceFile);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            civProfile.setImageBitmap(thumbnail);
        }
    }

    @SuppressLint("SetTextI18n")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        civProfile.setImageBitmap(BitmapFactory.decodeFile(filePath));
        return cursor.getString(column_index);

    }


}