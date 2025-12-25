
// VideoItem.java - 视频项数据结构（统一模型）
package com.hongchu.pojo.proxy.video;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;

/**
 * 视频项统一模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoItem implements Serializable {
    // ========== 基础标识 ==========
    private Object  vodId;        // 视频ID（必需）- 支持number/string
    private String  vodName;      // 视频名称（必需）
    private String  vodEn;        // 英文名/拼音

    // ========== 分类信息 ==========
    private Object  typeId;       // 分类ID（必需）
    private Object  typeId1;      // 一级分类ID（可选）
    private Object  typeIdOne;    // 别名（兼容字段）
    private String  typeName;     // 分类名称（必需）
    
    // ========== 基本信息 ==========
    private String vodSub;
    private String vodTag;
    private String vodClass;
    private String vodLetter;
    private String vodColor;
    private String vodRemarks;
    
    // ========== 媒体资源 ==========
    private String vodPic;
    private String vodPicThumb;
    private String vodPicSlide;
    private String vodPicScreenshot;
    
    // ========== 人员信息 ==========
    private String vodActor;
    private String vodDirector;
    private String vodWriter;
    private String vodBehind;
    
    // ========== 地区/语言/年份 ==========
    private String vodArea;
    private String vodLang;
    private String vodYear;
    private String vodVersion;
    
    // ========== 内容信息 ==========
    private String vodContent;
    private String vodBlurb;
    private String vodState;
    
    // ========== 播放信息 ==========
    private String vodPlayFrom;
    private String vodPlayServer;
    private String vodPlayNote;
    private String vodPlayUrl;
    
    // ========== 下载信息 ==========
    private String vodDownFrom;
    private String vodDownServer;
    private String vodDownNote;
    private String vodDownUrl;
    
    // ========== 密码保护 ==========
    private String vodPwd;
    private String vodPwdUrl;
    private String vodPwdPlay;
    private String vodPwdPlayUrl;
    private String vodPwdDown;
    private String vodPwdDownUrl;
    
    // ========== 统计信息 ==========
    private Integer vodHits;
    private Integer vodHitsDay;
    private Integer vodHitsWeek;
    private Integer vodHitsMonth;
    private Integer vodUp;
    private Integer vodDown;
    private String vodScore;
    private Integer vodScoreAll;
    private Integer vodScoreNum;
    
    // ========== 时间信息 ==========
    private String vodTime;
    private Long vodTimeAdd;
    private Long vodTimeHits;
    private Long vodTimeMake;
    private String vodPubdate;
    
    // ========== 其他信息 ==========
    private Integer vodTotal;
    private String vodSerial;
    private String vodTv;
    private String vodWeekday;
    private String vodDuration;
    private Integer vodIsend;
    private Integer vodStatus;
    private Integer vodLock;
    private Integer vodLevel;
    private Integer vodCopyright;
    private Integer vodTrysee;
    
    // ========== 豆瓣信息 ==========
    private Integer vodDoubanId;
    private String vodDoubanScore;
    
    // ========== 关联信息 ==========
    private String vodRelVod;
    private String vodRelArt;
    private String vodReurl;
    private String vodJumpurl;
    private String VodJumpurl;  // 兼容字段
    
    // ========== 积分系统 ==========
    private Integer vodPoints;
    private Integer vodPointsPlay;
    private Integer vodPointsDown;
    
    // ========== 模板信息 ==========
    private String vodTpl;
    private String vodTplPlay;
    private String vodTplDown;
    
    // ========== 剧情信息 ==========
    private Integer vodPlot;
    private String vodPlotName;
    private String vodPlotDetail;
    
    // ========== 其他字段 ==========
    private String vodAuthor;
}
