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
    private static String LIST_ITEMS = "list_items";
    private TaskAdapter mAdapter = null;
    private int mClickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (savedInstanceState != null) {
            List<Task> values = (List<Task>) savedInstanceState.getSerializable(LIST_ITEMS);
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
        ListView mListView = (ListView) findViewById(R.id.list_view);
        if (mListView != null) {
            mListView.setAdapter(mAdapter);
        }
        Button mButtonNew = (Button) findViewById(R.id.add_new);
        if (mButtonNew != null) {
            mButtonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), TaskActivity.class);
                    startActivityForResult(intent, mREQUEST_ADD);
                }
            });
        }
        if (mListView != null) {
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "Status changed at position: " + position);
                    mAdapter.changeStatus(position);
                }
            });
        }
        if (mListView != null) {
            mListView.setOnItemLongClickListener(new TaskListViewLongClickListener());
        }
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
                if (data.hasExtra(TaskActivity.DELETE_TAG) && TaskActivity.DELETE_TAG.equals(data.getStringExtra(TaskActivity.DELETE_TAG))) {
                    Log.d(TAG, "Delete task on position: " + mClickedPosition);
                    mAdapter.deleteTask(mClickedPosition);
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_ITEMS, (Serializable) mAdapter.getItems());
    }

    /**
     * Class that is used to make item long click listener for Task list view.
     */
    private class TaskListViewLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            mClickedPosition = position;
            Intent intent = new Intent(getBaseContext(), TaskActivity.class);
            Task clickedTask = (Task) mAdapter.getItem(position);
            intent.putExtra("task", clickedTask);
            startActivityForResult(intent, mREQUEST_EDIT, null);
            return true;
        }
    }
}
