package com.rails.purchaseplatform.framwork.http.observer.upload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 重写okHttp RequestBody
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2018/1/22.
 */

public class UploadRequestBody extends RequestBody {


    private RequestBody requestBody;
    private UploadObserver<ResponseBody> uploadObserver;


    public UploadRequestBody(RequestBody requestBody, UploadObserver<ResponseBody> uploadObserver) {
        this.requestBody = requestBody;
        this.uploadObserver = uploadObserver;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }


    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }


        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            if (uploadObserver != null) {
                uploadObserver.onProgressChange(bytesWritten, contentLength());
            }

        }
    }
}
