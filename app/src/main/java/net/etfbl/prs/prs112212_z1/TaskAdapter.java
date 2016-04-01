/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p>
 * All Rights Reserved
 * <p>
 * \file TaskActivity.java
 * \brief
 * This file contains a source code for class TaskAdapter
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
 **********************************************************************/
package net.etfbl.prs.prs112212_z1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to control {@link android.widget.ListView} items, that are {@link Task}s actually.
 *
 * @see Task
 * @see android.widget.ListView
 */
public class TaskAdapter extends BaseAdapter {
    private static String TAG = "TaskAdapter";
    private Context mContext;
    private List<Task> mList;

    /**
     * Constructor that makes empty instance of {@link TaskAdapter}.
     *
     * @param mContext {@link Context} of {@link View} in which {@link TaskAdapter} is used
     */
    public TaskAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    /**
     * Contructor that makes instance of {@link TaskAdapter} with prepared {@link ArrayList}.
     *
     * @param mContext {@link Context} of {@link View} in which {@link TaskAdapter} is used
     * @param mList    {@link ArrayList} containing {@link Task}s .
     */
    public TaskAdapter(Context mContext, List<Task> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > mList.size()) {

            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_layout, null);
        }
        Task task = (Task) getItem(position);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        if (task.getStatus() == Task.STATUS_IN_PROGRES) {
            image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ico_in_progress));
        } else {
            image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ico_done));
        }
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(task.getTitle());
        return view;
    }

    /**
     * @param task - reference to Task object that you want to add to ListView.
     * @brief This function adds task to ListView, and saves that task to external storage.
     * @see android.widget.ListView
     * @see net.etfbl.prs.prs112212_z1.Task
     * @see TaskAdapter#saveItems()
     */
    public void addTask(Task task) {
        mList.add(task);
        notifyDataSetChanged();
        saveItems();
    }

    /**
     * This method deletes task  from ListView and external storage
     *
     * @param position position of task in {@link android.widget.ListView}
     * @return true if task has been successfully deleted, false if not
     * @see net.etfbl.prs.prs112212_z1.Task
     * @see TaskAdapter#saveItems()
     */
    public boolean deleteTask(int position) {
        if (position < mList.size()) {
            Log.d(TAG, "Delete task");
            mList.remove(position);
            notifyDataSetChanged();
            saveItems();
            return true;

        }
        return false;
    }

    /**
     * This method is used to update task at some specific position.
     *
     * @param position position of task you want to update in ListView
     * @param task     reference to new task that you want to delete
     * @return this method returns true if task is successfully updated, and false if error occured
     */
    public boolean updateTask(int position, Task task) {
        Log.d(TAG, "Update task");
        if (position < mList.size()) {
            Task old = (Task) getItem(position);
            old.setStatus(task.getStatus());
            old.setTitle(task.getTitle());
            notifyDataSetChanged();
            saveItems();
            return true;
        }
        return false;
    }

    /**
     * This method you should invoke just to swap status of task. If task has {@link Task#status} valie {@link Task#STATUS_IN_PROGRES} then it is
     * changed to {@link Task#STATUS_DONE}. If task has {@link Task#status} value {@link Task#STATUS_DONE} then it is changed to {@link Task#STATUS_IN_PROGRES}.
     *
     * @param position position of task in {@link android.widget.ListView}
     * @see Task
     * @see android.widget.ListView
     * @see Task#status
     */
    public void changeStatus(int position) {
        Task task = (Task) getItem(position);
        if (task != null) {

            if (task.getStatus() == Task.STATUS_IN_PROGRES) {
                task.setStatus(Task.STATUS_DONE);
            } else {
                task.setStatus(Task.STATUS_IN_PROGRES);
            }
            notifyDataSetChanged();
            saveItems();
        }
    }

    /**
     * This method returns {@link ArrayList} of Tasks.
     *
     * @return {@link ArrayList} that is returned
     */
    public List<Task> getItems() {
        return mList;
    }

    /**
     * This method is used to save {@link Task} to external storage.
     *
     * @see ObjectOutputStream
     * @see java.io.Serializable
     * @see Task
     */
    public void saveItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = mContext.getDir("todo", Context.MODE_PRIVATE);
                File out = new File(dir, "list.ser");
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(
                            new FileOutputStream(out));
                    oos.writeObject(getItems());
                    oos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * This method is used to read {@link ArrayList} of {@link Task}s form external storage.
     *
     * @see ObjectInputStream
     * @see java.io.Serializable
     * @see Task
     */
    public void loadItems() {
        try {
            File dir = mContext.getDir("todo", Context.MODE_PRIVATE);
            File in = new File(dir, "list.ser");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                    in));
            if (in != null && ois != null) {
                ArrayList<Task> readObject2 = (ArrayList<Task>) ois
                        .readObject();
                ArrayList<Task> readObject = readObject2;
                if (readObject instanceof List<?>)
                    mList = readObject;
                else
                    mList = new ArrayList<Task>();
                ois.close();
            } else
                mList = new ArrayList<Task>();
        } catch (Exception e) {
            mList = new ArrayList<Task>();
        }
        notifyDataSetChanged();
    }

}
