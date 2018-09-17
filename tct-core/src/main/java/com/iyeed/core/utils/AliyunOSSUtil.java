package com.iyeed.core.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.iyeed.core.ConstantsEJS;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云OSS工具类
 * @author ZhangJunhua
 * @version 0.1 2017-03-18 17:35
 */
public class AliyunOSSUtil {
    private static final Logger log = LoggerFactory.getLogger(AliyunOSSUtil.class);

    private static final String bucket = ConstantsEJS.ALIYUN_OSS_APK_BUCKET;

    private static OSSClient client;

    public static void buildClient() {
        String endpoint = ConstantsEJS.ALIYUN_OSS_ENDPOINT;
        String accessKeyId = ConstantsEJS.ALIYUN_OSS_ACCESS_KEY_ID;
        String accessKeySecret = ConstantsEJS.ALIYUN_OSS_ACCESS_KEY_SECRET;
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setRequestTimeoutEnabled(true);
        clientConfiguration.setRequestTimeout(300 * 1000);
        client =  new OSSClient(endpoint, credentialsProvider, clientConfiguration);
    }

    /**
     * 上传文件
     */
    public static void putObject(String path, InputStream inputStream) {
        log.debug("文件开始上传");
        client.putObject(bucket, path, inputStream);
    }

    /**
     * 复制文件
     */
    public static void copyObject(String sourcePath, String targetPath) {
        OSSObject obj = client.getObject(bucket, sourcePath);
        if(obj != null){
            InputStream is = obj.getObjectContent();
            try {
                client.putObject(bucket, targetPath, is);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
    }

    /**
     * 删除文件
     */
    public static void deleteObject(String path) {
        client.deleteObject(bucket, path);
    }

    public static void shutdown() {
        log.debug("文件上传完毕,关闭上传");
        client.shutdown();
    }

    /**
     * 列出目录下的文件
     */
    public static List<String> listObjects(String prefix){
        if(StringUtils.isBlank(prefix)){
            return null;
        }
        ObjectListing objectListing = client.listObjects(bucket, prefix);
        List<String> list = new ArrayList<>(objectListing.getObjectSummaries().size());
        for (OSSObjectSummary ossObjectSummary : objectListing.getObjectSummaries()) {
            if(prefix.equals(ossObjectSummary.getKey())){
                continue;
            }
            list.add(ossObjectSummary.getKey());
        }
        return list;
    }
}
