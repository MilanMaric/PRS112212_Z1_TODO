/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p/>
 * All Rights Reserved
 * <p/>
 * \file TaskActivity.java
 * \brief
 * This file contains a source code for main activity - activity that would be launced when application starts
 * <p/>
 * Created on 31.03.2016
 *
 * @Author Milan Maric
 * <p/>
 * \notes
 * <p/>
 * <p/>
 * \history
 * <p/>
 **********************************************************************/
package net.etfbl.prs.prs112212_z1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static int mREQUEST_ADD = 987;
    private static int mREQUEST_EDIT = 988;
    private static String TAG = "MainActivity";
    private TaskAdapter mAdapter = null;
    private ListView mListView;
    private Button mButtonNew;
    private int mClickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (savedInstanceState != null) {
            List<Task> values = (List<Task>) savedInstanceState.getSerializable("list_items");
            if (values != null) {
                mAdapter = new TaskAdapter(this, values);
            } else {
                mAdapter = new TaskAdapter(this);
                mAdapter.loadItems();
            }
        } else {
            mAdapter = new TaskAdapter(this);
            mAdapter.loadItems();
        }
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);
        mButtonNew = (Button) findViewById(R.id.add_new);
        mButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TaskActivity.class);
                startActivityForResult(intent, mREQUEST_ADD);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Status changed at position: " + position);
                mAdapter.changeStatus(position);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mClickedPosition = position;
                Intent intent = new Intent(getBaseContext(), TaskActivity.class);
                Task clickedTask = (Task) mAdapter.getItem(position);
                intent.putExtra("task", clickedTask);
                startActivityForResult(intent, mREQUEST_EDIT, null);
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult rqCode:" + requestCode + " resultCode:" + resultCode);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("result")) {
                Task task = (Task) data.getSerializableExtra("result");
                if (requestCode == mREQUEST_ADD) {
                    mAdapter.addTask(task);
                }
                if (requestCode == mREQUEST_EDIT) {
                    mAdapter.updateTask(mClickedPosition, task);
                }
            } else {
                if (data.hasExtra("DELETE") && "DELETE".equals(data.getStringExtra("DELETE"))) {
                    Log.d(TAG, "Delete task on position: " + mClickedPosition);
                    mAdapter.deleteTask(mClickedPosition);
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list_items", (Serializable) mAdapter.getItems());
    }
}
