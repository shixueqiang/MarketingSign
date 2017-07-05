package com.marketing.sign.app;

import android.app.Application;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.marketing.sign.utils.CrashHandler;
import com.marketing.sign.utils.FileUtil;

/**
 * Created by shixq on 2017/6/17.
 */

public class MSApplication extends Application {
    private String TAG = "MarketingSign";

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        CrashHandler crashHandler= CrashHandler.getInstance();
        crashHandler.init(this);
    }

    private void init() {
        LogConfiguration config = new LogConfiguration.Builder()
                .tag(TAG)
                .build();
        Printer androidPrinter = new AndroidPrinter();             // 通过 android.util.Log 打印日志的打印器
        Printer filePrinter = new FilePrinter                      // 打印日志到文件的打印器
                .Builder(FileUtil.LOG_FILE_PATH)                    // 指定保存日志文件的路径
                .fileNameGenerator(new DateFileNameGenerator())    // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                .backupStrategy(new NeverBackupStrategy())         // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
                .build();
        XLog.init(config, androidPrinter, filePrinter);
    }
}
