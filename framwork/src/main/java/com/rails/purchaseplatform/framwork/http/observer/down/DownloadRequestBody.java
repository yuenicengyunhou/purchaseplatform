package com.rails.purchaseplatform.framwork.http.observer.down;


import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Okhttp3 读取数据信息
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2018/2/7.
 */

public class DownloadRequestBody extends ResponseBody {

    private ResponseBody responseBody;
    private DownloadProgressListener downloadListener;
    private BufferedSource bufferedSource;

    public DownloadRequestBody(ResponseBody responseBody, DownloadProgressListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null)
            bufferedSource = Okio.buffer(source(responseBody.source()));
        return bufferedSource;
    }


    private Source source(Source source) {
        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                if (downloadListener != null && bytesRead != -1) {
                    downloadListener.download(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }
                return totalBytesRead;
            }
        };
    }


    /**
     * 成功回调处理
     */
    public interface DownloadProgressListener {
        /**
         * 下载进度
         *
         * @param read
         * @param count
         * @param done
         */
        void download(long read, long count, boolean done);
    }
}
