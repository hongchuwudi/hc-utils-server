package com.hongchu.pojo.proxy.bilibili;

import lombok.Data;
import java.util.List;

/**
 * B站视频流地址响应实体
 */
@Data
public class BilibiliPlayUrlInfo {
    private Integer code;
    private String message;
    private Integer ttl;
    private PlayUrlData data;
    
    @Data
    public static class PlayUrlData {
        private String              from;
        private String              result;
        private String              message;
        private Integer             quality;
        private String              format;
        private Integer             timelength;
        private String              accept_format;
        private List<String>        accept_description;
        private List<Integer>       accept_quality;
        private Integer             video_codecid;
        private String              seek_param;
        private String              seek_type;
        private List<VideoUrl>      durl;
        private DashInfo            dash;
        private List<SupportFormat> support_formats;
        private Integer             last_play_time;
        private Integer             last_play_cid;
        
        @Data
        public static class VideoUrl {
            private Integer order;
            private Integer length;
            private Long size;
            private String ahead;
            private String vhead;
            private String url;
            private List<String> backup_url;
        }
        
        @Data
        public static class DashInfo {
            private Integer duration;
            private Double minBufferTime;
            private Double min_buffer_time;
            private List<VideoStream> video;
            private List<AudioStream> audio;
            private DolbyInfo dolby;
            private FlacInfo flac;
            
            @Data
            public static class VideoStream {
                private Integer id;
                private String baseUrl;
                private String base_url;
                private List<String> backupUrl;
                private List<String> backup_url;
                private Long bandwidth;
                private String mimeType;
                private String mime_type;
                private String codecs;
                private Integer width;
                private Integer height;
                private String frameRate;
                private String frame_rate;
                private String sar;
                private Integer startWithSap;
                private Integer start_with_sap;
                private SegmentBase SegmentBase;
                private Integer codecid;
                
                @Data
                public static class SegmentBase {
                    private String initialization;
                    private String index_range;
                }
            }
            
            @Data
            public static class AudioStream {
                private Integer id;
                private String baseUrl;
                private String base_url;
                private List<String> backupUrl;
                private List<String> backup_url;
                private Long bandwidth;
                private String mimeType;
                private String mime_type;
                private String codecs;
            }
            
            @Data
            public static class DolbyInfo {
                private Integer type;
                private List<AudioStream> audio;
            }
            
            @Data
            public static class FlacInfo {
                private Boolean display;
                private AudioStream audio;
            }
        }
        
        @Data
        public static class SupportFormat {
            private Integer quality;
            private String format;
            private String new_description;
            private String display_desc;
            private String superscript;
            private List<String> codecs;
        }
    }
}