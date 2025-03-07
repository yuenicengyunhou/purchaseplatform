package com.rails.purchaseplatform.order.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.pop.QuickJumpPop;
import com.rails.purchaseplatform.common.utils.StatusBarCompat;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.order_details.DeliveredAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityDeliveredBinding;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

@Route(path = ConRoute.ORDER.ORDER_DELIVER)
public class DeliveredActivity extends BaseErrorActivity<ActivityDeliveredBinding> implements OrderContract.OrderView {

    private String orderNo;
    private DeliveredAdapter adapter;
    private OrderPresenterImpl presenter;

    //下载
    private int currentIndex = -1;//默认为-1,为-1时没有下载任务，不为-1时等待下载完成
//    private final List<BaseDownloadTask> tasks = new ArrayList<>();
//    private HashMap<Integer, String> urlMap = new HashMap<>();
//    private FileDownloadQueueSet queueSet;

    //刷新下载状态
    private final UIHandler uiHandler = new UIHandler(this);
    private final int COMPLETE = 2;
    private final int DOWNLOADING = 1;

    //记录下载状态
    private final int STATE_DEFAULT = -1;
    private int mDownloadState = STATE_DEFAULT;
    private QuickJumpPop mQuickJumpPop;

    @SuppressLint("HandlerLeak")
    private class UIHandler extends Handler {
        private final WeakReference<DeliveredActivity> mActivity;

        @SuppressWarnings("deprecation")
        public UIHandler(DeliveredActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DeliveredActivity activity = mActivity.get();
            if (activity == null || currentIndex == -1) return;
            if (msg.what == COMPLETE) {
                adapter.notifyDownloadState(currentIndex, COMPLETE, 0);
                currentIndex = -1;
            } else if (msg.what == DOWNLOADING) {
                adapter.notifyDownloadState(currentIndex, DOWNLOADING, msg.arg1);
            } else if (msg.what == 57) {
                Toast.makeText(activity, "下载文件失败", Toast.LENGTH_SHORT).show();
                adapter.notifyDownloadState(currentIndex,STATE_DEFAULT,0);
            }
        }
    }

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        orderNo = extras.getString("orderNo", "");
    }

    @Override
    protected void initialize(Bundle bundle) {
//        binding.titleBar
//                .setTitleBar(R.string.deliveredFiles)
//                .setShowLine(true)
//                .setImgLeftRes(R.drawable.svg_back_black);
//        setSupportActionBar(binding.toolbar);
        binding.tvTitle.setText("妥投证明");

        adapter = new DeliveredAdapter(this);
        binding.recycler.addItemDecoration(new SpaceDecoration(this, 1, R.color.line_gray));
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.empty.setDescEmpty(R.string.order_empty).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setEmptyView(binding.empty);
        adapter.setDownloadListener((position, url, fileName, downloadState) -> start_single(url, position + 1, position, downloadState));
//            addTasks(position, url,position+1,downloadState);

        binding.refresh.setOnRefreshListener(refreshLayout -> presenter.getDelivered(orderNo));

        presenter = new OrderPresenterImpl(this, this);
        presenter.getDelivered(orderNo);

        binding.ibBack.setOnClickListener(v -> beforeFinish());
        binding.ivMore.setOnClickListener(v -> showJumpPop());
    }

    private void showJumpPop() {
        if (null == mQuickJumpPop) {
            mQuickJumpPop = new QuickJumpPop();
            mQuickJumpPop.setGravity(Gravity.BOTTOM);
            mQuickJumpPop.setType(BasePop.MATCH_WRAP);
        }
        mQuickJumpPop.show(getSupportFragmentManager(), "quick");
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDarkStatusBar(android.R.color.white);
    }

    @Override
    public void loadDeliveredFileList(ArrayList<DeliveredFile> list) {
        binding.refresh.finishRefresh();
        if (null != adapter) {
            adapter.update(list, true);
        }
    }

    @Override
    public void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean firstPage, int totalCount) {

    }

    @Override
    public void loadConditionNameList(ArrayList<BuyerBean> list) {
    }

    BaseDownloadTask singleTask;
    public int singleTaskId = 0;
    public String mSinglePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "GTSC"
            + File.separator;
//    public String mSaveFolder = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "GTSC";

    /**
     * positionPlus=index+1
     */
    public void start_single(String url, int positionPlus, int position, int state) {
        Log.e("WQ", "downloadurl---" + url);
        String extension = url.substring(url.lastIndexOf("."));
        String mFilePath = mSinglePath + orderNo + "_" + positionPlus + extension;
        if (state == 2) {
            String end = mFilePath.substring(mFilePath.lastIndexOf(".") + 1).toLowerCase();
            if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                    end.equals("jpeg") || end.equals("bmp")) {
                openImage(mFilePath);
            } else {
                readFile(mFilePath);
            }
        }
        if (state == 1) {
            ToastUtil.showCenter(this, "正在下载");
            return;
        }
        if (currentIndex != -1) {
            ToastUtil.showCenter(this, "已有下载任务");
            return;
        }
        adapter.notifyDownloadState(position, 1, 0);
        currentIndex = position;
        singleTask = FileDownloader.getImpl().create(url)
//                .setPath(mSinglePath,false)
                .setPath(mFilePath, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setCallbackProgressMinInterval(10)
                .setCallbackProgressTimes(10)
                //.setTag()
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        mDownloadState = STATE_DEFAULT;
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("feifei", "progress taskId:" + task.getId() + ",progress:" + (float) soFarBytes / totalBytes);
                        mDownloadState = DOWNLOADING;
                        Message message = new Message();
                        message.arg1 = soFarBytes * 100 / totalBytes;
                        message.what = DOWNLOADING;
                        uiHandler.sendMessage(message);

                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        mDownloadState = STATE_DEFAULT;
                        Message message = new Message();
                        message.what = COMPLETE;
                        uiHandler.sendMessage(message);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        mDownloadState = STATE_DEFAULT;
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        mDownloadState = STATE_DEFAULT;
                        Message message = new Message();
                        message.what=57;
                        uiHandler.sendMessage(message);
                        Log.d("feifei", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        mDownloadState = STATE_DEFAULT;
                        Log.d("feifei", "warn taskId:" + task.getId());
                    }
                });

        singleTaskId = singleTask.start();

    }

    //    private void OpenFile() {
//
//    }
    private void readFile(String mFilePath) {
        HashMap<String, String> params = new HashMap<>();
        params.put("style", "0");
        params.put("local", "true");
        QbSdk.openFileReader(this, mFilePath, params, s -> {
        });
    }


//    public void pause_single() {
//        Log.d("feifei", "pause_single task:" + singleTaskId);
//        FileDownloader.getImpl().pause(singleTaskId);
//    }
//
//    public void delete_single() {
//
//        //删除单个任务的database记录
//        boolean deleteData = FileDownloader.getImpl().clear(singleTaskId, mSaveFolder);
//        File targetFile = new File(mSinglePath);
//        boolean delate = false;
//        if (targetFile.exists()) {
//            delate = targetFile.delete();
//        }
//
//        Log.d("feifei", "delete_single file,deleteDataBase:" + deleteData + ",mSinglePath:" + mSinglePath + ",delate:" + delate);
//
//        new File(FileDownloadUtils.getTempPath(mSinglePath)).delete();
//    }

    // 多任务下载
//    private FileDownloadListener downloadListener;
//
//    public FileDownloadListener createLis() {
//        return new FileDownloadSampleListener() {
//            @Override
//            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                if (task.getListener() != downloadListener) {
//                    return;
//                }
//                Log.d("feifei", "pending taskId:" + task.getId() + ",fileName:" + task.getFilename() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
//
//            }
//
//            @Override
//            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                if (task.getListener() != downloadListener) {
//                    return;
//                }
//                Log.d("feifei", "progress taskId:" + task.getId() + ",fileName:" + task.getFilename() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
//            }
//
//            @Override
//            protected void blockComplete(BaseDownloadTask task) {
//                if (task.getListener() != downloadListener) {
//                    return;
//                }
//                Log.d("feifei", "blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
//            }
//
//            @Override
//            protected void completed(BaseDownloadTask task) {
//                if (task.getListener() != downloadListener) {
//                    return;
//                }
//                Log.d("feifei", "completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
//            }
//
//            @Override
//            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                if (task.getListener() != downloadListener) {
//                    return;
//                }
//                Log.d("feifei", "paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
//            }
//
//            @Override
//            protected void error(BaseDownloadTask task, Throwable e) {
//                if (task.getListener() != downloadListener) {
//                    return;
//                }
//                Log.d("feifei", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
//            }
//
//            @Override
//            protected void warn(BaseDownloadTask task) {
//                if (task.getListener() != downloadListener) {
//                    return;
//                }
//                Log.d("feifei", "warn taskId:" + task.getId());
//            }
//        };
//    }

//    private void addTasks(int position, String url, int positionPlus, int downloadState) {
//        adapter.notifyDownloadState(position, 1, 0);
//        String extension = url.substring(url.lastIndexOf("."));
//        String mFilePath = mSinglePath + orderNo + "_" + positionPlus + extension;
//        urlMap.put(position, url);
//        BaseDownloadTask task = FileDownloader.getImpl().create(url).setPath(mFilePath, false);
//        tasks.add(task);
//        start_multi();
//    }

//    public void start_multi() {
//
//        downloadListener = createLis();
//        //(1) 创建 FileDownloadQueueSet
//        if (null == queueSet) {
//            queueSet = new FileDownloadQueueSet(downloadListener);
//            //(2) 创建Task 队列
//
//
////        tasks.add(task1);
////        BaseDownloadTask task2 = FileDownloader.getImpl().create(BIG_FILE_URLS[4]).setPath(mSaveFolder,true);
////        tasks.add(task2);
//
//            //(3) 设置参数
//
//            // 每个任务的进度 无回调
//            //queueSet.disableCallbackProgressTimes();
//            // do not want each task's download progress's callback,we just consider which task will completed.
//
//            queueSet.setCallbackProgressTimes(100);
//            queueSet.setCallbackProgressMinInterval(100);
//            //失败 重试次数
//            queueSet.setAutoRetryTimes(3);
//
//            //避免掉帧
//            FileDownloader.enableAvoidDropFrame();
//
//        }
//        //(4)串行下载
//        queueSet.downloadSequentially(tasks);
//
//        //(5)任务启动
//        queueSet.start();
//    }

//    public void stop_multi() {
//        FileDownloader.getImpl().pause(downloadListener);
//    }

//    public void deleteAllFile() {
//
//        //清除所有的下载任务
//        FileDownloader.getImpl().clearAllTaskData();
//
//        //清除所有下载的文件
//        int count = 0;
//        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
//        do {
//            if (!file.exists()) {
//                break;
//            }
//
//            if (!file.isDirectory()) {
//                break;
//            }
//
//            File[] files = file.listFiles();
//
//            if (files == null) {
//                break;
//            }
//
//            for (File file1 : files) {
//                count++;
//                file1.delete();
//            }
//
//        } while (false);
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        beforeFinish();
    }

    private void beforeFinish() {
        if (mDownloadState == DOWNLOADING) {
            ToastUtil.showCenter(this, "已切换到后台下载");
        }
//        Intent intent = new Intent();
//        setResult(RESULT_OK, intent);
        finish();
    }

    public void openImage(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, "com.rails.purchaseplatform.fileprovider", file);
                intent.setDataAndType(contentUri, "image/*");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivity(intent);
        } else {
            ToastUtil.showCenter(this, "图片不存在");
        }
    }

    protected void setDarkStatusBar(int color) {
        int statusBarColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBarColor = ContextCompat.getColor(this, color);
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            statusBarColor = ContextCompat.getColor(this, color);
        }
        StatusBarCompat.compat(this, statusBarColor);
    }


    @Override
    public void finish() {
        super.finish();
        if (null != mQuickJumpPop) {
            mQuickJumpPop.dismiss();
            mQuickJumpPop = null;
        }

    }
}