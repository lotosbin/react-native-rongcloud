import React from 'react';
import {NativeModules} from 'react-native';

let rongCloud = NativeModules.RongCloudManager;

class RongCloud {
    async setDeviceToken(deviceToken: string): Promise {
        return rongCloud.initWithAppKey(deviceToken.replace(/[<> ]/, ""));
    }
    async initWithAppKey(appkey: string): Promise {
        return rongCloud.initWithAppKey(appkey);
    }

    async getToken(getTokenEndpoint, userId): string {
        const response = await  fetch(getTokenEndpoint + "?userId=" + userId);
        if (response.ok) {
            const result = await response.json();
            if (!result.token) {
                throw new Error('获取token异常')
            }
            return result.token;
        } else {
            let message = await response.text();
            throw new Error(message)
        }
    }

    async connectWithToken(token: string): Promise {
        return rongCloud.connectWithToken(token);
    }
    async openConversationList(title:string):Promise {
        return rongCloud.openConversationList(title);
    }
    async openPrivateConversation(targetUserId:string,title:string):Promise {
        return rongCloud.openPrivateConversation(targetUserId,title);
    }

    async disconnect(isReceivePush: boolean): Promise {
        return rongCloud.openPrivateConversation(isReceivePush || false);
    }
}

export default new RongCloud();
