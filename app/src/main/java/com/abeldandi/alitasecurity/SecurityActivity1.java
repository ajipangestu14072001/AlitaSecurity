package com.abeldandi.alitasecurity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;

import com.abeldandi.alitasecurity.adapter.CalendarAdapter;
import com.abeldandi.alitasecurity.databinding.ActivitySecurity1Binding;
import com.abeldandi.alitasecurity.model.CalendarDateModel;
import com.abeldandi.alitasecurity.utils.HorizontalItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecurityActivity1 extends AppCompatActivity {

    private ActivitySecurity1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecurity1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}