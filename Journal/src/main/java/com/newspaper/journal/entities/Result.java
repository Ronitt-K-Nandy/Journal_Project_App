package com.newspaper.journal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Result{

    public String article_id;
    public String link;
    public String title;
    public String description;
    public String content;
    public ArrayList<String> keywords;
    public ArrayList<String> creator;
    public String language;
    public ArrayList<String> country;
    public ArrayList<String> category;
    public String datatype;
    public String pubDate;
    public String pubDateTZ;
    public String fetched_at;
    public String image_url;
    public Object video_url;
    public String source_id;
    public String source_name;
    public int source_priority;
    public String source_url;
    public String source_icon;
    public String sentiment;
    public String sentiment_stats;
    public String ai_tag;
    public String ai_region;
    public String ai_org;
    public String ai_summary;
    public boolean duplicate;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<String> getCreator() {
        return creator;
    }

    public void setCreator(ArrayList<String> creator) {
        this.creator = creator;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<String> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<String> country) {
        this.country = country;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDateTZ() {
        return pubDateTZ;
    }

    public void setPubDateTZ(String pubDateTZ) {
        this.pubDateTZ = pubDateTZ;
    }

    public String getFetched_at() {
        return fetched_at;
    }

    public void setFetched_at(String fetched_at) {
        this.fetched_at = fetched_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Object getVideo_url() {
        return video_url;
    }

    public void setVideo_url(Object video_url) {
        this.video_url = video_url;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public int getSource_priority() {
        return source_priority;
    }

    public void setSource_priority(int source_priority) {
        this.source_priority = source_priority;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getSource_icon() {
        return source_icon;
    }

    public void setSource_icon(String source_icon) {
        this.source_icon = source_icon;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getSentiment_stats() {
        return sentiment_stats;
    }

    public void setSentiment_stats(String sentiment_stats) {
        this.sentiment_stats = sentiment_stats;
    }

    public String getAi_tag() {
        return ai_tag;
    }

    public void setAi_tag(String ai_tag) {
        this.ai_tag = ai_tag;
    }

    public String getAi_region() {
        return ai_region;
    }

    public void setAi_region(String ai_region) {
        this.ai_region = ai_region;
    }

    public String getAi_org() {
        return ai_org;
    }

    public void setAi_org(String ai_org) {
        this.ai_org = ai_org;
    }

    public String getAi_summary() {
        return ai_summary;
    }

    public void setAi_summary(String ai_summary) {
        this.ai_summary = ai_summary;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }
}

