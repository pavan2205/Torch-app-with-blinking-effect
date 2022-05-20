package com.example.torchapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.torchapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ImageView button,blink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        button=findViewById(R.id.button);
        blink=findViewById(R.id.blink);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.button.getTag().equals(R.drawable.power)){
                    binding.button.setImageResource(R.drawable.poweroff);
                    binding.button.setTag(R.drawable.poweroff);
                    changeLightState(true);
                }
                else{
                    binding.button.setImageResource(R.drawable.power);
                    binding.button.setTag(R.drawable.power);
                    changeLightState(false);
                }
            }
        });
        binding.blink.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(binding.button.getTag().equals(R.drawable.power)) {
                    blinkFlash();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void blinkFlash() {
        CameraManager cameraManager= (CameraManager) getSystemService(CAMERA_SERVICE);
        String myString="0101010101010101010101";
        long blinkDelay=50;
        for(int i=0;i<myString.length();i++){
            if(myString.charAt(i)=='0'){
                String camId= null;
                try {
                    camId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(camId,true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            else{
                String camId= null;
                try {
                    camId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(camId,false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            try{
                Thread.sleep(blinkDelay);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void changeLightState(boolean state) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            CameraManager cameraManager= (CameraManager) getSystemService(CAMERA_SERVICE);
            String camId=null;
            try {
                camId=cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(camId,state);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.button.setImageResource(R.drawable.power);
        binding.button.setTag(R.drawable.power);
    }
}