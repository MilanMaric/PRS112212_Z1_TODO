package net.etfbl.prs.prs112212_z1;

import java.io.Serializable;

/**
 * Created by milan on 31.3.2016.
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
