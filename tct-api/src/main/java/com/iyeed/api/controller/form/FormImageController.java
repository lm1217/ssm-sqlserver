package com.iyeed.api.controller.form;

import com.iyeed.api.controller.BaseController;
import com.iyeed.api.controller.common.emuns.RespCode;
import com.iyeed.api.controller.common.model.AjaxResponse;
import com.iyeed.core.utils.AliyunOSSUtil;
import com.iyeed.core.utils.Identities;
import com.iyeed.service.form.IBdFormImageService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/15 16:47
 */
@Controller
@RequestMapping(value = "api/form/image")
public class FormImageController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FormImageController.class);

    @Resource
    private IBdFormImageService bdFormImageService;

    @RequestMapping(value = "imageUpload.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse imageUpload(@RequestParam MultipartFile file, @RequestParam String type) {
        if (file == null && StringUtils.isNotBlank(file.getOriginalFilename())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        if (StringUtils.isBlank(type)) {
            type = "common";
        }

        // 截取文件的扩展名（如.jpg）
        String oriName = file.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf("."));

        String path = "image/"+type+"/" + Identities.uuid2() + extName;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            AliyunOSSUtil.buildClient();
            AliyunOSSUtil.putObject(path, inputStream);
            AliyunOSSUtil.shutdown();
        } catch (Exception e) {
            return AjaxResponse.failure(RespCode.FAILED, e.getMessage());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        log.debug("上传文件Path: " + path);
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("url", path);
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "fileUpload.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse fileUpload(@RequestParam MultipartFile file, @RequestParam String type, HttpServletRequest request) {
        if (file == null && StringUtils.isNotBlank(file.getOriginalFilename())) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        // 获取物理路径
        String targetDirectory = request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath().replace("/", "");
        String[] strArray = targetDirectory.split(contextPath);
        targetDirectory = strArray[0] + "/tct-tp";
        if (StringUtils.isBlank(type)) {
            type = "common";
        }

        // 截取文件的扩展名（如.jpg）
        String oriName = file.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf("."));

        // 保存文件位置
        String path = "/uploads/image/" + type + "/" + Identities.uuid2() + extName;

        targetDirectory = targetDirectory + path;
        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(targetDirectory);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        try {
            log.info("上传文件开始: " + System.currentTimeMillis());
            file.transferTo(new File(targetDirectory));
            log.info("上传文件结束: " + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        log.debug("上传文件Path: " + path);
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("url", path);
        return AjaxResponse.success(dataMap);
    }

    @RequestMapping(value = "base64Upload.json", method = { RequestMethod.POST })
    @ResponseBody
    public AjaxResponse base64Upload(@RequestParam String pic) {
        if (StringUtils.isBlank(pic)) {
            return AjaxResponse.failure(RespCode.ILLEGAL_ARGUMENT);
        }

        // 保存文件位置
        String path = "image/common/" + Identities.uuid2() + ".jpg";

        // 判断路径是否存在，如果不存在就创建一个
        File filePath = new File(path);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        // 通过base64来转化图片
        pic = pic.replaceAll("data:image/jpeg;base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] imageByte = null;
        try {
            imageByte = decoder.decodeBuffer(pic);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(filePath);
            out.write(imageByte);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("上传文件Path: " + path);
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("url", path);
        return AjaxResponse.success(dataMap);
    }
}
