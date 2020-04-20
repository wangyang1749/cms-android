package com.example.androidstudy.pojo;

import java.util.List;
import java.util.Set;

public class ArticleDetailVO extends ArticleDto{
    private String originalContent;
    private String formatContent;
//    private BaseCategory category;
    private Set<Integer> tagIds;
//    private List<Tags> tags;
    //更新channel的文章视图名称,将html的更新从service转移controller
    private Boolean isUpdateChannelFirstName;

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }

    public Set<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public Boolean getUpdateChannelFirstName() {
        return isUpdateChannelFirstName;
    }

    public void setUpdateChannelFirstName(Boolean updateChannelFirstName) {
        isUpdateChannelFirstName = updateChannelFirstName;
    }
}
