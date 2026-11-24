import java.sql.Date;

public class Task {
    private int id;
    private String title;
    private String status;
    private String priority;
    private Date deadline;
    private boolean rewardClaimed;

    public Task(int id, String title, String status, String priority, Date deadline, boolean rewardClaimed) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.rewardClaimed = rewardClaimed;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public Date getDeadline() { return deadline; }
    public boolean isRewardClaimed() { return rewardClaimed; }
}
