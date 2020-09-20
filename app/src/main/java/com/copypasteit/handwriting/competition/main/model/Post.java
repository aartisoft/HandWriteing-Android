package com.copypasteit.handwriting.competition.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("roles")
    @Expose
    private String roles;
    @SerializedName("prize_list")
    @Expose
    private String prizeList;
    @SerializedName("writeing_txt")
    @Expose
    private String writeingTxt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(String prizeList) {
        this.prizeList = prizeList;
    }

    public String getWriteingTxt() {
        return writeingTxt;
    }

    public void setWriteingTxt(String writeingTxt) {
        this.writeingTxt = writeingTxt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", examName='" + examName + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", roles='" + roles + '\'' +
                ", prizeList='" + prizeList + '\'' +
                ", writeingTxt='" + writeingTxt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
