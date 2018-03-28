//
//  RongCloudManager.swift
//  libRNRongCloud
//
//  Created by 刘斌斌 on 28/03/2018.
//  Copyright © 2018 lotos. All rights reserved.
//

import Foundation
@objc(RongCloudManager)
class RongCloudManager: NSObject {
    @objc public func initWithAppKey(_ appkey:String!,resolver resolve:  @escaping RCTPromiseResolveBlock, rejecter reject:  @escaping RCTPromiseRejectBlock)->Void{
        RCIM.shared().initWithAppKey(appkey);
        resolve("")
        //        [[RCIM sharedRCIM] initWithAppKey:@"8luwapkvu3bbl"];
        //rongcloud
        //http://127.0.0.1:3000/user/getToken?userId=lotosbin
    }
    
    @objc public func connectWithToken(_ token:String! ,resolver resolve:  @escaping RCTPromiseResolveBlock, rejecter reject:  @escaping RCTPromiseRejectBlock)->Void{
        RCIM.shared().connect(withToken: token, success: { (userId:String!) in
            print("登陆成功。当前登录的用户ID：%@", userId);
            resolve("")
        }, error: { (status:RCConnectErrorCode) in
            print("登陆的错误码为:%d", status);
            reject("","登陆的错误码为:\(status)",nil )
        }) {
            //token过期或者不正确。
            //如果设置了token有效期并且token过期，请重新请求您的服务器获取新的token
            //如果没有设置token有效期却提示token错误，请检查您客户端和服务器的appkey是否匹配，还有检查您获取token的流程。
            print("token错误");
            reject("","token错误", nil)
            
        }
    }
    @objc public func openConversationList(_ title:String,resolver resolve:  @escaping RCTPromiseResolveBlock, rejecter reject:  @escaping RCTPromiseRejectBlock)->Void{
        //        NotificationCenter.default.post(name: NSNotification.Name(rawValue: "openConversationList"), object: nil)
        //        [[NSNotificationCenter defaultCenter] postNotificationName:TO_NEW_FEATURES_NOTIFICATION object:nil];
        DispatchQueue.main.async(execute: {() -> Void in
            let view = MyRCDMainTabBarViewController.init()
            if let rootViewController = UIApplication.topViewController() {
                //do sth with root view controller
                rootViewController.navigationController?.pushViewController(view, animated: true)
            }
        })
        
        resolve("")
    }
    @objc public func openPrivateConversation(_ targetUserId:String!,title:String,resolver resolve:  @escaping RCTPromiseResolveBlock, rejecter reject:  @escaping RCTPromiseRejectBlock)->Void{
        DispatchQueue.main.async(execute:{()->Void in
            //新建一个聊天会话View Controller对象,建议这样初始化
            let chat = MyRCConversationViewController.init()
            
            //设置会话的类型，如单聊、讨论组、群聊、聊天室、客服、公众服务会话等
            chat.conversationType = RCConversationType.ConversationType_PRIVATE;
            //设置会话的目标会话ID。（单聊、客服、公众服务会话为对方的ID，讨论组、群聊、聊天室为会话的ID）
            chat.targetId = targetUserId;
            
            //设置聊天会话界面要显示的标题
            chat.title = title;
            //显示聊天会话界面
            if let rootViewController = UIApplication.topViewController() {
                //do sth with root view controller
                rootViewController.navigationController?.pushViewController(chat, animated: true)
            }
        })
        resolve("")
    }
    
}
extension UIApplication {
    class func topViewController(base: UIViewController? = UIApplication.shared.keyWindow?.rootViewController) -> UIViewController? {
        
        if let nav = base as? UINavigationController {
            return topViewController(base: nav.visibleViewController)
        }
        
        if let tab = base as? UITabBarController {
            let moreNavigationController = tab.moreNavigationController
            
            if let top = moreNavigationController.topViewController, top.view.window != nil {
                return topViewController(base: top)
            } else if let selected = tab.selectedViewController {
                return topViewController(base: selected)
            }
        }
        
        if let presented = base?.presentedViewController {
            return topViewController(base: presented)
        }
        
        return base
    }
}

