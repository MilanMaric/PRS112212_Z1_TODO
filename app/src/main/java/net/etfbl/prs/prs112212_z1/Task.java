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
 *
 **********************************************************************/

package net.etfbl.prs.prs112212_z1;

import java.io.Serializable;

/**
 * This is JavaBean class, actually model class of To Do task
 */
public class Task implements Serializable {
    /**
     * Status of task when it's done
     */
    public static int STATUS_DONE = 1;
    /**
     * Status of task in progress
     */
    public static int STATUS_IN_PROGRES = 0;
    /**
     * Title of the task
     */
    private String title;
    /**
     * Status of the task
     */
    private int status;

    /**
     * This constructor is used to make new task
     *
     * @param title  title of the task
     * @param status status of the task
     */
    public Task(String title, int status) {
        this.title = title;
        this.status = status;
    }

    /**
     * This constructor is used to make new task
     *
     * @param status status of the task
     * @param title  title of the task
     */
    public Task(int status, String title) {
        this.status = status;
        this.title = title;
    }

    public Task() {
    }

    /**
     * This constructor is used to make new task with default status {@link} ({@link Task#STATUS_IN_PROGRES})
     *
     * @param title title of the task
     */
    public Task(String title) {
        this.title = title;
        this.status = STATUS_IN_PROGRES;
    }

    /**
     * This is getter method for {@link Task#title}
     *
     * @return reference to {@link String} object
     */
    public String getTitle() {
        return title;
    }


    /**
     * This is setter method for
     *
     * @param title reference to {@link String} object
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This is getter method for {@link Task#status}
     *
     * @return int value of the status ({@link Task#STATUS_DONE} or {@link Task#STATUS_IN_PROGRES})
     */
    public int getStatus() {
        return status;
    }

    /**
     * This is setter method for {@link Task#status}
     *
     * @param status value of the status {@link Task#STATUS_DONE} or {@link Task#STATUS_IN_PROGRES})
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
