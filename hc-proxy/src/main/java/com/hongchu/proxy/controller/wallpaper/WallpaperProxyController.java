package com.hongchu.proxy.controller.wallpaper;

import com.hongchu.common.result.Result;
import com.hongchu.pojo.proxy.wallpaper.JinShanWallpaper;
import com.hongchu.proxy.service.WallpaperProxyService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 壁纸代理
 * @author HongChu-xc
 * @deprecated  用于代理各种壁纸的来源
 */
@Slf4j
@RestController
@RequestMapping("/proxy/paper")
public class WallpaperProxyController {
    private final WallpaperProxyService wallpaperProxyService;

    public WallpaperProxyController(WallpaperProxyService wallpaperProxyService) {
        this.wallpaperProxyService = wallpaperProxyService;
    }

    /**
     * 获取金山毒霸壁纸
     * @param msg    关键词
     * @param type  视频尺寸类型(pc/安卓)
     * @param page  页码
     * @param limit 每页数量
     * @return 壁纸列表
     */
    @GetMapping("/jinShan")
    public Result<List<JinShanWallpaper>> getJinShanWallpaper(
            @RequestParam @NotBlank String msg,
            @RequestParam(defaultValue = "pc") @Pattern(regexp = "pc|android") String type,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "30") @Min(1) @Max(60) Integer limit) {
            log.info("请求金山毒霸壁纸-关键词:{},平台类型:{},页:{},页数,{}", msg, type, page, limit);
            List<JinShanWallpaper> result = wallpaperProxyService.getJinShanWallpaper(msg, type, page, limit);
            return Result.success(result);
    }
}
