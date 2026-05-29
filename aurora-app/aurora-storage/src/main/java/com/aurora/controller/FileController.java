package com.aurora.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aurora.common.Result;
import com.aurora.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@Tag(name = "文件管理")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(description = "上传文件")
    @SaCheckPermission("sys:file:upload")
    public Result<String> upload(MultipartFile file) {
        return Result.success(fileService.upload(file));
    }

    @GetMapping("/delete")
    @Operation(description = "删除文件")
    @SaCheckPermission("sys:file:delete")
    public Result<Boolean> delete(String url) {
        return Result.success(fileService.delete(url));
    }
}
