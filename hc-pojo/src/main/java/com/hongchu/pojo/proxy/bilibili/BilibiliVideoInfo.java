package com.hongchu.pojo.proxy.bilibili;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * B站视频信息响应实体
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BilibiliVideoInfo {
    private Integer code;   // 状态码
    private String message; // 状态信息
    private Integer ttl;    // 响应时间
    private VideoData data; // 视频数据

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoData {
        private String          bvid;                        //          视频ID
        private Long            aid;                         //          视频ID
        private Integer         videos;                      //          视频数量
        private Integer         tid;                         //          分类ID
        private Integer         tid_v2;                      //          新版分类ID
        private String          tname;                       //          分类名称
        private String          tname_v2;                    //          新版分类名称
        private Integer         copyright;                   //          版权信息 1-自制,2-转载
        private String          title;                       //          标题
        private String          pic;                         //          封面
        private String          desc;                        //          描述
        private Long            pubdate;                     //          发布时间
        private Long            ctime;                       //          创建时间
        private Integer         duration;                    //          时长
        private Integer         state;                       //          视频状态
        private Integer         attribute;                   //          属性位
        private String          dynamic;                     //          动态描述
        private Long            cid;                         //          视频CID
        private VideoDimension  dimension;                   //          视频分辨率
        private VideoOwner      owner;                       //          UP信息
        private VideoStat       stat;                        //          统计信息
        private VideoRights     rights;                      //          权限信息
        private ArgueInfo       argue_info;                  //          争议信息
        private List<VideoPage> pages;                       //          页面信息
        private Subtitle        subtitle;                    //          字幕信息
        private String          label;                       //          标签
        private String          uri;                         //          视频URI
        private Object          premiere;                    //          首播信息
        private Integer         teenage_mode;                //          青少年模式

        private Boolean         is_chargeable_season;        //          是否收费赛季
        private Boolean         is_story;                    //          是否故事模式
        private Boolean         is_upower_exclusive;         //          是否UP主专属
        private Boolean         is_upower_play;              //          是否UP主播放
        private Boolean         is_upower_preview;           //          是否UP主预览
        private Integer         enable_vt;                   //          是否启用VT
        private String          vt_display;                  //          VT显示
        private Boolean         no_cache;                    //          是否无缓存
        private List<DescV2>    desc_v2;                     //          V2描述
        private Boolean         is_season_display;           //          是否赛季显示
        private UserGarb        user_garb;                   //          用户装扮
        private HonorReply      honor_reply;                 //          荣誉回复
        private String          like_icon;                   //          点赞图标
        private Boolean         need_jump_bv;                //          是否需要跳转BV
        private Boolean         disable_show_up_info;        //          禁用显示UP信息
        private Integer         is_story_play;               //          是否故事播放
        private Boolean         is_view_self;                //          是否查看自己
        private Integer         is_stein_gate;               //          是否斯坦门
        private Integer         is_360;                      //          是否360视频

        private Boolean         is_upower_exclusive_with_qa; //          是否UP主专属问答

        private Map<String,Object>               additional; //        用于存储其他未知字段

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoOwner {
        private Long   mid;  // UP主ID
        private String name; // UP主名称
        private String face; // UP主头像

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoStat {
        private Long    aid;        // 视频ID
        private Long    view;       // 播放数
        private Long    danmaku;    // 弹幕数
        private Long    reply;      // 评论数
        private Long    favorite;   // 收藏数
        private Long    coin;       // 投币数
        private Long    share;      // 分享数
        private Long    like;       // 点赞数
        private Long    dislike;    // 点踩数
        private Integer now_rank;   // 当前排名
        private Integer his_rank;   // 历史排名
        private String  evaluation; // 评价
        private Integer vt;         // VT值
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoRights {
        private Integer bp;              // BP
        private Integer elec;            // 充电
        private Integer download;        // 下载
        private Integer movie;           // 电影
        private Integer pay;             // 付费
        private Integer hd5;             // HD5
        private Integer no_reprint;      // 禁止转载
        private Integer autoplay;        // 自动播放
        private Integer ugc_pay;         // UGC付费
        private Integer is_cooperation;  // 是否合作
        private Integer ugc_pay_preview; // UGC付费预览
        private Integer no_background;   // 无背景
        private Integer clean_mode;      // 清洁模式
        private Integer is_stein_gate;   // 是否斯坦门
        private Integer is_360;          // 是否360
        private Integer no_share;        // 禁止分享
        private Integer arc_pay;         // 电弧付费
        private Integer free_watch;      // 免费观看
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoPage {
        private Long           cid;         // 页面ID
        private Integer        page;        // 页码
        private String         from;        // 来源
        private String         part;        // 页面标题
        private Integer        duration;    // 时长
        private String         vid;         // 视频ID
        private String         weblink;     // 网页链接
        private VideoDimension dimension;   // 分辨率
        private String         first_frame; // 第一帧
        private Long           ctime;       // 创建时间
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoDimension {
        private Integer width;  // 宽度
        private Integer height; // 高度
        private Integer rotate; // 旋转
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArgueInfo {
        private String  argue_msg;  // 争议信息
        private Integer argue_type; // 争议类型
        private String  argue_link; // 争议链接
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subtitle {
        private Boolean            allow_submit; // 是否允许提交
        private List<SubtitleItem> list;         // 字幕列表
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubtitleItem {
        private String id;           // 字幕ID
        private String lan;          // 语言
        private String lan_doc;      // 语言文档
        private String subtitle_url; // 字幕URL
        private String type;         // 类型
        // 可以添加其他字幕相关字段
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DescV2 {
        private String  raw_text; // 原始文本
        private Integer type;     // 类型
        private Long    biz_id;   // 业务ID
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserGarb {
        private String url_image_ani_cut; // 动画裁剪图片URL
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HonorReply {
        // 荣誉回复信息，根据实际情况补充字段
        private Map<String, Object> additional;
    }
}