package com.example.organichub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class image extends AppCompatActivity {

    private ImageView img;
    private Button add_img;
    private EditText product_name, product_desc, product_price;
    private Spinner gen,categ,subCateg;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("products");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("images/");
    private Uri imageUri;

    ArrayList<String> gender;
    ArrayAdapter<String> adapter_gender;

    ArrayList<String> Male, Female, Kids;
    ArrayAdapter<String> adapter_category;

    ArrayList<String> Topwear, BottomWear, Footwear;
    ArrayAdapter<String> adapter_subCateg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = findViewById(R.id.img_view);
        add_img = findViewById(R.id.add);
        product_name = findViewById(R.id.pname);
        product_desc = findViewById(R.id.pdesc);
        product_price = findViewById(R.id.pprice);
        gen = findViewById(R.id.gender);
        categ = findViewById(R.id.category);
        subCateg = findViewById(R.id.subcategory);

        spinner();


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    uploadImage(imageUri);
                }else{
                    Toast.makeText(image.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        product_name.addTextChangedListener(textWatcher);
        product_desc.addTextChangedListener(textWatcher);
        product_price.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String name = product_name.getText().toString();
            String description = product_desc.getText().toString();
            String price = product_price.getText().toString();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void selectImage(){

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent , 2);

    }
    private void spinner(){
        gender =new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        gender.add("Kids");

        adapter_gender =new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, gender);
        gen.setAdapter(adapter_gender);

        Male = new ArrayList<>();
        Male.add("Topwear");
        Male.add("Bottomwear");
        Male.add("Footwear");

        Female = new ArrayList<>();
        Female.add("Topwear");
        Female.add("Bottomwear");
        Female.add("Footwear");

        Kids = new ArrayList<>();
        Kids.add("Topwear");
        Kids.add("Bottomwear");
        Kids.add("Footwear");

        Topwear = new ArrayList<>();
        Topwear.add("Shirts");
        Topwear.add("T-Shirts");
        Topwear.add("Jackets");
        Topwear.add("Blazer");
        Topwear.add("Ethnic-Wear");

        BottomWear = new ArrayList<>();
        BottomWear.add("Trouser");
        BottomWear.add("Jeans");

        Footwear = new ArrayList<>();
        Footwear.add("Loafers");
        Footwear.add("Shoes");
        Footwear.add("Sandals");
        Footwear.add("Boots");
        Footwear.add("Flip-Flops");


        gen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    adapter_category = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, Male);
                    categ.setAdapter(adapter_category);
                    categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,Topwear);
                            }
                            if(position==1){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,BottomWear);
                            }
                            if(position==2){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,Footwear);
                            }
                            subCateg.setAdapter(adapter_subCateg);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
                if(position == 1){
                    adapter_category = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, Female);
                    categ.setAdapter(adapter_category);
                    categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,Topwear);
                            }
                            if(position==1){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,BottomWear);
                            }
                            if(position==2){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,Footwear);
                            }
                            subCateg.setAdapter(adapter_subCateg);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if(position == 2){
                    adapter_category = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, Kids);
                    categ.setAdapter(adapter_category);
                    categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,Topwear);
                            }
                            if(position==1){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,BottomWear);
                            }
                            if(position==2){
                                adapter_subCateg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,Footwear);
                            }
                            subCateg.setAdapter(adapter_subCateg);
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

    private void uploadImage(Uri uri){

        String sp1 = gen.getSelectedItem().toString();
        String sp2 = categ.getSelectedItem().toString();
        String sp3 = subCateg.getSelectedItem().toString();
        String name = product_name.toString();
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = shrd.edit();

                        Model model = new Model(uri.toString(), product_name.getText().toString(), product_price.getText().toString(), product_desc.getText().toString(),null);
                        String modelId = root.push().getKey();
                        root.child(sp1).child(sp2).child(sp3).child(modelId).setValue(model);
                        Toast.makeText(image.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        img.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                        editor.putString("model", modelId);
                        editor.apply();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            img.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
}