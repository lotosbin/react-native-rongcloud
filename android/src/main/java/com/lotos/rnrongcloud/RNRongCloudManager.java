package com.lotos.rnrongcloud;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import cn.rongcloud.im.SealUserInfoManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

@SuppressWarnings("unused")
public class RNRongCloudManager extends ReactContextBaseJavaModule {


    public RNRongCloudManager(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RongCloudManager";
    }

    @ReactMethod
    public void initWithAppKey(String appkey, Promise promise) {
        Context applicationContext = this.getReactApplicationContext().getApplicationContext();
        RongIM.init(applicationContext, appkey);
        promise.resolve(null);
    }

    @ReactMethod
    public void connectWithToken(String token, final Promise promise) {

        Context applicationContext = this.getReactApplicationContext().getApplicationContext();
        if (applicationContext.getApplicationInfo().packageName.equals(getCurProcessName(applicationContext))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    promise.reject("connectWithTokenError", "Token Incorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                    SealUserInfoManager.getInstance().openDB();
                    promise.resolve(userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    promise.reject("connectWithTokenError", errorCode.getMessage());
                }
            });
        }
    }

    @ReactMethod
    public void setDeviceToken(String deviceToken, Promise promise) {

    }

    @ReactMethod
    public void openConversationList(String title, Promise promise) {
//        openSubConversationList(title);
        Context applicationContext = this.getReactApplicationContext().getApplicationContext();

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + applicationContext.getPackageName()).buildUpon();
        builder.appendPath("main")
                .appendQueryParameter("title", title)
        ;
        Uri uri = builder.build();
        intent.setData(uri);
        this.startActivity(intent);
        promise.resolve("");
    }

    private void openSubConversationList(String title) {
        Context applicationContext = this.getReactApplicationContext().getApplicationContext();

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + applicationContext.getPackageName()).buildUpon();
/*   PRIVATE(1, "private"),
        DISCUSSION(2, "discussion"),
        GROUP(3, "group"),
        CHATROOM(4, "chatroom"),
        CUSTOMER_SERVICE(5, "customer_service"),
        SYSTEM(6, "system"),
        APP_PUBLIC_SERVICE(7, "app_public_service"),
        PUBLIC_SERVICE(8, "public_service"),
        PUSH_SERVICE(9, "push_service");*/
        builder.appendPath("subconversationlist")
                .appendQueryParameter("title", title)
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.CHATROOM.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.CUSTOMER_SERVICE.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.PUSH_SERVICE.getName(), "true")
        ;
        Uri uri = builder.build();
        intent.setData(uri);
        this.startActivity(intent);
    }

    private void startActivity(Intent intent) {
        Activity currentActivity = this.getCurrentActivity();
        if (currentActivity != null) {
            currentActivity.startActivity(intent);
        } else {
            Context applicationContext = this.getReactApplicationContext().getApplicationContext();
            applicationContext.startActivity(intent);
        }
    }

    @ReactMethod
    public void openPrivateConversation(String targetUserId, String title, Promise promise) {
        RongIM.getInstance().startPrivateChat(this.getContext(), targetUserId, title);
    }

    private Context getContext() {
        Activity currentActivity = this.getCurrentActivity();
        if (currentActivity != null) {
            return currentActivity;
        }
        return this.getReactApplicationContext().getApplicationContext();
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
