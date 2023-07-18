package com.abeldandi.alitasecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abeldandi.alitasecurity.adapter.CalendarAdapter;
import com.abeldandi.alitasecurity.callback.FetchRecyclerViewItems;
import com.abeldandi.alitasecurity.databinding.ActivityAbssenceBinding;
import com.abeldandi.alitasecurity.databinding.ActivitySecurity1Binding;
import com.abeldandi.alitasecurity.model.CalendarDateModel;
import com.abeldandi.alitasecurity.model.DataObject;
import com.abeldandi.alitasecurity.utils.HorizontalItemDecoration;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AbssenceActivity extends AppCompatActivity implements FetchRecyclerViewItems {

    private ActivityAbssenceBinding binding;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private final Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private final Calendar currentDate = Calendar.getInstance(Locale.ENGLISH);
    private final ArrayList<Date> dates = new ArrayList<>();
    private CalendarAdapter adapter;
    private final ArrayList<CalendarDateModel> calendarList2 = new ArrayList<>();
    List<DataObject> elements;
    DatabaseReference databaseReference;
    String targetSecurityID = "";

    private Date selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbssenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AlitaSecurity");
        init();
        setUpAdapter();
        setUpClickListener();
        setUpCalendar();
        try {
            Intent intent = getIntent();
            ListElement element = intent.getParcelableExtra("element");
            binding.securityname.setText(element.securityname);
            binding.idnumber.setText(element.idnumber);
            targetSecurityID = element.getIdnumber().equals("TT-6A-01") ? "1" : "2";
        } catch (Exception e) {
            System.out.println("Something When Wrong");
        }

        Glide.with(getApplicationContext())
                .load("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                .into(binding.iconImageView);

        ImageView menuIcon = findViewById(R.id.menu);
        menuIcon.setOnClickListener(view -> startActivity(new Intent(AbssenceActivity.this, NavDrawerActivity.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_abssence);

        binding.ln1.setOnClickListener(view -> onBackPressed());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_employee) {
                startActivity(new Intent(getApplicationContext(), EmployeeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_abssence) {
                startActivity(new Intent(getApplicationContext(), AbssenceActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_notifications) {
                startActivity(new Intent(getApplicationContext(), NotifActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_message) {
                startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

    private void setUpClickListener() {
        binding.ivCalendarNext.setOnClickListener(v -> {
            cal.add(Calendar.MONTH, 1);
            setUpCalendar();
        });

        binding.ivCalendarPrevious.setOnClickListener(v -> {
            cal.add(Calendar.MONTH, -1);
            setUpCalendar();
        });
    }
    private void setUpAdapter() {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.single_calendar_margin);
        binding.recyclerView.addItemDecoration(new HorizontalItemDecoration(spacingInPixels));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerView);

        adapter = new CalendarAdapter((calendarDateModel, position) -> {
            for (int i = 0; i < calendarList2.size(); i++) {
                calendarList2.get(i).setSelected(i == position);
            }
            adapter.setData(calendarList2);

            selectedDate = dates.get(position);
            init();
        });

        binding.recyclerView.setAdapter(adapter);
    }
    private void setUpCalendar() {
        ArrayList<CalendarDateModel> calendarList = new ArrayList<>();
        binding.tvDateMonth.setText(sdf.format(cal.getTime()));
        Calendar monthCalendar = (Calendar) cal.clone();
        int maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        dates.clear();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        int currentDateIndex = -1;

        Calendar currentDateNoTime = Calendar.getInstance(Locale.ENGLISH);
        currentDateNoTime.set(Calendar.HOUR_OF_DAY, 0);
        currentDateNoTime.set(Calendar.MINUTE, 0);
        currentDateNoTime.set(Calendar.SECOND, 0);
        currentDateNoTime.set(Calendar.MILLISECOND, 0);

        while (dates.size() < maxDaysInMonth) {
            Date currentDate = monthCalendar.getTime();

            if (!currentDate.before(currentDateNoTime.getTime())) {
                dates.add(currentDate);
                CalendarDateModel calendarDateModel = new CalendarDateModel(currentDate, false);
                calendarList.add(calendarDateModel);

                if (currentDateIndex == -1 &&
                        monthCalendar.get(Calendar.YEAR) == currentDateNoTime.get(Calendar.YEAR) &&
                        monthCalendar.get(Calendar.MONTH) == currentDateNoTime.get(Calendar.MONTH) &&
                        monthCalendar.get(Calendar.DAY_OF_MONTH) == currentDateNoTime.get(Calendar.DAY_OF_MONTH)) {
                    currentDateIndex = dates.size() - 1;
                }
            }

            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (currentDateIndex != -1) {
            calendarList.get(currentDateIndex).setSelected(true);
            selectedDate = dates.get(currentDateIndex);
        } else {
            selectedDate = null;
        }

        calendarList2.clear();
        calendarList2.addAll(calendarList);
        adapter.setData(calendarList);
    }
    public void init() {
        elements = new ArrayList<>();

        if (selectedDate == null) {
            selectedDate = Calendar.getInstance().getTime();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userID = dataSnapshot.child("userID").getValue(String.class);
                    String status = dataSnapshot.child("status").getValue(String.class);
                    String roomID = dataSnapshot.child("roomID").getValue(String.class);

                    if (userID != null && userID.equals(targetSecurityID)
                            && selectedDate != null && isSameDay(selectedDate, cal.getTime())
                            && status != null && status.equals("CLEAR")) {
                        DataObject dataObject = new DataObject(userID, status, roomID);
                        elements.add(dataObject);
                    }
                }

                ListAdapter listAdapter = new ListAdapter(elements, AbssenceActivity.this, AbssenceActivity.this);
                binding.recylerViewRuangan.setHasFixedSize(true);
                binding.recylerViewRuangan.setLayoutManager(new LinearLayoutManager(AbssenceActivity.this));
                binding.recylerViewRuangan.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();

                if (elements.isEmpty()) {
                    Toast.makeText(AbssenceActivity.this, "Tidak ada data untuk tanggal ini.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AbssenceActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onIntent(DataObject dataObject) {

    }

    @Override
    public void onIntentEmployee(ListElement element) {

    }
}
