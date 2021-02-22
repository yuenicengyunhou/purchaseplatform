package com.rails.purchaseplatform.framwork.http.observer.down;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 上传文件service
 * author:wangchao
 * date:2018/11/2
 */
public interface UploadService {


    /**
     * 下载文件
     *
     * @return
     */
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);

}
