/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p>
 * All Rights Reserved
 * <p>
 * \file TaskActivity.java
 * \brief
 * This file contains a source code for class TaskActivity
 * <p>
 * Created on 31.03.2016
 *
 * @Author Milan Maric
 * <p>
 * \notes
 * <p>
 * <p>
 * \history
 * <p>
 * 01.04.2016 Bug fixed for empty input string.
 * 31.03.2016 Implemented activity with two layouts.
 * 31.03.2016 Implemented onClickListeners.
 * 31.03.2016 Made initial class.
 **********************************************************************/
package net.etfbl.prs.prs112212_z1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is view class that is used for input data about new {@link Task} and for changing data of {@link Task}
 */
public class TaskActivity extends AppCompatActivity {
    /**
     * Task that is changing or inserting in this class
     */
    private Task mTask = null;
    /**
     * Confirm button
     */
    private Button mButtonOK;
    /**
     * Delete button (only if this {@link Activity} is using to change task)
     */
    private Button mButtonDelete;
    /**
     * TextView that is used
     */
    private TextView mTextViewName;
    /**
     * Reference to active {@link Activity} (this)
     */
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mTask = (Task) intent.getSerializableExtra("task");
        mActivity = this;
        if (mTask != null) {
            setContentView(R.layout.activity_task);
        } else {
            setContentView(R.layout.activity_new_task);
        }
        mButtonOK = (Button) findViewById(R.id.ok);
        mTextViewName = (TextView) findViewById(R.id.name);
        if (mTask != null) {
            if (mTextViewName != null)
                mTextViewName.setText(mTask.getTitle());
            mButtonDelete = (Button) findViewById(R.id.delete);
            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                    dialog.setTitle(R.string.confirm_delete_title);
                    dialog.setMessage(R.string.confirm_delete_text);
                    dialog.setCancelable(true);
                    dialog.setPositiveButton(R.string.confirm_delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent result = new Intent();
                            result.putExtra("DELETE", "DELETE");
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        }
                    });
                    dialog.setNegativeButton(R.string.confirm_delete_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextViewName.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.empty_title, Toast.LENGTH_SHORT).show();
                } else {
                    if (mTask == null) {
                        mTask = new Task(Task.STATUS_IN_PROGRES, mTextViewName.getText().toString());
                    } else {
                        mTask.setTitle(mTextViewName.getText().toString());
                    }
                    Intent result = new Intent();
                    result.putExtra("result", mTask);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });

    }


}
