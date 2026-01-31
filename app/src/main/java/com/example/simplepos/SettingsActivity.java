package com.example.simplepos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_NAME = "SimplePosPrefs";
    private static final String KEY_DAILY_TARGET = "daily_target";
    private static final String KEY_SHOP_NAME = "shop_name";
    private static final String KEY_CLOSING_TIME_HOUR = "closing_time_hour";
    private static final String KEY_CLOSING_TIME_MINUTE = "closing_time_minute";
    private static final int PERMISSION_REQUEST_CODE = 101;

    private EditText editDailyTarget;
    private com.google.android.material.textfield.TextInputEditText editShopName;
    private Button btnClosingTime;
    private Button btnSave;
    
    private int closingHour = -1;
    private int closingMinute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editDailyTarget = findViewById(R.id.edit_daily_target);
        editShopName = findViewById(R.id.edit_shop_name);
        btnClosingTime = findViewById(R.id.btn_closing_time);
        btnSave = findViewById(R.id.button_save_settings);

        loadSettings();

        btnClosingTime.setOnClickListener(v -> showTimePicker());
        btnSave.setOnClickListener(v -> saveSettings());

        setupBackgroundTests();
    }
    
    private void showTimePicker() {
        int hour = closingHour != -1 ? closingHour : 21; // Default 9 PM
        int minute = closingMinute != -1 ? closingMinute : 0;
        
        new android.app.TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            closingHour = hourOfDay;
            closingMinute = minuteOfHour;
            updateTimeButton();
        }, hour, minute, true).show();
    }
    
    private void updateTimeButton() {
        if (closingHour != -1) {
            String timeStr = String.format(java.util.Locale.getDefault(), "%02d:%02d", closingHour, closingMinute);
            btnClosingTime.setText("Closing Time: " + timeStr);
        } else {
            btnClosingTime.setText("Set Closing Time (Not Set)");
        }
    }

    private void setupBackgroundTests() {
        findViewById(R.id.btn_test_async).setOnClickListener(v -> {
            new com.example.simplepos.background.legacy.SalesCalculationTask(this).execute();
        });

        findViewById(R.id.btn_test_job).setOnClickListener(v -> {
            android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            android.content.ComponentName componentName = new android.content.ComponentName(this, com.example.simplepos.background.legacy.CleanupJobService.class);
            android.app.job.JobInfo jobInfo = new android.app.job.JobInfo.Builder(124, componentName)
                    .setRequiredNetworkType(android.app.job.JobInfo.NETWORK_TYPE_ANY)
                    .setRequiresDeviceIdle(false) // For testing purposes
                    .build();
            jobScheduler.schedule(jobInfo);
            Toast.makeText(this, "Cleanup Job Scheduled", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_test_workmanager).setOnClickListener(v -> {
            androidx.work.Constraints constraints = new androidx.work.Constraints.Builder()
                    .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
                    .build();

            androidx.work.OneTimeWorkRequest syncRequest = new androidx.work.OneTimeWorkRequest.Builder(com.example.simplepos.background.modern.SyncWorker.class)
                    .setConstraints(constraints)
                    .build();
            
            androidx.work.WorkManager.getInstance(this).enqueue(syncRequest);
            Toast.makeText(this, "Sync Task Enqueued (Needs Internet)", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_test_service).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, com.example.simplepos.service.SyncService.class);
            startService(intent);
        });

        findViewById(R.id.btn_test_alarm).setOnClickListener(v -> {
            android.app.AlarmManager alarmManager = (android.app.AlarmManager) getSystemService(Context.ALARM_SERVICE);
            android.content.Intent intent = new android.content.Intent(this, com.example.simplepos.service.DailyRemindReceiver.class);
            android.app.PendingIntent pendingIntent = android.app.PendingIntent.getBroadcast(this, 0, intent, android.app.PendingIntent.FLAG_IMMUTABLE);

            // Trigger alarm in 5 seconds
            long triggerTime = System.currentTimeMillis() + 5000;
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            Toast.makeText(this, "Alarm set for 5 seconds...", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        
        int target = prefs.getInt(KEY_DAILY_TARGET, 0);
        if (target > 0) editDailyTarget.setText(String.valueOf(target));
        
        String shopName = prefs.getString(KEY_SHOP_NAME, "");
        editShopName.setText(shopName);
        
        closingHour = prefs.getInt(KEY_CLOSING_TIME_HOUR, -1);
        closingMinute = prefs.getInt(KEY_CLOSING_TIME_MINUTE, -1);
        updateTimeButton();
    }


    private void saveSettings() {
        String targetStr = editDailyTarget.getText().toString();
        String shopName = editShopName.getText().toString();
        
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        editor.putString(KEY_SHOP_NAME, shopName);
        editor.putInt(KEY_CLOSING_TIME_HOUR, closingHour);
        editor.putInt(KEY_CLOSING_TIME_MINUTE, closingMinute);

        if (!targetStr.isEmpty()) {
            try {
                int target = Integer.parseInt(targetStr);
                editor.putInt(KEY_DAILY_TARGET, target);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number for target", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        
        editor.apply();
        
        // Schedule Closing Reminder
        if (closingHour != -1) {
            scheduleClosingReminder(closingHour, closingMinute);
        }

        Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
    
    private void scheduleClosingReminder(int hour, int minute) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) getSystemService(Context.ALARM_SERVICE);
        
        // Android 12+ (API 31+) requires SCHEDULE_EXACT_ALARM permission check
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                Toast.makeText(this, "Please allow exact alarms for reminders", Toast.LENGTH_LONG).show();
                return;
            }
        }

        // Android 13+ (API 33+) requires POST_NOTIFICATIONS permission
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
             if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                 androidx.core.app.ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                 return;
             }
        }
        
        android.content.Intent intent = new android.content.Intent(this, com.example.simplepos.service.ClosingReminderReceiver.class);
        android.app.PendingIntent pendingIntent = android.app.PendingIntent.getBroadcast(this, 1001, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
        
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
        calendar.set(java.util.Calendar.MINUTE, minute);
        calendar.set(java.util.Calendar.SECOND, 0);
        
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1); // Set for tomorrow if time passed
        }
        
        alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
