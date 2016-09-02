package com.di.tang.learncontentprovider.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.di.tang.learncontentprovider.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ContentResolver contentResolver;

    private static final String TAG = "MainActivity";

    private static final String AUTHOR =
            "com.di.tang.sqlproject.contentprovide.SelfContentProvide";

    private static final Uri uri1 = Uri.parse("content://" + AUTHOR + "/frist");
    private static final Uri uri2 = Uri.parse("content://" + AUTHOR + "/second");

    private Uri uri;

    private Button mode1, mode2, insert, updata, delate, queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mode1 = (Button)findViewById(R.id.mode1);
        mode2 = (Button)findViewById(R.id.mode2);
        insert = (Button)findViewById(R.id.insert);
        updata = (Button)findViewById(R.id.updata);
        delate = (Button)findViewById(R.id.deleat);
        queue = (Button)findViewById(R.id.queue);
        contentResolver = getContentResolver();
        //contentResolver.registerContentObserver();

        mode1.setOnClickListener(this);
        mode2.setOnClickListener(this);
        insert.setOnClickListener(this);
        updata.setOnClickListener(this);
        delate.setOnClickListener(this);
        queue.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mode1 :
                mode1.setBackgroundColor(Color.BLACK);
                mode2.setBackgroundColor(Color.parseColor("#FFEC0C0C"));
                uri = Uri.parse("content://" + AUTHOR + "/frist");
                break;
            case R.id.mode2 :
                mode2.setBackgroundColor(Color.BLACK);
                mode1.setBackgroundColor(Color.parseColor("#FFEC0C0C"));
                uri = Uri.parse("content://" + AUTHOR + "/second");
                break;
            case R.id.insert :
                insert();
                break;
            case R.id.queue :
                queue();
                break;
            case R.id.deleat :
                delate();
                break;
            case R.id.updata :
                upData();
                break;
        }
    }

    private void queue(){
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String tableName = cursor.getString(cursor.getColumnIndex("table_name"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));
            Log.d(TAG, "queue: TABLE_NAME" + tableName);
            Log.d(TAG, "queue: NAME" + name);
            Log.d(TAG, "queue: detail" + detail);
            cursor.moveToNext();
        }
        cursor.close();
    }

    private void insert(){
        int rand = new Random().nextInt();
        ContentValues values = new ContentValues();
        values.put("name", "thirdparty" + rand);
        values.put("detail", "detail" + rand);
        Uri uri1 = contentResolver.insert(uri, values);
        Log.d(TAG, "insert: uri" + uri.toString());
    }

    private void delate(){
        int count = contentResolver.delete(uri, "_id = 3", null);
        Log.d(TAG, "delate: count" + count);
    }

    private void upData(){
        ContentValues values = new ContentValues();
        values.put("name", "thirdparty" + 10086);
        values.put("detail", "detail" + 10086);
        int count = contentResolver.update(uri, values, "_id = 4",
                null);
        Log.d(TAG, "upData: count" + count);
    }
}
