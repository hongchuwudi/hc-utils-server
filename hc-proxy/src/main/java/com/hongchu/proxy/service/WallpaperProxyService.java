package com.hongchu.proxy.service;

import com.hongchu.pojo.proxy.wallpaper.JinShanWallpaper;

import java.util.List;

public interface WallpaperProxyService {
    /**
     * 获取金山毒霸壁纸
     * @param msg    关键词
     * @param type  视频尺寸类型(pc/安卓)
     * @param page  页码
     * @param limit 每页数量
     * @return 壁纸列表
     */
    List<JinShanWallpaper> getJinShanWallpaper(String msg, String type, Integer page, Integer limit);
}
