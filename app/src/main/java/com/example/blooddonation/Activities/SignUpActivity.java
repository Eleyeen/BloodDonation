package com.example.blooddonation.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.blooddonation.Adapter.AutoCompleteIngredientsAdapter;
import com.example.blooddonation.BuildConfig;
import com.example.blooddonation.Models.GetBloodGroupModel.Datum;
import com.example.blooddonation.Models.GetBloodGroupModel.GetBloodGroupNameModel;
import com.example.blooddonation.Models.SignUp.SignUpRespones;
import com.example.blooddonation.Network.APIClient;
import com.example.blooddonation.Network.ApiInterface;
import com.example.blooddonation.R;
import com.example.blooddonation.Utails.AlertUtils;
import com.example.blooddonation.Utails.GeneralUtills;
import com.example.blooddonation.interfaces.BloodGroupItemId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import static androidx.core.content.FileProvider.getUriForFile;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, BloodGroupItemId {
    Uri image_uri, cameraImageUri;
    final int CAMERA_CAPTURE = 1;
    final int GALLERY_PIC = 2;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    File  sourceFile;
    public static String fileName;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    ArrayList<Datum> listModels = new ArrayList<>();

    AutoCompleteIngredientsAdapter adapter;
    private String strItemBloodGroup = "";

    @BindView(R.id.civProfilePic)
    CircleImageView civProfile;
    @BindView(R.id.etFullNameSignUp)
    EditText etNameSignUp;
    @BindView(R.id.etGenderSignUp)
    EditText etGenderSignUp;
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
    AutoCompleteTextView dynamicSpinner;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    Dialog dialog;
    String strName, strBloodGroup, strEmail, strPassword, strPhoneNumber, strAge, strWeight, strArea, strGender;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        iniListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void iniListener() {
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        civProfile.setOnClickListener(this);
        getItem();
    }

    private boolean validate() {
        boolean valid = true;
        strName = etNameSignUp.getText().toString();
        strBloodGroup = dynamicSpinner.getText().toString();
        strEmail = etEmailSignUp.getText().toString();
        strPassword = etPassword.getText().toString();
        strPhoneNumber = etPhoneNUmber.getText().toString();
        strAge = etAge.getText().toString();
        strWeight = etWeight.getText().toString();
        strArea = etLocation.getText().toString();
        strGender = etGenderSignUp.getText().toString();

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
        if (strBloodGroup.isEmpty()) {
            dynamicSpinner.setError("enter a Blood Group");
            valid = false;
        } else {
            dynamicSpinner.setError(null);
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
        if (strGender.isEmpty()) {
            etGenderSignUp.setError("Enter a Gender");
        } else {
            etGenderSignUp.setError(null);
        }


        return valid;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {


            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {


                    sourceFile = new File(String.valueOf(getCacheImagePath(fileName)));
                    civProfile.setImageURI(getCacheImagePath(fileName));


                } else {
                    Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show();
                }
                break;

            case GALLERY_PIC:
                if (resultCode == RESULT_OK) {
                    image_uri = data.getData();
                    sourceFile = new File(getImagePath(image_uri));
                    civProfile.setImageURI(image_uri);

                } else {
                    Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }


        }

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
                permissionClass();
                break;

        }
    }


    public void permissionClass() {

        // Check whether this app has write external storage permission or not.
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // If do not grant write external storage permission.
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
        }

        cameraBuilder();
    }

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
                                intentGalleryPic();
                                break;
                            case 1:
                                takeCameraImage();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void takeCameraImage() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (report.areAllPermissionsGranted()) {
                                fileName = System.currentTimeMillis() + ".jpg";
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown
                            (List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).

                check();

    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(SignUpActivity.this, getPackageName() + ".provider", image);
    }

    public void intentGalleryPic() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, GALLERY_PIC);//one can be replaced with any action code
    }

    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;
        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                sourceFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getItem() {
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);

        Call<GetBloodGroupNameModel> call = services.getgroups();

        call.enqueue(new Callback<GetBloodGroupNameModel>() {
            @Override
            public void onResponse(Call<GetBloodGroupNameModel> call, Response<GetBloodGroupNameModel> response) {
                if (response.isSuccessful()) {
                    listModels.addAll(response.body().getData());
                    adapter = new AutoCompleteIngredientsAdapter(SignUpActivity.this, listModels, SignUpActivity.this);
                    dynamicSpinner.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<GetBloodGroupNameModel> call, Throwable t) {
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doSignUp() {
        dialog.show();
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);

        RequestBody BodyFullName = RequestBody.create(MediaType.parse("multipart/form-data"), strName);
        RequestBody BodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"), strEmail);
        RequestBody BodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), strPassword);
        RequestBody BodyPhoneNumber = RequestBody.create(MediaType.parse("multipart/form-data"), strPhoneNumber);
        RequestBody BodyAge = RequestBody.create(MediaType.parse("multipart/form-data"), strAge);
        RequestBody BodyWeight = RequestBody.create(MediaType.parse("multipart/form-data"), strWeight);
        RequestBody BodyGender = RequestBody.create(MediaType.parse("multipart/form-data"), strGender);
        RequestBody BodyArea = RequestBody.create(MediaType.parse("multipart/form-data"), strArea);
        RequestBody BodyBloodGroup = RequestBody.create(MediaType.parse("multipart/form-data"), strBloodGroup);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), sourceFile);
        final MultipartBody.Part profileImage = MultipartBody.Part.createFormData("profile_image", sourceFile.getName(), requestFile);
        RequestBody BodyName = RequestBody.create(MediaType.parse("text/plain"), "upload-test");

        Call<SignUpRespones> signupResponseModelCall = services.createUser(BodyFullName, BodyEmail, BodyPassword, BodyPhoneNumber, BodyAge, BodyWeight, BodyGender, BodyArea, BodyBloodGroup, profileImage, BodyName);
        signupResponseModelCall.enqueue(new Callback<SignUpRespones>() {
            @Override
            public void onResponse(Call<SignUpRespones> call, Response<SignUpRespones> response) {
                if (response.isSuccessful()) {
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_id", String.valueOf(response.body().getData().getId()));
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_name", response.body().getData().getFullname());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_email", response.body().getData().getEmail());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_BloodGroup", response.body().getData().getBloodGroup());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_Age", response.body().getData().getAge());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_Weight", response.body().getData().getWeight());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_phoneNUmber", response.body().getData().getPhone());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_Location", response.body().getData().getArea());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_gender", response.body().getData().getGender());
                    GeneralUtills.putStringValueInEditor(SignUpActivity.this, "user_profile_image", response.body().getData().getProfileImage());

                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

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

            }
        });


    }

    @Override
    public void bloodGroupItem(String id) {
        strItemBloodGroup = id;

    }
}